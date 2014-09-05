package uk.co.mccnet.simplenrpe.handlers.command;

import uk.co.mccnet.simplenrpe.enums.NRPEStatus;

/**
 * @author McCorA01
 *
 */
public class CommandResponse {
	private NRPEStatus status;
	private String summaryMessage = null;
	private String performanceData = null;
	private String longMessage = null;

	public CommandResponse(NRPEStatus status, String summaryMessage) {
		this.status = status;
		this.summaryMessage = summaryMessage;
	}
	
	/**
	 * Returns a Nagios V3 message with summary, performance and long message
	 */
	public String getFullMessage() {
		StringBuilder fullMessageSb = new StringBuilder(summaryMessage);
		
		if (longMessage != null) {
			fullMessageSb.append("|\n")
				.append(longMessage);
		}
		
		if (performanceData != null) {
			fullMessageSb.append("|\n")
				.append(performanceData);
		}
		
		return fullMessageSb.toString();
	}


	public NRPEStatus getStatus() {
		return status;
	}


	public String getPerformanceData() {
		return performanceData;
	}


	public void setPerformanceData(String performanceData) {
		this.performanceData = performanceData;
	}


	public String getLongMessage() {
		return longMessage;
	}


	public void setLongMessage(String longMessage) {
		this.longMessage = longMessage;
	}

}
