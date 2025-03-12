package Controler;
public class Collision {
    public Collision() {
    }

    // Méthode de détection de collision
    public int[] collisionJoueur(int x, int y) {
        int[] newPosition = new int[2];

        // Limite X : empêcher de sortir à gauche ou à droite
        if (x < 0) {
            newPosition[0] = 0;  // Position à gauche
            System.out.println("Collision gauche !");
        } else if (x > 1475 - Character.WIDTH) {
            newPosition[0] = 1475 - Character.WIDTH;  // Position à droite
            System.out.println("Collision droite !");
        } else {
            newPosition[0] = x;  // Position valide
        }

        // Limite Y : empêcher de sortir en haut ou en bas
        if (y < 0) {
            newPosition[1] = 0;  // Position en haut
            System.out.println("Collision haut !");
        } else if (y > 825 - Character.HEIGHT) {
            newPosition[1] = 825 - Character.HEIGHT;  // Position en bas
            System.out.println("Collision bas !");
        } else {
            newPosition[1] = y;  // Position valide
        }

        // Debug : Affichage de la position après la collision
        System.out.println("Position après collision : x = " + newPosition[0] + ", y = " + newPosition[1]);

        return newPosition;
    }
}