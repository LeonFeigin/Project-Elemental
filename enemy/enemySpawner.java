package enemy;

import enemy.enemyTemplate;
import enemy._enemies.greenBambooEnemy;
import world.worldTemplate;

public class enemySpawner {
    private int x;
    private int y;

    private worldTemplate currentWorld;

    private int spawnRate; // How often enemies spawn
    private int spawnAmount; // How many enemies spawn at once
    private int spawnRadius; // How far from the spawn point enemies can appear
    private int enemyId; // The type of enemy to spawn

    private long lastSpawnTime = System.currentTimeMillis(); // Time of the last spawn

    public enemySpawner(int x, int y, int enemyID, worldTemplate currentWorld, int spawnRate, int spawnAmount, int spawnRadius) {
        this.x = x;
        this.y = y;
        this.enemyId = enemyID; // Assuming enemyID corresponds
        this.currentWorld = currentWorld;
        this.spawnRate = spawnRate;
        this.spawnAmount = spawnAmount;
        this.spawnRadius = spawnRadius;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return spawnRadius;
    }

    public Class<? extends enemyTemplate> getTypeFromID(int id){
        if(id == 0) {
            return greenBambooEnemy.class;
        }

        return null;
    }

    public enemyTemplate getTypeFromID(int id, int posX, int posY){
        if(id == 0) {
            return new enemy._enemies.greenBambooEnemy(posX,posY,currentWorld);
        }

        return null;
    }

    public void update(){
        if(System.currentTimeMillis() - lastSpawnTime >= spawnRate) {
            lastSpawnTime = System.currentTimeMillis();
            int amountOfEnemies = 0;
            for(enemyTemplate e : currentWorld.getEnemies()) {
                if(e.getClass().equals(getTypeFromID(enemyId))) {
                    amountOfEnemies++;
                }
            }

            if(amountOfEnemies < spawnAmount) {
                //spawn enemy
                int spawnX = x + (int)(Math.random() * (2 * spawnRadius)) - spawnRadius;
                int spawnY = y + (int)(Math.random() * (2 * spawnRadius)) - spawnRadius;
                currentWorld.enemies.add(getTypeFromID(enemyId, spawnX, spawnY));                
            }
        }
    }
}
