package Rendering;

import java.util.stream.Collectors;

import javax.swing.*;

import Model.*;


class DebugWindow extends JFrame {
    private final javax.swing.Timer refresh;

    DebugWindow(MatchLog<LogEntry> log) {
        super("Debug Log");
        JTextArea text = new JTextArea();
        text.setEditable(false);
        add(new JScrollPane(text));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        refresh = new javax.swing.Timer(500, e -> {
            String output = log.stream()
                    .map(LogEntry::toString)
                    .collect(Collectors.joining("\n")); // should add more here
            if (!output.equals(text.getText()))
                text.setText(output);
        });

        refresh.start();
        setSize(ScreenSize.width / 2, ScreenSize.height);

    }

    @Override
    public void dispose() {
        refresh.stop();
        super.dispose();
    }

}