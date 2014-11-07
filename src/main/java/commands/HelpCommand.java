package commands;

import interfaces.Command;

import java.util.Map;

import output.DisplaySystem;

class HelpCommand implements Command {
	
	private static final String COMMAND_DESCRIPTION = "Prints list of available commands";
	private static final String COMMAND_NAME = "HELP";
	private static final String NEW_LINE = "\n";
	private static final String COLON = ": ";
	private static final String INFO_MESSAGE_HELP = "Help for command ";
	private static final String INFO_MESSAGE_AVAILABLE = "Available commands:\n";
	private Map<String, Command> commands;
	private DisplaySystem ds;
	
	public HelpCommand( Map<String, Command> commands) {
		this.commands = commands;
		this.ds = DisplaySystem.getInstance();
	}
	 
    @Override
    public boolean execute(String... args) {
        if (args == null) {
        	ds.DisplayMessage(INFO_MESSAGE_AVAILABLE + LINE_DELIMITER);
            for (Command cmd : commands.values()) {
            	ds.DisplayMessage(cmd.getName() + COLON + cmd.getDescription());
            }
            ds.DisplayMessage(LINE_DELIMITER);
        } else {
            for (String cmd : args) {
            	ds.DisplayMessage(INFO_MESSAGE_HELP + cmd + COLON + NEW_LINE + LINE_DELIMITER);
                Command command = commands.get(cmd.toUpperCase());
                if (command == null) {
                	ds.DisplayMessage(COMMAND_NOT_FOUND);
                } else {
                    command.printHelp();
                }
                ds.DisplayMessage(LINE_DELIMITER);
            }
        }
        return true;
    }

    @Override
    public void printHelp() {
    	ds.DisplayMessage(getDescription());
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }
}
