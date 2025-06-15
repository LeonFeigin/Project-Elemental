package inventory.items;

import inventory.item;
import player.sprite;

public class basicChestplate extends item {

    public basicChestplate() {
        super(
            sprite.getImages(32, 8, "inventory/images/chestplates/")[1],
            "Basic Chestplate",
            2, // Wearability for chestplate
            0, // Damage boost
            0, // Health boost
            0 // Attack speed boost
        );
    }
    
}
