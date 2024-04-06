package TheProject.FileHandling;

import java.util.ArrayList;

//The sole purpose of this class is to store data in an array list and check if a load was successful.//
public class AttemptedLoad {
    public boolean loaded;
    public ArrayList<String[]> data;

    public AttemptedLoad() {
        loaded = false;
        data = new ArrayList<String[]>();
    }
}