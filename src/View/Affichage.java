package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Controler.Character;
import Controler.Inputs;
import Controler.LevelManager;
import Model.Tir;
import Model.Obstacles;
import Model.Araignee;
import Model.Bonus;
import Model.Bouton;
import Model.ComboBonus;
import Model.Ennemies;
import Model.Fantome;

public class Affichage extends JPanel {

    // Dimensions de la vue, statiques
    public static final int X = 1920;
    public static final int Y = 1080;

    // // Dimmension de la fenetre de bonus
    // public static final int WIDTH_B = 900;


    // Buttons du jeu
    // Bouton pour relancer la partie si on a perdu
    private Bouton relancerButton = new Bouton("Nouvelle Partie",X / 2 - 100, Y / 2 - 30, 200, 50, new Color(169, 169, 169), Color.WHITE, new Color(70, 130, 180), null,18);
    // Bouton pour quitter le jeu
    private Bouton acceuil = new Bouton("Acceuil",X / 2 - 100, Y / 2 + 100, 200, 50, new Color(169, 169, 169), Color.WHITE, new Color(70, 130, 180), null,18);  
    // Bouton pour démarrer le jeu
    private Bouton startGame = new Bouton("Commencer une partie", 30, Y - 180, 300, 55, new Color(169, 169, 169), Color.WHITE, new Color(70, 130, 180), "src/Images/start_bouton.png",18);
    // Bouton pour passer au niveau suivant
    private Bouton nextStageBtn = new Bouton("Etage suivant", X / 2 + 230, Y / 2 + 285, 180, 40, new Color(169, 169, 169), Color.WHITE, new Color(70, 130, 180), null,18); 
    // Bouton pour quitter le jeu
    private Bouton quitter = new Bouton("Quitter",X - 150, 30, 100, 35, new Color(255, 0, 0), Color.WHITE, new Color(255, 0, 0), null,18);
    // Bouton pour accéder à la boutique
    private Bouton boutique = new Bouton("Boutique",X - 320, 30, 140, 35, new Color(169, 169, 169), Color.WHITE, new Color(70, 130, 180), null,18);
    // Bouton pour mettre le jeu en pause
    private Bouton pauseButton;
    // Bouton pour mettre le jeu en play
    private Bouton resumeButton;
    // Bouton pour acheter les combos de types 1
    private Bouton buyCombo1 = new Bouton("Buy 50p", X / 2 - 225, Y / 2 - 95, 90, 22, new Color(96,96,96), Color.WHITE, new Color(70, 130, 180), null,14);
    // Bouton pour acheter les combos de types 2
    private Bouton buyCombo2 = new Bouton("Buy 75p", X / 2 - 50, Y / 2 - 95, 90, 22, new Color(96,96,96), Color.WHITE, new Color(70, 130, 180), null,14);
    // Bouton pour acheter les combos de types 3
    private Bouton buyCombo3 = new Bouton("Buy 90p", X / 2 + 125, Y / 2 - 95, 90, 22, new Color(96,96,96), Color.WHITE, new Color(70, 130, 180), null,14);

    // Attribut pour lancer le jeu ou le mettre en pause
    public boolean game_running = false;
    // Attribut pour savoir si le jeu est perdu
    public boolean game_lose = false;
    // Attribut pour savoir si le jeu est en pause
    public boolean game_pause = false;
    
    
    // Instances de classe utiles
    private Bonus b;
    Character c;
    private Tir tir;
    Inputs i;
    private Obstacles o;
    private LevelManager lm;
    
    // Charger l'mage pour les obstacles (caisse.png)
    public Image imgObstacle = new ImageIcon("src/Images/caisse.png").getImage()
        .getScaledInstance(o.WIDTH_O, o.HEIGHT_O,Image.SCALE_DEFAULT);

    // Charger l'image du coin (ici on suppose qu'elle s'appelle "coin.png")
    public Image coinImage = new ImageIcon("src/Images/coin.png").getImage()
    .getScaledInstance(b.WIDTH_B, b.HEIGHT_B, Image.SCALE_DEFAULT);

    // Position de la barre de vie
    public static final int xBarreVie = 30;
    public static final int yBarreVie = 30;

    // Dimension barre de vie
    public static final int heightBarreVie = 20;  // Hauteur
    public static final int arcBarreVie = 10;  // Angle des bordures

    // Getteurs pour game_running, game_pause et game_lose
    public boolean getGame_running(){
        return game_running;
    }
    public boolean getGame_lose(){
        return game_lose;
    }
    public boolean getGame_pause(){
        return game_pause;
    } 

    // Constructeur de la classe Affichage

    public Affichage(Character character, Tir t, Bonus bonus, Inputs inputs, Obstacles obs, LevelManager levelManager) {
        setLayout(null); // Désactiver le LayoutManager
        setPreferredSize(new Dimension(X, Y));
        this.c = character;
        this.o = obs;
        this.tir = t;
        this.b = bonus;
        this.i = inputs;
        this.lm = levelManager;

        // -------------------------*   Gestion des boutons   *--------------------------------------------------------------    


        // Actions des boutons

        // Redémarrer la partie
        relancerButton.addActionListener(e -> {
             // Mettre à jour les états du jeu
            game_lose = false;
            game_running = true;
            // Réinitialiser le personnage
            inputs.resetKeys();
            c.restartPlayer(); // Réinitialisez le personnage existant
            c.resetInput(inputs);  // Réinitialiser les entrées clavier
            this.addKeyListener(inputs);  // Reajouter le KeyListener pour les entrées clavier
            this.setFocusable(true);  // Rendre le panel focusable pour les entrées clavier
            // Réinitialiser les autres éléments du jeu
            b.resetBonus();  // Réinitialiser les bonus
            t.resetTirs();  // Réinitialiser les tirs

        }); 

        // Retour à l'acceuil
        acceuil.addActionListener(e -> { 
            game_lose = false;
            game_running = false;
            // Rendre les boutons invisibles lorsqu'on clique sur acceuil
            relancerButton.setVisible(false);
            acceuil.setVisible(false);
            boutique.setVisible(false);
            repaint(); // Redessiner l'interface pour appliquer les changements
        });

        // Quitter le jeu
        quitter.addActionListener(e -> System.exit(0));

        // Lancer une nouvelle partie
        startGame.addActionListener(e -> {
            // Mettre à jour les états du jeu
            game_lose = false;
            game_running = true;
            // Réinitialiser le personnage
            inputs.resetKeys();
            c.restartPlayer(); // Réinitialisez le personnage existant
            c.resetInput(inputs);  // Réinitialiser les entrées clavier
            this.addKeyListener(inputs);  // Reajouter le KeyListener pour les entrées clavier
            this.setFocusable(true);  // Rendre le panel focusable pour les entrées clavier
            // Réinitialiser les autres éléments du jeu
            b.resetBonus();  // Réinitialiser les bonus
            t.resetTirs();  // Réinitialiser les tirs
            b.resetBonus(); // Réinitialiser les bonus
        });

        // Passer à l'étage suivant
        nextStageBtn.addActionListener(e -> {
            lm.goNextStage();
        });

        // Acceder à la boutique
        boutique.addActionListener(e -> {
            // action du bouton ici
        });

        // Rendre les boutons invisibles par défaut
        relancerButton.setVisible(false);
        acceuil.setVisible(false);
        startGame.setVisible(false);
        nextStageBtn.setVisible(false);
        quitter.setVisible(false);
        boutique.setVisible(false);
        buyCombo1.setVisible(false);
        buyCombo2.setVisible(false);
        buyCombo3.setVisible(false);

        // Acheter un bonus de type 1 ()
        buyCombo1.addActionListener(e -> {
            c.addComboBonus(new ComboBonus(50, 1)); // Acheter le bonus de type 1
        });

        // Acheter un bonus de type 2
        buyCombo2.addActionListener(e -> {
            c.addComboBonus(new ComboBonus(75, 2)); // Acheter le bonus de type 2
        });

        // Acheter un bonus de type 3
        buyCombo3.addActionListener(e -> {
            c.addComboBonus(new ComboBonus(90, 3)); // Acheter le bonus de type 3
        });


        // Ajouter les boutons au panel (sans les afficher directement)
        add(relancerButton);
        add(acceuil);
        add(startGame);
        add(nextStageBtn);
        add(boutique);
        add(quitter);
        add(buyCombo1);
        add(buyCombo2);
        add(buyCombo3);

    }

// ----------------- On redefinie la méthode paint pour ajouter les elements graphiques  ------------------------------

    // Redessiner la vue pour ajouter nos différents éléments
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Ajouter un fond d'ecran pour le jeu
        String lien_bg = lm.getNiveauActuel().getImage_arriere_plan();
        Image bg = new ImageIcon(lien_bg).getImage();
        g.drawImage(bg, 0, 0, X, Y, null);

        // Si le joueur n'a plus de points de vie, le jeu est perdu
        if (c.getVie() <= 0) {
            game_lose = true;
            c.pauseGame();
        }

        // Si le jeu est lancé, on affiche les éléments graphiques sinon on affiche un bouton pour lancer le jeu
        if (!game_running) {
            // Afficher un écran d'acceuil
            drawAcceuil(g);
        } 
        
        else {
            if (!game_lose) {   // Si le jeu n'est pas perdu, on affiche les éléments graphiques

                // Dessiner le personnage : la sorcière
                g.drawImage(Character.characterSprite, (int) c.getCurrent_x(), (int) c.getCurrent_y(), null);
                //g.drawRect((int)c.getCurrent_x(), (int)c.getCurrent_y(), Character.WIDTH, Character.HEIGHT);

                // Dessiner les tirs
                drawTirs(g);

                // Dessiner les bonus
                drawBonus(g);

                // Dessiner les ennemis
                drawEnnemies(g);

                // Dessiner les obstacles
                drawObstacle(g);
               
                // Dessiner la barre de vie
                drawBarreVie(g);

                // Afficher le niveau de jeu
                drawNiveau(g);

                // Rendre les boutons pour relancer et quitter invisibles
                relancerButton.setVisible(false);
                acceuil.setVisible(false);
                nextStageBtn.setVisible(false);
            } 
            else {
                // Si le jeu est perdu, afficher un écran de fin de partie avec game over
                drawGameStop(g);

            }

            // On affiche la boutique après chaque victoire
            if(lm.getShowStore() == true) {
                drawBoutique(g);
                nextStageBtn.setVisible(true); // Afficher le bouton pour passer au niveau suivant
            }
            
            // Rendre les boutons startGame, boutique et quitter invisible car le jeu a démarré
            startGame.setVisible(false);
            boutique.setVisible(false);
            quitter.setVisible(false);

            // Dessiner la boutique en cas de victoire
            drawBoutique(g);
        }

    }

// -----------------  Les méthodes pour dessiner les éléments de la vue -------------------------------------    


    // Méthode pour dessiner et afficher la boutique
    public void drawBoutique (Graphics g) {
        if(lm.getShowStore() == false) {
            // Un grand carré de 900*700 au centre de la fenetre représente la boutique
            g.setColor(new Color(220, 220, 220, 200)); // Couleur de fond de la boutique
            g.fillRect(X / 2 - 450, Y / 2 - 350, 900, 700); // Fond de la boutique
            g.setColor(Color.BLACK); // Couleur du contour
            g.drawRect(X / 2 - 450, Y / 2 - 350, 900, 700); // Contour de la boutique

            // Contenu de la boutique ici
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 20)); // Police de la boutique
            g.setColor(Color.BLACK); // Couleur du texte
            g.drawString("Victoire ! Etage " + lm.getNiveauActuel().getNiveau() + " réussi ✅", X / 2 - 115, Y / 2 - 300); // Titre de la boutique

            // dessiner 3 cercles alignés horizontalement qui representent les bonus qui peuvent etre achetés
            g.setColor(Color.YELLOW); // Couleur des cercles
            g.fillOval(X / 2 - 225, Y / 2 - 200, 80, 80); // Cercle 1
            g.fillOval(X / 2 - 50, Y / 2 - 200, 80, 80); // Cercle 2
            g.fillOval(X / 2 + 125, Y / 2 - 200, 80, 80); // Cercle 3

            

            // Afficher le nombre de pièces en bas à gauche
            g.drawImage(coinImage, X / 2 + 225, Y / 2 + 400, null); // Afficher l'image du bonus
            g.setColor(Color.BLACK); // Couleur du texte
            g.setFont(new Font("Arial", Font.PLAIN, 15)); // Police de texte
            g.drawString("Double tir", X / 2 - 220, Y / 2 - 105); // Texte du bonus 1
            g.drawString("Vitesse x2", X / 2 - 45, Y / 2 - 105); // Texte du bonus 2
            g.drawString("Vie pleine", X / 2 + 130, Y / 2 - 105); // Texte du bonus 3

            // Afficher le nombre de bonus collectés en bas à gauche
            g.setColor(Color.BLACK); // Couleur du texte
            g.setFont(new Font("Arial", Font.PLAIN, 15)); // Police de texte
            g.drawString("Pièces : " + c.getNombreBonus(), X / 2 + 300, Y / 2 + 400); // Texte du bonus 3
           

            nextStageBtn.setVisible(true); // Afficher le bouton pour passer au niveau suivant
            // Afficher les boutons d'achat des combos
            buyCombo1.setVisible(true); // Afficher le bouton d'achat du combo 1
            buyCombo2.setVisible(true); // Afficher le bouton d'achat du combo 2
            buyCombo3.setVisible(true); // Afficher le bouton d'achat du combo 3
        }
        else {
            nextStageBtn.setVisible(false);
            buyCombo1.setVisible(false);
            buyCombo2.setVisible(false);
            buyCombo3.setVisible(false);
        }

    }

    // Méthode pour dessiner les bonus avec l'image coin.png
    public void drawBonus(Graphics g) {
        for (int i = 0; i < b.getPointBonus().size(); i++) {
            g.drawImage(coinImage, b.getPointBonus().get(i).x, b.getPointBonus().get(i).y, null);
            //g.drawRect(b.getPointBonus().get(i).x, b.getPointBonus().get(i).y, Bonus.WIDTH_B, Bonus.HEIGHT_B);
        }
    }

    // Methode pour Récupérer la liste des tirs et les afficher à l'écran en tenant compte de la classe Tir
    public void drawTirs(Graphics g) {
        for (int i = 0; i < tir.getTirs().size(); i++) {
            g.setColor(Color.RED);
            int x = tir.getTirs().get(i).getPosition().x;
            int y = tir.getTirs().get(i).getPosition().y;
            g.fillOval(x, y, 8, 8);
            // g.drawRect(tir.getTirs().get(i).getPosition().x, tir.getTirs().get(i).getPosition().y, 8, 8);
        }
    }

    // Méthode affichant tous les ennemis
    public void drawEnnemies(Graphics g) {
        // Appel de la méthode statique sans instance
        List<Ennemies> ennemies = Ennemies.getListEnnemies();
        // Boucle affichant tous les ennemis
        for (Ennemies ennemi : ennemies) {
            // Vérification si l'ennemi est un Fantome
            if (ennemi instanceof Fantome) {
                Fantome fantome = (Fantome) ennemi; // Casting en Fantome
                g.drawImage(fantome.img, (int) fantome.getPosition().getX(), (int) fantome.getPosition().getY(), null);
                //g.drawRect((int) ennemi.getPosition().getX(), (int) ennemi.getPosition().getY(), ennemi.getWidth(ennemi), ennemi.getHeight(ennemi));
            }
            if (ennemi instanceof Araignee) {
                Araignee araignee = (Araignee) ennemi; // Casting en Fantome
                g.drawImage(araignee.img, (int) araignee.getPosition().getX(), (int) araignee.getPosition().getY(), null);
                //g.drawRect((int) ennemi.getPosition().getX(), (int) ennemi.getPosition().getY(), ennemi.getWidth(ennemi), ennemi.getHeight(ennemi));
            }
        }
    }
    
    // methode qui dessine les obstacles
    public void drawObstacle(Graphics g){
        int i = 0;
        ArrayList<Point> obstacles = o.getObstacles();
        for(Point obstacle : obstacles){
        // Afficher les images de caisse
        g.drawImage(imgObstacle,(int)obstacle.getX(),(int)obstacle.getY(), null);
        i+=1;
        }
    }

    // Méthode pour dessiner le niveau actuel
    public void drawNiveau(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Etage actuel : ", X - 450, 43);
        // Dessiner les 5 petits carrés avec des numéros
        for (int i = 0; i < 5; i++) {
            int x = X - 355 + (i * 35);
            g.drawRect(x, 30, 20, 20);
            // Remplir les carrés correspondant au niveau actuel
            if (i < lm.getNiveauActuel().getNiveau()) {
                g.setColor(new Color(255, 165, 80));
                g.fillRect(x, 30, 20, 20);
                g.setColor(Color.BLACK);
            }
            // Dessiner le numéro au centre du carré
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.drawString(String.valueOf(i + 1), x + 6, 45); // Ajuster la position pour centrer le numéro
        }
    }

    // Methode pour dessiner la page d'acceuil
    public void drawAcceuil(Graphics g) {
        // Ajouter l'image un fond d'ecran pour l'acceuil
        Image bg = new ImageIcon("src/Images/acceuil.jpg").getImage();
        g.drawImage(bg, 0, 0, X, Y, null);
        // Afficher les boutons de la page d'acceuil
        startGame.setVisible(true);
        boutique.setVisible(true);
        quitter.setVisible(true);
        // Afficher un message de bienvenue avec un contour rouge stylé
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
        g.setColor(Color.RED);
        g.drawString("Bienvenue sur Nibelhein", X / 2 - 400 - 2, Y / 2 - 2);
        g.setColor(Color.GREEN);
        g.drawString("Bienvenue sur Nibelhein", X / 2 - 400, Y / 2);  // Position exacte
    }

    // Methode pour dessiner la barre de vie et les bonus collectés
    public void drawBarreVie(Graphics g) {
        // Dessiner une barre de vie rouge dans un contour noir et des bordures arrondies
        g.setColor(Color.GRAY);
        g.fillRoundRect(xBarreVie, yBarreVie, c.maxVie * 40, heightBarreVie, arcBarreVie, arcBarreVie);
        g.setColor(Color.RED);
        g.fillRoundRect(xBarreVie, yBarreVie, c.getVie() * 40, heightBarreVie, arcBarreVie, arcBarreVie);
        g.setColor(Color.BLACK);
        g.drawRoundRect(xBarreVie, yBarreVie, c.maxVie * 40, heightBarreVie, arcBarreVie, arcBarreVie);
        // Afficher le nombre de bonus récupérés en haut à droite
        g.drawImage(coinImage, X - 135, 25, null);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Pièces : " + c.getNombreBonus(), X - 98, 43);
    }

    // Méthode pour afficher le gamelose et la page de pause selon le cas
    public void drawGameStop(Graphics g) {
        // Rendre les boutons visibles
        if (game_lose == true && game_running == true) {
            relancerButton.setVisible(true);
            acceuil.setVisible(true);
            nextStageBtn.setVisible(true);
            // Afficher un pop-up avec un message de game over
            g.setColor(Color.BLACK);
            g.fillRect(X / 2 - 252, Y / 2 - 252, 504, 504); // Cadre noir (plus grand)
            g.setColor(Color.WHITE);
            g.fillRect(X / 2 - 250, Y / 2 - 250, 500, 500); // Fond du pop-up
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 35));
            g.drawString("Game Over", X / 2 - 90, Y / 2 - 180);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Nombre de pièces collectées : " + c.getNombreBonus(), X / 2 - 150, Y / 2 - 120);

        }

        else {
            relancerButton.setVisible(false);
            acceuil.setVisible(false);
            nextStageBtn.setVisible(false);
        }
    }

}
