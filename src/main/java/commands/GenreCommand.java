package commands;

import interfaces.Command;
import management.ManagementSystem;
import output.DisplaySystem;

public class GenreCommand implements Command {
	
	private static final String PRINT_HELP = "" +
			"-a <new genre name>				add genre into list \r\n" +
			"-r <genre name>					remove genre from list \r\n" +
			"						attention, the operation remove genre also moves all tracks belong to it to Unsorted\r\n" +
			"-c <genre1 name> <genre2 name> <new genre name>	combines tracks of genre1 and genre2 into new genre name pack \r\n" +
			"-p <genre name>		 			print all tracks of this genre \r\n" +
			"-p						print genre list";
	private static final String COMMAND_DESCRIPTION = "Defines operations with genres";
	private static final String COMMAND_NAME = "GENRE";
	private static final String WARNING_NO_COMMAND_PARAMETER = "You must specify the parameter. Type \"help genre\" to view available";
	private static final String WARNING_SUBCOMMAND_SET = "Enter the old name of the genre and new to continue";
	private static final String WARNING_SUBCOMMAND_COMBINE = "The option \"combine genres\" need parameters <genre1 name> <genre2 name> <new genre name>";
	private static final String WARNING_SUBCOMMAND_INSERT = "Enter genre name to add";
	private static final String WARNING_SUBCOMMAND_REMOVE = "Enter genre name to remove";
	private static final String ARGUMENT_STUB = "-";

	private ManagementSystem ms;
	private DisplaySystem ds;
	
    public GenreCommand() {
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
        	 case "-s":
        		 if((args.length -1 < 2)){
        			 ds.DisplayMessage(WARNING_SUBCOMMAND_SET);
        			 break;
        		 }
        		 ms.setRecordsListName(args[1], args[2]); 
                 break;
        	 case "-a":
        		 if((args.length -1 == 0)){
        			 ds.DisplayMessage(WARNING_SUBCOMMAND_INSERT);
        			 break;
        		 }
        		 ms.setRecordsList(args[1]); 
                 break;
        	 case "-r":
        		 if((args.length -1 == 0)){
        			 ds.DisplayMessage(WARNING_SUBCOMMAND_REMOVE);
        			 break;
        		 }
        		 ms.removeRecordsList(args[1]);; 
                 break;
        	 case "-c":
        		 if((args.length -1 < 3)){
            		 ds.DisplayMessage(WARNING_SUBCOMMAND_COMBINE);
        		 }
        		 ms.combineRecordsLists(args[1], args[2], args[3]);
                 break;
        	 case "-p":
        		 if((args.length -1 == 0)){
        			 ms.getRecordsListsName();
        		 }else{
        			 ms.getTracksTitles(args[1]);
        		 }
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
