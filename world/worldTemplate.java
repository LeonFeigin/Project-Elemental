package world;

import java.awt.*;
import javax.swing.*;

import player.player;

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
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
    };

    public final int worldWidth = 1472+16; //pixels
    public final int worldHeight = 720; //pixels


    player myPlayer = new player();

    public int worldXOffset = 0;
    public int worldYOffset = 0;

    public worldTemplate(){
        //get grass tiles
        getImages("world/tileset/grass/", tiles, 32, 77);
    }

    private void getImages(String direction, BufferedImage[] image,int size, int count) {
        try{
            for (int i = 0; i < count; i++) {
                BufferedImage original = ImageIO.read(new File(direction + i+ ".png"));
                BufferedImage scaled = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
                AffineTransform at = AffineTransform.getScaleInstance(size / original.getWidth(), size / original.getHeight());
                AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                scaleOp.filter(original, scaled);
                image[i] = scaled;
            }
        }catch (IOException ex) {
            System.out.println("File not found!");
        }
    }

    public void update() {
        // Update logic for the world can be added here
        myPlayer.update(this);
        



    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //draw a white background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1280, 720);
        
        // Draw the tiles
        for (int y = 0; y < grassTiles.length; y++) {
            for (int x = 0; x < grassTiles[y].length; x++) {
                int tileIndex = grassTiles[y][x];
                if (tileIndex >= 0 && tileIndex < tiles.length) {
                    BufferedImage tileImage = tiles[tileIndex];
                    g.drawImage(tileImage, x * 32-worldXOffset, y * 32-worldYOffset, null);
                }
            }
        }

        //draw player
        myPlayer.draw(g,worldXOffset, worldYOffset);
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

        if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
            myPlayer.speed = 2; // Increase speed
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

        if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
            myPlayer.speed = 1; // Increase speed
        }
    }
}
