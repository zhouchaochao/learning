#!/bin/bash

CONFIG_PATH="/Users/cc/IdeaProjects/learning/shell/src/main/resources"
FILE_PATH=${CONFIG_PATH}/readfile.config

echo "FILE_PATH:${FILE_PATH}"
linenum=0
cat $FILE_PATH | while read oneline
do
    ((linenum=${linenum}+1))
    echo "第${linenum}行："

    if [ -z "${oneline}" ];then
      echo "空行:${oneline}"
    fi

    if [ -n "${oneline}" ];then
      echo "非空行:${oneline}"
    fi

done


run_path="/home/cc/datax/bin/"
job_path="/home/cc/stargate-job/datax-job"
json_path="$job_path/jsons/"

start_date=`date --date="-1 day" +%Y-%m-%d`
end_date=`date --date="-0 day" +%Y-%m-%d`

# table1.config 中使用 2018-01-01

echo $start_date $end_date

file1=$job_path/table1.config

cat $file1 | while read json
do
    if [ -n "${json}" ];then
        echo `date` "run $json begin"
        python $run_path/datax.py -p "-Dstartdate='$start_date' -Denddate='$end_date' " $json_path/$json
    fi
done


