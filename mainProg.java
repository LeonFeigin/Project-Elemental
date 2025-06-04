import javax.swing.JFrame;

public class mainProg {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setLocation(100,100);

        mainPanel myPanel = new mainPanel();

        frame.add(myPanel);

        frame.setVisible(true);
    }
}