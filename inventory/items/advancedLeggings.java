package inventory.items;

import inventory.item;
import player.sprite;

public class advancedLeggings extends item {

    public advancedLeggings() {
        super(
            sprite.getImages(32, 8, "inventory/images/pants/")[3],
            "Advanced Leggings",
            1, // Wearability for leggings
            0, // Damage boost
            0, // Health boost
            0 // Attack speed boost
        );
    }
}
