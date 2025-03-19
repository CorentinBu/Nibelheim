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

        Position position = new Position();
        Araignee araignee = new Araignee(position, c, t);

        Affichage a = new Affichage(c, t, araignee, position);
        f.add(a);
        f.pack();
        f.setVisible(true);

        ReactionClic m = new ReactionClic(t);
        PositionSouris ps = new PositionSouris(m);
        a.addMouseListener(m);
        Avancer_tir avancer_tir = new Avancer_tir(t);
        Redessine r = new Redessine(a);

        Bonus b = new Bonus(a);
        b.start();
        b.spawn(new Point(500, 600), 10);

        Fantome f1 = new Fantome(2, 0, new Point(500, 800), c);
        Fantome f2 = new Fantome(2, 0, new Point(400, 500), c);
        Fantome f3 = new Fantome(3, 0, new Point(300, 20), c);

        f1.startMovement();
        f2.startMovement();
        f3.startMovement();

        /* Modifier la position des araignées */
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
