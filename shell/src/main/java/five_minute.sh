#!/bin/bash

# 最近5分钟的区间.
TWO_AND_A_HALF_MINUTE=150
FIVE_MINUTE=300

NOW=$(date +%s)
echo "NOW:"$NOW"  "`date -d @$NOW "+%Y%m%d %H:%M:%S"`

#当前时间加上2.5分钟
CURRENT_ADD_TWO_AND_A_HALF_MINUTE=$(($NOW + $TWO_AND_A_HALF_MINUTE))

START_SECOND=$(($CURRENT_ADD_TWO_AND_A_HALF_MINUTE - $(($CURRENT_ADD_TWO_AND_A_HALF_MINUTE % $FIVE_MINUTE ))))
END_SECOND=$(($START_SECOND + $FIVE_MINUTE))

START_TIME=`date -d @$START_SECOND "+%Y%m%d %H:%M:%S"`
END_TIME=`date -d @$END_SECOND "+%Y%m%d %H:%M:%S"`

echo "执行开始时间:"$START_TIME " 结束时间:"$END_TIME

