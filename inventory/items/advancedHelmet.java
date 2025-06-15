package inventory.items;

import inventory.item;
import player.sprite;

public class advancedHelmet extends item {

    public advancedHelmet() {
        super(
            sprite.getImages(32, 8, "inventory/images/hats/")[2],
            "Advanced Helmet",
            3, // Wearability for advanced helmet
            2, // Damage boost
            0, // Health boost
            0 // Attack speed boost
        );
    }
    
}
