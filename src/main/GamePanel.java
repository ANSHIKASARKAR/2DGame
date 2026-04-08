package main;
import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public static final int FPS = 60;
    // Screen settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

// World settings
    public final int maxWorldCol = 50;// we will have a world that is 50 tiles wide and 50 tiles tall, so the player can move around in a big world
    public final int maxWorldRow = 50;
// SYSTEM
    public TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public UI ui = new UI(this);
    Thread gameThread;// keeps the game running again and again
    public CollisionChecker  cChecker=new CollisionChecker(this);
    public AssetSetter aSetter=new AssetSetter(this);

    //Entity and Object 
    public Player player = new Player(this, keyHandler);
    public SuperObject obj[] = new SuperObject[10]; // we will have 10 different objects in the game, like keys, doors, etc.
    public Entity npc[] = new Entity[10]; // we will have 10 different NPCs in
    //Game state
    public int gameState;
    //public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dailogueState=3;




    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);//BlueByAdventure music plays
        gameState = playState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();// calls the run method
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
     }
}
//--------------------UPDATE------------------------------------
    public void update() {
        if(gameState==playState) {
            //update the game
            player.update();
            //npc
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
        }
        if(gameState==pauseState) {
            // do nothing, the game is paused
    }
}
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
       Graphics2D g2 = (Graphics2D) g;
        //TILES
        tileManager.draw(g2);//typed tileManager.draw(g2) before player.draw(g2) because we want the player to be drawn on top of the tiles
        //OBJECT
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }
        //NPC
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw(g2);
            }
        }

        //PLAYER
        player.draw(g2);
        ui.draw(g2);
        // draw here
        g2.dispose(); //good habit to save some memory
    }
//------------------SOUND----------------------
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();}

    public void playSE(int i) {
        se.setFile(i);
        se.play();}
}
