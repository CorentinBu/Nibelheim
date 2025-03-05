package View;

import javax.swing.*;
import java.awt.*;
import Controler.Character;

public class Affichage extends JPanel {
    public static final int X = 1920;
    public static final int Y = 1080;

    public static final Image characterSprite = new ImageIcon("src/Images/character.png").getImage()
            .getScaledInstance(100, 100, Image.SCALE_DEFAULT);

    Character c;

    public Affichage(Character c) {
        setPreferredSize(new Dimension(X, Y));
        this.c = c;
    }

    // Override de la méthode paint qui va afficher l'image "character.png" au
    // centre de l'écran
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(characterSprite, c.current_x, c.current_y, null);
    }

}
