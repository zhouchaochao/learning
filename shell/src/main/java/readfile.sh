#!/bin/bash

CONFIG_PATH="/Users/didi/IdeaProjects/learning/shell/src/main/resources"
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
