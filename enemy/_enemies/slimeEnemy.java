package enemy._enemies;

import enemy.enemyTemplate;
import inventory.item;
import inventory.items.basicHelmet;
import inventory.items.basicShoes;
import world.worldTemplate;

public class slimeEnemy extends enemyTemplate{
    
    worldTemplate currentWorld;

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
        this.currentWorld = currentWorld; // Store the current world reference
    }

    @Override
    public void lootDrop(){
        super.lootDrop();
        if (Math.random() > 0.5) { // 50% chance to drop
            item itemDrop = new basicHelmet();
            double rarity = Math.random();
            if (rarity < 0.1) {
                itemDrop.setHealthBoost((int)(Math.random() * 10 + 10)); // 10 to 20 health boost
                itemDrop.setDamageBoost((int)(Math.random() * 5 + 5)); // 5 to 10 damage boost
            } else if (rarity < 0.3) {
                itemDrop.setHealthBoost((int)(Math.random() * 5 + 5)); // 5 to 10 health boost
                itemDrop.setDamageBoost((int)(Math.random() * 3 + 3)); // 3 to 6 damage boost
            } else {
                itemDrop.setHealthBoost((int)(Math.random() * 3 + 2)); // 2 to 5 health boost
                itemDrop.setDamageBoost((int)(Math.random() * 2 + 1)); // 1 to 3 damage boost
            }
            currentWorld.currentPlayer.inventory.addItem(itemDrop); // Add the item to the player's inventory
        }
    }
}
