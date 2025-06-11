package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import player.sprite;
import world.worldTemplate;

public class mainUI {
    
    worldTemplate currentWorld;

    int frames = 0;
    long startTime = System.currentTimeMillis();

    private boolean inMenu = false; // Whether the UI is in the menu state

    private int delayedPlayerHealth; // For delayed health updates
    private BufferedImage[] healthBarImages = new BufferedImage[3]; // 0 = background, 1 = red bar (health)
    private BufferedImage healthBaseBackground;

    // 0 = x, 1 = check mark, 2 = question mark, 3 = plus, 4 = minus, 5 = settings, 6 = cancel, 7 = power button
    private BufferedImage[] icons = new BufferedImage[8];
    private BufferedImage pauseBackground;

    public mainUI(worldTemplate currentWorld) {
        // Initialize the UI with the current world
        this.currentWorld = currentWorld;

        // Load health bar images (4x:1y aspect ratio)
        // 0 = background, 1 = red bar (health), 2 = yellow bar (delayed health)
        sprite.getImages("ui/healthBar/", healthBarImages, 256, 64, 3);

        // Load base background image (1x:1u aspect ratio)
        healthBaseBackground = sprite.getImages("ui/base/", 90, 90);
        
        // Load base background image (1x:1u aspect ratio)
        pauseBackground = sprite.getImages("ui/pauseMenu/", 1080, 520);

        // Load icons (1x:1u aspect ratio)
        sprite.getImages("ui/icons/", icons, 32, 32, 8);


        // Initialize delayed player health to the player's max health
        delayedPlayerHealth = currentWorld.currentPlayer.getMaxHealth();
    }

    public boolean isInMenu() {
        return inMenu;
    }

    public void setInMenu(boolean inMenu) {
        this.inMenu = inMenu;
    }

    public void updateHealth(int health){
        // Update the delayed player health
        delayedPlayerHealth = health;
        System.out.println();
    }

    public void update(){
        frames++;
        if(delayedPlayerHealth > currentWorld.currentPlayer.getHealth()) {
            delayedPlayerHealth--;
        }
    }

    public void draw(Graphics g){
        drawHealthBar(g, 90, 20);
        drawPlayerSelection(g,40, 160);

        // Draw the base background
        g.drawImage(healthBaseBackground, 20, 20, null);

        // Draw settings icon
        g.drawImage(icons[5], 10, 10, 32,32, null);

        if(inMenu){
            drawPauseMenu(g); // Draw the pause menu if in menu state
        }

        //all debugging information
        if(currentWorld.debugMode){
            drawDebug(g);
        }
    }

    public void drawPauseMenu(Graphics g){
        // Draw a semi-transparent background for the pause menu
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 1280, 720);

        g.drawImage(pauseBackground, 100, 100, 1080, 520,null); // Draw health bar background
    }

    public void drawPlayerSelection(Graphics g, int x, int y) {
        for (int i = 0; i < 4; i++) {
            BufferedImage playerImage = null;
            switch(currentWorld.playerSwitch.currentPlayerSelection[i]){
                case(0):
                    playerImage = sprite.getImages("player/playerFireSprites/idle/", 32);
                    break;
                case(1):
                    playerImage = sprite.getImages("player/playerWaterSprites/idle/", 32);
                    break;
                case(2):
                    playerImage = sprite.getImages("player/playerEarthSprites/idle/", 32);
                    break;
                case(3):
                    playerImage = sprite.getImages("player/playerIceSprites/idle/", 32);
                    break;
                case(4):
                    playerImage = sprite.getImages("player/playerLightningSprites/idle/", 32);
                    break;
            }

            g.setColor(Color.black);
            g.drawOval(x, y+100*i, 75, 75);

            if(playerImage != null){
                g.drawImage(playerImage, (int)(x-16+75/2f), (int)(y+100*i-16+75/2f), null);
            }
            g.setColor(new Color(0,0,0,100));
            g.fillArc(x, y+100*i, 75, 75,0,(int)(360*(currentWorld.playerSwitch.timeRemaining()/currentWorld.playerSwitch.coolddown)));
        }
    }

    public void drawHealthBar(Graphics g,int x, int y) {
        //Player Health
        g.drawImage(healthBarImages[0], x, y, null); // Draw health bar background
        // Draw the filled part, cropped by health
        int healthWidth = (int)(256 * ((float)delayedPlayerHealth / currentWorld.currentPlayer.getMaxHealth()));
        g.drawImage(healthBarImages[2], x, y, x + healthWidth, y+64, 0, 0, healthWidth, 64, null); // only draw the filled part
        // Draw the filled part, cropped by health
        healthWidth = (int)(256 * ((float)currentWorld.currentPlayer.getHealth() / currentWorld.currentPlayer.getMaxHealth()));
        g.drawImage(healthBarImages[1], x, y, x + healthWidth, y+64, 0, 0, healthWidth, 64, null); // only draw the filled part
    }

    public void drawDebug(Graphics g) {
        //debug info
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 300, 100);
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(16f));
        g.drawString("Player Health: " + currentWorld.currentPlayer.getHealth(), 10, 20);
        g.drawString("Player Position: (" + currentWorld.currentPlayer.x + ", " + currentWorld.currentPlayer.y + ")", 10, 40);


        //calculate FPS
        frames++;
        long currentTime = System.currentTimeMillis();
        g.drawString("FPS: " + (frames * 1000 / (Math.max(currentTime - startTime,1))), 10, 60);

        if(currentTime - startTime >= 1000) {
            frames = 0;
            startTime = currentTime;
        }

        //collision render
        for (int i = 0; i < currentWorld.getCollideTiles().length; i++) {
            for (int j = 0; j < currentWorld.getCollideTiles()[i].length; j++) {
                if(currentWorld.getCollideTiles()[i][j] != -1) {
                    if(currentWorld.getCollideTiles()[i][j] == 1) {
                        g.setColor(Color.BLUE); // Enemy Collision tile
                    } else if (currentWorld.getCollideTiles()[i][j] == 0) {
                        g.setColor(Color.RED); // World Collision tile
                    }
                    g.drawRect(j * 32-currentWorld.worldXOffset, i * 32-currentWorld.worldYOffset, 32, 32);
                }
            }
        }

        //spawner render
        for (int i = 0; i < currentWorld.enemySpawners.size(); i++) {
            g.setColor(Color.GREEN);
            g.fillOval(currentWorld.enemySpawners.get(i).getX(), currentWorld.enemySpawners.get(i).getY(), 25, 25);
            g.drawOval(currentWorld.enemySpawners.get(i).getX() - currentWorld.worldXOffset - currentWorld.enemySpawners.get(i).getRadius(), currentWorld.enemySpawners.get(i).getY() - currentWorld.worldYOffset - currentWorld.enemySpawners.get(i).getRadius(), currentWorld.enemySpawners.get(i).getRadius() * 2,  currentWorld.enemySpawners.get(i).getRadius() * 2);
        }
    }

    public void mouseClicked(int x, int y) {
        if(x > 350 && x < 540 && y > 440 && y < 570){ // resume button
            inMenu = false; // Exit the menu
        }else if(x > 740 && x < 930 && y > 440 && y < 570){ // settings button
            currentWorld.quitGame();
        }
            
        System.out.println("Mouse clicked at: " + x + ", " + y);
    }
}
