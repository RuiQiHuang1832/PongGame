package ponggame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class BallSpawn implements CollisionHandler {
    //note to self
    //dont know how to multiply without making new objects
    //dont know how to enlarge object

    //mirror by making vx, vy static variables.
    private BufferedImage img;
    private Launcher lf;
    private int x;
    private int y;
    int vx = getDirection(3);
    int vy = getDirection(3);
    Rectangle ball;
    static int scorePlayer1 = 0;
    static int scorePlayer2 = 0;
    private Pong pong = new Pong(lf);

    //change direction of ball
    static int getDirection(int i) {
        int dir = 0;
        if (Math.random() * 10 > 5)
            dir = -i;
        else {
            dir = i;
        }
        return dir;

    }

    public BallSpawn() {
    }
    public BallSpawn(Launcher lf, int x, int y, BufferedImage img) {

        this.lf = lf;
        this.x = x;
        this.y = y;
        this.img = img;
        ball = new Rectangle(this.x, this.y, img.getWidth(), img.getHeight());
    }


    public void passIn(int val) {
        vx = val;

    }

    public void passInY(int val) {
        vy = val;

    }

    public void resetBall() {
        ball.x = x;
        ball.y = y;
        vx = getDirection(3);
        vy = getDirection(3);
        pong.setSecondBallFalse();

    }

    public void move() {
        ball.x += vx;
        ball.y += vy;
        checkBorder();

    }

    public int getVx() {
        return vx = (int) (vx * 1.4);
    }

    public int getVy() {
        return vy = (int) (vy * 1.2);
    }

    public void playScore() {
        scorePlayer1--;
        scorePlayer2--;
    }

    private void checkBorder() {
        //left side
        if (ball.x < -20) {
            scorePlayer2++;
            resetBall();
        }
        //right side
        if (ball.x >= GameConstants.GAME_SCREEN_WIDTH - 40) {
            scorePlayer1++;
            resetBall();
        }
        //top
        if (ball.y < 0) {
            vy = -vy;
        }
        //bot
        if (ball.y >= 730) {
            vy = -vy;
        }
    }

    public Rectangle getBallRect() {
        return new Rectangle(ball);
    }


    public void drawImage(Graphics g) {
        AffineTransform direction = AffineTransform.getTranslateInstance(ball.x, ball.y);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, direction, null);


    }

    @Override
    public void collisionChecker(CollisionHandler c) {
        if (this.getRectangle().intersects(c.getRectangle())) {
            System.out.println();
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, img.getWidth(), img.getHeight());
    }
}
