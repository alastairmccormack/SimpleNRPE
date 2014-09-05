package uk.co.mccnet.simplenrpe.handlers;

import uk.co.mccnet.simplenrpe.NRPEPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * /**
 * Basic ServerHandler which just echo packets 
 *
 */
@Sharable
public class NRPEEchoServerHandler extends SimpleChannelInboundHandler<NRPEPacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NRPEPacket nrpePacket)
			throws Exception {
		// Generate a response packet with the original status and message
		NRPEPacket nrpePacketResponse = 
				NRPEPacket.getResponsePacket(nrpePacket.getStatus(), nrpePacket.getMessage());
		
		ctx.writeAndFlush(nrpePacketResponse);
	}
    
	 @Override
	 public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		 cause.printStackTrace();
	     ctx.close();
	 }
}
