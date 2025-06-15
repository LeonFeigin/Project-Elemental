package enemy._enemies;

import enemy.enemyTemplate;
import inventory.item;
import inventory.items.basicChestplate;
import world.worldTemplate;

public class greenBambooEnemy extends enemyTemplate{
    
    worldTemplate currentWorld;

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
        this.currentWorld = currentWorld;
    }

    @Override
    public void lootDrop(){
        super.lootDrop();
        if (Math.random() > 0.5) { // 50% chance to drop
            item itemDrop = new basicChestplate();
            double rarity = Math.random();
            if (rarity < 0.1) {
                itemDrop.setDamageBoost((int)Math.random() * 5 + 5); // 5 to 10 damage boost
                itemDrop.setHealthBoost((int)Math.random() * 10 + 10); // 10 to 20 health boost
            } else if (rarity < 0.3) {
                itemDrop.setDamageBoost((int)Math.random() * 3 + 3); // 3 to 6 damage boost
                itemDrop.setHealthBoost((int)Math.random() * 5 + 5); // 5 to 10 health boost
            } else {
                itemDrop.setDamageBoost((int)Math.random() * 2 + 1); // 1 to 3 damage boost
                itemDrop.setHealthBoost((int)Math.random() * 2 + 1); // 1 to 3 health boost
            }
            currentWorld.currentPlayer.inventory.addItem(itemDrop); // Add the item to the player's inventory
        }
    }
}
