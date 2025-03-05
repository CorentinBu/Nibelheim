package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Redessine extends Thread{

    public static final int DELAY = 50;
    public Affichage monAffichage;

    public Redessine(Affichage a){
        this.monAffichage = a;
    }

    //Redessine l'écran 
    @Override
    public void run() {
      while (true) {
        monAffichage.revalidate();
        monAffichage.repaint();
        try { Thread.sleep(DELAY); }
        catch (Exception e) { e.printStackTrace(); }
      }
    }

}