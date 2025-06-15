import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import WorldCreation.mainPanel2;
import world.worldTemplate;

public class mainPanel extends JPanel implements MouseListener, KeyListener{
    
    int stage = 0;

    worldTemplate currentWorld;

    long startTime = System.currentTimeMillis();
    int frames = 0;

    Timer timer;
    public mainPanel(){
        setWorld(new mainMenu(this));

        timer = new Timer(8, e -> {
            currentWorld.update();

            if (mainPanel.this.isFocusOwner()) {
                currentWorld.setMousePosition((int)MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().x, (int)MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().y);
            }

            repaint();
        });
        timer.start();
    }

    public void setWorld(worldTemplate world) {
        //stop playing all audios
        if(currentWorld != null) {
            currentWorld.audioPlayer.stopPlaying();
        }

        //remove from all listeners
        removeKeyListener(currentWorld);
        removeMouseListener(currentWorld);

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
        
    }


    @Override
    public void keyPressed(KeyEvent e) {
        
    }


    @Override
    public void keyReleased(KeyEvent e) {
        
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        
    }


    @Override
    public void mousePressed(MouseEvent e) {
        
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        
    }


    @Override
    public void mouseExited(MouseEvent e) {
        
    }

}
