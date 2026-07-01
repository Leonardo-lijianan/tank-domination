package Entity;

import java.awt.event.KeyEvent;

public class PlayerTank2 extends Tank {
    public PlayerTank2(int x, int y, int hp, int cd) {
        super(x, y, hp, cd, "yellow", 10);
        setTeamId(1);
    }

    public void handleKeyEvent(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            rotate(0);
            move(0, -5);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            rotate(1);
            move(-5, 0);
        } else if (keyCode == KeyEvent.VK_DOWN) {
            rotate(2);
            move(0, 5);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            rotate(3);
            move(5, 0);
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            shot();
        }
    }
}
