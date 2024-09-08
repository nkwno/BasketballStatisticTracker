package ui.pages;

import model.Coach;
import model.EventLog;
import ui.panels.NewTeamPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//represents the page when user wants to add a new team
public class AddNewTeamPage extends JFrame {

    public AddNewTeamPage(Coach coach) {
        super("StatTracker");
        setSize(new Dimension(700,400));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                coach.printEvents(EventLog.getInstance());
                System.exit(0);
            }
        });
        add(new NewTeamPanel(this, coach));
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
        setVisible(true);
    }
}
