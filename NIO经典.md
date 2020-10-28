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
		public class Demo13 {

	    public static void main(String[] args) throws Exception {
		nioMethod();
	    }


	    private static void nioMethod() throws IOException {
		ServerSocketChannel socketChannel = ServerSocketChannel.open();
		socketChannel.bind(new InetSocketAddress(9000));
		socketChannel.configureBlocking(false);
		Selector selector = Selector.open();
		socketChannel.register(selector, SelectionKey.OP_ACCEPT);

		while (selector.select() > 0) {
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
			    SelectionKey next = iterator.next();
			    if (next.isAcceptable()) {
				SocketChannel accept = socketChannel.accept();
				accept.configureBlocking(false);
				accept.register(selector, SelectionKey.OP_READ);
			    } else if (next.isReadable()) {
				SocketChannel channel = (SocketChannel) next.channel();
				channel.configureBlocking(false);
				handleChannel(channel);
			    }
			    iterator.remove();
			}

		}
	    }

	    public static void handleChannel(SocketChannel channel) throws IOException {
		ByteBuffer dst = ByteBuffer.allocate(1024);
		int i = 0;
		while ((i = channel.read(dst)) > 0) {
		    dst.flip();
		    while (dst.hasRemaining()) {
			System.out.print((char) dst.get());
		    }
		    dst.clear();
		}
		System.out.println("is result ");
	    }


	    private static void BioMethod() throws IOException {
		ServerSocket serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress("localhost", 9000));

		while (true) {
		    System.out.println("wait for accept ");
		    Socket accept = serverSocket.accept();
		    System.out.println("accept connection ");
		    InputStream is = accept.getInputStream();
		    byte[] bytes = new byte[1024];
		    is.read(bytes);
		    System.out.println(new String(bytes));
		}
	    }
	}
