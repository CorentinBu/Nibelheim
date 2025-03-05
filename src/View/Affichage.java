package View;
import javax.swing.*;
import java.awt.*;
import Model.Tir;

public class Affichage extends JPanel{

    // Instances de classes utiles
    private Tir tir;

    // Constantes
    public static final int X = 1920;
    public static final int Y = 1080;

    // constructeur pour la classe
    public Affichage(Tir tir){
        this.tir = tir;
        setPreferredSize(new Dimension(X, Y));
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.BLACK);
        // Carré Noir au centre de la fenetre comme point de départ du tir
        g.setColor(Color.BLACK);
        g.fillRect(X/2, Y/2, 10, 10);

        // Recuperer la liste des tirs et les afficher sachant x et y c'est leur position par rapport au centre de la fenetre
        if (tir != null && tir.getTirs().size() > 0){
            for (int j = 0; j < tir.getTirs().size(); j++){
                g.setColor(Color.RED);
                g.fillOval(tir.getTirs().get(j).x, tir.getTirs().get(j).y, 10, 10);
            }
        }
    }
}