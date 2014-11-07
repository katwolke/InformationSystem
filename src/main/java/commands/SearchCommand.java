package commands;
import interfaces.Command;
import management.ManagementSystem;
import output.DisplaySystem;

/**
 * Created by Morthanion on 06.11.2014.
 */
public class SearchCommand implements Command{
	
	private static final String PRINT_HELP = "search is case-insensitive \r\n" +
            "-t <search mask>			search by title \r\n" +
            "-a <search mask>         search by album name \r\n" +
            "-g <search mask>			search by genre \r\n" +
            "-s <search mask>			search by singer \r\n" +
            "-l <search mask>			search by length \r\n" +
            "search mask example \" *m?3 \"\r\n"+
            "where \"*\" - any or none symbols\r\n"+
            "      \"?\" - any or none single symbol\r\n";
	private static final String COMMAND_DESCRIPTION = "Defines operations with genre list";
	private static final String COMMAND_NAME = "SEARCH";
	private static final String WARNING_NO_COMMAND_PARAMETER = "You must specify the parameter. Type \"help search\" to view available";
	static final String WARNING_SUBCOMMAND = "Enter <search mask> to process";
	private static final String KEY_FIELD_TITLE = "Title";
	private static final String KEY_FIELD_ALBUM = "Album";
	private static final String KEY_FIELD_GENRE = "Genre";
	private static final String KEY_FIELD_SINGER = "Singer";
	private static final String KEY_FIELD_LENGTH = "Length";

	
    private DisplaySystem ds;
    private  ManagementSystem ms;
    public SearchCommand()
    {
        this.ds = DisplaySystem.getInstance();
        this.ms = ManagementSystem.getInstance();
    }
    @Override
    public boolean execute(String... args) {
        if (args == null)
            ds.DisplayMessage(WARNING_NO_COMMAND_PARAMETER);
        if((args.length -1 == 0))
   			 ds.DisplayMessage(WARNING_SUBCOMMAND);
        else {
        	String command = args[0];
            switch(command){
                case "-t":
                    ms.searchItems(KEY_FIELD_TITLE, args[1]);
                    break;
                case "-a":
                    ms.searchItems(KEY_FIELD_ALBUM, args[1]);
                    break;
                case "-g":
                    ms.searchItems(KEY_FIELD_GENRE, args[1]);
                    break;
                case "-s":
                    ms.searchItems(KEY_FIELD_SINGER, args[1]);
                    break;
                case "-l":
                    ms.searchItems(KEY_FIELD_LENGTH, args[1]);
                    break;
                default:
                    ds.DisplayMessage(COMMAND_NOT_FOUND);
            }
        }
        return true;
    }

    @Override
    public void printHelp() {
    	ds.DisplayMessage(PRINT_HELP);
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
