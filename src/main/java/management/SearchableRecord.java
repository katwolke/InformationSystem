package management;

/**
 * Created by Morthanion on 22.10.2014.
 */

// interface to be used in search
public interface SearchableRecord {
    public boolean fitsMask(String mask);// see if any of fields fits search mask, return true if so
}
