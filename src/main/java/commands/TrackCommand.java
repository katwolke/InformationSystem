package commands;

import interfaces.Command;

import java.util.logging.Logger;

import management.ManagementSystem;

class TrackCommand implements Command {
	
	ManagementSystem ms;
   
	@Override
    public boolean execute(String... args) {
		ms = ManagementSystem.getInstance();
    	Logger log = Logger.getAnonymousLogger();
    	 if (args == null) {
    		 log.warning("You must specify the parameter. Type \"help track\" to view available" );
         } else {
        	 String command = args[0];
        	 switch(command){
        	 case "-i":
        		 if((args.length -1 == 0)){
        			 System.out.println("Enter track title to process" );
        			 break;
        		 }
        		 ms.printTrackInfo(args[1]); 
                 break;
        	 case "-s":
        		 if((args.length -1 == 0)){
        			 System.out.println("Enter track title and parameters with new values to process" );
        			 break;
        		 }
        		 String[] setArgs = { "-", args[1], "-", "-", "-"};
        		 for(int i=2;i<args.length;i++)
	        		 switch(args[i]){
	        		 case "genre":
	        			 setArgs[0] = args[i+1];
	        		 case "title":
	        			 setArgs[1] = args[i+1];
	        			 break;
	        		 case "singer":
	        			 setArgs[2] = args[i+1];
	        			 break;
	        		 case "album":
	        			 setArgs[3] = args[i+1];
	        			 break;
	        		 case "length":
	        			 setArgs[4] = args[i+1];
	        			 break;
	        		 default:
	        			 break;
        		 }
        		 ms.setTrack(args[1], setArgs);
                 break;
        	 case "-a":
        		 if((args.length -1 == 0)){
        			 System.out.println("Enter parameters, \r\n" +
        			 		"Example: -a \"genre\" \"title\" \"singer\" \"Album\" Record length" );
        			 break;
        		 }
        		 String[] arguments = new String[args.length - 1];
                 System.arraycopy(args, 1, arguments, 0, arguments.length);
        		 ms.insertTrack(arguments);
                 break;
        	 case "-r":
        		 if((args.length -1 == 0)){
        			 System.out.println("Enter track title and genre to remove" );
        			 break;
        		 }
        		 ms.removeRecord(args[1], args[2]);
                 break;
        	 case "-g":
        		 ms.moveRecordAnotherSet(args[1], args[2]);
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
        		"-i \"track title\"				get track info \r\n" +
        		"-s \"track title\" \"parameter\" \"new value\"	set track info, you can change several parameters at once, \r\n" +
        		"						use the key -g to set track genre, please \r\n" +
        		"-a \"track parameters\"				insert (add) track into library \r\n" +
        		"-r \"track title\" \"genre name\"			remove track \r\n" +
        		"-g \"track title\" \"genre name\"			set another genre \r\n" +
        		"-p 						print info for all available tracks \r\n");
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