package uk.co.mccnet.simplenrpe;

import java.io.UnsupportedEncodingException;
import java.util.zip.CRC32;

import org.apache.commons.codec.binary.Hex;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import uk.co.mccnet.simplenrpe.enums.NRPEPacketType;
import uk.co.mccnet.simplenrpe.enums.NRPEPacketVersion;
import uk.co.mccnet.simplenrpe.enums.NRPEStatus;
import uk.co.mccnet.simplenrpe.exceptions.CRCValidationException;

public class NRPEPacket {
	
	public static final int PACKET_SIZE = 1036;
	public static final int MESSAGE_SIZE = 1026; 
	private NRPEPacketVersion version;
	private NRPEPacketType type;
	private NRPEStatus status;
	private String message;
	
	private static NRPEPacketVersion defaultPacketVersion = NRPEPacketVersion.VERSION_2;
	
	
	public NRPEPacketVersion getVersion() {
		return version;
	}

	public void setVersion(NRPEPacketVersion version) {
		this.version = version;
	}

	public NRPEPacketType getType() {
		return type;
	}

	public void setType(NRPEPacketType type) {
		this.type = type;
	}

	public NRPEStatus getStatus() {
		return status;
	}

	public void setStatus(NRPEStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public NRPEPacket(NRPEPacketVersion version, NRPEPacketType type, NRPEStatus status,
			String message) {
			this.version = version;
			this.type = type;
			this.status = status;
			this.message = message;
	}
	
	static public NRPEPacket getQueryPacket(NRPEStatus status, String message) {
		return new NRPEPacket(defaultPacketVersion , NRPEPacketType.TYPE_QUERY, 
				status, message);
	}
	
	static public NRPEPacket getResponsePacket(NRPEStatus status, String message) {
		return new NRPEPacket(defaultPacketVersion, NRPEPacketType.TYPE_RESPONSE, 
				status, message);
	}
	
	public void validate(int crc) throws CRCValidationException, UnsupportedEncodingException {
		ByteBuf packet = getPacket(0);
		int calcCrc = getCRC(packet);
		
		if (calcCrc != crc) throw new CRCValidationException();
	}
	
	private ByteBuf getPacket(int crc) throws UnsupportedEncodingException {
		// New response buffer, padded with 0x00 already
		ByteBuf packet = Unpooled.buffer(0, PACKET_SIZE);
		
		packet.writeShort(version.getValue());
		packet.writeShort(type.getValue());
		packet.writeInt(crc);
		packet.writeShort(status.getValue());
		
		byte messageBytes[] = message.getBytes("UTF8");
		byte destMessageBytes[] = java.util.Arrays.copyOf(messageBytes, MESSAGE_SIZE);
		packet.writeBytes(destMessageBytes);

		return packet;
	}
	
	public ByteBuf getPacket() throws UnsupportedEncodingException {
		ByteBuf dummyPacket = getPacket(0);
		int crc = getCRC(dummyPacket);
		ByteBuf packet = getPacket(crc);
		return packet;
	}
	
	public static int getCRC(ByteBuf packet) {	
		byte packetBytes[] = packet.array();
		CRC32 crc32 = new CRC32();
		crc32.update(packetBytes);
		return (int) crc32.getValue();
	}
	
}
