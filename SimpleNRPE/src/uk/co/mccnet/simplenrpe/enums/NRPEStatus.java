package uk.co.mccnet.simplenrpe.enums;

import org.apache.commons.lang.enums.ValuedEnum;

public class NRPEStatus extends ValuedEnum {
	private static final long serialVersionUID = -710976966995768977L;
	
	public static final NRPEStatus STATE_OK = new NRPEStatus( "STATE_OK", 0);
	public static final NRPEStatus STATE_WARNING = new NRPEStatus( "STATE_WARNING", 1);
	public static final NRPEStatus STATE_CRITICAL = new NRPEStatus( "STATE_CRITICAL", 2);
	public static final NRPEStatus STATE_UNKNOWN = new NRPEStatus( "STATE_UNKNOWN", 3);
	
	protected NRPEStatus(String name, int value) {
		super(name, value);
	}

}
