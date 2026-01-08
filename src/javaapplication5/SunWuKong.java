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


public class SunWuKong extends Character {
    private Staff staff;
    private PImage idleImg, moveImg, attackImg, jumpImg,idleImgLeft,moveImgLeft,attackImgLeft,jumpImgLeft;
    public boolean facingLeft = false;
    public enum actionState{
        IDLE,
        JUMP,
        MOVE,
        ATTACK
    }
    public actionState state = actionState.IDLE;

    private float velY = 0;
    private final float GRAVITY = 0.8f;
    private final float JUMP_FORCE = -15;
    private boolean touchingGround = true;

    private final int GROUND_Y = 380; 

    public SunWuKong(PApplet p, int x, int y) {
        super(p, x, y, 20.0, 100, "images/idle.png");

        idleImg   = app.loadImage("images/idle.png");
        idleImgLeft = flipImage(idleImg);
        moveImg   = app.loadImage("images/moving.png");
        moveImgLeft = flipImage(moveImg);
        attackImg = app.loadImage("images/fiery.png");
        attackImgLeft = flipImage(attackImg);
        jumpImg   = app.loadImage("images/jump.png");
        jumpImgLeft = flipImage(jumpImg);
        

        image = idleImg;
    }

    @Override
    public void move(int dx, int dy) {

        if (dx < 0) facingLeft = true;
        if (dx > 0) facingLeft = false;

        if (dx != 0 && touchingGround && state != actionState.ATTACK) {
            state = actionState.MOVE;
        } 
        else if (dx == 0 && touchingGround && state != actionState.ATTACK) {
            state = actionState.IDLE;
        }

        x += dx;
        updateImage();
    }


    public void attack() {
        if (state != actionState.ATTACK) {
            state = actionState.ATTACK;

            float cx = x + image.width / 2;
            float cy = y + image.height / 2;

            staff = new Staff(app, this,cx, cy, facingLeft);
            updateImage();
        }
    }
    public void jump() {
        if (touchingGround) {
            velY = JUMP_FORCE;
            touchingGround = false;
            state = actionState.JUMP;
            updateImage();
        }
    }
    public void updatePhysics() {
        if (!touchingGround) {
            velY += GRAVITY;
            y += velY;

            if (y >= GROUND_Y) {
                y = GROUND_Y;
                velY = 0;
                touchingGround = true;
                state = actionState.IDLE;
                updateImage();
            }
        }
    }

    private void updateImage() {
        switch (state) {
            case MOVE:
                image = (!facingLeft) ? moveImgLeft:moveImg;
                break;
            case ATTACK:
                image = (!facingLeft) ? attackImgLeft:attackImg;
                break;
            case JUMP:
                image = (!facingLeft) ? jumpImgLeft:jumpImg;
                break;
            default:
                image = (!facingLeft) ? idleImgLeft:idleImg;
        }
        
    }
    
    public void update() {
        updatePhysics();

        if (staff != null) {
            staff.update();
            if (!staff.isActive()) {
                staff = null;
                state = touchingGround ? actionState.IDLE : actionState.JUMP;
                updateImage();
            }
        }
    }
    
    public void render() {
        app.image(image, x, y);

        if (staff != null) {
            staff.draw();
        }
    }

    
    PImage flipImage(PImage src) {
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



