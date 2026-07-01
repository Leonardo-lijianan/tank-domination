package game;

import Entity.EntityPool;
import config.GameState;
import ui.GameHUD;

import javax.swing.JPanel;
import java.awt.*;

public class DrawPanel extends JPanel {
    private final EntityPool entityPool;
    private final GameState gameState;
    private final GameHUD gameHUD;
    private boolean showStageText;
    private int stageTextTimer;


    public DrawPanel(GameState gameState) {
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(600, 520));
        this.gameState = gameState;
        this.gameHUD = new GameHUD(gameState);
        entityPool = EntityPool.getEntityPoolSingleton();
        showStageText = false;
        stageTextTimer = 0;
    }

    public void showStageText() {
        showStageText = true;
        stageTextTimer = 150;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        entityPool.drawEntities(g);
        
        if (showStageText && stageTextTimer > 0) {
            drawStageText(g);
            stageTextTimer--;
            if (stageTextTimer <= 0) {
                showStageText = false;
            }
        }
        
        gameHUD.draw(g, getWidth(), getHeight());
    }

    private void drawStageText(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        String text = "STAGE " + gameState.getCurrentLevel();
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = getHeight() / 2;
        g2d.drawString(text, x, y);
    }
}
