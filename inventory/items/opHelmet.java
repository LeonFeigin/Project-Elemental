package inventory.items;

import inventory.item;
import player.sprite;

public class opHelmet extends item {

    public opHelmet() {
        super(
            sprite.getImages(32, 8, "inventory/images/hats/")[6],
            "OP Helmet",
            3, // Wearability for helmet
            5, // Damage boost
            10, // Health boost
            2 // Attack speed boost
        );
    }
    
}
