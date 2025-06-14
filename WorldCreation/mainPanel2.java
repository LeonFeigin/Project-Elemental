package WorldCreation;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class mainPanel2 extends JPanel implements MouseListener, KeyListener{
        // HOW TO USE THIS:
        // SPACE BAR to change tile index
        // 1 to select grass tiles
        // 2 to select path tiles
        // C to clear the world
        // +/- to zoom in/out
        // WASD to move around the world
        // SHIFT to increase speed
        // CTRL to decrease speed
        // Left click to place a tile
        // Right click to place a tile in a rectangle from the initial right click position to the current mouse position

        // NOTE: The world is saved in two files: grassTilesWorld.txt and pathTilesWorld.txt
        // Mainly used for testing purposes, you can load the world from these files 

        // Grass tiles and path tiles have same index
        // 0 = top left corner grass tile,      1 = top grass tile,     2 = top right corner grass tile
        // 11 = left grass tile,                12 = center grass tile, 13 = right grass tile
        // 22 = bottom left corner grass tile,  23 = bottom grass tile, 24 = bottom right corner grass tile
        
        //collision tiles:
        // 0 = all world collision, 1 = enemy collision

    public BufferedImage[] grassTiles = new BufferedImage[77];
    public BufferedImage[] pathTiles = new BufferedImage[77];


    long startTime = System.currentTimeMillis();
    int frames = 0;


    int xVel = 0;
    int yVel = 0;
    int speed = 2;

    private boolean AisPressed = false;
    private boolean DisPressed = false;
    private boolean WisPressed = false;
    private boolean SisPressed = false;

    private boolean leftMousePressed = false;

    int worldXOffset = 0;
    int worldYOffset = 0;

    int currentTile = 0;

    ArrayList<ArrayList<Integer>> grassTilesWorld = new ArrayList<>();
    ArrayList<ArrayList<Integer>> pathTilesWorld = new ArrayList<>();
    ArrayList<ArrayList<Integer>> collisionWorld = new ArrayList<>();

    int mouseX = 0;
    int mouseY = 0;

    int initXRightClick = 0;
    int initYRightClick = 0;

    float worldScale = 1;

    int currentTileSelection = 0; // 0 for grass, 1 for path

    Timer timer;
    public mainPanel2(){
        getImages("world/tileset/grass/", grassTiles, 32, 77);
        getImages("world/tileset/path/", pathTiles, 32, 77);

        timer = new Timer(1, e -> {
            worldXOffset += xVel * speed;
            worldYOffset += yVel * speed;
            if (worldXOffset < 0) {
                worldXOffset = 0; // Prevent scrolling left beyond the start
            }
            if (worldYOffset < 0) {
                worldYOffset = 0; // Prevent scrolling up beyond the start
            }

            if (mainPanel2.this.isFocusOwner()) {
                // Left mouse click logic
                mouseX = (int)MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().x;
                mouseY = (int)MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().y;
            }

            if(leftMousePressed){
                placeTile(currentTile, (int)((mouseX + worldXOffset)*(1/worldScale)), (int)((mouseY + worldYOffset)*(1/worldScale)));
            }

            repaint();
        });
        ArrayList<Integer> row = new ArrayList<>();
        row.add(0);
        grassTilesWorld.add(row);

        timer.start();
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        requestFocusInWindow();

        File file = new File("WorldCreation/files/grassTilesWorld.txt");
        if (file.exists()) {
            try {
                java.util.Scanner scanner = new java.util.Scanner(file);
                grassTilesWorld.clear();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().replaceAll("[\\[\\]\\s]", "");
                    ArrayList<Integer> rowList = new ArrayList<>();
                    if (!line.isEmpty()) {
                        for (String num : line.split(",")) {
                            if (!num.isEmpty()) {
                                rowList.add(Integer.parseInt(num));
                            }
                        }
                    }
                    grassTilesWorld.add(rowList);
                }
                scanner.close();
            } catch (Exception ex) {
                System.out.println("Error loading world!");
            }
        }

        file = new File("WorldCreation/files/pathTilesWorld.txt");
        if (file.exists()) {
            try {
                java.util.Scanner scanner = new java.util.Scanner(file);
                pathTilesWorld.clear();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().replaceAll("[\\[\\]\\s]", "");
                    ArrayList<Integer> rowList = new ArrayList<>();
                    if (!line.isEmpty()) {
                        for (String num : line.split(",")) {
                            if (!num.isEmpty()) {
                                rowList.add(Integer.parseInt(num));
                            }
                        }
                    }
                    pathTilesWorld.add(rowList);
                }
                scanner.close();
            } catch (Exception ex) {
                System.out.println("Error loading world!");
            }
        }

        file = new File("WorldCreation/files/collisionWorld.txt");
        if (file.exists()) {
            try {
                java.util.Scanner scanner = new java.util.Scanner(file);
                collisionWorld.clear();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().replaceAll("[\\[\\]\\s]", "");
                    ArrayList<Integer> rowList = new ArrayList<>();
                    if (!line.isEmpty()) {
                        for (String num : line.split(",")) {
                            if (!num.isEmpty()) {
                                rowList.add(Integer.parseInt(num));
                            }
                        }
                    }
                    collisionWorld.add(rowList);
                }
                scanner.close();
            } catch (Exception ex) {
                System.out.println("Error loading collision world!");
            }
        }
    }

    private void placeTile(int tileIndex, int x, int y) {
        int tileY = y / 32;
        int tileX = x / 32;

        if(currentTileSelection == 0){
            if(tileY > grassTilesWorld.size() - 1) {
                for (int i = grassTilesWorld.size(); i <= tileY; i++) {
                    grassTilesWorld.add(new ArrayList<>());
                }
            }
            
            if(tileX > grassTilesWorld.get(tileY).size() - 1) {
                for (int i = grassTilesWorld.get(tileY).size(); i < tileX; i++) {
                    grassTilesWorld.get(tileY).add(-1); // fill with empty tiles
                }
                grassTilesWorld.get(tileY).add(currentTile); // add the current tile
            }else{
                grassTilesWorld.get(tileY).set(tileX, currentTile); // set the current tile
            }
        }else if(currentTileSelection == 1){
            if(tileY > pathTilesWorld.size() - 1) {
                for (int i = pathTilesWorld.size(); i <= tileY; i++) {
                    pathTilesWorld.add(new ArrayList<>());
                }
            }
            
            if(tileX > pathTilesWorld.get(tileY).size() - 1) {
                for (int i = pathTilesWorld.get(tileY).size(); i < tileX; i++) {
                    pathTilesWorld.get(tileY).add(-1); // fill with empty tiles
                }
                pathTilesWorld.get(tileY).add(currentTile); // add the current tile
            }else{
                pathTilesWorld.get(tileY).set(tileX, currentTile); // set the current tile
            }
        }else if (currentTileSelection == 2) {
            // Handle collision tiles if needed
            if(tileY > collisionWorld.size() - 1) {
                for (int i = collisionWorld.size(); i <= tileY; i++) {
                    collisionWorld.add(new ArrayList<>());
                }
            }
            
            if(tileX > collisionWorld.get(tileY).size() - 1) {
                for (int i = collisionWorld.get(tileY).size(); i < tileX; i++) {
                    collisionWorld.get(tileY).add(-1); // fill with empty tiles
                }
                collisionWorld.get(tileY).add(currentTile); // add the current tile
            }else{
                collisionWorld.get(tileY).set(tileX, currentTile); // set the current tile
            }
            
        }
    }

    private void getImages(String direction, BufferedImage[] image,int size, int count) {
        try{
            for (int i = 0; i < count; i++) {
                BufferedImage original = ImageIO.read(new File(direction + i+ ".png"));
                BufferedImage scaled = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
                AffineTransform at = AffineTransform.getScaleInstance(size / original.getWidth(), size / original.getHeight());
                AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                scaleOp.filter(original, scaled);
                image[i] = scaled;
            }
        }catch (IOException ex) {
            System.out.println("File not found!");
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < grassTilesWorld.size(); i++) {
            for (int j = 0; j < grassTilesWorld.get(i).size(); j++) {
            int tileIndex = grassTilesWorld.get(i).get(j);
            if (tileIndex >= 0 && tileIndex < grassTiles.length) {
                int tileSize = (int)(32 * worldScale);
                int drawX = j * tileSize - worldXOffset;
                int drawY = i * tileSize - worldYOffset;
                g.drawImage(grassTiles[tileIndex], drawX, drawY, tileSize, tileSize, null);
            }
            }
        }

        for (int i = 0; i < pathTilesWorld.size(); i++) {
            for (int j = 0; j < pathTilesWorld.get(i).size(); j++) {
            int tileIndex = pathTilesWorld.get(i).get(j);
            if (tileIndex >= 0 && tileIndex < pathTiles.length) {
                int tileSize = (int)(32 * worldScale);
                int drawX = j * tileSize - worldXOffset;
                int drawY = i * tileSize - worldYOffset;
                g.drawImage(pathTiles[tileIndex], drawX, drawY, tileSize, tileSize, null);
            }
            }
        }

        //draw collsion tiles as white squares
        for (int i = 0; i < collisionWorld.size(); i++) {
            for (int j = 0; j < collisionWorld.get(i).size(); j++) {
                int tileIndex = collisionWorld.get(i).get(j);
                if (tileIndex >= 0 && tileIndex < grassTiles.length) {
                    if(tileIndex == 0){g.setColor(new Color(255,255,255,100));} // all world collsion
                    else if(tileIndex == 1){g.setColor(new Color(255,0,0,100));} // enemy collsion
                    int tileSize = (int)(32 * worldScale);
                    int drawX = j * tileSize - worldXOffset;
                    int drawY = i * tileSize - worldYOffset;
                    g.fillRect(drawX, drawY, tileSize, tileSize);
                }
            }
        }


        g.setColor(Color.WHITE);
        // draw a white rectangle around the mouse cursor that magnets to a grid
        int tileSize = (int)(32 * worldScale);
        int drawX = (int)((mouseX + worldXOffset) / tileSize) * tileSize - worldXOffset;
        int drawY = (int)((mouseY + worldYOffset) / tileSize) * tileSize - worldYOffset;
        g.drawRect(drawX, drawY, tileSize, tileSize);
        




        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        String currentTileString = "";
        if(currentTileSelection == 0) {
            currentTileString = "Grass Tile";
        } else if(currentTileSelection == 1) {
            currentTileString = "Path Tile";
        } else if(currentTileSelection == 2) {
            currentTileString = "Collision Tile";
        }
        g.drawString("Current Tile: " + currentTileString, 10, 20);
        //current moues position (in world pos)
        g.drawString("Mouse Position: (" + (int)((mouseX + worldXOffset) * (1/worldScale)) + ", " + (int)((mouseY + worldYOffset) * (1/worldScale)) + ")", 10, 40);

    }


    @Override
    public void keyTyped(KeyEvent e) {
        
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            currentTile = Integer.parseInt(JOptionPane.showInputDialog("Enter tile index (0-76):"));
        }

        if(e.getKeyCode() == KeyEvent.VK_C){
            int choice = JOptionPane.showConfirmDialog(this, "Do you want to clear the world?", "Clear World", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                grassTilesWorld.clear();
                pathTilesWorld.clear();
                collisionWorld.clear();
                grassTilesWorld.add(new ArrayList<>());
                grassTilesWorld.get(0).add(0); // Add a default tile to the first row
                System.out.println("World cleared!");
            } else {
                System.out.println("World not cleared.");
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_1) {
            currentTileSelection = 0; // Select grass tiles
            currentTile = 0; // Reset to first grass tile
            System.out.println("Selected grass tiles");
        } else if(e.getKeyCode() == KeyEvent.VK_2) {
            currentTileSelection = 1; // Select path tiles
            currentTile = 0; // Reset to first path tile
            System.out.println("Selected path tiles");
        }else if(e.getKeyCode() == KeyEvent.VK_3) {
            currentTileSelection = 2; // Select collision tiles
            currentTile = 0; // Reset to first collision tile
            System.out.println("Selected collision tiles");
        }

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            try{
                File file = new File("WorldCreation/files/grassTilesWorld.txt");
                PrintWriter filePrint = new PrintWriter(file);
                filePrint.close();
                file = new File("WorldCreation/files/grassTilesWorld.txt");
                filePrint = new PrintWriter(file);
                for (int i = 0; i < grassTilesWorld.size(); i++) {
                    // System.out.println(grassTilesWorld.get(i).toString());
                    filePrint.println(grassTilesWorld.get(i).toString());
                }
                filePrint.close();
            }catch (Exception ex) {
                System.out.println("Error saving world!");
            }

            try{
                File file = new File("WorldCreation/files/pathTilesWorld.txt");
                PrintWriter filePrint = new PrintWriter(file);
                filePrint.close();
                file = new File("WorldCreation/files/pathTilesWorld.txt");
                filePrint = new PrintWriter(file);
                for (int i = 0; i < pathTilesWorld.size(); i++) {
                    // System.out.println(pathTilesWorld.get(i).toString());
                    filePrint.println(pathTilesWorld.get(i).toString());
                }
                filePrint.close();
            }catch (Exception ex) {
                System.out.println("Error saving world!");
            }

            try{
                File file = new File("WorldCreation/files/collisionWorld.txt");
                PrintWriter filePrint = new PrintWriter(file);
                filePrint.close();
                file = new File("WorldCreation/files/collisionWorld.txt");
                filePrint = new PrintWriter(file);
                for (int i = 0; i < collisionWorld.size(); i++) {
                    // System.out.println(collisionWorld.get(i).toString());
                    filePrint.println(collisionWorld.get(i).toString());
                }
                filePrint.close();
            }catch (Exception ex) {
                System.out.println("Error saving collision world!");
            }
            
            System.exit(0); // Exit the program
        }

        if(e.getKeyCode() == KeyEvent.VK_W) {
            yVel = -5; // Move up
            WisPressed = true; // Set W key as pressed
        } else if(e.getKeyCode() == KeyEvent.VK_S) {
            yVel = 5; // Move down
            SisPressed = true; // Set S key as pressed
        } else if(e.getKeyCode() == KeyEvent.VK_A) {
            xVel = -5; // Move left
            AisPressed = true; // Set A key as pressed
        } else if(e.getKeyCode() == KeyEvent.VK_D) {
            xVel = 5; // Move right
            DisPressed = true; // Set D key as pressed
        }

        if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
            speed = 3; // Increase speed
        }

        if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
            speed = 1; // Decrease speed
        }

        if(e.getKeyCode() == '='){
            worldScale *= 2;
            System.out.println("World scale: " + worldScale);
        }else if(e.getKeyCode() == '-'){
            worldScale = worldScale/2f;
            System.out.println("World scale: " + worldScale);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W) {
            if(SisPressed) {
                yVel = 5; // If S is pressed, move down
            } else {
                yVel = 0; // Stop moving up
            }
            WisPressed = false; // Reset W key pressed state
        } else if(e.getKeyCode() == KeyEvent.VK_S) {
            if(WisPressed) {
                yVel = -5; // If S is pressed, move down
            } else {
                yVel = 0; // Stop moving up
            }
            SisPressed = false; // Reset S key pressed state
        } else if(e.getKeyCode() == KeyEvent.VK_A) {
            if(DisPressed) {
                xVel = 5; // If S is pressed, move down
            } else {
                xVel = 0; // Stop moving up
            }
            AisPressed = false; // Reset A key pressed state
        } else if(e.getKeyCode() == KeyEvent.VK_D) {
            if(AisPressed) {
                xVel = -5; // If S is pressed, move down
            } else {
                xVel = 0; // Stop moving up
            }
            DisPressed = false; // Reset D key pressed state
        }

        if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
            speed = 2; // Decrease speed
        }

        if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
            speed = 2; // Increase speed
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

 
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) { // Left click
            leftMousePressed = true;
        }else if(e.getButton() == MouseEvent.BUTTON3) { // Right click
            initXRightClick =(int)((e.getX()+ worldXOffset) * (1/worldScale));
            initYRightClick = (int)((e.getY()+ worldYOffset)  * (1/worldScale));
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) { // Left click
            leftMousePressed = false;
        }else if(e.getButton() == MouseEvent.BUTTON3) { // Right click
            int endX = (int)((e.getX()+ worldXOffset) * (1/worldScale));
            int endY = (int)((e.getY()+ worldYOffset)  * (1/worldScale));

            for (int i = Math.min(initYRightClick, endY); i <= Math.max(initYRightClick, endY); i++) {
                for (int j = Math.min(initXRightClick, endX); j <= Math.max(initXRightClick, endX); j++) {
                    if(i < 0 || j < 0) continue; // Skip negative indices
                    placeTile(currentTile, j, i);
                }
            }
        }
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        
    }


    @Override
    public void mouseExited(MouseEvent e) {
        
    }

}
