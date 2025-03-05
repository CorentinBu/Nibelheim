package Model;
import java.awt.*;
import java.util.ArrayList;

// Qui gère les différents tirs
public class Tir {

    // Attributs et constantes
    public static final int speed = 10;

    // Liste de balles tirées 
    private ArrayList<Point> tirs;

    // Constructeur pour initialiser la liste de tirs
    public Tir() {
        tirs = new ArrayList<>();
    }
    
    // Getteur pour recuperer les tirs
    public ArrayList<Point> getTirs() {
        return tirs;
    }

    // Methode pour ajouter un tir (tirer une nouvelle balle)
    public void addTir() {
        tirs.add(new Point(1,1));
    }

    // methode pour supprimer les tirs qui sont sortis de la fenetre
    public void removeTir(Point p) {
        tirs.remove(p);
    }

    // Methode pour mettre à jour les tirs et les faire avancer
    public void updateTirs() {
        for (int i = 0; i < tirs.size(); i++) {
            tirs.get(i).x += speed;
            tirs.get(i).y += speed;
            if (tirs.get(i).x > 1920 || tirs.get(i).y > 1080) {
                removeTir(tirs.get(i));
            }
        }
    }
}