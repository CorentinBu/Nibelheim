package Controler;

import Model.Araignee;
import Model.Tir;
import View.Affichage;

// Gérer toutes les collisions
public class Collision extends Thread {

    // Constante pour le délai de rafraichissement
    public static final int DELAY = 50;

    // Instances de classe utiles
    private Character c;
    private Tir t;
    private Araignee a;
    private Affichage aff;

    // Constructeur pour initialiser les instances de classe
    public Collision(Character c, Tir t, Araignee a, Affichage aff) {
        this.c = c;
        this.t = t;
        this.a = a;
        this.aff = aff;
    }

    // Methode pour gérer les collisions
    @Override
    public void run() {
        while (true) {
            // Gérer les collisions entre les tirs et les araignées
            System.out.println("Check collision");
            a.removeAraigneeTouchee();
            // Redessiner l'écran
            aff.revalidate();
            aff.repaint();
            System.out.println("position x:"+c.getVx()+" position y "+c.getVy());

            try {
                Thread.sleep(DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
