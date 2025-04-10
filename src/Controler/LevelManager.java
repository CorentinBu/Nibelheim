package Controler;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import Model.Araignee;
import Model.Bonus;
import Model.Ennemies;
import Model.Fantome;
import Model.Goules;
import Model.Niveau;
import Model.Obstacles;
import View.Affichage;


// Classe qui gère les différents niveaux du jeu
public class LevelManager extends Thread {
    // Liste des niveaux
    private List<Niveau> niveaux;
    private int currentLevelIndex = 0; // Index du niveau actuel, 0 -> niveau 1
    public static final int DELAY = 50; // Délai entre chaque vérification du niveau

    // Constantes pour les nombres de chaque type d'ennemi
    public static  int pourcentageFantomes = 40; // pourcenage de fantômes dans le niveau
    public static final int pourcentageAraignee = 60; // pourcentage d'araignées dans le niveau
    public static int pourcentageGoules=10;// pourcentage de goules dans le niveau

    // Instance du classe utile
    private Character c; // Instance du personnage
    private Ennemies e; // Instance de l'ennemi
    private Bonus b; // Instance de la classe bonus
    private Obstacles o; // Instance de la classe obstacle

    // Timer et TimerTask pour faire venir les ennemis en vagues
    private Timer timer;
    private TimerTask task ;

        // Generateur d'entier aléartoire
    private static final Random rand = new Random();

    // Getteur pour le niveau actuel
    public Niveau getNiveauActuel() {
        return niveaux.get(currentLevelIndex);
    }

    // Attribut pour savoir si on montre la boutique
    private static boolean showStore = false;

    // Getteur et setteur pour showStore
    public boolean getShowStore() {
        return this.showStore;
    }
    public void goNextStage() {
        this.showStore = false;
        currentLevelIndex++;
        initialiserEnnemis();
        System.out.println("Niveau suivant : " + (currentLevelIndex + 1));
    }

    // Constructeur de la classe LevelManager
    public LevelManager(Character character, Ennemies enemy, Bonus bonus, Obstacles obs) {
        // Initialisation des instances
        this.c = character;
        this.e = enemy;
        this.b = bonus;
        this.o = obs;
        // Initialisation de la liste des niveaux
        niveaux = new ArrayList<>();
        // Ajout des niveaux
        niveaux.add(new Niveau(1, 20, 8, 2, "src/Images/bg1.png", "src/Musique/level1.mp3"));
        niveaux.add(new Niveau(2, 40, 10, 2, "src/Images/bg2.png", "src/Musique/level2.mp3"));
        niveaux.add(new Niveau(3, 100, 12, 3, "src/Images/bg3.png", "src/Musique/level3.mp3"));
        niveaux.add(new Niveau(4, 480, 15, 3, "src/Images/bg3.png", "src/Musique/level4.mp3"));
        niveaux.add(new Niveau(5, 2000, 20, 4, "src/Images/bg3.png", "src/Musique/level5.mp3"));
        initialiserEnnemis();
    }

    // Méthode qui intialise les ennemis selon le niveau actuel
    public void initialiserEnnemis() {
        // Nombre d'obstacles du niveau actuel
        int nombreObstacles = niveaux.get(currentLevelIndex).getNombreObstacles();
        // On recupère le nombre d'ennemis du niveau actuel
        int nombreEnnemis = niveaux.get(currentLevelIndex).getNombreEnnemis();
        //System.out.println("Nombre d'ennemis : " + nombreEnnemis);

        int nombreGoules = 5; // Initialisation du nombre de goules à 0
        //si on est a un niveau supérieur ou égal à 1, on crée les goules
       /* if(getNiveauActuel().getNiveau()>=1){
            pourcentageFantomes=30;
            nombreGoules = (int) (nombreEnnemis * pourcentageGoules / 100);
        }*/

        // On calcule le nombre d'ennemis de chaque type
        int nombreFantomes = (int) (nombreEnnemis * pourcentageFantomes / 100);
        int nombreAraignees = (int) (nombreEnnemis * pourcentageAraignee / 100);
        
        
        // On crée les fantomes avec leurs nombres respectives
        for (int i = 0; i < nombreFantomes; i++) {
            Fantome fantome = new Fantome(c, rand.nextInt(6 - 3 + 1) + 3, 1, Ennemies.genererPositionAleartoire(), b);
        }
        // On crée les araignées avec leurs nombres respectives
        for (int i = 0; i < nombreAraignees; i++) {
            Araignee araignee = new Araignee(c, rand.nextInt(6 - 3 + 1) + 3, 1, Ennemies.genererPositionAleartoire(), b);
        }
        // On crée les goules avec leurs nombres respectives
        for (int i = 0; i < nombreGoules; i++) {
            Goules goule = new Goules(c, rand.nextInt(6 - 3 + 1) + 3, 1, Ennemies.genererPositionAleartoire(), b);
        }

        o.genererObstacle(nombreObstacles); // Générer les obstacles
        lancerVaguesEnnemies(); // Lancer les vagues d'ennemis
    }
    

    // Méthode pour démarer les mouvements des ennemis par vagues
    public void lancerVaguesEnnemies() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                // Niveau actuel
                Niveau niveau = niveaux.get(currentLevelIndex);
                // Liste des ennemis qui ne bougent pas encore (isMoving == false)
                List<Ennemies> ennemisNonDeplaces = new ArrayList<>();
                // On ajoute les ennemis qui ne bougent pas encore à la liste ennemisNonDeplaces
                for (Ennemies ennemi : e.getListEnnemies()) {
                    if (!ennemi.getIsMoving()) {
                        ennemisNonDeplaces.add(ennemi);
                    }
                }

                int n = niveau.getNombreEnnemis() / niveau.getNombreVague(); // nombre d'ennemi pour une vague

                // Vérifier si on a assez d'ennemi pour faire plusieurs vagues
                if(ennemisNonDeplaces.size() >= n ) {
                    // On démarre le mouvement de n ennemis choisis au hasard
                    for (int i = 0; i < n; i++) {
                        // On choisit au hasard un ennemi et on le démarre
                        int randomIndex = rand.nextInt(ennemisNonDeplaces.size());
                        Ennemies ennemi = ennemisNonDeplaces.get(randomIndex);
                        ennemi.startMouvement();
                        // On enlève l'ennemi de la liste pour éviter de le sélectionner à nouveau
                        ennemisNonDeplaces.remove(randomIndex);
                    }
                }
                // Sinon on démarre le mouvement de tous les ennemis restants
                else {
                    for (Ennemies ennemi : ennemisNonDeplaces) {
                        ennemi.startMouvement();
                    }
                }
            
            }
        };
        // Planifier la tâche pour exécuter une vague toutes les 60 secondes
        timer.schedule(task, 0, 15000); // Démarrer toutes les 15 secondes
    }


    // Méthode pour réinitialiser le jeu (par exemple, si le joueur perd)
    public void reinitialiserJeu() {
        // Réinitialiser la partie
        currentLevelIndex = 0;
        // Réinitialiser les ennemis
        e.getListEnnemies().clear();
        initialiserEnnemis();
    }

    // Méthode pour vérifier si le niveau est terminé
    public void checkNiveauFini() {
        // Si le joueur n'a plus de points de vie, on réinitialise le jeu
        if (c.getVie() <= 0) {
            System.out.println("Vous avez perdu !");
            reinitialiserJeu();
            return;
        }
    
        // Si le niveau est terminé (tous les monstres sont tués), on passe au niveau suivant
        else if (e.getListEnnemies().size() == 0) {
            // Vérifier si on est au dernier niveau
            if (currentLevelIndex >= niveaux.size()-1) {
                System.out.println("Bravo vous avez terminé tous les niveaux !");
                return;
            }
            // Sinon on affiche la boutique
            showStore = true;
        }
    }

    // On redéfinie la méthode run pour gérer le niveau actuel
    @Override
    public void run() {
        while (true) {
            checkNiveauFini(); // Vérifier si le niveau est terminé ou perdu
            try {
                Thread.sleep(DELAY); // On attend 50ms avant de vérifier à nouveau
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}