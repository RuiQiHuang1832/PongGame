package ponggame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Paddle {
    private int x;
    private int y;
    private int vx;
    private int vy;
    private BufferedImage img;
    private boolean upPressed;
    private boolean downPressed;
    private final int R = 8;
    private float angle;
    Rectangle paddle;


    Paddle(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        paddle = new Rectangle(this.x, this.y, img.getWidth(), img.getHeight());
    }

    void toggleUpPressed() {
        this.upPressed = true;
    }

    void toggleDownPressed() {
        this.downPressed = true;
    }

    void unToggleUpPressed() {
        this.upPressed = false;
    }

    void unToggleDownPressed() {
        this.downPressed = false;
    }


    void update() {
        if (this.upPressed) {
            this.moveUp();
        }
        if (this.downPressed) {
            this.moveDown();
        }


    }

    private void moveUp() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        paddle.x -= vx;
        paddle.y -= vy;
        checkBorder();
    }

    private void moveDown() {
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        paddle.x += vx;
        paddle.y += vy;
        checkBorder();
    }


    private void checkBorder() {
        if (paddle.x < -300) {
            paddle.x = -300;
        }
        if (paddle.x >= GameConstants.GAME_SCREEN_WIDTH) {
            paddle.x = GameConstants.GAME_SCREEN_WIDTH - 80;
        }
        //top
        if (paddle.y < 5) {
            paddle.y = 5;
        }
        //bot
        if (paddle.y >= GameConstants.WORLD_SCREEN_HEIGHT - 350) {
            paddle.y = GameConstants.WORLD_SCREEN_HEIGHT - 350;
        }
    }


    public Rectangle getRectangle() {
        return new Rectangle(paddle);
    }


    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(paddle.x, paddle.y);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

    }


}
