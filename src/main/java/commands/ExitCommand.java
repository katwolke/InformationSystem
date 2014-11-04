package commands;

import interfaces.Command;
import output.DisplaySystem;

import java.util.Map;

class ExitCommand implements Command {
	
	private Map<String, Command> commands;
	
    @Override
    public boolean execute(String... args) {
        System.out.println("Finishing command processor... done.");
        return false;
    }

    @Override
    public void printHelp() {
        DisplaySystem.getInstance().DisplayMessage(getDescription());
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