#!/bin/bash
#TODO

arr1=(
"v1"
v2
"v3"

"v4"
v5
)

#类似foreach的方式遍历
for one_value in ${arr1[*]}
do
    echo "数组arr1内容:${one_value}"
done

#通过下标遍历数据
for(( i=0;i<${#arr1[@]};i++))
do
    echo "$i:"${arr1[i]};
done;

ARRAY_LENGTH=`${#arr1[@]}`
echo "数组长度："$arr1


#空格当做分隔符了
index=0
while (($index < 4))
do
    #直接指定数组index进行赋值
    ARR2[$index]="value${index}"
    ((index=$index+1))
    #index=`expr $index + 1`
done

ARR2[10]="value4 空格      有问题"
ARR2[10]="value10 空格      有问题"

for ITEM_ARR2 in ${ARR2[*]}
do
    echo "ARR2内容:${ITEM_ARR2}"
done


