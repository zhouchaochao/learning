// awk -F"fengge" '{print "\"vvv\"" $2}' 表示使用字符串  fengge  作为分隔符，打印字符串 "vvv"，然后打印分割后的第二个元素
// awk -F",\"from" '{print $1}' 表示使用  ,"from   作为分隔符，打印分割后的第一个元素
//去重：sort|uniq -c    uniq只能相邻去重，所以需要先排序
grep xxxx dd.log|awk -F"fengge" '{print "\"vvv\"" $2}'|awk -F",\"from" '{print $1}'|sort|uniq -c
结果：第一列是序号
1 "vvv"5,"app_version":"6.15.7"
1 "vvv":5,"app_version":"6.16.4"





