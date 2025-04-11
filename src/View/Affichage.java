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
import Model.Ennemies;
import Model.Fantome;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Affichage extends JPanel {
    // Facteurs d'échelle pour le redimensionnement responsive
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private int originalWidth = 1500; // Largeur d'origine (X)
    private int originalHeight = 650; // Hauteur d'origine (Y)
    
    // Définition des constantes X et Y pour faciliter le code responsive
    private int X = originalWidth;
    private int Y = originalHeight;

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
    private Bouton relancerButton;
    private Bouton acceuil;
    private Bouton startGame;
    private Bouton nextStageBtn;
    private Bouton quitter;
    private Bouton boutique;
    private Thread renderThread;

    // Images redimensionnables
    private Image cachedObstacleImage;
    private Image cachedCoinImage;
    private Image cachedBackgroundImage;
    private Image cachedAcceuilImage;

    // Position de la barre de vie
    public static final int xBarreVie = 30;
    public static final int yBarreVie = 30;

    // Dimension barre de vie
    public static final int heightBarreVie = 20; // Hauteur
    public static final int arcBarreVie = 10; // Angle des bordures

    // Getteurs pour game_running, game_pause et game_lose
    public boolean getGame_running() {
        return game_running;
    }

    public boolean getGame_lose() {
        return game_lose;
    }

    public boolean getGame_pause() {
        return game_pause;
    }

    private void initButtons() {
        relancerButton = new Bouton("Nouvelle Partie", X / 2 - 100, Y / 2 - 30, 200, 50, new Color(169, 169, 169),
                Color.WHITE, new Color(70, 130, 180), null);
        acceuil = new Bouton("Acceuil", X / 2 - 100, Y / 2 + 100, 200, 50, new Color(169, 169, 169), Color.WHITE,
                new Color(70, 130, 180), null);
        startGame = new Bouton("Commencer une partie", 30, Y - 180, 300, 55, new Color(169, 169, 169), Color.WHITE,
                new Color(70, 130, 180), "src/Images/start_bouton.png");
        nextStageBtn = new Bouton("Etage suivant", X / 2 + 230, Y / 2 + 285, 180, 40, new Color(169, 169, 169),
                Color.WHITE, new Color(70, 130, 180), null);
        quitter = new Bouton("Quitter", X - 150, 30, 100, 35, new Color(255, 0, 0), Color.WHITE, new Color(255, 0, 0),
                null);
        boutique = new Bouton("Boutique", X - 320, 30, 140, 35, new Color(169, 169, 169), Color.WHITE,
                new Color(70, 130, 180), null);
    }

    private void adjustButtonSizes() {
        int width = getWidth();
        int height = getHeight();
        
        // Calculer les facteurs d'échelle actuels
        scaleX = (float) width / originalWidth;
        scaleY = (float) height / originalHeight;
        
        // Mettre à jour X et Y pour une utilisation plus facile dans le code
        X = width;
        Y = height;
        
        // Redimensionner tous les boutons
        relancerButton.setBounds(
            (int)(width / 2 - 100 * scaleX), 
            (int)(height / 2 - 30 * scaleY), 
            (int)(200 * scaleX), 
            (int)(50 * scaleY)
        );
        acceuil.setBounds(
            (int)(width / 2 - 100 * scaleX), 
            (int)(height / 2 + 100 * scaleY), 
            (int)(200 * scaleX), 
            (int)(50 * scaleY)
        );
        startGame.setBounds(
            (int)(30 * scaleX), 
            (int)(height - 180 * scaleY), 
            (int)(300 * scaleX), 
            (int)(55 * scaleY)
        );
        nextStageBtn.setBounds(
            (int)(width / 2 + 230 * scaleX), 
            (int)(height / 2 + 285 * scaleY), 
            (int)(180 * scaleX), 
            (int)(40 * scaleY)
        );
        quitter.setBounds(
            (int)(width - 150 * scaleX), 
            (int)(30 * scaleY), 
            (int)(100 * scaleX), 
            (int)(35 * scaleY)
        );
        boutique.setBounds(
            (int)(width - 320 * scaleX), 
            (int)(30 * scaleY), 
            (int)(140 * scaleX), 
            (int)(35 * scaleY)
        );
        
        // Ajuster les polices
        adjustFontSizes();
        
        // Recharger les images avec la nouvelle taille
        loadScaledImages();
    }
    
    private void loadScaledImages() {
        // Charger l'image pour les obstacles (caisse.png)
        cachedObstacleImage = new ImageIcon("src/Images/caisse.png").getImage()
                .getScaledInstance((int)(o.WIDTH_O * scaleX), (int)(o.HEIGHT_O * scaleY), Image.SCALE_SMOOTH);
        
        // Charger l'image du coin
        cachedCoinImage = new ImageIcon("src/Images/coin.png").getImage()
                .getScaledInstance((int)(b.WIDTH_B * scaleX), (int)(b.HEIGHT_B * scaleY), Image.SCALE_SMOOTH);
    }
    
    private void adjustFontSizes() {
        // Ajuster les tailles de police pour les boutons
        float buttonFontSize = Math.min(14 * scaleX, 14 * scaleY);
        buttonFontSize = Math.max(buttonFontSize, 10); // Taille minimale pour la lisibilité
        
        Font buttonFont = new Font("Arial", Font.BOLD, (int)buttonFontSize);
        relancerButton.setFont(buttonFont);
        acceuil.setFont(buttonFont);
        startGame.setFont(buttonFont);
        nextStageBtn.setFont(buttonFont);
        quitter.setFont(buttonFont);
        boutique.setFont(buttonFont);
    }
    
    // Constructeur de la classe Affichage
    public Affichage(Character character, Tir t, Bonus bonus, Inputs inputs, Obstacles obs, LevelManager levelManager) {
        setLayout(null); // Désactiver le LayoutManager
        setPreferredSize(new Dimension(originalWidth, originalHeight));
        this.c = character;
        this.o = obs;
        this.tir = t;
        this.b = bonus;
        this.i = inputs;
        this.lm = levelManager;

        initButtons();
        loadScaledImages();

        // Ajouter un écouteur pour détecter les changements de taille
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustButtonSizes();
                repaint();
            }
        });
        
        // -------------------------* Gestion des boutons *--------------------------------------------------------------

        // Actions des boutons

        // Redémarrer la partie
        relancerButton.addActionListener(e -> {
            // Mettre à jour les états du jeu
            game_lose = false;
            game_running = true;
            // Réinitialiser le personnage
            inputs.resetKeys();
            c.restartPlayer(); // Réinitialisez le personnage existant
            c.resetInput(inputs); // Réinitialiser les entrées clavier
            this.addKeyListener(inputs); // Reajouter le KeyListener pour les entrées clavier
            this.setFocusable(true); // Rendre le panel focusable pour les entrées clavier
            // Réinitialiser les autres éléments du jeu
            b.resetBonus(); // Réinitialiser les bonus
            t.resetTirs(); // Réinitialiser les tirs
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
            c.resetInput(inputs); // Réinitialiser les entrées clavier
            this.addKeyListener(inputs); // Reajouter le KeyListener pour les entrées clavier
            this.setFocusable(true); // Rendre le panel focusable pour les entrées clavier
            // Réinitialiser les autres éléments du jeu
            b.resetBonus(); // Réinitialiser les bonus
            t.resetTirs(); // Réinitialiser les tirs
            b.resetBonus(); // Réinitialiser les bonus
            
            // Démarrer le thread de rendu
            startRenderThread();
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

        // Ajouter les boutons au panel (sans les afficher directement)
        add(relancerButton);
        add(acceuil);
        add(startGame);
        add(nextStageBtn);
        add(boutique);
        add(quitter);
    }

    // ----------------- On redefinie la méthode paint pour ajouter les elements graphiques ------------------------------

    // Redessiner la vue pour ajouter nos différents éléments
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Appliquer l'antialiasing pour un meilleur rendu
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Ajouter un fond d'ecran pour le jeu
        String lien_bg = lm.getNiveauActuel().getImage_arriere_plan();
        Image bg = new ImageIcon(lien_bg).getImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);

        // Si le joueur n'a plus de points de vie, le jeu est perdu
        if (c.getVie() <= 0) {
            game_lose = true;
            c.pauseGame();
        }

        // Si le jeu est lancé, on affiche les éléments graphiques sinon on affiche un
        // bouton pour lancer le jeu
        if (!game_running) {
            // Afficher un écran d'acceuil
            drawAcceuil(g);
        } else {
            if (!game_lose) { // Si le jeu n'est pas perdu, on affiche les éléments graphiques
                // Dessiner le personnage : la sorcière
                int charWidth = (int)(Character.WIDTH * scaleX);
                int charHeight = (int)(Character.HEIGHT * scaleY);
                
                // Redimensionner le sprite du personnage
                Image scaledCharSprite = Character.characterSprite.getScaledInstance(charWidth, charHeight, Image.SCALE_SMOOTH);
                g.drawImage(scaledCharSprite, (int)(c.getCurrent_x() * scaleX), (int)(c.getCurrent_y() * scaleY), null);

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
            } else {
                // Si le jeu est perdu, afficher un écran de fin de partie avec game over
                drawGameStop(g);
            }

            // On affiche la boutique après chaque victoire
            if (lm.getShowStore() == true) {
                drawBoutique(g);
                nextStageBtn.setVisible(true); // Afficher le bouton pour passer au niveau suivant
            }

            // Rendre les boutons startGame, boutique et quitter invisible car le jeu a démarré
            startGame.setVisible(false);
            boutique.setVisible(false);
            quitter.setVisible(false);
        }
    }

    // ----------------- Les méthodes pour dessiner les éléments de la vue -------------------------------------

    // Méthode pour dessiner et afficher la boutique
    public void drawBoutique(Graphics g) {
        if (lm.getShowStore() == true) {
            // Un grand carré au centre de la fenetre représente la boutique
            int boutique_width = (int)(900 * scaleX);
            int boutique_height = (int)(700 * scaleY);
            
            g.setColor(new Color(220, 220, 220, 200)); // Couleur de fond de la boutique
            g.fillRect(X / 2 - boutique_width / 2, Y / 2 - boutique_height / 2, boutique_width, boutique_height); // Fond de la boutique
            g.setColor(Color.BLACK); // Couleur du contour
            g.drawRect(X / 2 - boutique_width / 2, Y / 2 - boutique_height / 2, boutique_width, boutique_height); // Contour de la boutique

            // Contenu de la boutique ici avec police adaptative
            int titleFontSize = (int)(20 * Math.min(scaleX, scaleY));
            g.setFont(new Font("Arial", Font.BOLD, titleFontSize)); // Police de la boutique
            g.setColor(Color.BLACK); // Couleur du texte
            
            FontMetrics metrics = g.getFontMetrics();
            String title = "Victoire !!! ...... Boutique ici";
            int titleWidth = metrics.stringWidth(title);
            
            g.drawString(title, X / 2 - titleWidth / 2, Y / 2 - boutique_height / 2 + (int)(50 * scaleY)); // Titre de la boutique

            nextStageBtn.setVisible(true); // Afficher le bouton pour passer au niveau suivant
        } else {
            nextStageBtn.setVisible(false);
        }
    }

    // Méthode pour dessiner les bonus avec l'image coin.png
    public void drawBonus(Graphics g) {
        for (int i = 0; i < b.getPointBonus().size(); i++) {
            int x = (int)(b.getPointBonus().get(i).x * scaleX);
            int y = (int)(b.getPointBonus().get(i).y * scaleY);
            int width = (int)(b.WIDTH_B * scaleX);
            int height = (int)(b.HEIGHT_B * scaleY);
            
            g.drawImage(cachedCoinImage, x, y, width, height, null);
        }
    }

    private void startRenderThread() {
        // Arrêter le thread précédent s'il existe
        if (renderThread != null && renderThread.isAlive()) {
            renderThread.interrupt();
        }
        
        renderThread = new Thread(() -> {
            while (game_running && !Thread.currentThread().isInterrupted()) {
                SwingUtilities.invokeLater(() -> {
                    repaint();
                });
                try {
                    Thread.sleep(16); // ~60 FPS
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        renderThread.setDaemon(true); // Pour que le thread se termine avec l'application
        renderThread.start();
    }

    // Méthode pour récupérer la liste des tirs et les afficher à l'écran en tenant compte de la classe Tir
    public void drawTirs(Graphics g) {
        for (int i = 0; i < tir.getTirs().size(); i++) {
            g.setColor(Color.RED);
            int x = (int)(tir.getTirs().get(i).getPosition().x * scaleX);
            int y = (int)(tir.getTirs().get(i).getPosition().y * scaleY);
            int size = (int)(8 * Math.min(scaleX, scaleY)); // taille des tirs
            g.fillOval(x, y, size, size);
        }
    }

    // Méthode affichant tous les ennemis
    public void drawEnnemies(Graphics g) {
        // Appel de la méthode statique sans instance
        List<Ennemies> ennemies = Ennemies.getListEnnemies();
        // Boucle affichant tous les ennemis
        for (Ennemies ennemi : ennemies) {
            int x = (int)(ennemi.getPosition().getX() * scaleX);
            int y = (int)(ennemi.getPosition().getY() * scaleY);
            int width = (int)(ennemi.getWidth(ennemi) * scaleX);
            int height = (int)(ennemi.getHeight(ennemi) * scaleY);
            
            // Vérification si l'ennemi est un Fantome
            if (ennemi instanceof Fantome) {
                Fantome fantome = (Fantome) ennemi; // Casting en Fantome
                // Redimensionner l'image du fantôme
                Image scaledImage = fantome.img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                g.drawImage(scaledImage, x, y, null);
            }
            if (ennemi instanceof Araignee) {
                Araignee araignee = (Araignee) ennemi; // Casting en Araignee
                // Redimensionner l'image de l'araignée
                Image scaledImage = araignee.img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                g.drawImage(scaledImage, x, y, null);
            }
        }
    }

    // Méthode qui dessine les obstacles
    public void drawObstacle(Graphics g) {
        int i = 0;
        ArrayList<Point> obstacles = o.getObstacles();
        for (Point obstacle : obstacles) {
            int x = (int)(obstacle.x * scaleX);
            int y = (int)(obstacle.y * scaleY);
            int width = (int)(o.WIDTH_O * scaleX);
            int height = (int)(o.HEIGHT_O * scaleY);
            
            // Afficher les images de caisse
            g.drawImage(cachedObstacleImage, x, y, width, height, null);
            i += 1;
        }
    }

    public void drawNiveau(Graphics g) {
        // Police adaptative pour le texte "Etage actuel"
        int fontSize = (int)(14 * Math.min(scaleX, scaleY));
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, fontSize));
        g.drawString("Etage actuel : ", X - (int)(450 * scaleX), (int)(43 * scaleY));
        
        // Dessiner les 5 petits carrés avec des numéros
        for (int i = 0; i < 5; i++) {
            int x = X - (int)(355 * scaleX) + (int)((i * 35) * scaleX);
            int y = (int)(30 * scaleY);
            int boxSize = (int)(20 * Math.min(scaleX, scaleY));
            
            g.drawRect(x, y, boxSize, boxSize);
            // Remplir les carrés correspondant au niveau actuel
            if (i < lm.getNiveauActuel().getNiveau()) {
                g.setColor(new Color(255, 165, 80));
                g.fillRect(x, y, boxSize, boxSize);
                g.setColor(Color.BLACK);
            }
            // Dessiner le numéro au centre du carré
            int numFontSize = (int)(12 * Math.min(scaleX, scaleY));
            g.setFont(new Font("Arial", Font.PLAIN, numFontSize));
            
            FontMetrics metrics = g.getFontMetrics();
            String numStr = String.valueOf(i + 1);
            int numWidth = metrics.stringWidth(numStr);
            
            g.drawString(numStr, x + (boxSize - numWidth) / 2, y + boxSize * 3/4); // Centrer le numéro
        }
    }

    // Méthode pour dessiner la page d'acceuil
    public void drawAcceuil(Graphics g) {
        // Ajouter l'image un fond d'ecran pour l'acceuil
        Image bg = new ImageIcon("src/Images/acceuil.jpg").getImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
        
        // Afficher les boutons de la page d'acceuil
        startGame.setVisible(true);
        boutique.setVisible(true);
        quitter.setVisible(true);
        
        // Afficher un message de bienvenue avec un contour rouge stylé
        int titleFontSize = (int)(60 * Math.min(scaleX, scaleY));
        Font titleFont = new Font("Comic Sans MS", Font.BOLD, titleFontSize);
        g.setFont(titleFont);
        
        // Calculer la largeur du texte pour le centrer correctement
        FontMetrics metrics = g.getFontMetrics(titleFont);
        String title = "Bienvenue sur Nibelhein";
        int titleWidth = metrics.stringWidth(title);
        
        // Dessiner le contour rouge
        g.setColor(Color.RED);
        g.drawString(title, X / 2 - titleWidth / 2 - 2, Y / 2 - 2);
        
        // Dessiner le texte vert
        g.setColor(Color.GREEN);
        g.drawString(title, X / 2 - titleWidth / 2, Y / 2);
    }

    // Méthode pour dessiner la barre de vie et les bonus collectés
    public void drawBarreVie(Graphics g) {
        // Positions et dimensions adaptatives
        int x = (int)(xBarreVie * scaleX);
        int y = (int)(yBarreVie * scaleY);
        int height = (int)(heightBarreVie * scaleY);
        int arc = (int)(arcBarreVie * Math.min(scaleX, scaleY));
        int barWidth = (int)(40 * scaleX); // Largeur par point de vie
        
        // Dessiner une barre de vie rouge dans un contour noir et des bordures arrondies
        g.setColor(Color.GRAY);
        g.fillRoundRect(x, y, c.maxVie * barWidth, height, arc, arc);
        g.setColor(Color.RED);
        g.fillRoundRect(x, y, c.getVie() * barWidth, height, arc, arc);
        g.setColor(Color.BLACK);
        g.drawRoundRect(x, y, c.maxVie * barWidth, height, arc, arc);
        
        // Afficher le nombre de bonus récupérés en haut à droite
        int coinX = X - (int)(135 * scaleX);
        int coinY = (int)(25 * scaleY);
        int coinWidth = (int)(b.WIDTH_B * scaleX);
        int coinHeight = (int)(b.HEIGHT_B * scaleY);
        
        g.drawImage(cachedCoinImage, coinX, coinY, coinWidth, coinHeight, null);
        
        g.setColor(Color.BLACK);
        int fontSize = (int)(14 * Math.min(scaleX, scaleY));
        g.setFont(new Font("Arial", Font.PLAIN, fontSize));
        g.drawString("Pièces : " + c.getNombreBonus(), X - (int)(98 * scaleX), (int)(43 * scaleY));
    }

    // Méthode pour afficher le gamelose et la page de pause selon le cas
    public void drawGameStop(Graphics g) {
        if (game_lose == true && game_running == true) {
            relancerButton.setVisible(true);
            acceuil.setVisible(true);
            
            // Calculer les dimensions du pop-up de fin de jeu
            int popupWidth = (int)(500 * scaleX);
            int popupHeight = (int)(500 * scaleY);
            int popupX = X / 2 - popupWidth / 2;
            int popupY = Y / 2 - popupHeight / 2;
            
            // Bordure noire
            g.setColor(Color.BLACK);
            g.fillRect(popupX - (int)(2 * scaleX), popupY - (int)(2 * scaleY), 
                     popupWidth + (int)(4 * scaleX), popupHeight + (int)(4 * scaleY));
            
            // Fond blanc
            g.setColor(Color.WHITE);
            g.fillRect(popupX, popupY, popupWidth, popupHeight);
            
            // Titre "Game Over"
            int titleFontSize = (int)(35 * Math.min(scaleX, scaleY));
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, titleFontSize));
            
            FontMetrics titleMetrics = g.getFontMetrics();
            String gameOverText = "Game Over";
            int gameOverWidth = titleMetrics.stringWidth(gameOverText);
            
            g.drawString(gameOverText, X / 2 - gameOverWidth / 2, 
                       popupY + (int)(70 * scaleY));
            
            // Texte d'information
            int infoFontSize = (int)(20 * Math.min(scaleX, scaleY));
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, infoFontSize));
            
            FontMetrics infoMetrics = g.getFontMetrics();
            String infoText = "Nombre de pièces collectées : " + c.getNombreBonus();
            int infoWidth = infoMetrics.stringWidth(infoText);
            
            g.drawString(infoText, X / 2 - infoWidth / 2, 
                       popupY + (int)(130 * scaleY));
        }
        else {
            relancerButton.setVisible(false);
            acceuil.setVisible(false);
            nextStageBtn.setVisible(false);
        }
    }

    // Méthode à appeler lors du redimensionnement de la fenêtre
    public void updateSize(int width, int height) {
        setSize(width, height);
        adjustButtonSizes();
        repaint();
    }
    
    // Méthode pour nettoyer les ressources avant de fermer l'application
    public void cleanup() {
        if (renderThread != null && renderThread.isAlive()) {
            renderThread.interrupt();
        }
    }
}