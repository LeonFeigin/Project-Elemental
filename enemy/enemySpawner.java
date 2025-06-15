package enemy;

import enemy._enemies.greenBambooEnemy;
import world.worldTemplate;

public class enemySpawner {
    private int x;
    private int y;

    private worldTemplate currentWorld;

    private int spawnRate; // How often enemies spawn
    private int spawnAmount; // How many enemies will spawn in total
    private int spawnRadius; // How far from the spawn point enemies can appear
    private int enemyId; // The type of enemy to spawn

    private long lastSpawnTime = System.currentTimeMillis(); // Time of the last spawn

    // Constructor for the enemySpawner class
    public enemySpawner(int x, int y, int enemyID, worldTemplate currentWorld, int spawnRate, int spawnAmount, int spawnRadius) {
        this.x = x;
        this.y = y;
        this.enemyId = enemyID; // Assuming enemyID corresponds
        this.currentWorld = currentWorld;
        this.spawnRate = spawnRate;
        this.spawnAmount = spawnAmount;
        this.spawnRadius = spawnRadius;
    }

    // Getters for the spawner's properties
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return spawnRadius;
    }

    // Get enemy class from its id
    public Class<? extends enemyTemplate> getTypeFromID(int id){
        if(id == 0) {
            return greenBambooEnemy.class;
        }else if(id == 1) {
            return enemy._enemies.greyBambooEnemy.class;
        }else if(id == 2) {
            return enemy._enemies.peaShooterEnemy.class;
        }else if(id == 3) {
            return enemy._enemies.slimeEnemy.class;
        }else if(id == 4) {
            return enemy._enemies.trexEnemy.class;
        }else if(id == 5) {
            return enemy._enemies.batEnemy.class;
        }else if(id == 6) {
            return enemy._enemies.slime2Enemy.class;
        }else if(id == 7) {
            return enemy._enemies.mouseEnemy.class;
        }

        return null;
    }

    // Get enemy instance from its id and position
    public enemyTemplate getTypeFromID(int id, int posX, int posY){
        if(id == 0) {
            return new enemy._enemies.greenBambooEnemy(posX,posY,currentWorld);
        }else if(id == 1) {
            return new enemy._enemies.greyBambooEnemy(posX,posY,currentWorld);
        }else if(id == 2) {
            return new enemy._enemies.peaShooterEnemy(posX,posY,currentWorld);
        }else if(id == 3) {
            return new enemy._enemies.slimeEnemy(posX,posY,currentWorld);
        }else if(id == 4) {
            return new enemy._enemies.trexEnemy(posX,posY,currentWorld);
        }else if(id == 5) {
            return new enemy._enemies.batEnemy(posX,posY,currentWorld);
        }else if(id == 6) {
            return new enemy._enemies.slime2Enemy(posX,posY,currentWorld);
        }else if(id == 7) {
            return new enemy._enemies.mouseEnemy(posX,posY,currentWorld);
        }

        return null;
    }

    public void update(){
        if(System.currentTimeMillis() - lastSpawnTime >= spawnRate) { // check if enough time has passed since the last spawn
            lastSpawnTime = System.currentTimeMillis();
            int amountOfEnemies = 0;
            //check the amount of enemies of this type in the world
            for(enemyTemplate e : currentWorld.getEnemies()) {
                if(e.getClass().equals(getTypeFromID(enemyId))) {
                    amountOfEnemies++;
                }
            }
            // If the amount of enemies is less than the spawn amount, spawn a new enemy
            if(amountOfEnemies < spawnAmount) {
                //spawn enemy
                int spawnX = x + (int)(Math.random() * (2 * spawnRadius)) - spawnRadius;
                int spawnY = y + (int)(Math.random() * (2 * spawnRadius)) - spawnRadius;
                currentWorld.enemies.add(getTypeFromID(enemyId, spawnX, spawnY));                
            }
        }
    }
}
