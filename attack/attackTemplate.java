package attack;

import java.awt.Graphics;
import java.util.ArrayList;
import world.worldTemplate;

public class attackTemplate {

    private int attackRange = 100; // Range of the attack
    
    private boolean isEnemyAttack = false; // True if this is an enemy attack, false if it's a player attack

    private boolean isActive = false; // Indicates if the attack is currently active

    ArrayList<bullet> bullets = new ArrayList<>(); // List to hold bullets fired by the attack

    worldTemplate currentWorld; // Reference to the current world

    long lastTimeFired = System.currentTimeMillis(); // Timestamp of the last time the attack was fired
    
    private long attackCooldown = 1000; // Cooldown time for the attack in milliseconds (1 second)
    private int inbetweenAttackCooldown = 100; // Cooldown time between each bullet fired in milliseconds (100 milliseconds)
    private int leftAmountOfShooting = 2;
    private int defaultLeftAmountOfShooting = 2; // Default amount of shooting left

    private float speed = 1; // Speed of the bullets fired by the attack

    private int currentAngle = 0; //used for attack type 3 & 4

    public boolean isSpecialAttack = false; // Indicates if this is a special attack
    private int specialInbetweenAttackCooldown = 100; // Cooldown time between each bullet fired in milliseconds (100 milliseconds)
    private int specialLeftAmountOfShooting = 2;
    private int specialDefaultLeftAmountOfShooting = 2; // Default amount of shooting left

    //enemy constructor
    public attackTemplate(boolean isEnemyAttack, float speed, worldTemplate world, int attackRange) {
        this.isEnemyAttack = isEnemyAttack;
        this.currentWorld = world; // Set the current world reference
        this.speed = speed; // Set the speed of the bullets fired by the attack
        this.attackRange = attackRange; // Set the range of the attack
        defaultLeftAmountOfShooting = 2; // Set the default amount of shooting left
    }

    public attackTemplate(boolean isEnemyAttack, float speed, worldTemplate world, int attackRange, int attackCooldown, int inbetweenAttackCooldown, int defaultLeftAmountOfShooting) {
        this.isEnemyAttack = isEnemyAttack;
        this.currentWorld = world; // Set the current world reference
        this.speed = speed; // Set the speed of the bullets fired by the attack
        this.attackRange = attackRange; // Set the range of the attack
        this.attackCooldown = attackCooldown; // Set the cooldown time for the attack
        this.inbetweenAttackCooldown = inbetweenAttackCooldown; // Set the cooldown time between each bullet fired
        this.defaultLeftAmountOfShooting = defaultLeftAmountOfShooting; // Set the amount of shooting left
        this.leftAmountOfShooting = defaultLeftAmountOfShooting; // Initialize the amount of shooting left
    }

    public boolean isActive() {
        return isActive;
    }

    public ArrayList<bullet> getBullets() {
        return bullets; // Return the list of bullets fired by the attack
    }

    public void addBullet(bullet b) {
        bullets.add(b); // Add a bullet to the list of bullets fired by the attack
    }

    public void attack(int attackType, int attackDamage, int initX, int initY, int targetX, int targetY) {

        if(System.currentTimeMillis() - lastTimeFired > inbetweenAttackCooldown && leftAmountOfShooting > 0) {

            leftAmountOfShooting--; // Decrease the amount of shooting left
            attack(attackType,  attackDamage, initX, initY, targetX, targetY, isEnemyAttack, abilityAttacks.NO_ELEMENT); // Call the attack method to fire bullets
            lastTimeFired = System.currentTimeMillis(); // Update the last time fired

        }else if(System.currentTimeMillis() - lastTimeFired > attackCooldown) {
            leftAmountOfShooting = defaultLeftAmountOfShooting; // Reset the amount of shooting left
        }
    }

    public void attack(int attackType, int attackDamage, int initX, int initY, int targetX, int targetY, int elementType) {

        if(System.currentTimeMillis() - lastTimeFired > inbetweenAttackCooldown && leftAmountOfShooting > 0) {

            leftAmountOfShooting--; // Decrease the amount of shooting left
            attack(attackType, attackDamage, initX, initY, targetX, targetY, isEnemyAttack, elementType); // Call the attack method to fire bullets
            lastTimeFired = System.currentTimeMillis(); // Update the last time fired

        }else if(System.currentTimeMillis() - lastTimeFired > attackCooldown) {
            leftAmountOfShooting = defaultLeftAmountOfShooting; // Reset the amount of shooting left
        }
    }

    public void specialAttack(int attackType, int attackDamage, int initX, int initY, int targetX, int targetY, int elementType) {
        if(System.currentTimeMillis() - lastTimeFired > specialInbetweenAttackCooldown && specialLeftAmountOfShooting > 0) {
            specialLeftAmountOfShooting--; // Decrease the amount of shooting left
            isSpecialAttack = true; // Mark this as a special attack
            specialAttacksShoot(attackType, attackDamage, initX, initY, targetX, targetY, elementType); // Call the special attack method to fire bullets
            lastTimeFired = System.currentTimeMillis(); // Update the last time fired
        }else if(System.currentTimeMillis() - lastTimeFired > attackCooldown) {
            specialLeftAmountOfShooting = specialDefaultLeftAmountOfShooting; // Reset the amount of shooting left
        }
    }

    private void specialAttacksShoot(int attackType, int attackDamage, int initX, int initY, int targetX, int targetY, int elementType){
        double angle = Math.atan2(targetY-initY,targetX-initX);
        
        float dx = targetX - initX;
        float dy = targetY - initY;
        float length = (float)Math.sqrt(dx * dx + dy * dy);

        float vx = dx / length;
        float vy = dy / length;

        if(attackType == 0){        //water: Huge wave
            if(!isActive){
                isActive = true; // Mark the attack as active
                specialLeftAmountOfShooting = 3; // Reset the amount of shooting left
                specialInbetweenAttackCooldown = 100; // Cooldown time between each bullet fired in milliseconds (100 milliseconds)
            }
            for (int i = -2; i < 3; i++) {
                double newInitX = (initX - initX+32) * Math.cos(angle) - (initY - initY+10*i) * Math.sin(angle) + initX;
                double newInitY = (initX - initX+32) * Math.sin(angle) + (initY - initY+10*i) * Math.cos(angle) + initY;

                bullet newBullet = new bullet((int)newInitX, (int)newInitY, vx, vy, speed, false, currentWorld, this, attackDamage, attackRange, elementType);
                bullets.add(newBullet);
            }
        }else if(attackType == 1){  //lightning: Triple Strike

        }else if(attackType == 2){  //ice: Huge Icicle

        }else if(attackType == 3){  //fire: Fire wall

        }else if(attackType == 4){  //earth: Tornado

        }
    }

    private void attack(int attackType, int attackDamage, int initX, int initY, int targetX, int targetY, boolean isEnemy, int elementType) { // atatck method for firing bullets
        double angle = Math.atan2(targetY-initY,targetX-initX);
        
        float dx = targetX - initX;
        float dy = targetY - initY;
        float length = (float)Math.sqrt(dx * dx + dy * dy);

        float vx = dx / length;
        float vy = dy / length;


        if(attackType == 0){// single shot attack
            if(!isActive){
                isActive = true; // Mark the attack as active
                if(isEnemy) {
                    leftAmountOfShooting = 4; // Reset the amount of shooting left
                    attackCooldown = 1000; // Cooldown time for the attack in milliseconds (1 second)
                    inbetweenAttackCooldown = 150; // Cooldown time between each bullet fired in milliseconds (100 milliseconds)
                }
            }
            double newInitX = (initX - initX+32) * Math.cos(angle) - (initY - initY) * Math.sin(angle) + initX;
            double newInitY = (initX - initX+32) * Math.sin(angle) + (initY - initY) * Math.cos(angle) + initY;

            bullet newBullet = new bullet((int)newInitX, (int)newInitY, vx, vy, speed, isEnemy, currentWorld, this, attackDamage, attackRange, elementType);
            bullets.add(newBullet);

        }else if(attackType == 1){ // triple shot attack
            if(!isActive){
                isActive = true; // Mark the attack as active
                if(isEnemy) {
                    leftAmountOfShooting = 2; // Reset the amount of shooting left
                    attackCooldown = 500; // Cooldown time for the attack in milliseconds (1 second)
                    inbetweenAttackCooldown = 100; // Cooldown time between each bullet fired in milliseconds (100 milliseconds)
                }
            }
            for (int i = -1; i < 2; i++) {
                double newInitX = (initX - initX+32) * Math.cos(angle) - (initY - initY+10*i) * Math.sin(angle) + initX;
                double newInitY = (initX - initX+32) * Math.sin(angle) + (initY - initY+10*i) * Math.cos(angle) + initY;

                bullet newBullet = new bullet((int)newInitX, (int)newInitY, vx, vy, speed, isEnemy, currentWorld, this, attackDamage, attackRange, elementType);
                bullets.add(newBullet);
            }
        }else if(attackType == 2){ // around the object
            if(!isActive){
                isActive = true; // Mark the attack as active
                if(isEnemy) {
                    leftAmountOfShooting = 90; // Reset the amount of shooting left
                    attackCooldown = 5000; // Cooldown time for the attack in milliseconds (1 second)
                    inbetweenAttackCooldown = 25; // Cooldown time between each bullet fired in milliseconds (100 milliseconds)
                }
            }

            double newInitX = (initX - initX+32) * Math.cos(Math.toRadians(currentAngle)) - (initY - initY) * Math.sin(Math.toRadians(currentAngle)) + initX;
            double newInitY = (initX - initX+32) * Math.sin(Math.toRadians(currentAngle)) + (initY - initY) * Math.cos(Math.toRadians(currentAngle)) + initY;

            double newVX = (initX - initX+32+100) * Math.cos(Math.toRadians(currentAngle)) - (initY - initY) * Math.sin(Math.toRadians(currentAngle)) + initX;
            double newVY = (initX - initX+32+100) * Math.sin(Math.toRadians(currentAngle)) + (initY - initY) * Math.cos(Math.toRadians(currentAngle)) + initY;

            dx = (float)newVX - (float)newInitX;
            dy = (float)newVY - (float)newInitY;
            length = (float)Math.sqrt(dx * dx + dy * dy);

            vx = dx / length;
            vy = dy / length;

            bullet newBullet = new bullet((int)newInitX, (int)newInitY, vx, vy, speed, isEnemy, currentWorld, this, attackDamage, attackRange, elementType);
            bullets.add(newBullet);
            currentAngle+=4;
        }else if(attackType == 3){ // fihal boss (around the enemy scaterd)
            if(!isActive){
                isActive = true; // Mark the attack as active
                if(isEnemy){
                    leftAmountOfShooting = 360; // Reset the amount of shooting left
                    attackCooldown = 10000; // Cooldown time for the attack in milliseconds (1 second)
                    inbetweenAttackCooldown = 10; // Cooldown time between each bullet fired in milliseconds (100 milliseconds)
                }
            }

            double newInitX = (initX - initX+32) * Math.cos(Math.toRadians(currentAngle)) - (initY - initY) * Math.sin(Math.toRadians(currentAngle)) + initX;
            double newInitY = (initX - initX+32) * Math.sin(Math.toRadians(currentAngle)) + (initY - initY) * Math.cos(Math.toRadians(currentAngle)) + initY;

            double newVX = (initX - initX+32+100) * Math.cos(currentAngle) - (initY - initY) * Math.sin(currentAngle) + initX;
            double newVY = (initX - initX+32+100) * Math.sin(currentAngle) + (initY - initY) * Math.cos(currentAngle) + initY;

            dx = (float)newVX - (float)newInitX;
            dy = (float)newVY - (float)newInitY;
            length = (float)Math.sqrt(dx * dx + dy * dy);

            vx = dx / length;
            vy = dy / length;


            bullet newBullet = new bullet((int)newInitX, (int)newInitY, vx, vy, speed, isEnemy, currentWorld, this, attackDamage, attackRange, elementType);
            bullets.add(newBullet);
            currentAngle++;
        }
    }

    public void update() {
        // Update all bullets in the list
        for (int i = 0; i < bullets.size(); i++) {
            bullet b = bullets.get(i);
            b.update(); // Update bullet position and check for collisions
        }

        if(leftAmountOfShooting <= 0 && !isSpecialAttack) {
            isActive = false; // Mark the attack as inactive when no bullets left to shoot
        }
        if(specialLeftAmountOfShooting <= 0 && isSpecialAttack) {
            isSpecialAttack = false; // Mark the special attack as inactive when no bullets left to shoot
            isActive = false; // Also mark the attack as inactive
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
