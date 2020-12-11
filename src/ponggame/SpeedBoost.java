package ponggame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpeedBoost extends PowerUps implements CollisionHandler {

    private static BufferedImage img;
    private int x, y;
    private Rectangle r;
    public boolean collided = false;
    public ArrayList<PowerUps> powerUps = new ArrayList<>();
    private Pong pong = new Pong(null);


    public boolean hasCollided() {
        return collided;
    }

    public void initSpeedBoost() {

        for (int i = 0; i <= 10; i++) {
            powerUps.add(new SpeedBoost(BallSpawn.getDirection((int) pong.getRandom(1, 14)),
                    BallSpawn.getDirection((int) pong.getRandom(1, 7))));
        }

    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(x, y, img.getWidth(), img.getHeight());
    }

    @Override
    public Rectangle getBallRect() {
        return null;
    }


    public static void setImg(BufferedImage img) {
        SpeedBoost.img = img;
    }

    public SpeedBoost(int x, int y) {

        this.x = x;
        this.y = y;
        this.r = new Rectangle(x, y, img.getWidth(), img.getHeight());

    }

    public void collisionChecker(CollisionHandler c) {
        if (this.getRectangle().intersects(c.getBallRect())) {
            collided = true;
        }
    }


    public void updateCollision() {
        for (int i = 0; i < powerUps.size(); i++) {
            if (powerUps.get(i).hasCollided()) {
                powerUps.remove(i);
                pong.returnFive();
            }

        }

    }


    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2g = (Graphics2D) g;

        for (int i = 0; i < powerUps.size(); i++) {
            powerUps.get(i).drawImage(g2g);
        }
        g.drawImage(img, x, y, null);

    }


}
