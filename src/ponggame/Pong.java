package ponggame;
//@author : Rui Qi Huang 12/10/2020
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Pong extends JPanel implements Runnable {

    private Launcher lf;
    private BufferedImage world;
    private static boolean gameEnd = false;
    private Paddle userPaddle1;
    private Paddle userPaddle2;
    private BufferedImage ballimg;
    private BallSpawn ballSpawn;
    private BallSpawn ballSpawn2;
    private BufferedImage player1wins;
    private BufferedImage player2wins;
    private SpeedBoost speedBoost;
    private SubtractPlayerScorePowerUp MinusOne;
    private duplicateBall duplicateBall;
    private BufferedImage scaledImage = null;
    static boolean secondBall = false;

    public Pong(Launcher lf) {
        this.lf = lf;
    }


    @Override
    public void run() {
        try {
            while (!gameEnd) {

                this.repaint();
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {
            System.out.print(ignored);
        }

    }

    public void gameInitialize() {

        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage paddleimg1 = null;

        try {
            paddleimg1 = ImageIO.read(getClass().getResource("/resources/paddle.png"));
            ballimg = ImageIO.read(getClass().getResource("/resources/ball.png"));
            scaledImage = Scalr.resize(ballimg, 23);
            player1wins = ImageIO.read(getClass().getResource("/resources/player-1-wins.png"));
            player2wins = ImageIO.read(getClass().getResource("/resources/player-2-wins.png"));
            SubtractPlayerScorePowerUp.setImg(Scalr.resize((ImageIO.read(getClass().getResource("/resources/whitesquare.png"))), 50));
            SpeedBoost.setImg(ImageIO.read(getClass().getResource("/resources/speedy.png")));
            ponggame.duplicateBall.setImg(ImageIO.read(getClass().getResource("/resources/shrink_1_50x50.png")));

            MinusOne = new SubtractPlayerScorePowerUp(-100, 2000);
            MinusOne.initSubtractOne();

            speedBoost = new SpeedBoost(-100, -2000);
            speedBoost.initSpeedBoost();

            duplicateBall = new duplicateBall(-100, -2200);
            duplicateBall.initSizeChange();

        } catch (IOException ex) {
            System.out.println(ex);
        }
        assert paddleimg1 != null;
        assert scaledImage != null;

        userPaddle1 = new Paddle(10, 380, 0, 0, 90, paddleimg1);
        userPaddle2 = new Paddle(1460, 380, 0, 0, 90, paddleimg1);

        ballSpawn = new BallSpawn(lf, 750, (int) (getRandom(1, 7)), ballimg);
        ballSpawn2 = new BallSpawn(lf, 750, (int) (getRandom(1, 7)), scaledImage);


        PaddleControl pc1 = new PaddleControl(userPaddle1, KeyEvent.VK_W, KeyEvent.VK_S);
        PaddleControl pc2 = new PaddleControl(userPaddle2, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
        this.lf.getJf().addKeyListener(pc1);
        this.lf.getJf().addKeyListener(pc2);


    }

    public double getRandom(int max, int min) {
        return (Math.random() * (max - min) + min) * 100;
    }


    static int i;

    public void returnFive() {
        i = 5;
    }


    public void increaseBallSpeed() {
        ballSpawn.passIn(ballSpawn.getVx() * 2);
    }


    public boolean checkValue() {
        if (i == 5) {
            i = 4;
            return true;
        } else {
            return false;
        }
    }

    private void collisionChecker() {
        if (getPaddle().intersects(getBall())) {
            ballSpawn.passIn(-ballSpawn.getVx());
            ballSpawn.passInY(ballSpawn.getVy());

        }
        if (getPaddle2().intersects(getBall())) {
            ballSpawn.passIn(-ballSpawn.getVx());
            ballSpawn.passInY(ballSpawn.getVy());


        }
    }


    private void collisionChecker2() {
        if (getPaddle().intersects(getBall2())) {
            ballSpawn2.passIn(-ballSpawn2.getVx());
            ballSpawn2.passInY(ballSpawn2.getVy());

        }
        if (getPaddle2().intersects(getBall2())) {
            ballSpawn2.passIn(-ballSpawn2.getVx());
            ballSpawn2.passInY(ballSpawn2.getVy());


        }
    }

    private Rectangle getBall() {

        return new Rectangle(ballSpawn.getBallRect());
    }

    private Rectangle getBall2() {

        return new Rectangle(ballSpawn2.getBallRect());
    }

    private Rectangle getPaddle() {

        return (userPaddle1.getRectangle());

    }

    private Rectangle getPaddle2() {
        return userPaddle2.getRectangle();
    }

    private void checkBallHitsSpeedBoost() {
        for (int i = 0; i < speedBoost.powerUps.size(); i++) {
            speedBoost.powerUps.get(i).collisionChecker(ballSpawn);
        }
    }

    private void checkBallHitsDuplicateBall() {
        for (int i = 0; i < duplicateBall.multiply.size(); i++) {
            duplicateBall.multiply.get(i).collisionChecker(ballSpawn);
        }
    }

    private void checkBallHitsSubtractOne() {
        for (int i = 0; i < MinusOne.subtractOne.size(); i++) {
            MinusOne.subtractOne.get(i).collisionChecker(ballSpawn);
        }
    }
    private void increaseSpeed() {
        if (checkValue()) {
            increaseBallSpeed();
        }
    }

    private void update() {
        speedBoost.updateCollision();
        MinusOne.updateCollision();
        duplicateBall.updateCollision();
        increaseSpeed();
        collisionChecker();
        checkBallHitsSpeedBoost();
        checkBallHitsDuplicateBall();
        checkBallHitsSubtractOne();
        userPaddle1.update();
        userPaddle2.update();
        ballSpawn.move();
        moveSecondBall();

    }
    private void moveSecondBall() {
        if (checkCondition()) {
            collisionChecker2();
            ballSpawn2.move();

        }
    }

    public boolean checkCondition() {
        return secondBall;
    }

    public void setSecondBallTrue() {
        secondBall = true;
    }

    public void setSecondBallFalse() {
        secondBall = false;
    }

    //called internally when objects move "when it needs to be called"
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        update();
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);

        speedBoost.drawImage(buffer);
        duplicateBall.drawImage(buffer);
        this.userPaddle1.drawImage(buffer);
        this.userPaddle2.drawImage(buffer);
        ballSpawn.drawImage(buffer);
        ballSpawn2.drawImage(buffer);
        MinusOne.drawImage(buffer);

        g2.drawImage(world, 0, 0, null);

        g2.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g2.setColor(Color.RED);
        g2.drawString("Player One : " + totalScorePlayerOne() + "/10", 10, 100);
        g2.drawString("Player Two : " + totalScorePlayerTwo() + "/10", 1220, 100);


        g2.setColor(Color.WHITE);
        g2.fillRect(GameConstants.GAME_SCREEN_WIDTH / 2, 0, 40, 1000);


        if (totalScorePlayerOne() == 10) {
            g2.drawImage(player1wins, 0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT, null);
            gameEnd = true;
        }
        if (totalScorePlayerTwo() == 10) {
            g2.drawImage(player2wins, 0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT, null);

            gameEnd = true;
        }


    }

    public int totalScorePlayerOne() {
        if (BallSpawn.scorePlayer1 < 0) {
            BallSpawn.scorePlayer1 = 0;
        }

        return (BallSpawn.scorePlayer1);
    }

    public int totalScorePlayerTwo() {
        if (BallSpawn.scorePlayer2 < 0) {
            BallSpawn.scorePlayer2 = 0;
        }
        return (BallSpawn.scorePlayer2);
    }
}
