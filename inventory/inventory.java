package inventory;

import java.util.ArrayList;
import java.util.Scanner;

import inventory.items.*;
import world.worldTemplate;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.PrintWriter;

public class inventory {

    item[] currentEquiped = new item[4]; // Array to hold currently equipped items (0: shoes, 1: pants, 2: shirt, 3: hat)

    ArrayList<item> items = new ArrayList<item>(); // List of items in the inventory
    ArrayList<int[]> itemPositions = new ArrayList<int[]>(); // List of positions for each item in the inventory

    private boolean mousePressed = false; // Flag to check if the mouse is pressed
    private int itemIndexPressed = -1; // Index of the item that is currently pressed

    private worldTemplate currentWorld; // Reference to the current world

    private item itemHover;

    public inventory(worldTemplate currentWorld) {
        this.currentWorld = currentWorld; // Initialize the current world reference
        
    }

    public ArrayList<item> getItems(){
        return items; // Return the items in the inventory as an array
    }

    public item[] getEquipt() {
        return currentEquiped; // Return the currently equipped items
    }

    public void addItem(item newItem) {
        if(items.size() < 30){
            items.add(newItem);
            itemPositions.add(new int[]{0,0});
            updateItemPositions(); // Update the positions of items after adding a new item
        }
    }

    public void equipItem(item itemToEquip) {
        int itemWearability = itemToEquip.getItemWearability(); // Get the wearability of the item
        if (currentEquiped[itemWearability] == null) { // Check if the slot is empty
            currentEquiped[itemWearability] = itemToEquip; // Equip the item
            items.remove(itemToEquip); // Remove the item from the inventory
            updateItemPositions(); // Update the positions of items after equipping
        }
    }

    public void update(){
        if(mousePressed) {
            if(itemIndexPressed != -1){
                itemPositions.get(itemIndexPressed)[0] = currentWorld.mouseX - 32; // Update the x position of the pressed item to the current mouse position
                itemPositions.get(itemIndexPressed)[1] = currentWorld.mouseY - 32; // Update the y position of the pressed item to the current mouse position
            }
        }else{
            //check if mouse is hovering over an item
            boolean isHovering = false; // Flag to check if hovering over an item
            for (int i = 0; i < items.size(); i++) {
                int[] position = itemPositions.get(i);
                if (currentWorld.mouseX >= position[0] && currentWorld.mouseX <= position[0] + 64 && currentWorld.mouseY >= position[1] && currentWorld.mouseY <= position[1] + 64) {
                    itemHover = items.get(i); // Set the item to hover over
                    isHovering = true; // Set the hovering flag to true
                }
            }
            if (!isHovering) {
                itemHover = null; // Reset the item hover if not hovering over any item
            }
        }
    }

    public void removeItem(item itemToRemove) {
        items.remove(itemToRemove);
    }

    public void mousePressed(int x, int y) {
        //each inventory space is 64x64 + 19px spacing // starts at 235px
        mousePressed = true;
        for (int i = 0; i < items.size(); i++) {
            int[] position = itemPositions.get(i);
            if (x >= position[0] && x <= position[0] + 64 && y >= position[1] && y <= position[1] + 64) {
                itemIndexPressed = i; // Store the index of the pressed item
                return;
            }
        }

        //check if the mouse is pressed on an equipped item
        for (int i = 0; i < currentEquiped.length; i++) {
            if (x >= 680 + i * 84 && x <= 680 + i * 84 + 32 && y >= 175 && y <= 175 + 32) {
                if (currentEquiped[i] != null) {
                    items.add(currentEquiped[i]); // Add the equipped item back to the inventory
                    itemPositions.add(new int[]{235, 334}); // Add a new position for the item in the inventory
                    currentEquiped[i] = null; // Remove the item from equipped items
                    itemIndexPressed = items.size() - 1; // Set the index of the newly added item
                }
                return;
            }
        }
    }

    public void mouseReleased(int x, int y) {
        if(mousePressed && itemIndexPressed != -1) {
            int itemWearability = items.get(itemIndexPressed).getItemWearability(); // Get the wearability of the item

            if (currentEquiped[itemWearability] == null && x >= 680 + itemWearability * 84 && x <= 680 + itemWearability * 84 + 32 && y >= 175 && y <= 175 + 32) {
                currentEquiped[itemWearability] = items.get(itemIndexPressed); // Equip the item
                items.remove(itemIndexPressed); // Remove the item from the inventory
                itemPositions.remove(itemIndexPressed); // Remove the item's position
            }else if(x > 1025 && x < 1025+64 && y > 235 && y < 235+64) {
                // If the item is dropped in the trash can area, remove it from the inventory
                items.remove(itemIndexPressed);
                itemPositions.remove(itemIndexPressed); // Remove the item's position
            }
            mousePressed = false; // Reset the mouse pressed flag
            itemIndexPressed = -1; // Reset the item index pressed
            updateItemPositions();
        }
    }

    public void updateItemPositions() {
        // Update the positions of items in the inventory based on their index
        for (int i = 0; i < items.size(); i++) {
            int x = 235 + (i % 10) * 64 + (i % 10) * 19; // Calculate x position
            int y = 334 + (i / 10) * 64 + (i / 10) * 19; // Calculate y position
            itemPositions.set(i, new int[]{x, y}); // Update the position of the item
        }
    }

    public void draw(Graphics g) {
        // draw items in the inventory
        for (int i = 0; i < items.size(); i++) {
            item currentItem = items.get(i);
            int[] position = itemPositions.get(i);
            currentItem.draw(g, position[0], position[1], 64, 64); // Draw each item at its calculated position
        }

        //draw equipped items starts at 680, 175 and has x spacing of 84
        for (int i = 0; i < currentEquiped.length; i++) {
            if (currentEquiped[i] != null) {
                currentEquiped[i].draw(g, 681 + i * 84, 176, 32, 32); // Draw each equipped item
            }
        }

        if(itemHover != null && !mousePressed) {
            // Draw item hover effect
            drawItemHover(g, currentWorld.mouseX, currentWorld.mouseY, itemHover);
        }
    }

    public void drawItemHover(Graphics g, int x, int y, item item) {
        int itemHeight = 110; // Height of the item hover box

        //draw item name
        g.setColor(new Color(0,0,0,200));
        g.fillRect(x, y, 300, itemHeight); // Draw a semi-transparent background for the item hover
        g.setFont(g.getFont().deriveFont(20f)); // Set font size for the item name
        g.setColor(Color.WHITE);
        g.drawString(item.getItemName(), x + 5, y + 22); // Draw the item name
        
        //draw item wearability
        g.setFont(g.getFont().deriveFont(16f)); // Set font size for the item name
        if(item.getItemWearability() == 0) {
            g.drawString("Wear: Shoes", x + 5, y + 45); // Draw the item wearability
        } else if(item.getItemWearability() == 1) {
            g.drawString("Wear: Pants", x + 5, y + 45); // Draw the item wearability
        } else if(item.getItemWearability() == 2) {
            g.drawString("Wear: Shirt", x + 5, y + 45); // Draw the item wearability
        } else if(item.getItemWearability() == 3) {
            g.drawString("Wear: Hat", x + 5, y + 45); // Draw the item wearability
        }

        //draw item boosts
        g.drawString("Damage Boost: " + item.getDamageBoost(), x + 5, y + 61); // Draw the damage boost
        g.drawString("Health Boost: " + item.getHealthBoost(), x + 5, y + 77); // Draw the health boost
        g.drawString("Attack Speed Boost: " + Math.round(item.getAttackSpeedBoost()*100)/100f, x + 5, y + 93); // Draw the attack speed boost
    }

    public void saveInventory() {
        try {
            File file = new File("inventory/savedInventory.txt");
            if (!file.exists()) {
                file.createNewFile(); // Create the file if it doesn't exist
            }
            PrintWriter pw = new PrintWriter(file);
            for (item currentItem : items) {
                pw.println(currentItem.getItemName() + "," + currentItem.getDamageBoost() + "," + currentItem.getHealthBoost() + "," + currentItem.getAttackSpeedBoost());
            }
            pw.close(); // Close the PrintWriter to save changes
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public item getItem(String itemName) {
        if(itemName.equals("Basic Shoes")) {
            return new basicShoes();
        } else if(itemName.equals("Advanced Boots")) {
            return new advanceBoots();
        } else if(itemName.equals("Basic Pants")) {
            return new basicPants();
        } else if(itemName.equals("Advanced Leggings")) {
            return new advancedLeggings();
        } else if(itemName.equals("Basic Chestplate")) {
            return new basicChestplate();
        } else if(itemName.equals("Advanced Chestplate")) {
            return new advancedChestplate();
        } else if(itemName.equals("Basic Helmet")) {
            return new basicHelmet();
        } else if(itemName.equals("Advanced Helmet")) {
            return new advancedHelmet();
        } else if(itemName.equals("OP Boots")) {
            return new opBoots();
        } else if(itemName.equals("OP Leggings")) {
            return new opLeggings();
        } else if(itemName.equals("OP Chestplate")) {
            return new opChestplate();
        } else if(itemName.equals("OP Helmet")) {
            return new opHelmet();
        }
        return null; // Return null if the item is not found
    }

    public void loadInventory() {
        try {
            File file = new File("inventory/savedInventory.txt");
            if (file.exists()) {
                items.clear(); // Clear the current items before loading
                itemPositions.clear(); // Clear the current item positions before loading
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        item newItem = getItem(parts[0]); // Initialize a new item variable
                        int damageBoost = Integer.parseInt(parts[1]);
                        int healthBoost = Integer.parseInt(parts[2]);
                        float attackSpeedBoost = Float.parseFloat(parts[3]);
                        
                        newItem.setDamageBoost(damageBoost);
                        newItem.setHealthBoost(healthBoost);
                        newItem.setAttackSpeedBoost(attackSpeedBoost);
                        addItem(newItem); // Add the new item to the inventory
                    }
                }
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }
}
