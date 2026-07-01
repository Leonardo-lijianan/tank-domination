package Entity;

import java.util.ArrayList;
import java.util.List;

public class EnemyTank extends Tank {
    private int curMoveCd;
    private int moveDuration;
    private int waitTimeToShot = 80;
    private int curWaitTime;
    private volatile int targetDirection;
    private volatile boolean shouldShoot;
    private final EntityPool pool;
    private Thread aiThread;
    private volatile boolean aiRunning;

    public EnemyTank(int x, int y, int hp, int cd, String color, int speed) {
        super(x, y, hp, cd, color, speed);
        setTeamId(2);
        pool = bulletPool;
        curMoveCd = 0;
        moveDuration = 30 + (int)(Math.random() * 40);
        targetDirection = 2;
        curWaitTime = 40 + (int)(Math.random() * 60);
        startAIThread();
    }

    /**
     * @deprecated Use EnemyTankFactory.createEnemyTank() instead
     */
    @Deprecated
    public EnemyTank(String color, int dx, int dy) {
        super(28 + 55 * dx, 28 + 55 * dy, 100, 20, color, 2);
        setTeamId(2);
        pool = bulletPool;
        curMoveCd = 0;
        moveDuration = 30 + (int)(Math.random() * 40);
        targetDirection = 2;
        curWaitTime = 40 + (int)(Math.random() * 60);
        startAIThread();
    }

    private void startAIThread() {
        aiRunning = true;
        aiThread = new Thread(() -> {
            while (aiRunning && !isDead) {
                try {
                    Thread.sleep(500);
                    if (isDead) break;
                    updateAI();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "EnemyAI-" + entityId);
        aiThread.setDaemon(true);
        aiThread.start();
    }

    private void updateAI() {
        if (isDead) return;

        List<PlayerTank> players = pool.getPlayers();
        PlayerTank nearestPlayer = null;
        int minDist = Integer.MAX_VALUE;

        for (PlayerTank p : players) {
            if (!p.isDead()) {
                int dist = (p.getX() - this.x) * (p.getX() - this.x) + (p.getY() - this.y) * (p.getY() - this.y);
                if (dist < minDist) {
                    minDist = dist;
                    nearestPlayer = p;
                }
            }
        }

        if (nearestPlayer != null && minDist < 250000) {
            int dx = nearestPlayer.getX() - this.x;
            int dy = nearestPlayer.getY() - this.y;

            if (Math.abs(dx) > Math.abs(dy)) {
                targetDirection = dx > 0 ? 3 : 1;
            } else {
                targetDirection = dy > 0 ? 2 : 0;
            }

            if (minDist < 150000) {
                shouldShoot = true;
            }
        } else {
            int rand = (int)(Math.random() * 4);
            targetDirection = rand;
            shouldShoot = false;
        }
    }

    @Override
    public void update() {
        if (isDead) return;

        if (curMoveCd > 0) {
            curMoveCd--;
        } else {
            this.rotate(targetDirection);
            switch (direct) {
                case 0:
                    move(0, -speed, bulletPool);
                    break;
                case 1:
                    move(-speed, 0, bulletPool);
                    break;
                case 2:
                    move(0, speed, bulletPool);
                    break;
                case 3:
                    move(speed, 0, bulletPool);
                    break;
            }
            moveDuration--;
            if (moveDuration <= 0) {
                moveDuration = 30 + (int)(Math.random() * 40);
                int rand = (int)(Math.random() * 4);
                targetDirection = rand;
            }
            curMoveCd = 1;
        }

        if (curCd > 0) {
            curCd--;
        } else if (curWaitTime > 0) {
            curWaitTime--;
        } else {
            if (shouldShoot) {
                shot();
                shouldShoot = false;
                curWaitTime = waitTimeToShot;
            }
        }
    }

    public void stopAI() {
        aiRunning = false;
        if (aiThread != null) {
            aiThread.interrupt();
        }
    }
}
