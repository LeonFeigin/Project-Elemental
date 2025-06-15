package inventory.items;

import inventory.item;
import player.sprite;

public class advancedChestplate extends item {

    public advancedChestplate() {
        super(
            sprite.getImages(32, 8, "inventory/images/chestplates/")[2],
            "Advanced Chestplate",
            2, // Wearability for chestplate
            5, // Damage boost
            10, // Health boost
            0 // Attack speed boost
        );
    }
    
}
