package commands;

public class GenreCommand implements Command {

	@Override
	public boolean execute(String... args) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void printHelp() {
		System.out.println(
				"-a [new genre name]	add genre into list \r" +
				"-g [genre name]		get all tracks this genre \r" +
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
