package Controler;

import javax.swing.ImageIcon;

import View.Affichage;

import java.awt.Image;

// Classe du personnage principal du jeu
// Le joueur utilise les touches Z, Q, S, D pour se déplacer.
public class Character extends Thread {

    // Attributs
    public int current_x = 820; // Position de départ en X
    public int current_y = 540; // Position de départ en Y
    private int speed = 50; // Vitesse de déplacement
    private Collision collision; // Instance de la classe Collision
    private Inputs inputs; // Instance de la classe Inputs (pour les touches)

    // Constructeur
    public Character(Inputs i) {
        inputs = i;
        collision = new Collision(); // Créer l'instance de collision
    }

    // Image du personnage
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    public static final Image characterSprite = new ImageIcon(Character.class.getResource("/Images/character.png")).getImage()
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
                Thread.sleep(50); // Attente de 50ms avant de traiter le prochain mouvement
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Méthodes de déplacement
    public void moveUp() {
    if (this.current_y > 0) { 
        this.current_y -= speed;
    }
    int[] newPos = collision.collisionJoueur(this.current_x, this.current_y);
    this.current_x = newPos[0];
    this.current_y = newPos[1];
}



public void moveLeft() {
    if (this.current_x > 0) { 
        this.current_x -= speed;
    }
    int[] newPos = collision.collisionJoueur(this.current_x, this.current_y);
    this.current_x = newPos[0];
    this.current_y = newPos[1];
}


public void moveDown() {
    // Calcul de la nouvelle position Y
    int newY = this.current_y + speed;
    
    // Vérification de la collision et mise à jour de la position
    int[] newPos = collision.collisionJoueur(this.current_x, newY);
    this.current_x = newPos[0];  // Nouvelle position X (après collision)
    this.current_y = newPos[1];  // Nouvelle position Y (après collision)

    System.out.println("Position du joueur : x = " + current_x + ", y = " + current_y);
    System.out.println("Taille de la fenêtre : " + Affichage.X + "x" + Affichage.Y);
}

public void moveRight() {
    int newX = this.current_x + speed;
    int[] newPos = collision.collisionJoueur(newX, this.current_y);
    this.current_x = newPos[0];
    System.out.println("Position du joueur : x = " + current_x + ", y = " + current_y);
    System.out.println("Taille de la fenêtre : " + Affichage.X + "x" + Affichage.Y);
    System.out.println("Dessin du personnage à x = " + current_x + ", y = " + current_y);
}
}
