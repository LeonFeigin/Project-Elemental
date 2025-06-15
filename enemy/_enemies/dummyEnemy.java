package enemy._enemies;

import java.awt.Color;
import java.util.ArrayList;

import enemy.enemyTemplate;
import world.worldTemplate;

public class dummyEnemy extends enemyTemplate{
    
    ArrayList<int[]> damageTaken = new ArrayList<>();

    //int x, int y, worldTemplate currentWorld, int attackType, int attackDamage, int attackRange, float bulletSpeed, int bulletRange, int health, int maxSpeed, int attackCooldown, int inbetweenAttackCooldown, int defaultLeftAmountOfShooting, String name
    public dummyEnemy(int x, int y, worldTemplate currentWorld){
        super(x,y,currentWorld,
        1, // attackType
        10, // attackDamage
        200, // attackRange
        50, // closest player distance 
        1f, // bulletSpeed
        200, // bulletRange
        Integer.MAX_VALUE, // health
        0.7f, // maxSpeed
        5000,
        50,
        5,
        "trex" // name
        );
    }

    @Override
    public void update() {
        //dont update the dummy enemy
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        damageTaken.add(new int[]{damage, (int)y, 255}); // store damage and the time it was taken
    }

    @Override
    public void draw(java.awt.Graphics g, int worldXOffset, int worldYOffset) {
        super.draw(g, worldXOffset, worldYOffset);
        for (int i = 0; i < damageTaken.size(); i++) {
            g.setColor(new Color(0,0,0, damageTaken.get(i)[2]));
            damageTaken.get(i)[2] -= 5; // fade out the damage text
            damageTaken.get(i)[1] -= 2;
            if (damageTaken.get(i)[2] <= 0) {
                damageTaken.remove(i);
                i--;
            } else {
                g.drawString("-" + damageTaken.get(i)[0], (int)x - worldXOffset, damageTaken.get(i)[1] - worldYOffset);
            }
        }
    }
}
