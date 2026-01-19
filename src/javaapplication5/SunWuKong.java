/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication5;
import processing.core.*;
/**
 *Playable player class. Includes movement, attacking and health.
 * @author jaspe
 */


public class SunWuKong extends Character {
    //Weapon
    protected Staff staff;
    //Images representing different actions
    private PImage idleImg, moveImg, attackImg, jumpImg,idleImgLeft,moveImgLeft,attackImgLeft,jumpImgLeft;
    //Flip image depending on direction
    public boolean facingLeft = false;
    //State to track current action
    public enum actionState{
        IDLE,
        JUMP,
        MOVE,
        ATTACK
    }
    //Set default to idle
    public actionState state = actionState.IDLE;
    //Jump physics
    private float velY = 0; //velocity
    private final float GRAVITY = 0.8f; //constant accelearation
    private final float JUMP_FORCE = -15; //initial vel on jump
    protected boolean touchingGround = false; //determine if jump is available
    private final int GROUND_Y = 375; //constant for ground level 
    //Damage mechanics
    private int damageCooldown = 0; //cooldown for damage ticks
    private final int DAMAGE_DELAY = 30; //frames for cooldown
    protected int numOfAttacks=0; //attack tracker

    
    /**
     * Constructor method for SunWuKong character
     * @param p PApplet instance
     * @param x x coordinate
     * @param y y coordinate
     */
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
    /**
     * Moves character around.Updates character action/image depending on direction and motion.
     * @param dx horizontal movement amount
     * @param dy vertical movement amount
     */
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
    /**
     * 
     * @return Returns staff
     */
    public Staff getStaff(){
        return staff;
    }
    /**
     * Initiates attack
     */
    public void attack() {
        if (state != actionState.ATTACK) {
            numOfAttacks++;
            state = actionState.ATTACK;

            float cx = x + image.width / 2;
            float cy = y + image.height / 2;

            staff = new Staff(app, this,cx, cy, facingLeft);
            updateImage();
        }
    }
    /**
     * Initiates jumping
     */
    public void jump() {
        if (touchingGround) {
            velY = JUMP_FORCE;
            touchingGround = false;
            state = actionState.JUMP;
            updateImage();
        }
    }
    /**
     * Updates gravity & landing
     */
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
    /**
     * Updates image based off of action
    */
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
    /**
     * Calls all other update functions
     */
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
    /**
     * Draws player, health bar, and staff
     */
    public void render() {
        app.image(image, x, y);
        app.fill(255, 0, 0);
        app.rect(20, 20, 200, 15);

        app.fill(0, 255, 0);
        app.rect(20, 20, 200 * ((float)getHealth() / (float)startHealth), 15);

        app.fill(0);
        app.text("HP: " + getHealth(), 50, 55);

        if (staff != null) {
            staff.draw();
        }
    }

}