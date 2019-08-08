#!/bin/bash

#接受参数格式：2017-12-13
# 删除表中当天数据

source /etc/profile
DB="k"
TABLES=(
"dm_tmp"
"dm_rate"
)
echo $TABLES

v_p_DATE=$1
if [ -z ${v_p_DATE} ];then
  v_p_DATE=`date --date="-2 day" +%Y-%m-%d`
fi
v_p_DATE=`date --date="$v_p_DATE" +%Y-%m-%d`
echo "数据日期:$v_p_DATE"

V_YEAR_OF_DATE=`date --date="$v_p_DATE" +%Y`
V_MONTH_OF_DATE=`date --date="$v_p_DATE" +%m`
V_DAY_OF_DATE=`date --date="$v_p_DATE" +%d`


for TABLE in ${TABLES[@]};
do

        PATH_PREFIX='/user/cc'
        TRASH_PATH=${PATH_PREFIX}/.Trash/*
        V_PATH=${PATH_PREFIX}/"whouse"/${TABLE}/"year="$V_YEAR_OF_DATE/"month="$V_MONTH_OF_DATE/"day="$V_DAY_OF_DATE

        log="[INFO] "`date`" The date is:$v_p_DATE, path is:$V_PATH"
        echo $log
        echo $log >> intelligentSolveRate.log

        # delete yesterday's data, to save file size quota
        echo "remove old data: $TABLE"
        $HADOOP_HOME/bin/hadoop fs -rm -r -f -skipTrash $TRASH_PATH
        $HADOOP_HOME/bin/hadoop fs -rm -r -f -skipTrash $V_PATH

done


