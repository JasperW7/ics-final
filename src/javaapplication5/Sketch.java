/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication5;

/**
 *Main game 
 * @author jaspe
 */

import processing.core.PApplet;
import processing.core.PImage;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Sketch extends PApplet{
    // scene tracker
    enum Scene {
        MENU,
        SCENE1,
        SCENE2,
        GAME1,
        PROMPT,
        TRANSITION,
        PEACH,
        GAME2,
        END
    }
    //mouse pressed double check
    boolean mousePrev = false;
    String playerName = ""; //player name (inputted)
    int cursorTimer = 0; //blinking cursor frame counter
    private boolean staffWasActive = false; ////staff hit double check


    Scene currentScene = Scene.MENU; //initialized scene
    //character inits
    SunWuKong player;
    Enemy enemy1;
    Boss boss;
    ArrayList<Enemy> enemies = new ArrayList<>();
    boolean secretLevel = false; //boolean if player reaches secret level
    int WINDOW_WIDTH=500, WINDOW_LENGTH = 800; //screen setup
    Set <String> keys = new HashSet<>(); //keys pressed arraylist
    
    public void settings(){
	   //sets the size of the window
        size (WINDOW_LENGTH,WINDOW_WIDTH);
    }
    public void setup(){
	   //sets the background colour using R,G,B (https://rgbcolorpicker.com/)
        textSize(20);
        player = new SunWuKong(this,100,300);
        enemy1 = new Enemy(this,400,350,10,100,"images/enemy.png",2,200,300,500);
    }
    /**
     * detects mouse pressed and tracks previous state
     */
    public void mousePressed(){
        mousePrev = true;
    }
    /**
     * remove key from keys arraylist when released
     */
    @Override
    public void keyReleased(){
        keys.remove(Integer.toString(keyCode));
    }
    /** Handles key pressed
     * adds key to keys arraylist when pressed
     * name input
     * Save/Read commands
     */
    @Override
    public void keyPressed(){
        int code = keyCode;
        keys.add(Integer.toString(code));
        //name input
        if (currentScene == Scene.PROMPT) {

            if (key == ENTER && playerName.length() > 0) {
                currentScene = Scene.GAME1;
                player.setY(300);
                return;
            }

            if (key == BACKSPACE && playerName.length() > 0) {
                playerName = playerName.substring(0, playerName.length() - 1);
                return;
            }

            if (key >= 'a' && key <= 'z' || 
                key >= 'A' && key <= 'Z' || 
                key == ' ') {

                if (playerName.length() < 30) {
                    playerName += key;
                }
            }
            return;
        }else if (currentScene == Scene.END) { //save
            File file = new File("save.txt");

            if (key == 's' || key == 'S') {
                saveGame(file);
                System.out.println("Game saved.");
            }

            if (key == 'r' || key == 'R') { //load
                loadGame(file);
            }
        }
        
    }
    /**
     * WRites data to file
     * @param file file path
     */
    void saveGame(File file) {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println(playerName);
            writer.println(player.health);
            writer.println(player.numOfAttacks);
            writer.println(secretLevel);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Reads data from file to terminal
     * @param file file path
     */
    void loadGame(File file){
        if (file == null) return;

        try {
            Scanner scanner = new Scanner(file);
            String name = scanner.nextLine();
            int health = scanner.nextInt();
            int attacks = scanner.nextInt();
            boolean reachedSecret = scanner.nextBoolean();
            scanner.close();
            System.out.println("Name: " + name);
            System.out.println("Health: " + health);
            System.out.println("Attacks: " + attacks);
            System.out.println("Reached secret level: "+reachedSecret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Main loop to draw scenes
     */
    public void draw() {
        switch(currentScene){
            case MENU -> drawMenu();
            case SCENE1 -> drawScene1();
            case SCENE2 -> drawScene2();
            case PROMPT -> promptScene();
            case GAME1 -> drawGame();
            case TRANSITION -> transitionScene();
            case PEACH -> peachScene();
            case GAME2 -> drawGame2();
            case END -> end();
        }
    }

    /**
     * Main menu scene
     */
    void drawMenu(){
        image(loadImage("images/menu page.jpg"),0,0);
        if (mousePressed && mouseX>=270 && mouseY<=525 && mouseY>=400 && mouseY<=470 && mousePrev){ //button area
            currentScene = Scene.SCENE1;
            mousePrev = false;
        }
    }
    //Scene 1
    void drawScene1(){
        image(loadImage("images/scene 1.jpg"),0,0);
        if (mousePressed && mousePrev){
            mousePrev = false;
            currentScene = Scene.SCENE2;
        }
        fill(0,0,0);
        textSize(30);
        text("Once there was a magical rock...",200,50);
        fill(255,255,0);
        textSize(25);
        text("Click mouse to move on..",250,480);
    }
    //Scene 2
    void drawScene2(){
        image(loadImage("images/scene 2.jpg"),0,0);
        if (mousePressed && mousePrev){
            currentScene = Scene.PROMPT;
        }
        fill(0,0,0);
        textSize(30);
        text("With dazzling bright light, a monkey springs forth from the rock!",00,50);
        fill(255,255,0);
        textSize(25);
        text("Click mouse to move on..",250,480);
    }
    //Scene for user prompt for name
    void promptScene() {
        image(loadImage("images/background.jpg"), 0, 0);

        fill(0);
        textAlign(CENTER);
        textSize(32);
//        text("From stone and starlight,", width / 2, 80);
        text("A name must be chosen.", width / 2, 120);

        textSize(20);
        text("Enter your name:", width / 2, 200);

        cursorTimer++;
        boolean showCursor = (cursorTimer / 30) % 2 == 0;
        String displayName = playerName + (showCursor ? "|" : "");

        fill(255);
        rect(width / 2 - 150, 230, 300, 40);
        fill(0);
        textAlign(LEFT);
        text(displayName, width / 2 - 140, 258);

        textAlign(CENTER);
        textSize(16);
        text("Press ENTER to begin your journey", width / 2, 320);
    }
    //transition scene method
    
    void transitionScene(){
        background(0);
        fill(255,255,00);
        textSize(20);
        text("Congratulations! You have defeated your first enemy.",width/2,height/2-100);
        text("The Jade Emperor has taken notice of you.", width/2, height/2);
        text("He has promoted you to be a royal immortal peach farmer.",width/2,height/2+50);
        textSize(15);
        text("Click to proceed..", width/2, height/2+100);
        if (mousePressed && mousePrev){ //54 81
            player.setX(100);
            player.setY(300);
            player.touchingGround=false;
            mousePrev = false;
            currentScene = Scene.PEACH;
        }
    }
    //peach scene method
    void peachScene(){
        image(loadImage("images/peach scene.jpg"),0,0);
        playerMovement();
        player.x = Math.min(width-player.image.width,Math.max(player.x,0));
        if (mousePressed &&mousePrev){
            mousePrev = false;
            if (mouseX>=100 && mouseX<=318 && mouseY>=54 && mouseY<=81){
                player.setX(100);
                player.setY(300);
                player.touchingGround=false;

                enemies.clear();
                //set enemies
                enemies.add(new Enemy(this, 350, 350, 8, 100, "images/enemy.png", 2, 200, 300, 500));
                enemies.add(new Enemy(this, 450, 350, 8, 100, "images/enemy.png", 2, 200, 300, 500));
                enemies.add(new Boss(this, 600, 350)); //polymorphic 
                
                currentScene = Scene.GAME2;
            }else if(mouseX>=480 && mouseX<=700 && mouseY>=54 && mouseY<=81){
                currentScene = Scene.END;
            }
        }
    }
    //game 1 method
    
    void drawGame(){
        image(loadImage("images/background.jpg"),0,0);
        playerMovement(); //player mvoement
        //text 
        fill(0);
        textAlign(CENTER);
        textSize(16);
        text(playerName, player.x + player.image.width / 2, player.y - 10);
        textSize(20);
        text("Use WASD to move, and Q to attack!", width/2,height/2-150);
        text("Avoid the enemy!", width/2, height/2-100);
        //enemy movement
        enemy1.chase(player);
        enemy1.updateCooldown(); //enemy attack

        if (enemy1.isAlive() && isColliding(enemy1, player) && enemy1.canAttack()) { //player collision with enemy
            player.takeDamage(enemy1.getAtk());
            enemy1.resetAttack();
        }
        
        Staff staff = player.getStaff();

        if (staff != null && staff.isActive() && !staffWasActive) {  //staff returned
            enemy1.resetHit();
        }

        staffWasActive = (staff != null && staff.isActive());

        if (staff != null && staff.isActive()) {
            if (staffCollision(staff, enemy1) && enemy1.canBeHit()) { //staff collision with enemy
                
                enemy1.takeDamage(player.getAtk());
                enemy1.markHit();
            }
        }
        if(staff!=null && staffCollision(staff,enemy1) && enemy1.isAlive()){ //text to show collission
            text(Double.toString(player.atk),enemy1.getCenterX(),enemy1.getCenterY()-50);
        }
        if (!enemy1.isAlive()){ //next scene
            if (player.x+player.image.width==WINDOW_LENGTH){
                currentScene = Scene.TRANSITION;
            }
        }else{
            player.x = Math.min(width-player.image.width,Math.max(player.x,0)); //player bounds
        }
        if (player.health==0){ //reset if player dies
            resetGame1();
            
        }

        enemy1.draw();
    }
    //game 2 method
    void drawGame2(){
        secretLevel = true; //secret level reached
        //text
        image(loadImage("images/background.jpg"),0,0);
        textSize(20);
        fill(0);
        text("The Jade Emperor is enraged! He has taken divine punishment on you.",width/2,150);
        text("Every 5 seconds, divine punishment will deal 2 damage.",width/2,200);
        playerMovement(); //player movement
        Staff staff = player.getStaff();

        for (Enemy e: enemies){ //loop through enemies
            e = (Enemy) e;
            e.chase(player);
            e.updateCooldown();
            if (e.isAlive() && isColliding(e,player) && e.canAttack()){ //colllision detection of player with enemies
                player.takeDamage(e.atk);
                e.resetAttack();
            }
            
            if (staff != null && staff.isActive() && !staffWasActive) { //staff returned
                e.resetHit();
            }
            
            if (staff != null && staff.isActive()) { //staff collision with enemies
                if (staffCollision(staff, e) && e.canBeHit()) {
                    
                    e.takeDamage(player.getAtk());
                    e.markHit();
                }
            }
            
            if(staff!=null && staffCollision(staff,e) && e.isAlive()){ //text to show collision of staff
                text(Double.toString(player.atk),e.getCenterX(),e.getCenterY()-50);
            }
            e.draw();
        }
        staffWasActive = staff!=null&&staff.isActive();
        
        for (Enemy e:enemies){
            if (e instanceof Boss){ //polymorphic check
                Boss b = (Boss) e;
                if (b.health==0){
                    currentScene = Scene.END; //only end when boss dies
                }
            }
        }
        
        if (frameCount % 300 == 0) { //divine punishment -> once every 300 frames
            fill(255, 255, 0, 120); //flash
            rect(0, 0, width, height);
            player.takeDamage(2);
        }
        
        if (player.health==0){ //reset if player dies
            resetGame2();
        }
        
    }
    //end scene
    void end() {
        background(0);
        fill(255);
        textAlign(CENTER);
        textSize(24);
        text("Your fate is sealed.", width/2, height/2 - 80);

        textSize(16);
        text("Press S to Save Game", width/2, height/2);
        text("Press R to Load Game", width/2, height/2 + 30);
    }

    //reset methods
    void resetGame1() {
        player = new SunWuKong(this, 100, 300);
        enemy1 = new Enemy(this, 400, 350, 10, 100, "images/enemy.png", 2, 200, 300, 500);

        staffWasActive = false;
        currentScene = Scene.GAME1;
    }
    
    void resetGame2(){
        player = new SunWuKong(this, 100, 300);

        enemies.clear();
        enemies.add(new Enemy(this, 350, 350, 8, 100, "images/enemy.png", 2, 200, 300, 500));
        enemies.add(new Enemy(this, 450, 350, 8, 100, "images/enemy.png", 2, 200, 300, 500));
        enemies.add(new Boss(this, 600, 350));

        currentScene = Scene.GAME2;
    }

    //collision detection (based off of graphics book creator)
    boolean isColliding(Character a, Character b) {
        return a.getX() < b.getX() + b.image.width &&
               a.getX() + a.image.width > b.getX() &&
               a.getY() < b.getY() + b.image.height &&
               a.getY() + a.image.height > b.getY();
    }
    //staff circular collision
    boolean staffCollision(Staff staff, Enemy enemy) {
        float dx = staff.getX() - enemy.getCenterX();
        float dy = staff.getY() - enemy.getCenterY();
        float distSq = dx * dx + dy * dy;

        float r = staff.getRadius() + enemy.getRadius();
        fill(255,0,0);
        return distSq <= r * r;
    }
    //movement & update based off keys pressed
    void playerMovement(){
        int dx = 0;
        keyReleased();
        if (keys.contains(Integer.toString(65))) {
            dx = -5;
        }
        if (keys.contains(Integer.toString(68))) {
            dx = 5;
        }   
        if (keys.contains(Integer.toString(87))) player.jump();
        if (keys.contains(Integer.toString(81))) player.attack();
        player.move(dx,0);
        player.update();
        player.render();
    }

    

    
    

    
    
}
