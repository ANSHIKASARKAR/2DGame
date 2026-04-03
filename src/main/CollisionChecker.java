package main;

import entity.Entity;


public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
        
    public void checkTile(Entity entity) {
        // we will implement this later to check if the player is colliding with any tiles that have collision = true
        int entityLeftWorldX = entity.worldX + entity.solidArea.x; // the left edge of the player's solid area in world coordinates
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y; 
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;     
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;   
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;
        int tileNum1, tileNum2;
        switch (entity.direction) {// we will check the tiles that the player is moving towards based on the direction of movement
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;// we need to calculate the new position of the player's solid area after moving up
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];// we will check the tile at the left edge of the player's solid area and the tile at the right edge of the player's solid area, because the player can be in between two tiles
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true) {
                    //if either of the tiles has collision = true, then we set the player's collisionOn flag to true, which will prevent the player from moving in that direction
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        } 
    }
    public int checkObject(Entity entity, boolean player) {
        // we will implement this later to check if the player is colliding with any objects that have collision = true
    int index = 999;// we will use this to store the index of the object that the player is colliding with, we will set it to 999 by default, which means no collision
    for (int i = 0; i < gp.obj.length; i++) {   
        if (gp.obj[i] != null) {
            // get the solid area position of the player and the object, we will use this to check for collision
            entity.solidArea.x = entity.worldX + entity.solidArea.x;
            entity.solidArea.y = entity.worldY + entity.solidArea.y;
            gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
            gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

            switch (entity.direction) {
                case "up":
                    entity.solidArea.y -= entity.speed;
                    break;
                case "down":
                    entity.solidArea.y += entity.speed;
                    break;
                case "left":
                    entity.solidArea.x -= entity.speed;
                    break;
                case "right":
                    entity.solidArea.x += entity.speed;
                    break;
            }
            if (entity.solidArea.intersects(gp.obj[i].solidArea)) {// if the player's solid area intersects with the object's solid area, then we have a collision
                if (gp.obj[i].collision == true) {
                    entity.collisionOn = true;
                }
                if (player == true) {
                    index = i;// we will store the index of the object that the player is colliding with, so we can use it later to interact with the object
                }
            }
            // reset the solid area position after checking for collision
            entity.solidArea.x = entity.solidAreaDefaultX;
            entity.solidArea.y = entity.solidAreaDefaultY;
            gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
            gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY; 
        }

    }
    return index;
}
}
