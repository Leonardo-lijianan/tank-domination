package util;

import javax.sound.sampled.*;
import java.io.InputStream;
import java.net.URL;

public class SoundManager {
    private static SoundManager instance;
    private boolean soundEnabled = true;

    private SoundManager() {}

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void playShot() {
        if (!soundEnabled) return;
        playResource("/111.wav");
    }

    public void playExplosion() {
        if (!soundEnabled) return;
        playResource("/111.wav");
    }

    private void playResource(String resourcePath) {
        try {
            URL url = SoundManager.class.getResource(resourcePath);
            if (url == null) return;
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            clip.start();
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
        } catch (Exception e) {
            // Silently ignore sound errors
        }
    }

    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
    }
}
