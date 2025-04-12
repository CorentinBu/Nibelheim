package Controler;

import Model.Character;
import Model.Ennemies;
import Model.Tir;

// Gérer toutes les collisions
public class Collision extends Thread {

    // Constante pour le délai de rafraichissement
    public static final int DELAY = 25;

    // Instances de classe utiles
    private Character c;
    private Tir t;
    private Ennemies e;

    // Constructeur pour initialiser les instances de classe
    public Collision(Character c, Tir t, Ennemies e) {
        this.c = c;
        this.t = t;
        this.e = e;
    }

    // Methode pour gérer les collisions
    @Override
    public void run() {
        while (true) {
            // Gérer les collisions entre les tirs et les araignées

            // Détecter si le joueur touche un bonus
            c.checkBonusProche();

            // Détecter les collisions des ennemis
            Ennemies.allCollisions(c, t);

            try {
                Thread.sleep(DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
