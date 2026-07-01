package manager;

import Entity.*;
import config.LevelConfig;
import config.GameState;

import java.util.Random;

public class LevelManager {
    private final GameState gameState;
    private final EntityPool entityPool;
    private final Random random;
    private int spawnTimer;
    private int enemiesSpawned;
    private boolean levelInitialized;

    public LevelManager(GameState gameState, EntityPool entityPool) {
        this.gameState = gameState;
        this.entityPool = entityPool;
        this.random = new Random();
        this.spawnTimer = 0;
        this.enemiesSpawned = 0;
        this.levelInitialized = false;
    }

    public void initializeLevel(LevelConfig config) {
        // 清空所有实体
        entityPool.getEnemies().clear();
        entityPool.getBullets().clear();
        entityPool.getObstacles().clear();

        // 在关卡配置中生成障碍物
        createObstacles(config);

        // Reset spawn tracking
        enemiesSpawned = 0;
        spawnTimer = 0;
        levelInitialized = true;
    }

    private void createObstacles(LevelConfig config) {
        int cellSize = 20;
        int halfCell = cellSize / 2;
        
        // 生成砖墙
        for (int[] pos : config.getWallLayout()) {
            int x = pos[0] * cellSize + halfCell;
            int y = pos[1] * cellSize + halfCell;
            entityPool.addEntity(new BrickWall(x, y));
        }

        // 生成铁墙
        for (int[] pos : config.getIronLayout()) {
            int x = pos[0] * cellSize + halfCell;
            int y = pos[1] * cellSize + halfCell;
            entityPool.addEntity(new IronWall(x, y));
        }

        // 生成河流
        for (int[] pos : config.getRiverLayout()) {
            int x = pos[0] * cellSize + halfCell;
            int y = pos[1] * cellSize + halfCell;
            entityPool.addEntity(new River(x, y));
        }
    }

    public void updateSpawning() {
        if (!levelInitialized) return;

        LevelConfig config = gameState.getSettings().isCustomGame() ?
                new LevelConfig(gameState.getCurrentLevel(), 
                        gameState.getSettings().getEnemyCount(),
                        gameState.getSettings().getTankSpeed(),
                        gameState.getSettings().isHasBoss()) :
                LevelConfig.getDefaultConfig(gameState.getCurrentLevel());

        int totalEnemies = config.getEnemyCount();
        boolean hasBoss = config.hasBoss();

        // 定时生成敌人
        if (enemiesSpawned < totalEnemies && entityPool.getEnemies().size() < 4) {
            spawnTimer++;
            if (spawnTimer >= 100) { // 每100帧(tick)生成一次
                spawnEnemy(enemiesSpawned, totalEnemies, hasBoss);
                enemiesSpawned++;
                spawnTimer = 0;
            }
        }
    }

    private void spawnEnemy(int spawnedCount, int totalEnemies, boolean hasBoss) {
        // 如果设置勾选了有关底，就在关底生成boss
        if (hasBoss && spawnedCount == totalEnemies - 1) {
            BossEnemyTank boss = new BossEnemyTank(300, 50, 3);
            entityPool.addEntity(boss);
            return;
        }

        // 基于概率随机选择生成的敌人类型
        int rand = random.nextInt(100);
        String type;
        int speed = gameState.getSettings().getTankSpeedValue();

        if (rand < 70) {
            type = "blue"; // 70%的概率
        } else if (rand < 80) {
            type = "pink"; // 10%的概率
        } else if (rand < 90) {
            type = "red"; // 10%的概率
        } else {
            type = "green"; // 10%的概率
        }

        // Spawn position (top area) - avoid obstacles
        int x, y;
        boolean validPosition = false;
        int attempts = 0;
        int spawnAreaWidth = 400;
        int spawnAreaHeight = 100;
        int spawnAreaOffsetX = 100;
        int spawnAreaOffsetY = 50;
        int tankWidth = 40;
        int tankHeight = 54;
        int maxSpawnAttempts = 50;
        
        while (!validPosition && attempts < maxSpawnAttempts) {
            x = spawnAreaOffsetX + random.nextInt(spawnAreaWidth);
            y = spawnAreaOffsetY + random.nextInt(spawnAreaHeight);
            
            // Check if position overlaps with obstacles
            boolean overlap = false;
            java.awt.Rectangle spawnBounds = new java.awt.Rectangle(x - tankWidth/2, y - tankHeight/2, tankWidth, tankHeight);
            
            for (Obstacle obs : entityPool.getObstacles()) {
                if (!obs.isDead()) {
                    java.awt.Rectangle obsBounds = new java.awt.Rectangle(
                        obs.getX() - obs.getWidth()/2, 
                        obs.getY() - obs.getHeight()/2, 
                        obs.getWidth(), 
                        obs.getHeight()
                    );
                    if (spawnBounds.intersects(obsBounds)) {
                        overlap = true;
                        break;
                    }
                }
            }
            
            if (!overlap) {
                validPosition = true;
                EnemyTank enemy = EnemyTankFactory.createEnemyTank(x, y, type, speed);
                entityPool.addEntity(enemy);
            }
            
            attempts++;
        }
    }

    public boolean isLevelInitialized() {
        return levelInitialized;
    }

    public void reset() {
        levelInitialized = false;
        enemiesSpawned = 0;
        spawnTimer = 0;
    }
}
