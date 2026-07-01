package config;

import java.util.HashMap;
import java.util.Map;

public class GameState {
    private static GameState instance;

    private int currentLevel;
    private int totalScore;
    private int levelScore;
    private int playerLives;
    private int enemyRemaining;
    private boolean isGameRunning;
    private boolean isGameOver;
    private boolean isLevelComplete;
    private GameSettings settings;

    private boolean isPlayerRespawning;
    private int respawnTimer;

    // Kill tracking per tank type for score dialog
    private Map<String, Integer> levelKillCounts;

    private GameState() {
        reset();
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public void reset() {
        GameSettings oldSettings = this.settings;
        currentLevel = 1;
        totalScore = 0;
        levelScore = 0;
        playerLives = oldSettings != null ? oldSettings.getPlayerLives() : 3;
        enemyRemaining = oldSettings != null ? oldSettings.getEnemyCount() : 20;
        isGameRunning = false;
        isGameOver = false;
        isLevelComplete = false;
        isPlayerRespawning = false;
        respawnTimer = 0;
        levelKillCounts = new HashMap<>();
        initKillCounts();
        settings = (oldSettings != null) ? oldSettings : new GameSettings();
    }

    private void initKillCounts() {
        levelKillCounts.put("blue", 0);
        levelKillCounts.put("pink", 0);
        levelKillCounts.put("red", 0);
        levelKillCounts.put("green", 0);
        levelKillCounts.put("navy", 0);
    }

    public void startLevel(int level) {
        LevelConfig config = settings.isCustomGame() ?
                new LevelConfig(level, settings.getEnemyCount(), settings.getTankSpeed(), settings.isHasBoss()) :
                LevelConfig.getDefaultConfig(level);
        currentLevel = level;
        levelScore = 0;
        enemyRemaining = config.getEnemyCount();
        isLevelComplete = false;
        initKillCounts();
    }

    public void addScore(int score) {
        levelScore += score;
        totalScore += score;
    }

    public void decreaseEnemy() {
        if (enemyRemaining > 0) {
            enemyRemaining--;
        }
    }

    public void recordKill(String tankType) {
        levelKillCounts.put(tankType, levelKillCounts.getOrDefault(tankType, 0) + 1);
    }

    public Map<String, Integer> getLevelKillCounts() { return levelKillCounts; }

    public void decreasePlayerLife() {
        if (playerLives > 0) {
            playerLives--;
        }
        if (playerLives == 0) {
            isGameOver = true;
            isGameRunning = false;
        } else {
            isPlayerRespawning = true;
            respawnTimer = 120;
        }
    }

    public boolean isPlayerRespawning() { return isPlayerRespawning; }
    public int getRespawnTimer() { return respawnTimer; }
    public void tickRespawn() {
        if (respawnTimer > 0) {
            respawnTimer--;
            if (respawnTimer == 0) {
                isPlayerRespawning = false;
            }
        }
    }

    public void checkLevelComplete() {
        if (enemyRemaining == 0 && isGameRunning) {
            isLevelComplete = true;
        }
    }

    public int getCurrentLevel() { return currentLevel; }
    public int getTotalScore() { return totalScore; }
    public int getLevelScore() { return levelScore; }
    public int getPlayerLives() { return playerLives; }
    public int getEnemyRemaining() { return enemyRemaining; }
    public boolean isGameRunning() { return isGameRunning; }
    public void setGameRunning(boolean running) { isGameRunning = running; }
    public boolean isGameOver() { return isGameOver; }
    public boolean isLevelComplete() { return isLevelComplete; }
    public GameSettings getSettings() { return settings; }
    public void setSettings(GameSettings settings) { this.settings = settings; }
}
