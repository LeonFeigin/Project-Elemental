package player;

import java.io.File;
import java.util.Scanner;

import world.worldTemplate;

public class playerSwitch {

    worldTemplate currentWorld;
    long lastSwitch = System.currentTimeMillis();

    public static final int coolddown = 1000;

    public int[] currentPlayerSelection = {0,2,3,4}; // assuming player with id 1 is out already

    public int[] playerHealths = {100, 100, 100, 100, 100}; // Health for each player type
    public int[] playerMaxHealths = {100, 100, 100, 100, 100}; // Health for each player type


    public playerSwitch(worldTemplate currentWorld) {
        this.currentWorld = currentWorld;
        for (int i = 0; i < 5; i++) {
            playerHealths[i] = getPlayerHealthFromID(i); // Initialize health for each player type
        }
    }

    public void switchPlayer(int playerType, boolean ingoreCooldown) {
        if(System.currentTimeMillis() - lastSwitch < coolddown && !ingoreCooldown) {
            // Prevent switching players too quickly
            return;
        }

        // Switch player based on playerType
        currentWorld.currentPlayer.savePlayerState();
        currentWorld.currentPlayer.inventory.saveInventory(); // Save the current player's inventory
        
        
        saveCurrentPlayerHealth(currentWorld.currentPlayer);

        if(currentPlayerSelection[playerType] == 0) {
            if(getPlayerHealthFromID(0) <= 0){
                return;
            }
            currentWorld.setCurrentPlayer(new playerFire(currentWorld.currentPlayer.x, currentWorld.currentPlayer.y, currentWorld, currentWorld.currentPlayer.attack,currentWorld.currentPlayer.specialAttack, currentWorld.currentPlayer.inventory, currentWorld.currentPlayer.xVel, currentWorld.currentPlayer.yVel, currentWorld.currentPlayer.speed, currentWorld.currentPlayer.maxSpeed));
            currentPlayerSelection = new int[]{1,2,3,4};
        }else if(currentPlayerSelection[playerType] == 1) {
            if(getPlayerHealthFromID(1) <= 0){
                return;
            }
            currentWorld.setCurrentPlayer(new playerWater(currentWorld.currentPlayer.x, currentWorld.currentPlayer.y, currentWorld, currentWorld.currentPlayer.attack,currentWorld.currentPlayer.specialAttack, currentWorld.currentPlayer.inventory, currentWorld.currentPlayer.xVel, currentWorld.currentPlayer.yVel, currentWorld.currentPlayer.speed, currentWorld.currentPlayer.maxSpeed));
            currentPlayerSelection = new int[]{0,2,3,4};
        }else if(currentPlayerSelection[playerType] == 2) {
            if(getPlayerHealthFromID(2) <= 0){
                return;
            }
            currentWorld.setCurrentPlayer(new playerEarth(currentWorld.currentPlayer.x, currentWorld.currentPlayer.y, currentWorld, currentWorld.currentPlayer.attack,currentWorld.currentPlayer.specialAttack, currentWorld.currentPlayer.inventory, currentWorld.currentPlayer.xVel, currentWorld.currentPlayer.yVel, currentWorld.currentPlayer.speed, currentWorld.currentPlayer.maxSpeed));
            currentPlayerSelection = new int[]{0,1,3,4};
        }else if(currentPlayerSelection[playerType] == 3) {
            if(getPlayerHealthFromID(3) <= 0){
                return;
            }
            currentWorld.setCurrentPlayer(new playerIce(currentWorld.currentPlayer.x, currentWorld.currentPlayer.y, currentWorld, currentWorld.currentPlayer.attack,currentWorld.currentPlayer.specialAttack, currentWorld.currentPlayer.inventory, currentWorld.currentPlayer.xVel, currentWorld.currentPlayer.yVel, currentWorld.currentPlayer.speed, currentWorld.currentPlayer.maxSpeed));
            currentPlayerSelection = new int[]{0,1,2,4};
        }else if(currentPlayerSelection[playerType] == 4) {
            if(getPlayerHealthFromID(4) <= 0){
                return;
            }
            currentWorld.setCurrentPlayer(new playerLightning(currentWorld.currentPlayer.x, currentWorld.currentPlayer.y, currentWorld, currentWorld.currentPlayer.attack,currentWorld.currentPlayer.specialAttack, currentWorld.currentPlayer.inventory, currentWorld.currentPlayer.xVel, currentWorld.currentPlayer.yVel, currentWorld.currentPlayer.speed, currentWorld.currentPlayer.maxSpeed));
            currentPlayerSelection = new int[]{0,1,2,3};
        }

        lastSwitch = System.currentTimeMillis();
        currentWorld.currentUI.updateHealth(currentWorld.currentPlayer.getHealth());
        currentWorld.currentPlayer.inventory.loadInventory(); // Reload inventory for the new player
    }

    public String getPlayerNameFromID(int id){
        String playerString = "";
        if(id == 0) {
            playerString = "playerFire";
        }else if(id == 1) {
            playerString = "playerWater";
        }else if(id == 2) {
            playerString = "playerEarth";
        }else if(id == 3) {
            playerString = "playerIce";
        }else if(id == 4) {
            playerString = "playerLightning";
        }
        return playerString;
    }

    public int getPlayerHealthFromID(int id){
        String playerName = getPlayerNameFromID(id);
        if(playerName.equals("")){
            return 100;
        }
        try {
            File file = new File("player/saves/"+playerName.replace(" ", "")+".txt");
            Scanner scan = new Scanner(file);
            return Integer.parseInt(scan.nextLine());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 100;
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
        lastSwitch = System.currentTimeMillis() - (1000-time);
    }

    public float timeRemaining() {
        if(coolddown - (System.currentTimeMillis() - lastSwitch) < 0){
            return 0;
        }
        return coolddown - (System.currentTimeMillis() - lastSwitch);
    }
}
