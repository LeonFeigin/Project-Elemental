package WorldCreation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

import WorldCreation.mainPanel2;

public class mainProg2 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Project Elemental WORLD CREATION");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        mainPanel2 myPanel = new mainPanel2();
        myPanel.setPreferredSize(new Dimension(1280, 720));

        frame.add(myPanel);
        frame.pack(); // Sizes frame so content is 1280x720

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}