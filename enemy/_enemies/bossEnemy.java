package enemy._enemies;

import enemy.enemyTemplate;
import inventory.item;
import inventory.items.basicPants;
import inventory.items.opBoots;
import inventory.items.opChestplate;
import inventory.items.opHelmet;
import world.worldTemplate;

public class bossEnemy extends enemyTemplate{
    
    worldTemplate currentWorld;

    //int x, int y, worldTemplate currentWorld, int attackType, int attackDamage, int attackRange, float bulletSpeed, int bulletRange, int health, int maxSpeed, int attackCooldown, int inbetweenAttackCooldown, int defaultLeftAmountOfShooting, String name
    public bossEnemy(int x, int y, worldTemplate currentWorld){
        super(x,y,currentWorld,
        3, // attackType
        200, // attackDamage
        20000, // attackRange
        300, // closest player distance 
        1.5f, // bulletSpeed
        300, // bulletRange
        50000, // health
        3f, // maxSpeed
        10000,
        10,
        360,
        "child" // name
        );

        this.currentWorld = currentWorld; // Store the current world reference
    }

    @Override
    public void lootDrop(){
        super.lootDrop();
        item[] itemSelection = new item[]{new opBoots(), new basicPants(), new opChestplate(), new opHelmet()}; // Possible item drops
        int randomIndex = (int)(Math.random() * itemSelection.length); // Randomly select an item from the array
        item itemDrop = itemSelection[randomIndex]; // Get the randomly selected item
        double rarity = Math.random();
        if (rarity < 0.1) {
            itemDrop.setDamageBoost((int)(Math.random() * 4000 + 1000)); // 1000 to 5000 damage boost
            itemDrop.setHealthBoost((int)(Math.random() * 9000 + 1000)); // 1000 to 10000 health boost
            itemDrop.setAttackSpeedBoost((float)(Math.random() * 0.5 + 0.99f)); // 0.5 to 0.9 attack speed boost
        } else if (rarity < 0.3) {
            itemDrop.setDamageBoost((int)(Math.random() * 2000 + 500)); // 500 to 2500 damage boost
            itemDrop.setHealthBoost((int)(Math.random() * 5000 + 500)); // 500 to 6000 health boost
            itemDrop.setAttackSpeedBoost((float)(Math.random() * 0.3 + 0.5f)); // 0.3 to 0.8 attack speed boost
        } else {
            itemDrop.setDamageBoost((int)(Math.random() * 1000 + 200)); // 200 to 1200 damage boost
            itemDrop.setHealthBoost((int)(Math.random() * 2000 + 200)); // 200 to 2200 health boost
            itemDrop.setAttackSpeedBoost((float)(Math.random() * 0.2 + 0.3f)); // 0.2 to 0.5 attack speed boost
        }
        currentWorld.currentPlayer.inventory.addItem(itemDrop); // Add the item to the player's inventory
    }
}
