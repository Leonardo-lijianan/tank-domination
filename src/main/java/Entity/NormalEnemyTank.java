package Entity;

public class NormalEnemyTank extends EnemyTank {
    public NormalEnemyTank(int x, int y, int speed) {
        super(x, y, 100, 20, "blue", speed);
    }

    public NormalEnemyTank(String color, int dx, int dy) {
        super(color, dx, dy);
    }
}
