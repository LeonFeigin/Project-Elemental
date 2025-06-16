package enemy._enemies;

import enemy.enemyTemplate;
import inventory.item;
import inventory.items.advanceBoots;
import inventory.items.advancedLeggings;
import world.worldTemplate;

public class batEnemy extends enemyTemplate{
    
    worldTemplate currentWorld;

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
        this.currentWorld = currentWorld; // Store the current world for loot dropping
    }

    @Override
    public void lootDrop(){
        super.lootDrop();
        if (Math.random() > 0.5) { // 50% chance to drop
            item itemDrop = new advancedLeggings();
            double rarity = Math.random();
            if(rarity < 0.01){
                itemDrop.setHealthBoost((int)(Math.random() * 800 + 200)); // 200 to 1000 health boost
                itemDrop.setAttackSpeedBoost((float)(Math.random() * 0.3 + 0.2)); // 0.2 to 0.5 attack speed boost
            }else if (rarity < 0.1) {
                itemDrop.setHealthBoost((int)(Math.random() * 500 + 100)); // 100 to 600 health boost
                itemDrop.setAttackSpeedBoost((float)(Math.random() * 0.2 + 0.1)); // 0.1 to 0.3 attack speed boost
            } else if (rarity < 0.3) {
                itemDrop.setHealthBoost((int)(Math.random() * 300 + 50)); // 50 to 350 health boost
                itemDrop.setAttackSpeedBoost((float)(Math.random() * 0.1 + 0.05)); // 0.05 to 0.15 attack speed boost
            } else {
                itemDrop.setHealthBoost((int)(Math.random() * 100 + 20)); // 20 to 120 health boost
                itemDrop.setAttackSpeedBoost((float)(Math.random() * 0.05 + 0.02)); // 0.02 to 0.07 attack speed boost
            }
            currentWorld.currentPlayer.inventory.addItem(itemDrop); // Add the item to the player's inventory
        }
        currentWorld.currentPlayer.inventory.saveInventory();
    }
}
