package enemy._enemies;

import enemy.enemyTemplate;
import world.worldTemplate;

public class batEnemy extends enemyTemplate{
    
    //int x, int y, worldTemplate currentWorld, int attackType, int attackDamage, int attackRange, float bulletSpeed, int bulletRange, int health, int maxSpeed, int attackCooldown, int inbetweenAttackCooldown, int defaultLeftAmountOfShooting, String name
    public batEnemy(int x, int y, worldTemplate currentWorld){
        super(x,y,currentWorld,
        2, // attackType
        20, // attackDamage
        300, // attackRange
        100, // closest player distance 
        1f, // bulletSpeed
        300, // bulletRange
        1000, // health
        1f, // maxSpeed
        5000,
        25,
        90,
        "bat" // name
        );
    }
}
