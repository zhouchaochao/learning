groovy


使用GroovyShell的parse方法导致perm区爆满的问题

如果应用中内嵌Groovy引擎，会动态执行传入的表达式并返回执行结果，而Groovy每执行一次脚本，都会生成一个脚本对应的class对象，并new一个InnerLoader去加载这个对象，而InnerLoader和脚本对象都无法在gc的时候被回收运行一段时间后将perm占满，一直触发fullgc。

为什么Groovy每执行一次脚本，都会生成一个脚本对应的class对象？
一个ClassLoader对于同一个名字的类只能加载一次，都由GroovyClassLoader加载，那么当一个脚本里定义了C这个类之后，另外一个脚本再定义一个C类的话，GroovyClassLoader就无法加载了。

为什么这里会每次执行都会加载？
这是因为对于同一个groovy脚本，groovy执行引擎都会不同的命名，且命名与时间戳有关系。当传入text时，class对象的命名规则为："script" + System.currentTimeMillis() + Math.abs(text.hashCode()) + ".groovy"。这就导致就算groovy脚本未发生任何变化，每次执行parse方法都会新生成一个脚本对应的class对象，且由GroovyClassLoader进行加载，不断增大perm区。

为什么InnerLoader加载的对应无法通过gc清理掉？
大家都知道，JVM中的Class只有满足以下三个条件，才能被GC回收，也就是该Class被卸载：1. 该类所有的实例都已经被GC，也就是JVM中不存在该Class的任何实例；2. 加载该类的ClassLoader已经被GC；3. 该类的java.lang.Class对象没有在任何地方被引用，如不能在任何地方通过反射访问该类的方法。

在GroovyClassLoader代码中有一个class对象的缓存，进一步跟下去，发现每次编译脚本时都会在Map中缓存这个对象，即：setClassCacheEntry(clazz)。每次groovy编译脚本后，都会缓存该脚本的Class对象，下次编译该脚本时，会优先从缓存中读取，这样节省掉编译的时间。这个缓存的Map由GroovyClassLoader持有，key是脚本的类名，这就导致每个脚本对应的class对象都存在引用，无法被gc清理掉。

如何解决？
请参考：Groovy引发的PermGen区爆满问题定位与解决。http://xiongzheng.me/question/2015/01/02/groovy-permgen-out/

参考：https://blog.csdn.net/zhongguomao/article/details/72817564


Java调用Groovy的方法总结 https://blog.csdn.net/eric_sunah/article/details/11541307


Groovy动态加载类踩中的那些坑.. https://my.oschina.net/chenxiaojie/blog/835934

groovy脚本导致的FullGC问题 https://www.cnblogs.com/fourspirit/p/4332154.html

在Java里整合Groovy脚本的一个陷阱 http://rednaxelafx.iteye.com/blog/620155




