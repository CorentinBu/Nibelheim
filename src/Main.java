import java.awt.Point;

import javax.swing.*;
import View.*;
import Controler.*;
import Controler.Character;
import Model.*;

public class Main {
    public static void main(String[] args) throws Exception {

        // Créer une fenêtre pour afficher le jeu
        JFrame f = new JFrame("Nibelheim");
        // Fermer la fenêtre lorsqu'on clique sur la croix
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Instances de classe utiles
        Inputs inputs = new Inputs();
        f.addKeyListener(inputs);

        Obstacle obstacle = new Obstacle();
        Character c = new Character(inputs, obstacle);
        Tir t = new Tir(c);

        Position position = new Position();
        Araignee araignee = new Araignee(position,c, t);
        
        

        Affichage a = new Affichage(c, t, araignee, position, obstacle);
        f.add(a);
        f.pack();
        f.setVisible(true);

        ReactionClic m = new ReactionClic(t);
        PositionSouris ps = new PositionSouris(m);
        Avancer_tir avancer_tir = new Avancer_tir(t);
        Redessine r = new Redessine(a);

        Bonus b = new Bonus(a);
        b.start();
        b.spawn(new Point(500,600), 10);


        /*Modifier la position des araignées */
        //MouvementAraignee mvtA = new MouvementAraignee(position);
        Collision col = new Collision(c, t, araignee, a);

        // Ajouter un KeyListener pour gérer les entrées clavier
        f.addKeyListener(inputs);

        // Ajouter la vue à la fenêtre et afficher la fenetre
        f.add(a);
        f.pack();
        f.setVisible(true);

        // Ajouter un MouseListener pour gérer les clics de souris
        a.addMouseListener(m);

        // Ajouter des fantomes
        Fantome f1 = new Fantome(2, 0, new Point(500, 800), c);
        Fantome f2 = new Fantome(2, 0, new Point(400, 500), c);
        Fantome f3 = new Fantome(3, 0, new Point(300, 20), c);

        // Démarrer les mouvements des fantomes
        f1.startMovement();
        f2.startMovement();
        f3.startMovement();

        // Démarrer nos différentes Threads
        col.start();
        //mvtA.start();
        avancer_tir.start();
        ps.start();
        r.start();
        c.start();

    }
}
