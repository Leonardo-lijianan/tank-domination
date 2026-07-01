package Entity;

import java.awt.*;

public class IronWall extends Obstacle {
    public IronWall(int x, int y) {
        super(x, y, 20, 20, "iron");
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
            ((Bullet) entity).setDead(true);
        }
    }

    @Override
    public void draw(Graphics g) {
        if (isDead) return;
        g.setColor(new Color(192, 192, 192));
        g.fillRect(x - width/2, y - height/2, width, height);
        g.setColor(new Color(128, 128, 128));
        g.fillRect(x - width/2 + 2, y - height/2 + 2, width - 4, height - 4);
        g.setColor(new Color(211, 211, 211));
        g.fillRect(x - width/2 + 4, y - height/2 + 4, width - 8, height - 8);
    }
}
