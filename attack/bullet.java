package attack;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import player.sprite;
import world.worldTemplate;

public class bullet{
    float x; // X position of the bullet
    float y; // Y position of the bullet

    int targetX;
    int targetY;

    float speed = 1; // Speed of the bullet

    boolean isEnemy; // True if the bullet is fired by the player, false if by an enemy

    BufferedImage bulletImage; // Image of the bullet

    worldTemplate currentWorld; // Reference to the current world

    float vx = 0, vy = 0;

    public bullet(int startX, int startY, int targetX, int targetY, boolean isEnemy, worldTemplate world) {
        this.x = startX;
        this.y = startY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.currentWorld = world;
        this.isEnemy = isEnemy;

        float dx = targetX - x;
        float dy = targetY - y;
        float length = (float)Math.sqrt(dx * dx + dy * dy);

        vx = dx / length;
            vy = dy / length;

        bulletImage = sprite.getImages("attack/bulletImage/", 12);
    }

    public void update() {
        // Move bullet along velocity
        x += speed * vx;
        y += speed * vy;
    }

    public void draw(Graphics g, int worldXOffset, int worldYOffset) {
        // Draw the bullet image at the current position
        g.drawImage(bulletImage, (int)x - worldXOffset, (int)y - worldYOffset, null);
    }
}
