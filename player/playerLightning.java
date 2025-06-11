package player;

import attack.abilityAttacks;
import attack.attackTemplate;
import world.worldTemplate;

public class playerLightning extends playerTemplate {
    // Player water class that extends playerTemplate

    // playerTemplate(int x, int y, int attackType, int elementType, int attackDamage, int attackRange, int attackCooldown, int inbetweenAttackCooldown, int attackSize, int bulletSpeed, int maxHealth, worldTemplate currentWorld)
    public playerLightning(int x, int y, worldTemplate currentWorld, attackTemplate attack) {
        super(x, y, 0, abilityAttacks.LIGHTNING_ELEMENT, 10, 1000, 1000, 100, 5, 2, 100, currentWorld, "playerLightningSprites",attack);
    }
    
}
