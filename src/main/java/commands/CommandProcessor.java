package commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/**
 * @author Ksiona
 * CommandProcessor
 */
public class CommandProcessor {
	 
    private Map<String, Command> commands;
 
    private String consoleEncoding;
 
    public CommandProcessor(String consoleEncoding) {
        commands = new TreeMap<>();
        Command cmd = new HelpCommand(commands);
        commands.put(cmd.getName(), cmd);
        cmd = new TrackCommand();
        commands.put(cmd.getName(), cmd);
        cmd = new GenreCommand();
        commands.put(cmd.getName(), cmd);
        cmd = new ExitCommand();
        commands.put(cmd.getName(), cmd);
        this.consoleEncoding = consoleEncoding;
    }
 
    public static void main(String[] args) {
    	System.setProperty("file.encoding","UTF-8"); //  track titles can be written in Cyrillic
    	System.setProperty("console.encoding","UTF-8");
        CommandProcessor cp = new CommandProcessor(System.getProperty("console.encoding"));
        cp.execute();
    }
    
    public void execute() {
        boolean result = true;
        Scanner scanner = new Scanner(System.in, consoleEncoding);
        do {
            System.out.print("> ");
            String fullCommand = scanner.nextLine();
            ParsedCommand parser = new ParsedCommand(fullCommand);
            if (parser.command == null || "".equals(parser.command)) {
                continue;
            }
            Command cmd = commands.get(parser.command.toUpperCase());
            if (cmd == null) {
                System.out.println("Command not found");
                continue;
            }
            result = cmd.execute(parser.args);
        } while (result);
    }
 
    class ParsedCommand {
        String command;
        String[] args;
 
        public ParsedCommand(String line) {
        	List<String> result = new ArrayList<>();
        	Pattern pattern = Pattern.compile("((\\\"[^\"]+\\\")|\\S+)");
    		Matcher matcher = pattern.matcher(line);
    		while (matcher.find())
    		   result.add(matcher.group().replaceAll("\"",""));
    	
            if (result != null) {
                command = result.get(0);
                if (result.size() > 1) {
                	result.remove(0);
                    args = result.toArray(new String[0]);                  
                }
            }
        }
    }
}