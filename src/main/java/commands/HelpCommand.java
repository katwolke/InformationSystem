package commands;

import interfaces.Command;
import java.util.Map;

import output.DisplaySystem;

class HelpCommand implements Command {
	private Map<String, Command> commands;
	private DisplaySystem ds;
	
	public HelpCommand( Map<String, Command> commands) {
		this.commands = commands;
		this.ds = DisplaySystem.getInstance();
	}
	 
    @Override
    public boolean execute(String... args) {
        if (args == null) {
        	ds.DisplayMessage("Available commands:\n" + LINE_DELIMITER);
            for (Command cmd : commands.values()) {
            	ds.DisplayMessage(cmd.getName() + ": " + cmd.getDescription());
            }
            ds.DisplayMessage(LINE_DELIMITER);
        } else {
            for (String cmd : args) {
            	ds.DisplayMessage("Help for command " + cmd + ":\n" + LINE_DELIMITER);
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
        return "HELP";
    }

    @Override
    public String getDescription() {
        return "Prints list of available commands";
    }
}
