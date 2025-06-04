package player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

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

    private BufferedImage idleImage;
    private BufferedImage[] runningDownImages = new BufferedImage[4];
    private BufferedImage[] runningUpImages = new BufferedImage[4];
    private BufferedImage[] runningLeftImages = new BufferedImage[4];
    private BufferedImage[] runningRightImages = new BufferedImage[4];
    
    private int currentState = 0; // 0 for idle, 1 for running

    private worldTemplate currentWorld;

    public player() {
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
        
        
    }

    public void update(worldTemplate world) {
        // update world reference
        this.currentWorld = world;

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
        if (x > world.getWorldWidth() - playerSize){ x = world.getWorldWidth() - playerSize;}
        if (y > world.getWorldHeight() - playerSize){ y = world.getWorldHeight() - playerSize;}

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

        world.worldXOffset = Math.min(Math.max(x - 640, 0), Math.max(world.getWorldWidth()-1280,0)); // Center camera on player
        world.worldYOffset = Math.min(Math.max(y - 360, 0), Math.max(world.getWorldHeight()-720,0)); // Center camera on player
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
        
        g.drawImage(img, x-worldXOffset, y-worldYOffset, null); 
    }
}
