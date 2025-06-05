package attack;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import player.sprite;
import world.worldTemplate;

public class bullet{
    float x; // X position of the bullet
    float y; // Y position of the bullet

    float vx;
    float vy;

    float speed = 1; // Speed of the bullet

    boolean isEnemy; // True if the bullet is fired by the player, false if by an enemy

    BufferedImage bulletImage; // Image of the bullet

    worldTemplate currentWorld; // Reference to the current world

    attackTemplate attackParent; // Reference to the attack that fired this bullet

    public bullet(int startX, int startY, float vx, float vy, float speed, boolean isEnemy, worldTemplate world, attackTemplate attackParent) {
        this.x = startX;
        this.y = startY;
        this.vx = vx;
        this.vy = vy;
        this.speed = speed;
        this.currentWorld = world;
        this.isEnemy = isEnemy;
        this.attackParent = attackParent;

        bulletImage = sprite.getImages("attack/bulletImage/", 12);
    }

    public void update() {
        // Move bullet along velocity
        x += speed * vx;
        y += speed * vy;

        //detect collision
        if(isEnemy){
            float distanceToPlayer = (float)Math.sqrt(Math.pow(x - currentWorld.currentPlayer.x, 2) + Math.pow(y - currentWorld.currentPlayer.y, 2));
            if(distanceToPlayer < 6) {
                currentWorld.currentPlayer.takeDamage(10); // Deal damage to the player
                attackParent.removeBullet(this);
            }
        }
    }

    public void draw(Graphics g, int worldXOffset, int worldYOffset) {
        // Draw the bullet image at the current position
        g.drawImage(bulletImage, (int)x - worldXOffset, (int)y - worldYOffset, null);
    }
}
