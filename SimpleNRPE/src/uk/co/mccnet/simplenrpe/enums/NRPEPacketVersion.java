package uk.co.mccnet.simplenrpe.enums;

import org.apache.commons.lang.enums.ValuedEnum;

public class NRPEPacketVersion extends ValuedEnum {
	private static final long serialVersionUID = 6232533568092304947L;
	
	public static final NRPEPacketVersion VERSION_1  = new NRPEPacketVersion( "VERSION_1", 1);
	public static final NRPEPacketVersion VERSION_2  = new NRPEPacketVersion( "VERSION_2", 2);
	
	protected NRPEPacketVersion(String name, int value) {
		super(name, value);
	}

}
