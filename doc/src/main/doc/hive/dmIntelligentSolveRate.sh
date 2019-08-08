#!/bin/bash

#接受参数格式：2017-12-13

source /etc/profile
DB="fu"
TABLE="dm_fu_rate"
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
V_PATH=${PATH_PREFIX}/"whouse"/${TABLE}/"year="$V_YEAR_OF_DATE/"month="$V_MONTH_OF_DATE/"day="$V_DAY_OF_DATE

log="[INFO] "`date`"The date is:$v_p_DATE, path is:$V_PATH"
echo $log
echo $log >> in.log


spark-sql --queue r.i.p -e "
set spark.sql.shuffle.partitions=50;
set hive.merge.mapfiles=true;
set hive.merge.mapredfiles=true;
set hive.merge.smallfiles.avgsize=3000;
INSERT
OVERWRITE TABLE $DB.$TABLE PARTITION(year='$V_YEAR_OF_DATE',month='$V_MONTH_OF_DATE',day='$V_DAY_OF_DATE')
SELECT
    create_time,
    a,
    a,
    a,
    a,
    a,
    a,
    a,
    a,
    a,
    a,
    (CASE WHEN channel_id=1 THEN '自助' WHEN channel_id=2 THEN 'rvi' WHEN channel_id=3 THEN '机器人' WHEN channel_id=4 THEN 'APP Close' WHEN channel_id=5 THEN 'zn取消' ELSE 'channel_id枚举值错误' END) AS channel_name
FROM fu.dm_tmp
    WHERE concat_ws('-',year,month,day)='$v_p_DATE';
"
line_num=0
line_num=`$HADOOP_HOME/bin/hadoop fs -du -s ${V_PATH}/ |tail -1 | awk '{print $1}'`
if [ $line_num -gt 0 ]
then
    $HADOOP_HOME/bin/hadoop fs -touchz $V_PATH/_SUCCESS
else
    echo "fail ${TABLE} is empty"
fi

log="[INFO] "`date`" Calculate zn解决率"$TABLE" completed."
echo $log
echo $log >> intelligentSolveRate.log
