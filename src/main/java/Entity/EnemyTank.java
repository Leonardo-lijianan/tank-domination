package Entity;

import java.util.Random;

import static java.lang.Math.abs;

public class EnemyTank extends Tank {
    private final int moveCd = 1;
    private int curMoveCd;
    private final int waitTimeToShot = 100;
    private int curWaitTime = waitTimeToShot;

    public EnemyTank(int x, int y, int hp, int cd, String color, int speed) {
        super(x, y, hp, cd, color, speed);
        setTeamId(2);
    }

    public EnemyTank(String color, int dx, int dy) {
        super(28+55*dx, 28+55*dy, 100, 20, color, 2);
        setTeamId(2);
    }

    @Override
    public void update() {
        if(isDead) return;

        Random random = new Random();
        if (curMoveCd > 0) {
            curMoveCd--;
        } else {
            int direction = abs(random.nextInt())%4;
            this.rotate(direction); // 应用旋转
            switch (direct) { // 应用移动
                case 0:
                    move(0, -speed);
                    break;
                case 1:
                    move(-speed, 0);
                    break;
                case 2:
                    move(0, speed);
                    break;
                case 3:
                    move(speed, 0);
                    break;
            }
            curMoveCd = moveCd;
        }

        if (curCd > 0) {
            curCd--;
        } else if (curWaitTime > 0) {
            curWaitTime--;
        } else {
            if (random.nextInt() % 10 == 0) {
                shot();
            }
         }
    }
}
