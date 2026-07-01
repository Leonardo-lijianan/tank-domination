package Entity;

import java.awt.event.KeyEvent;

public class PlayerTank extends Tank {
    public PlayerTank(int x, int y, int hp, int cd) {
        super(x, y, hp, cd, "yellow",10);
        setTeamId(1);
    }


    public void handleKeyEvent(KeyEvent e) {
        System.out.println(e.getKeyChar());
        if(e.getKeyChar() == 'w') {
            rotate(0);
            move(0, -5);
        } else if (e.getKeyChar() == 'a') {
            rotate(1);
            move(-5, 0);
        }  else if (e.getKeyChar() == 's') {
            rotate(2);
            move(0, 5);
        } else if (e.getKeyChar() == 'd') {
            rotate(3);
            move(5, 0);
        }
        if (e.getKeyChar() == 'j') {
            shot();
        }
    }
}
