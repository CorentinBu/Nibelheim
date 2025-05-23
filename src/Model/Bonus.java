package Model;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.sound.sampled.Clip;

//Cette classe décrit les bonus que le joueur peut ramasser.
//Les bonus sont lâchés par les ennemis lorsqu'ils sont détruits.
//Les bonus sont aimantés, attirés par le joueur

public class Bonus {

    // Taille des bonus
    public static final int WIDTH_B = 25;
    public static final int HEIGHT_B = 25;

    // Hitbox du bonus
    public Rectangle hitboxBonus;

    // Instance de la classe utiles
    private ArrayList<Rectangle> pointBonus = new ArrayList<Rectangle>();

    // Getter pour récupérer la liste des points de bonus
    public ArrayList<Rectangle> getPointBonus() {
        return pointBonus;
    }

    // Methode pour ajouter un bonus à la liste pointBonus
    public void addBonus(Point p) {
        // A un x et y aléatoires entre -0.5 et 0.5 aux alentours de p
        Point newP = new Point(p.x + (int) (Math.random() * WIDTH_B) - WIDTH_B / 4,
                p.y + (int) (Math.random() * HEIGHT_B) - HEIGHT_B / 4);

        Rectangle bonus = new Rectangle(newP.x, newP.y, WIDTH_B, HEIGHT_B);
        pointBonus.add(bonus);
    }

    // Methode pour supprimer un bonus de pointBonus
    public void removeBonus(Point point) {
        for (int i = 0; i < pointBonus.size(); i++) {
            if (pointBonus.get(i).getLocation().equals(point)) {
                pointBonus.remove(i);
                break;
            }
        }
    }

    // Methode pour reinitialiser la liste des bonus
    public void resetBonus() {
        pointBonus.clear();
    }

}