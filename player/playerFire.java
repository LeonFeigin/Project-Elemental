package player;

import attack.abilityAttacks;
import attack.attackTemplate;
import world.worldTemplate;

public class playerFire extends playerTemplate {
    // Player fire class that extends playerTemplate

    //playerTemplate(int x, int y, int attackType, int elementType, int attackDamage, int attackRange, int attackCooldown, int inbetweenAttackCooldown, int attackSize, int bulletSpeed, int maxHealth, worldTemplate currentWorld) {
    public playerFire(int x, int y, worldTemplate currentWorld, attackTemplate attack) {
        super(x,y,0,abilityAttacks.FIRE_ELEMENT, 50, 300, 500, 100, 1, 1, 100, currentWorld, "playerFireSprites",attack, "Fire Guy", 5000, 3, "Fire Wall", 50);
    }
    
}
