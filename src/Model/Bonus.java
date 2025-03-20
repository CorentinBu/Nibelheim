package Model;

import java.awt.Point;
import java.util.ArrayList;

//Cette classe décrit les bonus que le joueur peut ramasser.
//Les bonus sont lâchés par les ennemis lorsqu'ils sont détruits.
//Les bonus sont aimantés, attirés par le joueur

public class Bonus {

    // Instance de la classe utiles
    private ArrayList<Point> pointBonus = new ArrayList<>();
    
    // Getter pour récupérer la liste des points de bonus
    public ArrayList<Point> getPointBonus(){
        return pointBonus;
    }

    //  Methode pour ajouter un bonus à la liste pointBonus
    public void addBonus(Point p) {
        pointBonus.add(p);
        System.out.println("Bonus ajouté à la position: " + p);
    }

    // Methode pour supprimer un bonus de pointBonus
    public void removeBonus(Point point) {
        pointBonus.remove(point);
    }

    // Méthode vérifiant si le joueur touche le bonus
    // public void checkCollision(int playerX, int playerY) {
    //     // Si le joueur est assez proche du bonus pour le ramasser
    //     if (!collected && Math.abs(playerX - spawnPoint.x) < size && Math.abs(playerY - spawnPoint.y) < size) {
    //         collected = true; // Le bonus est collecté
    //         removeBonus(this); // Retirer le bonus de l'affichage (méthode à créer dans Affichage)
    //         // On peut aussi ici ajouter des points ou d'autres effets, selon le type de bonus
    //     }
    // }
 

    // Méthode de déplacement du bonus attiré par le joueur
    public void attractToPlayer(Point p) {
        // Vérifier si la piéce est proche du joueur (p)
        for (int i = 0; i < pointBonus.size(); i++) {
            // le bonus va vers le joueur
            if (pointBonus.get(i).x < p.x) {
                pointBonus.get(i).x += 1;
            } else if (pointBonus.get(i).x > p.x) {
                pointBonus.get(i).x -= 1;
            }
        }

    }

}