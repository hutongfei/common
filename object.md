lock: 锁状态标记位，该标记的值不同，整个mark word表示的含义不同。

biased_lock：偏向锁标记，为1时表示对象启用偏向锁，为0时表示对象没有偏向锁。

age：Java GC标记位对象年龄。

identity_hashcode：对象标识Hash码，采用延迟加载技术。当对象使用HashCode()计算后，并会将结果写到该对象头中。当对象被锁定时，该值会移动到线程Monitor中。

thread：持有偏向锁的线程ID和其他信息。这个线程ID并不是JVM分配的线程ID号，和Java Thread中的ID是两个概念。

epoch：偏向时间戳。

ptr_to_lock_record：指向栈中锁记录的指针。

ptr_to_heavyweight_monitor：指向线程Monitor的指针。

 

