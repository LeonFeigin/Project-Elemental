package player;

import world.worldTemplate;

public class playerSwitch {

    worldTemplate currentWorld;
    long lastSwitch = System.currentTimeMillis();

    public static final int coolddown = 3000;

    public int[] currentPlayerSelection = {0,2,3,4}; // assuming player with id 0 is out already

    public playerSwitch(worldTemplate currentWorld) {
        this.currentWorld = currentWorld;
    }

    public void switchPlayer(int playerType) {
        if(System.currentTimeMillis() - lastSwitch < coolddown) {
            // Prevent switching players too quickly
            return;
        }
        lastSwitch = System.currentTimeMillis();
        // Switch player based on playerType
        currentWorld.currentPlayer.savePlayerState();
        
        if(currentPlayerSelection[playerType] == 0) {
            currentWorld.setCurrentPlayer(new playerFire(currentWorld.currentPlayer.x, currentWorld.currentPlayer.y, currentWorld, currentWorld.currentPlayer.attack,currentWorld.currentPlayer.specialAttack));
            currentPlayerSelection = new int[]{1,2,3,4};
        }else if(currentPlayerSelection[playerType] == 1) {
            currentWorld.setCurrentPlayer(new playerWater(currentWorld.currentPlayer.x, currentWorld.currentPlayer.y, currentWorld, currentWorld.currentPlayer.attack,currentWorld.currentPlayer.specialAttack));
            currentPlayerSelection = new int[]{0,2,3,4};
        }else if(currentPlayerSelection[playerType] == 2) {
            currentWorld.setCurrentPlayer(new playerEarth(currentWorld.currentPlayer.x, currentWorld.currentPlayer.y, currentWorld, currentWorld.currentPlayer.attack,currentWorld.currentPlayer.specialAttack));
            currentPlayerSelection = new int[]{0,1,3,4};
        }else if(currentPlayerSelection[playerType] == 3) {
            currentWorld.setCurrentPlayer(new playerIce(currentWorld.currentPlayer.x, currentWorld.currentPlayer.y, currentWorld, currentWorld.currentPlayer.attack,currentWorld.currentPlayer.specialAttack));
            currentPlayerSelection = new int[]{0,1,2,4};
        }else if(currentPlayerSelection[playerType] == 4) {
            currentWorld.setCurrentPlayer(new playerLightning(currentWorld.currentPlayer.x, currentWorld.currentPlayer.y, currentWorld, currentWorld.currentPlayer.attack,currentWorld.currentPlayer.specialAttack));
            currentPlayerSelection = new int[]{0,1,2,3};
        }

        currentWorld.currentUI.updateHealth(currentWorld.currentPlayer.getHealth());
    }

    public void setTimeRemaining(long time) {
        lastSwitch = System.currentTimeMillis() - (3000-time);
    }

    public float timeRemaining() {
        if(coolddown - (System.currentTimeMillis() - lastSwitch) < 0){
            return 0;
        }
        return coolddown - (System.currentTimeMillis() - lastSwitch);
    }
}
