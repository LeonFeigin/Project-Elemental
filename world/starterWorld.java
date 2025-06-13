package world;

import enemy.enemySpawner;
import enemy._enemies.greenBambooEnemy;
import player.playerWater;
import ui.mainUI;

public class starterWorld extends worldTemplate {

    public starterWorld() {
        super(worldTemplate.loadAWorld("world/starterWorldTiles/grassTilesWorld.txt"),worldTemplate.loadAWorld("world/starterWorldTiles/pathTilesWorld.txt"),worldTemplate.loadAWorld("world/starterWorldTiles/collisionTilesWorld.txt"));
        setCurrentPlayer(new playerWater(1925, 3730, this, null, null)); //5786, 4839
        currentUI = new mainUI(this);

        enemySpawners.add(new enemySpawner(1925, 3730, 0, this, 500, 5, 500));
    }

    @Override
    public void update() {
        // Update logic for the starter world can be added here
        super.update();

    }
}
