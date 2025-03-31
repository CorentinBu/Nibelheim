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
        PositionAraignee position = new PositionAraignee();  // Exemple : position de départ (100, 100)
        Araignee araignee = new Araignee(position,c, t, b);
        Affichage a = new Affichage(c, t, araignee, position, b, inputs, o);
        // Les Threads
        ReactionClic m = new ReactionClic(t);
        Avancer_tir avancer_tir = new Avancer_tir(t);
        Redessine r = new Redessine(a);
        MouvementAraignee mvtA = new MouvementAraignee(position);
        Collision col = new Collision(c, t, araignee, a);
        Fantome fantome = new Fantome(c, 2, 0, new Point(0, 0));
        LevelManager lm = new LevelManager(c, fantome);

        // Ajouter un MouseListener pour gérer les clics de souris
        a.addMouseListener(m);

        // 
        Ennemies.startCollision(c,t);

        // Démarrer nos différentes Threads
        col.start(); // Démarrer le thread de collision
        mvtA.start(); // Démarrer le thread de mouvement de l'araignée
        avancer_tir.start(); // Démarrer le thread de tir
        r.start(); // Démarrer le thread de redessin
        c.start(); // Démarrer le thread du personnage
        lm.start(); // Démarrer le gestionnaire de niveaux

        // Ajouter un KeyListener pour gérer les entrées clavier
        f.addKeyListener(inputs);

        // Ajouter la vue à la fenêtre et afficher la fenetre
        f.add(a);
        f.pack();
        f.setVisible(true);

    }
}
