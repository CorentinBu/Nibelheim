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
import java.awt.Image;

public class Affichage extends JPanel {
    public static final int X = 1920;
    public static final int Y = 1080;

    private List<Point> bonusPoints = new ArrayList<>();
    private List<Integer> bonusSizes = new ArrayList<>();
    private Bonus b = new Bonus();
    Character c;
    private Tir tir;

    Position position;
    private Araignee a = new Araignee(position, c, tir, b);


    private Image coinImage; // Variable pour stocker l'image du coin

    public Affichage(Character c, Tir t, Araignee a, Position position) {
        setPreferredSize(new Dimension(X, Y));
        this.c = c;
        this.tir = t;
        // Initialisation de position si c'est null
        if (position == null) {
            this.position = new Position(100, 100); // Exemple de position initiale
        } else {
            this.position = position;
        }
        this.a = a;

        // Charger l'image du coin (ici on suppose qu'elle s'appelle "coin.png")
        coinImage = new ImageIcon("src/Images/coin.png").getImage()
                .getScaledInstance(50, 50, Image.SCALE_DEFAULT);
    }

    // Override de la méthode paint qui va afficher l'image "character.png" au
    // centre de l'écran
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(Character.characterSprite, (int) c.current_x, (int) c.current_y, null);

        g.setColor(Color.BLACK);
        // Carré Noir au centre de la fenetre comme point de départ du tir
        g.fillRect(X / 2, Y / 2, 10, 10);

        // Récupérer la liste des tirs et les afficher
        if (tir != null && tir.getTirs().size() > 0) {
            for (int j = 0; j < tir.getTirs().size(); j++) {
                g.setColor(Color.RED);
                g.fillOval(tir.getTirs().get(j).x, tir.getTirs().get(j).y, 10, 10);
            }
        }

        // Dessiner les bonus
        for (int i = 0; i < bonusPoints.size(); i++) {
            g.setColor(Color.GREEN);
            g.fillOval(bonusPoints.get(i).x, bonusPoints.get(i).y, bonusSizes.get(i), bonusSizes.get(i));
        }

        // Afficher le nombre de bonus récupérés en haut à gauche
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        // Afficher l'image du coin (en haut à gauche)
        g.drawImage(coinImage, 20, 50, null);

        // Dessiner les araignées
        drawAraignee(g);

        // Dessiner les bonus
        drawBonus(g);
    }

  //Metode pour dessiner les bonus avec l'image coin.png
    public void drawBonus(Graphics g) {
        for (int i = 0; i < b.getPointBonus().size(); i++) {
            g.drawImage(coinImage, b.getPointBonus().get(i).x, b.getPointBonus().get(i).y, null);
        }
    }



    /* Dessiner les araignées */
    public void drawAraignee(Graphics g) {
        ArrayList<Point> araignee = a.getPosition();
        for (Point araigneP : araignee) {
            // Afficher les images des araignées
            g.drawImage(Araignee.araigneeSprite, araigneP.x, araigneP.y, null);
            // Si le joueur touche une araignée, on appelle la méthode toucher de la classe
            // Araignée
            a.toucher(araigneP);
        }

        // Faire réapparaître des araignées s'il reste moins de 4 (Juste pour le fun !)
        if (a.getNombreAraignee() < 4) {
            a.Listeposition();
        }
    }
}