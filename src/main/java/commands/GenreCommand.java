package commands;

import interfaces.Command;
import management.ManagementSystem;
import output.DisplaySystem;

public class GenreCommand implements Command {

	ManagementSystem ms;
	
	@Override
	public boolean execute(String... args) {
		ms = ManagementSystem.getInstance();
		 if (args == null) {
    		 System.out.println("You must specify the parameter. Type \"help genre\" to view available" );
		 } else {
        	 String command = args[0];
        	 String genreName;
        	 switch(command){
        	 case "-a":
        		 genreName = args[1];
                 DisplaySystem.getInstance().DisplayMessage("The option \"add genre into list\" is not ready yet");
                 break;
        	 case "-r":
        		 genreName = args[1];
                 DisplaySystem.getInstance().DisplayMessage("The option \"remove genre from list\" is not ready yet");
                 break;
        	 case "-g":
        		 genreName = args[1];
                 DisplaySystem.getInstance().DisplayMessage("The option \"get all tracks this genre\" is not ready yet");
                 break;
        	 case "-p":
        		 if((args.length -1 == 0)){
                     DisplaySystem.getInstance().DisplayMessage("The option \"print genre list\" is not ready yet");
        		 }else
        			 ms.getTracksTitles(args[1]);
                 break;
           	 default:
                 DisplaySystem.getInstance().DisplayMessage("Parameter <" + command + "> didn't defined");
        	 }
		 }
		return true;
	}

	@Override
	public void printHelp() {
        DisplaySystem.getInstance().DisplayMessage(
				"-a [new genre name]	add genre into list \r\n" +
				"-r [genre name]		remove genre from list \r\n" +
				"-p [genre name]		print all tracks of this genre \r\n" +
				"-p			print genre list");
		
	}

	@Override
	public String getName() {
		return "GENRE";
	}

	@Override
	public String getDescription() {
		return "Defines operations with genre list";
	}

}
