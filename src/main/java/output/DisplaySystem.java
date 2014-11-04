package output;

import java.util.Collections;
import java.util.Iterator;
import java.util.Collection;
import interfaces.Record;

/**
 * Created by Morthanion on 31.10.2014.
 */
public class DisplaySystem {
    private static DisplaySystem instance = new DisplaySystem();
    private DisplaySystem(){}
    public static DisplaySystem getInstance()
    {
        return instance;
    }
    public void DisplayMessage(String message)
    {
        System.out.println(message);
    }
    public void DisplayError(Exception e)
    {
        System.out.println("Error: "+e.getMessage());
    }
        public void DisplayList(Collection<Record> list) {
              System.out.printf("%-30s %-9s %-30s %-20s %-20s%n",
                "Name",
                " | "+"Length",
                " | "+"Album",
                " | "+"Genre",
                " | "+"Singer" );
        Iterator<Record> iterator = list.iterator();
        while (iterator.hasNext()) {
            Record out = iterator.next();
            System.out.printf("%-30s %-9s %-30s %-20s %-20s%n",
                    out.getTrackTitle(),
                    " | "+out.getRecordLength(),
                    " | "+out.getAlbum(),
                    " | "+out.getGenre(),
                    " | "+out.getSinger());
        }
    }
}
