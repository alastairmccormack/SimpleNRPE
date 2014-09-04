package uk.co.mccnet.simplenrpe.enums;

import org.apache.commons.lang.enums.ValuedEnum;

public class NRPEPacketType extends ValuedEnum {
	private static final long serialVersionUID = 8077940032817195300L;
	
	public static final NRPEPacketType TYPE_QUERY = new NRPEPacketType( "TYPE_QUERY", 1);
	public static final NRPEPacketType TYPE_RESPONSE = new NRPEPacketType( "TYPE_RESPONSE", 2);
	
	protected NRPEPacketType(String name, int value) {
		super(name, value);
	}

}
