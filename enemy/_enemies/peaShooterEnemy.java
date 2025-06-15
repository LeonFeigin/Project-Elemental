package enemy._enemies;

import enemy.enemyTemplate;
import inventory.item;
import inventory.items.basicPants;
import inventory.items.basicShoes;
import world.worldTemplate;

public class peaShooterEnemy extends enemyTemplate{
    
    worldTemplate currentWorld;

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

        this.currentWorld = currentWorld; // Store the current world reference
    }

    @Override
    public void lootDrop(){
        super.lootDrop();
        if (Math.random() > 0.5) { // 50% chance to drop
            item itemDrop = new basicShoes();
            double rarity = Math.random();
            if (rarity < 0.1) {
                itemDrop.setHealthBoost((int)(Math.random() * 10 + 10)); // 10 to 20 health boost
                itemDrop.setAttackSpeedBoost((float)(Math.random() * 0.2 + 0.1)); // 0.1 to 0.3 attack speed boost
            } else if (rarity < 0.3) {
                itemDrop.setHealthBoost((int)(Math.random() * 5 + 5)); // 5 to 10 health boost
                itemDrop.setAttackSpeedBoost((float)(Math.random() * 0.1 + 0.05)); // 0.05 to 0.15 attack speed boost
            } else {
                itemDrop.setHealthBoost((int)(Math.random() * 3 + 2)); // 2 to 5 health boost
                itemDrop.setAttackSpeedBoost((float)(Math.random() * 0.05 + 0.02)); // 0.02 to 0.07 attack speed boost
            }
            currentWorld.currentPlayer.inventory.addItem(itemDrop); // Add the item to the player's inventory
        }
    }
}
