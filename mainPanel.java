import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import world.starterWorld;
import world.testWorld;
import world.worldTemplate;

public class mainPanel extends JPanel implements MouseListener, KeyListener{
    
    int stage = 0;

    worldTemplate currentWorld;

    long startTime = System.currentTimeMillis();
    int frames = 0;

    Timer timer;
    public mainPanel(){
        setWorld(new starterWorld());

        timer = new Timer(1, e -> {
            currentWorld.update();

            repaint();
        });
        timer.start();
    }

    public void setWorld(worldTemplate world) {
        this.currentWorld = world;
        addKeyListener(currentWorld);
        addMouseListener(currentWorld);
        setFocusable(true);
        currentWorld.requestFocusInWindow();

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
