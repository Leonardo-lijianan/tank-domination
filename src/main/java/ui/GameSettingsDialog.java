package ui;

import config.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSettingsDialog extends JDialog implements ActionListener {
    private final GameSettings settings;

    private JRadioButton singlePlayerBtn;
    private JRadioButton twoPlayerBtn;
    private JRadioButton normalGameBtn;
    private JRadioButton customGameBtn;
    private JComboBox<String> levelCombo;
    private JComboBox<String> speedCombo;
    private JComboBox<String> bulletSpeedCombo;
    private JTextField enemyCountField;
    private JCheckBox continueGameCheck;
    private JCheckBox hasBossCheck;
    private JButton okBtn;
    private JButton cancelBtn;

    private JPanel customPanel;

    public GameSettingsDialog(GameSettings settings) {
        this.settings = settings;
        initDialog();
    }

    private void initDialog() {
        setTitle("游戏设置");
        setModal(true);
        setSize(320, 380);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        mainPanel.add(createPlayerPanel());
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(createGameModePanel());
        mainPanel.add(Box.createVerticalStrut(8));

        customPanel = createCustomPanel();
        customPanel.setEnabled(settings.isCustomGame());
        mainPanel.add(customPanel);

        add(mainPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        okBtn = new JButton("确定");
        cancelBtn = new JButton("取消");
        okBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private JPanel createPlayerPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        ButtonGroup group = new ButtonGroup();
        singlePlayerBtn = new JRadioButton("单人游戏");
        twoPlayerBtn = new JRadioButton("双人游戏");
        singlePlayerBtn.setSelected(!settings.isTwoPlayer());
        twoPlayerBtn.setSelected(settings.isTwoPlayer());
        group.add(singlePlayerBtn);
        group.add(twoPlayerBtn);
        panel.add(singlePlayerBtn);
        panel.add(twoPlayerBtn);
        return panel;
    }

    private JPanel createGameModePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        ButtonGroup group = new ButtonGroup();
        normalGameBtn = new JRadioButton("正常游戏");
        customGameBtn = new JRadioButton("自选游戏");
        normalGameBtn.setSelected(!settings.isCustomGame());
        customGameBtn.setSelected(settings.isCustomGame());
        group.add(normalGameBtn);
        group.add(customGameBtn);

        normalGameBtn.addActionListener(e -> customPanel.setEnabled(false));
        customGameBtn.addActionListener(e -> customPanel.setEnabled(true));

        panel.add(normalGameBtn);
        panel.add(customGameBtn);
        return panel;
    }

    private JPanel createCustomPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // 请选择关数
        JPanel levelRow = createRow("请选择关数:");
        levelCombo = new JComboBox<>(new String[]{"1","2","3","4","5","6","7","8"});
        levelCombo.setSelectedItem(String.valueOf(settings.getStartLevel()));
        levelRow.add(levelCombo);
        panel.add(levelRow);

        // 坦克速度
        JPanel speedRow = createRow("坦克速度:");
        speedCombo = new JComboBox<>(new String[]{"0","1","2"});
        speedCombo.setSelectedItem(String.valueOf(getSpeedIndex()));
        speedRow.add(speedCombo);
        panel.add(speedRow);

        // 子弹速度
        JPanel bulletRow = createRow("子弹速度:");
        bulletSpeedCombo = new JComboBox<>(new String[]{"0","1","2"});
        bulletSpeedCombo.setSelectedItem(String.valueOf(getBulletSpeedIndex()));
        bulletRow.add(bulletSpeedCombo);
        panel.add(bulletRow);

        // 坦克数量
        JPanel enemyRow = createRow("坦克数量(10-50):");
        enemyCountField = new JTextField(String.valueOf(settings.getEnemyCount()), 5);
        enemyRow.add(enemyCountField);
        panel.add(enemyRow);

        // Checkboxes
        JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        continueGameCheck = new JCheckBox("继续游戏");
        continueGameCheck.setSelected(settings.isContinueGame());
        hasBossCheck = new JCheckBox("出现关底");
        hasBossCheck.setSelected(settings.isHasBoss());
        checkPanel.add(continueGameCheck);
        checkPanel.add(hasBossCheck);
        panel.add(checkPanel);

        return panel;
    }

    private JPanel createRow(String label) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        JLabel lbl = new JLabel(label);
        lbl.setPreferredSize(new Dimension(110, 25));
        row.add(lbl);
        return row;
    }

    private int getSpeedIndex() {
        switch (settings.getTankSpeed()) {
            case "slow": return 0;
            case "medium": return 1;
            case "fast": return 2;
            default: return 0;
        }
    }

    private int getBulletSpeedIndex() {
        int bs = settings.getBulletSpeed();
        if (bs <= 5) return 0;
        if (bs <= 10) return 1;
        return 2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okBtn) {
            settings.setTwoPlayer(twoPlayerBtn.isSelected());
            settings.setCustomGame(customGameBtn.isSelected());
            settings.setStartLevel(Integer.parseInt((String) levelCombo.getSelectedItem()));
            settings.setEndLevel(settings.getStartLevel());

            String speed = (String) speedCombo.getSelectedItem();
            settings.setTankSpeed(speed.equals("0") ? "slow" : speed.equals("1") ? "medium" : "fast");

            String bulletSpeed = (String) bulletSpeedCombo.getSelectedItem();
            settings.setBulletSpeed(bulletSpeed.equals("0") ? 5 : bulletSpeed.equals("1") ? 10 : 15);

            try {
                int count = Integer.parseInt(enemyCountField.getText().trim());
                settings.setEnemyCount(Math.max(10, Math.min(50, count)));
            } catch (NumberFormatException ex) {
                settings.setEnemyCount(20);
            }

            settings.setHasBoss(hasBossCheck.isSelected());
            settings.setContinueGame(continueGameCheck.isSelected());
            dispose();
        } else {
            dispose();
        }
    }
}
