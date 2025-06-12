package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import attack.attackTemplate;
import attack.bullet;
import world.worldTemplate;

public class playerTemplate{
    //Player positional properties
    public int x = 0;
    public int y = 0;
    public int xVel = 0;
    public int yVel = 0;
    public float speed = 2  ; // Speed of the player
    public float maxSpeed = 2; // Maximum speed of the player
    
    //Player health properties
    private int playerHealth = 100; // Player health
    private int playerMaxHealth = 100; // Player max health

    //Animation properties
    private BufferedImage idleImage;
    private BufferedImage[] runningDownImages = new BufferedImage[4];
    private BufferedImage[] runningUpImages = new BufferedImage[4];
    private BufferedImage[] runningLeftImages = new BufferedImage[4];
    private BufferedImage[] runningRightImages = new BufferedImage[4];
    private int currentFrame = 0;
    private long lastFrameChangeTime = 0;
    private int currentState = 0; // 0 for idle, 1 for running
    public int playerSize = 32;

    //World properties
    private worldTemplate currentWorld;
    private String playerName = "defaultPlayer"; // Name of the player, used for loading sprites

    //Attack properties
    public attackTemplate attack; // Attack template for player
    private int attackType = 0;
    private int elementType = 0; // Type of the element applied to the player 
    private int attackDamage = 10; // Damage dealt by the player's attack
    private int attackRange = 100; // Range of the player's attack
    private int attackCooldown = 500; // Cooldown time for the player's attack in milliseconds
    private int inbetweenAttackCooldown = 100; // Cooldown time between attacks in milliseconds
    private int attackSize = 5; //how many bullets the player can shoot at once
    private int bulletSpeed = 1;
    private int lastAttackX;
    private int lastAttackY;
    private int specialAttackCooldown = 5000;
    private long lastSpecialAttackTime = System.currentTimeMillis(); // Time of the last special attack
    private int specialAttackId = 0; // ID for the special attack, can be used to differentiate between different special attacks
    private String specialName; // Name of the special attack, used for loading sprites
    public boolean specialAttackSelected = false;
    private int specialAttackDamage = 20;


    public playerTemplate(int x, int y, int attackType, int elementType, int attackDamage, int attackRange, int attackCooldown, int inbetweenAttackCooldown, int attackSize, int bulletSpeed, int maxHealth, worldTemplate currentWorld, String playerNameDir, attackTemplate attack, String playerName, int specialAttackCooldown, int specialAttackId, String specialName, int specialAttackDamage) {
        this.x = x;
        this.y = y;
        this.attackType = attackType; // Set the attack type for the player
        this.attackRange = attackRange; // Set the attack range for the player
        this.attackCooldown = attackCooldown; // Set the attack cooldown for the player
        this.inbetweenAttackCooldown = inbetweenAttackCooldown; // Set the cooldown between attacks
        this.attackDamage = attackDamage; // Set the attack damage for the player
        this.attackSize = attackSize; // Set the size of the attack for the player
        this.bulletSpeed = bulletSpeed; // Set the speed of the bullets fired by the player
        this.playerHealth = maxHealth; // Initialize player health to max health
        this.playerMaxHealth = maxHealth; // Set the maximum health for the player
        this.elementType = elementType; // Set the element type for the player
        this.playerName = playerName; // Set the player name for loading sprites
        this.specialAttackCooldown = specialAttackCooldown; // Set the special attack cooldown for the player
        this.specialAttackId = specialAttackId; // Set the special attack ID for the player
        this.specialName = specialName; // Set the special attack name for loading sprites
        this.specialAttackDamage = specialAttackDamage; // Set the special attack damage for the player
        
        //load idle image
        idleImage = sprite.getImages("player/"+playerNameDir+"/idle/", playerSize);

        // load running down images
        sprite.getImages("player/"+playerNameDir+"/running/down/", runningDownImages, playerSize,4);

        // load running up images
        sprite.getImages("player/"+playerNameDir+"/running/up/", runningUpImages, playerSize,4);

        // load running left images
        sprite.getImages("player/"+playerNameDir+"/running/left/", runningLeftImages, playerSize,4);

        // load running right images
        sprite.getImages("player/"+playerNameDir+"/running/right/", runningRightImages, playerSize,4);
        
        this.currentWorld = currentWorld; // Set the current world reference

        this.attack = new attackTemplate(false, bulletSpeed, currentWorld, attackRange,attackCooldown,inbetweenAttackCooldown, attackSize);

        if(attack != null){
            for (bullet bullet : attack.getBullets()) {
                bullet.updateAttackParent(this.attack); // Update the attack reference for each bullet
                this.attack.addBullet(bullet); // Initialize bullets in the attack
            }
        }

        loadPlayer(); // Load player state from file
    }
    
    public void takeDamage(int damage) {
        playerHealth -= damage;
        if (playerHealth < 0) {
            playerHealth = 0; // Ensure health doesn't go below 0
        }
    }

    public int getHealth() {
        return playerHealth;
    }

    public int getMaxHealth() {
        return playerMaxHealth;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getAttackCooldown() {
        return attackCooldown;
    }

    public String getPlayerName() {
        return playerName;
    }

    public long getSpecialAttackCooldownRemaining() {
        return Math.max(0, specialAttackCooldown - (System.currentTimeMillis() - lastSpecialAttackTime));
    }

    public int getSpecialCooldown(){
        return specialAttackCooldown;
    }

    public void loadPlayer(){
        try {
            File file = new File("player/saves/"+playerName.replace(" ", "")+".txt");
            if(file == null || !file.exists()) {
                System.out.println("Player save file not found, creating new one.");
                savePlayerState(); // Create a new save file if it doesn't exist
                return;
            }else{
                Scanner scan = new Scanner(file);
                playerHealth = Integer.parseInt(scan.nextLine()); // Read player name
                attackDamage = Integer.parseInt(scan.nextLine()); // Read player health
            }
        } catch (Exception e) {
        }
    }

    public void savePlayerState(){
        try {
            File file = new File("player/saves/"+playerName.replace(" ", "")+".txt");
            if(file == null || !file.exists()) {
                file.createNewFile(); // Create the file if it doesn't exist
            }
            PrintWriter pw = new PrintWriter(file);
            //save like this
            // health
            // attack damage
            pw.println(playerHealth);
            pw.println(attackDamage);
            pw.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void attack(int targetX, int targetY) {
        if(!attack.isActive() && !attack.isSpecialAttack) {
            lastAttackX = targetX;
            lastAttackY = targetY;
            attack.attack(attackType, attackDamage, x, y, lastAttackX, lastAttackY, elementType);
        }
    }

    public void specialAttack(int targetX, int targetY) {
        if(!attack.isActive() && getSpecialAttackCooldownRemaining() == 0) {
            specialAttackSelected = false;
            lastSpecialAttackTime = System.currentTimeMillis(); // Update the last special attack time
            attack.specialAttack(specialAttackId, targetY, targetY, targetY, targetX, targetY, targetY);
        }
    }

    public void update() {
        attack.update(); // Update attack state and bullets
        if(attack.isActive()) {
            if(attack.isSpecialAttack) {
                attack.specialAttack(specialAttackId, specialAttackDamage, x, y, lastAttackX, lastAttackY, elementType); // Update special attack logic
            } else {
                attack.attack(attackType, attackDamage, x, y, lastAttackX, lastAttackY, elementType);
            }
        }

        //animation logic
        if(System.currentTimeMillis() - lastFrameChangeTime > 100 && currentState == 1) {
            lastFrameChangeTime = System.currentTimeMillis();
            currentFrame++;
        }
        if (currentFrame >= 4) {
            currentFrame = 0;
        }

        //slow down if player moving x and y at the same time
        if (xVel != 0 && yVel != 0) {
            maxSpeed = 1.4f; // Diagonal movement speed
        } else {
            maxSpeed = 2; // Normal movement speed
        }
        
        //check that the player isnt colliding with world, if not allow to move
        // Check X movement
        float nextX = x + (Math.min(Math.abs(xVel), maxSpeed) == Math.abs(xVel) ? xVel : maxSpeed * (xVel < 0 ? -1 : 1)) * (int)speed;
        if (!currentWorld.isColliding(nextX, y, false)) {
            x = (int)nextX;
        }

        // Check Y movement
        float nextY = y + (Math.min(Math.abs(yVel), maxSpeed) == Math.abs(yVel) ? yVel : maxSpeed * (yVel < 0 ? -1 : 1)) * (int)speed;
        if (!currentWorld.isColliding(x, nextY, false)) {
            y = (int)nextY;
        }

        // Ensure player stays within bounds
        if (x < 0){x = 0;}
        if (y < 0){y = 0;}
        if (x > currentWorld.getWorldWidth() - playerSize){ x = currentWorld.getWorldWidth() - playerSize;}
        if (y > currentWorld.getWorldHeight() - playerSize){ y = currentWorld.getWorldHeight() - playerSize;}

        // Update current state based on velocity
        if (xVel != 0 || yVel != 0) {
            currentState = 1; // Running
        } else {
            currentState = 0; // Idle
            currentFrame = 0; // Reset frame when idle
        }

        // reset current frame if the player is idle
        if (currentState == 0) {
            currentFrame = 0; // Reset frame when idle
            lastFrameChangeTime = 0;
        }

        currentWorld.worldXOffset = Math.min(Math.max(x - 640, 0), Math.max(currentWorld.getWorldWidth()-1280,0)); // Center camera on player
        currentWorld.worldYOffset = Math.min(Math.max(y - 360, 0), Math.max(currentWorld.getWorldHeight()-720,0)); // Center camera on player
    }
    
    public void draw(Graphics g,int worldXOffset, int worldYOffset) {
        BufferedImage img = idleImage; // Assuming idleImage is set
        if(yVel > 0){
            img = runningDownImages[currentFrame];
        }else if(yVel < 0){
            img = runningUpImages[currentFrame];
        }else if(xVel < 0){
            img = runningLeftImages[currentFrame];
        }else if (xVel > 0) {
            img = runningRightImages[currentFrame];
        }
        
        g.drawImage(img, x-worldXOffset-16, y-worldYOffset-16, null); 

        attack.drawBullets(g, worldXOffset, worldYOffset); // Draw bullets fired by the attack

        //show player hit box when in debug mode or in slow mode
        if(currentWorld.debugMode || speed == 1){
            g.setColor(Color.RED);
            g.fillArc(x-worldXOffset-6, y-worldYOffset-6, 12, 12, 0, 360);
        }
    }
}
