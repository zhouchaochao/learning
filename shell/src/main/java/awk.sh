// awk -F"fengge" '{print "\"vvv\"" $2}' 表示使用字符串  fengge  作为分隔符，打印字符串 "vvv"，然后打印分割后的第二个元素
// awk -F",\"from" '{print $1}' 表示使用  ,"from   作为分隔符，打印分割后的第一个元素
//去重：sort|uniq -c    uniq只能相邻去重，所以需要先排序
grep xxxx dd.log|awk -F"fengge" '{print "\"vvv\"" $2}'|awk -F",\"from" '{print $1}'|sort|uniq -c
结果：第一列是序号
1 "vvv"5,"app_version":"6.15.7"
1 "vvv":5,"app_version":"6.16.4"

// 用于从特别长的字符串中提取关键信息
// 从一大段字符串中，截取找到 从 dest_timestamp 开始，到 finish_time 结束的字符串，以及从 finish_time 开始的字符串，并换行，然后以 , 分割，截取前面的
grep order_finished public.log|tail -n 4|awk -F"dest_timestamp" '{print "\"开始1\"" $2}'|awk -F"finish_time" '{print $1 "\n" "\"开始2\"" $2}'|awk -F"," '{print $1}'
结果：
"开始1"":"1715346354"
"开始2"":"2024-05-10 21:05:53"
"开始1"":"1715346354"
"开始2"":"2024-05-10 21:05:53"
"开始1"":"1715346354"
"开始2"":"2024-05-10 21:05:53"
"开始1"":"1715346354"
"开始2"":"2024-05-10 21:05:54"






