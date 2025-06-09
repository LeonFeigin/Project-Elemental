package player;

import attack.abilityAttacks;
import world.worldTemplate;

public class playerWater extends playerTemplate {
    // Player water class that extends playerTemplate

    // playerTemplate(int x, int y, int attackType, int elementType, int attackDamage, int attackRange, int attackCooldown, int inbetweenAttackCooldown, int attackSize, int bulletSpeed, int maxHealth, worldTemplate currentWorld)
    public playerWater(int x, int y, worldTemplate currentWorld) {
        super(x, y, 0, abilityAttacks.WATER_ELEMENT, 10, 100, 500, 100, 5, 1, 100, currentWorld, "playerWaterSprites");
    }
    
}
