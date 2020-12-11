package ponggame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SubtractPlayerScorePowerUp extends PowerUps implements CollisionHandler {
    private int x, y;
    private static BufferedImage img;
    private Pong pong = new Pong(null);
    ArrayList<PowerUps> subtractOne = new ArrayList<>();
    private boolean collided = false;
    BallSpawn ballspawn = new BallSpawn();

    public void collisionChecker(CollisionHandler c) {
        if (this.getRectangle().intersects(c.getBallRect())) {
            collided = true;
        }
    }

    public void updateCollision() {
        for (int i = 0; i < subtractOne.size(); i++) {
            if (subtractOne.get(i).hasCollided()) {
                subtractOne.remove(i);
                ballspawn.playScore();
            }
        }

    }

    public SubtractPlayerScorePowerUp(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static void setImg(BufferedImage img) {
        SubtractPlayerScorePowerUp.img = img;
    }

    public void initSubtractOne() {
        for (int i = 0; i <= 10; i++) {
            subtractOne.add(new SubtractPlayerScorePowerUp(BallSpawn.getDirection((int) pong.getRandom(1, 14)),
                    BallSpawn.getDirection((int) pong.getRandom(1, 7))));
        }
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2g = (Graphics2D) g;
        for (int i = 0; i < subtractOne.size(); i++) {
            subtractOne.get(i).drawImage(g2g);
        }
        g.drawImage(img, x, y, null);

    }

    @Override
    protected boolean hasCollided() {
        return collided;
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(x, y, img.getWidth(), img.getHeight());
    }

    @Override
    public Rectangle getBallRect() {
        return null;
    }
}
