package Entity;

import java.awt.*;

public class River extends Obstacle {
    private int animFrame;
    private int animCounter;

    public River(int x, int y) {
        super(x, y, 20, 20, "river");
        this.animFrame = 0;
        this.animCounter = 0;
    }

    @Override
    public boolean isBulletPassable() { return true; }

    @Override
    public boolean isTankPassable() { return false; }

    @Override
    public void update() {
        animCounter++;
        if (animCounter >= 8) {
            animFrame = (animFrame + 1) % 4;
            animCounter = 0;
        }
    }

    @Override
    public void handleCollided(Entity entity) {
    }

    @Override
    public void draw(Graphics g) {
        if (isDead) return;
        Color[] waterColors = {
                new Color(30, 144, 255),
                new Color(65, 105, 225),
                new Color(30, 144, 255),
                new Color(100, 149, 237)
        };
        g.setColor(waterColors[animFrame]);
        g.fillRect(x - width/2, y - height/2, width, height);
        g.setColor(new Color(135, 206, 250));
        g.fillRect(x - width/2 + 3, y - height/2 + 3, 5, 3);
        g.fillRect(x + 2, y + 2, 5, 3);
    }
}
