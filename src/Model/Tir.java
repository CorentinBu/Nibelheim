package Model;

import java.awt.*;
import java.util.ArrayList;
import Controler.Character;

public class Tir {

    // Attributs et constantes
    public static final int speed = 20;

    // Liste de balles tirées
    private ArrayList<Projectile> tirs;
    ///private ArrayList<Point> directions; // Nouvel attribut pour stocker la direction de chaque tir


    private Point mousePosition; // Position de la souris
    private Character c;  // Le joueur

    // Constructeur pour initialiser la liste de tirs
    public Tir(Character c) {
        this.c = c;
        tirs = new ArrayList<>();
        mousePosition = new Point(0, 0); // Initialiser la position de la souris

        // Démarrer le thread pour mettre à jour la position de la souris
        startMousePositionThread();
    }

    // Getteur pour récupérer les tirs
    public ArrayList<Projectile> getTirs() {
        return tirs;
    }

    // Méthode pour ajouter un tir (tirer une nouvelle balle)
    public void addTir() {
        Point startPoint = new Point((int) (c.getCurrent_x() + 50), (int) (c.getCurrent_y() + 50)); // Point de départ du tir (position du joueur)
        Point direction = new Point(mousePosition.x - startPoint.x, mousePosition.y - startPoint.y); // Direction du tir
        // Créer un nouveau projectile avec la position et la direction
        Projectile projectile = new Projectile(startPoint, direction);
        // Ajouter le projectile à la liste des projectiles
        tirs.add(projectile); 
    }

    // Méthode pour supprimer les tirs qui sont sortis de la fenêtre
    public void removeTir(int index) {
        tirs.remove(index);
    }

    // Méthode pour mettre à jour les tirs et les faire avancer
    public void updateTirs() {
        for (int i = 0; i < tirs.size(); i++) {
            Projectile tir = tirs.get(i);
            Point direction = tir.getDirection(); ///directions.get(i); // Récupérer la direction du tir

            // Normaliser la direction
            double length = Math.sqrt(direction.x * direction.x + direction.y * direction.y);
            double dirX = direction.x / length;
            double dirY = direction.y / length;

            // Déplacer le tir dans la direction de la souris
            tir.setPosition(new Point((int) (tir.getPosition().x + dirX * speed), (int) (tir.getPosition().y + dirY * speed)));
            

            // Vérifier si le tir est sorti de la fenêtre et le supprimer
            if (tir.getPosition().x < 0 || tir.getPosition().x > 1920 || tir.getPosition().y < 0 || tir.getPosition().y > 1080) {
                removeTir(i);
            }


            /// for (int i = 0; i < tirs.size(); i++) {
            ///     Projectile tir = tirs.get(i);
            ///     Point direction = tir.getDirection(); ///directions.get(i); // Récupérer la direction du tir
    
            ///     // Normaliser la direction
            ///     double length = Math.sqrt(direction.x * direction.x + direction.y * direction.y);
            ///     double dirX = direction.x / length;
            ///     double dirY = direction.y / length;
    
            ///     // Avancer le tir dans la direction de la souris
            ///     tir.x += dirX * speed;
            ///     tir.y += dirY * speed;
    
            ///     // Supprimer le tir s'il sort de l'écran
            ///     if (tir.x > 1920 || tir.y > 1080 || tir.x < 0 || tir.y < 0) {
            ///         removeTir(i);
            ///         i--; // Ajuster l'index après suppression
            ///     }
            /// }
            /// 
            /// 
            ///tir.setPosition(new Point((int) (tir.getPosition().x + dirX * speed + c.getVx()), (int) (tir.getPosition().y + dirY * speed + c.getVy())));
        }
    }

    // Méthode pour démarrer le thread de mise à jour de la position de la souris
    private void startMousePositionThread() {
        Thread mousePositionThread = new Thread(() -> {
            while (true) {
                PointerInfo mouseInfo = MouseInfo.getPointerInfo();
                if (mouseInfo != null) {
                    Point mousePoint = mouseInfo.getLocation();
                    mousePosition.setLocation(mousePoint);
                }

                try {
                    Thread.sleep(50); // Délai de mise à jour de la position de la souris
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        mousePositionThread.start();
    }
}