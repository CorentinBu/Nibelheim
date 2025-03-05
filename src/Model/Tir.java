package Model;

import java.awt.*;
import java.util.ArrayList;
import Controler.Character;

public class Tir {

    // Attributs et constantes
    public static final int speed = 20;

    // Liste de balles tirées
    private ArrayList<Point> tirs;
    private ArrayList<Point> directions; // Nouvel attribut pour stocker la direction de chaque tir
    Character c;

    // Constructeur pour initialiser la liste de tirs
    public Tir(Character c) {
        this.c = c;
        tirs = new ArrayList<>();
        directions = new ArrayList<>(); // Initialiser la liste des directions
    }

    // Getteur pour recuperer les tirs
    public ArrayList<Point> getTirs() {
        return tirs;
    }

    // Methode pour ajouter un tir (tirer une nouvelle balle)
    public void addTir(int mouseX, int mouseY) {
        Point startPoint = new Point(c.current_x + 50, c.current_y + 50); // Point de départ du tir
        Point direction = new Point(mouseX - startPoint.x, mouseY - startPoint.y); // Direction du tir

        tirs.add(startPoint);
        directions.add(direction);
    }

    // methode pour supprimer les tirs qui sont sortis de la fenetre
    public void removeTir(int index) {
        tirs.remove(index);
        directions.remove(index);
    }

    // Methode pour mettre à jour les tirs et les faire avancer
    public void updateTirs() {
        for (int i = 0; i < tirs.size(); i++) {
            Point tir = tirs.get(i);
            Point direction = directions.get(i);

            // Normaliser la direction
            double length = Math.sqrt(direction.x * direction.x + direction.y * direction.y);
            double dirX = direction.x / length;
            double dirY = direction.y / length;

            // Avancer le tir dans la direction de la souris
            tir.x += dirX * speed;
            tir.y += dirY * speed;

            // Supprimer le tir s'il sort de l'écran
            if (tir.x > 1920 || tir.y > 1080 || tir.x < 0 || tir.y < 0) {
                removeTir(i);
                i--; // Ajuster l'index après suppression
            }
        }
    }
}