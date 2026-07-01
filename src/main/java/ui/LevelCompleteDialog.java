package ui;

import Entity.EnemyTank;
import config.GameState;
import game.TankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LevelCompleteDialog extends JDialog {
    private final GameState gameState;
    private final TankGame tankGame;

    // Tank type display info: color key, score per kill
    private static final String[] TANK_KEYS = {"cyan", "pink", "red", "green", "blue"};
    private static final int[] TANK_SCORES = {100, 200, 300, 400, 1000};

    // Pre-created enemy tanks for display (no AI, won't move)
    private final List<EnemyTank> displayTanks = new ArrayList<>();

    public LevelCompleteDialog(JFrame parent, GameState gameState, TankGame tankGame) {
        super(parent, "统计分数", true);
        this.gameState = gameState;
        this.tankGame = tankGame;
        initDisplayTanks();
        initDialog();
    }

    private void initDisplayTanks() {
        for (String color : TANK_KEYS) {
            EnemyTank et = new EnemyTank(0, 0, 100, 20, color, 2);
            et.stopAI(); // Stop AI thread so it doesn't move, but keep isDead=false for drawing
            displayTanks.add(et);
        }
    }

    private void initDialog() {
        setSize(520, 480);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);

        ScorePanel scorePanel = new ScorePanel();
        scorePanel.setPreferredSize(new Dimension(500, 500));
        add(scorePanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        btnPanel.setBackground(Color.BLACK);
        JButton nextBtn = new JButton(gameState.getCurrentLevel() < 8 ? "下一关" : "查看纪录");
        nextBtn.setFont(new Font("宋体", Font.BOLD, 16));
        nextBtn.setPreferredSize(new Dimension(90, 20));
        nextBtn.addActionListener(e -> {
            dispose();
            if (gameState.getCurrentLevel() < 8) {
                int nextLevel = gameState.getCurrentLevel() + 1;
                gameState.startLevel(nextLevel);
                tankGame.startLevel(nextLevel);
            }
        });
        btnPanel.add(nextBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private class ScorePanel extends JPanel {
        private final Font titleFont = new Font("宋体", Font.BOLD, 22);
        private final Font infoFont = new Font("宋体", Font.PLAIN, 18);
        private final Font totalFont = new Font("宋体", Font.BOLD, 18);

        // Scale factor for tank icons (tank is 40x54, scale to ~20x27)
        private static final double TANK_SCALE = 0.5;

        public ScorePanel() {
            setBackground(Color.BLACK);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int y = 35;

            // Title: stage N
            g2.setColor(Color.WHITE);
            g2.setFont(titleFont);
            String title = "stage  " + gameState.getCurrentLevel();
            FontMetrics fm = g2.getFontMetrics(titleFont);
            g2.drawString(title, (w - fm.stringWidth(title)) / 2, y);
            y += 45;

            // Tank kill breakdown
            Map<String, Integer> kills = gameState.getLevelKillCounts();
            int totalEnemy = 0;
            int totalScore = 0;

            for (int i = 0; i < TANK_KEYS.length; i++) {
                String type = TANK_KEYS[i];
                int scorePerKill = TANK_SCORES[i];
                int count = kills.getOrDefault(type, 0);
                int subtotal = count * scorePerKill;

                totalEnemy += count;
                totalScore += subtotal;

                int x = 55;

                // Draw scaled-down tank icon using EnemyTank's draw method
                EnemyTank displayTank = displayTanks.get(i);
                int tankX = x + 10;
                int tankY = y + 10;

                // Save and apply scale transform
                AffineTransform oldTransform = g2.getTransform();
                g2.translate(tankX, tankY);
                g2.scale(TANK_SCALE, TANK_SCALE);
                displayTank.setX(0);
                displayTank.setY(0);
                displayTank.draw(g2);
                g2.setTransform(oldTransform);

                // Count
                g2.setColor(Color.WHITE);
                g2.setFont(infoFont);
                String countStr = String.format("%2d", count);
                g2.drawString(countStr, x + 40, y + 14);

                // " x "
                int cx = x + 72;
                g2.drawString("x", cx, y + 14);

                // Score per kill (right-aligned)
                int sx = cx + 18;
                String scoreStr = String.valueOf(scorePerKill);
                g2.drawString(scoreStr, sx + 44 - g2.getFontMetrics(infoFont).stringWidth(scoreStr), y + 14);

                // " = "
                int eqx = sx + 52;
                g2.drawString("=", eqx, y + 14);

                // Subtotal (right-aligned)
                int subx = eqx + 20;
                String subStr = String.valueOf(subtotal);
                g2.drawString(subStr, subx + 64 - g2.getFontMetrics(infoFont).stringWidth(subStr), y + 14);

                y += 55;
            }

            // Separator line
            y += 8;
            g2.setColor(Color.WHITE);
            g2.drawLine(28, y, w - 28, y);
            y += 30;

            // Bottom info
            g2.setFont(totalFont);
            g2.setColor(Color.WHITE);
            String enemyStr = "enemy: " + totalEnemy;
            String totalStr = "total: " + totalScore;

            g2.drawString(enemyStr, 28, y);

            fm = g2.getFontMetrics(totalFont);
            g2.drawString(totalStr, w - 28 - fm.stringWidth(totalStr), y);
        }
    }
}
