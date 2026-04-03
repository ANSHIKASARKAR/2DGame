package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false; // by default, objects have no collision, player can walk through them. We will set this to true for objects that should have collision, like walls or trees
    public int worldX, worldY; // the position of the object in the world, we will use this to check for collisions with the player
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // the area that will be used for collision detection, we will set this to the size of the tile (48x48) by default, but we can change it for objects that are smaller or larger than a tile
    public int solidAreaDefaultX=0;
    public int solidAreaDefaultY=0; // we will use these to reset the solid area position when we check for collisions, because we will be changing the solid area position based on the player's position in the world
    UtilityTool uTool = new UtilityTool();

    public void draw(Graphics2D g2, GamePanel gp) {
    int screenX = worldX - gp.player.worldX + gp.player.screenX;// calculate the position of the object on the screen based on the player's position in the world
    int screenY = worldY - gp.player.worldY + gp.player.screenY;

    // Check if object is within the visible screen area
    if (screenX + gp.tileSize > 0 &&
        screenX < gp.screenWidth &&
        screenY + gp.tileSize > 0 &&
        screenY < gp.screenHeight) {
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
}
