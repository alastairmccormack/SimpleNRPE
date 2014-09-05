package uk.co.mccnet.simplenrpe.enums;

import org.apache.commons.lang.enums.ValuedEnum;

public class NRPEStatus extends ValuedEnum {
	private static final long serialVersionUID = -710976966995768977L;
	
	public static final NRPEStatus OK = new NRPEStatus( "OK", 0);
	public static final NRPEStatus WARNING = new NRPEStatus( "WARNING", 1);
	public static final NRPEStatus CRITICAL = new NRPEStatus( "CRITICAL", 2);
	public static final NRPEStatus UNKNOWN = new NRPEStatus( "UNKNOWN", 3);
	
	protected NRPEStatus(String name, int value) {
		super(name, value);
	}

}
