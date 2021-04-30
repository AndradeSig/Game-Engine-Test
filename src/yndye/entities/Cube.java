package yndye.entities;

import yndye.main.Main;

import java.awt.*;

public class Cube extends Entity{

    // SPEED VARIABLE
    private double speed = 4;

    public Cube(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    public void update(){
        x+=speed;
        if(this.getX() > Main.WIDTH){
            x = Main.WIDTH - Main.WIDTH;
        }
    }

    public void render(Graphics eg){
        eg.setColor(new Color((int)Math.floor(Math.random() * 100),(int)Math.floor(Math.random() * 100),(int)Math.floor(Math.random() * 100)));
        eg.fillRect(this.getX(),this.getY(),16,16);
    }
}
