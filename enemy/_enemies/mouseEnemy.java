package enemy._enemies;

import enemy.enemyTemplate;
import inventory.item;
import inventory.items.advancedChestplate;
import inventory.items.advancedHelmet;
import world.worldTemplate;

public class mouseEnemy extends enemyTemplate{
    
    worldTemplate currentWorld;

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
        this.currentWorld = currentWorld; // Store the current world reference
    }

    @Override
    public void lootDrop(){
        super.lootDrop();
        if(currentWorld.currentPlayer == null) {
            return; // If there is no current player, do not drop loot
        }
        if (Math.random() > 0.5) { // 50% chance to drop
            item itemDrop = new advancedHelmet();
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
        currentWorld.currentPlayer.inventory.saveInventory();
    }
}
