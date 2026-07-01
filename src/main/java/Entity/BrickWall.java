package Entity;

import java.awt.*;

public class BrickWall extends Obstacle {
    private int hp;

    public BrickWall(int x, int y) {
        super(x, y, 20, 20, "brick");
        this.hp = 1;
    }

    @Override
    public boolean isBulletPassable() { return false; }

    @Override
    public boolean isTankPassable() { return false; }

    @Override
    public void update() {}

    @Override
    public void handleCollided(Entity entity) {
        if (entity instanceof Bullet) {
            isDead = true;
        }
    }

    @Override
    public void draw(Graphics g) {
        if (isDead) return;
        g.setColor(new Color(165, 82, 42));
        g.fillRect(x - width/2, y - height/2, width, height);
        g.setColor(new Color(139, 69, 19));
        g.fillRect(x - width/2 + 2, y - height/2 + 2, width - 4, height - 4);
        g.setColor(new Color(165, 82, 42));
        g.fillRect(x - width/2 + 4, y - height/2 + 4, width - 8, height - 8);
    }
}
