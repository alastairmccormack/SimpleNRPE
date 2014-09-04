package uk.co.mccnet.simplenrpe;

import org.apache.commons.codec.binary.Hex;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NRPEResponseEncoder extends MessageToByteEncoder<NRPEPacket> {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, NRPEPacket nrpePacket,
			ByteBuf out) throws Exception {		
		
		byte packetBytes[] = nrpePacket.getPacket().array();
		out.writeBytes( packetBytes );
	}

}
