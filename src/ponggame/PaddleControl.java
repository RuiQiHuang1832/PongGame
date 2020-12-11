package ponggame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PaddleControl implements KeyListener {

    private Paddle p1;
    private final int up;
    private final int down;


    public PaddleControl(Paddle p1, int up, int down) {
        this.p1 = p1;
        this.up = up;
        this.down = down;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyPressed = e.getKeyCode();
        if (keyPressed == up) {
            this.p1.toggleUpPressed();
        }
        if (keyPressed == down) {
            this.p1.toggleDownPressed();
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyReleased = e.getKeyCode();
        if (keyReleased == up) {
            this.p1.unToggleUpPressed();
        }
        if (keyReleased == down) {
            this.p1.unToggleDownPressed();
        }

    }
}
