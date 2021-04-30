package yndye.main;

import yndye.Generator.Generator;
import yndye.entities.Cube;
import yndye.entities.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Canvas implements Runnable {

    // FRAME VARIABLE
    public static JFrame frame;

    // RUNNING BOOLEAN
    private boolean running = false;

    // THREAD
    public Thread thread;

    // FRAME VARIABLE
    public int frames = 0;

    // WIDTH - HEIGHT - SCALE VARIABLES
    public static final int WIDTH = 360;
    public static final int HEIGHT = 210;
    public static final int SCALE = 3;

    // BUFFEREDIMAGE VARIABLE
    private BufferedImage renderImage;


    // OTHER CLASS
    public static List<Entity> entities; // ENTITY CLASS
    public static Generator generator; // GENERATOR CLASS

    public Main(){
        Window(); // Create Window
        renderImage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

        // OTHER CLASS
        entities = new ArrayList<Entity>();
        generator = new Generator();
    }

    public void Window(){
        setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        frame = new JFrame("Engine Test");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public synchronized void start(){
        thread = new Thread(this);
        running = true;
        thread.start();
    }

    public synchronized void stop(){
        running = false;
        try {thread.join();}catch(InterruptedException e){e.printStackTrace();}
    }

    public static void main(String[] args){
        Main main = new Main();
        main.start();
    }

    public void update(){
        for(int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            e.update();
        }
        generator.update();
    }

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){this.createBufferStrategy(3);return;}
        Graphics eg = renderImage.getGraphics();
        eg.setColor(new Color(0,0,0));
        eg.fillRect(0,0,WIDTH,HEIGHT);
        /**** RENDER ENGINE *****/

        for(int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            e.render(eg);
        }

        eg.setColor(Color.WHITE);
        eg.setFont(new Font("arial",Font.BOLD,18));
        eg.drawString("Engine Test",WIDTH/SCALE,180);

        /**********/
        eg.dispose();
        eg = bs.getDrawGraphics();
        eg.drawImage(renderImage,0,0,WIDTH*SCALE,HEIGHT*SCALE,null); // DRAW IMAGE "RENDER IMAGE"
        bs.show();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountUpdataes = 60.0;
        double ns = 1000000000 / amountUpdataes;
        double delta = 0;
        double timer = System.currentTimeMillis();
        while(running){
            long now = System.nanoTime();
            delta+= (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                update();
                render();
                frames++;
                delta--;
            }
            if(System.currentTimeMillis() - timer >= 1000){
                System.out.println("Fps: "+frames);
                frames = 0;
                timer+=1000;
            }
        }
        stop();
    }
}
