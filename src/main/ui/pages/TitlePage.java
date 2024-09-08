package ui.pages;

import model.Coach;
import model.EventLog;
import ui.panels.TitlePanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//represents the title page of the app
public class TitlePage extends JFrame {

    public TitlePage(Coach coach) {
        super("StatTracker");
        setSize(new Dimension(700,400));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                coach.printEvents(EventLog.getInstance());
                System.exit(0);
            }
        });
        add(new TitlePanel(coach, this), BorderLayout.NORTH);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
        setVisible(true);
        this.repaint();
        this.revalidate();
    }
}


