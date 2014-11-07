package commands;

import interfaces.Command;
import management.ManagementSystem;
import output.DisplaySystem;

import java.util.Map;

class ExitCommand implements Command {
	
	private Map<String, Command> commands;
	private DisplaySystem ds;
	
    public ExitCommand() {
		this.ds = DisplaySystem.getInstance();
	}
	
    @Override
    public boolean execute(String... args) {
    	ds.DisplayMessage("Finishing command processor... done.");
        return false;
    }

    @Override
    public void printHelp() {
       ds.DisplayMessage(getDescription());
    }

    @Override
    public String getName() {
        return "EXIT";
    }

    @Override
    public String getDescription() {
        return "Exits from command processor";
    }
}