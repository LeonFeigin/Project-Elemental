package enemy._enemies;

import enemy.enemyTemplate;
import world.worldTemplate;

public class peaShooterEnemy extends enemyTemplate{
    
    //int x, int y, worldTemplate currentWorld, int attackType, int attackDamage, int attackRange, float bulletSpeed, int bulletRange, int health, int maxSpeed, int attackCooldown, int inbetweenAttackCooldown, int defaultLeftAmountOfShooting, String name
    public peaShooterEnemy(int x, int y, worldTemplate currentWorld){
        super(x,y,currentWorld,
        0, // attackType
        50, // attackDamage
        300, // attackRange
        150, // closest player distance 
        1.2f, // bulletSpeed
        150, // bulletRange
        290, // health
        0.9f, // maxSpeed
        2000,
        100,
        1,
        "peaShooter" // name
        );
    }
}
