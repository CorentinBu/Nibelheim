import javax.swing.*;
import View.*;
import Controler.*;
import Controler.Character;
import Model.*;
public class Main {
    public static void main(String[] args) throws Exception {
        JFrame f = new JFrame("Nibelheim");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Inputs inputs = new Inputs();
        f.addKeyListener(inputs);

        Character c = new Character(inputs);
        Tir t = new Tir(c);

        Position position = new Position();
        Araignee araignee = new Araignee(position, c);

        Affichage a = new Affichage(c, t, araignee, position);
        f.add(a);
        f.pack();
        f.setVisible(true);

        // Test collision
        int x = 100;
        int y = 100;

        System.out.println("Position initiale : x = " + x + ", y = " + y);
        // Vérification des collisions
        int[] newPosition = position.collisionJoueur(x, y);
        System.out.println("Position après collision : x = " + newPosition[0] + ", y = " + newPosition[1]);

        // Reste du code
        ReactionClic m = new ReactionClic(a, t);
        PositionSouris ps = new PositionSouris(m);
        a.addMouseListener(m);
        Avancer_tir avancer_tir = new Avancer_tir(t);
        Redessine r = new Redessine(a);

        MouvementAraignee mvtA = new MouvementAraignee(position);
        mvtA.start();

        avancer_tir.start();
        ps.start();
        r.start();
        c.run();
        m.run();
    }
}