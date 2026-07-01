package Entity;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Bullet extends Entity {

    private Color color;
    private int direct;
    private int speed;

    public void setColor(Color color) {
        this.color = color;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public void setDead(boolean dead) {
        this.isDead = dead;
    }

    public int getEntityId() {
        return entityId;
    }

    public Bullet(int x, int y) {
        super(x, y, 3, 5);
        this.speed = 10;
    }
    
    

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // 保存旧的变换（防止影响其它UI组件）
        AffineTransform oldTransform = g2d.getTransform();

        // 创建新变换：（非绕坐标原点的旋转）
        AffineTransform at = new AffineTransform();
        at.translate(x, y);
        at.rotate(Math.toRadians(-90*this.direct));

        // 应用变换
        g2d.transform(at);

        g2d.setColor(color);
        g2d.fillRect(-width/2, -height/2, width, height);

        // 还原变换，防止影响后续绘制
        g2d.setTransform(oldTransform);
    }

    public void update() {
        if (isDead) return;
        boolean isOutBounds = false;
        switch (direct) {
            case 0:
                isOutBounds = move(0, -speed);
                break;
            case 1:
                isOutBounds = move(-speed, 0);
                break;
            case 2:
                isOutBounds = move(0, speed);
                break;
            case 3:
                isOutBounds = move(speed, 0);
                break;
        }
        if(isOutBounds) isDead = true;
    }

    @Override
    public void handleCollided(Entity entity) {
        if(isDead) return;
        isDead = true;
    }
}
