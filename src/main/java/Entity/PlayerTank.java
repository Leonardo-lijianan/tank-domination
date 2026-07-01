package Entity;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.HashMap;

public class PlayerTank extends Tank {
    private int respawnFlashTimer;
    private boolean flashVisible;
    private final int respawnDuration;

    // Key bindings
    private int keyUp;
    private int keyDown;
    private int keyLeft;
    private int keyRight;
    private int keyShot;

    public PlayerTank(int x, int y, int hp, int cd, String color, int speed,
                      int keyUp, int keyDown, int keyLeft, int keyRight, int keyShot) {
        super(x, y, hp, cd, color, speed);
        setTeamId(1);
        respawnDuration = 25;
        respawnFlashTimer = respawnDuration;
        flashVisible = true;
        this.keyUp = keyUp;
        this.keyDown = keyDown;
        this.keyLeft = keyLeft;
        this.keyRight = keyRight;
        this.keyShot = keyShot;
    }

    public PlayerTank(int x, int y, int hp, int cd, String color, int speed) {
        this(x, y, hp, cd, color, speed,
             KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_J);
    }

    public PlayerTank(int x, int y, int hp, int cd) {
        this(x, y, hp, cd, "yellow", 10);
    }

    public boolean isRespawning() {
        return respawnFlashTimer > 0;
    }

    public boolean isInvincible() {
        return respawnFlashTimer > 0;
    }

    public void startRespawnFlash() {
        respawnFlashTimer = respawnDuration;
        flashVisible = true;
    }

    @Override
    public void update() {
        super.update();
        if (respawnFlashTimer > 0) {
            respawnFlashTimer--;
            flashVisible = (respawnFlashTimer / 3) % 2 == 0;
        }
    }

    public void handleKeyEvent(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == keyUp) {
            rotate(0);
            move(0, -5);
        } else if (keyCode == keyLeft) {
            rotate(1);
            move(-5, 0);
        } else if (keyCode == keyDown) {
            rotate(2);
            move(0, 5);
        } else if (keyCode == keyRight) {
            rotate(3);
            move(5, 0);
        }
        if (keyCode == keyShot) {
            shot();
        }
    }

    @Override
    public void draw(Graphics g) {
        if (isDead) return;
        if (respawnFlashTimer > 0 && !flashVisible) return;

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();
        AffineTransform at = new AffineTransform();
        at.translate(x, y);
        at.rotate(Math.toRadians(-90 * this.direct));
        g2d.transform(at);

        g2d.setColor(this.colorSet.get(this.color)[0]);
        g2d.fillRect(-20, -27, 10, 54);
        g2d.fillRect(-10, -17, 20, 34);
        g2d.fillRect(10, -27, 10, 54);
        g2d.setColor(this.colorSet.get(this.color)[1]);
        g2d.fillRect(-20, -27, 10 - 2, 54 - 2);
        g2d.fillRect(-10, -17, 20 - 2, 34 - 2);
        g2d.fillRect(10, -27, 10 - 2, 54 - 2);

        g2d.setColor(this.colorSet.get(this.color)[0]);
        g2d.fillOval(-10, -10, 20, 20);
        g2d.fillRect(-1, -25, 4, 28);

        g2d.setTransform(oldTransform);
    }
}
