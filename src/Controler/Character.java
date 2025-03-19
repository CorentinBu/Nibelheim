package Controler;

import javax.swing.ImageIcon;

import View.Affichage;

import java.awt.Image;



//Classe du personnage principal du jeu. Celui contrôlé par le joueur.
//Le joueur utilise les touches Z,Q,S,D pour se déplacer.
//Il peut se déplacer librement sur toute la fenêre.
//Il peut également tirer des projectiles.
//Il a une barre de vie qui diminue lorsqu'il est touché par un ennemi.
//Il peut ramasser des bonus pour augmenter sa vie ou sa puissance de tir.
public class Character extends Thread {

    // Attributs
    public double current_x = 820;
    public double current_y = 540;
    
    private double vx = 0, vy = 0;  // Vitesse horizontale et verticale
    private double acceleration = 2; // Accélération progressive
    private double friction = 0.9;  // Décélération (simule l’inertie)
    private double maxSpeed = 25;   // Vitesse maximale
    
    private int vie = 110; // Points de vie du joueur
    private Inputs inputs; // Gestion des entrées clavier

    // Dimensions du personnage
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    //cree des getter pour vx et vy
    public double getVx() {
        return vx;
    }
    public double getVy() {
        return vy;
    }


    // Sprite du personnage
    public static final Image characterSprite = new ImageIcon("src/Images/character.png")
            .getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);

    // Constructeur
    public Character(Inputs i) {
        inputs = i;
    }

    // Thread qui gère le déplacement
    public void run() {
        while (true) {
            updateMovement(); // Mise à jour des déplacements

            try {
                Thread.sleep(16); // Environ 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Gestion du déplacement avec inertie
    private void updateMovement() {
        // Accélération selon les touches pressées
        if (inputs.up) { vy -= acceleration; }
        if (inputs.down) { vy += acceleration; }
        if (inputs.left) { vx -= acceleration; }
        if (inputs.right) { vx += acceleration; }
        

        // Limite la vitesse maximale
        vx = Math.max(-maxSpeed, Math.min(maxSpeed, vx));
        vy = Math.max(-maxSpeed, Math.min(maxSpeed, vy));

        // Appliquer la friction (simule une glisse après l'arrêt des touches)
        vx *= friction;
        vy *= friction;

        // Appliquer le mouvement
        current_x += vx;
        current_y += vy;

        // Garder la sorcière dans l'écran
        current_x = Math.max(0, Math.min(1800, current_x));
        current_y = Math.max(0, Math.min(1080, current_y));
    }

    // Getter et setter pour la vie
    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }
}