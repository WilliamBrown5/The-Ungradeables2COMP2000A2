package Model;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class TimerPanel extends JPanel {
    private MatchTimer matchTimer;
    private JLabel timerLabel;
    private Timer timer;
    private Match matchMain;


    public TimerPanel(Match match) {
        this(match, 90, 1.0);
    }

    public TimerPanel(Match match, int startingSeconds, double speed) {
        this.matchMain = match;
        int delay = 1000 / (int) speed;
        matchTimer = new MatchTimer(startingSeconds);
        timerLabel = new JLabel(matchTimer.getTime(), SwingConstants.CENTER);
        timerLabel.setFont(new Font("Lexend", Font.BOLD, 24));
        timerLabel.setForeground(Color.BLACK);
        add(timerLabel);

        timer = new Timer(delay, e -> {
            matchTimer.tick();
            timerLabel.setText(matchTimer.getTime());

            if (matchTimer.getSeconds() == 0) {
                timer.stop();

                if (matchMain != null) {
                    matchMain.finishMatch();
                }
            }
        });

        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }


    public void resumeTimer() {
        if (!timer.isRunning() && matchTimer.getSeconds() > 0) {
            timer.start();
        }
    }


}