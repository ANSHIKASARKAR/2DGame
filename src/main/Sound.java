package main;

import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];
    public Sound() {
        soundURL[0] = getClass().getResource("/res/Sound/blueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/res/Sound/coin.wav");
        soundURL[2] = getClass().getResource("/res/Sound/powerup.wav");
        soundURL[3] = getClass().getResource("/res/Sound/unlock.wav");
        soundURL[4] = getClass().getResource("/res/Sound/fanfare.wav");
    }
    public void setFile(int i) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundURL[i]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
}
}
