package Entity;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Explosion extends Entity {
    private ImageIcon[] frames;
    private int currentFrame;
    private int frameCounter;
    private static final int FRAMES_PER_FRAME = 4;
    private static final int TOTAL_FRAMES = 3;

    public Explosion(int x, int y) {
        super(x, y, 40, 40);
        this.entityId = -2;
        this.currentFrame = 0;
        this.frameCounter = 0;
        loadFrames();
    }

    private void loadFrames() {
        frames = new ImageIcon[TOTAL_FRAMES];
        String[] names = {"bomb_1.gif", "bomb_2.gif", "bomb_3.gif"};
        for (int i = 0; i < TOTAL_FRAMES; i++) {
            URL url = getClass().getResource("/" + names[i]);
            if (url != null) {
                frames[i] = new ImageIcon(url);
            }
        }
    }

    @Override
    public void update() {
        frameCounter++;
        if (frameCounter >= FRAMES_PER_FRAME) {
            frameCounter = 0;
            currentFrame++;
            if (currentFrame >= TOTAL_FRAMES) {
                isDead = true;
            }
        }
    }

    @Override
    public void handleCollided(Entity entity) {
    }

    @Override
    public void draw(Graphics g) {
        if (isDead) return;
        if (frames[currentFrame] != null) {
            Image img = frames[currentFrame].getImage();
            g.drawImage(img, x - width / 2, y - height / 2, width, height, null);
        }
    }
}
