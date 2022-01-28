package BreakoutBall;
import javax.swing.*;


public class Main {
    public static void main(String [] args) {
       JFrame obj= new JFrame("Breakout Ball Game");
       Gameplay g=new Gameplay();
       obj.setBounds(20, 20, 710, 600);
       obj.setResizable(false);
       obj.setVisible(true);
       obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       obj.add(g);
    }
    
}
