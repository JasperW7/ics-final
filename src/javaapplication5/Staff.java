/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication5;

/**
 *Staff weapon used by player
 * @author jaspe
 */
import processing.core.*;

public class Staff {
    //PApplet instance
    private PApplet app;
    //coordinates/position
    private float x, y;
    private SunWuKong owner; //SunWuKong character
    private float startX; //initial position when thrown
    private float angle = 0; //rotation of staff

    private final float SPEED = 5; //speed the staff moves at
    private final float MAX_DIST = 200; //distance before returning to owner
    private boolean goingOut = true; //direction swap
    private boolean active = true;
    private PImage img; //staff image
    private int dir; //direction
    /**
     * Constructor for Staff
     * @param app PApplet instance
     * @param owner SunWuKong character that threw the staff
     * @param x x coordinate
     * @param y y coordinate
     * @param facingLeft starting direction
     */
    public Staff(PApplet app, SunWuKong owner, float x, float y, boolean facingLeft) {
        this.app = app;
        this.owner = owner;
        this.x = x;
        this.startX=x;
        this.y = y;
        this.dir = facingLeft ? -1 : 1; //ternary direction check
        img = app.loadImage("images/staff.png");
    }
    /**
     * Updates staff position & rotation per frame
     * Includes ending sequence when reached back to the player
     */
    public void update() {
        if (!active) return;

        angle += 0.2; //increae rotation

        if (goingOut) {
            x += SPEED * dir;
            if (Math.abs(x - startX) >= MAX_DIST) {
                goingOut = false;
            }
        } else {
            x -= SPEED * dir;
            if ((dir == 1 && x <= owner.x+owner.image.width) || (dir == -1 && x >= owner.x)) {
                active = false;
            }
        }

    }
    /**
     * Draw staff to PApplet
     * @author https://stackoverflow.com/questions/43209886/using-pushmatrix-popmatrix-translate-and-rotate, https://www.youtube.com/watch?v=hsAiIytWf-I&t=765s
     */
    public void draw() {
        if (!active) return;

        app.pushMatrix();
        app.pushStyle();
  
        app.translate(x, y);
        app.rotate(angle);
        app.imageMode(PConstants.CENTER);
        app.image(img, 0, 0);

        app.popStyle();
        app.popMatrix();
    }

    /**
     * 
     * @return whether staff is active
     */
    public boolean isActive() {
            return active;
    }
    /**
     * 
     * @return x coordinate
     */
    public float getX() {
        return x;
    }
/**
 * 
 * @return y coordinate
 */
    public float getY() {
        return y;
    }
    /**
     * 
     * @return approximate radius for collision detection
     */
    public float getRadius() {
        return img.width*0.4f;
    }
    
    

}

