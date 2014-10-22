package commands;

import java.util.Map;

class HelpCommand implements Command {
	private Map<String, Command> commands;
	
	public HelpCommand( Map<String, Command> commands) {
		this.commands = commands;
	}
	 
    @Override
    public boolean execute(String... args) {
        if (args == null) {
            System.out.println("Available commands:\n" + LINE_DELIMITER);
            for (Command cmd : commands.values()) {
                System.out.println(cmd.getName() + ": " + cmd.getDescription());
            }
            System.out.println(LINE_DELIMITER);
        } else {
            for (String cmd : args) {
                System.out.println("Help for command " + cmd + ":\n" + LINE_DELIMITER);
                Command command = commands.get(cmd.toUpperCase());
                if (command == null) {
                    System.out.println(COMMAND_NOT_FOUND);
                } else {
                    command.printHelp();
                }
                System.out.println(LINE_DELIMITER);
            }
        }
        return true;
    }

    @Override
    public void printHelp() {
        System.out.println(getDescription());
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
