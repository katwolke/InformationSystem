package commands;

import interfaces.Command;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import output.DisplaySystem;
 
/**
 * @author Ksiona
 * CommandProcessor
 */
public class CommandProcessor {
	 
	static final String INVITATION_TO_PRINT = "> ";

    private Map<String, Command> commands;
	private static DisplaySystem ds;
    private String consoleEncoding;
 
    public CommandProcessor(DisplaySystem ds, String consoleEncoding) {
        commands = new TreeMap<>();
        putCommandIntoMap(new HelpCommand(commands), commands);
        putCommandIntoMap(new TrackCommand(), commands);
        putCommandIntoMap(new GenreCommand(), commands);
        putCommandIntoMap(new SearchCommand(), commands);
        putCommandIntoMap(new ExitCommand(), commands);
        this.consoleEncoding = consoleEncoding;
        this.ds = DisplaySystem.getInstance();
    }
 
    private void putCommandIntoMap(Command c, Map<String, Command> map){
    	map.put(c.getName(), c);
    }
    
    public void execute() {
        boolean result = true;
        Scanner scanner = new Scanner(System.in, consoleEncoding);
        do {
        	ds.DisplaySymbols(INVITATION_TO_PRINT);
        	String fullCommand = "";
        	String line;
        	do  {
        		line = scanner.nextLine();
        		 fullCommand += line;
        	 }while (!line.contains("/"));
            if (fullCommand == null || "".equals(fullCommand)) {
                continue;
            }
            CommandParser parser = new CommandParser(fullCommand);
            if (parser.command == null || "".equals(parser.command)) {
                continue;
            }
            Command cmd = commands.get(parser.command.toUpperCase());
            if (cmd == null) {
            	ds.DisplayMessage(Command.COMMAND_NOT_FOUND);
                continue;
            }
            result = cmd.execute(parser.args);
        } while (result);
    }
}