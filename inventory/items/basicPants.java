package inventory.items;

import inventory.item;
import player.sprite;

public class basicPants extends item {

    public basicPants() {
        super(
            sprite.getImages(32, 8, "inventory/images/pants/")[1],
            "Basic Pants",
            1,
            0,
            0,
            0
        );
    }
    
}
