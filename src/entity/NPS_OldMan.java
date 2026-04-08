package entity;
import java.util.Random;

import main.GamePanel;
public class NPS_OldMan extends Entity {
    public NPS_OldMan(GamePanel gp) {
        // this is the constructor for
        super(gp);
        direction = "down";
        speed = 1;
        getImage();
    }
    public void getImage() {
        up1 = setup("/res/NPC/oldman_up_1");
        up2 = setup("/res/NPC/oldman_up_2");    
        down1 = setup("/res/NPC/oldman_down_1");
        down2 = setup("/res/NPC/oldman_down_2");
        left1 = setup("/res/NPC/oldman_left_1");
        left2 = setup("/res/NPC/oldman_left_2");
        right1 = setup("/res/NPC/oldman_right_1");
        right2 = setup("/res/NPC/oldman_right_2");

    }
    public void setAction() {
        actionLockCounter++;
if(actionLockCounter == 120) { // change direction every 2 seconds (assuming 60 FPS)
     Random random = new Random();
        int i = random.nextInt(100) + 1; // generate a random number between 1 and 100
        if (i <= 25) {
            direction = "up";
        } else if (i > 25 && i <= 50) {
            direction = "down";
        } else if (i > 50 && i <= 75) {
            direction = "left";
        } else if(i > 75 && i <= 100) {
            direction = "right";
        }          
    actionLockCounter = 0;
    }

    }
}
