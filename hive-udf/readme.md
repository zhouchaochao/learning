

[hive UDF 测试样例开发] https://blog.csdn.net/zwjzqqb/article/details/79042636
[hive UDF UDAF demo code] https://github.com/rathboma/hive-extension-examples/blob/master/src/main/java/com/matthewrathbone/example/ComplexUDFExample.java


打包jar
cd /Users/di————di/IdeaProjects/learning/hive-udf
mvn clean package -Dmaven.test.skip=true
打包后生成jar文件:
.../hive-udf/target/hive-udf-1.0-SNAPSHOT-sources.jar

上传
cd /Users/di————di/IdeaProjects/learning/hive-udf/target/
curl http://100————.69————.238————.36:8000/resource/tiyan/down/hive-udf-1.0-SNAPSHOT201807181048.jar -X POST -F filecontent=@hive-udf-1.0-SNAPSHOT.jar
然后下载到有hive客户端的机器上

1.创建临时函数测试
hive> 
add jar ~/zhouchaochao/udf/hive-udf-1.0-SNAPSHOT201807181048.jar;
create temporary function cc_trace_udaf as 'com.cc.TraceUDAF';
create temporary function cc_duplicateRemoval_udf as 'com.cc.DuplicateRemovalUDF';
create temporary function cc_complexExample_udf as 'com.cc.ComplexUDFExample';
create temporary function cc_strconcat_udaf as 'com.cc.StrConcatUDAF';
create temporary function cc_hello_udf as 'com.cc.HelloUDF';
create temporary function cc_maxmin_udaf as 'com.cc.GenericUDAFMaxMin';
create temporary function cc_numofletters_udaf as 'com.cc.TotalNumOfLettersGenericUDAF';

hive> create temporary function cc_hello_udf as 'com.cc.HelloUDF';
OK
Time taken: 0.813 seconds
hive>

hive> show functions like 'cc_*';
OK
tab_name
cc_hello_udf
Time taken: 0.312 seconds, Fetched: 1 row(s)
hive>
hive> select cc_hello_udf('hello'),'world';
OK
_c0     _c1
hellohello      world
Time taken: 0.571 seconds, Fetched: 1 row(s)
hive>

如果退出终端后在打开，函数失效。

SELECT
v3,cc_maxmin_udaf(v1)
FROM
(
select 1 AS v1,'world' AS v2,'a' AS v3
UNION ALL
select 2 AS v1,'hi' AS v2,'a' AS v3
UNION ALL
select 4 AS v1,'hi' AS v2,'b' AS v3
UNION ALL
select 3 AS v1,'world' AS v2,'b' AS v3
) t1
GROUP BY v3


SELECT
v3,cc_numofletters_udaf(v2)
FROM
(
select 1 AS v1,'world' AS v2,'a' AS v3
UNION ALL
select 2 AS v1,'hi' AS v2,'a' AS v3
UNION ALL
select 4 AS v1,'hi' AS v2,'a' AS v3
UNION ALL
select 3 AS v1,'ab' AS v2,'b' AS v3
) t1
GROUP BY v3


SELECT
v3,cc_trace_udaf(v2)
FROM
(
select 1 AS v1,'world' AS v2,'a' AS v3
UNION ALL
select 2 AS v1,'hi' AS v2,'a' AS v3
UNION ALL
select 4 AS v1,'hi' AS v2,'a' AS v3
UNION ALL
select 3 AS v1,'ab' AS v2,'b' AS v3
) t1
GROUP BY v3



SELECT
v3,cc_trace2_udaf(v2)
FROM
(
select 1 AS v1,'world' AS v2,'a' AS v3
UNION ALL
select 2 AS v1,'hi' AS v2,'a' AS v3
UNION ALL
select 4 AS v1,'hi' AS v2,'a' AS v3
UNION ALL
select 3 AS v1,'ab' AS v2,'b' AS v3
UNION ALL
select 1 AS v1,'d_f' AS v2,'c' AS v3
UNION ALL
select 2 AS v1,'e_h' AS v2,'c' AS v3
) t1
GROUP BY v3


SELECT
v3,cc_trace_udaf(v2)
FROM
(
select 1 AS v1,'101:A' AS v2,'a' AS v3
UNION ALL
select 2 AS v1,'102:B' AS v2,'a' AS v3
UNION ALL
select 4 AS v1,'103:B' AS v2,'a' AS v3
UNION ALL
select 3 AS v1,'104:B' AS v2,'a' AS v3
UNION ALL
select 3 AS v1,'106:C' AS v2,'a' AS v3
UNION ALL
select 3 AS v1,'107:C' AS v2,'a' AS v3
UNION ALL
select 1 AS v1,'106:A' AS v2,'b' AS v3
UNION ALL
select 2 AS v1,'105:D' AS v2,'b' AS v3
UNION ALL
select 4 AS v1,'103:B' AS v2,'b' AS v3
UNION ALL
select 3 AS v1,'103:B' AS v2,'b' AS v3
UNION ALL
select 3 AS v1,'102:A' AS v2,'b' AS v3
UNION ALL
select 3 AS v1,'101:A' AS v2,'b' AS v3
) t1
GROUP BY v3


SELECT
v3,SORT_ARRAY(collect_list(v2)),cc_duplicateRemoval_udf(collect_list(v2),'_')
FROM
(
select 1 AS v1,'101:A' AS v2,'a' AS v3
UNION ALL
select 2 AS v1,'102:B' AS v2,'a' AS v3
UNION ALL
select 4 AS v1,'103:B' AS v2,'a' AS v3
UNION ALL
select 3 AS v1,'104:B' AS v2,'a' AS v3
UNION ALL
select 3 AS v1,'106:C' AS v2,'a' AS v3
UNION ALL
select 3 AS v1,'107:C' AS v2,'a' AS v3
UNION ALL
select 1 AS v1,'106:A' AS v2,'b' AS v3
UNION ALL
select 2 AS v1,'105:D' AS v2,'b' AS v3
UNION ALL
select 4 AS v1,'103:B' AS v2,'b' AS v3
UNION ALL
select 3 AS v1,'103:B' AS v2,'b' AS v3
UNION ALL
select 3 AS v1,'102:A' AS v2,'b' AS v3
UNION ALL
select 3 AS v1,'101:A' AS v2,'b' AS v3
) t1
GROUP BY v3;
OK
v3      _c1     _c2
a       ["101:A","102:B","103:B","104:B","106:C","107:C"]       A_B_C
b       ["101:A","102:A","103:B","103:B","105:D","106:A"]       A_B_D_A


select cc_complexExample_udf(array('a','b','c'),'a');




2.正式环境创建永久函数

将jar包上传到hdfs上
hadoop fs -put -f ~/zhouchaochao/udf/hive-udf-1.0-SNAPSHOT201807181048.jar /user/cs/jar
对应的全路径为：hdfs://D————Cluster————Nmg4/user/cs/jar/hive-udf-1.0-SNAPSHOT201807181048.jar


创建永久函数
hive>
CREATE FUNCTION cc_duplicate_removal_udf AS 'com.cc.DuplicateRemovalUDF' USING JAR 'hdfs://DClusterNmg4/user/cs/jar/hive-udf-1.0-SNAPSHOT201807181048.jar';

然后就可以在任何地方使用函数 cc_duplicate_removal_udf 了。

