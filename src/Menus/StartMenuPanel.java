package Menus;

import ponggame.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class StartMenuPanel extends JPanel {

        private BufferedImage menuBackground;
        private JButton start;
        private JButton exit;
        private Launcher lf;

        public StartMenuPanel(Launcher lf) {
            this.lf = lf;
            try {
                menuBackground = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResource("resources/start.png")));
            } catch (IOException e) {
                System.out.println("Error cant read menu background");
                e.printStackTrace();
                System.exit(-3);
            }
            this.setBackground(Color.BLACK);
            this.setLayout(null);
            start = new JButton("Start");
            start.setForeground(Color.RED);
            start.setFont(new Font("Times Roman", Font.BOLD ,24));
            start.setBounds(300,330,150,50);
            start.addActionListener((actionEvent -> {
                this.lf.setFrame("game");
            }));


            exit = new JButton("Exit");
            exit.setForeground(Color.BLUE);
            exit.setSize(new Dimension(200,100));
            exit.setFont(new Font("Times Roman", Font.BOLD ,24));
            exit.setBounds(520,330,150,50);
            exit.addActionListener((actionEvent -> {
                this.lf.closeGame();
            }));


            this.add(start);
            this.add(exit);

        }

        @Override
        public void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(this.menuBackground,0,0,null);
        }
    }