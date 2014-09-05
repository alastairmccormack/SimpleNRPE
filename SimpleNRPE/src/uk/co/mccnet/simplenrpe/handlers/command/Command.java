package uk.co.mccnet.simplenrpe.handlers.command;

public interface Command {
	
	public CommandResponse execute(CommandRequest commandRequest);

}
