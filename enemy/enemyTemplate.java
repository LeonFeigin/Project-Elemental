package enemy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import world.worldTemplate;
import player.sprite;

public class enemyTemplate {
    //Enemy Sprite properties
    public int x = 0;
    public int y = 0;

    public int xVel = 0;
    public int yVel = 0;

    public float speed = 1; // Speed of the enemy
    public float maxSpeed = 1; // Maximum speed of the enemy

    private int currentFrame = 0;
    private long lastFrameChangeTime = 0;

    public int enemySize = 32;

    private int currentState = 0; // 0 for idle, 1 for running

    //enemy attack properties
    public int attackRange = 800; // Range within which the enemy can attack


    private long lastTimeMoved = System.currentTimeMillis();
    private int nextTimeMove = 1000;

    worldTemplate currentWorld;

    public BufferedImage idleImage;
    private BufferedImage[] runningDownImages = new BufferedImage[4];
    private BufferedImage[] runningUpImages = new BufferedImage[4];
    private BufferedImage[] runningLeftImages = new BufferedImage[4];
    private BufferedImage[] runningRightImages = new BufferedImage[4];

    public enemyTemplate(int x, int y) {
        this.x = x;
        this.y = y;

        idleImage = sprite.getImages("enemy/template/idle/", enemySize);
        sprite.getImages("enemy/template/running/down/", runningDownImages, enemySize, 4);
        sprite.getImages("enemy/template/running/up/", runningUpImages, enemySize, 4);
        sprite.getImages("enemy/template/running/left/", runningLeftImages, enemySize, 4);
        sprite.getImages("enemy/template/running/right/", runningRightImages, enemySize, 4);
    }

    public void update(worldTemplate world) {
        long currentTime = System.currentTimeMillis();

        currentWorld = world;

        // Update current state based on velocity
        if (xVel != 0 || yVel != 0) {
            currentState = 1; // Running
        } else {
            currentState = 0; // Idle
            currentFrame = 0; // Reset frame when idle
        }

        // Update enemy position based on velocity
        x += (Math.min(Math.abs(xVel), maxSpeed) == Math.abs(xVel) ? xVel : maxSpeed*(xVel < 0 ? -1 : 1))*speed;
        y += (Math.min(Math.abs(yVel), maxSpeed) == Math.abs(yVel) ? yVel : maxSpeed*(yVel < 0 ? -1 : 1))*speed;
        
        // Ensure enemy stays within bounds
        if (x < 0){x = 0;}
        if (y < 0){y = 0;}
        if (x > world.getWorldWidth() - enemySize){ x = world.getWorldWidth() - enemySize;}
        if (y > world.getWorldHeight() - enemySize){ y = world.getWorldHeight() - enemySize;}

        //if player not detected, move randomly
        if(lastTimeMoved + nextTimeMove < currentTime) {
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
        if(yVel > 0){
            img = runningDownImages[currentFrame];
        }else if(yVel < 0){
            img = runningUpImages[currentFrame];
        }else if(xVel < 0){
            img = runningLeftImages[currentFrame];
        }else if (xVel > 0) {
            img = runningRightImages[currentFrame];
        }

        g.drawOval(x - worldXOffset + enemySize / 2 - attackRange/2, y - worldYOffset + enemySize / 2 - attackRange/2, attackRange, attackRange);
        
        g.drawImage(img, x-worldXOffset, y-worldYOffset, null); 
    }

}