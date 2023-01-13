package com.mycompany.snake;

import javax.swing.JFrame;


/**
 *
 * @author israelperez
 */
public class Gameframe extends JFrame {
    
      Gameframe(){
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

   
    
    
    
    
}
