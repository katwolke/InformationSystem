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
public class CommandProcessor { //am i right that CommandProcessor should be also Singleton? Yes i'm please implement Singlton here  
	 
	static final String INVITATION_TO_PRINT = "> ";

    private Map<String, Command> commands;
	private static DisplaySystem ds;
    private String consoleEncoding;
 
    public CommandProcessor(DisplaySystem ds, String consoleEncoding) {
        commands = new TreeMap<>();
        putCommandIntoMap(new HelpCommand(commands), commands); // I like commands, but i don't like that they have not generic parent, that could for example perform resources initialisation
        putCommandIntoMap(new TrackCommand(), commands); //what? why commands know about each other? no no no, remove association dependencies between commands, only parent/child are allowed
        putCommandIntoMap(new GenreCommand(), commands);
        putCommandIntoMap(new SearchCommand(), commands);
        putCommandIntoMap(new ExitCommand(), commands);
        this.consoleEncoding = consoleEncoding;
        this.ds = DisplaySystem.getInstance();
    }
 
    private void putCommandIntoMap(Command c, Map<String, Command> map){
    	map.put(c.getName(), c);
    }
    
    public void execute() { //Yes, it's clear execute command could throw anything and this could be not caught, please provide throws or try/catch 
        boolean result = true;
        Scanner scanner = new Scanner(System.in, consoleEncoding);// why i see resource that is retrieved here in the middle of code? do we have any lasy initialisation no. perform initialisation of resources in constuctors
        do {
        	ds.DisplaySymbols(INVITATION_TO_PRINT);
            String fullCommand = scanner.nextLine();
            if (fullCommand == null || "".equals(fullCommand)) {
                continue;
            }
            CommandParser parser = new CommandParser(fullCommand);// why i see resource that is retrieved here in the middle of code? do we have any lasy initialisation no. perform initialisation of resources in constuctors
            if (parser.command == null || "".equals(parser.command)) {
                continue;
            }
            Command cmd = commands.get(parser.command.toUpperCase());
            if (cmd == null) {
            	ds.DisplayMessage(Command.COMMAND_NOT_FOUND);
                continue;
            }
            result = cmd.execute(parser.args); // why cmds doesn't provide any throws signature? I also don't see in cmds handling of exceprional situations
        } while (result);
    }
}
