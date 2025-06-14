package enemy._enemies;

import enemy.enemyTemplate;
import world.worldTemplate;

public class greenBambooEnemy extends enemyTemplate{
    
    //int x, int y, worldTemplate currentWorld, int attackType, int attackDamage, int attackRange, float bulletSpeed, int bulletRange, int health, int maxSpeed, String name
    public greenBambooEnemy(int x, int y, worldTemplate currentWorld){
        super(x,y,currentWorld,
        0, // attackType
        10, // attackDamage
        400, // attackRange
        100, // closest player distance 
        1.0f, // bulletSpeed
        100, // bulletRange
        100, // health
        1.0f, // maxSpeed
        2000, // attackCooldown
        100, // inbetweenAttackCooldown
        3, // defaultLeftAmountOfShooting
        "greenBamboo" // name
        );
    }
}
