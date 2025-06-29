import javax.swing.JFrame;

import java.awt.Dimension;

public class mainProg {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Project Elemental");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        mainPanel myPanel = new mainPanel();
        myPanel.setPreferredSize(new Dimension(1280, 720));

        frame.add(myPanel);
        frame.pack(); // Sizes frame so content is 1280x720

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}