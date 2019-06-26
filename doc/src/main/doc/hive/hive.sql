Hive


我的第一条sql:
select * from kefu.dwm_kefu_smart_analysis_statistics where concat_ws('-',year,month,day)='2017-11-11' limit 10



查询语句例子：
SELECT
    '2017-11-20' as create_time,
    business_type,
    business_type_name,
    issued_count,
    satisfaction_count/participatin_count as satisfaction_rate,
    participatin_count/issued_count as participatin_rate,
    one_star_count,
    meiyouxiangyaodegongneng,
    meiyouzhaodaorengongfuwu,
    '2017-11-20' as stat_date
FROM
    kefu.dwm_intelligent_satisfaction_statistics
WHERE concat_ws('-',year,month,day)='2017-11-20';


select value1,value2,value1+value2,COALESCE(value1,0)+COALESCE(value2,0)
FROM
(
select 10 AS value1,10 AS value2
UNION ALL
select 20 AS value1,NULL AS value2) tmp;
总条数:2
10 10
20 20
20 null
null 20



null加法 COALESCE
----------------------------------------------------------------------------

select 1 + NULL from dual;
OK
_c0
NULL


SELECT table_a.value1 + table_a.value2
FROM (SELECT 100 AS value1,null AS value2) table_a;
OK
_c0
NULL


加法遇上null结果变成null，所以对于加法操作，必须使用COALESCE(value1,0)+COALESCE(value2,0)
select value1,value2,value1+value2,COALESCE(value1,0)+COALESCE(value2,0)
FROM
(
select 10 AS value1,10 AS value2
UNION ALL
select 20 AS value1,NULL AS value2) tmp;
总条数:2
10 10
20 20
20 null
null 20



sum函数包含null也没关系
select sum(value2)
FROM
(
select 10 AS value1,10 AS value2
UNION ALL
select 20 AS value1,NULL AS value2) tmp;
总条数:1
10


union all
------------------------------------------------------

select *
FROM
(
select 10
UNION ALL
select 20) tmp;
总条数:2
10
20


select 10 AS value1,10 AS value2
UNION ALL
select 20 AS value1,NULL AS value2
UNION ALL
select 30 AS value1,10 AS value2
UNION ALL
select 40 AS value1,NULL AS value2
总条数:4
10 10
20 null
30 10
40 null



SELECT table_a.value1,table_b.value1
FROM
(
select 0 AS join_value,1 AS value1
UNION ALL
select 0 AS join_value,2 AS value1
) table_a
LEFT JOIN
(
select 0 AS join_value,1 AS value1
UNION ALL
select 0 AS join_value,2 AS value1
UNION ALL
select 0 AS join_value,3 AS value1
) table_b
ON table_a.join_value=table_b.join_value
总条数:6
1 1
1 2
1 3
2 1
2 2
2 3



一行变多行 explode(） 和 lateral view
------------------------------------------------------

hive> SELECT explode(split('a b c', ' ')) AS column1;
OK
column1
a
b
c


select
    tmp_column
from (SELECT 1) tmp_table
lateral view explode(split('1,3,5,10', ',')) myTable as tmp_column;
总条数:4
1
3
5
10

select column1,
    tmp_column
from (SELECT 1 AS column1) tmp_table
lateral view explode(split('1,3,5,10', ',')) myTable as tmp_column;
总条数:4
1 1
1 3
1 5
1 10


select column1,
    tmp_column
from
(
select tmp_column AS column1
from (SELECT 1) tmp_table
lateral view explode(split('a,b,c', ',')) myTable as tmp_column
) tmp_table
lateral view explode(split('1,3,5,10', ',')) myTable as tmp_column;
总条数:12
a 1
a 3
a 5
a 10
b 1
b 3
b 5
b 10
c 1
c 3
c 5
c 10

多行变一行
-------------------------------------------------------------------

select value2,concat_ws('|',collect_set(value1))
from
(
select 'a' AS value1,10 AS value2
UNION ALL
select 'b' AS value1,10 AS value2
UNION ALL
select 'c' AS value1,20 AS value2
UNION ALL
select 'd' AS value1,20 AS value2
) table_1
group by value2;

OK
value2  _c1
10      a|b
20      c|d




unix_timestamp
———————————————————————————————————


select unix_timestamp('20180109070011','yyyyMMddHHmmss')-unix_timestamp('20180109065956','yyyyMMddHHmmss');



===============================
distinct

SELECT DISTINCT value1,value2
FROM
(
select 1 AS value1,1 AS value2
UNION ALL
select 1 AS value1,2 AS value2
UNION ALL
select 1 AS value1,3 AS value2
UNION ALL
select 2 AS value1,1 AS value2
UNION ALL
select 2 AS value1,1 AS value2
) table_1
OK
value1  value2
1       1
1       2
1       3
2       1

SELECT value1,DISTINCT value2
FROM
(
select 1 AS value1,1 AS value2
UNION ALL
select 1 AS value1,2 AS value2
UNION ALL
select 1 AS value1,3 AS value2
UNION ALL
select 2 AS value1,1 AS value2
UNION ALL
select 2 AS value1,1 AS value2
) table_1
语法错误

SELECT DISTINCT(value1), value2
FROM
(
select 1 AS value1,1 AS value2
UNION ALL
select 1 AS value1,2 AS value2
UNION ALL
select 1 AS value1,3 AS value2
UNION ALL
select 2 AS value1,1 AS value2
UNION ALL
select 2 AS value1,1 AS value2
) table_1
OK
value1  value2
1       1
1       2
1       3
2       1

SELECT value1, count(value2)
FROM
(
select 1 AS value1,1 AS value2
UNION ALL
select 1 AS value1,2 AS value2
UNION ALL
select 1 AS value1,3 AS value2
UNION ALL
select 2 AS value1,1 AS value2
UNION ALL
select 2 AS value1,1 AS value2
) table_1
GROUP BY value1;
OK
value1  _c1
1       3
2       2



SELECT value1, count(distinct value2)
FROM
(
select 1 AS value1,1 AS value2
UNION ALL
select 1 AS value1,2 AS value2
UNION ALL
select 1 AS value1,3 AS value2
UNION ALL
select 2 AS value1,1 AS value2
UNION ALL
select 2 AS value1,1 AS value2
) table_1
GROUP BY value1;
OK
value1  _c1
1       3
2       1


===============

删除hive分区
alter table kefu.dm_kefu_ivr_lost_rate drop PARTITION(year='2017');
alter table kefu.dm_kefu_ivr_lost_rate drop PARTITION(year='2018',month='01',day='01');








