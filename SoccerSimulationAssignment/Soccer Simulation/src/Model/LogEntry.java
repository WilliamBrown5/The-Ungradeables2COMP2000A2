package Model;

public class LogEntry {
    public enum Type {KICKOFF, POSSESSION, PASS, SHOT_ON_GOAL, TACKLE, CLEARANCE, GOAL, ERROR, FULL_TIME }

    private final long tick;
    private final Type type;
    private final String team;
    private final String message;

    public LogEntry(long tick, Type type, String team, String message){
        this.tick = tick;
        this.type = type;
        this.team = team;
        this.message = message;
    }

    public long getTick(){
        return tick;
    }

    public Type getType(){
        return type;
    }

    public String getTeam(){
        return team;
    }

    public String getMessage() {
        return message;
    }

    @Override 
    public String toString(){
        return String.format("[%5d] %-10s %s", tick, type, message)
    }
}
