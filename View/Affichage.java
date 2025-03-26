package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import Controler.Character;
import Model.Tir;
import Model.Araignee;
import Model.Bonus;
import Model.Position;
import Model.Ennemies;
import Model.Fantome;

public class Affichage extends JPanel {

    // Dimensions de la vue, statiques
    public static final int X = 1920;
    public static final int Y = 1080;

    // Buttons du jeu
    private JButton relancerButton; // Bouton pour relancer la partie
    private JButton quitterButton;  // Bouton pour quitter le jeu

    // Attribut pour lancer le jeu ou le mettre en pause
    public boolean game_running = true;

    // Attribut pour savoir si le jeu est perdu
    public boolean game_lose = false;

    // Instances de classe utiles
    private Bonus b;
    Character c;
    private Tir tir;
    Position position;
    private Araignee a = new Araignee(position, c, tir, b);

    // Position de la barre de vie
    public static final int xBarreVie = 20;
    public static final int yBarreVie = 20;

    // Dimension barre de vie
    public static final int heightBarreVie = 20;  // Hauteur
    public static final int arcBarreVie = 10;  // Angle des bordures

    private Image coinImage; // Variable pour stocker l'image du coin

    public Affichage(Character c, Tir t, Araignee a, Position position, Bonus bonus) {
        setPreferredSize(new Dimension(X, Y));
        this.c = c;
        this.tir = t;
        this.b = bonus;

        // Initialisation de position si elle est nulle
        if (position == null) {
            this.position = new Position(100, 100); // Exemple de position initiale
        } else {
            this.position = position;
        }
        this.a = a;

        // Initialisation des boutons à afficher lors du Game Over
        relancerButton = new JButton("Relancer");
        quitterButton = new JButton("Quitter");

        // Actions des boutons
        relancerButton.addActionListener(e -> {
            game_lose = false;  // Réinitialise la partie
            c.setVie(c.maxVie); // Réinitialise les points de vie du joueur
            c.setNombreBonus(0); // Réinitialise le nombre de bonus
            a.ListePosition(); // Réinitialise les araignées
            repaint(); // Redessine la vue
        });

        // Quitter le jeu et fermer la fenêtre
        quitterButton.addActionListener(e -> System.exit(0));

        // Ajouter les boutons au JPanel mais les rendre invisibles par défaut
        setLayout(null); // Utiliser un layout null pour positionner les boutons
        relancerButton.setBounds(X / 2 - 100, Y / 2 + 30, 200, 50);
        quitterButton.setBounds(X / 2 - 100, Y / 2 + 90, 200, 50);
        relancerButton.setVisible(false);
        quitterButton.setVisible(false);
        add(relancerButton);
        add(quitterButton);

        // Charger l'image du coin (ici on suppose qu'elle s'appelle "coin.png")
        coinImage = new ImageIcon("src/Images/coin.png").getImage()
                .getScaledInstance(25, 25, Image.SCALE_DEFAULT);
    }

    // Redessiner la vue pour ajouter nos différents éléments
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Ajouter l'image bg.png en fond d'écran du JPanel
        Image bg = new ImageIcon("src/Images/bg.png").getImage();
        g.drawImage(bg, 0, 0, X, Y, null);

        // Si le joueur n'a plus de points de vie, le jeu est perdu
        if (c.getVie() < 0) {
            game_lose = true;
        }

        // Si le jeu est lancé, on affiche les éléments graphiques, sinon on affiche un bouton pour lancer le jeu
        if (!game_running) {
            // Afficher un message ou un bouton pour démarrer le jeu
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Appuyez sur un bouton pour commencer", X / 2 - 200, Y / 2);
        } else {
            if (!game_lose) {   // Si le jeu n'est pas perdu, on affiche les éléments graphiques

                // Dessiner le personnage au centre de l'écran
                g.drawImage(Character.characterSprite, (int) c.getCurrent_x(), (int) c.getCurrent_y(), null);

                // Récupérer la liste des tirs et les afficher à l'écran en tenant compte de la classe Tir
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
                drawEnnemies(g);

                // Dessiner une barre de vie rouge dans un contour noir et des bordures arrondies
                g.setColor(Color.GRAY);
                g.fillRoundRect(xBarreVie, yBarreVie, c.maxVie * 2, heightBarreVie, arcBarreVie, arcBarreVie);
                g.setColor(Color.RED);
                g.fillRoundRect(xBarreVie, yBarreVie, c.getVie() * 2, heightBarreVie, arcBarreVie, arcBarreVie);
                g.setColor(Color.BLACK);
                g.drawRoundRect(xBarreVie, yBarreVie, c.maxVie * 2, heightBarreVie, arcBarreVie, arcBarreVie);

                // Dessiner les bonus
                drawBonus(g);

                // Rendre les boutons pour relancer et quitter invisibles
                relancerButton.setVisible(false);
                quitterButton.setVisible(false);

            } else {
                // Si le jeu est perdu, afficher un écran de fin de partie
                g.setColor(Color.WHITE);
                g.fillRect(X / 2 - 250, Y / 2 - 250, 500, 500);
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString("Game Over", X / 2 - 50, Y / 2 - 150);
                g.setColor(Color.BLACK);
                g.drawString("Score : " + c.getNombreBonus(), X / 2 - 50, Y / 2 - 120);

                // Rendre les boutons pour restart et quitter visibles
                relancerButton.setVisible(true);
                quitterButton.setVisible(true);
            }
        }
    }

    // Méthode pour dessiner les bonus avec l'image coin.png
    public void drawBonus(Graphics g) {
        for (int i = 0; i < b.getPointBonus().size(); i++) {
            g.drawImage(coinImage, b.getPointBonus().get(i).x, b.getPointBonus().get(i).y, null);
        }
    }

    // Méthode pour dessiner les ennemis
    public void drawEnnemies(Graphics g) {
        List<Ennemies> ennemies = Ennemies.getListEnnemies();
        for (Ennemies ennemi : ennemies) {
            if (ennemi instanceof Fantome) {
                Fantome fantome = (Fantome) ennemi;
                g.drawImage(fantome.img, (int) fantome.getPosition().getX(), (int) fantome.getPosition().getY(), null);
            }
        }
    }

    // Méthode pour dessiner les araignées
    public void drawAraignee(Graphics g) {
        ArrayList<Point> araignee = a.getPosition();
        for (Point araigneP : araignee) {
            g.drawImage(Araignee.araigneeSprite, araigneP.x, araigneP.y, null);
            g.setColor(Color.RED);
            g.drawOval(araigneP.x, araigneP.y, 5, 5);
            a.detecterCollisionAraigneeJoueur(araigneP);
        }

        if (a.getNombreAraignee() < 4) {
            a.ListePosition();
        }
    }
}