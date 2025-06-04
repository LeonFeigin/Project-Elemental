import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextLayout;

import javax.swing.*;

import world.testWorld;
import world.worldTemplate;

public class mainPanel extends JPanel implements MouseListener, KeyListener{
    
    int stage = 0;

    worldTemplate currentWorld = new testWorld();

    long startTime = System.currentTimeMillis();
    int frames = 0;

    Timer timer;
    public mainPanel(){
        timer = new Timer(1, e -> {
            currentWorld.update();

            //calculate FPS
            frames++;
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime >= 1000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                startTime = currentTime;
            }

            repaint();
        });
        timer.start();

        addKeyListener(currentWorld);
        addMouseListener(currentWorld);
        setFocusable(true);
        requestFocusInWindow();
    }


    @Override
    public void paintComponent(Graphics g) {
        currentWorld.paintComponent(g);
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }


    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }


    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }


    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }


    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }

}
