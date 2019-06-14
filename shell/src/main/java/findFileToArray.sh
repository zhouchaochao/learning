#!/bin/bash
#将find命令查找到的结果写入数组中

SHELLL_ARRAY=($(find /Users/didi/Tools/scripts/vm -name "to*.sh"| awk '{print $1}'))


#通过下标遍历数据
for(( i=0;i<${#SHELLL_ARRAY[@]};i++))
do
    echo "$i:"${SHELLL_ARRAY[i]};
done;

read -p "请输入选项:" check_item
echo "通过下标取数组值:${SHELLL_ARRAY[$check_item]}"
