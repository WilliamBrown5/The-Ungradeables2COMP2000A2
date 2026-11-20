package Rendering;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.stream.*;

import javax.swing.*;

import Model.*;


class DebugWindow extends JFrame {
    private javax.swing.Timer refresh;
    private final JTextArea text = new JTextArea();
    private final JComboBox<String> teamFilterBox = new JComboBox<>(new String[] {"ALL", "HOME", "AWAY"});
    private final JComboBox<String> typeFilterBox = new JComboBox<>(eventTypeOptions());
    private final MatchLog<LogEntry> log;


    DebugWindow(MatchLog<LogEntry> log) {
        super("Debug Log");
        this.log = log;
        text.setEditable(false);
        text.setFont(new Font("Lexend", Font.PLAIN, 13));
        text.setBackground(new Color(64, 65, 67));
        text.setForeground(new Color(255, 255, 255));

        JPanel filters = new JPanel();
        filters.add(new JLabel("Team: "));
        filters.add(teamFilterBox);
        filters.add(new JLabel("Type: "));
        filters.add(typeFilterBox);

        add(filters, BorderLayout.NORTH);
        add(new JScrollPane(text), BorderLayout.CENTER);
        
        teamFilterBox.addActionListener(e -> updateText());
        typeFilterBox.addActionListener(e -> updateText());

        refresh = new javax.swing.Timer(500, e -> updateText());
        refresh.start();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize((ScreenSize.width / 2) + 30, ScreenSize.height + 40);

    }

    private void updateText(){
        String team = (String) teamFilterBox.getSelectedItem();
        String type = (String) typeFilterBox.getSelectedItem();

        String output = log.stream()
                           .filter((team.equals("ALL")) ? entry -> true : entry -> entry.getTeam().equals(team))
                           .filter((type.equals("ALL")) ? entry -> true : entry -> entry.getType().name().equals(type))
                           .map(LogEntry::toString)
                           .collect(Collectors.joining("\n"));

        if (!output.equals(text.getText()))
            text.setText(output);
    }

    private static String[] eventTypeOptions() {
        return Stream.concat(Stream.of("ALL"),
                             Arrays.stream(LogEntry.EventType.values())
                                   .map(Enum::name))
                     .toArray(String[]::new);
    }


    @Override
    public void dispose() {
        refresh.stop();
        super.dispose();
    }

}