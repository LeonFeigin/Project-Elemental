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
    
    public BufferedImage[] grassTiles = new BufferedImage[77];
    public BufferedImage[] pathTiles = new BufferedImage[77];

    public int[][] grassTilesWorld = {
        {0}
    };

    public int[][] pathTilesWorld = {
        {0}
    };

    public int worldXOffset = 0;
    public int worldYOffset = 0;

    public worldTemplate() {
        //get grass tiles
        getImages("world/tileset/grass/", grassTiles, 32, 77);
        getImages("world/tileset/path/", pathTiles, 32, 77);
    }

    public int getWorldWidth() {
        int longestRow = 0;
        for (int i = 0; i < grassTilesWorld.length; i++) {
            if (grassTilesWorld[i].length > longestRow) {
                longestRow = grassTilesWorld[i].length;
            }
        }
        return longestRow * 32;
    }
    public int getWorldHeight() {
        return grassTilesWorld.length * 32;
    }

    public void setGrassTilesWorld(int[][] newWorld) {
        this.grassTilesWorld = newWorld;
    }

    public void setPathTilesWorld(int[][] newWorld) {
        this.pathTilesWorld = newWorld;
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
        
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //draw a white background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1280, 720);
        
    }

    public void drawTiles(Graphics g, int[][] tilesWorld, BufferedImage[] tiles) {
        for (int y = 0; y < tilesWorld.length; y++) {
            for (int x = 0; x < tilesWorld[y].length; x++) {
                int tileIndex = tilesWorld[y][x];
                if (tileIndex >= 0 && tileIndex < tiles.length) {
                    BufferedImage tileImage = tiles[tileIndex];
                    g.drawImage(tileImage, x * 32-worldXOffset, y * 32-worldYOffset, null);
                }
            }
        }
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
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
