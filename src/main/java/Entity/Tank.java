package Entity;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashMap;

public class Tank extends Entity {
    private int hp;
    private int cd;
    protected int curCd;
    protected int speed;
    protected int teamId;
    private final EntityPool bulletPool;


    protected final HashMap<String, Color[]> colorSet = initColorSet();
    protected String color;

    private HashMap<String, Color[]> initColorSet() {
        HashMap<String, Color[]> colorSet = new HashMap<>();
        colorSet.put("yellow", new Color[]{Color.YELLOW, new Color(154, 154, 0)});

        colorSet.put("blue", new Color[]{Color.BLUE, new Color(0, 0, 154)});
        colorSet.put("red", new Color[]{Color.RED, new Color(154, 0, 0)});
        colorSet.put("green", new Color[]{Color.GREEN, new Color(0, 154, 0)});
        colorSet.put("pink", new Color[]{Color.PINK, new Color(154, 105, 105)});
        colorSet.put("cyan", new Color[]{Color.CYAN, new Color(0, 154, 154)});
        return colorSet;
    }

    public Tank(int x, int y, int hp, int cd, String color,  int speed) {
        super(x, y, 40, 54);
        this.hp = hp;
        this.cd = cd;
        this.curCd = 0;
        this.color = color;
        this.speed = speed;
        bulletPool = EntityPool.getEntityPoolSingleton();
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
        this.entityId = teamId;
    }

    public String getColor() {
        return color;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getHp() {
        return hp;
    }

    public void takeDamage() {
        this.hp -= 100;
        if (this.hp <= 0) {
            isDead = true;
        }
    }

    @Override
    public void update() {
        if(isDead) return;
        if (curCd > 0) {
            curCd--;
        }
    }

    @Override
    public void handleCollided(Entity entity) {
        if(isDead) return;
        if(entity instanceof Bullet && ((Bullet) entity).getEntityId() != this.entityId) {
            takeDamage();
        }
    }

    public void shot() {
        if(isDead) return;
        if (curCd == 0) {
            Bullet bullet = new Bullet(x, y);
            bullet.setColor(this.colorSet.get(color)[0]);
            bullet.setDirect(this.direct);
            bullet.setEntityId(this.teamId);
            bulletPool.addEntity(bullet);
            curCd = cd;
        }
    }

    public void draw(Graphics g) {
        if(isDead) return;
        // int x = 50; // 坦克左上角的x坐标
        // int y = 50; // 坦克左上角的y坐标

        Graphics2D g2d = (Graphics2D) g;


        // 保存旧的变换（防止影响其它UI组件）
        AffineTransform oldTransform = g2d.getTransform();

        // 创建新变换：（非绕坐标原点的旋转）
        AffineTransform at = new AffineTransform();
        at.translate(x, y);
        at.rotate(Math.toRadians(-90*this.direct));

        // 应用变换
        g2d.transform(at);

        // 履带和机身
        g2d.setColor(this.colorSet.get(this.color)[0]); // 亮部
        g2d.fillRect(-20, -27, 10, 54);
        g2d.fillRect(-10, -17, 20, 34);
        g2d.fillRect(10, -27, 10, 54);
        g2d.setColor(this.colorSet.get(this.color)[1]); // 暗部
        g2d.fillRect(-20, -27, 10 - 2, 54-2);
        g2d.fillRect(-10, -17, 20-2, 34-2);
        g2d.fillRect(10, -27, 10-2, 54-2);

        // 炮台
        g2d.setColor(this.colorSet.get(this.color)[0]);
        g2d.fillOval(-10, -10, 20, 20);
        // 炮管
        g2d.fillRect(-1, -25, 4, 28);

        // 还原变换，防止影响后续绘制
        g2d.setTransform(oldTransform);
    }
}
