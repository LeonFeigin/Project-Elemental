package ui;

import java.awt.Color;
import java.awt.Graphics;

import world.worldTemplate;

public class mainUI {
    
    worldTemplate currentWorld;

    int frames = 0;
    long startTime = System.currentTimeMillis();

    public mainUI(worldTemplate currentWorld) {
        // Initialize the UI with the current world
        this.currentWorld = currentWorld;
    }

    public void update(){
        frames++;
    }

    public void draw(Graphics g){
        //all debugging information
        if(currentWorld.debugMode){
            //debug info
            g.drawString("Player Health: " + currentWorld.currentPlayer.getHealth(), 10, 20);
            g.drawString("Player Position: (" + currentWorld.currentPlayer.x + ", " + currentWorld.currentPlayer.y + ")", 10, 40);


            //calculate FPS
            frames++;
            long currentTime = System.currentTimeMillis();
            g.drawString("FPS: " + (frames * 1000 / (currentTime - startTime)), 10, 60);

            if(currentTime - startTime >= 1000) {
                frames = 0;
                startTime = currentTime;
            }

            //collision render
            g.setColor(Color.RED);
            for (int i = 0; i < currentWorld.getCollideTiles().length; i++) {
            for (int j = 0; j < currentWorld.getCollideTiles()[i].length; j++) {
                if(currentWorld.getCollideTiles()[i][j] == 1) {
                    g.drawRect(j * 32-currentWorld.worldXOffset, i * 32-currentWorld.worldYOffset, 32, 32);
                }
            }
        }
        }
        
    }
}
