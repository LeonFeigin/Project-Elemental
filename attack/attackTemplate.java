package attack;

import java.awt.Graphics;
import java.util.ArrayList;

import player.player;
import world.worldTemplate;

public class attackTemplate {
    
    private int attackRange = 100; // Range of the attack
    
    private boolean isEnemyAttack = false; // True if this is an enemy attack, false if it's a player attack

    private boolean isActive = false; // Indicates if the attack is currently active

    ArrayList<bullet> bullets = new ArrayList<>(); // List to hold bullets fired by the attack

    worldTemplate currentWorld; // Reference to the current world

    long lastTimeFired = System.currentTimeMillis(); // Timestamp of the last time the attack was fired
    
    private long attackCooldown = 1000; // Cooldown time for the attack in milliseconds (1 second)
    private int leftAmountOfShooting = 3;

    private float speed = 1; // Speed of the bullets fired by the attack

    public attackTemplate(boolean isEnemyAttack, float speed, worldTemplate world) {
        this.isEnemyAttack = isEnemyAttack;
        this.currentWorld = world; // Set the current world reference
    }

    public boolean isActive() {
        return isActive;
    }

    public void attack(int attackType, int initX, int initY, player targetPlayer) {
        if(isActive){
            if(System.currentTimeMillis() - lastTimeFired < attackCooldown) {
                return; // If the attack is already active and less than 1 second has passed, do not reinitialize
            }else{
                lastTimeFired = System.currentTimeMillis(); // Update the last time fired
                isActive = false; // Deactivate the attack
            }
        }
        
        isActive = true; // Mark the attack as active
        attack(0, initX, initY, targetPlayer.x, targetPlayer.y, isEnemyAttack); // Call the attack method to fire bullets
    }

    public void attack(int attackType, int initX, int initY, int targetX, int targetY) {
        if(isActive){
            if(System.currentTimeMillis() - lastTimeFired < attackCooldown) {
                return; // If the attack is already active and less than 1 second has passed, do not reinitialize
            }else{
                lastTimeFired = System.currentTimeMillis(); // Update the last time fired
                isActive = false; // Deactivate the attack
            }
        }
        isActive = true; // Mark the attack as active
        attack(0, initX, initY, targetX, targetY, isEnemyAttack); // Call the attack method to fire bullets
    }

    private void attack(int attackType, int initX, int initY, int targetX, int targetY, boolean isEnemy) { // atatck method for firing bullets
        if(attackType == 0){ // straight line attack
            double angle = Math.atan2(targetY-initY,targetX-initX);
            
            float dx = targetX - initX;
            float dy = targetY - initY;
            float length = (float)Math.sqrt(dx * dx + dy * dy);

            float vx = dx / length;
            float vy = dy / length;

            for (int i = -1; i < 2; i++) {
                double newInitX = (initX - initX+32) * Math.cos(angle) - (initY - initY+10*i) * Math.sin(angle) + initX;
                double newInitY = (initX - initX+32) * Math.sin(angle) + (initY - initY+10*i) * Math.cos(angle) + initY;

                // System.out.println(newTargetX);
                bullet newBullet = new bullet((int)newInitX, (int)newInitY, vx, vy, speed, isEnemy, currentWorld, this);
                bullets.add(newBullet);
            }
        }
    }

    public void update() {
        // Update all bullets in the list
        for (int i = 0; i < bullets.size(); i++) {
            bullet b = bullets.get(i);
            b.update(); // Update bullet position and check for collisions
        }
        
        // If no bullets are left, deactivate the attack
        if (bullets.isEmpty()) {
            isActive = false;
        }
    }

    public void removeBullet(bullet b) {
        bullets.remove(b); // Remove the bullet from the list
    }

    public void drawBullets(Graphics g, int worldXOffset, int worldYOffset) {
        for(bullet b : bullets) {
            b.draw(g, worldXOffset, worldYOffset);
        }
    }
}
