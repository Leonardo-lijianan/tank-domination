package config;

public class LevelConfig {
    private int level;
    private int enemyCount;
    private String speedMode;
    private boolean hasBoss;
    private int[][] wallLayout;
    private int[][] ironLayout;
    private int[][] riverLayout;

    public LevelConfig(int level, int enemyCount, String speedMode, boolean hasBoss) {
        this.level = level;
        this.enemyCount = enemyCount;
        this.speedMode = speedMode;
        this.hasBoss = hasBoss;
        initLayouts();
    }

    private void initLayouts() {
        switch (level) {
            case 1:
                wallLayout = new int[][]{{5,5},{10,5},{15,5},{5,10},{15,10},{8,8},{12,8}};
                ironLayout = new int[][]{{10,10}};
                riverLayout = new int[][]{{3,3},{17,3},{3,12},{17,12}};
                break;
            case 2:
                wallLayout = new int[][]{{4,4},{8,4},{12,4},{16,4},{4,8},{8,8},{12,8},{16,8},{6,12},{14,12}};
                ironLayout = new int[][]{{10,6},{10,12}};
                riverLayout = new int[][]{{2,6},{18,6},{2,10},{18,10}};
                break;
            case 3:
                wallLayout = new int[][]{{3,3},{6,3},{9,3},{12,3},{15,3},{18,3},{3,6},{18,6},{3,9},{18,9},{3,12},{18,12}};
                ironLayout = new int[][]{{10,8},{8,10},{12,10}};
                riverLayout = new int[][]{{5,15},{15,15}};
                break;
            case 4:
                wallLayout = new int[][]{{5,5},{10,5},{15,5},{5,10},{15,10},{8,15},{12,15}};
                ironLayout = new int[][]{{10,8},{10,12},{5,15},{15,15}};
                riverLayout = new int[][]{{3,3},{17,3},{3,12},{17,12}};
                break;
            case 5:
                wallLayout = new int[][]{{4,4},{8,4},{12,4},{16,4},{4,12},{8,12},{12,12},{16,12},{10,8}};
                ironLayout = new int[][]{{6,8},{14,8},{10,4},{10,12}};
                riverLayout = new int[][]{{2,6},{18,6},{2,10},{18,10}};
                break;
            case 6:
                wallLayout = new int[][]{{3,3},{6,3},{9,3},{12,3},{15,3},{18,3},{10,6},{10,10}};
                ironLayout = new int[][]{{5,8},{15,8},{8,12},{12,12}};
                riverLayout = new int[][]{{3,15},{17,15},{5,15},{15,15}};
                break;
            case 7:
                wallLayout = new int[][]{{4,4},{8,4},{12,4},{16,4},{4,8},{16,8},{4,12},{16,12},{8,12},{12,12}};
                ironLayout = new int[][]{{10,6},{10,10},{6,10},{14,10}};
                riverLayout = new int[][]{{2,4},{18,4},{2,12},{18,12}};
                break;
            case 8:
                wallLayout = new int[][]{{3,3},{6,3},{9,3},{12,3},{15,3},{18,3},{3,6},{18,6},{3,9},{18,9},{3,12},{18,12},{3,15},{18,15}};
                ironLayout = new int[][]{{10,8},{8,10},{12,10},{6,12},{14,12}};
                riverLayout = new int[][]{{5,5},{15,5},{5,10},{15,10}};
                break;
            default:
                wallLayout = new int[0][];
                ironLayout = new int[0][];
                riverLayout = new int[0][];
        }
    }

    public int getLevel() { return level; }
    public int getEnemyCount() { return enemyCount; }
    public String getSpeedMode() { return speedMode; }
    public boolean hasBoss() { return hasBoss; }
    public int[][] getWallLayout() { return wallLayout; }
    public int[][] getIronLayout() { return ironLayout; }
    public int[][] getRiverLayout() { return riverLayout; }

    public static LevelConfig getDefaultConfig(int level) {
        switch (level) {
            case 1: return new LevelConfig(1, 20, "slow", false);
            case 2: return new LevelConfig(2, 20, "slow", false);
            case 3: return new LevelConfig(3, 20, "slow", true);
            case 4: return new LevelConfig(4, 25, "medium", false);
            case 5: return new LevelConfig(5, 25, "medium", false);
            case 6: return new LevelConfig(6, 25, "medium", true);
            case 7: return new LevelConfig(7, 30, "fast", false);
            case 8: return new LevelConfig(8, 30, "fast", true);
            default: return new LevelConfig(1, 20, "slow", false);
        }
    }
}
