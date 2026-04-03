package entity;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyHandler;
    public final int screenX;
    public final int screenY;
    //public int hasKey = 0;// we will use this to check if the player has the key or not, we can also use a boolean for this, but I want to use an int so we can have multiple keys in the future
    int standCounter = 0;// this will be used to reset the sprite animation to the default sprite when the player is not moving

    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);// we want the player to be in the center of the screen, so we divide the screen width by 2
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        solidArea = new Rectangle(8, 16, 32, 32);// this is the area that will be used for collision detection, we want it to be smaller than the tile size so the player can walk through narrow paths
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;// starting position of the player in the world
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        up1 = setup("boy_up_1");
        up2 = setup("boy_up_2");    
        down1 = setup("boy_down_1");
        down2 = setup("boy_down_2");
        left1 = setup("boy_left_1");
        left2 = setup("boy_left_2");
        right1 = setup("boy_right_1");
        right2 = setup("boy_right_2");

    }

    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/Player/Walking/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }


    public void update() {
        //comdition to check if any of the movement keys are pressed, if not, we don't need to update the player's position or sprite animation
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) { 

        if (keyHandler.upPressed) {
            direction = "up";
            worldY -= speed;
        } else if (keyHandler.downPressed) {
            direction = "down";
            worldY += speed;
        } else if (keyHandler.leftPressed) {
            direction = "left";
            worldX -= speed;
        } else if (keyHandler.rightPressed) {
            direction = "right";
            worldX += speed;
        }

        // check tile collision

        collisionOn = false;// reset collision flag before checking for collisions
        gp.cChecker.checkTile(this);// check for tile collisions, we will implement
        // check object collision
        int objIndex = gp.cChecker.checkObject(this, true);// check for object collisions,
        pickUpObject(objIndex);
        //if collisionOn is true, then we will undo the movement that we just did, so the player will not move into the tile that has collision = true    
        if(collisionOn == true) {
            switch(direction) {
                case "up":
                    worldY += speed;
                    break;
                case "down":
                    worldY -= speed;
                    break;
                case "left":
                    worldX += speed;
                    break;
                case "right":
                    worldX -= speed;
                    break;
            }
        }
        // sprite animation
            spriteCounter++;//players seems to walk faster than 12 frames, so we need to increase the counter faster
            if (spriteCounter > 12) { // change sprite every 12 frames
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }   
        else{
            standCounter++;
            if (standCounter == 12) { // reset to default sprite after 12 frames of not moving
                spriteNum = 1;
                standCounter = 0;
            }
        } 
    }

    public void pickUpObject(int index) {
        if (index != 999) {
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up": 
            if(spriteNum == 1) {
                image = up1;
            } 
            if (spriteNum == 2) {
                image = up2;
            }
            break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        // FIX: prevent crash if image not loaded
        if (image != null) {
            g2.drawImage(image, screenX, screenY, null);
        }
    }
}