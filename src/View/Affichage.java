package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Controler.Character;
import Model.*;

public class Affichage extends JPanel {

    // Constantes pour la taille de la fenetre
    public static final int X = 1920;
    public static final int Y = 1080;

    // Liste des points et des tailles des bonus
    private List<Point> bonusPoints = new ArrayList<>();
    private List<Integer> bonusSizes = new ArrayList<>();

    // Variables des autres classes
    Character c;
    private Tir tir;
    Position position;

    // Constructeur
    public Affichage(Character c, Tir t, Position position) {
        setPreferredSize(new Dimension(X, Y));
        this.c = c;
        this.tir = t;
        this.position = position;
    }

    // Override de la méthode paint qui va afficher l'image "character.png" au
    // centre de l'écran
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.drawRect(c.current_x, c.current_y, c.WIDTH, c.HEIGHT);
        // Dessiner le sprite de la sorcière
        g.drawImage(Character.characterSprite, c.current_x, c.current_y, null);

        // Carré Noir au centre de la fenetre comme point de départ du tir
        g.setColor(Color.BLACK);
        g.setColor(Color.BLACK);
        g.fillRect(X / 2, Y / 2, 10, 10);

        // Recuperer la liste des tirs et les afficher sachant x et y c'est leur
        // position par rapport au centre de la fenetre
        for (int i = 0; i < tir.getTirs().size(); i++) {
            g.setColor(Color.RED);
            int x = tir.getTirs().get(i).getPosition().x;
            int y = tir.getTirs().get(i).getPosition().y;
            g.fillOval(x, y, 10, 10);
            g.drawRect(tir.getTirs().get(i).getPosition().x, tir.getTirs().get(i).getPosition().y, 10, 10);
        }

        // Dessiner les bonus (faut penser à le changer en utilisant la procédure)
        for (int i = 0; i < bonusPoints.size(); i++) {
            g.setColor(Color.GREEN);
            g.fillOval(bonusPoints.get(i).x, bonusPoints.get(i).y, bonusSizes.get(i), bonusSizes.get(i));
        }

        // Aficher tous les ennemis
        drawEnnemies(g);
    }

    // Inutilisée mais à mettre dans le paint
    public void drawBonus(Point p, int size) {
        bonusPoints.add(p);
        bonusSizes.add(size);
        repaint();
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
                g.drawRect((int) fantome.getPosition().getX(), (int) fantome.getPosition().getY(), Ennemies.WIDTH, Ennemies.HEIGHT);
            }
        }
    }

}
