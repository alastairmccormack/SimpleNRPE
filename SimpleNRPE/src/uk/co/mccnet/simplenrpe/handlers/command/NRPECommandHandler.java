package uk.co.mccnet.simplenrpe.handlers.command;

import uk.co.mccnet.simplenrpe.NRPEPacket;
import uk.co.mccnet.simplenrpe.enums.NRPEStatus;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * ServerHandler which handles NRPE command structure.
 * Converts between NRPEPackets and CommandReq/Response objects 
 *
 */
@Sharable
public class NRPECommandHandler extends SimpleChannelInboundHandler<NRPEPacket> {
	private CommandFactory commandFactory;
	
	public NRPECommandHandler(CommandFactory commandFactory) {
		this.commandFactory = commandFactory;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NRPEPacket nrpePacket)
			throws Exception {
		
		CommandRequest commandRequest = parseRequestMessage(nrpePacket.getMessage());
		
		CommandResponse commandResponse = commandFactory.execute(commandRequest);
		
		NRPEPacket nrpePacketResponse = commandResponseToNRPEPacket(commandResponse);
		
		ctx.writeAndFlush(nrpePacketResponse);
	}
	
	protected CommandRequest parseRequestMessage(String message) {
		String messageSplit[] = message.split("!", 2);
		String commandName = messageSplit[0];
		String args = (messageSplit.length == 2) ? messageSplit[1] : null;
		return new CommandRequest(commandName, args);
	}
	
	protected NRPEPacket commandResponseToNRPEPacket(CommandResponse commandResponse) {
		String message = commandResponse.getFullMessage();
		NRPEStatus status = commandResponse.getStatus();
		
		NRPEPacket nrpePacket = NRPEPacket.getResponsePacket(status, message);
		return nrpePacket;
	}
    
	 @Override
	 public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		 cause.printStackTrace();
	     ctx.close();
	 }
}
