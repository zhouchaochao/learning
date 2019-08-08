#!/bin/bash

#接受参数格式：2017-12-13

source /etc/profile
DB="fu"
TABLE="dm_temp"
ITEM_KEY="robot"
echo $TABLE

v_p_DATE=$1
if [ -z ${v_p_DATE} ];then
  v_p_DATE=`date --date="-2 day" +%Y-%m-%d`
fi
v_p_DATE=`date --date="$v_p_DATE" +%Y-%m-%d`
echo "数据日期:$v_p_DATE"

V_YEAR_OF_DATE=`date --date="$v_p_DATE" +%Y`
V_MONTH_OF_DATE=`date --date="$v_p_DATE" +%m`
V_DAY_OF_DATE=`date --date="$v_p_DATE" +%d`

PATH_PREFIX='/user/cc'
TRASH_PATH=${PATH_PREFIX}/.Trash/*
V_PATH=${PATH_PREFIX}/"whouse"/${TABLE}/"year="$V_YEAR_OF_DATE/"month="$V_MONTH_OF_DATE/"day="$V_DAY_OF_DATE/"item_key="$ITEM_KEY

log="[INFO] "`date`"The date is:$v_p_DATE, path is:$V_PATH"
echo $log
echo $log >> a.log

# delete yesterday's data, to save file size quota
#echo "remove old data: $TABLE"
$HADOOP_HOME/bin/hadoop fs -rm -r -f -skipTrash $TRASH_PATH
$HADOOP_HOME/bin/hadoop fs -rm -r -f -skipTrash $V_PATH


spark-sql --queue r.i.p -e "
set spark.sql.shuffle.partitions=50;
set hive.merge.mapfiles=true;
set hive.merge.mapredfiles=true;
set hive.merge.smallfiles.avgsize=3000;
INSERT
OVERWRITE TABLE $DB.$TABLE PARTITION(year='$V_YEAR_OF_DATE',month='$V_MONTH_OF_DATE',day='$V_DAY_OF_DATE',item_key='$ITEM_KEY')
SELECT
    '$v_p_DATE' as create_time,
    business_type,
    customer_type,
    city_id,
    sum(case when unsolved_times = 0 then 1 else 0 end) AS solve_count,
    COUNT(1) AS serve_count
FROM
(
    SELECT  serve_record_table.key_id,
            serve_record_table.business_type,
            serve_record_table.customer_type,
            serve_record_table.city_id,
            sum(if(rengong_table.cell is not null and serve_record_table.timestamp_t<=rengong_table.timestamp_t and (unix_timestamp(rengong_table.timestamp_t) - unix_timestamp(serve_record_table.timestamp_t))<86400,1,0)) AS unsolved_times
    FROM (
        SELECT 	param['isc_online_trace_id'] AS key_id,
                param['business_type'] AS business_type,
                param['customer_type'] AS customer_type,
                param['city_id'] AS city_id,
                param['isc_user_phone'] AS cell,
                from_unixtime(unix_timestamp(param['time'],'yyyyMMdd HH:mm:ss')) AS timestamp_t
        FROM fu.ods
        WHERE param['isc_channel_id']=2
        AND concat_ws('-',year,month,day)='$v_p_DATE'
        AND (param['isc_come_from']<100 OR param['isc_come_from']=101 OR param['isc_come_from']=1000)
    ) serve_record_table
    LEFT JOIN(
        SELECT cell,
               timestamp_t
        FROM(
            SELECT ani AS cell,
                   statisticdate AS timestamp_t,
                   vq
            FROM fu.a
            WHERE ani IS NOT NULL
            AND (concat_ws('-',year,month,day)='$v_p_DATE' OR concat_ws('-',year,month,day)=date_add('$v_p_DATE',1))
        ) table_a
        LEFT JOIN
        (
            SELECT DISTINCT object_num
            FROM fu.a
            WHERE dd2=1 AND a_type = 5
            AND concat_ws('-',year,month,day)=date_sub(current_date,1)
        ) table_b
        ON table_a.vq=table_b.object_num
        WHERE table_b.object_num IS NULL

        UNION ALL

        SELECT 	consumer_phone AS cell,
                create_time AS timestamp_t
        FROM fu.a
        WHERE consumer_phone IS NOT NULL
        AND create_time IS NOT NULL
        AND concat_ws('-',year,month,day)>='$v_p_DATE' AND concat_ws('-',year,month,day)<=date_add('$v_p_DATE',1)
    ) rengong_table
    ON serve_record_table.cell=rengong_table.cell
    GROUP BY
        serve_record_table.business_type,
        serve_record_table.customer_type,
        serve_record_table.key_id,
        serve_record_table.city_id
) slove_detail_table
GROUP BY business_type,customer_type,city_id;
"
line_num=0
line_num=`$HADOOP_HOME/bin/hadoop fs -du -s ${V_PATH}/ |tail -1 | awk '{print $1}'`
if [ $line_num -gt 0 ]
then
    $HADOOP_HOME/bin/hadoop fs -touchz $V_PATH/_SUCCESS
else
    echo "fail ${TABLE} is empty"
fi

log="[INFO] "`date`" Calculate robot solve rate completed."
echo $log
echo $log >> intelligentSolveRate.log

