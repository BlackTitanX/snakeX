
package com.mycompany.snake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


/**
 *
 * @author israelperez
 */
public final class GamePanel extends JPanel implements ActionListener{
    
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    int bodyParts = 6;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (int)(SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 100;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    
    // Sets the screen values
    GamePanel(){
     random = new Random();
     this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
     this.setBackground(Color.black);
     this.setFocusable(true);
     this.addKeyListener(new MyKeyAdapter());
     startGame();
        System.out.println("cordinates " + x[0] + " " 
        + y[0]);
    }
    
    // starts the game and set initial apple coordinates
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        
    }
  
  
    // gives the ability to create graphics 
    @Override
    public void paintComponent(Graphics g){
    super.paintComponent(g);
    draw(g);
    }
    
     public void draw(Graphics g){
       if(running){
       //Draws the apple
       g.setColor(Color.red);
       g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
       
       
       // draws snakebody
       
       for(int i =0; i < bodyParts; i++){
         if(i == 0){
           g.setColor(Color.green);
           g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
         }else{
         
         g.setColor(Color.LIGHT_GRAY);
         g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
         }
       
       
       
       }
       
        // shows text score
       showText(g,15, "Score");
       }else{
        
           // shows game over text and also restart text
        showText(g,15, "Score");
        gameOver(g);
        
       }
    }
     
     // creates a new apple
     public void newApple(){
       appleX = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE) ) * UNIT_SIZE;
       appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE) ) * UNIT_SIZE;
     }
     // Shows the Score
     public void showText(Graphics g, int position, String message){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(message + " " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score : " + applesEaten))/2, g.getFont().getSize() /2 + position);
     }
     // moves the snake by assigning the previous value to the next body part of the snake, starting from the head
    public void move(){
       for(int i = bodyParts; i>0; i-- ){
         x[i] = x[i -1];
         y[i] = y[i -1];
    
       }
       // moves the snake infinitly
       switch(direction){
         
           case 'U' -> y[0] = y[0] - UNIT_SIZE;
               
           case 'D' -> y[0] = y[0] + UNIT_SIZE;
               
           case 'R' -> x[0] = x[0] + UNIT_SIZE;
               
           case 'L' -> x[0] = x[0] - UNIT_SIZE;
           
               
               
       
       }
        
        
        
    }
    
    // Check if the apple has been eaten and then if positve then spawns another one
    public void checkApple(){
        if((appleX == x[0]) && (appleY == y[0])){
          applesEaten++;
          bodyParts++;
          newApple();
        }
    }
    
    // checks for coallitions and stops the game if positive
    public void checkCollitions(){
        //checks that the coordinates are not colliding with it self
        for(int i = bodyParts; i>0; i-- ){
           if((x[0] == x[i]) && (y[0] == y[i]) ){
         
           running = false;
           
           }
        }
        
        // Checks if the snake havent collide with the right wall
        if(x[0] > SCREEN_WIDTH){
        running = false;
        }
        
        // Checks if the snake havent collide with the left wall
        if(x[0] < 0){
        running = false;
        }
        
        // Checks if the snake havent collide with the bottom wall
         if(y[0] > SCREEN_HEIGHT){
        running = false;
        }
        
         // Checks if the snake havent collide with the upper wall
        if(y[0] < 0){
        running = false;
        }
        
        
    }
    
    // Displays the game over screen if you lose
    
    public void gameOver(Graphics g){
        
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT /2);
    }
   
    
    @Override 
    public void actionPerformed(ActionEvent e){
        if(running){
        move();
        checkApple();
        checkCollitions();
        }
        repaint();
    }
    
    // restarts the game if you press R
    public void restartGame(){
         x[0] = 0;
         y[0] = 0;
         direction = 'R';
         bodyParts = 6;
         newApple();
         running = true;
         applesEaten =0;
       }
    
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
            
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                      direction = 'L';
                    }
                break;
                
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                      direction = 'R';
                    }
                    break;
                    
                case KeyEvent.VK_UP:
                     if(direction != 'D'){
                        direction = 'U';
                      }
                     break;
                     
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    
                    break;
                    
                case KeyEvent.VK_R:
                    if(running == false){
                    restartGame();
                    }
                    break;
            }
        }
    }
    
}
