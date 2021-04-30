package yndye.Generator;

import yndye.entities.Cube;
import yndye.main.Main;

public class Generator {

    // TIMER VARIABLE
    private int timer = 0;

    public void update(){
        timer++;
        if(timer % 60 == 0){
            Cube cube = new Cube(0,(int)Math.floor(Math.random() * 100),16,16);
            Main.entities.add(cube);
        }
    }

}
