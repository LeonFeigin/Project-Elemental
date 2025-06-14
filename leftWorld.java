import enemy.enemySpawner;
import player.playerWater;
import ui.mainUI;
import world.worldTemplate;

public class leftWorld extends worldTemplate {

    mainPanel myMainPanel;

    public leftWorld(mainPanel myMainPanel) {
        super(worldTemplate.loadAWorld("world/leftWorldTiles/grassTilesWorld.txt"),worldTemplate.loadAWorld("world/leftWorldTiles/pathTilesWorld.txt"),worldTemplate.loadAWorld("world/leftWorldTiles/collisionWorld.txt"));

        this.myMainPanel = myMainPanel;

        setCurrentPlayer(new playerWater(9937, 1740, this, null, null));
        currentUI = new mainUI(this);

        //bat spawner at the bottom 
        enemySpawners.add(new enemySpawner(4012, 3102, 5, this, 2000, 10, 300)); 

        //slime 2 spawner at the top
        enemySpawners.add(new enemySpawner(5544, 638, 6, this, 2000, 15, 500));

        //mouse spawner at the left
        enemySpawners.add(new enemySpawner(1534, 2300, 7, this, 2000, 15, 500));
    }

    @Override
    public void update() {
        super.update();

        if(currentPlayer.x > 10145){
            myMainPanel.setWorld(new starterWorld(myMainPanel, 0));
        }
    }

    @Override
    public void quitGame(){
        myMainPanel.setWorld(new mainMenu(myMainPanel));
        currentPlayer.savePlayerState();
    }
}