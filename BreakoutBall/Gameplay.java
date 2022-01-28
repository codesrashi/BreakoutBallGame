package BreakoutBall;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
    
    private int score=0;
    private int totalBricks=21;
    private Timer timer;
    private boolean play = false;
    private int delay=4;
    Random r=new Random();
    
    int n=r.nextInt(600);
    int x=r.nextInt(600);
    private int playerX=310;
    private int ballposX=n;
    private int ballposY=350;
    Random ran=new Random();
    
    //int x=ran.nextInt(2);
     //private int ballXdir = n;
   private int ballXdir=-1;

    private int ballYdir=-2;

    private MapGenerator map;
    public Gameplay(){
        setMap(new MapGenerator(3, 7));
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
         timer=new Timer(delay, this);
         timer.start();

    }
    public int getTotalBricks() {
        return totalBricks;
    }
    public void setTotalBricks(int totalBricks) {
        this.totalBricks = totalBricks;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public MapGenerator getMap() {
        return map;
    }
    public void setMap(MapGenerator map) {
        this.map = map;
    }
    public boolean isPlay() {
        return play;
    }
    public void setPlay(boolean play) {
        this.play = play;
    }
    public void paint(Graphics g){
        //background;
        super.paintComponent(g);
        ImageIcon img = new ImageIcon("Capture3.jpg");
        g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
       // g.setColor(Color.black);
       // g.fillRect(1, 1, 692, 592);

        //drawing map
        map.draw((Graphics2D)g);

        //borders
        g.setColor(Color.pink);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(692, 0, 3, 592);

        //scores
        g.setColor(Color.yellow);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString(" "+score, 590, 65);

        //the paddle
        g.setColor(Color.yellow);
        g.fillRect(playerX, 550, 100,  30);

        //the ball
        g.setColor(Color.cyan);
        g.fillOval(ballposX, ballposY, 20, 20);

        if(totalBricks==0){
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString(" You Won", 190, 310);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString(" Press Enter to Restart", 230, 350);
        }
        

        if(ballposY>570){
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString(" Game Over, Score: "+score, 190, 300);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString(" Press Enter to Restart", 230, 350);
        }
        g.dispose();

    }
    public void actionPerformed(ActionEvent e){

        timer.start();
        if(play){
            if(new Rectangle(ballposX, ballposY, 20,20).intersects(new Rectangle(playerX,550,100,30))){
               ballYdir=-ballYdir;
            }
            A: for(int i=0;i<map.map.length;i++){
                for(int j=0;j<map.map[0].length;j++){
                    if(map.map[i][j]>0){
                        int brickX = j*map.brickwidth+80;
                        int brickY=i*map.brickheight+80;
                        int brickwidth=map.brickwidth;
                        int brickheight= map.brickheight;
                        Rectangle rect= new Rectangle(brickX,brickY,brickwidth,brickheight);
                        Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect=rect;
                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0, i, j);
                            setTotalBricks(getTotalBricks() - 1);
                            setScore(getScore() + 5);
                            if(ballposX+19<=brickRect.x|| ballposX+1>=brickRect.x+brickRect.width){
                                ballXdir=-ballXdir;

                            }else{
                                ballYdir= -ballYdir;
                            }
                            break A;
                        }
                    } 
                }
            }
            ballposX+=ballXdir;
            ballposY+=ballYdir;
            if(ballposX<0){
                ballXdir= -ballXdir;

            }
            if(ballposY<0){
                ballYdir= -ballYdir;
                
            }
            if(ballposX>670){
                ballXdir= -ballXdir; 
                
            }
        }
        repaint();

    }
    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){

            if(playerX>=600){
                playerX=600;
            }
            else{
                moveRight();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if(playerX<10){
                playerX=10;
            }
            else{
                moveLeft();
            }
        }
        if(e.getKeyCode()== KeyEvent.VK_ENTER){
            if(!play){
                play=true;
                
                ballposX=x;
                ballposY=350;
                ballXdir=-1;
                ballYdir=-2;
                score=0;
                totalBricks=21;
                map=new MapGenerator(3, 7);
                repaint();

            }
        }

    }
    public void moveRight(){
        play = true;
       playerX+=20;
    }
    public void moveLeft(){
        play = true;
       playerX-=20;
    }
    public void keyReleased(KeyEvent e){}

}
