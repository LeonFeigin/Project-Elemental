package enemy._enemies;

import enemy.enemyTemplate;
import world.worldTemplate;

public class slime2Enemy extends enemyTemplate{
    
    //int x, int y, worldTemplate currentWorld, int attackType, int attackDamage, int attackRange, float bulletSpeed, int bulletRange, int health, int maxSpeed, int attackCooldown, int inbetweenAttackCooldown, int defaultLeftAmountOfShooting, String name
    public slime2Enemy(int x, int y, worldTemplate currentWorld){
        super(x,y,currentWorld,
        1, // attackType
        10, // attackDamage
        500, // attackRange
        200, // closest player distance 
        1.3f, // bulletSpeed
        200, // bulletRange
        1000, // health
        1.2f, // maxSpeed
        5000,
        50,
        5,
        "slime2" // name
        );
    }
}
