package ui;

import config.GameState;

import javax.swing.*;
import java.awt.*;

public class LevelCompleteDialog extends JDialog {
    private final GameState gameState;

    public LevelCompleteDialog(JFrame parent, GameState gameState) {
        super(parent, "过关！", true);
        this.gameState = gameState;
        initDialog();
    }

    private void initDialog() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        infoPanel.add(createInfoLine("第 " + gameState.getCurrentLevel() + " 关完成！"));
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(createInfoLine("本关得分: " + gameState.getLevelScore()));
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(createInfoLine("累计总分: " + gameState.getTotalScore()));

        add(infoPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton nextBtn = new JButton(gameState.getCurrentLevel() < 8 ? "下一关" : "查看纪录");
        nextBtn.addActionListener(e -> {
            dispose();
            if (gameState.getCurrentLevel() < 8) {
                gameState.startLevel(gameState.getCurrentLevel() + 1);
                gameState.setGameRunning(true);
            }
        });
        btnPanel.add(nextBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private JLabel createInfoLine(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("微软雅黑", Font.BOLD, 16));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}
