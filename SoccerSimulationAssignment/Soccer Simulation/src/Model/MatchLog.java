package Model;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class MatchLog {
    private final List<LogEntry> entries = new CopyOnWriteArrayList<>();

    public void add(long tick, LogEntry.Type type, String team, String message){
        entries.add(new LogEntry(tick, type, team, message));
    }

    
}
