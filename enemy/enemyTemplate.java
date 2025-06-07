package enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import attack.attackTemplate;
import world.worldTemplate;
import player.sprite;

public class enemyTemplate {
    //Enemy Sprite properties
    public float x = 0;
    public float y = 0;

    public float xVel = 0;
    public float yVel = 0;

    public float speed = 1; // Speed of the enemy
    public float maxSpeed = 1; // Maximum speed of the enemy

    private int currentFrame = 0;
    private long lastFrameChangeTime = 0;

    public int enemySize = 32;

    //Enemy health properties
    public int health = 100;
    public ArrayList<Integer> elementsList = new ArrayList<>();
    private float bulletSpeed = 1.1f;

    private int currentState = 0; // 0 for idle, 1 for running

    //enemy attack properties
    public int attackRange = 800; // Range within which the enemy can attack
    private int closestPlayerDistance = 200;
    private attackTemplate attack; // Attack template for enemy

    private long lastTimeMoved = System.currentTimeMillis();
    private int moveCooldown = 1000;

    private int playerLastSeenX = 0; // Last known X position of the player
    private int playerLastSeenY = 0; // Last known Y position of the player

    worldTemplate currentWorld;

    public BufferedImage idleImage;
    private BufferedImage[] runningDownImages = new BufferedImage[4];
    private BufferedImage[] runningUpImages = new BufferedImage[4];
    private BufferedImage[] runningLeftImages = new BufferedImage[4];
    private BufferedImage[] runningRightImages = new BufferedImage[4];

    public enemyTemplate(int x, int y, worldTemplate currentWorld) {
        this.currentWorld = currentWorld; // Set the current world reference
        this.x = x;
        this.y = y;
        attack = new attackTemplate(true, bulletSpeed, currentWorld, 10); // Initialize attack with enemy properties

        idleImage = sprite.getImages("enemy/template/idle/", enemySize);
        sprite.getImages("enemy/template/running/down/", runningDownImages, enemySize, 4);
        sprite.getImages("enemy/template/running/up/", runningUpImages, enemySize, 4);
        sprite.getImages("enemy/template/running/left/", runningLeftImages, enemySize, 4);
        sprite.getImages("enemy/template/running/right/", runningRightImages, enemySize, 4);
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0; // Ensure health does not go below zero
            // Handle enemy death logic here, e.g., remove from world
        }
    } 

    public void applyElement(int element) {
        if (!elementsList.contains(element)) {
            elementsList.add(element); // Add the element to the list if not already present
        }
    }

    public void resetElements() {
        elementsList.removeAll(elementsList); // Clear all elements
    }

    public ArrayList<Integer> getElementsList() {
        return elementsList; // Return the list of elements
    }

    public void update() {
        // Get current time for movement logic
        long currentTime = System.currentTimeMillis();

        attack.update(); // Update attack state and bullets

        // Update current state based on velocity
        if (xVel != 0 || yVel != 0) {
            currentState = 1; // Running
        } else {
            currentState = 0; // Idle
            currentFrame = 0; // Reset frame when idle
        }

        //check for world collision and if not colliding, move the enemy
        if(!attack.isActive() && !currentWorld.isColliding(x+(Math.min(Math.abs(xVel), maxSpeed) == Math.abs(xVel) ? xVel : maxSpeed*(xVel < 0 ? -1 : 1))*speed, y+(Math.min(Math.abs(yVel), maxSpeed) == Math.abs(yVel) ? yVel : maxSpeed*(yVel < 0 ? -1 : 1))*speed)){
            x += (Math.min(Math.abs(xVel), maxSpeed) == Math.abs(xVel) ? xVel : maxSpeed*(xVel < 0 ? -1 : 1))*speed;
            y += (Math.min(Math.abs(yVel), maxSpeed) == Math.abs(yVel) ? yVel : maxSpeed*(yVel < 0 ? -1 : 1))*speed;
        }
        
        
        // Ensure enemy stays within bounds
        if (x < 0){x = 0;}
        if (y < 0){y = 0;}
        if (x > currentWorld.getWorldWidth() - enemySize){ x = currentWorld.getWorldWidth() - enemySize;}
        if (y > currentWorld.getWorldHeight() - enemySize){ y = currentWorld.getWorldHeight() - enemySize;}

        //if player not detected or not currently shooting, move randomly
        if(lastTimeMoved + moveCooldown < currentTime && !attack.isActive()) {
            if(currentState == 1){
                lastTimeMoved = currentTime;
                currentFrame = 0; // Reset frame when moving
                xVel = 0; // Reset velocity to avoid continuous movement
                yVel = 0; // Reset velocity to avoid continuous movement
            }else{
                lastTimeMoved = currentTime;
                // Randomly change direction
                xVel = (int) (Math.random() * 3) - 1; // -1, 0, or 1
                yVel = (int) (Math.random() * 3) - 1; // -1, 0, or 1
            }
        }

        if(attack.isActive()){
            attack.attack(3, (int)x+8, (int)y+8, playerLastSeenX, playerLastSeenY); // Attack the player
        }

        //check if player is within attack range
        if (currentWorld.currentPlayer != null) {
            int playerX = currentWorld.currentPlayer.x; 
            int playerY = currentWorld.currentPlayer.y;

            // Calculate distance to player
            double distanceToPlayer = Math.sqrt(Math.pow(playerX - x, 2) + Math.pow(playerY - y, 2));

            // If within attack range, stop moving and prepare to attack
            if (distanceToPlayer <= attackRange/2) {
                if(distanceToPlayer > closestPlayerDistance/2) {
                    xVel = (float) ((playerX - x) / distanceToPlayer); // Move towards the player
                    yVel = (float) ((playerY - y) / distanceToPlayer); // Move towards the player  
                }else{
                    xVel = 0; // Stop moving towards the player
                    yVel = 0; // Stop moving towards the player
                }
                playerLastSeenX = playerX; // Update last seen position
                playerLastSeenY = playerY; // Update last seen position
                attack.attack(3, (int)x+8, (int)y+8, playerLastSeenX, playerLastSeenY); // Attack the player
            }
        }
    }

    public void draw(Graphics g, int worldXOffset, int worldYOffset ) {
        if(System.currentTimeMillis() - lastFrameChangeTime > 100 && currentState == 1) {
            lastFrameChangeTime = System.currentTimeMillis();
            currentFrame++;
        }
        if (currentFrame >= 4) {
            currentFrame = 0;
        }
        BufferedImage img = idleImage; // Assuming idleImage is set
        if (!attack.isActive()) {
            if(yVel > 0){
                img = runningDownImages[currentFrame];
            }else if(yVel < 0){
                img = runningUpImages[currentFrame];
            }else if(xVel < 0){
                img = runningLeftImages[currentFrame];
            }else if (xVel > 0) {
                img = runningRightImages[currentFrame];
            }
        }

        //debuging circle for attack range
        if(currentWorld.debugMode) {
            g.setColor(Color.BLACK);
            //show the attack range of the enemy
            g.drawOval((int)x - worldXOffset + enemySize / 2 - attackRange/2, (int)y - worldYOffset + enemySize / 2 - attackRange/2, attackRange, attackRange);
            g.setColor(Color.RED);
            //show until where the enemy will approch the player
            g.drawOval((int)x - worldXOffset + enemySize / 2 - closestPlayerDistance/2, (int)y - worldYOffset + enemySize / 2 - closestPlayerDistance/2, closestPlayerDistance, closestPlayerDistance);
        }
        
        g.drawImage(img, (int)x-worldXOffset, (int)y-worldYOffset, null); 

        attack.drawBullets(g, worldXOffset, worldYOffset); // Draw bullets fired by the attack
        g.setColor(Color.BLACK);
        g.fillRoundRect((int)x-2-worldXOffset, (int)y-10-worldYOffset,36, 8,3,12);
        g.setColor(Color.RED);
        g.fillRoundRect((int)x-2-worldXOffset, (int)y-10-worldYOffset,(int)(36*(health/100.0)) , 8,3,12);
    }

}