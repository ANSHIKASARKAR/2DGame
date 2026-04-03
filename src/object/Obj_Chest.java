package object;

import main.GamePanel;

public class Obj_Chest extends SuperObject {
    GamePanel gp;
    public Obj_Chest(GamePanel gp) {
        name = "Chest";
            try {
                image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/Object/chest.png"));
                uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
    }
    
}
