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
    // Dimensions de la vue
    public static final int X = 1920;
    public static final int Y = 1080;

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

        // Initialisation de position si c'est elle est nulle
        if (position == null) {
            this.position = new Position(100, 100); // Exemple de position initiale
        } else {
            this.position = position;
        }
        this.a = a;

        // Charger l'image du coin (ici on suppose qu'elle s'appelle "coin.png")
        coinImage = new ImageIcon("src/Images/coin.png").getImage()
                .getScaledInstance(25, 25, Image.SCALE_DEFAULT);
    }

    // Redessiner la vue pour ajouter nos différents éléments
    @Override
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
        drawEnnemies(g);

        // Dessiner une barre de vie rouge dans un contour noir et des bordures arrondies
        g.setColor(Color.GRAY);
        g.fillRoundRect(xBarreVie, yBarreVie, c.maxVie*2, heightBarreVie, arcBarreVie, arcBarreVie);
        g.setColor(Color.RED);
        g.fillRoundRect(xBarreVie, yBarreVie, c.getVie()*2, heightBarreVie, arcBarreVie, arcBarreVie);
        g.setColor(Color.BLACK);
        g.drawRoundRect(xBarreVie, yBarreVie, c.maxVie*2, heightBarreVie, arcBarreVie, arcBarreVie);

        // Dessiner les bonus
        drawBonus(g);
    }

  //Metode pour dessiner les bonus avec l'image coin.png
    public void drawBonus(Graphics g) {
        for (int i = 0; i < b.getPointBonus().size(); i++) {
            g.drawImage(coinImage, b.getPointBonus().get(i).x, b.getPointBonus().get(i).y, null);
        }
    }

    // Méthode pour dessiner les ennemis
    public void drawEnnemies(Graphics g) {
        // Appel de la méthode statique sans instance
        List<Ennemies> ennemies = Ennemies.getListEnnemies();
        for (Ennemies ennemi : ennemies) {
            // Vérification si l'ennemi est un Fantome
            if (ennemi instanceof Fantome) {
                Fantome fantome = (Fantome) ennemi; // Casting en Fantome
                g.drawImage(fantome.img, (int) fantome.getPosition().getX(), (int) fantome.getPosition().getY(), null);
                System.out.println("Position x : " + fantome.getPosition().getX());
                // System.out.println("Position y : "+fantome.getPosition().getY());
                // System.out.println("img : "+fantome.img);
            }
        }
    }

    // Méthode pour dessiner les araignées
    public void drawAraignee(Graphics g) {
        ArrayList<Point> araignee = a.getPosition();
        for (Point araigneP : araignee) {
            // Afficher les images des araignées
            g.drawImage(Araignee.araigneeSprite, araigneP.x, araigneP.y, null);
            // Afficher un point rouge qui represente l'araignée
            g.setColor(Color.RED);
            g.drawOval(X, Y, 5, 5);
            // Si le joueur touche une araignée, on appelle la méthode toucher de la classe Araignée
            a.detecterCollisionAraigneeJoueur(araigneP);
        }

        // Faire réapparaître des araignées s'il reste moins de 4 (Juste pour le fun !)
        if (a.getNombreAraignee() < 4) {
            a.ListePosition();
        }
    }
}