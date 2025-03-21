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
    private Araignee a = new Araignee(position, c, tir);

    // Constructeur
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

        // Dessiner le sprite de la sorcière
        g.drawImage(Character.characterSprite, c.current_x, c.current_y, null);

        // Carré Noir au centre de la fenetre comme point de départ du tir
        g.setColor(Color.BLACK);
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
