#!/bin/bash

NOW_TIMESTAMP=`date '+%Y-%m-%d %H:%M:%S'`
echo "当前时间戳:${NOW_TIMESTAMP}"

YESTERDAY_Y_M_D=`date --date="-1 day" '+%Y-%m-%d'`
echo "昨天:${YESTERDAY_Y_M_D}"

TOMORROW_YMD=`date --date="1 day" '+%Y%m%d'`
echo "明天:${TOMORROW_YMD}"

TIME1="2018-05-31"
TIME1_ADD3=`date --date="+3 day $TIME1" '+%Y%m%d'`
TIME1_ADD_MONTH=`date --date="+1 month $TIME1" '+%Y%m%d'`
#输出20180531
echo "TIME1_ADD3:${TIME1_ADD3}"
#输出20180701
echo "TIME1_ADD_MONTH:${TIME1_ADD_MONTH}"

TIME2="20180701"
TIME2_ADD3=`date --date="+3 day $TIME2" '+%Y%m%d'`
#输出20180704
echo "TIME2_ADD3:${TIME2_ADD3}"

TIME3="2018-07-01 12:12:12"
TIME3_ADD3=`date --date="+3 day $TIME3" '+%Y%m%d %H:%M:%S'`
#输出20180704 12:12:12
echo "TIME3_ADD3:${TIME3_ADD3}"

#日期转秒
TIME4="2018-07-01 12:12:12"
TIME4_SECONDS=`date --date="$TIME4" '+%s'`
NOW_SECONDS=`date '+%s'`
#输出1530418332
echo "TIME4_SECONDS:${TIME4_SECONDS}"
echo "当前时间s:${NOW_SECONDS}"

#秒转日期
SECOND1=1530418394
SECOND1_TO_DATE1=`date --date="@$SECOND1" '+%Y-%m-%d %H:%M:%S'`
#输出2018-07-01 12:13:14
echo "SECOND1_TO_DATE1:${SECOND1_TO_DATE1}"
#加上一天的秒数86400
SECOND2=`expr $SECOND1 + 86400`
#输出 2018-07-02 12:13:14
echo "加上一天秒数之后:"`date --date="@$SECOND2" '+%Y-%m-%d %H:%M:%S'`


#日期比较
date1="2008-4-09 12:00:00"
date2="2008-4-10 15:00:00"
date1_SECONDS=`date --date="$date1" +%s`
date2_SECONDS=`date --date="$date2" +%s`
if [ $date1_SECONDS -gt $date2_SECONDS ]; then
    echo "$date1_SECONDS > $date2_SECONDS"
elif [ $date1_SECONDS -eq $date2_SECONDS ]; then
    echo "$date1_SECONDS == $date2_SECONDS"
else
    echo "$date1_SECONDS < $date2_SECONDS"
fi


start_date=$1
if [ -z ${start_date} ];then
      start_date=`date --date="-1 day" +%Y-%m-%d`
fi
start_date=`date --date="$start_date" +%Y-%m-%d`

end_date=$2
if [ -z ${end_date} ];then
      end_date=`date --date="-0 day" +%Y-%m-%d`
fi
end_date=`date --date="$end_date" +%Y-%m-%d`

echo $start_date $end_date


