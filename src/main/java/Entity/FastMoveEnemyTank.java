package Entity;

public class FastMoveEnemyTank extends EnemyTank {
    public FastMoveEnemyTank(int x, int y, int speed) {
        super(x, y, 100, 20, "red", speed * 2);
    }
}
