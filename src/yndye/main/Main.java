package yndye.main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main extends Canvas implements Runnable {

    // FRAME VARIABLE
    public static JFrame frame;

    // RUNNING BOOLEAN
    private boolean running = false;

    // THREAD
    public Thread thread;

    // FRAMES
    public int frames = 0;

    // WIDTH - HEIGHT - SCALE VARIABLES
    public static final int WIDTH = 260;
    public static final int HEIGHT = 160;
    public static double SCALE = 3;

    // BUFFEREDIMAGE VARIABLE
    private BufferedImage renderImage;

    public Main(){
        // Set Window Size
        setPreferredSize(new Dimension((int)(WIDTH*SCALE),(int)(HEIGHT*SCALE)));
        Window();
        renderImage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
    }

    public void Window(){
        frame = new JFrame("Engine Test");
        frame.add(this);
        frame.setResizable(true);
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

    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {createBufferStrategy(3); return;};

        Graphics eg = renderImage.getGraphics();
        eg.dispose();
        eg = bs.getDrawGraphics();

        double fX = frame.getContentPane().getWidth() / (double)WIDTH;
        double fY = frame.getContentPane().getHeight() / (double)HEIGHT;
        if(fX > fY) SCALE = fY; else SCALE = fX;
        int ww = (int)(WIDTH*SCALE);
        int hh = (int)(HEIGHT*SCALE);
        int xw = frame.getContentPane().getWidth()/2 - ww/2;
        int yh = frame.getContentPane().getHeight()/2 - hh/2;

        // RENDER -->
        eg.setColor(Color.BLACK);
        eg.fillRect(0,0,frame.getContentPane().getWidth(),frame.getContentPane().getHeight());

        eg.drawImage(renderImage,xw,yh,ww,hh,null);

        // DRAW THE TEXT "ENGINE TEST"
        eg.setColor(new Color((int)Math.floor(Math.random() * 100),(int)Math.floor(Math.random() * 100),(int)Math.floor(Math.random() * 100)));
        eg.setFont(new Font("arial",Font.BOLD,32));
        eg.drawString("Engine Test",120,100);

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
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                update();
                render();
                frames++;
                delta--;
            }
            if(System.currentTimeMillis() - timer >= 1000){
                frames = 0;
                timer+=1000;
            }
        }
        stop();
    }
}
