package Model;

import java.awt.Image;
import java.awt.Point;
import Controler.Character;

import javax.swing.ImageIcon;

public class Fantome extends Ennemies {
    private static final int HEALTH_MAX = 20;

    public static final int weight = 80;
    public static final int height = 50;

    Character c;

    // Image gif de l'araignée
    public static final Image sprite = new ImageIcon(Fantome.class.getResource("/Images/ghost.png")).getImage().getScaledInstance(weight,
    height, Image.SCALE_DEFAULT);

    public Fantome(int speed, int bonusAmount, Point pos, Character c) {
        super(HEALTH_MAX, speed, bonusAmount, pos, sprite);
        this.c = c;
    }
    

    public void goToCharacter() {
        // Récupérer la position actuelle du joueur
        Point playerPosition = new Point((int)(c.getCurrent_x()), (int)(c.getCurrent_y()) );

        // Récupérer la position actuelle du fantôme
        Point ghostPosition = getPosition();

        // Calculer la direction vers le joueur
        int dx = playerPosition.x - ghostPosition.x;
        int dy = playerPosition.y - ghostPosition.y;

        // Calculer la distance entre le joueur et le fantôme
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Éviter la division par zéro
        if (distance > 0) {
            // Normaliser la direction
            double directionX = dx / distance;
            double directionY = dy / distance;

            // Déplacer le fantôme dans la direction du joueur
            ghostPosition.x += directionX * getSpeed();
            ghostPosition.y += directionY * getSpeed();

            // Mettre à jour la position du fantôme
            setPosition(ghostPosition);

            // Log pour déboguer
            System.out.println("Position du fantôme : " + ghostPosition);
            System.out.println("Direction : (" + directionX + ", " + directionY + ")");
        } else {
            // Le fantôme est déjà sur le joueur
            System.out.println("Le fantôme a atteint le joueur !");
        }
    }

    public void startMovement() {
        Thread movementThread = new Thread(() -> {
            while (true) {
                goToCharacter();
                try {
                    Thread.sleep(50); // Attendre 50 ms entre chaque déplacement
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        movementThread.start();
    }
}
