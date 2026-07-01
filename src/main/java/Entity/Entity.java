package Entity;

import java.awt.*;

public abstract class Entity {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int direct; // 0:w 1:a 2:s 3:d

    protected int entityId;
    protected boolean isDead;

    private static final Rectangle GAME_BOUNDS = new Rectangle(0, 0, 600, 480);


    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        isDead = false;
    }
    public abstract void update();

    public abstract void handleCollided(Entity entity);

    public abstract void draw(Graphics g);

    public boolean move(int dx, int dy) {
        boolean isOutBounds = false;

        // 注意，我定义的实体的局部原点在物体中心！所以左上角的坐标是(x-w/2, y-h/2)，右下角是(x+w/2, y+h/2)
        int nextX = this.x + dx;
        int nextY = this.y + dy;

        int halfW = this.width/2;
        int halfH = this.height/2;


        if (nextX - halfW < GAME_BOUNDS.x) {
            nextX = GAME_BOUNDS.x + halfW;
            isOutBounds = true;
        } else if (nextX + halfW > GAME_BOUNDS.x + GAME_BOUNDS.width) {
            isOutBounds = true;
            nextX = GAME_BOUNDS.x + GAME_BOUNDS.width - halfW;
        }

        if (nextY - halfH < GAME_BOUNDS.y) {
            nextY = GAME_BOUNDS.y + halfH;
            isOutBounds = true;
        } else if (nextY + halfH > GAME_BOUNDS.y + GAME_BOUNDS.height) {
            nextY = GAME_BOUNDS.y + GAME_BOUNDS.height - halfH;
            isOutBounds = true;
        }

        this.x = nextX;
        this.y = nextY;

        return isOutBounds;
    }

    public void rotate(int direction) {
        if (this.direct%2 != direction%2) {
            int temp = this.width;
            this.width = this.height;
            this.height = temp;
        }
        this.direct = direction;
    }

    public boolean isCollided(Entity entity) {
        if(isDead) return false;
        if(entityId==entity.entityId) return false;

        int a_left = this.x - this.width/2;
        int a_right = this.x + this.width/2;
        int a_top = this.y - this.height/2;
        int a_bottom = this.y + this.height/2;

        int b_left = entity.x-entity.width/2;
        int b_right = entity.x+entity.width/2;
        int b_top = entity.y-entity.height/2;
        int b_bottom = entity.y+entity.height/2;

        return !(a_right < b_left || a_left > b_right || a_bottom < b_top || a_top > b_bottom);
    }

    public boolean isDead() {
        return isDead;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
