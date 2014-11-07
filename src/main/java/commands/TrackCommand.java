package commands;

import interfaces.Command;
import management.ManagementSystem;
import output.DisplaySystem;

class TrackCommand implements Command {
	
	static final String PRINT_HELP = "-i \"track title\"				get track info \r\n" +
    		"-s \"track title\" \"parameter\" \"new value\"	set track info, you can change several parameters at once, \r\n" +
    		"						use the key -g to set track genre, please \r\n" +
    		"-a \"track parameters\"				insert (add) track into library \r\n" +
    		"-r \"track title\" \"genre name\"			remove track \r\n" +
    		"-g \"track title\" \"genre name\"			set another genre \r\n" +
    		"-p 						print info for all available tracks \r\n";
	static final String COMMAND_DESCRIPTION = "Defines operations with tracks";
	static final String COMMAND_NAME = "TRACK";
	static final String WARNING_NO_COMMAND_PARAMETER = "You must specify the parameter. Type \"help track\" to view available";
	static final String WARNING_SUBCOMMAND_INFO = "Enter track title to process";
	static final String WARNING_SUBCOMMAND_SET = "Enter track title and parameters with new values to process";
	static final String WARNING_SUBCOMMAND_INSERT = "Enter parameters, \r\n" +
	 		"Example: -a \"genre\" \"title\" \"singer\" \"Album\" Record length";
	static final String WARNING_SUBCOMMAND_REMOVE = "Enter track title and genre name to remove";
	static final String WARNING_SUBCOMMAND_SET_GENRE = "Enter track title and genre name to process";
	static final String ARGUMENT_STUB = "-";
	
	private ManagementSystem ms;
	private DisplaySystem ds;
	
    public TrackCommand() {
		this.ds = DisplaySystem.getInstance();
		this.ms = ManagementSystem.getInstance();
	}
    
	@Override
    public boolean execute(String... args) {
		if (args == null) {
			ds.DisplayMessage(WARNING_NO_COMMAND_PARAMETER);
         } else {
        	 String command = args[0];
        	 switch(command){
        	 case "-i":
        		 if((args.length -1 == 0)){
        			 ds.DisplayMessage(WARNING_SUBCOMMAND_INFO);
        			 break;
        		 }
        		 ms.printTrackInfo(args[1]); 
                 break;
        	 case "-s":
        		 if((args.length -1 == 0)){
        			 ds.DisplayMessage(WARNING_SUBCOMMAND_SET);
        			 break;
        		 }
        		 String[] setArgs = { ARGUMENT_STUB, args[1], ARGUMENT_STUB, ARGUMENT_STUB, ARGUMENT_STUB};
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
        			 ds.DisplayMessage(WARNING_SUBCOMMAND_INSERT);
        			 break;
        		 }
        		 String[] arguments = new String[args.length - 1];
                 System.arraycopy(args, 1, arguments, 0, arguments.length);
        		 ms.insertTrack(arguments);
                 break;
        	 case "-r":
        		 if((args.length -1 < 2)){
        			 ds.DisplayMessage(WARNING_SUBCOMMAND_REMOVE);
        			 break;
        		 }
        		 ms.removeRecord(args[1], args[2]);
                 break;
        	 case "-g":
        		 if((args.length -1 < 2)){
        			 ds.DisplayMessage(WARNING_SUBCOMMAND_SET_GENRE);
        			 break;
        		 }
        		 ms.moveRecordAnotherSet(args[1], args[2]);
                 break;
        	 case "-p":
                 ms.printAllTracksTitle();
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