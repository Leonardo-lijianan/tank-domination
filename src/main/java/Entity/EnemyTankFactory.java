package Entity;

import java.util.Random;

public class EnemyTankFactory {
    private static final Random random = new Random();

    public static EnemyTank createEnemyTank(int x, int y, String type, int speed) {
        switch (type) {
            case "blue":
                return new NormalEnemyTank(x, y, speed);
            case "pink":
                return new FastShotEnemyTank(x, y, speed);
            case "red":
                return new FastMoveEnemyTank(x, y, speed);
            case "green":
                return new HeavyEnemyTank(x, y, speed);
            case "navy":
                return new BossEnemyTank(x, y, speed);
            default:
                return new NormalEnemyTank(x, y, speed);
        }
    }

    public static EnemyTank createRandomEnemyTank(int dx, int dy, boolean isBoss) {
        if (isBoss) {
            return new BossEnemyTank(28 + 55 * dx, 28 + 55 * dy, 3);
        }
        int rand = random.nextInt(100);
        String type;
        if (rand < 70) {
            type = "blue";
        } else if (rand < 80) {
            type = "pink";
        } else if (rand < 90) {
            type = "red";
        } else {
            type = "green";
        }
        return createEnemyTank(28 + 55 * dx, 28 + 55 * dy, type, 2);
    }
}
