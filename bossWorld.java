import java.awt.Color;
import java.awt.Graphics;

import enemy.enemySpawner;
import player.playerWater;
import ui.mainUI;
import world.worldTemplate;
import java.awt.image.BufferedImage;
import player.sprite;

public class bossWorld extends worldTemplate {

    mainPanel myMainPanel;

    public boolean hasBossStarted = false;
    private boolean hasBossEnded = false; // To track if the boss fight has ended

    private int delayedBossHealth; // For delayed health updates
    private BufferedImage[] healthBarImages = new BufferedImage[3]; // 0 = background, 1 = red bar (health)
    private BufferedImage healthBaseBackground;

    public bossWorld(mainPanel myMainPanel) {
        super(worldTemplate.loadAWorld("world/bossWorldTiles/grassTilesWorld.txt"),worldTemplate.loadAWorld("world/bossWorldTiles/pathTilesWorld.txt"),worldTemplate.loadAWorld("world/bossWorldTiles/collisionWorld.txt"));
        setCurrentPlayer(new playerWater(2163, 2744, this, null, null)); //60, 615
        currentUI = new mainUI(this);
        currentUI.updateHealth(currentPlayer.getHealth());

        this.myMainPanel = myMainPanel;

        // Load health bar images (4x:1y aspect ratio)
        // 0 = background, 1 = red bar (health), 2 = yellow bar (delayed health)
        sprite.getImages("ui/healthBar/", healthBarImages, 256, 64, 3);

        // Load base background image (1x:1u aspect ratio)
        healthBaseBackground = sprite.getImages("ui/base/", 90, 90);
    }

    @Override
    public void update() {
        super.update();
        if(currentPlayer.y > 2800){
            myMainPanel.setWorld(new starterWorld(myMainPanel, 2));
        }
        if(!hasBossStarted){
            if(currentPlayer.y < 2328){
                hasBossStarted = true;
                //change the world when boss spawns
                setGrassTilesWorld(worldTemplate.loadAWorld("world/bossWorldTiles/bossWorld/grassTilesWorld.txt"));
                setCollideTiles(worldTemplate.loadAWorld("world/bossWorldTiles/bossWorld/collisionWorld.txt"));
                setPathTilesWorld(worldTemplate.loadAWorld("world/bossWorldTiles/bossWorld/pathTilesWorld.txt"));

                //spawn boss at 2208 1009
                enemies.add(new enemy._enemies.bossEnemy(2208, 1009, this));

                delayedBossHealth = enemies.get(0).getMaxHealth(); // Initialize delayed boss health to the boss's max health

                //make annoying spawners around the world
                enemySpawners.add(new enemySpawner(1617, 1723, 5, this, 2000, 10, 100));
                enemySpawners.add(new enemySpawner(1229, 1287, 6, this, 2000, 15, 100));
                enemySpawners.add(new enemySpawner(1577, 586, 7, this, 2000, 15, 100));
                enemySpawners.add(new enemySpawner(2560, 502, 4, this, 2000, 10, 100));
                enemySpawners.add(new enemySpawner(3040, 930, 3, this, 2000, 10, 100));
            }
        }else if(!hasBossEnded){
            if(delayedBossHealth > enemies.get(0).health){
                // Update delayed boss health to match the current health of the boss
                delayedBossHealth -= (enemies.get(0).getMaxHealth()/300.0);
            }
            //check if the boss is dead
            if(enemies.get(0).getClass() != enemy._enemies.bossEnemy.class){
                ///load old world
                setGrassTilesWorld(worldTemplate.loadAWorld("world/bossWorldTiles/grassTilesWorld.txt"));
                setCollideTiles(worldTemplate.loadAWorld("world/bossWorldTiles/collisionWorld.txt"));
                setPathTilesWorld(worldTemplate.loadAWorld("world/bossWorldTiles/pathTilesWorld.txt"));

                hasBossEnded = true; // Reset the boss fight state

                currentUI.setInMenu(true);
                currentUI.winMenu = true; // Set the UI to indicate the player has won
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(hasBossStarted && !hasBossEnded) {
            //draw the name of the boss
            g.setColor(Color.BLACK);
            g.setFont(g.getFont().deriveFont(60f));
            String bossName = "THE CHILD";
            int stringWidth = g.getFontMetrics().stringWidth(bossName);
            int xPos = (1280 - stringWidth) / 2;
            g.drawString(bossName, xPos, 60);

            // health bar
            drawBossHealth(g, 384, 50);
        }

        
    }

    public void drawBossHealth(Graphics g, int x, int y){
        // Boss Health
        g.drawImage(healthBarImages[0], x, y, 512, 128, null); // Draw health bar background

        // Draw the filled part, cropped by delayed health
        int healthWidth = (int)(512 * ((float)delayedBossHealth / enemies.get(0).getMaxHealth()));
        g.drawImage(healthBarImages[2], x, y, x + healthWidth, y + 128, 0, 0, healthWidth / 2, 64, null); // only draw the filled part

        // Draw the filled part, cropped by current health
        healthWidth = (int)(512 * ((float)enemies.get(0).health / enemies.get(0).getMaxHealth()));
        g.drawImage(healthBarImages[1], x, y, x + healthWidth, y + 128, 0, 0, healthWidth / 2, 64, null); // only draw the filled part
    }

    @Override
    public void quitGame(){
        myMainPanel.setWorld(new mainMenu(myMainPanel));
        if(!currentUI.deathMenu){
            currentPlayer.savePlayerState();
        }
    }
}
