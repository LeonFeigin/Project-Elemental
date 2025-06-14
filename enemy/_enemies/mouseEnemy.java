package enemy._enemies;

import enemy.enemyTemplate;
import world.worldTemplate;

public class mouseEnemy extends enemyTemplate{
    
    //int x, int y, worldTemplate currentWorld, int attackType, int attackDamage, int attackRange, float bulletSpeed, int bulletRange, int health, int maxSpeed, int attackCooldown, int inbetweenAttackCooldown, int defaultLeftAmountOfShooting, String name
    public mouseEnemy(int x, int y, worldTemplate currentWorld){
        super(x,y,currentWorld,
        0, // attackType
        100, // attackDamage
        700, // attackRange
        300, // closest player distance 
        1.5f, // bulletSpeed
        150, // bulletRange
        250, // health
        1.5f, // maxSpeed
        3000,
        100,
        1,
        "mouse" // name
        );
    }
}
