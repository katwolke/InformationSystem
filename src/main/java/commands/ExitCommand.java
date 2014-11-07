package commands;

import interfaces.Command;
import management.ManagementSystem;
import output.DisplaySystem;

import java.util.Map;

class ExitCommand implements Command {
	
	private static final String COMMAND_DESCRIPTION = "Exits from command processor";
	private static final String COMMAND_NAME = "EXIT";
	private static final String EXIT_MESSAGE = "Finishing command processor... done.";
	private Map<String, Command> commands;
	private DisplaySystem ds;
	
    public ExitCommand() {
		this.ds = DisplaySystem.getInstance();
	}
	
    @Override
    public boolean execute(String... args) {
    	ds.DisplayMessage(EXIT_MESSAGE);
        return false;
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