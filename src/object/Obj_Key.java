package object;
import javax.imageio.ImageIO;

import main.GamePanel;

import java.io.IOException;

public class Obj_Key extends SuperObject {
    GamePanel gp;
    
    public Obj_Key(GamePanel gp) {
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/Object/key.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        solidArea.x = 5;
        // solidArea.y = 5;
    }
}