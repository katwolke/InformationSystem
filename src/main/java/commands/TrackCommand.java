package commands;

import management.ManagementSystem;

class TrackCommand implements Command {
	
	ManagementSystem ms = new ManagementSystem();
	
    @Override
    public boolean execute(String... args) {
    	 if (args == null) {
    		 System.out.println("You must specify the parameter. Type \"help track\" to view available" );
         }// else if((args.length -1 == 0))
        	//System.out.println("You must specify the object to process" );
         else {
        	 String command = args[0];
        	 String trackName;
        	 switch(command){
        	 case "-i":
        		 trackName = args[1];
                 ms.printTrackInfo(trackName); 
                 break;
        	 case "-s":
        		// trackName = args[1];
        		 System.out.println("The option is not ready yet"); 
                 break;
        	 case "-a":
        		// trackName = args[1];
        		 System.out.println("The option is not ready yet");
                 break;
        	 case "-r":
        		// trackName = args[1];
        		 System.out.println("The option is not ready yet");
                 break;
        	 case "-g":
        		 trackName = args[1];
        		 String genreName = args[2];
        		 ms.moveTrackAnotherGenre(trackName, genreName);
                 break;
        	 case "-p":
                 ms.printAllTracksTitle();
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
        		"-i [track name]			get track info \r" +
        		"-s [track name]			set track info \r" +
        		"-a [track parameters]		insert (add) track into library \r" +
        		"-r [track name]			remove track \r" +
        		"-g [track name, genre name]	set another genre \r" +
        		"-p 				print info for all available tracks \r");
    }

    @Override
    public String getName() {
        return "TRACK";
    }

    @Override
    public String getDescription() {
        return "Defines operations with tracks";
    }
}