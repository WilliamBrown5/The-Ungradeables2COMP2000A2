/*
Please run this file, this is where the whole simulation runs out of :)

*/
package Rendering;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import Model.*;

class UIWindow {
    JFrame frame;
    MenuState currentState;
    CircleList<TeamFormation> formationCircle;
    CircleList<TeamFormation> homeFormationCircle;
    CircleList<TeamFormation> awayFormationCircle;
    CircleList<String> sidesCircle;
    SoccerPitch pitch;
    VerticalPitch vertPitch;
    SimWindow activeSimulation;
    boolean debugMode = false;
    String backgroundColour = "#329632";
    String btnColour = "#404143";

    public UIWindow() {
        frame = new JFrame("Soccer Simulation");
        frame.setSize(ScreenSize.width, ScreenSize.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        TeamFormation[] possibleFormations = {new FourFourTwoFormation(), new FourThreeThreeFormation(), new ThreeFiveTwoFormation(), new FourFiveOneFormation()};
        homeFormationCircle = new CircleList<>(possibleFormations);
        awayFormationCircle = new CircleList<>(possibleFormations);
        formationCircle = homeFormationCircle;
        sidesCircle = new CircleList<>(new String[] { "Home", "Away" });
        pitch = new SoccerPitch();
        vertPitch = new VerticalPitch();

    }

    void changeState(MenuState nexState) {
        if (currentState != null)
            currentState.onExit();
        currentState = nexState;
        frame.setContentPane(nexState);
        frame.revalidate();
        frame.repaint();
        frame.getContentPane().setBackground(Color.decode(backgroundColour));
        nexState.onEnter();
    }

    void start() {
        changeState(new MainMenu(this));
        frame.setVisible(true);
    }

    boolean getDebug() {
        return this.debugMode;
    }

    void setDebug(boolean b) {
        this.debugMode = b;
        frame.setTitle("Soccer Simulation" + debugString());
    }

    private String debugString() {
        if (this.getDebug() == true)
            return " (Debug)";

        return "";
    }

    void displayButtons(JButton[] buttons) {
        for (JButton btn : buttons) {
            btn.setBackground(Color.decode(btnColour));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Lexend", Font.BOLD, 16));
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    void displayCheckBox(JCheckBox[] boxes) {
        for (JCheckBox cbx : boxes) {
            cbx.setBackground(Color.decode(btnColour));
            cbx.setForeground(Color.WHITE);
            cbx.setFont(new Font("Lexend", Font.BOLD, 16));
            cbx.setBorderPainted(false);
            cbx.setFocusPainted(false);
            cbx.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    void displayLabel(JLabel[] labels) {
        for (JLabel lbl : labels) {
            lbl.setBackground(Color.decode(btnColour));
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Lexend", Font.BOLD, 16));
            lbl.setOpaque(true);
            lbl.setHorizontalAlignment(JLabel.CENTER);
            lbl.setVerticalAlignment(JLabel.CENTER);
        }
    }

}

abstract class MenuState extends JPanel {
    protected UIWindow window;

    MenuState(UIWindow window) {
        this.window = window;
    }

    void onEnter() {
    }

    void onExit() {
    }

    abstract void back();
}

class MainMenu extends MenuState {
    MainMenu(UIWindow window) {
        super(window);
        setLayout(null);
        window.pitch.setBounds(0, 0, ScreenSize.width, ScreenSize.height);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> openStartMenu());

        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> openSettingsMenu());

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> back());

        JButton[] buttons = { startButton, settingsButton, exitButton };

        window.displayButtons(buttons);

        startButton.setBounds(ScreenSize.width / 2 - 125, ScreenSize.height / 2 - 100, 250, 50);
        settingsButton.setBounds(ScreenSize.width / 2 - 125, ScreenSize.height / 2 - 30, 250, 50);
        exitButton.setBounds(ScreenSize.width / 2 - 125, ScreenSize.height / 2 + 40, 250, 50);

        add(startButton);
        add(settingsButton);
        add(exitButton);
        add(window.pitch);
    }

    void openStartMenu() {
        window.changeState(new StartMenu(window));
    }

    void openSettingsMenu() {
        window.changeState(new SettingsMenu(window));
    }

    @Override
    void back() {
        System.exit(0);
    }

}

class StartMenu extends MenuState {
    StartMenu(UIWindow window) {
        super(window);
        setLayout(null);
        window.vertPitch.setBounds(0, 0, ScreenSize.width, ScreenSize.height);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> startSim());

        JButton formationButton = new JButton("Formation Settings");
        formationButton.addActionListener(e -> openFormationSettingsMenu());

        JButton matchSettingsButton = new JButton("Match Settings");
        matchSettingsButton.addActionListener(e -> openMatchSettingsMenu());

        JButton exitButton = new JButton("Back");
        exitButton.addActionListener(e -> back());

        JButton[] buttons = { startButton, formationButton, matchSettingsButton, exitButton };

        window.displayButtons(buttons);

        startButton.setBounds(ScreenSize.width / 4 - 125, ScreenSize.height / 2 - 140, 250, 50);
        formationButton.setBounds(ScreenSize.width / 4 - 125, ScreenSize.height / 2 - 70, 250, 50);
        matchSettingsButton.setBounds(ScreenSize.width / 4 - 125, ScreenSize.height / 2, 250, 50);
        exitButton.setBounds(ScreenSize.width / 4 - 125, ScreenSize.height / 2 + 70, 250, 50);

        add(startButton);
        add(formationButton);
        add(matchSettingsButton);
        add(exitButton);
        add(window.vertPitch);

        window.vertPitch.drawPlayers(window);

    }

    void startSim() {
        window.changeState(new SimWindow(window));
    }

    void openFormationSettingsMenu() {
        window.changeState(new FormationMenu(window));
    }

    void openMatchSettingsMenu() {
        window.changeState(new MatchSettingsMenu(window));
    }

    @Override
    void back() {
        window.changeState(new MainMenu(window));
    }

}

/*
 * NEEDS, to have the debug output make a pop-up window that prints the log of
 * everything that happens
 */
class SettingsMenu extends MenuState {
    SettingsMenu(UIWindow window) {
        super(window);
        setLayout(null);

        JCheckBox debugButton = new JCheckBox("Debug Mode", window.getDebug());
        debugButton.addActionListener(e -> window.setDebug(debugButton.isSelected()));

        JButton exitButton = new JButton("Save");
        exitButton.addActionListener(e -> back());

        JButton[] buttons = { exitButton };
        JCheckBox[] boxes = { debugButton };
        window.displayCheckBox(boxes);
        window.displayButtons(buttons);

        debugButton.setBounds(ScreenSize.width / 2 - 125, ScreenSize.height / 2 - 100, 250, 50);
        exitButton.setBounds(ScreenSize.width / 2 - 125, ScreenSize.height / 2 - 30, 250, 50);

        add(debugButton);
        add(exitButton);
    }

    @Override
    void back() {
        window.changeState(new MainMenu(window));
    }
}

class FormationMenu extends MenuState {
    CircleList<TeamFormation> formationCircle;
    CircleList<String> sidesCircle;

    FormationMenu(UIWindow window) {
        super(window);
        setLayout(null);
        window.vertPitch.setBounds(0, 0, 700, 400);

        this.formationCircle = window.formationCircle;
        this.sidesCircle = window.sidesCircle;
        JButton formPrevBtn = new JButton("<");
        JButton formNextBtn = new JButton(">");

        JLabel formationLabel = new JLabel(formationCircle.getCurrent().getName());

        formPrevBtn.addActionListener(e -> {
            formationCircle.previous();
            formationLabel.setText(formationCircle.getCurrent().getName());
            window.vertPitch.drawPlayers(window);
        });

        formNextBtn.addActionListener(e -> {
            formationCircle.next();
            formationLabel.setText(formationCircle.getCurrent().getName());
            window.vertPitch.drawPlayers(window);
        });

        JButton swapPrevBtn = new JButton("<");
        JButton swapNextBtn = new JButton(">");

        JLabel swapLabel = new JLabel(sidesCircle.getCurrent().toString());

        swapPrevBtn.addActionListener(e -> {
            window.vertPitch.swapSides(window);
            formationCircle = window.formationCircle;
            formationLabel.setText(formationCircle.getCurrent().getName());
            swapLabel.setText(sidesCircle.getCurrent().toString());
            window.vertPitch.drawPlayers(window);
        });

        swapNextBtn.addActionListener(e -> {
            window.vertPitch.swapSides(window);
            formationCircle = window.formationCircle;
            formationLabel.setText(formationCircle.getCurrent().getName());
            swapLabel.setText(sidesCircle.getCurrent().toString());
            window.vertPitch.drawPlayers(window);
        });

        JButton exitButton = new JButton("Save");
        exitButton.addActionListener(e -> back());

        JButton[] buttons = { formPrevBtn, formNextBtn, swapPrevBtn, swapNextBtn, exitButton };
        JLabel[] labels = { formationLabel, swapLabel };
        window.displayLabel(labels);

        window.displayButtons(buttons);

        formPrevBtn.setBounds(ScreenSize.width / 4 - 125, ScreenSize.height / 2 - 140, 45, 50);
        formationLabel.setBounds(ScreenSize.width / 4 - 75, ScreenSize.height / 2 - 140, 150, 50);
        formNextBtn.setBounds(ScreenSize.width / 4 + 80, ScreenSize.height / 2 - 140, 45, 50);

        swapPrevBtn.setBounds(ScreenSize.width / 4 - 125, ScreenSize.height / 2 - 70, 45, 50);
        swapLabel.setBounds(ScreenSize.width / 4 - 75, ScreenSize.height / 2 - 70, 150, 50);
        swapNextBtn.setBounds(ScreenSize.width / 4 + 80, ScreenSize.height / 2 - 70, 45, 50);

        exitButton.setBounds(ScreenSize.width / 4 - 125, ScreenSize.height / 2, 250, 50);

        add(formPrevBtn);
        add(formationLabel);
        add(formNextBtn);

        add(swapPrevBtn);
        add(swapLabel);
        add(swapNextBtn);

        add(exitButton);

        add(window.vertPitch);

        window.vertPitch.drawPlayers(window);
    }

    @Override
    void back() {
        window.changeState(new StartMenu(window));
    }
}

class MatchSettingsMenu extends MenuState {
    MatchSettingsMenu(UIWindow window) {
        super(window);
        setLayout(null);

        JButton exitButton = new JButton("Save");
        exitButton.addActionListener(e -> back());

        JButton[] buttons = { exitButton };

        window.displayButtons(buttons);

        exitButton.setBounds(ScreenSize.width / 2 - 125, ScreenSize.height / 2 - 100, 250, 50);

        add(exitButton);
    }

    @Override
    void back() {
        window.changeState(new StartMenu(window));
    }
}

class SimWindow extends MenuState {
    Match match;
    private TimerPanel timerPanel;

    SimWindow(UIWindow window) {
        super(window);
        setLayout(null);

        match = new Match();
        window.activeSimulation = this;
        match.setupAndStartSimulation(window.homeFormationCircle.getCurrent(), window.awayFormationCircle.getCurrent());

        JButton menuButton = new JButton("Menu");
        menuButton.addActionListener(e -> back());

        JButton[] buttons = { menuButton };

        window.displayButtons(buttons);

        menuButton.setBounds(ScreenSize.width / 5 - 125, ScreenSize.height - 385, 80, 40);

        timerPanel = new TimerPanel(match);
        timerPanel.setBounds(ScreenSize.width / 2 - 75, 20, 150, 40);

        match.pitch.setBounds(0, 0, ScreenSize.width, ScreenSize.height);
        add(timerPanel);
        add(menuButton);
        add(match.pitch);

    }

    // Overlapping children (the full-screen pitch sits behind the timer/menu)
    // need this override, otherwise Swing assumes nothing overlaps and skips
    // repainting the timer label whenever the pitch redraws underneath it,
    // that's what was causing the timer to blink in and out.
    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }

    @Override
    void onEnter() {
        match.resumeSimulation();
        timerPanel.resumeTimer();
    }

    @Override
    void onExit() {
        match.pauseSimulation();
        timerPanel.pauseTimer();
    }

    void stop() {
        match.stopSimulation();
        timerPanel.stopTimer();
    }

    @Override
    void back() {
        window.changeState(new SimWindowMenu(window));
    }
}

class SimWindowMenu extends MenuState {
    SimWindowMenu(UIWindow window) {
        super(window);
        setLayout(null);

        JButton resumeButton = new JButton("Resume");
        resumeButton.addActionListener(e -> resume());

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> back());

        JButton[] buttons = { resumeButton, exitButton };

        window.displayButtons(buttons);

        resumeButton.setBounds(ScreenSize.width / 2 - 75, ScreenSize.height / 2 - 100, 150, 50);
        exitButton.setBounds(ScreenSize.width / 2 - 75, ScreenSize.height / 2 - 30, 150, 50);

        add(resumeButton);
        add(exitButton);
    }

    void resume() {
        if (window.activeSimulation != null) {
            window.changeState(window.activeSimulation);
        }
    }

    @Override
    void back() {
        if (window.activeSimulation != null) {
            window.activeSimulation.stop();
            window.activeSimulation = null;
        }
        window.changeState(new StartMenu(window));
    }
}

class CircleNode<T> {
    T value;
    CircleNode<T> next;
    CircleNode<T> prev;

    CircleNode(T value) {
        this.value = value;
    }
}

class CircleList<T> {
    private CircleNode<T> current;

    CircleList(T[] values) {
        CircleNode<T> start = new CircleNode<T>(values[0]);
        CircleNode<T> prevNode = start;

        for (int i = 1; i < values.length; i++) {
            CircleNode<T> node = new CircleNode<>(values[i]);
            prevNode.next = node;
            node.prev = prevNode;
            prevNode = node;
        }

        prevNode.next = start;
        start.prev = prevNode;

        this.current = start;

    }

    T getCurrent() {
        return current.value;
    }

    void next() {
        this.current = this.current.next;
    }

    void previous() {
        this.current = this.current.prev;
    }
}

class VerticalPitch extends JPanel {
    private int width;
    private int height;
    private ArrayList<Player> players;
    private Color sideColour = Color.BLUE;
    private int rectX, rectY, rectW, rectH;

    VerticalPitch() {
        setPreferredSize(new Dimension(ScreenSize.width, ScreenSize.height));
        setOpaque(false);
        this.height = ScreenSize.height / 2 + 130;
        this.width = (int) (this.height * 0.65);
        players = new ArrayList<>();

        rectX = ScreenSize.width - width - 50;
        rectY = height - 310;
        rectW = width;
        rectH = height;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(50, 150, 50));
        g.fillRect(rectX, rectY, rectW, rectH);

        g.setColor(Color.WHITE);
        g.drawRect(rectX, rectY, rectW, rectH);
        g.drawLine(rectX, rectY + rectH / 2, rectX + rectW, rectY + rectH / 2);

        int penaltySpan = (int) (rectW * 0.59);
        int penaltyDepth = (int) (rectH * 0.16);
        int penaltyX = rectX + (rectW - penaltySpan) / 2;
        g.drawRect(penaltyX, rectY, penaltySpan, penaltyDepth);
        g.drawRect(penaltyX, rectY + rectH - penaltyDepth, penaltySpan, penaltyDepth);

        int centerX = rectX + rectW / 2;
        int centerY = rectY + rectH / 2;
        int circleRadius = (int) (rectW * 0.135);
        g.drawOval(centerX - circleRadius, centerY - circleRadius, circleRadius * 2, circleRadius * 2);
        g.fillOval(centerX - 4, centerY - 4, 8, 8);

        int goalSpan = (int) (rectW * 0.27);
        int goalDepth = (int) (rectH * 0.05);
        int goalX = rectX + (rectW - goalSpan) / 2;
        g.drawRect(goalX, rectY, goalSpan, goalDepth);
        g.drawRect(goalX, rectY + rectH - goalDepth, goalSpan, goalDepth);

        int spotOffset = (int) (rectH * 0.105);
        g.fillOval(centerX - 4, rectY + spotOffset - 4, 8, 8);
        g.fillOval(centerX - 4, rectY + rectH - spotOffset - 4, 8, 8);

        for (Player p : players) {
            p.draw(g);
        }
    }

    private void addPlayer(int jerseyNumber, double nx, double ny) {
        int x = rectX + (int) (nx * rectW);
        int y = rectY + (int) (ny * rectH);
        players.add(new Player(x, y, jerseyNumber, sideColour));
    }

    void drawPlayers(UIWindow window) {
        players.clear(); // avoid stacking duplicates on repeated calls

        addPlayer(1, 0.5, 0.92); // goalkeeper
        int jersey = 2;
        for (int[] pos : window.formationCircle.getCurrent().getPositions()) {
            addPlayer(jersey++, 0.5 + pos[1] / 430.0, 0.92 - pos[0] / 600.0);
        }
        repaint();
    }

    void swapSides(UIWindow window) {
        if (window.sidesCircle.getCurrent().equals("Home")) {
            sideColour = Color.RED;
            window.sidesCircle.next();
        } else {
            sideColour = Color.BLUE;
            window.sidesCircle.next();
        }

        // keep each team's formation when switching sides
        window.formationCircle = window.sidesCircle.getCurrent().equals("Home")
                ? window.homeFormationCircle
                : window.awayFormationCircle;

    }

}

public class SimulationUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UIWindow().start());
    }

}
