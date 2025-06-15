package enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import attack.abilityAttacks;
import attack.attackTemplate;
import inventory.item;
import world.worldTemplate;
import player.sprite;

public class enemyTemplate {
    //Enemy positional properties
    public float x = 0;
    public float y = 0;
    public float xVel = 0;
    public float yVel = 0;
    public float speed = 1; // Speed of the enemy
    public float maxSpeed = 1; // Maximum speed of the enemy
    private long lastTimeMoved = System.currentTimeMillis();
    private int moveCooldown = 1000;
    
    //Enemy health properties
    public int health = 100;
    private float maxHealth = 100; // Maximum health of the enemy
    public ArrayList<Integer> elementsList = new ArrayList<>();
    private ArrayList<BufferedImage> elementsApplied = new ArrayList<>(); // List to hold images of applied elements
    

    private int currentState = 0; // 0 for idle, 1 for running

    //enemy attack properties
    private int attackType = 0;
    public int attackRange = 800; // Range within which the enemy can attack
    private int attackDamage = 10;
    private int closestPlayerDistance;
    private attackTemplate attack; // Attack template for enemy
    private int playerLastSeenX = 0; // Last known X position of the player
    private int playerLastSeenY = 0; // Last known Y position of the player
    private float bulletSpeed = 1.1f;

    private int bulletRange = 1000;

    private boolean isActive = true;

    private worldTemplate currentWorld;

    //Animation properties
    public BufferedImage idleImage;
    private BufferedImage[] runningDownImages = new BufferedImage[4];
    private BufferedImage[] runningUpImages = new BufferedImage[4];
    private BufferedImage[] runningLeftImages = new BufferedImage[4];
    private BufferedImage[] runningRightImages = new BufferedImage[4];
    private int currentFrame = 0;
    private long lastFrameChangeTime = 0;
    public int enemySize = 32;

    public enemyTemplate(int x, int y, worldTemplate currentWorld, int attackType, int attackDamage, int attackRange, int closestPlayerDistance, float bulletSpeed, int bulletRange, int health, float maxSpeed, int attackCooldown, int inbetweenAttackCooldown, int defaultLeftAmountOfShooting, String name) {
        this.currentWorld = currentWorld; // Set the current world reference
        this.x = x;
        this.y = y;
        this.attackType = attackType; // Set the attack type for the enemy
        this.attackRange = attackRange; // Set the attack range for the enemy
        this.closestPlayerDistance = closestPlayerDistance; // Set the closest player distance for the enemy
        this.attackDamage = attackDamage; // Set the attack damage for the enemy
        this.bulletSpeed = bulletSpeed; // Set the bullet speed for the enemy
        this.bulletRange = bulletRange; // Set the bullet range for the enemy
        this.health = health; // Initialize enemy health
        this.maxHealth = health; // Set the maximum health for the enemy
        this.maxSpeed = maxSpeed; // Set the maximum speed for the enemy
        attack = new attackTemplate(true, bulletSpeed, currentWorld, bulletRange, attackCooldown, inbetweenAttackCooldown, defaultLeftAmountOfShooting); // Initialize attack with enemy properties

        idleImage = sprite.getImages("enemy/"+name+"/idle/", enemySize);
        sprite.getImages("enemy/"+name+"/running/down/", runningDownImages, enemySize, 4);
        sprite.getImages("enemy/"+name+"/running/up/", runningUpImages, enemySize, 4);
        sprite.getImages("enemy/"+name+"/running/left/", runningLeftImages, enemySize, 4);
        sprite.getImages("enemy/"+name+"/running/right/", runningRightImages, enemySize, 4);
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0; // Ensure health does not go below zero
            isActive = false; // Set enemy to inactive when health reaches zero
            lootDrop();
        }
    } 

    public void applyElement(int element) {
        if (!elementsList.contains(element)) {
            elementsList.add(element); // Add the element to the list if not already present
            elementsApplied.add(abilityAttacks.getElementImage(element)); // Add the corresponding image to the applied elements list
        }
    }

    public void resetElements() {
        elementsList.clear(); // Clear all elements
        elementsApplied.clear(); // Clear the list of applied element images
    }

    public ArrayList<Integer> getElementsList() {
        return elementsList; // Return the list of elements
    }

    public boolean isActive() {
        return isActive; // Return whether the enemy is active
    }

    public int getMaxHealth() {
        return (int) maxHealth; // Return the maximum health of the enemy
    }

    public void lootDrop(){
        //add enemy health to player health
        currentWorld.currentPlayer.addHealth(health);
    }

    public void addItemToPlayerInventory(item item) {
        if (currentWorld.currentPlayer != null) {
            currentWorld.currentPlayer.inventory.addItem(item); // Add the item to the player's inventory
        }
    }

    public void update() {
        // Get current time for movement logic
        long currentTime = System.currentTimeMillis();

        attack.update(); // Update attack state and bullets

        if(!isActive) {
            //only remvoe the enemy once all of its bullets are gone
            if(attack.getBullets().size() <= 0) {
                
                currentWorld.getEnemies().remove(this); // Remove the enemy from the world if health is zero
            }
            return; // If the enemy is not active, skip the update
        }

        //animation logic
        if(System.currentTimeMillis() - lastFrameChangeTime > 100 && currentState == 1) {
            lastFrameChangeTime = System.currentTimeMillis();
            currentFrame++;
        }
        if (currentFrame >= 4) {
            currentFrame = 0;
        }

        // Update current state based on velocity
        if (xVel != 0 || yVel != 0) {
            currentState = 1; // Running
        } else {
            currentState = 0; // Idle
            currentFrame = 0; // Reset frame when idle
        }

        //check for world collision and if not colliding, move the enemy
        if(!attack.isActive() && !currentWorld.isColliding(x+(Math.min(Math.abs(xVel), maxSpeed) == Math.abs(xVel) ? xVel : maxSpeed*(xVel < 0 ? -1 : 1))*speed, y+(Math.min(Math.abs(yVel), maxSpeed) == Math.abs(yVel) ? yVel : maxSpeed*(yVel < 0 ? -1 : 1))*speed, true)){
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
            attack.attack(attackType, attackDamage, (int)x+8, (int)y+8, playerLastSeenX, playerLastSeenY); // Attack the player
        }

        //check if player is within attack range
        if (currentWorld.currentPlayer != null) {
            int playerX = currentWorld.currentPlayer.x; 
            int playerY = currentWorld.currentPlayer.y;

            // Calculate distance to player
            double distanceToPlayer = Math.sqrt(Math.pow(playerX - (x+16), 2) + Math.pow(playerY - (y+16), 2));

            // If within attack range, stop moving and prepare to attack
            if (distanceToPlayer <= attackRange/2) {
                if(distanceToPlayer > closestPlayerDistance/2) {
                    xVel = (float) ((playerX - (x+16)) / distanceToPlayer); // Move towards the player
                    yVel = (float) ((playerY - (y+16)) / distanceToPlayer); // Move towards the player  
                }else{
                    xVel = 0; // Stop moving towards the player
                    yVel = 0; // Stop moving towards the player
                }
                playerLastSeenX = playerX; // Update last seen position
                playerLastSeenY = playerY; // Update last seen position
                attack.attack(attackType, attackDamage, (int)x+16, (int)y+16, playerLastSeenX, playerLastSeenY); // Attack the player
            }
        }
    }

    public void draw(Graphics g, int worldXOffset, int worldYOffset ) {
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
            //show the enemy hitbox
            g.setColor(Color.RED);
            g.drawRect((int)x - worldXOffset, (int)y - worldYOffset, enemySize, enemySize);
        }
        
        //only draw the enemy if it is active
        if(isActive){
            //enemy itself
            g.drawImage(img, (int)x-worldXOffset, (int)y-worldYOffset, null); 

            //health bar
            g.setColor(Color.BLACK);
            g.fillRoundRect((int)x-2-worldXOffset, (int)y-10-worldYOffset,36, 8,3,12);
            g.setColor(Color.RED);
            g.fillRoundRect((int)x-2-worldXOffset, (int)y-10-worldYOffset,(int)(36*(health/maxHealth)) , 8,3,12);

            // Draw the elements applied to the enemy
            for (int i = 0; i < elementsApplied.size(); i++) {
                BufferedImage elementImage = elementsApplied.get(i);
                if (elementImage != null) {
                    g.drawImage(elementImage, (int)x - worldXOffset + i * 16, (int)y - worldYOffset - 32, null);
                }
            }
        }

        attack.drawBullets(g, worldXOffset, worldYOffset); // Draw bullets fired by the attack
        
    }

}