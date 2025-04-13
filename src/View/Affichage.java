package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import Controler.Inputs;
import Controler.LevelManager;
import Model.Tir;
import Model.Obstacles;
import Model.Araignee;
import Model.Bonus;
import Model.Bouton;
import Model.Character;
import Model.ComboBonus;
import Model.Dimensions;
import Model.Ennemis;
import Model.Fantome;
import Model.Goules;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Affichage extends JPanel {

    // Attributs de la classe
    private static int originalWidth = Dimensions.getReferenceWidth();
    private static int originalHeight = Dimensions.getReferenceHeight();

    // Position et dimension de la barre de vie
    public static final int xBarreVie = 30;
    public static final int yBarreVie = 30;
    public static final int heightBarreVie = 20;
    public static final int arcBarreVie = 10;

    // Positions et dimensions de base des boutons
    private static final Point POS_RELANCER = new Point(originalWidth / 2 - 100, originalHeight / 2 - 30);
    private static final Dimension SIZE_RELANCER = new Dimension(200, 50);
    private static final Point POS_ACCEUIL = new Point(originalWidth / 2 - 100, originalHeight / 2 + 50);
    private static final Dimension SIZE_ACCEUIL = new Dimension(200, 50);
    private static final Point POS_START = new Point(originalWidth / 2 - 200, originalHeight / 2 + 50);
    private static final Dimension SIZE_START = new Dimension(300, 55);
    private static final Point POS_NEXT_STAGE = new Point(originalWidth / 2 + 145, originalHeight / 2 + 285);
    private static final Dimension SIZE_NEXT_STAGE = new Dimension(180, 40);
    private static final Point POS_QUITTER = new Point(originalWidth - 150, 30);
    private static final Dimension SIZE_QUITTER = new Dimension(100, 35);
    private static final Point POS_COMBO1 = new Point(originalWidth / 2 - 225, originalHeight / 2 - 95);
    private static final Dimension SIZE_COMBO1 = new Dimension(90, 22);
    private static final Point POS_COMBO2 = new Point(originalWidth / 2 - 50, originalHeight / 2 - 95);
    private static final Dimension SIZE_COMBO2 = new Dimension(90, 22);
    private static final Point POS_COMBO3 = new Point(originalWidth / 2 + 125, originalHeight / 2 - 95);
    private static final Dimension SIZE_COMBO3 = new Dimension(90, 22);

    // Taille de base de certains textes de boutons
    private static final int TEXT_SIZE1 = 18;
    private static final int TEXT_SIZE2 = 14;


    // Créations des boutons du jeu
    private Bouton relancerButton = new Bouton("Nouvelle Partie", POS_RELANCER.x, POS_RELANCER.y, SIZE_RELANCER.width, SIZE_RELANCER.height,
        new Color(169, 169, 169), Color.WHITE, new Color(70, 130, 180), null, TEXT_SIZE1);
    private Bouton acceuil = new Bouton("Accueil", POS_ACCEUIL.x, POS_ACCEUIL.y, SIZE_ACCEUIL.width, SIZE_ACCEUIL.height,
        new Color(169, 169, 169), Color.WHITE, new Color(70, 130, 180), null, TEXT_SIZE1);
    private Bouton startGame = new Bouton("Commencer une partie", POS_START.x, POS_START.y, SIZE_START.width, SIZE_START.height,
        new Color(169, 169, 169), Color.WHITE, new Color(70, 130, 180), "src/Images/start_bouton.png", TEXT_SIZE1);
    private Bouton nextStageBtn = new Bouton("Etage suivant", POS_NEXT_STAGE.x, POS_NEXT_STAGE.y, SIZE_NEXT_STAGE.width, SIZE_NEXT_STAGE.height,
        new Color(169, 169, 169), Color.WHITE, new Color(70, 130, 180), null, TEXT_SIZE1);
    private Bouton quitter = new Bouton("Quitter", POS_QUITTER.x, POS_QUITTER.y, SIZE_QUITTER.width, SIZE_QUITTER.height,
        new Color(255, 0, 0), Color.WHITE, new Color(255, 0, 0), null, TEXT_SIZE1);
    private Bouton buyCombo1 = new Bouton("Buy 15p", POS_COMBO1.x, POS_COMBO1.y, SIZE_COMBO1.width, SIZE_COMBO1.height,
        new Color(96, 96, 96), Color.WHITE, new Color(70, 130, 180), null, TEXT_SIZE2);
    private Bouton buyCombo2 = new Bouton("Buy 30p", POS_COMBO2.x, POS_COMBO2.y, SIZE_COMBO2.width, SIZE_COMBO2.height,
        new Color(96, 96, 96), Color.WHITE, new Color(70, 130, 180), null, TEXT_SIZE2);
    private Bouton buyCombo3 = new Bouton("Buy 50p", POS_COMBO3.x, POS_COMBO3.y, SIZE_COMBO3.width, SIZE_COMBO3.height,
        new Color(96, 96, 96), Color.WHITE, new Color(70, 130, 180), null, TEXT_SIZE2);

    // Instances de classes utiles
    private Bonus b;
    private Character c;
    private Tir tir;
    private Inputs i;
    private Obstacles o;
    private LevelManager lm;

    // Images (initialisées dans initImages() pour éviter des NullPointer lors de la
    // création)
    private Image imgObstacle;
    private Image coinImage;
    private Image imgCombo1;
    private Image imgCombo2;
    private Image imgCombo3;
    

    public Affichage(Character character, Tir t, Bonus bonus, Inputs inputs, Obstacles obs, LevelManager levelManager) {
        // Configuration du panneau
        setLayout(null);
        setPreferredSize(new java.awt.Dimension(originalWidth, originalHeight));

        this.c = character;
        this.tir = t;
        this.b = bonus;
        this.i = inputs;
        this.o = obs;
        this.lm = levelManager;

        // Initialiser les dimensions
        Model.Dimensions.updateDimensions(originalWidth, originalHeight);

        // Ajouter un écouteur pour détecter les changements de taille
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Model.Dimensions.updateDimensions(getWidth(), getHeight());
                System.out.println("Dimensions mises à jour : " + getWidth() + "x" + getHeight());
                System.out.println("Ratio : " + Dimensions.getScaleX() + "x" + Dimensions.getScaleY());
                float newScaleX = Dimensions.getScaleX();
                float newScaleY = Dimensions.getScaleY();
                // Mettre à jour les dimensions de la fenetre
                originalWidth = getWidth();
                originalHeight = getHeight();

                // Redimensionner les boutons
                relancerButton.setBounds(Dimensions.scaleX(POS_RELANCER.x), Dimensions.scaleY(POS_RELANCER.y),
                        Dimensions.scaleX(SIZE_RELANCER.width), Dimensions.scaleY(SIZE_RELANCER.height));
                relancerButton.setFont(new Font("Arial", Font.BOLD, (int) (TEXT_SIZE1 * newScaleX)));
                acceuil.setBounds(Dimensions.scaleX(POS_ACCEUIL.x), Dimensions.scaleY(POS_ACCEUIL.y),
                        Dimensions.scaleX(SIZE_ACCEUIL.width), Dimensions.scaleY(SIZE_ACCEUIL.height));
                acceuil.setFont(new Font("Arial", Font.BOLD, (int) (TEXT_SIZE1 * newScaleX)));
                startGame.setBounds(Dimensions.scaleX(POS_START.x), Dimensions.scaleY(POS_START.y),
                        Dimensions.scaleX(SIZE_START.width), Dimensions.scaleY(SIZE_START.height));
                startGame.setFont(new Font("Arial", Font.BOLD, (int) (TEXT_SIZE1 * newScaleX)));
                nextStageBtn.setBounds(Dimensions.scaleX(POS_NEXT_STAGE.x), Dimensions.scaleY(POS_NEXT_STAGE.y),
                        Dimensions.scaleX(SIZE_NEXT_STAGE.width), Dimensions.scaleY(SIZE_NEXT_STAGE.height));
                nextStageBtn.setFont(new Font("Arial", Font.BOLD, (int) (TEXT_SIZE1 * newScaleX)));
                quitter.setBounds(Dimensions.scaleX(POS_QUITTER.x), Dimensions.scaleY(POS_QUITTER.y),
                        Dimensions.scaleX(SIZE_QUITTER.width), Dimensions.scaleY(SIZE_QUITTER.height));
                quitter.setFont(new Font("Arial", Font.BOLD, (int) (TEXT_SIZE1 * newScaleX)));
                buyCombo1.setBounds(Dimensions.scaleX(POS_COMBO1.x), Dimensions.scaleY(POS_COMBO1.y),
                        Dimensions.scaleX(SIZE_COMBO1.width), Dimensions.scaleY(SIZE_COMBO1.height));
                buyCombo1.setFont(new Font("Arial", Font.BOLD, (int) (TEXT_SIZE2 * newScaleX)));
                buyCombo2.setBounds(Dimensions.scaleX(POS_COMBO2.x), Dimensions.scaleY(POS_COMBO2.y),
                        Dimensions.scaleX(SIZE_COMBO2.width), Dimensions.scaleY(SIZE_COMBO2.height));
                buyCombo2.setFont(new Font("Arial", Font.BOLD, (int) (TEXT_SIZE2 * newScaleX)));
                buyCombo3.setBounds(Dimensions.scaleX(POS_COMBO3.x), Dimensions.scaleY(POS_COMBO3.y),
                        Dimensions.scaleX(SIZE_COMBO3.width), Dimensions.scaleY(SIZE_COMBO3.height));
                buyCombo3.setFont(new Font("Arial", Font.BOLD, (int) (TEXT_SIZE2 * newScaleX)));

                
                // Mettre à jour la taille de la fenêtre dans les classes associées
                Obstacles.setDimensionsFenetre(getWidth(), getHeight());
                Tir.setDimensionsFenetre(getWidth(), getHeight());
                Character.setDimensionsFenetre(getWidth(), getHeight());
                Ennemis.setDimensionsFenetre(getWidth(), getHeight());

                // Mettre à jours la taille du personnage
                c.setDimensionsJoueur(Dimensions.scaleX(c.WIDTH_perso), Dimensions.scaleX(c.WIDTH_perso));

                // Mettre à jour la taille de l'ennemi 
                for (Ennemis ennemi : Ennemis.getListEnnemies()) {
                    // Si c'est un fantome 
                    if (ennemi instanceof Fantome) {
                        Fantome f = (Fantome) ennemi;
                        f.setDimensionsEnnemis(Dimensions.scaleX(Fantome.WIDTH), Dimensions.scaleY(Fantome.HEIGHT));
                    }
                    // Si c'est une araignee
                    if (ennemi instanceof Araignee) {
                        Araignee a = (Araignee) ennemi;
                        a.setDimensionsEnnemis(Dimensions.scaleX(Araignee.WIDTH), Dimensions.scaleY(Araignee.HEIGHT));
                    }
                    // Si c'est une goule
                    if (ennemi instanceof Goules) {
                        Goules g = (Goules) ennemi;
                        g.setDimensionsEnnemis(Dimensions.scaleX(Goules.WIDTH), Dimensions.scaleY(Goules.HEIGHT));
                    }
                }
            }
        });

        // Initialisation des images après avoir initialisé les instances
        initImages();

        // Initialisation des boutons et des listeners
        initButtons();
        initListeners();

        // Rendre invisibles certains boutons par défaut
        hideGameButtons();
    }

    // Initialisation des images de la vue
    private void initImages() {
        // S'assurer que les dimensions nécessaires sont accessibles
        imgObstacle = new ImageIcon("src/Images/caisse.png").getImage()
                .getScaledInstance(Obstacles.WIDTH_O, Obstacles.HEIGHT_O, Image.SCALE_DEFAULT);
        coinImage = new ImageIcon("src/Images/coin.png").getImage()
                .getScaledInstance(Bonus.WIDTH_B, Bonus.HEIGHT_B, Image.SCALE_DEFAULT);
        imgCombo1 = new ImageIcon("src/Images/shoot.png").getImage()
                .getScaledInstance(85, 85, Image.SCALE_DEFAULT);
        imgCombo2 = new ImageIcon("src/Images/speed.png").getImage()
                .getScaledInstance(85, 85, Image.SCALE_DEFAULT);
        imgCombo3 = new ImageIcon("src/Images/sante.png").getImage()
                .getScaledInstance(85, 85, Image.SCALE_DEFAULT);
    }

    // Initialisation des boutons et ajout au panel
    private void initButtons() {
        add(relancerButton);
        add(acceuil);
        add(startGame);
        add(nextStageBtn);
        add(quitter);
        add(buyCombo1);
        add(buyCombo2);
        add(buyCombo3);
    }

    // Initialisation des listeners sur les boutons
    private void initListeners() {
        // Redémarrer la partie
        relancerButton.addActionListener(e -> {
            i.resetKeys();
            c.restartPlayer();
            c.resetInput(i);
            configurerClavier();
            tir.resetTirs();
            b.resetBonus();
            lm.relancerGame(); // relancer le jeu
            // simuler un componet resized pour mettre à jour les dimensions
            this.dispatchEvent(new ComponentEvent(this, ComponentEvent.COMPONENT_RESIZED));
            
        });

        // Retour à l'accueil
        acceuil.addActionListener(e -> {
            lm.goToAccueil();
            relancerButton.setVisible(false);
            acceuil.setVisible(false);
            repaint();
        });

        // Quitter le jeu
        quitter.addActionListener(e -> System.exit(0));

        // Lancer une nouvelle partie
        startGame.addActionListener(e -> {
            lm.startNewGame();
            i.resetKeys();
            c.restartPlayer();
            c.resetInput(i);
            configurerClavier();
            tir.resetTirs();
            b.resetBonus();
            // simuler un componet resized pour mettre à jour les dimensions
            this.dispatchEvent(new ComponentEvent(this, ComponentEvent.COMPONENT_RESIZED));
        });

        // Passage au niveau suivant
        nextStageBtn.addActionListener(e -> {
            lm.goNextStage();
            buyCombo1.setEnabled(true); // Activer le bouton de combo 1
            buyCombo2.setEnabled(true); // Activer le bouton de combo 2
            buyCombo3.setEnabled(true); // Activer le bouton de combo 3
            c.activerLesCombos();
        });

        // Achat des combos
        buyCombo1.addActionListener(e -> {
            boolean isBuy = c.addComboBonus(new ComboBonus(1));
            if (isBuy) {
                buyCombo1.setEnabled(false);
            }
        });
        buyCombo2.addActionListener(e -> {
            boolean isBuy = c.addComboBonus(new ComboBonus(2));
            if (isBuy) {
                buyCombo2.setEnabled(false);
            }
        });
        buyCombo3.addActionListener(e -> {
            boolean isBuy = c.addComboBonus(new ComboBonus(3));
            if (isBuy) {
                buyCombo3.setEnabled(false);
            }
        });
    }

    // Méthode pour configurer le clavier
    private void configurerClavier() {
        this.removeKeyListener(i); // Évite les doublons
        this.addKeyListener(i);
        this.setFocusable(true);
        this.requestFocusInWindow(); // Force le focus clavier
    }

    // Méthode utilitaire pour masquer les boutons de jeu
    private void hideGameButtons() {
        relancerButton.setVisible(false);
        acceuil.setVisible(false);
        startGame.setVisible(false);
        nextStageBtn.setVisible(false);
        quitter.setVisible(false);
        buyCombo1.setVisible(false);
        buyCombo2.setVisible(false);
        buyCombo3.setVisible(false);
    }

    // ------------------------------- Redessiner la vue
    // -------------------------------

    // Redéfinition de paintComponent pour dessiner la vue
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessiner le fond d'écran du niveau
        String lien_bg = lm.getNiveauActuel().getImage_arriere_plan();
        Image bg = new ImageIcon(lien_bg).getImage();
        g.drawImage(bg, 0, 0, originalWidth, originalHeight, null);

        // Afficher la page d'accueil si le jeu n'a pas démarré
        if (!lm.getGameStart()) {
            drawAcceuil(g);
        } else {
            // Si le jeu est en cours
            if (lm.getGame_running() && !lm.getGameLose() && !lm.getGameWon()) {
                g.drawImage(Character.characterSprite, (int) c.getCurrent_x(), (int) c.getCurrent_y(), null);
                drawTirs(g); // Dessiner les tirs
                drawBonus(g); // Dessiner les bonus
                drawEnnemies(g); // Dessiner les ennemis
                drawObstacle(g); // Dessiner les obstacles
                drawBarreVie(g); // Dessiner la barre de vie
                drawNiveau(g); // Dessiner le niveau actuel
                relancerButton.setVisible(false);
                acceuil.setVisible(false);
                nextStageBtn.setVisible(false);
            }
            // Masquer startGame et quitter quand le jeu est lancé
            startGame.setVisible(false);
            quitter.setVisible(false);
        }
        // Affichage de l'écran de victoire/défaite et de la boutique
        drawGameStop_Won(g);
        drawBoutique(g);
    }

    // -------------------------- Méthodes de dessin spécifiques
    // ----------------------------

    // Méthode pour dessiner la boutique (si applicable)
    public void drawBoutique(Graphics g) {
        if (lm.getShowStore()) {
            // Un grand carré de au centre de la fenetre représente la boutique
            g.setColor(new Color(220, 220, 220, 200)); // Couleur de fond de la boutique
            g.fillRect(originalWidth/ 2 - 350,originalHeight/ 2 - 350, 700, 700); // Fond de la boutique
            g.setColor(Color.BLACK); // Couleur du contour
            g.drawRect(originalWidth/ 2 - 350,originalHeight/ 2 - 350, 700, 700); // Contour de la boutique
            // Contenu de la boutique ici
            g.setFont(new Font("Arial", Font.BOLD, 25)); // Police de la boutique
            g.setColor(Color.BLACK); // Couleur du texte
            g.drawString("Victoire ! Etage " + lm.getNiveauActuel().getNiveau() + " terminé", originalWidth/ 2 - 120,originalHeight/ 2 - 300); // Titre de la boutique
            // Dessiner les icones des combos
            g.drawImage(imgCombo1, originalWidth/ 2 - 225,originalHeight/ 2 - 210, null); // Image du bonus 1
            g.drawImage(imgCombo2, originalWidth/ 2 - 50,originalHeight/ 2 - 210, null); // Image du bonus 2
            g.drawImage(imgCombo3, originalWidth/ 2 + 125,originalHeight/ 2 - 210, null); // Image du bonus 3
            // Afficher le nombre de pièces en bas à gauche
            g.setColor(Color.BLACK); // Couleur du texte
            g.setFont(new Font("Arial", Font.PLAIN, 15)); // Police de texte
            g.drawString("Double tir", originalWidth/ 2 - 220,originalHeight/ 2 - 105); // Texte du bonus 1
            g.drawString("Vitesse x2", originalWidth/ 2 - 45,originalHeight/ 2 - 105); // Texte du bonus 2
            g.drawString("Vie pleine", originalWidth/ 2 + 130,originalHeight/ 2 - 105); // Texte du bonus 3
            // Afficher le nombre de bonus collectés en bas à gauche
            g.setColor(Color.BLACK); // Couleur du texte
            g.setFont(new Font("Arial", Font.PLAIN, 15)); // Police de texte
            g.drawString("Mes pièces : " + c.getNombreBonus(), originalWidth/ 2 - 320,originalHeight/ 2 + 320); // Texte du bonus 3
            // Un cadre au centre avec les astuces
            g.setColor(new Color(230, 230, 230, 200)); // Couleur de fond de la zone d'astuces
            g.fillRect(originalWidth/ 2 - 250,originalHeight/ 2 + 8, 500, 150); // Fond de la zone d'astuces
            g.setColor(Color.BLACK); // Bordure de la zone d'astuces
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Astuces", originalWidth/ 2 - 250,originalHeight/ 2);
            g.drawRect(originalWidth/ 2 - 250,originalHeight/ 2 + 8, 500, 150);
            // Ecrire les astuces
            g.setFont(new Font("Arial", Font.PLAIN, 15));
            g.drawString("•  Double tir vous permet de tirer 2 projectiles en un clic.", originalWidth/ 2 - 240,originalHeight/ 2 + 35);
            g.drawString("•  Vitesse x2 vous permet d'aller 2 fois plus vite.", originalWidth/ 2 - 240,originalHeight/ 2 + 65);
            g.drawString("•  Vie pleine réinitialise vos points de vie à 100%.", originalWidth/ 2 - 240,originalHeight/ 2 + 95);

            // Afficher les boutons de la boutique
            nextStageBtn.setVisible(true);
            buyCombo1.setVisible(true);
            buyCombo2.setVisible(true);
            buyCombo3.setVisible(true);
        } else {
            nextStageBtn.setVisible(false);
            buyCombo1.setVisible(false);
            buyCombo2.setVisible(false);
            buyCombo3.setVisible(false);
        }
    }

    // Méthode pour dessiner les bonus (coins)
    public void drawBonus(Graphics g) {
        for (int i = 0; i < b.getPointBonus().size(); i++) {
            g.drawImage(coinImage, b.getPointBonus().get(i).x, b.getPointBonus().get(i).y, null);
        }
    }

    // Méthode pour dessiner les tirs
    public void drawTirs(Graphics g) {
        for (int i = 0; i < tir.getTirs().size(); i++) {
            int x= tir.getTirs().get(i).getPosition().x;
            int y= tir.getTirs().get(i).getPosition().y;
            g.drawImage(Tir.imageProjectile, x, y, null);
        }
    }

    // Méthode pour dessiner les ennemis
    public void drawEnnemies(Graphics g) {
        // Appel de la méthode statique sans instance
        List<Ennemis> ennemis = Ennemis.getListEnnemies();
        // Boucle affichant tous les ennemis
        for (Ennemis ennemi : ennemis) {
            // Vérification si l'ennemi est un Fantome
            if (ennemi instanceof Fantome) {
                Fantome fantome = (Fantome) ennemi; // Casting en Fantome
                g.drawImage(fantome.img, (int) fantome.getPosition().getX(), (int) fantome.getPosition().getY(), null);
            }
            // Vérifier si l'ennemi est une araignée
            if (ennemi instanceof Araignee) {
                Araignee araignee = (Araignee) ennemi; // Casting en Araignee
                g.drawImage(araignee.img, (int) araignee.getPosition().getX(), (int) araignee.getPosition().getY(), null);
            }
            // Vérifier si l'ennemi est un goule
            if(ennemi instanceof Goules) {
                Goules goule = (Goules) ennemi; // Casting en Goule
                g.drawImage(goule.img, (int) goule.getPosition().getX(), (int)
                goule.getPosition().getY(), null);
                if (goule.projectile != null) {
                    g.setColor(Color.RED);
                    g.fillOval(goule.projectile.getPosition().x,
                    goule.projectile.getPosition().y, 8, 8);
                    g.setColor(Color.BLACK);
                }
            }
        }
    }

    // Méthode pour dessiner les obstacles
    public void drawObstacle(Graphics g) {
        ArrayList<Point> obstacles = o.getObstacles();
        for (Point obstacle : obstacles) {
            g.drawImage(imgObstacle, (int) obstacle.getX(), (int) obstacle.getY(), null);
        }
    }

    // Méthode pour dessiner le niveau actuel
    public void drawNiveau(Graphics g) {
        Color couleur = (lm.getNiveauActuel().getNiveau() < 3) ? Color.BLACK : Color.WHITE;
        g.setColor(couleur);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Etage actuel : ", originalWidth- 450, 43);
        for (int i = 0; i < 5; i++) {
            int x= originalWidth- 355 + (i * 35);
            g.drawRect(x, 30, 20, 20);
            if (i < lm.getNiveauActuel().getNiveau()) {
                g.setColor(new Color(70, 130, 180));
                g.fillRect(x, 30, 20, 20);
                g.setColor(couleur);
            }
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.drawString(String.valueOf(i + 1), originalWidth+ 6, 45);
        }
    }

    // Méthode pour dessiner la page d'accueil
    public void drawAcceuil(Graphics g) {
        Image bg = new ImageIcon("src/Images/acceuil.jpg").getImage();
        g.drawImage(bg, 0, 0, originalWidth, originalHeight, null);
        startGame.setVisible(true);
        quitter.setVisible(true);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
        g.setColor(Color.WHITE);
        g.drawString("Bienvenue sur Nibelhein", originalWidth/ 2 - 402,originalHeight/ 2 - 2);
        g.setColor(Color.BLACK);
        g.drawString("Bienvenue sur Nibelhein", originalWidth/ 2 - 400,originalHeight/ 2);
    }

    // Méthode pour dessiner la barre de vie et afficher les pièces
    public void drawBarreVie(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRoundRect(xBarreVie, yBarreVie, c.MAXVIE * 40, heightBarreVie, arcBarreVie, arcBarreVie);
        g.setColor(Color.RED);
        g.fillRoundRect(xBarreVie, yBarreVie, c.getVie() * 40, heightBarreVie, arcBarreVie, arcBarreVie);
        g.setColor(Color.BLACK);
        g.drawRoundRect(xBarreVie, yBarreVie, c.MAXVIE * 40, heightBarreVie, arcBarreVie, arcBarreVie);
        g.drawImage(coinImage, originalWidth- 135, 25, null);
        g.setColor(new Color(225, 0, 0));
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Pièces : " + c.getNombreBonus(), originalWidth- 100, 43);
    }

    // Méthode pour dessiner l'écran de victoire ou de game over
    public void drawGameStop_Won(Graphics g) {
        if (lm.getGameWon()) {
            relancerButton.setVisible(true);
            acceuil.setVisible(true);
            g.setColor(Color.BLACK);
            g.fillRect(originalWidth/ 2 - 252,originalHeight/ 2 - 252, 504, 504);
            g.setColor(Color.WHITE);
            g.fillRect(originalWidth/ 2 - 250,originalHeight/ 2 - 250, 500, 500);
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 35));
            g.drawString("Victoire !!!", originalWidth/ 2 - 90,originalHeight/ 2 - 180);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Vous êtes simplement imbattables !", originalWidth/ 2 - 175,originalHeight/ 2 - 120);
        } else if (lm.getGameLose()) {
            relancerButton.setVisible(true);
            acceuil.setVisible(true);
            g.setColor(Color.BLACK);
            g.fillRect(originalWidth/ 2 - 252,originalHeight/ 2 - 252, 504, 504);
            g.setColor(Color.WHITE);
            g.fillRect(originalWidth/ 2 - 250,originalHeight/ 2 - 250, 500, 500);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 35));
            g.drawString("Game Over", originalWidth/ 2 - 90,originalHeight/ 2 - 180);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Nombre de pièces collectées : " + c.getNombreBonus(), originalWidth/ 2 - 150,originalHeight/ 2 - 120);
        } else {
            relancerButton.setVisible(false);
            acceuil.setVisible(false);
        }
    }
}
