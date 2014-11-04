package commands;

import interfaces.Command;
import output.DisplaySystem;

import java.util.Map;

class HelpCommand implements Command {
	private Map<String, Command> commands;
	
	public HelpCommand( Map<String, Command> commands) {
		this.commands = commands;
	}
	 
    @Override
    public boolean execute(String... args) {
        if (args == null) {
            DisplaySystem.getInstance().DisplayMessage("Available commands:\n" + LINE_DELIMITER);
            for (Command cmd : commands.values()) {
                DisplaySystem.getInstance().DisplayMessage(cmd.getName() + ": " + cmd.getDescription());
            }
            System.out.println(LINE_DELIMITER);
        } else {
            for (String cmd : args) {
                DisplaySystem.getInstance().DisplayMessage("Help for command " + cmd + ":\n" + LINE_DELIMITER);
                Command command = commands.get(cmd.toUpperCase());
                if (command == null) {
                    DisplaySystem.getInstance().DisplayMessage(COMMAND_NOT_FOUND);
                } else {
                    command.printHelp();
                }
                DisplaySystem.getInstance().DisplayMessage(LINE_DELIMITER);
            }
        }
        return true;
    }

    @Override
    public void printHelp() {
        DisplaySystem.getInstance().DisplayMessage(getDescription());
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
