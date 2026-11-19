package Model;

public class LogEntry {
    public enum EventType {KICKOFF, POSSESSION, PASS, SHOT_ON_GOAL, TACKLE, CLEARANCE, GOAL, ERROR, FULL_TIME }

    private final long tick;
    private final EventType eventType;
    private final String team;
    private final String message;

    public LogEntry(long tick, EventType eventType, String team, String message){
        this.tick = tick;
        this.eventType = eventType;
        this.team = team;
        this.message = message;
    }

    public long getTick(){
        return tick;
    }

    public EventType getType(){
        return eventType;
    }

    public String getTeam(){
        return team;
    }

    public String getMessage() {
        return message;
    }

    @Override 
    public String toString(){
        return String.format("[%5d] %-10s %s", tick, eventType, message);
    }
}
