package Model;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;

import Controler.Character;

public class Fantome extends Ennemies {

    // Points de vie de l'ennemie
    private static final int HEALTH_MAX = 10;

    // Taille du sprite du fantôme
    public static final int weight = 80;
    public static final int height = 50;

    // Timer pour ajouter les ennemis à intervalles réguliers
    private Timer timer;
    private TimerTask task;

    // Classe Character
    Character c;

    // Liste des postions possibles pour le fantôme
    private ArrayList<Point> ListPositions = new ArrayList<Point>();

    // Liste des fantômes
    public static CopyOnWriteArrayList<Fantome> ListFantomes = new CopyOnWriteArrayList<>();

    // Image de l'ennemie
    public static final Image sprite = new ImageIcon("src/Images/ghost.png").getImage().getScaledInstance(weight,
            height, Image.SCALE_DEFAULT);

    // Constructeur
    public Fantome(Character c, int speed, int bonusAmount, Point pos) {
        super(c, HEALTH_MAX, speed, bonusAmount, pos, sprite);
        this.c = c;
        ListPositions.add(new Point(-50, -50));
        ListPositions.add(new Point(-50, 520));
        ListPositions.add(new Point(-50, 1080));
    }

    // Getteurs pour la liste des fantômes
    public static CopyOnWriteArrayList<Fantome> getListFantomes() {
        return ListFantomes;
    }

    // Methode pour créer les fantômes à intervalle regulier
    public void createFantomes(int nombreFantomes) {
        // On crée un timer pour ajouter 1/4 des fantômes à intervalles réguliers (toutes les 1 minute)
        timer = new Timer();
        task = new TimerTask() {
         // Ajouter 1/4 de nombreFamtomes
            int count = 0;

            @Override
            public void run() {
                if (count < nombreFantomes) {
                    // Créer un fantôme à une position aléatoire
                    Point randomPosition = ListPositions.get((int) (Math.random() * ListPositions.size()));
                    Fantome f = new Fantome(c, 5, 0, randomPosition);
                    f.setPosition(randomPosition);
                    f.startMovement();
                    count++;
                } else {
                    timer.cancel(); // Arrêter le timer une fois tous les fantômes créés
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 3000); // Démarrer le timer toutes les secondes

        
    }

    // Méthode pour déplacer le fantôme vers le joueur
    public void goToCharacter() {
        // Récupérer la position actuelle du joueur
        Point playerPosition = new Point((int)c.getCurrent_x(), (int)c.getCurrent_y());

        // Mettre à jour la hitbox de l'ennemie
        this.hitboxEnnemie.x=this.position.x;
        this.hitboxEnnemie.y=this.position.y;

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
        } else {
            // Le fantôme est déjà sur le joueur
            // System.out.println("Le fantôme a atteint le joueur !");
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
