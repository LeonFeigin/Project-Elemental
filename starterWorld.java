

import enemy.enemySpawner;
import player.playerWater;
import ui.mainUI;
import world.worldTemplate;

public class starterWorld extends worldTemplate {

    mainPanel myMainPanel;

    public starterWorld(mainPanel myMainPanel, int travelFrom) {
        //travelFrom = 0 means from leftWorld, 1 means from rightWorld, 2 means from boss world, 3 means from nowhere
        super(worldTemplate.loadAWorld("world/starterWorldTiles/grassTilesWorld.txt"),worldTemplate.loadAWorld("world/starterWorldTiles/pathTilesWorld.txt"),worldTemplate.loadAWorld("world/starterWorldTiles/collisionTilesWorld.txt"));
        if(travelFrom == 3){
            setCurrentPlayer(new playerWater(5786, 4839, this, null, null));
        }else if(travelFrom == 0){
            setCurrentPlayer(new playerWater(110, 420, this, null, null));
        }else if(travelFrom == 1){
            setCurrentPlayer(new playerWater(10070, 1797, this, null, null));
        }else if(travelFrom == 2){
            setCurrentPlayer(new playerWater(4883, 100, this, null, null));
        }
        currentUI = new mainUI(this);

        this.myMainPanel = myMainPanel;

        enemySpawners.add(new enemySpawner(1925, 3730, 0, this, 500, 5, 500));
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
    public void quitGame(){
        myMainPanel.setWorld(new mainMenu(myMainPanel));
        currentPlayer.savePlayerState();
    }
}
