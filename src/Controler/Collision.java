package Controler;

public class Collision {
    public Collision() {
    }

    // Modification de la méthode pour inclure la taille du personnage
    public int[] collisionJoueur(int x, int y) {
        int[] newPosition = new int[2];

        // Vérification des limites X (gauche/droite)
        if (x < 0) {
            newPosition[0] = 0;
        } else if (x > 1920 - Character.WIDTH) { // Prendre en compte la largeur du personnage
            newPosition[0] = 1920 - Character.WIDTH;
        } else {
            newPosition[0] = x;
        }

        // Vérification des limites Y (haut/bas)
        if (y < 0) {
            newPosition[1] = 0;
        } else if (y > 1080 - Character.HEIGHT) { // Prendre en compte la hauteur du personnage
            newPosition[1] = 1080 - Character.HEIGHT;
        } else {
            newPosition[1] = y;
        }

        return newPosition;
    }
}
