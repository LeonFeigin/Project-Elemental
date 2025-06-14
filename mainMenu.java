import world.worldTemplate;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import enemy.enemyTemplate;
import player.playerWater;
import player.sprite;
import ui.mainUI;

import java.awt.event.MouseEvent;

public class mainMenu extends worldTemplate implements KeyListener, MouseListener{

    mainPanel currentPanel; // Reference to the main panel

    private BufferedImage[] buttons = new BufferedImage[3]; // 0 = play, 1 = settings, 2 = exit
    private BufferedImage title;

    private boolean settingsOpen = false; // Flag to check if settings are open
    private BufferedImage settingsImage;
    
    public mainMenu(mainPanel panel){
        super(worldTemplate.loadAWorld("world/mainMenuTiles/grassTilesWorld.txt"),worldTemplate.loadAWorld("world/mainMenuTiles/pathTilesWorld.txt"),worldTemplate.loadAWorld("world/mainMenuTiles/collisionWorld.txt")); // No tiles or collision for the main menu
        
        this.currentPanel = panel; // Set the reference to the main panel

        setCurrentPlayer(new playerWater(0, 0, this, null, null));

        sprite.getImages("world/tileset/button/", buttons, 200,133, 3); // Load the button images for play, settings, and exit 
        title = sprite.getImages("world/tileset/button/title/", 215,55); // Load the title image (12.5x : 1y)

        settingsImage = sprite.getImages("world/tileset/button/settings/", 600,570);

        currentUI = new mainUI(this);
        //892 362
        enemySpawners.add(new enemy.enemySpawner(892, 362, (int)Math.round(Math.random()*6), this, 1, (int)Math.round(Math.random()*6)+5, 200)); // Example enemy spawner
    }

    @Override
    public void paintComponent(Graphics g) {
        drawTiles(g, grassTilesWorld, grassTiles);
        drawTiles(g, pathTilesWorld, pathTiles);

        for(enemyTemplate enemy : enemies) {
            enemy.draw(g, worldXOffset, worldYOffset);
        }

        for (int i = 0; i < 3; i++) {
            g.drawImage(buttons[i], 170, 200+150*i, null);
        }

        g.drawImage(title, 165, 120, null);

        if(settingsOpen) {
            drawSettings(g, 597, 84);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            //play button
            if(e.getX() >= 170 && e.getX() <= 370 && e.getY() >= 200 && e.getY() <= 333) {
                currentPanel.setWorld(new starterWorld(currentPanel, 3)); // change to the starter world
            }

            //settings button
            else if (e.getX() >= 170 && e.getX() <= 370 && e.getY() >= 350 && e.getY() <= 480) {
                settingsOpen = !settingsOpen;
            }

            //exit button
            else if (e.getX() >= 170 && e.getX() <= 370 && e.getY() >= 500 && e.getY() <= 633) {
                quitGame();
            }

            if(settingsOpen){
                if(e.getX() >= 1150 && e.getX() <= 1182 && e.getY() >= 97 && e.getY() <= 125) {
                    settingsOpen = false; // Close the settings
                }
            }else{ // let the player kill the enemies in the main menu
                for (int i = 0; i < enemies.size(); i++) {
                    enemyTemplate enemy = enemies.get(i);
                    if (e.getX() >= enemy.x && e.getX() <= enemy.x + 32 && e.getY() >= enemy.y && e.getY() <= enemy.y + 32) {
                        enemy.takeDamage(enemy.getMaxHealth() / 10); // Deal a lot of damage to the enemy
                        if (enemy.health <= 0) {
                            enemies.remove(i); // Remove the enemy if it is dead
                            i--; // Adjust index after removal
                        }
                    }
                }
            }


        }

        System.out.println("Mouse Released at: " + e.getX() + ", " + e.getY());
    }

    public void drawSettings(Graphics g, int x, int y) {
        g.drawImage(settingsImage, x, y, null);
    }
}
