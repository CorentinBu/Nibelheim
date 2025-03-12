package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import Controler.Character;
import Model.Tir;
import Model.Araignee;
import Model.Position;


public class Affichage extends JPanel {
    public static final int X = 1920;
    public static final int Y = 1080;


    Character c;
    private Tir tir;
    Position position;
    private Araignee a = new Araignee(position,c);

    

    public Affichage(Character c, Tir t,Araignee a, Position position) {
        setPreferredSize(new Dimension(X, Y));
        this.c = c;
        this.tir = t;
        this.a=a;
        this.position=position;
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
                System.err.println("x: "+tir.getTirs().get(j).x+"  Y: "+tir.getTirs().get(j).y);
                g.setColor(Color.RED);
                g.fillOval(tir.getTirs().get(j).x, tir.getTirs().get(j).y, 10, 10);
            }
        }
        /*afficher les araignées */
        drawAraignee(g);
       
    }

    /*dessiner les araignées */
    public void drawAraignee(Graphics g){
        ArrayList<Point> araignee = a.getPosition();
        for(Point araigneP : araignee){
            g.fillOval(araigneP.x, araigneP.y, position.LARGEUR_A, position.HAUTEUR_A);
            a.toucher(araigneP);
        }
    }
    
}
