package Model;

import java.util.List;
import java.util.stream.*;
// have to use this because there are two threads that will interact with the arraylist so to avoid any crashes im using this instead
import java.util.concurrent.CopyOnWriteArrayList;


public class MatchLog<T> {
    private final List<T> entries = new CopyOnWriteArrayList<>();

    public void add(T entry){
        entries.add(entry);
    }

    public Stream<T> stream(){
        return entries.stream();
    }

}
