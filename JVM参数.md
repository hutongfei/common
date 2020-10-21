# JVM 

-Xms memory start 简称  最小堆容量

-Xmx memory max 内存最大值  最小堆容量

-XX:+HeapDumpOnOutOfMemoryError

-XX:HeapDumpPath 输出oom 信息

-XX:HeapDumpPath 输出路径，

-XX:OnOutOfMemoryError 发生oom后触发的脚本

-XX:+PrintGCDetails 打印gc 日志

-XX:+PrintGCTimeStamps 

-Xloggc:./gc.log gc文件的方式出输

-XX:SurvivorRatio=1

-XX:MaxDirectMemorySize=1G 堆外内存

-XX:+DisableExplicitGC

-XX:CMSInitiatingOccupancyFraction=60：老年代内存回收阈值，默认值为68。

-XX:ConcGCThreads=4：CMS垃圾回收器并行线程线，推荐值为CPU核心数。

-XX:ParallelGCThreads=8：新生代并行收集器的线程数。

-XX:MaxTenuringThreshold=10： 设置垃圾最大年龄。如果设置为0的话，则年轻代对象不经过Survivor区，直接进入年老代。对于年老代比较多的应用，可以提高效率。如果将此值设置为一个较大值，则年轻代对象会在Survivor区进行多次复制，这样可以增加对象再年轻代的存活时间，增加在年轻代即被回收的概论

-XX:CMSFullGCsBeforeCompaction=4：指定进行多少次fullGC之后，进行tenured区 内存空间压缩。

-XX:CMSMaxAbortablePrecleanTime=500：当abortable-preclean预清理阶段执行达到这个时间时就会结束。
