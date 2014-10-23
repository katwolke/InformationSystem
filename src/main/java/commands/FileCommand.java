package commands;

import management.ManagementSystem;

/**
 * Created by Morthanion on 23.10.2014.
 */
public class FileCommand implements Command {
    ManagementSystem ms = new ManagementSystem();

    @Override
    public boolean execute(String... args) {
        if (args == null) {
            System.out.println("You must specify the parameter. Type \"help file\" to view available");
        } else {
            String command = args[0];
            String fileName;
            switch(command){
                case "-t":
                    fileName = args[1];
                    ms.addTracks(fileName);
                    break;
                case "-g":
                    fileName = args[1];
                    ms.addGenres(fileName);
                    break;
                default:
                    System.out.println("Parameter <" + command + "> didn't defined");
            }
        }
        return true;
    }

    @Override
    public void printHelp() {
        System.out.println(
                "-t [file name]	add tracks from file \r" +
                "-g [file name] add genres from file"
        );
    }

    @Override
    public String getName() {
        return "FILE";
    }

    @Override
    public String getDescription() {
        return "Defines operations with external files";
    }
}
