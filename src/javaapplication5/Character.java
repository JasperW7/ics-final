/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication5;
import processing.core.*;
/**
 *Generic character parent class
 * @author jaspe
 */
public class Character {
    //variables
    protected PImage image;
    protected int health,startHealth;
    protected int x,y;
    protected double atk;
    protected PApplet app;
    /**
     * Constructor method for Characters
     * @param p PApplet instance
     * @param x x coordinate
     * @param y y coordinate
     * @param atk attack dmg
     * @param health HP
     * @param imagePath String for image directory
     */
    public Character(PApplet p, int x, int y, double atk, int health, String imagePath){
        this.app=p;
        this.x = x; this.y=y;
        this.atk=atk;
        this.health=health;
        this.image = app.loadImage(imagePath);
        startHealth=health;
        
    }
    /**
     * Moves character around.Applies minimum and maximum bounds.
     * @param dx horizontal movement amount
     * @param dy vertical movement amount
     */
    public void move(int dx, int dy){
        x += dx;
        y += dy;
        x= Math.max(0,Math.min(x,app.width-image.width));
        y = Math.max(0,Math.min(y,app.height-image.height));
    }
    /**
     * Draws character if character health is above 0
     */
    public void draw(){
        if (health>0){
            app.image(image,x,y);
        }
    }
    /**
     * Reduces HP by an amount
     * @param dmg amount of dmg taken
     */
    public void takeDamage(double dmg) {
        if (health > 0 && dmg > 0) {
            health -= dmg;
            if (health<0) health=0;
        }
    }
    /**
     * Alive check
     * @return if character is still alive
     */
    public boolean isAlive(){
        return health>0;
    }
    /**
     * Returns attack value
     * @return attack
     */
    public double getAtk(){
        return atk;
    }
    /**
     * Returns remaining health
     * @return 
     */
    public int getHealth() {
        return health;
    }
    /**
     * 
     * @return x coordinate
     */
    public int getX() {
        return x;
    }
    /**
     * 
     * @return y coordinate
     */
    public int getY() {
        return y;
    }
    /**
     * set x coordinate
     * @param x new x coord
     */
    public void setX(int x){
        this.x=x;
    }
    /**
     * set y coordinate
     * @param y new y coord
     */
    public void setY(int y){
        this.y=y;
    }
    /**
     * flip image horizontally
     * @param src original image
     * @author https://stackoverflow.com/questions/29334348/processing-mirror-image-over-x-axis
     * @return flipped image
     */
    public PImage flipImage(PImage src) {
        PImage flipped = app.createImage(src.width, src.height,PGraphics.ARGB);

        src.loadPixels();
        flipped.loadPixels();

        int w = src.width;
        int h = src.height;

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                flipped.pixels[y * w + x] =
                    src.pixels[y * w + (w - 1 - x)];
            }
        }

        flipped.updatePixels();
        return flipped;
    }

}
