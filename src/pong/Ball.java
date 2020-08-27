package pong;

import java.awt.*;
import java.util.Random;

public class Ball {

    public double x, y;
    public int width, height;

    public static int enemyPoints;
    public static int playerPoints;

    public double dx, dy;
    public double speed = 1.6;

    public Ball(int x, int y){
        this.x = x;
        this.y = y;
        this.width = 4;
        this.height = 4;
        this.dx = new Random().nextGaussian();
        this.dy = new Random().nextGaussian();

    }
    public void tick(){

        if(x+(dx*speed) + width >= Game.WIDTH){
            dx*=-1;
        }
        else if(x + (dx*speed) < 0) {
            dx *= -1;
        }
        if(y >= Game.HEIGHT){
            //ponto do inimigo
            enemyPoints++;
            System.out.println("Enemy points: " + enemyPoints);
            new Game();

            return;
        }
        else if(y < 0 ){
            //ponto do jogador
            playerPoints++;
            System.out.println("Player Points: " + playerPoints);
            new Game();
            return;
        }

        Rectangle bounds = new Rectangle((int)(x+(dx*speed)), (int)(y+(dy*speed)), width, height);
        Rectangle boundsPlayer = new Rectangle(Game.player.x, Game.player.y, Game.player.width, Game.player.height);
        Rectangle boundsEnemy = new Rectangle((int)Game.enemy.x, (int)Game.enemy.y, Game.enemy.width, Game.enemy.height);

        if(bounds.intersects(boundsPlayer)){
            dy*= -1;
        }
        else if(bounds.intersects(boundsEnemy)){
            dy*= -1;
        }

        x += dx * speed;
        y += dy * speed;
    }
    public void render(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect((int)x, (int)y, width, height);

        g.setFont(new Font("Arial", Font.PLAIN, 9));
        g.setColor(Color.WHITE);
        String s = "Player: " + playerPoints;
        g.drawString(s, 0, Game.HEIGHT / 2 );

        g.setFont(new Font("Arial", Font.PLAIN, 9));
        g.setColor(Color.WHITE);
        String e = "Enemy: " + enemyPoints;
        g.drawString(e, 0, Game.HEIGHT / 2 + 10 );

    }

}
