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
        Bonus b = new Bonus();
        Obstacles o = new Obstacles();
        Character c = new Character(b, inputs, o);
        Tir t = new Tir(c, o);
        Affichage a = new Affichage(c, t, b, inputs, o);
        // Les Threads
        ReactionClic m = new ReactionClic(t);
        Avancer_tir avancer_tir = new Avancer_tir(t);
        Redessine r = new Redessine(a);
        Collision col = new Collision(c, t, a);

        // Ajouter un MouseListener pour gérer les clics de souris
        a.addMouseListener(m);

        // Ajouter des fantomes
        Fantome f1 = new Fantome(c, 2, 1, new Point(500, 800), b);
        Fantome f2 = new Fantome(c, 5, 2, new Point(400, 500), b);
        Fantome f3 = new Fantome(c, 3, 1, new Point(300, 20), b);

        // Démarrer les mouvements des fantomes
        f1.startMovement();
        f2.startMovement();
        f3.startMovement();

        Araignee a1 = new Araignee(c, 15, 1, new Point(0, 0), b);
        a1.startMovement();
        
        int posY = 0;
        int araigneeSpeed = 0;
        for (int nbEnnemies = 0; nbEnnemies < 5; nbEnnemies++) {
            posY = posY + 25;
            araigneeSpeed = (int) ((Math.random() * (15 - 8) + 8 ));
            System.out.println("Speed : " + araigneeSpeed);
            Araignee araigneeee = new Araignee(c, araigneeSpeed, 1, new Point(0, posY), b);
            araigneeee.startMovement();
        }
        // 
        Ennemies.startCollision(c,t);

        // Démarrer nos différentes Threads
        col.start();
        avancer_tir.start();
        r.start();
        c.start();

        // Ajouter un KeyListener pour gérer les entrées clavier
        f.addKeyListener(inputs);

        // Ajouter la vue à la fenêtre et afficher la fenetre
        f.add(a);
        f.pack();
        f.setVisible(true);
    }
}
