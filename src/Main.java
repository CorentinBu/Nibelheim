import javax.swing.*;
import View.*;
import Controler.*;
import Controler.Character;
import Model.*;
import java.awt.Point;

public class Main {
    public static void main(String[] args) throws Exception {

        JFrame f = new JFrame("Nibelheim");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Inputs inputs = new Inputs();
        f.addKeyListener(inputs);

        Character c = new Character(inputs);
        Tir t = new Tir(c);

        Bonus b = new Bonus();

        Position position = new Position(100, 100);  // Exemple : position de départ (100, 100)
        Araignee araignee = new Araignee(position,c, t, b);
        

        Affichage a = new Affichage(c, t, araignee, position);
        f.add(a);
        f.pack();
        f.setVisible(true);

        ReactionClic m = new ReactionClic(t);
        PositionSouris ps = new PositionSouris(m);
        a.addMouseListener(m); 
        Avancer_tir avancer_tir = new Avancer_tir(t);
        Redessine r = new Redessine(a);

        b.start();
        b.spawn();


        /*Modifier la position des araignées */
        MouvementAraignee mvtA = new MouvementAraignee(position);
        Collision col = new Collision(c, t, araignee, a);
        col.start();
        mvtA.start();
        avancer_tir.start();
        ps.start();
        r.start();
        c.start();

    }
}
