package commands;

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
        System.out.println(getDescription());
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