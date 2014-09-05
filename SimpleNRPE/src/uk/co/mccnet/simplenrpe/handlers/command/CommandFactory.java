package uk.co.mccnet.simplenrpe.handlers.command;

import java.util.HashMap;

public class CommandFactory {
	
	HashMap<String, Command> commands = new HashMap<String, Command>();
	
	public void addCommand(String name, Command command) {
		commands.put(name, command);
	}
	
	public Command getCommand(String name) {
		return commands.get(name);
	}

	CommandResponse execute(CommandRequest commandRequest) {
		String commandName = commandRequest.getCommandName();
		return getCommand(commandName).execute(commandRequest);
	}
}
