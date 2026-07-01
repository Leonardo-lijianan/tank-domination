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
    private JMenuItem newGameItem;
    private JMenuItem exitItem;
    private JMenuItem gameSettingsItem;
    private JMenuItem recordItem;

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

        newGameItem = new JMenuItem("新游戏");
        newGameItem.addActionListener(this);
        exitItem = new JMenuItem("退出");
        exitItem.addActionListener(this);

        gameSettingsItem = new JMenuItem("设置游戏选项");
        gameSettingsItem.addActionListener(this);
        recordItem = new JMenuItem("游戏纪录");
        recordItem.addActionListener(this);

        gameMenu.add(newGameItem);
        gameMenu.add(exitItem);
        settingsMenu.add(gameSettingsItem);
        settingsMenu.add(recordItem);

        add(gameMenu);
        add(settingsMenu);
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
        }
    }
}
