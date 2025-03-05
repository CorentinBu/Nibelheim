package Controler;

import View.Affichage;

//Classe du personnage principal du jeu. Celui contrôlé par le joueur.
//Le joueur utilise les touches Z,Q,S,D pour se déplacer.
//Il peut se déplacer librement sur toute la fenêre.
//Il peut également tirer des projectiles.
//Il a une barre de vie qui diminue lorsqu'il est touché par un ennemi.
//Il peut ramasser des bonus pour augmenter sa vie ou sa puissance de tir.

public class Character extends Thread {

    // Attributs
    public int current_x = 820;
    public int current_y = 540;
    private int speed = 50;

    private Inputs inputs;

    // Constructeur
    public Character(Inputs i) {
        inputs = i;
    }

    // Thread qui va regarder les valeurs booléennes dans la classe Input pour
    // appeler ou non les fonctions de déplacement
    public void run() {
        while (true) {
            if (inputs.up) {
                moveUp();
            }
            if (inputs.down) {
                moveDown();
            }
            if (inputs.left) {
                moveLeft();
            }
            if (inputs.right) {
                moveRight();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Méthodes
    public void moveUp() {
        this.current_y -= speed;
        // System.out.println(current_y);
    }

    public void moveDown() {
        this.current_y += speed;
    }

    public void moveLeft() {
        this.current_x -= speed;
    }

    public void moveRight() {
        this.current_x += speed;
    }

}
