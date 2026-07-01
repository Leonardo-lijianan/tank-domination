package ui;

import config.GameSettings;
import config.GameState;
import game.TankGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu extends JMenuBar implements ActionListener {
    private JMenu gameMenu;
    private JMenu settingsMenu;
    private JMenu helpMenu;
    private JMenuItem newGameItem;
    private JMenuItem exitItem;
    private JMenuItem gameSettingsItem;
    private JMenuItem recordItem;
    private JMenuItem helpItem;
    private JMenuItem aboutItem;

    private final GameState gameState;
    private TankGame tankGame;

    public GameMenu(GameState gameState, TankGame tankGame) {
        this.gameState = gameState;
        this.tankGame = tankGame;
        initMenu();
    }

    private void initMenu() {
        gameMenu = new JMenu("游戏");
        settingsMenu = new JMenu("设置");
        helpMenu = new JMenu("帮助");

        newGameItem = new JMenuItem("新游戏");
        newGameItem.addActionListener(this);
        exitItem = new JMenuItem("退出");
        exitItem.addActionListener(this);

        gameSettingsItem = new JMenuItem("设置游戏选项");
        gameSettingsItem.addActionListener(this);
        recordItem = new JMenuItem("游戏纪录");
        recordItem.addActionListener(this);

        helpItem = new JMenuItem("游戏说明");
        helpItem.addActionListener(this);
        aboutItem = new JMenuItem("关于");
        aboutItem.addActionListener(this);

        gameMenu.add(newGameItem);
        gameMenu.add(exitItem);
        settingsMenu.add(gameSettingsItem);
        settingsMenu.add(recordItem);
        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);

        add(gameMenu);
        add(settingsMenu);
        add(helpMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameItem) {
            tankGame.startNewGame();
        } else if (e.getSource() == exitItem) {
            System.exit(0);
        } else if (e.getSource() == gameSettingsItem) {
            GameSettingsDialog dialog = new GameSettingsDialog(gameState.getSettings());
            dialog.setVisible(true);
        } else if (e.getSource() == recordItem) {
            GameRecordDialog dialog = new GameRecordDialog();
            dialog.setVisible(true);
        } else if (e.getSource() == helpItem) {
            showHelp();
        } else if (e.getSource() == aboutItem) {
            showAbout();
        }
    }

    private void showHelp() {
        String helpText =
            "【操作说明】\n\n" +
            "玩家1：\n" +
            "  W/S/A/D - 上下左右移动\n" +
            "  J - 射击\n\n" +
            "玩家2（双人模式）：\n" +
            "  ↑/↓/←/→ - 上下左右移动\n" +
            "  Enter - 射击\n\n" +
            "【障碍物说明】\n" +
            "  砖墙 - 可被子弹打穿，阻止坦克移动\n" +
            "  铁墙 - 不可被子弹打穿，阻止坦克移动\n" +
            "  河流 - 子弹可通过，阻止坦克移动\n\n" +
            "【敌人坦克】\n" +
            "  浅蓝色 - 普通坦克，100分\n" +
            "  粉色 - 射击速度快，200分\n" +
            "  红色 - 移动速度快，300分\n" +
            "  绿色 - 重型坦克，需4次击毁，400分\n" +
            "  深蓝色 - BOSS坦克，需6次击毁，1000分";

        JTextArea textArea = new JTextArea(helpText);
        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("宋体", java.awt.Font.PLAIN, 13));
        textArea.setMargin(new java.awt.Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(350, 380));

        JOptionPane.showMessageDialog(
            tankGame,
            scrollPane,
            "游戏说明",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(
            tankGame,
            "坦克游戏\n版本 1.0\n\nJava编写的经典坦克大战游戏\n制作人 李家楠\n联系方式 leonardo-lijianan@outlook.com",
            "关于",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
