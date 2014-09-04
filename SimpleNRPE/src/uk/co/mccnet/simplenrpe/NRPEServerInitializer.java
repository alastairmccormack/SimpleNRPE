package uk.co.mccnet.simplenrpe;

import javax.net.ssl.SSLEngine;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;

public class NRPEServerInitializer extends ChannelInitializer<SocketChannel> {
	private boolean sslEnabled = false;
	
	public NRPEServerInitializer(boolean sslEnabled) {
		this.sslEnabled = sslEnabled;
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
		pipeline.addLast(new NRPEServerHandler() );
	}
	
	

}
