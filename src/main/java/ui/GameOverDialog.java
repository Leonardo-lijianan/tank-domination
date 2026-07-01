package ui;

import config.GameState;
import database.GameRecordDAO;

import javax.swing.*;
import java.awt.*;

public class GameOverDialog extends JDialog {
    private final GameState gameState;

    public GameOverDialog(JFrame parent, GameState gameState) {
        super(parent, "游戏结束", true);
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

        infoPanel.add(createInfoLine("游戏结束！"));
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(createInfoLine("最终得分: " + gameState.getTotalScore()));

        add(infoPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton okBtn = new JButton("确定");
        okBtn.addActionListener(e -> {
            GameRecordDAO dao = new GameRecordDAO();
            if (dao.isNewRecord(gameState.getTotalScore())) {
                boolean b = dao.getTopRecords(1).isEmpty() || ((Integer) dao.getTopRecords(1).getFirst()[1]) < gameState.getTotalScore();
                if (b) {
                    String name = JOptionPane.showInputDialog(this, "恭喜破纪录！请输入姓名:");
                    if (name != null && !name.isEmpty()) {
                        dao.saveRecord(name, gameState.getTotalScore());
                    }
                }
            }
            dispose();
        });
        btnPanel.add(okBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private JLabel createInfoLine(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("微软雅黑", Font.BOLD, 16));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}
