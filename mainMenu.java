import world.worldTemplate;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import audio.audioPlayer;
import enemy.enemyTemplate;
import player.playerWater;
import player.sprite;
import ui.mainUI;

import java.awt.event.MouseEvent;

public class mainMenu extends worldTemplate implements KeyListener, MouseListener{

    mainPanel currentPanel; // Reference to the main panel

    private BufferedImage[] buttons = new BufferedImage[3]; // 0 = play, 1 = settings, 2 = exit
    private BufferedImage title;
    
    public mainMenu(mainPanel panel){
        super(worldTemplate.loadAWorld("world/mainMenuTiles/grassTilesWorld.txt"),worldTemplate.loadAWorld("world/mainMenuTiles/pathTilesWorld.txt"),worldTemplate.loadAWorld("world/mainMenuTiles/collisionWorld.txt")); // No tiles or collision for the main menu
        
        this.currentPanel = panel; // Set the reference to the main panel

        setCurrentPlayer(new playerWater(0, 0, this, null, null,null,0,0,2,2));

        sprite.getImages("world/tileset/button/", buttons, 200,133, 3); // Load the button images for play, settings, and exit 
        title = sprite.getImages("world/tileset/button/title/", 215,55); // Load the title image (12.5x : 1y)

        currentUI = new mainUI(this);

        enemySpawners.add(new enemy.enemySpawner(892, 362, (int)Math.round(Math.random()*6), this, 1, (int)Math.round(Math.random()*6)+5, 200)); // Example enemy spawner
    }

    @Override
    public void update() {
        // Update the enemies in the main menu
        for (enemyTemplate enemy : enemies) {
            enemy.update();
        }
        for (int i = 0; i < enemySpawners.size(); i++) {
            enemySpawners.get(i).update();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        drawTiles(g, grassTilesWorld, grassTiles);
        drawTiles(g, pathTilesWorld, pathTiles);

        for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).draw(g, worldXOffset, worldYOffset); // Draw each enemy in the main menu
        }

        g.drawImage(buttons[0], 170, 250, null);
        g.drawImage(buttons[2], 170, 450, null);

        g.drawImage(title, 165, 150, null);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // dont let the world do anything when a key is pressed in the main menu
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            //play button
            if(e.getX() >= 170 && e.getX() <= 370 && e.getY() >= 200 && e.getY() <= 333) {
                audioPlayer.playAudio("buttonClick"); // Play button click sound
                currentPanel.setWorld(new starterWorld(currentPanel, 3)); // change to the starter world
            }

            //exit button
            else if (e.getX() >= 170 && e.getX() <= 370 && e.getY() >= 500 && e.getY() <= 633) {
                audioPlayer.playAudio("buttonClick"); // Play button click sound
                quitGame();
            }

            for (int i = 0; i < enemies.size(); i++) {
                enemyTemplate enemy = enemies.get(i);
                if (e.getX() >= enemy.x && e.getX() <= enemy.x + 32 && e.getY() >= enemy.y && e.getY() <= enemy.y + 32) {
                    enemy.takeDamage(enemy.getMaxHealth() / 5); // Deal a lot of damage to the enemy
                    if (enemy.health <= 0) {
                        enemies.remove(i); // Remove the enemy if it is dead
                        i--; // Adjust index after removal
                    }
                }
            }
        }
    }
}
