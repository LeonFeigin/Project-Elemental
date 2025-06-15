

import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.MouseEvent;

import enemy.enemySpawner;
import enemy.enemyTemplate;
import enemy._enemies.dummyEnemy;
import player.playerWater;
import ui.mainUI;
import world.worldTemplate;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public class starterWorld extends worldTemplate implements KeyListener, MouseListener{

    mainPanel myMainPanel;

    public starterWorld(mainPanel myMainPanel, int travelFrom) {
        //travelFrom = 0 means from leftWorld, 1 means from rightWorld, 2 means from boss world, 3 means from nowhere
        super(worldTemplate.loadAWorld("world/starterWorldTiles/grassTilesWorld.txt"),worldTemplate.loadAWorld("world/starterWorldTiles/pathTilesWorld.txt"),worldTemplate.loadAWorld("world/starterWorldTiles/collisionTilesWorld.txt"));
        if(travelFrom == 3){
            setCurrentPlayer(new playerWater(5786, 4839, this, null, null,null,0,0,2,2));
        }else if(travelFrom == 0){
            setCurrentPlayer(new playerWater(110, 420, this, null, null,null,0,0,2,2));
        }else if(travelFrom == 1){
            setCurrentPlayer(new playerWater(10070, 1797, this, null, null,null,0,0,2,2));
        }else if(travelFrom == 2){
            setCurrentPlayer(new playerWater(4883, 100, this, null, null,null,0,0,2,2));
        }
        currentUI = new mainUI(this);
        currentUI.updateHealth(currentPlayer.getHealth());

        this.myMainPanel = myMainPanel;

        enemySpawners.add(new enemySpawner(1925, 3730, 0, this, 500, 5, 500));

        //6408 3673
        enemies.add(new dummyEnemy(6408, 3683, this));
    }

    @Override
    public void update() {
        super.update();
        //10112 right world
        if(currentPlayer.x > 10112){
            myMainPanel.setWorld(new rightWorld(myMainPanel));
        }else if(currentPlayer.x < 40){
            myMainPanel.setWorld(new leftWorld(myMainPanel));
        }else if(currentPlayer.y < 40){
            myMainPanel.setWorld(new bossWorld(myMainPanel));
        }
    }

    @Override
    public void paintComponent(Graphics g){
        //draw a black background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1280, 720);

        drawTiles(g, grassTilesWorld, grassTiles);
        drawTiles(g, pathTilesWorld, pathTiles);

        //tutorial text
        g.setColor(Color.BLACK);
        g.setFont(g.getFont().deriveFont(20f));
            // movement (spawn)
        g.drawString("Welcome To Project Elemental", 5640-worldXOffset, 4805-worldYOffset);
        g.drawString("To move around use WASD", 5655-worldXOffset, 4705-worldYOffset);
        g.drawString("Press E to open inventory", 5665-worldXOffset, 4605-worldYOffset);
            //attack (above spawn)
        g.drawString("To shoot, point your mouse and press left click", 6050-worldXOffset, 3530-worldYOffset);
        g.drawString("Press right click to use your special attack", 6050-worldXOffset, 3550-worldYOffset);
        g.drawString("Press 1-4 to switch between character with different elements", 6050-worldXOffset, 3600-worldYOffset);
        g.drawString("Elements match up cause the enemy to take greater damage", 6050-worldXOffset, 3620-worldYOffset);
            //Good luck (above attack)
        g.drawString("Good luck on your journey!", 6890-worldXOffset, 3140-worldYOffset);
        //map direction
            // first fork
        g.drawString("<- Boss / Weak Enemies / Hard Enemies", 6680-worldXOffset,2027-worldYOffset);
        g.drawString("Mid Enemies ->", 7010-worldXOffset,2068-worldYOffset);

        g.drawString("^ Boss Path", 5065-worldXOffset,1975-worldYOffset);
        g.drawString("<- Weak / Hard Enemies", 4446-worldXOffset,2035-worldYOffset);

        g.drawString("^ Hard Enemy", 1488-worldXOffset,2909-worldYOffset);
        g.drawString("  Weak Enemy \\/", 1620-worldXOffset,3045-worldYOffset);
    
        //draw player
        currentPlayer.draw(g,worldXOffset, worldYOffset);

        //draw enemy
        for(enemyTemplate enemy : enemies) {
            enemy.draw(g, worldXOffset, worldYOffset);
        }

        //draw UI
        currentUI.draw(g);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!currentUI.isInMenu()){
            if(e.getButton() == MouseEvent.BUTTON1){
                currentPlayer.attack(e.getX()+worldXOffset, e.getY()+worldYOffset); // Attack at the mouse click position
            }else if(e.getButton() == MouseEvent.BUTTON3){
                currentPlayer.specialAttack(e.getX()+worldXOffset, e.getY()+worldYOffset);
            }
            if(e.getX() > 10 && e.getX() < 10 + 32 && e.getY() > 10 && e.getY() < 10 + 32) {
                currentUI.setInMenu(true); // Open the menu if the settings icon is clicked
                currentUI.inPauseMenu = true;
                return;
            }
        }else{
            currentUI.mouseClicked(e.getX(), e.getY()); // Handle mouse press in the UI
        }
    }

    @Override
    public void quitGame(){
        myMainPanel.setWorld(new mainMenu(myMainPanel));
        if(!currentUI.deathMenu){
            currentPlayer.savePlayerState();
            currentPlayer.inventory.saveInventory();
        }
    }
}
