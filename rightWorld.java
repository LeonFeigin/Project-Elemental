

import enemy.enemySpawner;
import player.playerWater;
import ui.mainUI;
import world.worldTemplate;

public class rightWorld extends worldTemplate {

    mainPanel myMainPanel;

    public rightWorld(mainPanel myMainPanel) {
        super(worldTemplate.loadAWorld("world/rightWorldTiles/grassTilesWorld.txt"),worldTemplate.loadAWorld("world/rightWorldTiles/pathTilesWorld.txt"),worldTemplate.loadAWorld("world/rightWorldTiles/collisionWorld.txt"));
        setCurrentPlayer(new playerWater(115, 550, this, null, null)); //60, 615
        currentUI = new mainUI(this);
        currentUI.updateHealth(currentPlayer.getHealth());

        this.myMainPanel = myMainPanel;

        //grey bamboo enemy spawner at the top 
        enemySpawners.add(new enemySpawner(3914, 558, 1, this, 2000, 10, 400));

        //pea shooter enemy spawner at the bottom
        enemySpawners.add(new enemySpawner(3950, 3520, 2, this, 2000, 10, 300));

        //trex enemy spawner at the small area
        enemySpawners.add(new enemySpawner(5692, 1935, 4, this, 2000, 3, 100));

        //slime enemy spawner at the right
        enemySpawners.add(new enemySpawner(8037, 1201, 3, this, 2000, 10, 300));
    }

    @Override
    public void update() {
        super.update();
        if(currentPlayer.x < 40){
            myMainPanel.setWorld(new starterWorld(myMainPanel, 1));
        }
    }

    @Override
    public void quitGame(){
        myMainPanel.setWorld(new mainMenu(myMainPanel));
        if(!currentUI.deathMenu){
            currentPlayer.savePlayerState();
        }
    }
}
