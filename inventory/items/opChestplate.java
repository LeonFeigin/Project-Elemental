package inventory.items;

import inventory.item;
import player.sprite;

public class opChestplate extends item {

    public opChestplate() {
        super(
            sprite.getImages(32, 8, "inventory/images/chestplates/")[6],
            "OP Chestplate",
            2, // Wearability for chestplate
            10, // Damage boost
            5, // Health boost
            0 // Attack speed boost
        );
    }
    
}
