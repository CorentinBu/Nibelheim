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
import Model.Dimension;
import Model.Ennemies;
import Model.Fantome;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class Affichage extends JPanel {
    // Attributs de la classe
    private int originalWidth = Dimension.getReferenceWidth();
    private int originalHeight = Dimension.getReferenceHeight();

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
        int centerX = Dimension.getWidth() / 2;
        int centerY = Dimension.getHeight() / 2;
        
        relancerButton = new Bouton("Nouvelle Partie", 
            centerX - Dimension.scaleX(100), 
            centerY - Dimension.scaleY(30), 
            Dimension.scaleX(200), 
            Dimension.scaleY(50), 
            new Color(169, 169, 169), Color.WHITE, new Color(70, 130, 180), null);
            
        acceuil = new Bouton("Acceuil", 
            centerX - Dimension.scaleX(100), 
            centerY + Dimension.scaleY(100), 
            Dimension.scaleX(200), 
            Dimension.scaleY(50), 
            new Color(169, 169, 169), Color.WHITE, new Color(70, 130, 180), null);
            
        startGame = new Bouton("Commencer une partie", 
            Dimension.scaleX(30), 
            Dimension.getHeight() - Dimension.scaleY(180), 
            Dimension.scaleX(300), 
            Dimension.scaleY(55), 
            new Color(169, 169, 169), Color.WHITE, new Color(70, 130, 180), "src/Images/start_bouton.png");
            
        nextStageBtn = new Bouton("Etage suivant", 
            centerX + Dimension.scaleX(230), 
            centerY + Dimension.scaleY(285), 
            Dimension.scaleX(180), 
            Dimension.scaleY(40), 
            new Color(169, 169, 169), Color.WHITE, new Color(70, 130, 180), null);
            
        quitter = new Bouton("Quitter", 
            Dimension.getWidth() - Dimension.scaleX(150), 
            Dimension.scaleY(30), 
            Dimension.scaleX(100), 
            Dimension.scaleY(35), 
            new Color(255, 0, 0), Color.WHITE, new Color(255, 0, 0), null);
            
        boutique = new Bouton("Boutique", 
            Dimension.getWidth() - Dimension.scaleX(320), 
            Dimension.scaleY(30), 
            Dimension.scaleX(140), 
            Dimension.scaleY(35), 
            new Color(169, 169, 169), Color.WHITE, new Color(70, 130, 180), null);
    }

    private void adjustButtonSizes() {
        int centerX = Dimension.getWidth() / 2;
        int centerY = Dimension.getHeight() / 2;
        
        // Redimensionner tous les boutons
        relancerButton.setBounds(
            centerX - Dimension.scaleX(100), 
            centerY - Dimension.scaleY(30), 
            Dimension.scaleX(200), 
            Dimension.scaleY(50)
        );
        
        acceuil.setBounds(
            centerX - Dimension.scaleX(100), 
            centerY + Dimension.scaleY(100), 
            Dimension.scaleX(200), 
            Dimension.scaleY(50)
        );
        
        startGame.setBounds(
            Dimension.scaleX(30), 
            Dimension.getHeight() - Dimension.scaleY(180), 
            Dimension.scaleX(300), 
            Dimension.scaleY(55)
        );
        
        nextStageBtn.setBounds(
            centerX + Dimension.scaleX(230), 
            centerY + Dimension.scaleY(285), 
            Dimension.scaleX(180), 
            Dimension.scaleY(40)
        );
        
        quitter.setBounds(
            Dimension.getWidth() - Dimension.scaleX(150), 
            Dimension.scaleY(30), 
            Dimension.scaleX(100), 
            Dimension.scaleY(35)
        );
        
        boutique.setBounds(
            Dimension.getWidth() - Dimension.scaleX(320), 
            Dimension.scaleY(30), 
            Dimension.scaleX(140), 
            Dimension.scaleY(35)
        );
        
        // Ajuster les polices
        adjustFontSizes();
        
        // Recharger les images avec la nouvelle taille
        loadScaledImages();
    }
    
    private void loadScaledImages() {
        cachedObstacleImage = new ImageIcon(getClass().getResource("/Images/caisse.png")).getImage()
            .getScaledInstance(Dimension.scaleX(o.WIDTH_O), Dimension.scaleY(o.HEIGHT_O), Image.SCALE_SMOOTH);
        
        cachedCoinImage = new ImageIcon(getClass().getResource("/Images/coin.png")).getImage()
            .getScaledInstance(Dimension.scaleX(b.WIDTH_B), Dimension.scaleY(b.HEIGHT_B), Image.SCALE_SMOOTH);

    }
    
    private void adjustFontSizes() {
        // Ajuster les tailles de police pour les boutons
        Font buttonFont = new Font("Arial", Font.BOLD, Dimension.scaleFontSize(19));
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
        setPreferredSize(new java.awt.Dimension(originalWidth, originalHeight));
        this.c = character;
        this.o = obs;
        this.tir = t;
        this.b = bonus;
        this.i = inputs;
        this.lm = levelManager;
        
        // Initialiser les dimensions
        Model.Dimension.updateDimensions(originalWidth, originalHeight);

        initButtons();
        loadScaledImages();

        // Ajouter un écouteur pour détecter les changements de taille
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Model.Dimension.updateDimensions(getWidth(), getHeight());
                adjustButtonSizes();
                repaint();
            }
        });
        
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

    // Redessiner la vue pour ajouter nos différents éléments
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Mettre à jour les dimensions avant de dessiner
        Model.Dimension.updateDimensions(getWidth(), getHeight());
        
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
            if (!game_lose) {
                // Debug: Afficher les informations du personnage
                System.out.println("Dessin personnage - Position: (" + c.getCurrent_x() + "," + c.getCurrent_y() + ")");
                
                int charWidth = Dimension.scaleX(Character.WIDTH);
                int charHeight = Dimension.scaleY(Character.HEIGHT);
                
                // Debug: Vérifier les dimensions
              /*   System.out.println("Dimensions originales: " + Character.WIDTH + "x" + Character.HEIGHT);
                System.out.println("Dimensions redimensionnées: " + charWidth + "x" + charHeight);*/
                
                // Vérifier que l'image existe
                if (Character.characterSprite == null) {
                    // Dessiner un rectangle rouge pour visualiser la position
                    g.fillRect(Dimension.scaleX((int)c.getCurrent_x()), 
                              Dimension.scaleY((int)c.getCurrent_y()),
                              charWidth, charHeight);
                } else {
                       
                    int drawX = Dimension.scaleX((int)c.getCurrent_x());
                    int drawY = Dimension.scaleY((int)c.getCurrent_y());
                    
                 /*   System.out.println("characterSprite chargée - taille originale: " + 
                                     Character.characterSprite.getWidth(null) + "x" + 
                                     Character.characterSprite.getHeight(null));*/
                    
                    // Redimensionner et dessiner
                    BufferedImage resizedImage = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2dResized = resizedImage.createGraphics();
                    g2dResized.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2dResized.drawImage(Character.characterSprite, 0, 0, charWidth, charHeight, null);
                    g2dResized.dispose();
                    g.drawImage(resizedImage, drawX, drawY, this);
                    
                    
                    
                    // Dessiner un contour de débogage
                    g.drawRect(drawX, drawY, charWidth, charHeight);
                }
            

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

    // Méthode pour dessiner et afficher la boutique
    public void drawBoutique(Graphics g) {
        if (lm.getShowStore() == true) {
            // Un grand carré au centre de la fenetre représente la boutique
            int boutique_width = Dimension.scaleX(900);
            int boutique_height = Dimension.scaleY(700);
            int centerX = Dimension.getWidth() / 2;
            int centerY = Dimension.getHeight() / 2;
            
            g.setColor(new Color(220, 220, 220, 200)); // Couleur de fond de la boutique
            g.fillRect(centerX - boutique_width / 2, centerY - boutique_height / 2, boutique_width, boutique_height); 
            g.setColor(Color.BLACK); // Couleur du contour
            g.drawRect(centerX - boutique_width / 2, centerY - boutique_height / 2, boutique_width, boutique_height); 

            // Contenu de la boutique ici avec police adaptative
            int titleFontSize = Dimension.scaleFontSize(20);
            g.setFont(new Font("Arial", Font.BOLD, titleFontSize)); 
            g.setColor(Color.BLACK); // Couleur du texte
            
            FontMetrics metrics = g.getFontMetrics();
            String title = "Victoire !!! ...... Boutique ici";
            int titleWidth = metrics.stringWidth(title);
            
            g.drawString(title, centerX - titleWidth / 2, centerY - boutique_height / 2 + Dimension.scaleY(50)); 

            nextStageBtn.setVisible(true); // Afficher le bouton pour passer au niveau suivant
        } else {
            nextStageBtn.setVisible(false);
        }
    }

    // Méthode pour dessiner les bonus avec l'image coin.png
    public void drawBonus(Graphics g) {
        for (int i = 0; i < b.getPointBonus().size(); i++) {
            Point bonus = new Point((int) b.getPointBonus().get(i).getX(), (int) b.getPointBonus().get(i).getY());
            Point scaledPoint = Dimension.scalePoint(bonus);
            int width = Dimension.scaleX(b.WIDTH_B);
            int height = Dimension.scaleY(b.HEIGHT_B);
            
            g.drawImage(cachedCoinImage, scaledPoint.x, scaledPoint.y, width, height, null);
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

    // Méthode pour récupérer la liste des tirs et les afficher à l'écran
    public void drawTirs(Graphics g) {
        for (int i = 0; i < tir.getTirs().size(); i++) {
            g.setColor(Color.RED);
            Point tirPos = tir.getTirs().get(i).getPosition();
            Point scaledPos = Dimension.scalePoint(tirPos);
            int size = Dimension.scaleFontSize(8); // taille des tirs
            
            g.fillOval(scaledPos.x, scaledPos.y, size, size);
        }
    }

    // Méthode affichant tous les ennemis
    public void drawEnnemies(Graphics g) {
        List<Ennemies> ennemies = Ennemies.getListEnnemies();
        if (ennemies.isEmpty()) {
            System.out.println("[DEBUG] Aucun ennemi à dessiner");
            return;
        }
    
        for (Ennemies ennemi : ennemies) {
            // Validation de la position
            Point position = ennemi.getPosition();
            if (position == null) {
                System.err.println("[ERROR] Position null pour un ennemi");
                continue;
            }
    
            int x = Dimension.scaleX(position.x);
            int y = Dimension.scaleY(position.y);
            int width = Math.max(1, Dimension.scaleX(ennemi.getWidth(ennemi)));
            int height = Math.max(1, Dimension.scaleY(ennemi.getHeight(ennemi)));
    
            // Vérification que l'ennemi est visible à l'écran
            if (!isVisible(x, y, width, height)) {
                System.out.printf("[DEBUG] Ennemi hors écran - Type: %s @ (%d,%d)%n",
                                ennemi.getClass().getSimpleName(), x, y);
                continue;
            }
    
            try {
                Image enemyImage = null;
                String enemyType = "";
                
                if (ennemi instanceof Fantome) {
                    enemyImage = ((Fantome)ennemi).img;
                    enemyType = "Fantôme";
                } else if (ennemi instanceof Araignee) {
                    enemyImage = ((Araignee)ennemi).img;
                    enemyType = "Araignée";
                }
                //debug lg
                if (enemyImage == null) {
                    System.err.printf("[ERROR] Image manquante pour %s%n", enemyType);
                    continue;
                }
    
                // Redimensionnement optimal avec BufferedImage
                BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = resizedImage.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                                   RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.drawImage(enemyImage, 0, 0, width, height, null);
                g2d.dispose();
    
                g.drawImage(resizedImage, x, y, this);
                
                // Debug léger
                System.out.printf("[DRAW] %s - Pos: (%d,%d) Size: %dx%d%n",
                                enemyType, x, y, width, height);
                
            } catch (Exception e) {
                System.err.printf("[ERROR] Erreur dessin ennemi: %s%n", e.getMessage());
            }
        }
    }
    
    private boolean isVisible(int x, int y, int width, int height) {
        int margin = 100; // Marge pour les éléments partiellement visibles
        return x + width >= -margin && 
               y + height >= -margin && 
               x <= getWidth() + margin && 
               y <= getHeight() + margin;
    }

    // Méthode qui dessine les obstacles
    public void drawObstacle(Graphics g) {
        int i = 0;
        ArrayList<Point> obstacles = o.getObstacles();
        for (Point obstacle : obstacles) {
            Point scaledPoint = Dimension.scalePoint(obstacle);
            int width = Dimension.scaleX(o.WIDTH_O);
            int height = Dimension.scaleY(o.HEIGHT_O);
            
            // Afficher les images de caisse
            g.drawImage(cachedObstacleImage, scaledPoint.x, scaledPoint.y, width, height, null);
            i += 1;
        }
    }

    public void drawNiveau(Graphics g) {
        // Police adaptative pour le texte "Etage actuel"
        int fontSize = Dimension.scaleFontSize(14);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, fontSize));
        g.drawString("Etage actuel : ", Dimension.getWidth() - Dimension.scaleX(450), Dimension.scaleY(43));
        
        // Dessiner les 5 petits carrés avec des numéros
        for (int i = 0; i < 5; i++) {
            int x = Dimension.getWidth() - Dimension.scaleX(355) + Dimension.scaleX(i * 35);
            int y = Dimension.scaleY(30);
            int boxSize = Dimension.scaleFontSize(20);
            
            g.drawRect(x, y, boxSize, boxSize);
            // Remplir les carrés correspondant au niveau actuel
            if (i < lm.getNiveauActuel().getNiveau()) {
                g.setColor(new Color(255, 165, 80));
                g.fillRect(x, y, boxSize, boxSize);
                g.setColor(Color.BLACK);
            }
            // Dessiner le numéro au centre du carré
            int numFontSize = Dimension.scaleFontSize(12);
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
        int titleFontSize = Dimension.scaleFontSize(60);
        Font titleFont = new Font("Comic Sans MS", Font.BOLD, titleFontSize);
        g.setFont(titleFont);
        
        // Calculer la largeur du texte pour le centrer correctement
        FontMetrics metrics = g.getFontMetrics(titleFont);
        String title = "Bienvenue sur Nibelhein";
        int titleWidth = metrics.stringWidth(title);
        int centerX = Dimension.getWidth() / 2;
        int centerY = Dimension.getHeight() / 2;
        
        // Dessiner le contour rouge
        g.setColor(Color.RED);
        g.drawString(title, centerX - titleWidth / 2 - 2, centerY - 2);
        
        // Dessiner le texte vert
        g.setColor(Color.GREEN);
        g.drawString(title, centerX - titleWidth / 2, centerY);
    }

    // Méthode pour dessiner la barre de vie et les bonus collectés
    public void drawBarreVie(Graphics g) {
        // Positions et dimensions adaptatives
        // Suite de la méthode drawBarreVie(Graphics g)
        // Afficher le nombre de bonus récupérés en haut à droite
        int coinX = Dimension.getWidth() - Dimension.scaleX(135);
        int coinY = Dimension.scaleY(25);
        int coinWidth = Dimension.scaleX(b.WIDTH_B);
        int coinHeight = Dimension.scaleY(b.HEIGHT_B);
        
        g.drawImage(cachedCoinImage, coinX, coinY, coinWidth, coinHeight, null);
        
        g.setColor(Color.BLACK);
        int fontSize = Dimension.scaleFontSize(14);
        g.setFont(new Font("Arial", Font.PLAIN, fontSize));
        g.drawString("Pièces : " + c.getNombreBonus(), Dimension.getWidth() - Dimension.scaleX(98), Dimension.scaleY(43));
    }

    // Méthode pour afficher le gamelose et la page de pause selon le cas
    public void drawGameStop(Graphics g) {
        if (game_lose == true && game_running == true) {
            relancerButton.setVisible(true);
            acceuil.setVisible(true);
            
            // Calculer les dimensions du pop-up de fin de jeu
            int popupWidth = Dimension.scaleX(500);
            int popupHeight = Dimension.scaleY(500);
            int centerX = Dimension.getWidth() / 2;
            int centerY = Dimension.getHeight() / 2;
            int popupX = centerX - popupWidth / 2;
            int popupY = centerY - popupHeight / 2;
            
            // Bordure noire
            g.setColor(Color.BLACK);
            g.fillRect(popupX - Dimension.scaleX(2), popupY - Dimension.scaleY(2), 
                     popupWidth + Dimension.scaleX(4), popupHeight + Dimension.scaleY(4));
            
            // Fond blanc
            g.setColor(Color.WHITE);
            g.fillRect(popupX, popupY, popupWidth, popupHeight);
            
            // Titre "Game Over"
            int titleFontSize = Dimension.scaleFontSize(35);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, titleFontSize));
            
            FontMetrics titleMetrics = g.getFontMetrics();
            String gameOverText = "Game Over";
            int gameOverWidth = titleMetrics.stringWidth(gameOverText);
            
            g.drawString(gameOverText, centerX - gameOverWidth / 2, 
                       popupY + Dimension.scaleY(70));
            
            // Texte d'information
            int infoFontSize = Dimension.scaleFontSize(20);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, infoFontSize));
            
            FontMetrics infoMetrics = g.getFontMetrics();
            String infoText = "Nombre de pièces collectées : " + c.getNombreBonus();
            int infoWidth = infoMetrics.stringWidth(infoText);
            
            g.drawString(infoText, centerX - infoWidth / 2, 
                       popupY + Dimension.scaleY(130));
        }
        else {
            relancerButton.setVisible(false);
            acceuil.setVisible(false);
            nextStageBtn.setVisible(false);
        }
        
        // Informations de débogage (à commenter ou supprimer pour la version finale)
        System.out.println("État du jeu: game_running=" + game_running + ", game_lose=" + game_lose);
        System.out.println("Nombre d'ennemis: " + Ennemies.getListEnnemies().size());
        System.out.println("Image de la sorcière: " + (Character.characterSprite == null ? "null" : "chargée"));
    }

    // Méthode à appeler lors du redimensionnement de la fenêtre
    public void updateSize(int width, int height) {
        // Mettre à jour les dimensions globales
        Model.Dimension.updateDimensions(width, height);
        
        // Ajuster les boutons et autres éléments d'interface
        adjustButtonSizes();
        
        // Redessiner l'interface
        repaint();
    }
    
    // Méthode pour nettoyer les ressources avant de fermer l'application
    public void cleanup() {
        if (renderThread != null && renderThread.isAlive()) {
            renderThread.interrupt();
        }
    }
}