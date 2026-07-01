package Entity;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class EntityPool {
    private final ArrayList<PlayerTank> players;
    private final ArrayList<EnemyTank> enemies;
    private final ArrayList<Bullet> bullets;
    private final ArrayList<Obstacle> obstacles;
    private final ArrayDeque<Entity> writeQueue;
    private static EntityPool entityPool;

    private final ArrayList<Explosion> explosions;

    private EntityPool() {
        players = new ArrayList<>();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        obstacles = new ArrayList<>();
        explosions = new ArrayList<>();
        writeQueue = new ArrayDeque<>();
    }

    public static EntityPool getEntityPoolSingleton() {
        if (entityPool == null) {
            entityPool = new EntityPool();
        }
        return entityPool;
    }

    public void reset() {
        players.clear();
        enemies.clear();
        bullets.clear();
        obstacles.clear();
        explosions.clear();
        writeQueue.clear();
    }

    public void addEntity(Entity entity) {
        this.writeQueue.add(entity);
    }

    public void drawEntities(Graphics g) {
        drawArrEntities(g, obstacles);
        drawArrEntities(g, bullets);
        drawArrEntities(g, enemies);
        drawArrEntities(g, players);
        drawArrEntities(g, explosions);
    }

    private void drawArrEntities(Graphics g, ArrayList<? extends Entity> entities) {
        for(Entity it_entity: entities) {
            it_entity.draw(g);
        }
    }

    public void handleCollided() {
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < bullets.size(); j++) {
                Entity a = players.get(i);
                Entity b = bullets.get(j);
                if (b.isCollided(a)) {
                    a.handleCollided(b);
                    b.handleCollided(a);
                }
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            for (int j = 0; j < bullets.size(); j++) {
                Entity a = enemies.get(i);
                Entity b = bullets.get(j);
                if (b.isCollided(a)) {
                    a.handleCollided(b);
                    b.handleCollided(a);
                }
            }
        }

        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < obstacles.size(); j++) {
                Bullet bullet = bullets.get(i);
                Obstacle obstacle = obstacles.get(j);
                if (!obstacle.isBulletPassable() && bullet.isCollided(obstacle)) {
                    obstacle.handleCollided(bullet);
                    bullet.handleCollided(obstacle);
                }
            }
        }

        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < obstacles.size(); j++) {
                Tank player = players.get(i);
                Obstacle obstacle = obstacles.get(j);
                if (!obstacle.isTankPassable() && player.isCollided(obstacle)) {
                    player.handleCollided(obstacle);
                }
            }
        }

        for (int i = 0; i < enemies.size(); i++) {
            for (int j = 0; j < obstacles.size(); j++) {
                EnemyTank enemy = enemies.get(i);
                Obstacle obstacle = obstacles.get(j);
                if (!obstacle.isTankPassable() && enemy.isCollided(obstacle)) {
                    enemy.handleCollided(obstacle);
                }
            }
        }

        enemies.removeIf(Entity::isDead);
        players.removeIf(Entity::isDead);
        bullets.removeIf(Entity::isDead);
        obstacles.removeIf(Entity::isDead);
    }

    public void updateEntity() {
        updateArrEntities(obstacles);
        updateArrEntities(bullets);
        updateArrEntities(enemies);
        updateArrEntities(players);
        updateArrEntities(explosions);
        addEntities();
        this.writeQueue.clear();
    }

    private void addEntities() {
        while(!this.writeQueue.isEmpty()) {
            Entity entity = writeQueue.pop();
            switch (entity) {
                case Bullet bullet -> bullets.add(bullet);
                case EnemyTank enemy -> enemies.add(enemy);
                case PlayerTank player -> players.add(player);
                case Obstacle obstacle -> obstacles.add(obstacle);
                case Explosion explosion -> explosions.add(explosion);
                default -> {}
            }
        }
    }

    private void updateArrEntities(ArrayList<? extends Entity> entities) {
        for(Entity it_entity: entities) {
            it_entity.update();
        }
    }

    public ArrayList<Obstacle> getObstacles() { return obstacles; }
    public ArrayList<Bullet> getBullets() { return bullets; }
    public ArrayList<EnemyTank> getEnemies() { return enemies; }
    public ArrayList<PlayerTank> getPlayers() { return players; }
}
