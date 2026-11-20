package Rendering;

import java.util.stream.Collectors;

import javax.swing.*;

import Model.*;


class DebugWindow extends JFrame {
    private javax.swing.Timer refresh;

    DebugWindow(MatchLog<LogEntry> log) {
        super("Debug Log");
        JTextArea text = new JTextArea();
        text.setEditable(false);
        add(new JScrollPane(text));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        displayTeamOutput(text, log);
        
        setSize(ScreenSize.width / 2, ScreenSize.height);
    }

    public void displayRawOutput(JTextArea textArea, MatchLog<LogEntry> inputLog){
        refresh = new javax.swing.Timer(500, e -> {
            String output = inputLog.stream()
                                    .map(LogEntry::toString)
                                    .collect(Collectors.joining("\n"));
            if (!output.equals(textArea.getText()))
                textArea.setText(output);
        });
        refresh.start();
    }

    public void displayTeamOutput(JTextArea textArea, MatchLog<LogEntry> inputLog){
        refresh = new javax.swing.Timer(500, e -> {
            String output = inputLog.stream()
                                    .filter(entry -> entry.getTeam().equals("HOME"))
                                    .map(LogEntry::toString)
                                    .collect(Collectors.joining("\n"));
            if (!output.equals(textArea.getText()))
                textArea.setText(output);
        });
        refresh.start();
    }

    public void displayTypeOutput(JTextArea textArea, MatchLog<LogEntry> inputLog){
        refresh = new javax.swing.Timer(500, e -> {
            String output = inputLog.stream()
                                    .map(LogEntry::toString)
                                    .collect(Collectors.joining("\n"));
            if (!output.equals(textArea.getText()))
                textArea.setText(output);
        });
        refresh.start();
    }

    public String fulltimeStats(MatchLog<LogEntry> inputLog){
        String stats = inputLog.stream()
                               .filter(entry -> entry.getTeam().equals("HOME") && entry.getTeam().equals("AWAY"))
                               .collect(Collectors.groupingBy(LogEntry::getTeam, Collectors.counting())).toString();
        return stats;
    }

    

    @Override
    public void dispose() {
        refresh.stop();
        super.dispose();
    }

}