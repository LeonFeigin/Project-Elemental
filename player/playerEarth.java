package player;

import attack.abilityAttacks;
import attack.attackTemplate;
import inventory.inventory;
import world.worldTemplate;

public class playerEarth extends playerTemplate {
    // Player fire class that extends playerTemplate

    // public playerTemplate(int x, int y, 
    //                         int attackType, int elementType, int attackDamage, int attackRange, int attackCooldown, int inbetweenAttackCooldown, int attackSize, int bulletSpeed, 
    //                         int maxHealth, worldTemplate currentWorld, String playerNameDir, attackTemplate attack, String playerName, 
    //                         int specialAttackType, int specialAttackDamage, int specialAttackRange, int specialAttackCooldown, int specialInbetweenAttackCooldown, int specialAttackSize, int specialBulletSpeed, String specialName) {


    public playerEarth(int x, int y, worldTemplate currentWorld, attackTemplate attack, attackTemplate specialAttack, inventory inventory, int xVel, int yVel, float speed, float maxSpeed) {
        super(x,y,
        1,abilityAttacks.EARTH_ELEMENT, 25, 100, 1000, 100, 3, 1,
        100, currentWorld, "playerEarthSprites", attack, "Earth Guy", 
        2, 10, 200, 5000, 10, 45, 1, "Tornado", specialAttack, inventory, xVel, yVel, speed, maxSpeed);
    }
    
}
