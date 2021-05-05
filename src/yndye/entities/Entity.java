package yndye.entities;

import java.awt.*;

public class Entity {

    protected double x;
    protected double y;
    protected int width;
    protected int height;

    // Método principal para indicar a posição X, Y, LARGURA E ALTURA de tal entidade
    public Entity(double x, double y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX(){
        return (int)x;
    } // Retornar o Valor X da entidade

    public int getY(){
        return (int)y;
    } // Retornar o Valor Y da entidade

    public int getWidth(){
        return width;
    } // Retornar a Largura da entidade

    public int getHeight(){
        return height;
    } // Retornar a Altura da entidade

    public void update(){

    }

    public void render(Graphics eg){

    }
}
