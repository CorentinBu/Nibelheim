package Model;

import java.awt.Point;
import java.util.ArrayList;

import View.Affichage;

//Cette classe décrit les bonus que le joueur peut ramasser.
//Les bonus sont lâchés par les ennemis lorsqu'ils sont détruits.
//Les bonus sont aimantés, attirés par le joueur

public class Bonus extends Thread {

    private Affichage a;
    private Point spawnPoint;
    private int size = 1;
    private ArrayList<Point> pointBonus;
    private boolean collected = false;

    public Bonus() {
        this.a = a;
        pointBonus = new ArrayList<>();
        // Ajouter 3 bonus pour tester
        pointBonus.add(new Point(100, 100));
        pointBonus.add(new Point(200, 200));
        pointBonus.add(new Point(300, 300));
    }
    public ArrayList<Point> getPointBonus(){
        return pointBonus;
    }

    //  Methode pour ajouter un bonus à pointBonus
    public void addBonus(Point p) {
        pointBonus.add(p);
    }

    // Methode pour supprimer un bonus de pointBonus
    public void removeBonus(Bonus b) {
        pointBonus.remove(b);
    }
    

    public Point getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Point spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void spawn() {
        if (spawnPoint == null) {
            spawnPoint = new Point(500, 500); // Remplace ces coordonnées par la position correcte
        }
/*         a.drawBonus(spawnPoint, size);
 */    }

    // Méthode vérifiant si le joueur touche le bonus
    public void checkCollision(int playerX, int playerY) {
        // Si le joueur est assez proche du bonus pour le ramasser
        if (!collected && Math.abs(playerX - spawnPoint.x) < size && Math.abs(playerY - spawnPoint.y) < size) {
            collected = true; // Le bonus est collecté
            removeBonus(this); // Retirer le bonus de l'affichage (méthode à créer dans Affichage)
            // On peut aussi ici ajouter des points ou d'autres effets, selon le type de bonus
        }
    }
 

    // Méthode de déplacement du bonus attiré par le joueur
    public void attractToPlayer(int playerX, int playerY) {
        // Mouvement simple du bonus vers le joueur
        if (!collected) {
            int dx = playerX - spawnPoint.x;
            int dy = playerY - spawnPoint.y;
            
            // Calcul de l'accélération vers le joueur
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance > 1) {
                dx /= distance;
                dy /= distance;
                spawnPoint.x += dx; // Déplacer le bonus vers le joueur
                spawnPoint.y += dy;
            }
        }
    }

    @Override
    public void run() {
        // Ce thread peut gérer les déplacements animés du bonus si nécessaire
        // Par exemple, faire bouger le bonus progressivement vers le joueur
        while (!collected) {
            try {
                Thread.sleep(10); // Attendre un peu avant de faire un autre mouvement
                // Cette méthode pourrait être appelée régulièrement pour déplacer le bonus
                // Et vérifier les collisions avec le joueur
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}