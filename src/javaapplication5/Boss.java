/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication5;
import processing.core.*;
/**
 *Boss enemy, variation of Enemy class. More enhanced and stronger version.
 * @author jaspe
 */
public class Boss extends Enemy {
    protected int phase = 1;
    public Boss(PApplet app, int x, int y) {
        super(app, x, y, 20, 200, "images/enemy.png",
              1, 400, 300, 700);
    }
    /**
     * Updates phase based on remaining HP.As health decreases, becomes more 'enraged'.
     */
    public void updatePhase() {
        if (health < 120) phase = 2;
        if (health < 60) phase = 3;
    }
    /**
     * Boss chases the player while adjusting its speed to match its phase. 
     * @param player 
     */

    @Override
    public void chase(Character player) {
        super.chase(player);
        updatePhase();

        if (phase == 2) {
            speed = 1.5;
        } else if (phase == 3) {
            speed = 2;
        }
    }
}

