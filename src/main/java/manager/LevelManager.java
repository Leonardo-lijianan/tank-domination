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
        // Clear existing entities
        entityPool.getEnemies().clear();
        entityPool.getBullets().clear();
        entityPool.getObstacles().clear();

        // Create obstacles from level config
        createObstacles(config);

        // Reset spawn tracking
        enemiesSpawned = 0;
        spawnTimer = 0;
        levelInitialized = true;
    }

    private void createObstacles(LevelConfig config) {
        int cellSize = 30;
        
        // Create brick walls
        for (int[] pos : config.getWallLayout()) {
            int x = pos[0] * cellSize;
            int y = pos[1] * cellSize;
            entityPool.addEntity(new BrickWall(x, y));
        }

        // Create iron walls
        for (int[] pos : config.getIronLayout()) {
            int x = pos[0] * cellSize;
            int y = pos[1] * cellSize;
            entityPool.addEntity(new IronWall(x, y));
        }

        // Create rivers
        for (int[] pos : config.getRiverLayout()) {
            int x = pos[0] * cellSize;
            int y = pos[1] * cellSize;
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

        // Spawn enemies periodically
        if (enemiesSpawned < totalEnemies && entityPool.getEnemies().size() < 4) {
            spawnTimer++;
            if (spawnTimer >= 100) { // Spawn every 100 ticks
                spawnEnemy(enemiesSpawned, totalEnemies, hasBoss);
                enemiesSpawned++;
                spawnTimer = 0;
            }
        }
    }

    private void spawnEnemy(int spawnedCount, int totalEnemies, boolean hasBoss) {
        // Spawn boss at the end if applicable
        if (hasBoss && spawnedCount == totalEnemies - 1) {
            BossEnemyTank boss = new BossEnemyTank(300, 50, 3);
            entityPool.addEntity(boss);
            return;
        }

        // Random enemy type based on probabilities
        int rand = random.nextInt(100);
        String type;
        int speed = gameState.getSettings().getTankSpeedValue();

        if (rand < 70) {
            type = "blue"; // 70% probability
        } else if (rand < 80) {
            type = "pink"; // 10% probability
        } else if (rand < 90) {
            type = "red"; // 10% probability
        } else {
            type = "green"; // 10% probability
        }

        // Spawn position (top area)
        int x = 100 + random.nextInt(400);
        int y = 50 + random.nextInt(100);

        EnemyTank enemy = EnemyTankFactory.createEnemyTank(x, y, type, speed);
        entityPool.addEntity(enemy);
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
