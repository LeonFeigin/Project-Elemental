package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import player.playerSwitch;
import player.sprite;
import world.worldTemplate;

public class mainUI {
    
    worldTemplate currentWorld;

    int frames = 0;
    long startTime = System.currentTimeMillis();

    private boolean inMenu = false; // Whether the UI is in the menu state
    
    public boolean inPauseMenu = false; // Whether the UI is in the pause menu state
    public boolean deathMenu = false; // Whether the UI is in the death menu state
    public boolean winMenu = false; // only used in the boss world
    public boolean inInventory = false; // Whether the UI is in the inventory state

    private int delayedPlayerHealth; // For delayed health updates
    private BufferedImage[] healthBarImages = new BufferedImage[3]; // 0 = background, 1 = red bar (health)
    private BufferedImage healthBaseBackground;

    // 0 = x, 1 = check mark, 2 = question mark, 3 = plus, 4 = minus, 5 = settings, 6 = cancel, 7 = power button
    private BufferedImage[] icons = new BufferedImage[8];
    private BufferedImage pauseBackground;

    private BufferedImage inventoryImage; // Image for the inventory UI

    private BufferedImage deathMenuImage;

    public mainUI(worldTemplate currentWorld) {
        // Initialize the UI with the current world
        this.currentWorld = currentWorld;

        // Load health bar images (4x:1y aspect ratio)
        // 0 = background, 1 = red bar (health), 2 = yellow bar (delayed health)
        sprite.getImages("ui/healthBar/", healthBarImages, 256, 64, 3);

        // Load base background image (1x:1y aspect ratio)
        healthBaseBackground = sprite.getImages("ui/base/", 90, 90);
        
        // Load base background image
        pauseBackground = sprite.getImages("ui/pauseMenu/", 1080, 520);

        // Load death menu image
        deathMenuImage = sprite.getImages("ui/deathMenu/", 1080, 520);

        inventoryImage = sprite.getImages("ui/inventory/", 1080, 520);

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
    }

    public void update(){
        frames++;
        if(delayedPlayerHealth > currentWorld.currentPlayer.getHealth()) {
            delayedPlayerHealth -= currentWorld.currentPlayer.getMaxHealth() / 100; // Decrease the delayed health by 10% of max health
        }
        if(inInventory){
            currentWorld.currentPlayer.inventory.update(); // Update the inventory if in inventory state
        }
    }

    public void draw(Graphics g){
        drawHealthBar(g, 90, 30);
        drawPlayerSelection(g,40, 160);
        drawCurrentPlayerStats(g, 115, 40);
        drawPlayerSpecial(g, 63, 657); // Draw the player's special ability circle

        // Draw the base background
        g.drawImage(healthBaseBackground, 20, 20, null);
        currentWorld.currentPlayer.draw(g, 64,64, 32,32); // Draw the current player

        // Draw settings icon
        g.drawImage(icons[5], 10, 10, 32,32, null);

        if(inMenu){
            if(inPauseMenu){
                drawPauseMenu(g); // Draw the pause menu if in pause menu state
            }
            else if(deathMenu){
                drawDeathScreen(g);
            }
            else if(winMenu){
                drawWinScreen(g);
            }else if(inInventory){
                drawInventory(g); // Draw the inventory if in inventory state
            }
        }

        //all debugging information
        if(currentWorld.debugMode){
            drawDebug(g);
        }
    }

    public void drawPlayerSpecial(Graphics g, int x, int y){
        g.setColor(Color.BLACK);
        g.drawOval(x-75/2, y-75/2, 75, 75);
        g.setColor(new Color(0,0,0,100));
        g.fillArc(x-75/2, y-75/2, 75, 75,0,(int)(360*(currentWorld.currentPlayer.getSpecialAttackCooldownRemaining()/(float)currentWorld.currentPlayer.getSpecialCooldown())));
    }

    public void drawCurrentPlayerStats(Graphics g, int x, int y){
        // Draw the current player's stats
        g.setColor(Color.BLACK);
        g.setFont(g.getFont().deriveFont(16f));
        g.drawString(currentWorld.currentPlayer.getPlayerName(), x, y);
        g.drawString("Attack Damage: " + currentWorld.currentPlayer.getAttackDamage(), x, y + 50);
        g.drawString("Reload Time:" + Math.max(Math.round(currentWorld.currentPlayer.getAttackCooldown()/1000.0*100.0)/100.0, 0) + "s", x, y+70);
    }

    public void drawPauseMenu(Graphics g){
        // Draw a semi-transparent background for the pause menu
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 1280, 720);

        g.drawImage(pauseBackground, 100, 100, 1080, 520,null);
    }

    public void drawInventory(Graphics g){
        // Draw a semi-transparent background for the pause menu
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 1280, 720);

        g.drawImage(inventoryImage, 100, 100, 1080, 520,null);

        currentWorld.currentPlayer.draw(g, 503, 170, 64,64);

        currentWorld.currentPlayer.inventory.draw(g);

        int damageBoost = 0;
        int healthBoost = 0;
        float attackSpeedBoost = 0;
        for (int i = 0; i < currentWorld.currentPlayer.inventory.getEquipt().length; i++) {
            if(currentWorld.currentPlayer.inventory.getEquipt()[i] != null) {
                damageBoost += currentWorld.currentPlayer.inventory.getEquipt()[i].getDamageBoost();
                healthBoost += currentWorld.currentPlayer.inventory.getEquipt()[i].getHealthBoost();
                attackSpeedBoost += currentWorld.currentPlayer.inventory.getEquipt()[i].getAttackSpeedBoost();
            }
        }
        g.setColor(Color.BLACK);
        g.setFont(g.getFont().deriveFont(16f));
        g.drawString("Damage Boost: " + damageBoost, 215, 170);
        g.drawString("Health Boost: " + healthBoost, 215, 190);
        g.drawString("Attack Speed Boost: " + Math.round(attackSpeedBoost*100)/100.0, 215, 210);
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
            
            drawHealthBar(g, x+75, y+100*i+20, 128, 32, currentWorld.playerSwitch.currentPlayerSelection[i]);

            if(playerImage != null){
                g.drawImage(playerImage, (int)(x-16+75/2f), (int)(y+100*i-16+75/2f), null);
            }
            g.setColor(new Color(0,0,0,100));
            g.fillArc(x, y+100*i, 75, 75,0,(int)(360*(currentWorld.playerSwitch.timeRemaining()/playerSwitch.coolddown)));
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

    public void drawHealthBar(Graphics g,int x, int y, int xSize, int ySize, int playerId) {
        // Player Health
        g.drawImage(healthBarImages[0], x, y, x + xSize, y + ySize, 0, 0, 256, 64, null); // Draw health bar background

        // Draw the current health (red bar)
        int healthWidth = (int)(xSize * ((float)currentWorld.playerSwitch.playerHealths[playerId] / currentWorld.playerSwitch.playerMaxHealths[playerId]));
        g.drawImage(healthBarImages[1], x, y, x + healthWidth, y + ySize, 0, 0, (int)(256 * ((float)currentWorld.playerSwitch.playerHealths[playerId] / currentWorld.playerSwitch.playerMaxHealths[playerId])), 64, null);
    }
    
    public void drawDeathScreen(Graphics g){
        // Draw a semi-transparent background for the pause menu
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 1280, 720);

        g.drawImage(deathMenuImage, 100, 100, 1080, 520,null);
    }

    public void drawWinScreen(Graphics g){
        // Draw a semi-transparent background for the pause menu
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 1280, 720);

        g.drawImage(sprite.getImages("ui/winMenu/", 1080, 520), 100, 100, null);
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
            g.setColor(new Color(0,100,0));
            g.fillOval(currentWorld.enemySpawners.get(i).getX(), currentWorld.enemySpawners.get(i).getY(), 25, 25);
            g.drawOval(currentWorld.enemySpawners.get(i).getX() - currentWorld.worldXOffset - currentWorld.enemySpawners.get(i).getRadius(), currentWorld.enemySpawners.get(i).getY() - currentWorld.worldYOffset - currentWorld.enemySpawners.get(i).getRadius(), currentWorld.enemySpawners.get(i).getRadius() * 2,  currentWorld.enemySpawners.get(i).getRadius() * 2);
        }
    }

    public void mousePressed(int x, int y){
        if(inMenu){
            if(inInventory){
                currentWorld.currentPlayer.inventory.mousePressed(x, y); // Handle inventory mouse press
            }
        }
    }

    public void mouseClicked(int x, int y) {
        if(inPauseMenu){
            if(x > 350 && x < 540 && y > 440 && y < 570){ // resume button
                inMenu = false; // Exit the menu
                inPauseMenu = false; // Reset pause menu state
            }else if(x > 740 && x < 930 && y > 440 && y < 570){ // settings button
                currentWorld.quitGame();
            }
        }else if(deathMenu){
            if(x > 547 && x < 734 && y > 443 && y < 568){ // restart button
                for (int i = 0; i < 5; i++) {
                    try {
                        File file = new File("player/saves/"+currentWorld.playerSwitch.getPlayer(i).getPlayerName().replace(" ", "")+".txt");
                        if(file.exists()) {
                            file.delete();
                        }
                    } catch (Exception e) {
                        
                    }
                }
                currentWorld.quitGame();
            }
        }else if(winMenu){
            if(x > 546 && x < 735 && y > 445 && y < 563){ // restart button
                setInMenu(false);
                winMenu = false; // Reset win menu state
            }
        }else if(inInventory){
            currentWorld.currentPlayer.inventory.mouseReleased(x, y); // Handle inventory mouse press        
        }
    }
}
