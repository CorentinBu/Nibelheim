import javax.swing.*;
import View.*;
import Control.*;
import Model.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // instances de classe utiles
        Tir t = new Tir();
        Affichage a = new Affichage(t);
        ReactionClic r = new ReactionClic(a, t);
        Avancer_tir avancer_tir = new Avancer_tir(t);
        Redessine redessine = new Redessine(a);

        // Ajout du listener
        a.addMouseListener(r);

        // Mise en marche des Threads
        avancer_tir.start();
        redessine.start();

        // Creation de la fenetre principale
        JFrame f = new JFrame("Nibelheim game");

        // Ajout du panel et parametrage + affichage de la fenetre
        f.add(a);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        
    }
}
