package com.example.tap2024proyecto.components;

import javafx.scene.control.ProgressBar;

public class CorredorThread extends Thread{

    private ProgressBar pgbCorredor;

    public CorredorThread(String name, ProgressBar pgbCorredor) {
        super(name);
        this.pgbCorredor = pgbCorredor;
    }

    @Override
    public void run() {
        super.run();
        System.out.println(this.getName()+ " en su carril");
        double avance = 0;
        while ( avance <= 1 ) {
            avance += Math.random()/10;
            try {
                Thread.sleep((long)(Math.random()*2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.pgbCorredor.setProgress(avance);
        }
        System.out.println(this.getName()+ " llego a la meta");
    }
}
