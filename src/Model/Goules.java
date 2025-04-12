package Model;

import java.awt.Rectangle;

import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

public class Goules extends Ennemies {
    // Points de vie de l'ennemie
    private static final int HEALTH_MAX = 3;

    // Taille du sprite du goule
    public static final int width = 52;
    public static final int height = 64;

    // Taille de la fenêtre de jeu pour gerer la position de l'ennemi
    private static final int WIDTH_fenetre = 1920;
    private static final int HEIGHT_fenetre = 1080;

    // Classe Character
    Character c;

    // distance max entre le joueur et la goule pour qu'elle tire
    private final int DISTANCE_MAX_X = 300;
    private static final int DISTANCE_MAX_Y = 300;

    public Projectile projectile = null;

    // Image de l'ennemie
    public static final Image sprite = new ImageIcon("src/Images/goule.gif").getImage().getScaledInstance(width,
            height, Image.SCALE_DEFAULT);

    // constructeur
    public Goules(Character c, int speed, int bonusAmount, Point pos, Bonus b) {
        super(c, HEALTH_MAX, speed, width, height, bonusAmount, pos, sprite, b);
        this.c = c;
    }

    // Méthode pour déplacer la goule vers le joueur mais a une certaine distance
    public void goToCharacter() {
        // on recupere la position du joueur
        Point playerPosition = new Point((int) c.getCurrent_x(), (int) c.getCurrent_y());

        // on recupere la position de la goule
        Point goulePosition = getPosition();
        // System.out.println("Position de la goule : " + goulePosition.x + " " +
        // goulePosition.y);

        // on met a jour la hitbox de l'ennemie
        this.hitboxEnnemie.x = this.position.x;
        this.hitboxEnnemie.y = this.position.y;

        // on calcule la direction entre le joueur et la goule
        int dx = playerPosition.x - goulePosition.x;
        int dy = playerPosition.y - goulePosition.y;

        // on calcule la distance entre le joueur et la goule
        double distance = Math.sqrt(dx * dx + dy * dy);
        // on evite la division par zero

        if (distance > 0) {
            // on normalise la direction
            double directionX = dx / distance;
            double directionY = dy / distance;
            // on verifie si la distance entre le joueur et la goule est superieur ou égale
            // a DISTANCE_MAX_X et DISTANCE_MAX_Y
            if (distance >= DISTANCE_MAX_X && distance >= DISTANCE_MAX_Y) {
                // System.out.println("Goule en mouvement vers le joueur !");
                // on deplace la goule dans la direction du joueur
                goulePosition.x += directionX * getSpeed();
                goulePosition.y += directionY * getSpeed();
                // on met a jour la position de la goule
                setPosition(goulePosition);
            } else {
                TirGoule(goulePosition, directionX, directionY);
            }
        }

    }

    /*
     * public void startShooting() {
     * Thread shootingThread = new Thread(() -> {
     * while (true) {
     * try {
     * Thread.sleep(5000); // 5 secondes entre chaque tir
     * } catch (InterruptedException e) {
     * e.printStackTrace();
     * }
     * 
     * Point playerPosition = new Point((int) c.getCurrent_x(), (int)
     * c.getCurrent_y());
     * Point goulePosition = getPosition();
     * 
     * int dx = playerPosition.x - goulePosition.x;
     * int dy = playerPosition.y - goulePosition.y;
     * 
     * double distance = Math.sqrt(dx * dx + dy * dy);
     * 
     * if (distance < DISTANCE_MAX_X && distance < DISTANCE_MAX_Y) {
     * double directionX = dx / distance;
     * double directionY = dy / distance;
     * 
     * TirGoule(goulePosition, directionX, directionY);
     * }
     * }
     * });
     * 
     * shootingThread.start();
     * }
     */

    // Méthode pour tirer un projectile
    public void TirGoule(Point goulePosition, double directionX, double directionY) {
        // on verifie si le projectile est null
        if (projectile == null) {
            // on crée un nouveau projectile
            projectile = new Projectile(new Point(goulePosition.x, goulePosition.y),
                    new Point((int) directionX, (int) directionY));
            // on met a jour la hitbox du projectile
            projectile.hitboxProjectile.x = projectile.getPosition().x;
            projectile.hitboxProjectile.y = projectile.getPosition().y;
        } else {
            // System.out.println("Projectile en mouvement !");
            // onverfie si il y a collision entre le projectile et le joueur
            collisionProjectile();
            // on deplace le projectile

            // System.out.println("Position du projectile : " + projectile.getPosition().x +
            // " " + projectile.getPosition().y);
            double moveX = directionX * 10;
            double moveY = directionY * 10;

            int newX = projectile.getPosition().x + (int) Math.round(moveX);
            int newY = projectile.getPosition().y + (int) Math.round(moveY);

            projectile.setPosition(new Point(newX, newY));

            System.out.println(
                    "Position du projectile : " + projectile.getPosition().x + " " + projectile.getPosition().y);
            // on met a jour la hitbox du projectile
            projectile.hitboxProjectile.x = projectile.getPosition().x;
            projectile.hitboxProjectile.y = projectile.getPosition().y;
        }
    }

    // Méthode pour gerer la collision entre le projectile et le joueur
    public void collisionProjectile() {
        // on verifie si le projectile n'est pas null
        if (projectile != null) {
            // on verifie si le projectile touche le joueur
            if (projectile.hitboxProjectile.intersects(c.hitboxC)) {
                // on retire de la vie au joueur
                c.setVie(c.getVie() - 1);
                // on retire le projectile
                projectile.setPosition(getPosition());
            }
        }
    }

    // Thread pour démarrer le déplacement de l'ennemie
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
