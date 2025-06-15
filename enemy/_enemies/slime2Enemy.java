package enemy._enemies;

import enemy.enemyTemplate;
import inventory.item;
import inventory.items.advanceBoots;
import inventory.items.advancedChestplate;
import world.worldTemplate;

public class slime2Enemy extends enemyTemplate{
    
    worldTemplate currentWorld;

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
        this.currentWorld = currentWorld; // Store the current world reference
    }

    @Override
    public void lootDrop(){
        super.lootDrop();
        if (Math.random() > 0.5) { // 50% chance to drop
            item itemDrop = new advancedChestplate();
            double rarity = Math.random();
            if(rarity < 0.01){
                itemDrop.setHealthBoost((int)(Math.random() * 800 + 200)); // 200 to 1000 health boost
                itemDrop.setDamageBoost((int)(Math.random() * 400 + 100)); // 100, 500 damage boost
            }else if (rarity < 0.1) {
                itemDrop.setHealthBoost((int)(Math.random() * 500 + 100)); // 100 to 600 health boost
                itemDrop.setDamageBoost((int)(Math.random() * 300 + 50)); // 50 to 350 damage boost
            } else if (rarity < 0.3) {
                itemDrop.setHealthBoost((int)(Math.random() * 300 + 50)); // 50 to 350 health boost
                itemDrop.setDamageBoost((int)(Math.random() * 200 + 20)); // 20 to 220 damage boost
            } else {
                itemDrop.setHealthBoost((int)(Math.random() * 100 + 20)); // 20 to 120 health boost
                itemDrop.setDamageBoost((int)(Math.random() * 100 + 10)); // 10 to 110 damage boost
            }
            currentWorld.currentPlayer.inventory.addItem(itemDrop); // Add the item to the player's inventory
        }
    }
}
