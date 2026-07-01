package Entity;

public class BossEnemyTank extends EnemyTank {
    public BossEnemyTank(int x, int y, int speed) {
        super(x, y, 600, 8, "navy", speed * 2);
    }
}
