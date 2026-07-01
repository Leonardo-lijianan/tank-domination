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
    private JComboBox<String> startLevelCombo;
    private JComboBox<String> endLevelCombo;
    private JComboBox<String> speedCombo;
    private JComboBox<String> bulletSpeedCombo;
    private JSpinner enemyCountSpinner;
    private JCheckBox hasBossCheck;
    private JCheckBox continueGameCheck;
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
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(createPlayerPanel());
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(createGameModePanel());
        mainPanel.add(Box.createVerticalStrut(10));

        customPanel = createCustomPanel();
        customPanel.setEnabled(settings.isCustomGame());
        mainPanel.add(customPanel);

        add(mainPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        okBtn = new JButton("确定");
        cancelBtn = new JButton("取消");
        okBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private JPanel createPlayerPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("玩家模式"));
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
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("游戏模式"));
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
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("自选游戏设置"));

        panel.add(new JLabel("起始关数:"));
        startLevelCombo = new JComboBox<>(new String[]{"1","2","3","4","5","6","7","8"});
        startLevelCombo.setSelectedItem(String.valueOf(settings.getStartLevel()));
        panel.add(startLevelCombo);

        panel.add(new JLabel("结束关数:"));
        endLevelCombo = new JComboBox<>(new String[]{"1","2","3","4","5","6","7","8"});
        endLevelCombo.setSelectedItem(String.valueOf(settings.getEndLevel()));
        panel.add(endLevelCombo);

        panel.add(new JLabel("坦克速度:"));
        speedCombo = new JComboBox<>(new String[]{"慢速","中速","快速"});
        speedCombo.setSelectedItem(settings.getTankSpeed().equals("slow") ? "慢速" :
                settings.getTankSpeed().equals("medium") ? "中速" : "快速");
        panel.add(speedCombo);

        panel.add(new JLabel("子弹速度:"));
        bulletSpeedCombo = new JComboBox<>(new String[]{"慢","中","快"});
        bulletSpeedCombo.setSelectedItem(settings.getBulletSpeed() <= 5 ? "慢" :
                settings.getBulletSpeed() <= 10 ? "中" : "快");
        panel.add(bulletSpeedCombo);

        panel.add(new JLabel("敌人坦克数量:"));
        enemyCountSpinner = new JSpinner(new SpinnerNumberModel(settings.getEnemyCount(), 5, 50, 1));
        panel.add(enemyCountSpinner);

        panel.add(new JLabel("是否有关底:"));
        hasBossCheck = new JCheckBox();
        hasBossCheck.setSelected(settings.isHasBoss());
        panel.add(hasBossCheck);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okBtn) {
            settings.setTwoPlayer(twoPlayerBtn.isSelected());
            settings.setCustomGame(customGameBtn.isSelected());
            settings.setStartLevel(Integer.parseInt((String) startLevelCombo.getSelectedItem()));
            settings.setEndLevel(Integer.parseInt((String) endLevelCombo.getSelectedItem()));
            String speed = (String) speedCombo.getSelectedItem();
            settings.setTankSpeed(speed.equals("慢速") ? "slow" : speed.equals("中速") ? "medium" : "fast");
            String bulletSpeed = (String) bulletSpeedCombo.getSelectedItem();
            settings.setBulletSpeed(bulletSpeed.equals("慢") ? 5 : bulletSpeed.equals("中") ? 10 : 15);
            settings.setEnemyCount((Integer) enemyCountSpinner.getValue());
            settings.setHasBoss(hasBossCheck.isSelected());
            settings.setContinueGame(continueGameCheck != null && continueGameCheck.isSelected());
            dispose();
        } else {
            dispose();
        }
    }
}
