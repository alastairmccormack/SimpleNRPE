package uk.co.mccnet.simplenrpe;

import uk.co.mccnet.simplenrpe.enums.NRPEStatus;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NRPEServerHandler extends SimpleChannelInboundHandler<NRPEPacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NRPEPacket nrpePacket)
			throws Exception {
		
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
