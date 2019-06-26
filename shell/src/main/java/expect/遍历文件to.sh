#!/bin/sh
###################
#auto login shell##
###################

echo `pwd`
#find /Users/cc/Tools/scripts/vm -name "*.sh"
#cd /Users/cc/Tools/scripts/vm

echo "例子："
echo "sh /Users/cc/Tools/scripts/vm/common/connect.sh 3 dssh 10.160.128.37  'sudo -iu cc'"

#将find的文件awk后保存到数组
SHELLL_ARRAY=($(find /Users/cc/Tools/scripts/vm -name "to*.sh"| awk '{print $1}'))

#通过下标遍历数据
for(( i=0;i<${#SHELLL_ARRAY[@]};i++))
do
    echo "$i "${SHELLL_ARRAY[i]};
done;

read -p "请输入选项:" check_item
echo "sh ${SHELLL_ARRAY[$check_item]} $d_key"
sh ${SHELLL_ARRAY[$check_item]} $d_key

