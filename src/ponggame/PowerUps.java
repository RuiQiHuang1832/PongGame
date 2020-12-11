package ponggame;

import java.awt.*;

public abstract class PowerUps implements CollisionHandler {

    public void collisionChecker(CollisionHandler c) {}

    public abstract void drawImage(Graphics g2g);

    protected abstract boolean hasCollided();

}




