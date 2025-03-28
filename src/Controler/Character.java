package Controler;

import javax.swing.ImageIcon;
import java.awt.Image;

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
    private int speed = 25;
    private Collision collision;
    // points de vie du joueur
    private int vie = 110;

    private Inputs inputs;

    // Constructeur
    public Character(Inputs i) {
        inputs = i;
    }

    /* image character */
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    public static final Image characterSprite = new ImageIcon("src/Images/character.png").getImage()
            .getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);

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

    // getter et setter vie du joueur
    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    // Méthodes de déplacement
    public void moveUp() {
        if (current_y > 0) {
            this.current_y -= speed;
        }
    }

    public void moveDown() {
        if (current_y < 1080) {
            this.current_y += speed;
        }
    }

    public void moveLeft() {
        if (current_x > 0) {
            this.current_x -= speed;
        }
    }

    public void moveRight() {
        if (current_x < 1800) {
            this.current_x += speed;
        }
    }

    public int getSpeed() {
        return speed;
    }

}