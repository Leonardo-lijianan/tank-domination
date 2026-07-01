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
                // 第1关：密集掩体，适合新手
                wallLayout = new int[][]{
                    // 上方左右两组
                    {2,2},{3,2},{4,2},
                    {20,2},{21,2},{22,2},
                    // 上方竖墙
                    {2,3},{2,4},
                    {22,3},{22,4},
                    // 中间上方
                    {8,5},{9,5},{10,5},
                    {14,5},{15,5},{16,5},
                    // 中间
                    {8,7},{9,7},{10,7},
                    {14,7},{15,7},{16,7},
                    // 中间下方
                    {8,9},{9,9},{10,9},
                    {14,9},{15,9},{16,9},
                    // 下方左右
                    {2,15},{3,15},{4,15},
                    {20,15},{21,15},{22,15},
                    // 下方竖墙
                    {2,16},{2,17},
                    {22,16},{22,17},
                    // 底部中间
                    {8,18},{9,18},{10,18},
                    {14,18},{15,18},{16,18},
                };
                ironLayout = new int[][]{{11,6},{13,6},{11,8},{13,8}};
                riverLayout = new int[][]{{5,3},{6,3},{18,3},{19,3},{5,16},{6,16},{18,16},{19,16}};
                break;
            case 2:
                // 第2关：通道式布局，掩体减少
                wallLayout = new int[][]{
                    // 上方左右
                    {2,2},{3,2},{4,2},
                    {20,2},{21,2},{22,2},
                    // 中间上方横墙
                    {6,4},{7,4},{8,4},
                    {16,4},{17,4},{18,4},
                    // 中间横墙
                    {6,8},{7,8},{8,8},
                    {16,8},{17,8},{18,8},
                    // 中下横墙
                    {6,12},{7,12},{8,12},
                    {16,12},{17,12},{18,12},
                    // 下方左右
                    {2,18},{3,18},{4,18},
                    {20,18},{21,18},{22,18},
                    // 底部中间
                    {8,20},{9,20},{10,20},
                    {14,20},{15,20},{16,20},
                };
                ironLayout = new int[][]{{11,6},{13,6},{11,10},{13,10}};
                riverLayout = new int[][]{{5,6},{6,6},{18,6},{19,6},{5,14},{6,14},{18,14},{19,14}};
                break;
            case 3:
                // 第3关：十字通道，掩体更少
                wallLayout = new int[][]{
                    // 上方左右
                    {2,2},{3,2},{4,2},
                    {20,2},{21,2},{22,2},
                    // 中间上方
                    {7,5},{8,5},{9,5},
                    {15,5},{16,5},{17,5},
                    // 中间左右
                    {5,10},{6,10},
                    {18,10},{19,10},
                    // 中间下方
                    {7,14},{8,14},{9,14},
                    {15,14},{16,14},{17,14},
                    // 下方左右
                    {2,18},{3,18},{4,18},
                    {20,18},{21,18},{22,18},
                    // 底部中间
                    {8,20},{9,20},{10,20},
                    {14,20},{15,20},{16,20},
                };
                ironLayout = new int[][]{{11,7},{13,7},{11,12},{13,12}};
                riverLayout = new int[][]{{5,8},{6,8},{18,8},{19,8},{5,16},{6,16},{18,16},{19,16}};
                break;
            case 4:
                // 第4关：稀疏掩体，开放空间
                wallLayout = new int[][]{
                    // 上方左右
                    {2,2},{3,2},{4,2},
                    {20,2},{21,2},{22,2},
                    // 中间上方
                    {8,6},{9,6},{10,6},
                    {14,6},{15,6},{16,6},
                    // 中间
                    {8,12},{9,12},{10,12},
                    {14,12},{15,12},{16,12},
                    // 下方左右
                    {2,18},{3,18},{4,18},
                    {20,18},{21,18},{22,18},
                    // 底部中间
                    {8,20},{9,20},{10,20},
                    {14,20},{15,20},{16,20},
                };
                ironLayout = new int[][]{{11,8},{13,8},{11,14},{13,14}};
                riverLayout = new int[][]{{5,10},{6,10},{18,10},{19,10}};
                break;
            case 5:
                // 第5关：更少掩体，更大开放空间
                wallLayout = new int[][]{
                    // 上方左右
                    {2,2},{3,2},{4,2},
                    {20,2},{21,2},{22,2},
                    // 中间上方
                    {8,8},{9,8},{10,8},
                    {14,8},{15,8},{16,8},
                    // 中间
                    {8,14},{9,14},{10,14},
                    {14,14},{15,14},{16,14},
                    // 下方左右
                    {2,18},{3,18},{4,18},
                    {20,18},{21,18},{22,18},
                    // 底部中间
                    {8,20},{9,20},{10,20},
                    {14,20},{15,20},{16,20},
                };
                ironLayout = new int[][]{{11,10},{13,10},{11,16},{13,16}};
                riverLayout = new int[][]{{5,12},{6,12},{18,12},{19,12}};
                break;
            case 6:
                // 第6关：极少掩体
                wallLayout = new int[][]{
                    // 上方左右
                    {2,2},{3,2},{4,2},
                    {20,2},{21,2},{22,2},
                    // 中间
                    {8,10},{9,10},{10,10},
                    {14,10},{15,10},{16,10},
                    // 下方左右
                    {2,18},{3,18},{4,18},
                    {20,18},{21,18},{22,18},
                    // 底部中间
                    {8,20},{9,20},{10,20},
                    {14,20},{15,20},{16,20},
                };
                ironLayout = new int[][]{{11,12},{13,12}};
                riverLayout = new int[][]{{5,14},{6,14},{18,14},{19,14}};
                break;
            case 7:
                // 第7关：几乎没有掩体
                wallLayout = new int[][]{
                    // 上方左右
                    {2,2},{3,2},{4,2},
                    {20,2},{21,2},{22,2},
                    // 中间
                    {9,12},{10,12},
                    {14,12},{15,12},
                    // 下方左右
                    {2,18},{3,18},{4,18},
                    {20,18},{21,18},{22,18},
                    // 底部中间
                    {9,20},{10,20},
                    {14,20},{15,20},
                };
                ironLayout = new int[][]{{11,14},{13,14}};
                riverLayout = new int[][]{{5,16},{6,16},{18,16},{19,16}};
                break;
            case 8:
                // 第8关：最终关，几乎无掩体，纯考验技术
                wallLayout = new int[][]{
                    // 上方左右
                    {2,2},{3,2},{4,2},
                    {20,2},{21,2},{22,2},
                    // 中间少量
                    {10,14},{14,14},
                    // 下方左右
                    {2,18},{3,18},{4,18},
                    {20,18},{21,18},{22,18},
                    // 底部中间
                    {10,20},{14,20},
                };
                ironLayout = new int[][]{{11,16},{13,16}};
                riverLayout = new int[][]{{5,18},{6,18},{18,18},{19,18}};
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
