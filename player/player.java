package player;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import world.worldTemplate;

public class player {
    public int x = 600;
    public int y = 0;

    public int xVel = 0;
    public int yVel = 0;

    public float speed = 1; // Speed of the player
    public float maxSpeed = 2; // Maximum speed of the player

    public int width;
    public int height;

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
            idleImage = getImages("player/template/idle/", 32);

            // load running down images
            getImages("player/template/running/down/", runningDownImages, 32,4);

            // load running up images
            getImages("player/template/running/up/", runningUpImages, 32,4);

            // load running left images
            getImages("player/template/running/left/", runningLeftImages, 32,4);

            // load running right images
            getImages("player/template/running/right/", runningRightImages, 32,4);
        
        
    }

    private BufferedImage getImages(String direction, int size) {
        try{
            BufferedImage original = ImageIO.read(new File(direction + 0 + ".png"));
            BufferedImage scaled = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            AffineTransform at = AffineTransform.getScaleInstance(size / original.getWidth(), size / original.getHeight());
            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            scaleOp.filter(original, scaled);
            return scaled;
        }catch (IOException ex) {
            System.out.println("File not found!");
        }
        return null;
    }

    private void getImages(String direction, BufferedImage[] image,int size, int count) {
        try{
            for (int i = 0; i < count; i++) {
                BufferedImage original = ImageIO.read(new File(direction + i+ ".png"));
                BufferedImage scaled = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
                AffineTransform at = AffineTransform.getScaleInstance(size / original.getWidth(), size / original.getHeight());
                AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                scaleOp.filter(original, scaled);
                image[i] = scaled;
            }
        }catch (IOException ex) {
            System.out.println("File not found!");
        }
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

        // Ensure player stays within bounds (example bounds)
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > world.worldWidth - width-48) x = world.worldWidth - width - 48;
        if (y > world.worldHeight - height-48) y = world.worldHeight - height - 48;

        System.out.println("Player Position: (" + x + ", " + y + ") + " + world.worldWidth);

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

        world.worldXOffset = Math.min(Math.max(x - 640, 0), world.worldWidth-1280); // Center camera on player
        world.worldYOffset = Math.min(Math.max(y - 360, 0), world.worldHeight-720); // Center camera on player
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
        
        g.drawImage(img, x-worldXOffset, y, null); 
    }

}
