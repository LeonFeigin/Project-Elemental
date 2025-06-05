package ui;

import java.awt.Graphics;

import world.worldTemplate;

public class mainUI {
    
    worldTemplate currentWorld;

    public mainUI(worldTemplate currentWorld) {
        // Initialize the UI with the current world
        this.currentWorld = currentWorld;
    }

    public void update(){

    }

    public void draw(Graphics g){
        // Draw the UI elements here
        // For example, you can draw the player's health, score, etc.
        g.drawString("Player Health: " + currentWorld.currentPlayer.getHealth(), 10, 20);
        g.drawString("Player Position: (" + currentWorld.currentPlayer.x + ", " + currentWorld.currentPlayer.y + ")", 10, 40);
    }
}
