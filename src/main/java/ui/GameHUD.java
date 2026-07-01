package ui;

import config.GameState;

import java.awt.*;

public class GameHUD {
    private final GameState gameState;

    public GameHUD(GameState gameState) {
        this.gameState = gameState;
    }

    public void draw(Graphics g, int panelWidth, int panelHeight) {
        Graphics2D g2d = (Graphics2D) g;
        
        // Draw HUD background bar at bottom
        g2d.setColor(new Color(50, 50, 50));
        g2d.fillRect(0, panelHeight - 40, panelWidth, 40);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Draw stage info
        String stageText = "Stage " + gameState.getCurrentLevel();
        g2d.drawString(stageText, 20, panelHeight - 15);
        
        // Draw enemy count
        String enemyText = "Enemies: " + gameState.getEnemyRemaining();
        g2d.drawString(enemyText, panelWidth / 3, panelHeight - 15);
        
        // Draw player lives
        String livesText = "Lives: " + gameState.getPlayerLives();
        g2d.drawString(livesText, panelWidth * 2 / 3, panelHeight - 15);
        
        // Draw score
        String scoreText = "Score: " + gameState.getTotalScore();
        g2d.drawString(scoreText, panelWidth - 120, panelHeight - 15);
    }
}
