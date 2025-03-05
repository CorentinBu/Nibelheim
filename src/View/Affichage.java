package View;

import javax.swing.*;
import java.awt.*;
import Controler.Character;
import Model.Tir;

public class Affichage extends JPanel {
    public static final int X = 1920;
    public static final int Y = 1080;

    public static final Image characterSprite = new ImageIcon("src/Images/character.png").getImage()
            .getScaledInstance(100, 100, Image.SCALE_DEFAULT);

    Character c;
    private Tir tir;

    public Affichage(Character c, Tir t) {
        setPreferredSize(new Dimension(X, Y));
        this.c = c;
        this.tir = t;
    }

    // Override de la méthode paint qui va afficher l'image "character.png" au
    // centre de l'écran
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(characterSprite, c.current_x, c.current_y, null);

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
    }

}
