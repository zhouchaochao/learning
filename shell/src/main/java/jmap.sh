
jmap -dump:live,format=b,file=/home/odin/hello/dump.manager.pre.20190718.11.58.hd 636755

1.使用jmap生成堆快照，可以下载到本地分析
live 选项找到内存泄漏

mat 各个tab页
shallow heap 与
G1 CMS对比

GC参数说明

shallow heap：
	对象本身的大小，如果是数组或集合则是各个元素的总大小。

retained heap：
	对象本身的大小 + 引用的其他对象的大小。

年轻代晋升老年代的最大年龄阈值

Card Table是一种points-out（我引用了谁的对象）的结构，见图2，每个Card 覆盖一定范围的Heap（一般为512Bytes）。传统的年轻代垃圾回收器在每次GC时，会扫描整个Card Table来找出老年代引用年轻代的对象，避免把年轻代中被老年代引用的对象回收。在CMS垃圾收集器中也使用到了该数据结构，扫描年轻代对象引用老年代对象的情况。

RSet全称是Remembered Set，是辅助GC过程的一种结构，典型的空间换时间工具，和Card Table有些类似。RSet主要的作用是为了记录代际之间引用关系的一种数据结构。在G1中每个Region都有一个RSet，RSet记录了其他Region中的对象引用本Region中对象的关系，属于points-into结构（谁引用了我的对象）。RSet其实是一个Hash Table，Key是别的Region的起始地址，Value是一个集合，里面的元素是Card Table的Index。
