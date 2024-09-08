package ui.pages;

import model.Coach;
import org.json.JSONException;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

//represents the very first page where the user is asked if they want to load page
public class LoadPage extends JFrame {

    private final JButton yes = new JButton("Yes");
    private final JButton no = new JButton("No");
    private Coach coach;
    private final JsonReader jsonReader;

    public LoadPage() {
        super("StatTracker");
        jsonReader = new JsonReader("./data/app.json");
        setSize(new Dimension(300,150));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel btnPanel = new JPanel();
        btnPanel.add(yes);
        addLogo(btnPanel);
        btnPanel.add(no);
        Panel loadPanel = new Panel();
        loadPanel.setBackground(Color.gray);
        no.setFont(new Font("Impact", Font.BOLD, 18));
        yes.setFont(new Font("Impact", Font.BOLD, 18));
        JLabel loadLabel = new JLabel("Would you like to load previous data?");
        loadLabel.setPreferredSize(new Dimension(250,25));
        loadPanel.add(loadLabel);
        callButtons();
        btnPanel.setBackground(Color.gray);
        add(loadPanel, BorderLayout.NORTH);
        add(btnPanel);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
        setVisible(true);
    }

    //EFFECTS: sets the logo of the frame
    private void addLogo(JPanel panel) {
        java.net.URL imageURL = getClass().getResource("../Images/StatTrackerLogo.jpeg");
        if (imageURL != null) {
            ImageIcon icon = new ImageIcon(imageURL);
            Image sizedIcon = icon.getImage();
            Image newing = sizedIcon.getScaledInstance(60,60, Image.SCALE_SMOOTH);
            icon = new ImageIcon(newing);
            JLabel iconLabel = new JLabel();
            iconLabel.setIcon(icon);
            panel.add(iconLabel);
        }
    }

    //EFFECTS: calls buttons
    private void callButtons() {
        setNoButton();
        setYesButton();
    }

    //EFFECTS: sets action listener for no button
    private void setNoButton() {
        no.addActionListener(e -> {
            setVisible(false);
            new WelcomePage();
        });
    }

    //EFFECTS: sets action listener for yes button
    private void setYesButton() {
        yes.addActionListener(e -> {
            try {
                coach = jsonReader.read();
                setVisible(false);
                new TitlePage(coach);
            } catch (IOException arg) {
                JOptionPane.showMessageDialog(null,"Unable to read from file ./data/app.json");
                setVisible(false);
                new WelcomePage();
            } catch (JSONException arg) {
                JOptionPane.showMessageDialog(null,"Unable to read data");
                setVisible(false);
                new WelcomePage();
            }
        });
    }
}
