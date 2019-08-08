#!/bin/bash

#接受参数格式：2017-12-13

source /etc/profile
DB="fu"
TABLE="dm_fu_tmp"
CHANNEL_ID=2
echo $TABLE" CHANNEL_ID:"$CHANNEL_ID

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
V_PATH=${PATH_PREFIX}/"whouse"/${TABLE}/"year="$V_YEAR_OF_DATE/"month="$V_MONTH_OF_DATE/"day="$V_DAY_OF_DATE/"channel_id="$CHANNEL_ID

log="[INFO] "`date`"The date is:$v_p_DATE, path is:$V_PATH"
echo $log
echo $log >> int.log


spark-sql --queue r.i.p -e "
set spark.sql.shuffle.partitions=50;
set hive.merge.mapfiles=true;
set hive.merge.mapredfiles=true;
set hive.merge.smallfiles.avgsize=3000;
INSERT
OVERWRITE TABLE $DB.$TABLE PARTITION(year='$V_YEAR_OF_DATE',month='$V_MONTH_OF_DATE',day='$V_DAY_OF_DATE',channel_id='$CHANNEL_ID')
SELECT
    '$v_p_DATE' as create_time,
    base.business_type,
    business_t.business_name,
    base.customer_type,
    customer_t.customer_name,
    base.question_id,
    base.question_name,
    base.solve_count,
    base.serve_count,
    base.solve_count/base.serve_count AS solve_rate
FROM(
    SELECT business_type,
           customer_type,
           question_id,
           question_name,
           COUNT(1) AS serve_count,
           sum(case when unsolved_times = 0 then 1 else 0 end) AS solve_count
    FROM
    (
        SELECT 	serve_record_table.key_id,
                serve_record_table.business_type,
        		serve_record_table.customer_type,
        		serve_record_table.question_id,
        		serve_record_table.question_name,
        	    sum(if(rengong_table.cell is not null and serve_record_table.timestamp_t<=rengong_table.timestamp_t and (unix_timestamp(rengong_table.timestamp_t) - unix_timestamp(serve_record_table.timestamp_t))<86400,1,0)) AS unsolved_times
        FROM
        (
        	SELECT  rvi_data_t.cell,
                    rvi_data_t.timestamp_t,
            		rvi_data_t.business_type,
            		rvi_data_t.customer_type,
            		rvi_data_t.question_id,
            		rvi_node_t.question_name,
            		rvi_data_t.key_id
            FROM
            (
                SELECT  ani AS cell,
                        statisticdate AS timestamp_t,
                        buid AS business_type,
                        ddcc4 AS customer_type,
                        presskey AS question_id,
                        connid AS key_id
                FROM fu.edw_t_rvi_data
                WHERE concat_ws('-',year,month,day)='$v_p_DATE'
                     AND datatype IN ('hangup','dtmferror','notworktime','transfer')
                     AND calltype=0
            ) rvi_data_t
            LEFT JOIN(
                SELECT DISTINCT rvi_id,name AS question_name
                FROM fu.a
                WHERE a_type IN (11,12,13) AND concat_ws('-',year,month,day)=date_sub(current_date,2)
            ) rvi_node_t
            ON rvi_data_t.question_id=rvi_node_t.rvi_id
            WHERE rvi_node_t.rvi_id IS NOT NULL
        ) serve_record_table
        LEFT JOIN (
            SELECT cell,
                   timestamp_t
            FROM(
                SELECT ani AS cell,
                       statisticdate AS timestamp_t,
                       vq
                FROM fu.a
                WHERE ani IS NOT NULL
                AND concat_ws('-',year,month,day)>='$v_p_DATE' AND concat_ws('-',year,month,day)<=date_add('$v_p_DATE',1)
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

            SELECT consumer_phone AS cell,
                   create_time AS timestamp_t
            FROM fu.a
            WHERE consumer_phone IS NOT NULL
            AND create_time IS NOT NULL
            AND concat_ws('-',year,month,day)>='$v_p_DATE' AND concat_ws('-',year,month,day)<=date_add('$v_p_DATE',1)
        ) rengong_table
        ON serve_record_table.cell=rengong_table.cell
        GROUP BY
            serve_record_table.key_id,
            serve_record_table.business_type,
            serve_record_table.customer_type,
            serve_record_table.question_id,
            serve_record_table.question_name
    )slove_detail_table
    GROUP BY business_type,customer_type,question_id,question_name
) base
LEFT JOIN ( SELECT id,name AS business_name FROM fu.a WHERE concat_ws ('-',YEAR,MONTH,DAY)=date_sub(current_date,2)) business_t
ON base.business_type=business_t.id
LEFT JOIN ( SELECT id,name AS customer_name FROM fu.b WHERE concat_ws ('-',YEAR,MONTH,DAY)=date_sub(current_date,2)) customer_t
ON base.customer_type = customer_t.id;
"
line_num=0
line_num=`$HADOOP_HOME/bin/hadoop fs -du -s ${V_PATH}/ |tail -1 | awk '{print $1}'`
if [ $line_num -gt 0 ]
then
    $HADOOP_HOME/bin/hadoop fs -touchz $V_PATH/_SUCCESS
else
    echo "fail ${TABLE} is empty"
fi

