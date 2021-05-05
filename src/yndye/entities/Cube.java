package yndye.entities;

import yndye.main.Main;

import java.awt.*;

public class Cube extends Entity{

    // SPEED VARIABLE
    private double speed = 4;

    // VARIABLES
    private int acel = 0;
    private int mass = (int)speed*getWidth()/3;

    private double ax;
    private double rx;

    public Cube(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    public void update(){
        x+=(speed*mass)/acel;
        if(this.getX() > Main.WIDTH){
            x = Main.WIDTH - Main.WIDTH; // Caso a posição X do jogador for maior que a largura da janela, a posição X volta para o começo da janela
            if(acel < 15) acel++;
        }
    }

    public void render(Graphics eg){
        Graphics2D g2 = (Graphics2D) eg;
        g2.setColor(new Color((int)Math.floor(Math.random() * 100),(int)Math.floor(Math.random() * 100),(int)Math.floor(Math.random() * 100)));
        g2.fillRect(this.getX(),this.getY(),32,32);
    }
}
