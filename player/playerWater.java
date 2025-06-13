package player;

import attack.abilityAttacks;
import attack.attackTemplate;
import world.worldTemplate;

public class playerWater extends playerTemplate {
    // Player water class that extends playerTemplate

    // public playerTemplate(int x, int y, 
    //                         int attackType, int elementType, int attackDamage, int attackRange, int attackCooldown, int inbetweenAttackCooldown, int attackSize, int bulletSpeed, 
    //                         int maxHealth, worldTemplate currentWorld, String playerNameDir, attackTemplate attack, String playerName, 
    //                         int specialAttackType, int specialAttackDamage, int specialAttackRange, int specialAttackCooldown, int specialInbetweenAttackCooldown, int specialAttackSize, int specialBulletSpeed, String specialName) {


    public playerWater(int x, int y, worldTemplate currentWorld, attackTemplate attack, attackTemplate specialAttack) {
        super(x, y,
        1, abilityAttacks.WATER_ELEMENT, 10, 200, 1000, 100, 3, 1,
        100, currentWorld, "playerWaterSprites",attack, "Water Guy", 
        0, 20,300, 5000, 100, 5, 2, "Huge Wave", specialAttack);
    }
    
}
