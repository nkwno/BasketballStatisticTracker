package ui.pages;

import ui.panels.SurveyPanel;

import javax.swing.*;
import java.awt.*;


//represents a frame where the user did not load data or is using the app for the first time
public class WelcomePage extends JFrame {

    public WelcomePage() {
        super("StatTracker");
        setSize(new Dimension(700,400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel welcomeLabel = new JLabel("Welcome To StatTracker!");
        Panel titlePanel = new Panel();
        titlePanel.add(welcomeLabel);
        add(titlePanel, BorderLayout.NORTH);
        SurveyPanel surveyPanel = new SurveyPanel(this);
        add(surveyPanel);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
        setVisible(true);
    }
}
