package Controler;

import javax.swing.ImageIcon;
import java.awt.*;

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

    // points de vie du joueur
    private double vx = 0, vy = 0;
    private int vie = 110;

    private Inputs inputs;
    public Rectangle hitboxC;

    // Constructeur
    public Character(Inputs i) {
        inputs = i;
        this.hitboxC = new Rectangle(current_x, current_y, WIDTH, HEIGHT);
    }

    /* image character */
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    public static final Image characterSprite = new ImageIcon("src/Images/character.png").getImage()
            .getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);

                //creer des getters pour vx et vy
    public double getVx() {
        return vx;
    }
    public double getVy() {
        return vy;
    }

    // Getters pour la position du joueur
    public double getCurrent_x() {
        return current_x;
    }
    public double getCurrent_y() {
        return current_y;
    }

    // Thread qui va regarder les valeurs booléennes dans la classe Input pour
    // appeler ou non les fonctions de déplacement
    public void run() {
        while (true) {
            hitboxC.x = current_x;
            hitboxC.y = current_y;

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

    public int getSpeed() {
        return speed;
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

}