package player;

import attack.abilityAttacks;
import attack.attackTemplate;
import world.worldTemplate;

public class playerLightning extends playerTemplate {
    // Player water class that extends playerTemplate

    // public playerTemplate(int x, int y, 
    //                         int attackType, int elementType, int attackDamage, int attackRange, int attackCooldown, int inbetweenAttackCooldown, int attackSize, int bulletSpeed, 
    //                         int maxHealth, worldTemplate currentWorld, String playerNameDir, attackTemplate attack, String playerName, 
    //                         int specialAttackType, int specialAttackDamage, int specialAttackRange, int specialAttackCooldown, int specialInbetweenAttackCooldown, int specialAttackSize, int specialBulletSpeed, String specialName) {


    public playerLightning(int x, int y, worldTemplate currentWorld, attackTemplate attack, attackTemplate specialAttack) {
        super(x, y,
        0, abilityAttacks.LIGHTNING_ELEMENT, 10, 1000, 1000, 100, 5, 2,
        100, currentWorld, "playerLightningSprites",attack, "Lightning Guy",
        4, 20, 500, 5000, 100, 5, 3, "Tripple Strike", specialAttack);
    }
    
}
