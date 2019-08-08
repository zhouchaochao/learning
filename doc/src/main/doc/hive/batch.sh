#!/bin/bash
#接受两个参数：2017-07-01 2017-08-01

index=0
startdate=`date -d "+0 day $1" +%Y%m%d`
echo $startdate
enddate=`date -d "+0 day $2" +%Y%m%d`
echo $enddate
while (($startdate < $enddate))
do
    startdate=`date -d "+0 day $startdate" +%Y-%m-%d`
    DOWN_DATES[$index]=$startdate
    startdate=`date -d "+1 day $startdate" +%Y%m%d`
    index=`expr $index + 1`
done

for DOWN_DATE in ${DOWN_DATES[*]}
do

    echo `date`" begin:"${DOWN_DATE}
    echo `date`" begin:"${DOWN_DATE} >> batch.log

    sh oldNoQid.sh ${DOWN_DATE}


    echo `date`" end:"${DOWN_DATE}
    echo `date`" end:"${DOWN_DATE} >> batch.log

    echo "=======complete ${DOWN_DATE}========"
    sleep 3s
    echo "=======prepare to execute next day========"
    sleep 1s

done

exit 0
