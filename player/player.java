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

public class player {
    public int x = 0;
    public int y = 0;

    public int xVel = 0;
    public int yVel = 0;

    public int width;
    public int height;

    private int currentFrame = 0;

    private BufferedImage idleImage;
    private BufferedImage[] runningDownImages = new BufferedImage[4];
    
    private int currentState = 0; // 0 for idle, 1 for running

    public player(){
        for (int i = 0; i < 4; i++) {
            try {
                BufferedImage original = ImageIO.read(new File("player/template/running/down/ (" + (i+1) + ").png"));
                BufferedImage scaled = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
                AffineTransform at = AffineTransform.getScaleInstance(32 / original.getWidth(), 32 / original.getHeight());
                AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                scaleOp.filter(original, scaled);
                this.runningDownImages[i] = scaled;
            } catch (IOException ex) {
                System.out.println("File not found! " + "player/template/idle/" + (i+1) + ".png");
            }
        }
    }

    public void update() {
        // Update player position based on velocity
        x += xVel;
        y += yVel;

        // Ensure player stays within bounds (example bounds)
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > 1280 - width) x = 1280 - width; // Assuming screen width is 1280
        if (y > 720 - height) y = 720 - height; // Assuming screen height is 720

        // Update current state based on velocity
        if (xVel != 0 || yVel != 0) {
            currentState = 1; // Running
        } else {
            currentState = 0; // Idle
            currentFrame = 0; // Reset frame when idle
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform backup = g2d.getTransform();
        AffineTransform transform = new AffineTransform();           
        g2d.transform(transform);
        currentFrame++;
        if (currentFrame >= runningDownImages.length) {
            currentFrame = 0;
        }
        BufferedImage img = runningDownImages[currentFrame];
        width = img.getWidth();
        height = img.getHeight();
        g2d.drawImage(img, x, y, null); 
        g2d.setTransform(backup);
    }

}
