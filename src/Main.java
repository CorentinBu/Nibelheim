import javax.swing.*;
import View.*;
import Controler.*;
import Controler.Character;
import Model.*;
import java.awt.Point;

public class Main {
    public static void main(String[] args) throws Exception {

        // Création de la fenêtre
        JFrame f = new JFrame("Nibelheim");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création des objets que l'on va utiliser
        Inputs inputs = new Inputs();
        Bonus b = new Bonus();
        Obstacles obstacles = new Obstacles();
        Character c = new Character(b, inputs, obstacles);
        Tir t = new Tir(c,obstacles);
        PositionAraignee position = new PositionAraignee();  // Exemple : position de départ (100, 100)
        Araignee araignee = new Araignee(position,c, t, b);
       
        Affichage a = new Affichage(c, t, araignee, position, b,inputs, obstacles);
        ReactionClic m = new ReactionClic(t);
        Avancer_tir avancer_tir = new Avancer_tir(t);
        Redessine r = new Redessine(a);
        MouvementAraignee mvtA = new MouvementAraignee(position);
        Collision col = new Collision(c, t, araignee, a);

        // Ajouter des fantomes
        Fantome f1 = new Fantome(c, 2, 0, new Point(500, 800));
        Fantome f2 = new Fantome(c, 2, 0, new Point(400, 500));
        Fantome f3 = new Fantome(c, 3, 0, new Point(300, 20));


        f1.startMovement();
        f2.startMovement();
        f3.startMovement();

        // 
        Ennemies.startCollision(c,t);

        // Démarrer nos différentes Threads
        col.start();
        mvtA.start();
        avancer_tir.start();
        r.start();
        c.start();

        // Ajouter un KeyListener pour gérer les entrées clavier
        f.addKeyListener(inputs);
        f.add(a);
        f.pack();
        f.setVisible(true);
    }
}
