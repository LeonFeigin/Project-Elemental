package inventory.items;

import inventory.item;
import player.sprite;

public class opBoots extends item {

    public opBoots() {
        super(
            sprite.getImages(32, 8, "inventory/images/shoes/")[6],
            "OP Boots",
            0, // Wearability for boots
            0, // Damage boost
            0, // Health boost
            0 // Attack speed boost
        );
    }
    
}
