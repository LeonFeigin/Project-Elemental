package world;

import java.awt.*;

import javax.swing.*;

import attack.bullet;
import audio.audioPlayer;
import enemy.enemySpawner;
import enemy.enemyTemplate;
import player.playerFire;
import player.playerSwitch;
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
import java.util.Scanner;

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

    public int mouseX = 0;
    public int mouseY= 0;

    public playerTemplate currentPlayer;

    public playerSwitch playerSwitch;
    private long timeRemaining = 0; // Time remaining for player switch cooldown
    private int attackTimeRemaining = 0; // Time remaining for attack cooldown
    private int specialAttackTimeRemaining = 0; // Time remaining for special attack cooldown

    public ui.mainUI currentUI;

    public boolean debugMode = false;

    public ArrayList<enemyTemplate> enemies = new ArrayList<>();

    public ArrayList<bullet> bullets = new ArrayList<>(); // List to hold bullets fired by the attack

    public ArrayList<enemySpawner> enemySpawners = new ArrayList<>(); // List to hold enemy spawners

    private boolean AisPressed = false;
    private boolean DisPressed = false;
    private boolean WisPressed = false;
    private boolean SisPressed = false;

    public audioPlayer audioPlayer; // Audio player instance

    public worldTemplate(int[][] grassTilesWorld, int[][] pathTilesWorld, int[][] collideTiles) {
        //get grass tiles
        getImages("world/tileset/grass/", grassTiles, 32, 77);
        getImages("world/tileset/path/", pathTiles, 32, 77);
        playerSwitch = new playerSwitch(this);

        this.grassTilesWorld = grassTilesWorld;
        this.pathTilesWorld = pathTilesWorld;
        this.collideTiles = collideTiles;

        this.audioPlayer = new audioPlayer(); // Initialize the audio player

        playAmbientSound();
    }

    public static int[][] loadAWorld(String pathToFile){
        try{
            File file = new File(pathToFile);
            Scanner scan = new Scanner(file);
            int rows = 0;
            while(scan.hasNextLine()) {
                scan.nextLine();
                rows++;
            }
            scan.close();
            int[][] world = new int[rows][];
            scan = new Scanner(file);
            for (int i = 0; i < rows; i++) {
                String line = scan.nextLine(); // Remove the brackets
                line = line.substring(1, line.length() - 1);
                String[] numbers = line.split(",");
                world[i] = new int[numbers.length];
                for (int j = 0; j < numbers.length; j++) {
                    world[i][j] = Integer.parseInt(numbers[j].replace(" ", ""));
                }
            }
            scan.close();
            return world;
        }catch(IOException ex) {
        }
        return null;
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

    public void setMousePosition(int x, int y) {
        this.mouseX = x;
        this.mouseY = y;
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

    public void quitGame() {
        System.exit(0); // Exit the game
    }
    
    public boolean isColliding(float x, float y, boolean isEnemy){
        for (int i = 0; i < collideTiles.length; i++) {
            for (int j = 0; j < collideTiles[i].length; j++) {
                if(isEnemy){
                    if(collideTiles[i][j] == 0 || collideTiles[i][j] == 1){
                        if (x > j * 32-1 && x < j * 32 + 33 && y > i * 32-1 && y < i * 32 + 33) {
                            return true;
                        }
                    }
                }else{
                    if(collideTiles[i][j] == 0){
                        if (x > j * 32-1 && x < j * 32 + 33 && y > i * 32-1 && y < i * 32 + 33) {
                            return true;
                        }
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
        }
    }

    public void update() {
        audioPlayer.update(); // Update the audio player

        if(currentUI.isInMenu()){
            if(timeRemaining == 0) {
                timeRemaining = (long)playerSwitch.timeRemaining(); // Set the cooldown time when entering the menu
            }
            if(attackTimeRemaining == 0){
                attackTimeRemaining = (int)currentPlayer.attack.getCooldownRemaming(); // Set the cooldown time for attack when entering the menu
            }
            if(specialAttackTimeRemaining == 0){
                specialAttackTimeRemaining = (int)currentPlayer.specialAttack.getCooldownRemaming(); // Set the cooldown time for special attack when entering the menu
            }
            playerSwitch.setTimeRemaining(timeRemaining); // Reset the cooldown timer when in menu
            currentPlayer.specialAttack.setCooldownRemaining(specialAttackTimeRemaining); // Reset the special attack cooldown when in menu
            currentPlayer.attack.setCooldownRemaining(attackTimeRemaining); // Reset the attack cooldown when in menu
            currentUI.update();
            return;
        }
        
        timeRemaining = 0;
        attackTimeRemaining = 0;
        specialAttackTimeRemaining = 0;

        currentUI.update(); // Update the UI
        currentPlayer.update();
        for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
        }

        for (int i = 0; i < enemySpawners.size(); i++) {
            enemySpawners.get(i).update(); // Update each enemy spawner
        }
    }

    public void playAmbientSound() {
        audioPlayer.playAudioForever("worldAudio"); // Play ambient sound
    }

    public void paintComponent(Graphics g) {
        //draw a black background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1280, 720);

        drawTiles(g, grassTilesWorld, grassTiles);
        drawTiles(g, pathTilesWorld, pathTiles);
        
        //draw player
        currentPlayer.draw(g,worldXOffset, worldYOffset);

        //draw enemy
        for(enemyTemplate enemy : enemies) {
            enemy.draw(g, worldXOffset, worldYOffset);
        }

        //draw UI
        currentUI.draw(g);
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
        currentUI.mousePressed(e.getX(), e.getY()); // Handle mouse press in the UI
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!currentUI.isInMenu()){
            if(e.getButton() == MouseEvent.BUTTON1){
                currentPlayer.attack(e.getX()+worldXOffset, e.getY()+worldYOffset); // Attack at the mouse click position
            }else if(e.getButton() == MouseEvent.BUTTON3){
                currentPlayer.specialAttack(e.getX()+worldXOffset, e.getY()+worldYOffset);
            }
            if(e.getX() > 10 && e.getX() < 10 + 32 && e.getY() > 10 && e.getY() < 10 + 32) {
                currentUI.setInMenu(true); // Open the menu if the settings icon is clicked
                currentUI.inPauseMenu = true;
                return;
            }
        }else{
            currentUI.mouseClicked(e.getX(), e.getY()); // Handle mouse press in the UI
        }
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE && !currentUI.deathMenu && !currentUI.winMenu && !currentUI.inInventory) {
            currentUI.setInMenu(!currentUI.isInMenu()); // Toggle menu state
            currentUI.inPauseMenu = currentUI.isInMenu(); // Reset pause menu state when toggling menu
        }

        if(e.getKeyCode() == KeyEvent.VK_E && !currentUI.deathMenu && !currentUI.winMenu && !currentUI.inPauseMenu) {
                currentUI.setInMenu(!currentUI.isInMenu()); // Toggle menu state
                currentUI.inInventory = currentUI.isInMenu(); // Reset pause menu state when toggling menu
            }

        if(debugMode){
            if(e.getKeyCode() == KeyEvent.VK_G) {
                currentPlayer.toggleGodMode(); // Toggle god mode
                System.out.println("God Mode Toggled");
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_F3){
            debugMode = !debugMode; // Toggle debug mode
            if (debugMode) {
                System.out.println("Debug mode enabled (F3)");
            } else {
                System.out.println("Debug mode disabled (F3)");
            }
        }

        if(!currentUI.isInMenu()) {
            if(e.getKeyCode() >= '1' && e.getKeyCode() <= '4') {
                int playerType = e.getKeyCode() - '1'   ; // Convert key code to player type (1-5)
                playerSwitch.switchPlayer(playerType, false); // Switch player based on key pressed
            }

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
