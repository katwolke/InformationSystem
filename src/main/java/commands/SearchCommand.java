package commands;
import interfaces.Command;
import management.ManagementSystem;
import output.DisplaySystem;

/**
 * Created by Morthanion on 06.11.2014.
 */
public class SearchCommand implements Command{
    DisplaySystem ds;
    ManagementSystem ms;
    public SearchCommand()
    {
        this.ds = DisplaySystem.getInstance();
        this.ms = ManagementSystem.getInstance();
    }
    @Override
    public boolean execute(String... args) {
        if (args == null) {
            ds.DisplayMessage("You must specify the parameter. Type \"help search\" to view available" );
        } else {
            String command = args[0];
            switch(command){
                case "-t":
                    ms.searchItems("Title", args[1]);
                    break;
                case "-a":
                    ms.searchItems("Album", args[1]);
                    break;
                case "-g":
                    ms.searchItems("Genre", args[1]);
                    break;
                case "-s":
                    ms.searchItems("Singer", args[1]);
                    break;
                case "-l":
                    ms.searchItems("Length", args[1]);
                    break;
                default:
                    ds.DisplayMessage("Parameter <" + command + "> didn't defined");
            }
        }
        return true;
    }

    @Override
    public void printHelp() {
        DisplaySystem.getInstance().DisplayMessage(
                "search is case-insensitive" +
                        "-t \"search mask\"			search by title \r\n" +
                        "-a \"search mask\"         search by album name \r\n" +
                        "-g \"search mask\"			search by genre \r\n" +
                        "-s \"search mask\"			search by singer \r\n" +
                        "-l \"search mask\"			search by length \r\n" +
                        "search mask example \" *m?3 \"\r\n"+
                        "where \"*\" - any or none symbols\r\n"+
                        "      \"?\" - any or none single symbol\r\n");
    }

    @Override
    public String getName() {
        return "SEARCH";
    }

    @Override
    public String getDescription() {
        return "Defines operations with genre list";
    }
}
