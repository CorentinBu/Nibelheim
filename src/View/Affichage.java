package View;

import javax.print.attribute.standard.MediaSize.Engineering;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Controler.Character;
import Model.Tir;
import Model.Araignee;
import Model.Position;
import Model.Ennemies;
import Model.Fantome;

public class Affichage extends JPanel {
    public static final int X = 1920;
    public static final int Y = 1080;

    private List<Point> bonusPoints = new ArrayList<>();
    private List<Integer> bonusSizes = new ArrayList<>();

    Character c;
    private Tir tir;

    Position position;
    private Araignee a = new Araignee(position, c, tir);
    // private Ennemies e = new Ennemies(10, 10, 10, new Point(100, 100),
    // Character.characterSprite);

    public Affichage(Character c, Tir t, Araignee a, Position position) {
        setPreferredSize(new Dimension(X, Y));
        this.c = c;
        this.tir = t;
        this.a = a;
        this.position = position;
    }

    // Override de la méthode paint qui va afficher l'image "character.png" au
    // centre de l'écran
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(Character.characterSprite, c.current_x, c.current_y, null);

        g.setColor(Color.BLACK);
        // Carré Noir au centre de la fenetre comme point de départ du tir
        g.setColor(Color.BLACK);
        g.fillRect(X / 2, Y / 2, 10, 10);

        // Recuperer la liste des tirs et les afficher sachant x et y c'est leur
        // position par rapport au centre de la fenetre
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

        /* afficher les araignées */
        // drawAraignee(g);
        drawEnnemies(g);

    }

    public void drawBonus(Point p, int size) {
        bonusPoints.add(p);
        bonusSizes.add(size);
        repaint();
    }

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

    /* dessiner les araignées */
    public void drawAraignee(Graphics g) {
        ArrayList<Point> araignee = a.getPosition();
        for (Point araigneP : araignee) {
            // afficher les images des araignées
            g.drawImage(Araignee.araigneeSprite, araigneP.x, araigneP.y, null);
            // si le joueur touche une araignée, on appelle la méthode toucher de la classe
            // Araignée
            a.toucher(araigneP);
        }

        // faire réaparaitre des araignées s'il reste moins de 4 (Juste pour le fun !)
        if (a.getNombreAraignee() < 4) {
            a.Listeposition();
        }
    }

}
