package yndye.entities;

import java.awt.*;

public class Entity {

    protected double x;
    protected double y;
    protected int width;
    protected int height;

    public Entity(double x, double y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX(){
        return (int)x;
    }

    public int getY(){
        return (int)y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void update(){

    }

    public void render(Graphics eg){

    }
}
