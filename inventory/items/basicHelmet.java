package inventory.items;

import inventory.item;
import player.sprite;

public class basicHelmet extends item{

    public basicHelmet() {
        super(
            sprite.getImages(32, 8, "inventory/images/hats/")[0],
            "Basic Helmet",
            3, // Wearability for helmet
            0, // Damage boost
            0, // Health boost
            0 // Attack speed boost
        );
    }
    
}
