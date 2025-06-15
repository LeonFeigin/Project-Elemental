package inventory;

import java.awt.image.BufferedImage;

public class item {
    private BufferedImage itemImage; // Image of the item
    private String itemName; // Name of the item
    private int itemWearability; // 0 = shoes, 1 = pants, 2 = shirt, 3 = hat
    private int damageBoost;
    private int healthBoost; // Health boost provided by the item
    private float attackSpeedBoost; // Attack speed boost provided by the item

    public item(BufferedImage itemImage, String itemName, int itemWearability, int damageBoost, int healthBoost, int attackSpeedBoost) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemWearability = itemWearability;
        this.damageBoost = damageBoost;
        this.healthBoost = healthBoost;
        this.attackSpeedBoost = attackSpeedBoost;
    }

    public void draw(java.awt.Graphics g, int x, int y, int width, int height) {
        g.drawImage(itemImage, x, y, width, height, null);
    }

    // setters and getters

    public String getItemName() {
        return itemName;
    }

    public int getItemWearability() {
        return itemWearability;
    }

    public int getDamageBoost() {
        return damageBoost;
    }

    public int getHealthBoost() {
        return healthBoost;
    }

    public float getAttackSpeedBoost() {
        return attackSpeedBoost;
    }

    public void setDamageBoost(int damageBoost) {
        this.damageBoost = damageBoost;
    }

    public void setHealthBoost(int healthBoost) {
        this.healthBoost = healthBoost;
    }

    public void setAttackSpeedBoost(float attackSpeedBoost) {
        this.attackSpeedBoost = attackSpeedBoost;
    }
}
