package pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Game extends Canvas implements Runnable, KeyListener {

    public static int WIDTH = 160;
    public static int HEIGHT = 120;
    public static int SCALE = 3;

    public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    public static Player player;
    public static Enemy enemy;
    public static Ball ball;

    public Game(){
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        this.addKeyListener(this);
        player = new Player(100, HEIGHT - 5);
        enemy = new Enemy(100, 0);
        ball = new Ball(100, HEIGHT/2 - 1);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong");
        Game game = new Game();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        new Thread(game).start();

    }

    public void tick(){

        player.tick();
        enemy.tick();
        ball.tick();

    }

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = layer.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH,HEIGHT);

        player.render(g);
        enemy.render(g);
        ball.render(g);

        g = bs.getDrawGraphics();
        g.drawImage(layer, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        bs.show(); // mostra na tela
    }



    @Override
    public void run() {
        requestFocus(); // foca a tela
        while(true){
            tick();
            render();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            player.right = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            player.left = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_R){
            new Game();
            Ball.enemyPoints = 0;
            Ball.playerPoints = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            player.right = false;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            player.left = false;
        }
    }
}
