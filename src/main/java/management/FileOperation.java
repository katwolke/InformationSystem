package management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import musicLibrary.Genre;
import musicLibrary.Track;

public class FileOperation {
    private static Logger log = Logger.getAnonymousLogger();


	public static void serialized(String fileName, Object obj) throws IOException{
		ObjectOutputStream objectOutStream = null;
		try {
			objectOutStream = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
			objectOutStream.writeObject(obj);
		}
		catch (Exception e) {
			log.info("Caught exception while processing object: " + e.getMessage());
		}finally{
			objectOutStream.close();
		}
	}
	
	public static <classType> Object deserialized(String fileName, Class classType) throws IOException{
		ObjectInputStream objectInStream = null;
		Object obj = null;
		try {
			objectInStream = new ObjectInputStream(new FileInputStream(new File(fileName)));
			obj = (classType) objectInStream.readObject();
		}catch (Exception e) {
			log.info("Caught exception while processing object: " + e.getMessage());
		}finally{
			objectInStream.close();
		}
		return obj;
	}
	
	public static void main(String[] args) throws IOException {
		Collection<Track> tracks = new HashSet<>();
		Collection<Track> tracks2 = new HashSet<>();
		List<Genre> genres = new ArrayList<>();
		
		Track track = new Track("Power Metal", "Whispers of the Great Mother", "MaterDea", "A Rose for Egeria", "7.06");
		tracks.add(track);
		genres.add(new Genre(track.getGenre(), tracks));

		Track track2 = new Track("Symphonic Metal", "500 letters", "Tarja Turunen", "Colours in the dark", "4.22");
		tracks2.add(track2);
		genres.add(new Genre(track2.getGenre(), tracks2));

		serialized("storage/"+track.getGenre()+".bin", tracks);
		serialized("storage/"+track2.getGenre()+".bin", tracks2);
		
		List<Genre> reTracks = new ArrayList<>();
		File dir = new File("storage/.");
		for(String path : dir.list()) {
			reTracks.add(new Genre(path.substring(0, path.lastIndexOf('.')),(HashSet)deserialized("storage/"+ path, HashSet.class)));
			System.out.println(path.substring(0, path.lastIndexOf('.')));
		}
		
		
	//	System.out.println(reTracks.get(0).getTracks().get(0).getTrackTitle());
	//	System.out.println(reTracks.get(1).getTracks().get(0).getTrackTitle());
	}
}


