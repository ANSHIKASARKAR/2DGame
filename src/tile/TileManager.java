package tile;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gp;
    public Tile[] tile; // array to hold different types of tiles
    public int mapTileNum[][]; // 2D array to hold the map layout (which tile goes where)


    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[50]; // we will have 10 different types of tiles
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // initialize the map layout array
        getTileImage(); 
        loadMap("/res/maps/worldV2.txt");
    }


    
    public void getTileImage() {
            //PLACEHOLDER TILES
            setup(0, "green-001", false);
            setup(1, "green-001", false);
            setup(2, "green-001", false);
            setup(3, "green-001", false);
            setup(4, "green-001", false);
            setup(5, "green-001", false);
            setup(6, "green-001", false);
            setup(7, "green-001", false);
            setup(8, "green-001", false);
            setup(9, "green-001", false);

            setup(10, "green-001", false);
            setup(11, "grass-002", false);
            //water tiles
            setup(12, "water00-18", true);
            setup(13, "water01-19", true);
            setup(14, "water02-20", true);
            setup(15, "water03-21", true);
            setup(16, "water04-22", true);
            setup(17, "water05-23", true);
            setup(18, "water06-24", true);
            setup(19, "water07-25", true);
            setup(20, "water08-26", true);
            setup(21, "water09-27", true);
            setup(22, "water10-28", true);
            setup(23, "water11-29", true);
            setup(24, "water12-30", true);
            setup(25, "water13-31", true);
            //sand tiles
            setup(26, "sand-003", false);
            setup(27, "004", false);
            setup(28, "005", false);
            setup(29, "006", false);
            setup(30, "007", false);
            setup(31, "008", false);
            setup(32, "009", false);
            setup(33, "010", false);
            setup(34, "011", false);
            setup(35, "012", false);
            setup(36, "013", false);
            setup(37, "014", false);
            setup(38, "015", false);
            //earth tiles
            setup(39, "earth-017", false);
            setup(40, "wall-032", true);
            setup(41, "tree-016", true);

    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/Tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadMap(String filePath) {
        // we will implement this later to load the map layout from a text file
    try {
        // read the map layout from a text file and fill the mapTileNum array
        InputStream is = getClass().getResourceAsStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        if (is == null) {
            System.out.println("MAP FILE NOT FOUND: " + filePath);
            return;
        }
        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            String line = br.readLine();//reads single line of text from the file, which represents one row of the map
            while (col < gp.maxWorldCol) {
                String numbers[] = line.split(" "); // split the line into an array of strings based on spaces, each string represents a tile number
                int num = Integer.parseInt(numbers[col]);// convert the string to an integer, which represents the tile type
                mapTileNum[col][row] = num;// store the tile number in the mapTileNum array
                col++;
            }
            if (col == gp.maxWorldCol) {
            col = 0;
            row++;// move to the next row after reading maxWorldCol tiles
            }
        }
        br.close();
    } catch (Exception e) {
        e.printStackTrace();    
    }
}    



    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow]; // default tile is grass
           int worldX = worldCol * gp.tileSize;// calculate the world position of the tile based on its column and row in the map
           int worldY = worldRow * gp.tileSize;
           int screenX = worldX - gp.player.worldX + gp.player.screenX;// calculate the screen position of the tile based on the player's world position and screen position
           int screenY = worldY - gp.player.worldY + gp.player.screenY;
           if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && // only draw the tile if it's within the player's visible area
              worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&// saves processing power by not drawing tiles that are far away from the player
              worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
              worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image,screenX, screenY,null);
            }
            worldCol++;// move to the next column
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
    }
    }
}
  