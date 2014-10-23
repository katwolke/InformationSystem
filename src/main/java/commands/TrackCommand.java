package commands;

import java.util.logging.Logger;

import management.ManagementSystem;

class TrackCommand implements Command {
	
	ManagementSystem ms = new ManagementSystem();
   
	@Override
    public boolean execute(String... args) {
    	Logger log = Logger.getAnonymousLogger();
    	 if (args == null) {
    		 log.warning("You must specify the parameter. Type \"help track\" to view available" );
         } else {
        	 String command = args[0];
        	 switch(command){
        	 case "-i":
        		 if((args.length -1 == 0)){
        			 System.out.println("Type track title to process" );
        			 break;
        		 }
        		 ms.printTrackInfo(args[1]); 
                 break;
        	 case "-s":
        		 if((args.length -1 == 0)){
        			 System.out.println("Type track title to process" );
        			 break;
        		 }
        		 log.info("The option \"set track info\" is not ready yet"); 
                 break;
        	 case "-a":
        		 if((args.length -1 < 5)){
        			 System.out.println("Don't skip parameters, if don't no info - type \"-\" \r" +
        			 		"Example: -a \"genre\" \"title\" \"singer\" \"-\" -" );
        			 break;
        		 }
        		 String[] arguments = new String[args.length - 1];
                 System.arraycopy(args, 1, arguments, 0, arguments.length);
        		 ms.insertTrack(arguments);
                 break;
        	 case "-r":
        		 if((args.length -1 == 0)){
        			 System.out.println("Type track title to remove" );
        			 break;
        		 }
        		 log.info("The option \"remove track\" is not ready yet");
                 break;
        	 case "-g":
        		 ms.moveTrackAnotherGenre(args[1], args[2]);
                 break;
        	 case "-p":
                 ms.printAllTracksTitle();
                 break;
        	 default:
        		 log.warning("Parameter <" + command + "> didn't defined");
        	 }
         }
        return true;
    }

    @Override
    public void printHelp() {
        System.out.println(
        		"-i \"track name\"			get track info \r" +
        		"-s \"track name\"			set track info \r" +
        		"-a \"track parameters\"		insert (add) track into library \r" +
        		"-r \"track name\"			remove track \r" +
        		"-g \"track name\" \"genre name\"	set another genre \r" +
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