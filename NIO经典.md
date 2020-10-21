```java
    @Test
    public void client() throws Exception {
        /**  获取Channel通道 **/
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
        /** 设置为非阻塞型 **/
        sChannel.configureBlocking(false);
        /** 分配指定大小缓冲区 **/
        ByteBuffer dst = ByteBuffer.allocate(1024);
        /** 加入数据    **/
        dst.put((new Date().toString()).getBytes());
        dst.flip();
        /** 读取并写入 **/
        sChannel.write(dst);
        /**   关闭通道  **/
        sChannel.close();
    }

	
```



```java
    @Test
    public void client() throws Exception {
        /**  获取Channel通道 **/
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
        /** 设置为非阻塞型 **/
        sChannel.configureBlocking(false);
        /** 分配指定大小缓冲区 **/
        ByteBuffer dst = ByteBuffer.allocate(1024);
        /** 加入数据    **/
        dst.put((new Date().toString()).getBytes());
        dst.flip();
        /** 读取并写入 **/
        sChannel.write(dst);
        /**   关闭通道  **/
        sChannel.close();
    }

```

