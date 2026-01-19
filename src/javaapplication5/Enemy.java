/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication5;
import processing.core.PApplet;
import processing.core.PImage;
/**
 *Generic enemy character
 * @author jaspe
 */



public class Enemy extends Character {
    //variables
    protected double speed; //movement speed
    protected int detectionRange; //detection threshold
    protected int attackCooldown = 60; // frames between attacks
    protected int attackTimer = 0; //counter for attack frames
    protected PImage facingLeft,facingRight; //directional images
    protected int leftBound, rightBound; //movement bounds if not chasing
    protected boolean movingRight = true; //direction
    /**
     * Constructor for enemy
     * @param app PApplet instance
     * @param x x coordinate
     * @param y y coordinate
     * @param atk attack value
     * @param health HP
     * @param imagePath image directory path
     * @param speed movement speed
     * @param detectionRange range threshold
     * @param leftBound min bound
     * @param rightBound max bound
     */
    public Enemy(PApplet app, int x, int y, double atk, int health, String imagePath, double speed, int detectionRange, int leftBound, int rightBound){
        super(app, x, y, atk, health, imagePath);
        this.speed = speed;
        this.detectionRange = detectionRange;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.attackCooldown = 60; 
        
        facingRight =app.loadImage("images/enemy.png");
        facingLeft = flipImage(facingRight);
    }
    /**
     * 
     * @return enemy x centerpoint
     */
    public float getCenterX() {
        return x + image.width / 2f;
    }
    /**
     * 
     * @return enemy y centerpoint
     */
    public float getCenterY() {
        return y + image.height / 2f;
    }
    /**
     * 
     * @return approx circular radius of enemy image
     */
    public float getRadius() {
        return Math.min(image.width, image.height) * 0.4f;
    }



    /**
     * chasing mechanics if player is within detection threshold
     * @param player tracked player
     */
    public void chase(Character player) {
        int dx = player.getX() - x;

        if (Math.abs(dx) <= detectionRange) {
            if (dx > 0) {
                x += speed;
                movingRight = true;
            } else {
                x -= speed;
                movingRight = false;
            }
        } else {
            if (movingRight) {
                x += speed;
                if (x >= rightBound) movingRight = false;
            } else {
                x -= speed;
                if (x <= leftBound) movingRight = true;
            }
        }
    }
    /**
     * Update attack cooldown counter
     */
    public void updateCooldown() {
        if (attackTimer > 0) attackTimer--;
    }
    /**
     * Check if able to attack
     * @return 
     */
    public boolean canAttack() {
        return attackTimer == 0;
    }
    /**
     * Resets attack cooldown after attacking
     */
    public void resetAttack() {
        attackTimer = attackCooldown;
    }
    //track whether the enemy has been marked already per swing 
    private boolean hitThisSwing = false;
    /**
     * Determines if enemy can be damaged
     * @return 
     */
    public boolean canBeHit() {
        return !hitThisSwing && health > 0;
    }
    /**
     * Marks enemy as hit
     */
    public void markHit() {
        hitThisSwing = true;
    }
    /**
     * Reset hit detection
     */
    public void resetHit() {
        hitThisSwing = false;
    }
    
    


    /**
     * Draws enemy + health bar
     * 
     */
    @Override
    public void draw() {
        if (getHealth() > 0) {
            if (movingRight){
                app.image(facingRight,x,y);
            }else{
                app.image(facingLeft, x, y);
            }
            

            app.fill(255, 0, 0);
            app.rect(x+5, y - 10, 30, 5);
            app.fill(0, 255, 0);
            app.rect(x+5, y - 10, 30 * ((float)getHealth() / (float)startHealth), 5);
        }
    }
}

