package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Controler.Character;
import Model.Tir;
import Model.Araignee;
import Model.Bonus;
// Ensure this import is correct and the class exists in the specified package
import Model.PositionAraignee;
import Model.Ennemies;
import Model.Fantome;
import Model.Obstacles;
import Controler.Collision;
import Controler.Inputs;
public class Affichage extends JPanel {

    // Constantes pour la taille de la fenetre
    public static final int X = 1500;
    public static final int Y = 1080;

    // Liste des points et des tailles des bonus
    private List<Point> bonusPoints = new ArrayList<>();
    private List<Integer> bonusSizes = new ArrayList<>();

    // Variables des autres classes
    Character c;
    private Tir tir;
    PositionAraignee position;
    private Araignee a ;
    private Bonus b;
    private Obstacles o; // Instance de la classe Obstacles
    private Inputs inputs;

    // Buttons du jeu
    private JButton relancerButton; // Bouton pour relancer la partie
    private JButton acceuil;  // Bouton pour quitter le jeu
    private JButton startGame;  // Bouton pour démarrer le jeu
    private JButton boutiqueGame;  // Bouton pour accéder à la boutique
    private JButton quitter; // Bouton pour quitter le jeu
    private JButton boutique;  // Bouton pour accéder à la boutique
    private JButton pauseButton;  // Bouton pour mettre en pause le jeu
    private JButton resumeButton;  // Bouton pour reprendre le jeu

    // Attribut pour lancer le jeu ou le mettre en pause
    public boolean game_running = false;
    // Attribut pour savoir si le jeu est perdu
    public boolean game_lose = false;
    // Attribut pour savoir si le jeu est en pause
    public boolean game_pause = false;

    // Attribut pour afficher la oboutique ou pas
    private boolean showStore = false;

    
    // Position de la barre de vie
    public static final int xBarreVie = 30;
    public static final int yBarreVie = 30;

    // Dimension barre de vie
    public static final int heightBarreVie = 20;  // Hauteur
    public static final int arcBarreVie = 10;  // Angle des bordures

    private Image coinImage; // Variable pour stocker l'image du coin

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

    // Constructeur
    public Affichage(Character c, Tir t, Araignee a,  PositionAraignee position, Bonus bonus, Inputs inputs,Obstacles o) {
        setPreferredSize(new Dimension(X, Y));
        this.c = c;
        this.tir = t;
        this.a = a;
        this.position = position;
        this.inputs = inputs;
        this.b = bonus;
        this.o = o; // Initialiser les obstacles

        /*// Initialisation de position si elle est nulle
        if (position == null) {
            this.position = new PositionAraignee(); // Exemple de position initiale
        } else {
            this.position = position;
        }*/

        // Initialisation des boutons à afficher lors du Game Over
        relancerButton = new JButton("Nouvelle Partie");
        acceuil = new JButton("Acceuil");
        startGame  = new JButton("Commencer une partie");
        boutiqueGame  = new JButton("Boutique");
        quitter  = new JButton("Quitter");
        boutique  = new JButton("Boutique");

        // Charger une icone dans le bouton startGame
        ImageIcon OriginalstartIcon = new ImageIcon("src/Images/start_bouton.png"); // Image originale pour l'icone du bouton
        Image resizedImageStart = OriginalstartIcon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT); // Redimensionner l'image
        ImageIcon startIcon = new ImageIcon(resizedImageStart); // Créer un ImageIcon avec l'image redimensionnée
        startGame.setIcon(startIcon); // Associer l'icône au bouton

        // Charger une icone dans le bouton Boutique
        ImageIcon OriginalboutiqueIcon = new ImageIcon("src/Images/boutique.png"); // Image originale pour l'icone du bouton
        Image resizedImageboutique = OriginalboutiqueIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT); // Redimensionner l'image
        ImageIcon boutiqueIcon = new ImageIcon(resizedImageboutique); // Créer un ImageIcon avec l'image redimensionnée
        boutique.setIcon(boutiqueIcon); // Associer l'icône au bouton

        // Personnaliser le bouton startGame de l'acceuil
        startGame.setFont(new Font("Arial", Font.BOLD, 20));
        startGame.setBackground(new Color(169, 169, 169));
        startGame.setForeground(Color.WHITE); // Couleur du texte
        startGame.setFocusPainted(false); // Retirer le contour par défaut quand on pointe sur le bouton

        // Personnaliser le bouton boutiqueGame du jeu perdu et du jeu en pause
        boutiqueGame.setFont(new Font("Arial", Font.BOLD, 18));
        boutiqueGame.setFocusPainted(false); // Retirer le contour par défaut quand on pointe sur le bouton

        // Personnaliser le bouton rejouer du jeu perdu et du jeu en pause
        relancerButton.setFont(new Font("Arial", Font.BOLD, 18));
        relancerButton.setFocusPainted(false); // Retirer le contour par défaut quand on pointe sur le bouton

        // Personnaliser le bouton Acceuil du jeu perdu et du jeu en pause
        acceuil.setFont(new Font("Arial", Font.BOLD, 18));
        acceuil.setFocusPainted(false); // Retirer le contour par défaut quand on pointe sur le bouton

        // Personnaliser le bouton Quitter de l'acceuil
        quitter.setFont(new Font("Arial", Font.BOLD, 15));
        quitter.setBackground(new Color(255, 0, 0));
        quitter.setForeground(Color.WHITE); // Couleur du texte
        quitter.setFocusPainted(false); // Retirer le contour par défaut quand on pointe sur le bouton

        // Personnaliser le bouton Boutique de l'acceuil
        boutique.setFont(new Font("Arial", Font.BOLD, 15));
        boutique.setBackground(new Color(169, 169, 169));
        boutique.setForeground(Color.WHITE); // Couleur du texte
        boutique.setFocusPainted(false); // Retirer le contour par défaut quand on pointe sur le bouton


        // Effet au survol du bouton startGame
        startGame.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startGame.setBackground(new Color(70, 130, 180)); // Couleur de fond au survol
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startGame.setBackground(new Color(169, 169, 169)); // Revenir à la couleur initiale
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
            boutiqueGame.setVisible(false);
            
            repaint(); // Redessiner l'interface pour appliquer les changements
        });


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
        });

        // Acceder à la boutique pendant la partie
        boutiqueGame.addActionListener(e -> {
            showStore = true;
        });

        // Acceder à la boutique
        boutique.addActionListener(e -> {
            showStore = true;
        });

        // Ajouter les boutons au JPanel mais les rendre invisibles par défaut
        setLayout(null); // Utiliser un layout null pour positionner les boutons
        relancerButton.setBounds(X / 2 - 100, Y / 2 - 30, 200, 50);
        acceuil.setBounds(X / 2 - 100, Y / 2 + 100, 200, 50);
        startGame.setBounds(30, Y - 180, 300, 55);
        boutiqueGame.setBounds(X / 2 - 100, Y / 2 + 35, 200, 50);
        boutique.setBounds(X - 320, 30, 140, 35);
        quitter.setBounds(X - 150, 30, 100, 35);

        // Rendre les boutons invisibles par défaut
        relancerButton.setVisible(false);
        acceuil.setVisible(false);
        startGame.setVisible(false);
        boutiqueGame.setVisible(false);
        quitter.setVisible(false);
        boutique.setVisible(false);

        // Ajouter les boutons au panel
        add(relancerButton);
        add(acceuil);
        add(startGame);
        add(boutiqueGame);
        add(boutique);
        add(quitter);

        // Charger l'image du coin (ici on suppose qu'elle s'appelle "coin.png")
        coinImage = new ImageIcon("src/Images/coin.png").getImage()
                .getScaledInstance(25, 25, Image.SCALE_DEFAULT); 
    }

    // Override de la méthode paint qui va afficher l'image "character.png" au
    // centre de l'écran
     @Override
    public void paint(Graphics g) {
         // Ajouter un fond d'ecran pour le jeu
         Image bg = new ImageIcon("src/Images/bg.png").getImage();
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
                g.drawRect((int)c.getCurrent_x(), (int)c.getCurrent_y(), Character.WIDTH, Character.HEIGHT);

                // Dessiner les tirs
                drawTirs(g);

                // Dessiner les bonus
                drawBonus(g);

                // Dessiner les araignées
                drawAraignee(g);

                // Dessiner les ennemis
                drawEnnemies(g);
            
                // Dessiner la barre de vie
                drawBarreVie(g);

                // Dessiner les obstacles
                drawObstacle(g);

                // Rendre les boutons pour relancer et quitter invisibles
                relancerButton.setVisible(false);
                acceuil.setVisible(false);
                boutiqueGame.setVisible(false);

            } 
            else {
                // Si le jeu est perdu, afficher un écran de fin de partie avec game over
                drawGameStop(g);

            }

            // Rendre les boutons startGame, boutique et quitter invisible car le jeu a démarré
            startGame.setVisible(false);
            boutique.setVisible(false);
            quitter.setVisible(false);
        }
            

           
 
    }
            

    /*@Override
    public void paint(Graphics g) {
        super.paint(g);
        // Dessiner le personnage au centre de l'écran
        g.drawImage(Character.characterSprite, (int) c.getCurrent_x(), (int) c.getCurrent_y(), null);

        // Recuperer la liste des tirs et les afficher à l'écran en tenant compte de la classe Tir
        for (int i = 0; i < tir.getTirs().size(); i++) {
            g.setColor(Color.RED);
            int x = tir.getTirs().get(i).getPosition().x;
            int y = tir.getTirs().get(i).getPosition().y;
            g.fillOval(x, y, 10, 10);
        }

        // Afficher le nombre de bonus récupérés en haut à droite
        g.drawImage(coinImage, X - 130, 21, null);
        g.setColor(Color.BLACK);
        g.drawString("Pièces : " + c.getNombreBonus(), X - 98, 40);

        // Dessiner les araignées
        drawAraignee(g);

        // Dessiner les ennemis
       // drawEnnemies(g);

        // Dessiner une barre de vie rouge dans un contour noir et des bordures arrondies
        g.setColor(Color.GRAY);
        g.fillRoundRect(xBarreVie, yBarreVie, c.maxVie*2, heightBarreVie, arcBarreVie, arcBarreVie);
        g.setColor(Color.RED);
        g.fillRoundRect(xBarreVie, yBarreVie, c.getVie()*2, heightBarreVie, arcBarreVie, arcBarreVie);
        g.setColor(Color.BLACK);
        g.drawRoundRect(xBarreVie, yBarreVie, c.maxVie*2, heightBarreVie, arcBarreVie, arcBarreVie);
        g.fillRect(X / 2, Y / 2, 10, 10);

        // Recuperer la liste des tirs et les afficher sachant x et y c'est leur
        // position par rapport au centre de la fenetre
        if (tir != null && tir.getTirs().size() > 0) {
            for (int j = 0; j < tir.getTirs().size(); j++) {
                System.err.println("x: "+tir.getTirs().get(j).getPosition().x+"  Y: "+tir.getTirs().get(j).getPosition().y);
                g.setColor(Color.RED);
                g.fillOval(tir.getTirs().get(j).getPosition().x, tir.getTirs().get(j).getPosition().y, 10, 10);
            }
        }

        // Dessiner les bonus
        drawBonus(g);
        // Dessiner les obstacles
        drawObstacle(g);
    }*/

    // Méthode pour dessiner les bonus avec l'image coin.png
    public void drawBonus(Graphics g) {
        for (int i = 0; i < b.getPointBonus().size(); i++) {
            g.drawImage(coinImage, b.getPointBonus().get(i).x, b.getPointBonus().get(i).y, null);
        }
    }

    // Methode pour Récupérer la liste des tirs et les afficher à l'écran en tenant compte de la classe Tir
    public void drawTirs(Graphics g) {
        for (int i = 0; i < tir.getTirs().size(); i++) {
            g.setColor(Color.RED);
            int x = tir.getTirs().get(i).getPosition().x;
            int y = tir.getTirs().get(i).getPosition().y;
            g.fillOval(x, y, 8, 8);
            g.drawRect(tir.getTirs().get(i).getPosition().x, tir.getTirs().get(i).getPosition().y, 8, 8);
        }
    }


    // Procédure affichant tous les ennemis
    public void drawEnnemies(Graphics g) {

        // Appel de la méthode statique sans instance
        List<Ennemies> ennemies = Ennemies.getListEnnemies();

        // Boucle affichant tous les ennemis
        for (Ennemies ennemi : ennemies) {

            // Vérification si l'ennemi est un Fantome
            if (ennemi instanceof Fantome) {
                Fantome fantome = (Fantome) ennemi; // Casting en Fantome
                g.drawImage(fantome.img, (int) fantome.getPosition().getX(), (int) fantome.getPosition().getY(), null);
            }
        }
    }

    // Ancienne procédure dessinant les araignées, inutilisée, à retirer lorsque la
    // classe Ennemie sera bien implémentée
    public void drawAraignee(Graphics g) {

        ArrayList<Point> araignee = a.getPosition();

        for (Point araigneP : araignee) {
            // afficher les images des araignées
            g.drawImage(Araignee.araigneeSprite, araigneP.x, araigneP.y, null);
            // Afficher un point rouge qui represente l'araignée
            g.setColor(Color.RED);
            g.drawOval(X, Y, 5, 5);
            // Si le joueur touche une araignée, on appelle la méthode toucher de la classe Araignée
            //a.detecterCollisionAraigneeJoueur(araigneP);
        }

        // faire réaparaitre des araignées s'il reste moins de 4 (Juste pour le fun !)
        if (a.getNombreAraignee() < 4) {
            a.ListePosition();
        }
    }

    //methode qui dessine les obstacles
    public void drawObstacle(Graphics g){
        int i = 0;
        ArrayList<Point> obstacles = o.getObstacles();
        for(Point obstacle : obstacles){
            g.setColor(Color.BLUE);
            g.fillRect(obstacle.x, obstacle.y, Obstacles.WIDTH_O, Obstacles.HEIGHT_O);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(i), obstacle.x + Obstacles.WIDTH_O / 2, obstacle.y + Obstacles.HEIGHT_O / 2);
            i+=1;
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
        g.fillRoundRect(xBarreVie, yBarreVie, c.maxVie * 2, heightBarreVie, arcBarreVie, arcBarreVie);
        g.setColor(Color.RED);
        g.fillRoundRect(xBarreVie, yBarreVie, c.getVie() * 2, heightBarreVie, arcBarreVie, arcBarreVie);
        g.setColor(Color.BLACK);
        g.drawRoundRect(xBarreVie, yBarreVie, c.maxVie * 2, heightBarreVie, arcBarreVie, arcBarreVie);
        // Afficher le nombre de bonus récupérés en haut à droite
        g.drawImage(coinImage, X - 135, 25, null);
        g.setColor(Color.BLACK);
        g.drawString("Pièces : " + c.getNombreBonus(), X - 98, 40);
    }

    // Méthode pour afficher le gamelose et la page de pause selon le cas
    public void drawGameStop(Graphics g) {
        // Rendre les boutons visibles
        if (game_lose == true && game_running == true) {
            relancerButton.setVisible(true);
            acceuil.setVisible(true);
            boutiqueGame.setVisible(true);
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
            boutiqueGame.setVisible(false);
        }
    }


}
