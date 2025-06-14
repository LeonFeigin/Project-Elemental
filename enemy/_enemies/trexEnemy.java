package enemy._enemies;

import enemy.enemyTemplate;
import world.worldTemplate;

public class trexEnemy extends enemyTemplate{
    
    //int x, int y, worldTemplate currentWorld, int attackType, int attackDamage, int attackRange, float bulletSpeed, int bulletRange, int health, int maxSpeed, int attackCooldown, int inbetweenAttackCooldown, int defaultLeftAmountOfShooting, String name
    public trexEnemy(int x, int y, worldTemplate currentWorld){
        super(x,y,currentWorld,
        1, // attackType
        10, // attackDamage
        200, // attackRange
        50, // closest player distance 
        1f, // bulletSpeed
        200, // bulletRange
        750, // health
        0.7f, // maxSpeed
        5000,
        50,
        5,
        "trex" // name
        );
    }
}
