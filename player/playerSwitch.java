package player;

import world.worldTemplate;

public class playerSwitch {

    worldTemplate currentWorld;
    long lastSwitch = System.currentTimeMillis();

    public static final int coolddown = 3000;

    public int[] currentPlayerSelection = {0,2,3,4}; // assuming player with id 1 is out already

    public int[] playerHealths = {100, 100, 100, 100, 100}; // Health for each player type
    public int[] playerMaxHealths = {100, 100, 100, 100, 100}; // Health for each player type


    public playerSwitch(worldTemplate currentWorld) {
        this.currentWorld = currentWorld;
        for (int i = 0; i < 5; i++) {
            playerHealths[i] = getPlayer(i).getHealth(); // Initialize health for each player type
        }
    }

    public void switchPlayer(int playerType, boolean ingoreCooldown) {
        if(System.currentTimeMillis() - lastSwitch < coolddown && !ingoreCooldown) {
            // Prevent switching players too quickly
            return;
        }

        // Switch player based on playerType
        currentWorld.currentPlayer.savePlayerState();
        
        saveCurrentPlayerHealth(currentWorld.currentPlayer);

        if(currentPlayerSelection[playerType] == 0) {
            if(getPlayer(0).getHealth() <= 0){
                return;
            }
            currentWorld.setCurrentPlayer(new playerFire(currentWorld.currentPlayer.x, currentWorld.currentPlayer.y, currentWorld, currentWorld.currentPlayer.attack,currentWorld.currentPlayer.specialAttack));
            currentPlayerSelection = new int[]{1,2,3,4};
        }else if(currentPlayerSelection[playerType] == 1) {
            if(getPlayer(1).getHealth() <= 0){
                return;
            }
            currentWorld.setCurrentPlayer(new playerWater(currentWorld.currentPlayer.x, currentWorld.currentPlayer.y, currentWorld, currentWorld.currentPlayer.attack,currentWorld.currentPlayer.specialAttack));
            currentPlayerSelection = new int[]{0,2,3,4};
        }else if(currentPlayerSelection[playerType] == 2) {
            if(getPlayer(2).getHealth() <= 0){
                return;
            }
            currentWorld.setCurrentPlayer(new playerEarth(currentWorld.currentPlayer.x, currentWorld.currentPlayer.y, currentWorld, currentWorld.currentPlayer.attack,currentWorld.currentPlayer.specialAttack));
            currentPlayerSelection = new int[]{0,1,3,4};
        }else if(currentPlayerSelection[playerType] == 3) {
            if(getPlayer(3).getHealth() <= 0){
                return;
            }
            currentWorld.setCurrentPlayer(new playerIce(currentWorld.currentPlayer.x, currentWorld.currentPlayer.y, currentWorld, currentWorld.currentPlayer.attack,currentWorld.currentPlayer.specialAttack));
            currentPlayerSelection = new int[]{0,1,2,4};
        }else if(currentPlayerSelection[playerType] == 4) {
            if(getPlayer(4).getHealth() <= 0){
                return;
            }
            currentWorld.setCurrentPlayer(new playerLightning(currentWorld.currentPlayer.x, currentWorld.currentPlayer.y, currentWorld, currentWorld.currentPlayer.attack,currentWorld.currentPlayer.specialAttack));
            currentPlayerSelection = new int[]{0,1,2,3};
        }

        lastSwitch = System.currentTimeMillis();
        currentWorld.currentUI.updateHealth(currentWorld.currentPlayer.getHealth());
    }

    public playerTemplate getPlayer(int id){
        if(id == 0) {
            return new playerFire(0,0,null,null,null);
        }else if(id == 1) {
            return new playerWater(0,0,null,null,null);
        }else if(id == 2) {
            return new playerEarth(0,0,null,null,null);
        }else if(id == 3) {
            return new playerIce(0,0,null,null,null);
        }else if(id == 4) {
            return new playerLightning(0,0,null,null,null);
        }
        return null;
    }

    private void saveCurrentPlayerHealth(playerTemplate player){
        if(player.getClass() == playerFire.class) {
            playerHealths[0] = player.getHealth();
            playerMaxHealths[0] = player.getMaxHealth();
        } else if(player.getClass() == playerWater.class) {
            playerHealths[1] = player.getHealth();
            playerMaxHealths[1] = player.getMaxHealth();
        } else if(player.getClass() == playerEarth.class) {
            playerHealths[2] = player.getHealth();
            playerMaxHealths[2] = player.getMaxHealth();
        } else if(player.getClass() == playerIce.class) {
            playerHealths[3] = player.getHealth();
            playerMaxHealths[3] = player.getMaxHealth();
        } else if(player.getClass() == playerLightning.class) {
            playerHealths[4] = player.getHealth();
            playerMaxHealths[4] = player.getMaxHealth();
        }
    }

    public void playerDiedSwitch(){
        for (int i = 0; i < currentPlayerSelection.length; i++) {
            if(playerHealths[currentPlayerSelection[i]] > 0) {
                switchPlayer(i, true);
                return;
            }
        }
        currentWorld.currentUI.setInMenu(true); // If no players are available, open the menu
        currentWorld.currentUI.deathMenu = true; // Reset pause menu state
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
