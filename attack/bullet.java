package attack;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import enemy.enemyTemplate;
import player.sprite;
import world.worldTemplate;

public class bullet{
    private float x; // X position of the bullet
    private float y; // Y position of the bullet

    private float vx; // X velocity of the bullet
    private float vy; // Y velocity of the bullet

    private float speed = 1; // Speed of the bullet

    private boolean isEnemy; // True if the bullet is fired by the player, false if by an enemy

    private BufferedImage bulletImage; // Image of the bullet

    private worldTemplate currentWorld; // Reference to the current world

    private attackTemplate attackParent; // Reference to the attack that fired this bullet

    private int damageAmount; // Amount of damage the bullet deals, default is 10

    private int bulletElementType; // Element type of the bullet

    private int bulletSize = 12; // Size of the bullet 

    private int bulletRange; // Range of the bullet, how far it can travel before disappearing

    private int initX; // Initial X position of the bullet
    private int initY; // Initial Y position of the bullet

    // Constructor for the bullet class
    public bullet(int startX, int startY, float vx, float vy, float speed, boolean isEnemy, worldTemplate world, attackTemplate attackParent, int damageAmount, int bulletRange, int bulletElementType) {
        this.x = startX;
        this.y = startY;
        this.initX = startX; // Set the initial X position
        this.initY = startY; // Set the initial Y position
        this.vx = vx;
        this.vy = vy;
        this.speed = speed;
        this.currentWorld = world;
        this.isEnemy = isEnemy;
        this.attackParent = attackParent;
        this.damageAmount = damageAmount; // Set the damage amount
        this.bulletRange = bulletRange; // Set the range of the bullet
        this.bulletElementType = bulletElementType;

        bulletImage = sprite.getImages("attack/bulletImage/", bulletSize); // Load the bullet image
    }

    // Method to update the bullet's parent attack reference
    public void updateAttackParent(attackTemplate attackParent) {
        this.attackParent = attackParent; // Update the reference to the attack that fired this bullet
    }

    public void update() {
        // Move bullet along velocity
        x += speed * vx;
        y += speed * vy;

        //detect collision with enemy or player
        if(isEnemy){
            float distanceToPlayer = (float)Math.sqrt(Math.pow(x - currentWorld.currentPlayer.x, 2) + Math.pow(y - currentWorld.currentPlayer.y, 2));
            if(distanceToPlayer < 6) {
                currentWorld.currentPlayer.takeDamage(damageAmount); // Deal damage to the player
                attackParent.removeBullet(this);
            }
        }else{
            for (enemyTemplate enemy : currentWorld.getEnemies()) {
                if(enemy.isActive()) { // Check if the bullet collides with an active enemy
                    if(x+bulletSize/2 > enemy.x && x-bulletSize/2 < enemy.x + enemy.enemySize && y+bulletSize/2 > enemy.y && y-bulletSize/2 < enemy.y + enemy.enemySize) {
                        int damage = abilityAttacks.getDamageMultiplier(damageAmount, enemy.getElementsList(), bulletElementType);
                        enemy.takeDamage(damage); // Deal damage to the enemy
                        if(damageAmount != damage){
                            enemy.resetElements(); // if an element matchup occurs, reset the enemy's elements
                        }else{
                            enemy.applyElement(bulletElementType); // Add the element type to the enemy
                        }
                        attackParent.removeBullet(this); // Remove the bullet after hitting the enemy
                        return;
                    }
                }
            }
        }

        //detect collision with collide tiles
        for (int i = 0; i < currentWorld.getCollideTiles().length; i++) {
            for (int j = 0; j < currentWorld.getCollideTiles()[i].length; j++) {
                if(currentWorld.getCollideTiles()[i][j] == 0) {
                    // Check if bullet collides with the tile
                    if (x > j * 32 && x < j * 32 + 32 && y > i * 32 && y < i * 32 + 32) {
                        attackParent.removeBullet(this); // Remove the bullet on collision
                        return;
                    }
                }
            }
        }

        //check if bullet is out of range
        if (Math.sqrt(Math.pow(x - initX, 2) + Math.pow(y - initY, 2)) > bulletRange) {
            attackParent.removeBullet(this); // Remove the bullet if it exceeds its range
        }
    }

    public void draw(Graphics g, int worldXOffset, int worldYOffset) {
        //make a translucent circle for the bullet element type
        if(bulletElementType != abilityAttacks.NO_ELEMENT){
            if(bulletElementType == abilityAttacks.FIRE_ELEMENT){
                g.setColor(new Color(255,0,0,100));
            }else if(bulletElementType == abilityAttacks.ICE_ELEMENT){
                g.setColor(new Color(0,0,255,100));
            }else if(bulletElementType == abilityAttacks.LIGHTNING_ELEMENT){
                g.setColor(new Color(255,0,255,100));
            }else if(bulletElementType == abilityAttacks.WATER_ELEMENT){
                g.setColor(new Color(0,255,255,100));
            }else if(bulletElementType == abilityAttacks.EARTH_ELEMENT){
                g.setColor(new Color(0,255,0,100));
            }

            g.fillOval((int)(x - worldXOffset-bulletSize/2f), (int)(y - worldYOffset-bulletSize/2f), bulletSize, bulletSize); // Draw a colored circle for the element type
        }

        // Draw the bullet image at the current position
        g.drawImage(bulletImage, (int)(x - worldXOffset-bulletSize/2f), (int)(y - worldYOffset-bulletSize/2f), null);
    }
}
