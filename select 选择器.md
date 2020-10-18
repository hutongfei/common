**selector 选择器**



基本流程

1 创建socket 通道  ->   绑定端口 -> 设置非阻塞 -> 获取连接selector选择器 - >  通道注册到selector上

-> 获取连接的selectKey -> 遍历且查找可读，可写，可连接 -> 移除



SelectionKey（上一次就绪事件SelectionKey集合） : 代表这个Channel和它注册的Selector间的关系 

keys(所有SelectionKey集合)：该集合中保存了所有注册到当前选择器上的通道的SelectionKey对象；

cancelledKeys：该集合保存了已经被取消但其关联的通道还未被注销的SelectionKey对象集合，它始终是keys的子集。