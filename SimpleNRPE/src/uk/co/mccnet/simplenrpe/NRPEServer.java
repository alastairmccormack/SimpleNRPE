package uk.co.mccnet.simplenrpe;

import uk.co.mccnet.simplenrpe.handlers.NRPEEchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;

public class NRPEServer {
	
	int port = 5666;	
	boolean sslEnabled = true;
	LogLevel logLevel = LogLevel.ERROR;
	// Default action to echo responses
	//HandlerFactory handlerFactory = new HandlerFactory( NRPEEchoServerHandler.class) ;
	ChannelHandler mainServerHandler = new NRPEEchoServerHandler();

	public void run() throws Exception {
	    
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		ChannelInitializer<SocketChannel> channelInit = new NRPEServerInitializer(sslEnabled, mainServerHandler);
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					//.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(channelInit);

			ChannelFuture f = b.bind(port).sync();
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
	
	// Getters and Setters
	
	public static void main(String[] args) throws Exception {
		new NRPEServer().run();
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isSslEnabled() {
		return sslEnabled;
	}

	public void setSslEnabled(boolean sslEnabled) {
		this.sslEnabled = sslEnabled;
	}


	public ChannelHandler getMainServerHandler() {
		return mainServerHandler;
	}


	public void setMainServerHandler(ChannelHandler mainServerHandler) {
		this.mainServerHandler = mainServerHandler;
	}
	
}
