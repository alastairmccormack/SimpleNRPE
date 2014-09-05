package uk.co.mccnet.simplenrpe;

import javax.net.ssl.SSLEngine;

import uk.co.mccnet.simplenrpe.handlers.NRPEEchoServerHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;

public class NRPEServerInitializer extends ChannelInitializer<SocketChannel> {
	private boolean sslEnabled = false;
	private ChannelHandler mainServerHandler;
	
	public NRPEServerInitializer(boolean sslEnabled, ChannelHandler mainServerHandler) {
		this.sslEnabled = sslEnabled;
		this.mainServerHandler = mainServerHandler;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		if (sslEnabled) {
			SelfSignedCertificate ssc = new SelfSignedCertificate();
		    SslContext sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
		    
			SslHandler sslCh = sslCtx.newHandler(ch.alloc() );
			SSLEngine sslEngine = sslCh.engine();
			sslEngine.setEnabledCipherSuites(sslEngine.getSupportedCipherSuites());
			pipeline.addLast(sslCh);
		}
		
		pipeline.addLast(new NRPERequestDecoder() );
		pipeline.addLast(new NRPEResponseEncoder() );

		// Where the interesting stuff happens
		pipeline.addLast( mainServerHandler );
	}
	
	

}
