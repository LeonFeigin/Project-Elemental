package inventory.items;

import inventory.item;
import player.sprite;

public class basicShoes extends item {

    public basicShoes() {
        super(
            sprite.getImages(32, 8, "inventory/images/shoes/")[0],
            "Basic Shoes",
            0,
            0,
            0,
            0
        );
    }
}
