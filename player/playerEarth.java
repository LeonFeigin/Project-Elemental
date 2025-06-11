package player;

import attack.abilityAttacks;
import attack.attackTemplate;
import world.worldTemplate;

public class playerEarth extends playerTemplate {
    // Player fire class that extends playerTemplate

    //playerTemplate(int x, int y, int attackType, int elementType, int attackDamage, int attackRange, int attackCooldown, int inbetweenAttackCooldown, int attackSize, int bulletSpeed, int maxHealth, worldTemplate currentWorld) {
    public playerEarth(int x, int y, worldTemplate currentWorld, attackTemplate attack) {
        super(x,y,1,abilityAttacks.EARTH_ELEMENT, 25, 100, 1000, 100, 3, 1, 100, currentWorld, "playerEarthSprites", attack);
    }
    
}
