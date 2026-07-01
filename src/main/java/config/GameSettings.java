package config;

public class GameSettings {
    private boolean isTwoPlayer;
    private boolean isCustomGame;
    private int startLevel;
    private int endLevel;
    private String tankSpeed;
    private int bulletSpeed;
    private int enemyCount;
    private boolean hasBoss;
    private boolean continueGame;
    private int playerLives;

    public GameSettings() {
        this.isTwoPlayer = false;
        this.isCustomGame = false;
        this.startLevel = 1;
        this.endLevel = 8;
        this.tankSpeed = "slow";
        this.bulletSpeed = 10;
        this.enemyCount = 20;
        this.hasBoss = false;
        this.continueGame = true;
        this.playerLives = 3;
    }

    public boolean isTwoPlayer() { return isTwoPlayer; }
    public void setTwoPlayer(boolean twoPlayer) { isTwoPlayer = twoPlayer; }
    public boolean isCustomGame() { return isCustomGame; }
    public void setCustomGame(boolean customGame) { isCustomGame = customGame; }
    public int getStartLevel() { return startLevel; }
    public void setStartLevel(int startLevel) { this.startLevel = startLevel; }
    public int getEndLevel() { return endLevel; }
    public void setEndLevel(int endLevel) { this.endLevel = endLevel; }
    public String getTankSpeed() { return tankSpeed; }
    public void setTankSpeed(String tankSpeed) { this.tankSpeed = tankSpeed; }
    public int getBulletSpeed() { return bulletSpeed; }
    public void setBulletSpeed(int bulletSpeed) { this.bulletSpeed = bulletSpeed; }
    public int getEnemyCount() { return enemyCount; }
    public void setEnemyCount(int enemyCount) { this.enemyCount = enemyCount; }
    public boolean isHasBoss() { return hasBoss; }
    public void setHasBoss(boolean hasBoss) { this.hasBoss = hasBoss; }
    public boolean isContinueGame() { return continueGame; }
    public void setContinueGame(boolean continueGame) { this.continueGame = continueGame; }
    public int getPlayerLives() { return playerLives; }
    public void setPlayerLives(int playerLives) { this.playerLives = playerLives; }

    public int getTankSpeedValue() {
        switch (tankSpeed) {
            case "slow": return 1;
            case "medium": return 2;
            case "fast": return 3;
            default: return 1;
        }
    }
}
