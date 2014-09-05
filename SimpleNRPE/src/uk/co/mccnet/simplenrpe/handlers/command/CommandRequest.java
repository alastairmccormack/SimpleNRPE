package uk.co.mccnet.simplenrpe.handlers.command;

public class CommandRequest {
	private String commandName;
	private String arguments;
	
	public CommandRequest(String commandName, String arguments) {
		this.commandName = commandName;
		this.arguments = arguments;
	}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public String getArguments() {
		return arguments;
	}

	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

}
