package object;

import main.GamePanel;

public class Obj_Boots extends SuperObject {
    GamePanel gp;
    public Obj_Boots(GamePanel gp) {
        name = "Boots";
            try {
                image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/Object/boots.png"));
                uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
    }
    
}
