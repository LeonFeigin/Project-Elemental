package world;

import java.awt.*;
import javax.swing.*;

import player.player;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class worldTemplate extends JPanel implements KeyListener, MouseListener {
    
    private BufferedImage[] tiles = new BufferedImage[77];

    private int[][] grassTiles = {
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
    };


    player myPlayer = new player();

    public worldTemplate(){
        //get grass tiles
        for (int i = 0; i < 77; i++) {
            try {
                BufferedImage original = ImageIO.read(new File("world/tileset/grass/" + i + ".png"));
                BufferedImage scaled = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
                double scaleX = 64.0 / original.getWidth();
                double scaleY = 64.0 / original.getHeight();
                AffineTransform at = AffineTransform.getScaleInstance(scaleX, scaleY);
                AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                scaleOp.filter(original, scaled);
                this.tiles[i] = scaled;
            } catch (IOException ex) {
                System.out.println("File not found! " + "world/tileset/grass/" + i + ".png");
            }
        }


    }

    public void update() {
        // Update logic for the world can be added here
        myPlayer.update();
        



    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
        
        // Draw the tiles
        for (int y = 0; y < grassTiles.length; y++) {
            for (int x = 0; x < grassTiles[y].length; x++) {
                int tileIndex = grassTiles[y][x];
                if (tileIndex >= 0 && tileIndex < tiles.length) {
                    BufferedImage tileImage = tiles[tileIndex];
                    g.drawImage(tileImage, x * 64, y * 64, null);
                }
            }
        }

        //draw player
        myPlayer.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W) {
            myPlayer.yVel = -5; // Move up
        } else if(e.getKeyCode() == KeyEvent.VK_S) {
            myPlayer.yVel = 5; // Move down
        } else if(e.getKeyCode() == KeyEvent.VK_A) {
            myPlayer.xVel = -5; // Move left
        } else if(e.getKeyCode() == KeyEvent.VK_D) {
            myPlayer.xVel = 5; // Move right
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W) {
            myPlayer.yVel = 0; // Move up
        } else if(e.getKeyCode() == KeyEvent.VK_S) {
            myPlayer.yVel = 0; // Move down
        } else if(e.getKeyCode() == KeyEvent.VK_A) {
            myPlayer.xVel = 0; // Move left
        } else if(e.getKeyCode() == KeyEvent.VK_D) {
            myPlayer.xVel = 0; // Move right
        }
    }
}
