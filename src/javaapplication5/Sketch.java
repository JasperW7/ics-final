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

public class Sketch extends PApplet {
    int WINDOW_WIDTH=400, WINDOW_LENGTH = 400;
    public void settings(){
	   //sets the size of the window
        size (WINDOW_LENGTH,WINDOW_WIDTH);
    }
    public void setup(){
	   //sets the background colour using R,G,B (https://rgbcolorpicker.com/)
        background(255);
        textSize(20);
    }
    public void drawCollisions(){
        
    }
    public void mousePressed(){

    }
    public void draw(){//set the colour
        background(255);
        drawCollisions();
            
        
    }
}
