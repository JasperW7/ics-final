/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication5;

/**
 *
 * @author jaspe
 */

import processing.core.PApplet;
import processing.core.PImage;
import java.util.Map;
import java.util.HashMap;
public class Sketch extends PApplet {
    PImage background;
    SunWuKong player;
    int WINDOW_WIDTH=500, WINDOW_LENGTH = 800;
    
    public void settings(){
	   //sets the size of the window
        size (WINDOW_LENGTH,WINDOW_WIDTH);
    }
    public void setup(){
	   //sets the background colour using R,G,B (https://rgbcolorpicker.com/)
        background = loadImage("images/background.jpg");
        textSize(20);
        player = new SunWuKong(this,100,380);
    }
    public void drawCollisions(){
        
    }
    public void mousePressed(){

    }
    
    public void draw(){//set the colour
        image(background,0,0);
        int dx = 0;
        System.out.println(pressedKeys);
        if (keyPressed) {
            if (key == 'a' && player.x>0) {
                dx = -5;
            }
            if (key == 'd' && player.x<WINDOW_LENGTH-player.image.width) {
                dx = 5;
            }   
            if (key=='w') player.jump();
            if (key=='q') player.attack();
            
        }
        System.out.println(player.state);
        player.move(dx,0);
        player.update();
        player.render();
            
    }
    
    
}
