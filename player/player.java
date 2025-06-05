package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import attack.attackTemplate;
import world.worldTemplate;

public class player{
    public int x = 0;
    public int y = 0;

    public int xVel = 0;
    public int yVel = 0;

    public float speed = 2  ; // Speed of the player
    public float maxSpeed = 2; // Maximum speed of the player

    public int playerSize = 32;

    private int currentFrame = 0;
    private long lastFrameChangeTime = 0;

    private int playerHealth = 100; // Player health

    private BufferedImage idleImage;
    private BufferedImage[] runningDownImages = new BufferedImage[4];
    private BufferedImage[] runningUpImages = new BufferedImage[4];
    private BufferedImage[] runningLeftImages = new BufferedImage[4];
    private BufferedImage[] runningRightImages = new BufferedImage[4];
    
    private int currentState = 0; // 0 for idle, 1 for running

    private worldTemplate currentWorld;

    private attackTemplate attack; // Attack template for player

    public player(worldTemplate currentWorld) {
        //load idle image
            idleImage = sprite.getImages("player/template/idle/", playerSize);

            // load running down images
            sprite.getImages("player/template/running/down/", runningDownImages, playerSize,4);

            // load running up images
            sprite.getImages("player/template/running/up/", runningUpImages, playerSize,4);

            // load running left images
            sprite.getImages("player/template/running/left/", runningLeftImages, playerSize,4);

            // load running right images
            sprite.getImages("player/template/running/right/", runningRightImages, playerSize,4);
        
        this.currentWorld = currentWorld; // Set the current world reference

        attack = new attackTemplate(false, 1, currentWorld);
    }

    public void update() {
        //slow down if player moving x and y at the same time
        if (xVel != 0 && yVel != 0) {
            maxSpeed = 1.4f; // Diagonal movement speed
        } else {
            maxSpeed = 2; // Normal movement speed
        }
        // Update player position based on velocity
        x += (Math.min(Math.abs(xVel), maxSpeed) == Math.abs(xVel) ? xVel : maxSpeed*(xVel < 0 ? -1 : 1))*speed;
        y += (Math.min(Math.abs(yVel), maxSpeed) == Math.abs(yVel) ? yVel : maxSpeed*(yVel < 0 ? -1 : 1))*speed;

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
    
    public void takeDamage(int damage) {
        playerHealth -= damage;
    }

    public int getHealth() {
        return playerHealth;
    }
    
    public void draw(Graphics g,int worldXOffset, int worldYOffset) {
        if(System.currentTimeMillis() - lastFrameChangeTime > 100 && currentState == 1) {
            lastFrameChangeTime = System.currentTimeMillis();
            currentFrame++;
        }
        if (currentFrame >= 4) {
            currentFrame = 0;
        }
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

        g.setColor(Color.RED);
        g.fillArc(x-worldXOffset-6, y-worldYOffset-6, 12, 12, 0, 360);

        // g.drawOval(x-8,y-8,16,16);
    }
}
