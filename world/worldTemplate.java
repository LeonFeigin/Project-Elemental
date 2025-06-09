package world;

import java.awt.*;
import java.awt.RenderingHints.Key;

import javax.swing.*;

import enemy.enemyTemplate;
import player.playerTemplate;
import ui.mainUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

    public int[][] collideTiles = {
        {0}
    };


    public int worldXOffset = 0;
    public int worldYOffset = 0;

    public playerTemplate currentPlayer;

    public ui.mainUI currentUI;

    public boolean debugMode = false;

    public ArrayList<enemyTemplate> enemies = new ArrayList<>();

    private boolean AisPressed = false;
    private boolean DisPressed = false;
    private boolean WisPressed = false;
    private boolean SisPressed = false;

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

    public void setCollideTiles(int[][] newWorld) {
        this.collideTiles = newWorld;
    }

    public void setCurrentPlayer(playerTemplate player) {
        this.currentPlayer = player;
    }

    public ArrayList<enemyTemplate> getEnemies() {
        return enemies;
    }

    public int[][] getCollideTiles() {
        return collideTiles;
    }
    
    public boolean isColliding(float x, float y){
        for (int i = 0; i < collideTiles.length; i++) {
            for (int j = 0; j < collideTiles[i].length; j++) {
                if( collideTiles[i][j] == 1) {
                    if (x > j * 32-1 && x < j * 32 + 33 && y > i * 32-1 && y < i * 32 + 33) {
                        return true;
                    }
                }
            }
        }
        return false;
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
        if(currentUI.isInMenu()){
            return;
        }
        currentUI.update(); // Update the UI
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //draw a white background
        g.setColor(Color.BLACK);
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
        if(e.getX() > 10 && e.getX() < 10 + 32 && e.getY() > 10 && e.getY() < 10 + 32) {
            currentUI.setInMenu(!currentUI.isInMenu()); // Toggle menu state
            System.out.println("Menu toggled: " + currentUI.isInMenu());
            return;
        }
        if(!currentUI.isInMenu()){
            currentPlayer.attack(e.getX()+worldXOffset, e.getY()+worldYOffset); // Attack at the mouse click position
        }
        
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
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0); // Exit the game
        }

        if(e.getKeyCode() == KeyEvent.VK_F3){
            debugMode = !debugMode; // Toggle debug mode
            if (debugMode) {
                System.out.println("Debug mode enabled");
            } else {
                System.out.println("Debug mode disabled");
            }
        }

        if(!currentUI.isInMenu()) {
            if(e.getKeyCode() == KeyEvent.VK_W) {
                currentPlayer.yVel = -5; // Move up
                WisPressed = true; // Set W key as pressed
            } else if(e.getKeyCode() == KeyEvent.VK_S) {
                currentPlayer.yVel = 5; // Move down
                SisPressed = true; // Set S key as pressed
            } else if(e.getKeyCode() == KeyEvent.VK_A) {
                currentPlayer.xVel = -5; // Move left
                AisPressed = true; // Set A key as pressed
            } else if(e.getKeyCode() == KeyEvent.VK_D) {
                currentPlayer.xVel = 5; // Move right
                DisPressed = true; // Set D key as pressed
            }

            if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
                currentPlayer.speed = 3; // Increase speed
            }

            if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
                currentPlayer.speed = 1; // Decrease speed
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W) {
            if(SisPressed) {
                currentPlayer.yVel = 5; // If S is pressed, move down
            } else {
                currentPlayer.yVel = 0; // Stop moving up
            }
            WisPressed = false; // Reset W key pressed state
        } else if(e.getKeyCode() == KeyEvent.VK_S) {
            if(WisPressed) {
                currentPlayer.yVel = -5; // If S is pressed, move down
            } else {
                currentPlayer.yVel = 0; // Stop moving up
            }
            SisPressed = false; // Reset S key pressed state
        } else if(e.getKeyCode() == KeyEvent.VK_A) {
            if(DisPressed) {
                currentPlayer.xVel = 5; // If S is pressed, move down
            } else {
                currentPlayer.xVel = 0; // Stop moving up
            }
            AisPressed = false; // Reset A key pressed state
        } else if(e.getKeyCode() == KeyEvent.VK_D) {
            if(AisPressed) {
                currentPlayer.xVel = -5; // If S is pressed, move down
            } else {
                currentPlayer.xVel = 0; // Stop moving up
            }
            DisPressed = false; // Reset D key pressed state
        }

        if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
            currentPlayer.speed = 2; // Decrease speed
        }

        if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
            currentPlayer.speed = 2; // Increase speed
        }
    }
}
