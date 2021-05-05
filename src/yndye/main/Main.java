package yndye.main;

import yndye.Generator.Generator;
import yndye.entities.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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
        renderImage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB); // Indicar a "renderImage" como um Buffer

        // OTHER CLASS
        entities = new ArrayList<Entity>(); // Setar entidades como ArrayList
        generator = new Generator(); // Setar a Classe para a Geração dos objetos
    }

    public void Window(){
        setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE)); // Setar as dimensões da Janela
        frame = new JFrame("Engine Test"); // Setar a variável "frame" como a classe "JFrame", e setar o Título
        frame.add(this); // Adicionar o frame com a localização da classe main(this)
        frame.setResizable(false); // Indicar se a janela vai ser Redimensionável ou não
        frame.pack(); // Setar o pack da janela
        frame.setLocationRelativeTo(null); // Setar a posição da janela no centro
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Setar a operação da finalização da Janela da forma correta
        frame.setVisible(true); // Setar a janela como visível
    }

    public synchronized void start(){
        thread = new Thread(this); // Setar a variável Thread como a classe Thread pegando o método prícipal(this)
        running = true;
        thread.start(); // Iniciar as Threads
    }

    // FUNÇÃO PARA INDICAR A FINALIZAÇÃO DO PROGRAMA(PARA FINALIZAR AS THREADS). ESTE MÉTODO SERVE PARA FINALIZAR AS THREADS, E NÃO DEIXAR AS THREADS RODANDO MESMO DEEPOIS DE TER FINALIZADO O PROGRAMA
    public synchronized void stop(){
        running = false; // Parar o método "run"
        try {thread.join();}catch(InterruptedException e){e.printStackTrace();} // Finalizar as Threads
    }

    // MÉTODO PRINCIPAL .-.
    public static void main(String[] args){
        Main main = new Main();
        main.start(); // Iniciar
    }

    // MÉTODO DE ATUALIZAÇÃO(UPDATE)
    public void update(){
        /***  Laço de Repetição para pegar todas as entidades, e atualizar elas  ***/
        for(int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            e.update();
        }
        generator.update(); // Atualização do Gerador
    }

    // MÉTODO DE RENDERIZAÇÃO
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

        eg.setFont(new Font("arial",Font.BOLD,12));
        eg.drawString("Entities: "+entities.size(),WIDTH/SCALE,200);

        /**********/
        eg.dispose(); // Remover tudo que não está sendo renderizado(Função ótima para otimização)
        eg = bs.getDrawGraphics();
        eg.drawImage(renderImage,0,0,WIDTH*SCALE,HEIGHT*SCALE,null); // DRAW IMAGE "RENDER IMAGE"
        bs.show(); // Mostrar as Buffers
    }

    // MÉTODO "RUN"(RODAS OS FPS)
    @Override
    public void run() {
        long lastTime = System.nanoTime(); // Pega o tempo do computador em nanosegundos
        double amountUpdataes = 60.0; // Máximo de FPS
        double ns = 1000000000 / amountUpdataes; // Divide o máximo de FPS em 10000000000(nanosegundos)
        double delta = 0;
        double timer = System.currentTimeMillis(); // Pega o tempo do computador em nanosegundos, porém não tão preciso
        while(running){
            long now = System.nanoTime(); // Pega o tempo do computador em nanosegundo
            delta+= (now - lastTime) / ns;
            lastTime = now; // Transforma o último nanosegundo no novo nanosegundo do computador
            if(delta >= 1){
                update(); // Seta o método Update a cada atualização
                render(); // Seta o método Render a cada atualização
                frames++;
                delta--;
            }
            /** Printar os FPS **/
            if(System.currentTimeMillis() - timer >= 1000){
                System.out.println("FPS: "+frames);
                frames = 0;
                timer+=1000;
            }
        }
        stop(); // Caso não estiver rodando o loop, vai setar o método "stop()"
    }
}
