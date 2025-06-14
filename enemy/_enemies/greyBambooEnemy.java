package enemy._enemies;

import enemy.enemyTemplate;
import world.worldTemplate;

public class greyBambooEnemy extends enemyTemplate{
    
    //int x, int y, worldTemplate currentWorld, int attackType, int attackDamage, int attackRange, float bulletSpeed, int bulletRange, int health, int maxSpeed, int attackCooldown, int inbetweenAttackCooldown, int defaultLeftAmountOfShooting, String name
    public greyBambooEnemy(int x, int y, worldTemplate currentWorld){
        super(x,y,currentWorld,
        0, // attackType
        20, // attackDamage
        500, // attackRange
        50, // closest player distance 
        1.3f, // bulletSpeed
        200, // bulletRange
        150, // health
        1.2f, // maxSpeed
        2000, // attackCooldown
        100, // inbetweenAttackCooldown
        3, // defaultLeftAmountOfShooting
        "greyBamboo" // name
        );
    }
}
