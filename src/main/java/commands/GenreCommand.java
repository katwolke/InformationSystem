package commands;

import java.util.TreeSet;

import interfaces.Command;
import management.ManagementSystem;
import output.DisplaySystem;

public class GenreCommand implements Command {

	ManagementSystem ms;
	private DisplaySystem ds;
	
    public GenreCommand() {
		this.ds = DisplaySystem.getInstance();
		this.ms = ManagementSystem.getInstance();
	}
	
	@Override
	public boolean execute(String... args) {
		 if (args == null) {
			 ds.DisplayMessage("You must specify the parameter. Type \"help genre\" to view available" );
		 } else {
        	 String command = args[0];
        	 switch(command){
        	 case "-a":
        		 ms.setRecordsList(args[1]); 
                 break;
        	 case "-r":
        		 ms.removeRecordsList(args[1]);; 
                 break;
        	 case "-c":
        		 if((args.length -1 < 3)){
            		 ds.DisplayMessage("The option \"combine genres\" need parameters \"genre1 name\" \"genre2 name\" \"new genre name\"");
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
           		 ds.DisplayMessage("Parameter <" + command + "> didn't defined");
        	 }
		 }
		return true;
	}

	@Override
	public void printHelp() {
		 ds.DisplayMessage(
				"-a \"new genre name\"				add genre into list \r\n" +
				"-r \"genre name\"					remove genre from list \r\n" +
				"						attention, the operation remove genre also moves all tracks belong to it to Unsorted\r\n" +
				"-c \"genre1 name\" \"genre2 name\" \"new genre name\"	combine genres \r\n" +
				"-p \"genre name\"		 			print all tracks of this genre \r\n" +
				"-p						print genre list");
		
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
