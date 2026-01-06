/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication5;
import processing.core.*;
/**
 *
 * @author jaspe
 */
public class Character {
    private PImage image;
    private int health;
    private int x,y;
    private double atk;
    private PApplet app;
    
    public Character(PApplet p, int x, int y, double atk, int health, String imagePath){
        this.app=p;
        this.x = x; this.y=y;
        this.atk=atk;
        this.health=health;
        this.image = app.loadImage(imagePath);
        
    }
    
    public void move(int dx, int dy){
        x += dx;
        y += dy;
    }
    
    public void draw(){
        if (health>0){
            app.image(image,x,y);
        }
    }
    
    public void takeDamage(double dmg) {
    if (health > 0 && dmg > 0) {
        health -= dmg;
    }
}

    
    public double getAtk(){
        return atk;
    }
    
    public int getHealth() {
        return health;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
