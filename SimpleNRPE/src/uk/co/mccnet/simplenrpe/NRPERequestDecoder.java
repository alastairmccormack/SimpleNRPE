package uk.co.mccnet.simplenrpe;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.CRC32;

import org.apache.commons.lang.enums.EnumUtils;

import uk.co.mccnet.simplenrpe.enums.NRPEPacketType;
import uk.co.mccnet.simplenrpe.enums.NRPEPacketVersion;
import uk.co.mccnet.simplenrpe.enums.NRPEStatus;
import uk.co.mccnet.simplenrpe.exceptions.CRCValidationException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class NRPERequestDecoder extends ReplayingDecoder<Void> {
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
		// Fragmentation is handled by ReplayingDecoder
		ByteBuf packet = buf.readBytes(NRPEPacket.PACKET_SIZE);
		
		validate(packet);
		
		// Will only reach hear when NRPEPacket.PACKET_SIZE is reached
		short packetVersionShort = packet.readShort();
		short packetTypeShort = packet.readShort();
		// Skip CRC
		packet.readInt();
		short statusShort = packet.readShort();
		ByteBuf rawMessageByteBuf = packet.readBytes(NRPEPacket.MESSAGE_SIZE);
		
		// Extracts UTF-8 string from message, which is 0x0 terminated 
		int messageIndex = rawMessageByteBuf.bytesBefore( (byte) 0x0 );
		ByteBuf messageBytes = rawMessageByteBuf.readBytes(messageIndex);
		String message = messageBytes.toString(Charset.forName("UTF8"));
		
		NRPEStatus messageStatus = (NRPEStatus) 
				EnumUtils.getEnum(NRPEStatus.class, statusShort);
		
		if (messageStatus == null) {
			messageStatus = NRPEStatus.UNKNOWN;
		}
		
		NRPEPacketVersion packetVersion = (NRPEPacketVersion) 
				EnumUtils.getEnum(NRPEPacketVersion.class, packetVersionShort);
				
		NRPEPacketType packetType = (NRPEPacketType)
				EnumUtils.getEnum(NRPEPacketType.class, packetTypeShort);
		
		NRPEPacket nrpePacket = new NRPEPacket(packetVersion, packetType, 
				messageStatus, message);
		
		//nrpePacket.validate(crc);
		
		out.add(nrpePacket);
	}

	/**
	 * Allows for validating a packet from the wire, which may contain
	 * unsupported/unset fields
	 * 
	 * @param packet
	 * @param crc
	 * @throws CRCValidationException
	 * @throws UnsupportedEncodingException
	 */
	public void validate(ByteBuf packet) throws CRCValidationException, UnsupportedEncodingException {
		// Read CRC at index 4
		packet.resetReaderIndex();
		packet.skipBytes(4);
		int crc = packet.readInt();
		
		// Replace existing CRC with 0
		packet.resetReaderIndex();		
		packet.setInt(4, 0);
		
		int calcCrc = getCRC(packet);
		if (calcCrc != crc) throw new CRCValidationException();
	}
	
	private int getCRC(ByteBuf packet) {	
		byte packetBytes[] = packet.array();
		CRC32 crc32 = new CRC32();
		crc32.update(packetBytes);
		return (int) crc32.getValue();
	}
}
