package player;

import attack.abilityAttacks;
import attack.attackTemplate;
import world.worldTemplate;

public class playerWater extends playerTemplate {
    // Player water class that extends playerTemplate

    // playerTemplate(int x, int y, int attackType, int elementType, int attackDamage, int attackRange, int attackCooldown, int inbetweenAttackCooldown, int attackSize, int bulletSpeed, int maxHealth, worldTemplate currentWorld)
    public playerWater(int x, int y, worldTemplate currentWorld, attackTemplate attack) {
        super(x, y, 1, abilityAttacks.WATER_ELEMENT, 10, 200, 1000, 100, 3, 1, 100, currentWorld, "playerWaterSprites",attack, "Water Guy", 5000, 0, "Huge Wave", 10);
    }
    
}
