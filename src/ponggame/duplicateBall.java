package ponggame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class duplicateBall extends PowerUps implements CollisionHandler {

    private int x, y;
    public boolean collided = false;
    private Pong pong = new Pong(null);
    private static BufferedImage img;
    ArrayList<PowerUps> multiply = new ArrayList<>();
    Rectangle r;

    public static void setImg(BufferedImage img) {
        duplicateBall.img = img;
    }

    public duplicateBall(int x, int y) {
        this.x = x;
        this.y = y;
        this.r = new Rectangle(x, y, img.getWidth(), img.getHeight());
    }

    public void initSizeChange() {
        for (int i = 0; i <= 15; i++) {
            multiply.add(new duplicateBall(BallSpawn.getDirection((int) pong.getRandom(1, 14)),
                    BallSpawn.getDirection((int) pong.getRandom(1, 7))));
        }

    }

    public void collisionChecker(CollisionHandler c) {
        if (this.getRectangle().intersects(c.getBallRect())) {
            collided = true;
        }
    }

    public void updateCollision() {
        for (int i = 0; i < multiply.size(); i++) {
            if (multiply.get(i).hasCollided()) {
                multiply.remove(i);
                pong.setSecondBallTrue();
            }
        }

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

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2g = (Graphics2D) g;
        for (int i = 0; i < multiply.size(); i++) {
            multiply.get(i).drawImage(g2g);
        }
        g.drawImage(img, x, y, null);

    }
}
