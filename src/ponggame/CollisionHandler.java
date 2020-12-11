package ponggame;

import java.awt.*;

public interface CollisionHandler {
    void collisionChecker(CollisionHandler c);
    Rectangle getRectangle() ;
    Rectangle getBallRect();
}
