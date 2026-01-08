/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication5;

/**
 *
 * @author jaspe
 */
import processing.core.*;

public class Staff {
    private PApplet app;

    private float x, y;
    private SunWuKong owner;
    private float startX;
    private float angle = 0;

    private final float SPEED = 5;
    private final float MAX_DIST = 200;
    private boolean goingOut = true;
    private boolean active = true;
    private PImage img;
    private int dir; 

    public Staff(PApplet app, SunWuKong owner, float x, float y, boolean facingLeft) {
        this.app = app;
        this.owner = owner;
        this.x = x;
        this.startX=x;
        this.y = y;
        this.dir = facingLeft ? -1 : 1;
        img = app.loadImage("images/staff.png");
    }

    public void update() {
        if (!active) return;

        angle += 0.2; 

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


    public boolean isActive() {
        return active;
    }
}

