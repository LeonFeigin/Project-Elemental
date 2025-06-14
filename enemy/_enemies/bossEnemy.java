package enemy._enemies;

import enemy.enemyTemplate;
import world.worldTemplate;

public class bossEnemy extends enemyTemplate{
    
    //int x, int y, worldTemplate currentWorld, int attackType, int attackDamage, int attackRange, float bulletSpeed, int bulletRange, int health, int maxSpeed, int attackCooldown, int inbetweenAttackCooldown, int defaultLeftAmountOfShooting, String name
    public bossEnemy(int x, int y, worldTemplate currentWorld){
        super(x,y,currentWorld,
        3, // attackType
        200, // attackDamage
        20000, // attackRange
        300, // closest player distance 
        1.5f, // bulletSpeed
        300, // bulletRange
        50000, // health
        3f, // maxSpeed
        10000,
        10,
        360,
        "child" // name
        );
    }
}
