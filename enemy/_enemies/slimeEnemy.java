package enemy._enemies;

import enemy.enemyTemplate;
import world.worldTemplate;

public class slimeEnemy extends enemyTemplate{
    
    //int x, int y, worldTemplate currentWorld, int attackType, int attackDamage, int attackRange, float bulletSpeed, int bulletRange, int health, int maxSpeed, int attackCooldown, int inbetweenAttackCooldown, int defaultLeftAmountOfShooting, String name
    public slimeEnemy(int x, int y, worldTemplate currentWorld){
        super(x,y,currentWorld,
        2, // attackType
        5, // attackDamage
        500, // attackRange
        200, // closest player distance 
        0.8f, // bulletSpeed
        350, // bulletRange
        500, // health
        0.7f, // maxSpeed
        5000,
        25,
        90,
        "slime" // name
        );
    }
}
