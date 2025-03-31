package Controler;

import java.util.ArrayList;
import java.util.List;

import Model.Ennemies;
import Model.Fantome;
import Model.Niveau;


// Classe qui gère les différents niveaux du jeu
public class LevelManager extends Thread {
    // Liste des niveaux
    private List<Niveau> niveaux;
    private int currentLevelIndex = 0; // Index du niveau actuel
    public static final int DELAY = 50; // Délai entre chaque vérification du niveau

    // Constantes pour les nombres de chaque type d'ennemi
    public static final int pourcentageFantomes = 20; // pourcenage de fantômes dans le niveau
    public static final int pourcentageAraignee = 40; // pourcentage d'araignées dans le niveau

    // Instance du classe utile
    private Character c; // Instance du personnage
    private Fantome f; // Instance du fantome

    // Constructeur de la classe LevelManager
    public LevelManager(Character character, Fantome ghost) {
        // Initialisation des instances
        this.c = character;
        this.f = ghost;
        // Initialisation de la liste des niveaux
        niveaux = new ArrayList<>();
        // Ajout des niveaux
        niveaux.add(new Niveau(1, 5, 8, "src/Images/level1.png", "src/Musique/level1.mp3"));
        niveaux.add(new Niveau(2, 75, 10, "src/Images/level2.png", "src/Musique/level2.mp3"));
        niveaux.add(new Niveau(3, 100, 12, "src/Images/level3.png", "src/Musique/level3.mp3"));
        niveaux.add(new Niveau(4, 175, 15, "src/Images/level4.png", "src/Musique/level4.mp3"));
        niveaux.add(new Niveau(5, 300, 20, "src/Images/level5.png", "src/Musique/level5.mp3"));
        initialiserEnnemis();
    }

    // getteur pour le niveau actuel
    public Niveau getNiveauActuel() {
        return niveaux.get(currentLevelIndex);
    }

    // Méthode qui intialise les ennemis selon le niveau
    public void initialiserEnnemis() {
        // On recupère le nombre d'ennemis du niveau actuel
        int nombreEnnemis = niveaux.get(currentLevelIndex).getNombreEnnemis();
        System.out.println("Nombre d'ennemis : " + nombreEnnemis);

        // On calcule le nombre d'ennemis de chaque type
        int nombreFantomes = (int) (nombreEnnemis * pourcentageFantomes / 100);
        int nombreAraignees = (int) (nombreEnnemis * pourcentageAraignee / 100);

        // On crée les ennemis avec leurs méthodes respectives
        f.createFantomes(nombreFantomes);
        // a.createAraignée(nombreAraignees);

    }

    // Méthode pour réinitialiser le jeu ()
    public void reinitialiserJeu() {
        // Réinitialiser le niveau actuel
        currentLevelIndex = 1;
        // Réinitialiser le personnage
        c.restartPlayer();
    }

    // Méthode pour vérifier si le niveau est terminé
    public void checkNiveauFini() {
        // Si le joueur n'a plus de points de vie on arrète le jeu
        if (c.getVie() <= 0) {
            reinitialiserJeu();
            System.out.println("Vous avez perdu !");
            return;
        }
        // Si le niveau est terminé (si tous les monstres sont tués) on passe au niveau suivant
        else if (f.getListFantomes().size() == 0) {
            // On passe au niveau suivant
            currentLevelIndex++;
            if (currentLevelIndex > niveaux.size()) {
                // Si tous les niveaux sont terminés, on affiche un message de victoire
                System.out.println("Bravo vous avez terminé tous les niveaux !");
            }
        }
        
    }

    // On redéfinie la méthode run pour gérer le niveau actuel
    @Override
    public void run() {
        while (true) {
            checkNiveauFini();
            try {
                Thread.sleep(DELAY); // On attend 50ms avant de vérifier à nouveau
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // je veux rajouter les ennemis par vague (4 vagues après chaque 1 minute de jeu)

    }




}