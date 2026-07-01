package Entity;

import java.awt.*;

public abstract class Obstacle extends Entity {
    protected String type;

    public Obstacle(int x, int y, int width, int height, String type) {
        super(x, y, width, height);
        this.type = type;
        this.entityId = -1;
    }

    public String getType() { return type; }

    public abstract boolean isBulletPassable();
    public abstract boolean isTankPassable();
}
