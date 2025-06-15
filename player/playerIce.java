package player;

import attack.abilityAttacks;
import attack.attackTemplate;
import inventory.inventory;
import world.worldTemplate;

public class playerIce extends playerTemplate {
    // Player fire class that extends playerTemplate

    // public playerTemplate(int x, int y, 
    //                         int attackType, int elementType, int attackDamage, int attackRange, int attackCooldown, int inbetweenAttackCooldown, int attackSize, int bulletSpeed, 
    //                         int maxHealth, worldTemplate currentWorld, String playerNameDir, attackTemplate attack, String playerName, 
    //                         int specialAttackType, int specialAttackDamage, int specialAttackRange, int specialAttackCooldown, int specialInbetweenAttackCooldown, int specialAttackSize, int specialBulletSpeed, String specialName) {


    public playerIce(int x, int y, worldTemplate currentWorld, attackTemplate attack, attackTemplate specialAttack, inventory inventory, int xVel, int yVel, float speed, float maxSpeed) {
        super(x,y,
        0,abilityAttacks.ICE_ELEMENT, 50, 100, 500, 100, 1, 1,
        100, currentWorld, "playerIceSprites", attack, "Ice Guy", 
        3, 100, 200, 2000, 20, 3, 2, "HUGE Icicle", specialAttack, inventory, xVel, yVel, speed, maxSpeed);
    }
    
}
