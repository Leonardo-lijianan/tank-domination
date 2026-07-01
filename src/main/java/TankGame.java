package game;

import Entity.*;
import config.GameState;
import config.LevelConfig;
import manager.LevelManager;
import ui.*;
import util.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;

public class TankGame extends JFrame {
    private DrawPanel drawPanel;
    private PlayerTank player;
    private PlayerTank2 player2;
    private Timer gameLoop;
    private GameState gameState;
    private LevelManager levelManager;
    private GameMenu gameMenu;
    private InputHandler inputHandler;
    private int shotCooldown;


    public TankGame() {
        gameState = GameState.getInstance();
        inputHandler = new InputHandler();
        initDrawPanel();
        initJFrame();
        initLevelManager();
        initGameLoop();
        gameLoop.start();
        startLevel(1);
    }

    public void startNewGame() {
        gameState.reset();
        startLevel(1);
    }

    public void startLevelFromMenu(int level) {
        startLevel(level);
    }

    public GameState getGameState() {
        return gameState;
    }

    private void initLevelManager() {
        levelManager = new LevelManager(gameState, EntityPool.getEntityPoolSingleton());
    }

    private void initGameLoop() {
        gameLoop = new Timer(16, (e) -> {
            if (gameState.isGameRunning()) {
                EntityPool entityPool = EntityPool.getEntityPoolSingleton();
                handlePlayerInput();
                levelManager.updateSpawning();
                entityPool.updateEntity();
                handleCollisions();
                handleRespawn();
                checkGameState();
                repaint();
            }
        });
    }

    private void handleRespawn() {
        if (gameState.isPlayerRespawning()) {
            gameState.tickRespawn();
            if (!gameState.isPlayerRespawning()) {
                EntityPool entityPool = EntityPool.getEntityPoolSingleton();
                this.player = new PlayerTank(300, 450, 100, 10);
                entityPool.addEntity(this.player);
            }
        }
    }

    private void handlePlayerInput() {
        if (player != null && !player.isDead()) {
            boolean moved = false;
            if (inputHandler.isKeyPressed(KeyEvent.VK_W)) {
                player.rotate(0);
                player.move(0, -5);
                moved = true;
            } else if (inputHandler.isKeyPressed(KeyEvent.VK_S)) {
                player.rotate(2);
                player.move(0, 5);
                moved = true;
            } else if (inputHandler.isKeyPressed(KeyEvent.VK_A)) {
                player.rotate(1);
                player.move(-5, 0);
                moved = true;
            } else if (inputHandler.isKeyPressed(KeyEvent.VK_D)) {
                player.rotate(3);
                player.move(5, 0);
                moved = true;
            }
            
            if (inputHandler.isKeyPressed(KeyEvent.VK_J)) {
                if (shotCooldown <= 0) {
                    player.shot();
                    shotCooldown = 5;
                }
            }
            if (shotCooldown > 0) shotCooldown--;
        }

        if (player2 != null && !player2.isDead() && gameState.getSettings().isTwoPlayer()) {
            if (inputHandler.isKeyPressed(KeyEvent.VK_UP)) {
                player2.rotate(0);
                player2.move(0, -5);
            } else if (inputHandler.isKeyPressed(KeyEvent.VK_DOWN)) {
                player2.rotate(2);
                player2.move(0, 5);
            } else if (inputHandler.isKeyPressed(KeyEvent.VK_LEFT)) {
                player2.rotate(1);
                player2.move(-5, 0);
            } else if (inputHandler.isKeyPressed(KeyEvent.VK_RIGHT)) {
                player2.rotate(3);
                player2.move(5, 0);
            }
            if (inputHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                player2.shot();
            }
        }
    }

    private void handleCollisions() {
        EntityPool entityPool = EntityPool.getEntityPoolSingleton();
        
        // Player bullets vs enemies
        for (PlayerTank p : entityPool.getPlayers()) {
            for (int j = 0; j < entityPool.getBullets().size(); j++) {
                Bullet bullet = entityPool.getBullets().get(j);
                if (bullet.getEntityId() == 1 && !bullet.isDead()) {
                    for (EnemyTank enemy : entityPool.getEnemies()) {
                        if (!enemy.isDead() && bullet.isCollided(enemy)) {
                            enemy.handleCollided(bullet);
                            bullet.setDead(true);
                            if (enemy.isDead()) {
                                int score = ScoreManager.getScore(enemy.getColor());
                                gameState.addScore(score);
                                gameState.decreaseEnemy();
                                gameState.checkLevelComplete();
                                entityPool.addEntity(new Explosion(enemy.getX(), enemy.getY()));
                                SoundManager.getInstance().playExplosion();
                            }
                            break;
                        }
                    }
                }
            }
        }

        // Enemy bullets vs players
        for (PlayerTank p : entityPool.getPlayers()) {
            for (Bullet bullet : entityPool.getBullets()) {
                if (bullet.getEntityId() == 2 && !bullet.isDead() && !p.isDead()) {
                    if (bullet.isCollided(p)) {
                        p.handleCollided(bullet);
                        bullet.setDead(true);
                        if (p.isDead()) {
                            gameState.decreasePlayerLife();
                        }
                    }
                }
            }
        }

        // Bullets vs obstacles
        for (Bullet bullet : entityPool.getBullets()) {
            if (!bullet.isDead()) {
                for (Obstacle obstacle : entityPool.getObstacles()) {
                    if (!obstacle.isBulletPassable() && !obstacle.isDead() && bullet.isCollided(obstacle)) {
                        obstacle.handleCollided(bullet);
                        bullet.setDead(true);
                        break;
                    }
                }
            }
        }

        // Players vs obstacles
        for (PlayerTank p : entityPool.getPlayers()) {
            if (!p.isDead()) {
                for (Obstacle obstacle : entityPool.getObstacles()) {
                    if (!obstacle.isTankPassable() && !obstacle.isDead() && p.isCollided(obstacle)) {
                        p.handleCollided(obstacle);
                    }
                }
            }
        }

        // Enemies vs obstacles
        for (EnemyTank enemy : entityPool.getEnemies()) {
            if (!enemy.isDead()) {
                for (Obstacle obstacle : entityPool.getObstacles()) {
                    if (!obstacle.isTankPassable() && !obstacle.isDead() && enemy.isCollided(obstacle)) {
                        enemy.handleCollided(obstacle);
                    }
                }
            }
        }

        // Remove dead entities
        entityPool.getEnemies().removeIf(Entity::isDead);
        entityPool.getPlayers().removeIf(Entity::isDead);
        entityPool.getBullets().removeIf(Entity::isDead);
        entityPool.getObstacles().removeIf(Entity::isDead);
    }

    private void checkGameState() {
        if (gameState.isLevelComplete()) {
            gameState.setGameRunning(false);
            LevelCompleteDialog dialog = new LevelCompleteDialog(this, gameState);
            dialog.setVisible(true);
            if (gameState.isGameRunning()) {
                startLevel(gameState.getCurrentLevel());
            }
        }

        if (gameState.isGameOver()) {
            GameOverDialog dialog = new GameOverDialog(this, gameState);
            dialog.setVisible(true);
            gameState.reset();
        }
    }

    private void startLevel(int level) {
        EntityPool entityPool = EntityPool.getEntityPoolSingleton();
        entityPool.reset();

        gameState.startLevel(level);
        levelManager.reset();
        
        LevelConfig config = gameState.getSettings().isCustomGame() ?
                new LevelConfig(level, gameState.getSettings().getEnemyCount(),
                        gameState.getSettings().getTankSpeed(),
                        gameState.getSettings().isHasBoss()) :
                LevelConfig.getDefaultConfig(level);

        levelManager.initializeLevel(config);

        // Create player tank
        this.player = new PlayerTank(300, 450, 100, 10);
        entityPool.addEntity(this.player);

        // Create player 2 if two-player mode
        if (gameState.getSettings().isTwoPlayer()) {
            this.player2 = new PlayerTank2(250, 450, 100, 10);
            entityPool.addEntity(this.player2);
        }

        drawPanel.showStageText();
        gameState.setGameRunning(true);
    }

    private void initDrawPanel() {
        this.drawPanel = new DrawPanel(gameState);
    }

    private void initJFrame() {
        this.setTitle("Tank Game");
        this.setBackground(Color.GRAY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Block IME input method to prevent interference with game controls
        this.enableInputMethods(false);
        this.addInputMethodListener(new InputMethodListener() {
            @Override public void caretPositionChanged(InputMethodEvent e) {}
            @Override public void inputMethodTextChanged(InputMethodEvent e) {
                e.consume();
            }
        });
        
        // Create and set menu bar
        gameMenu = new GameMenu(gameState, this);
        this.setJMenuBar(gameMenu);
        
        this.add(this.drawPanel);
        this.pack();
        setLocationRelativeTo(null);
        setResizable(false);
        this.setVisible(true);
        this.addKeyListener(inputHandler);
        this.requestFocusInWindow();
    }
}
