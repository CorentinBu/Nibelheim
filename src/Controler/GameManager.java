// package Controler;

// import javax.swing.*;
// import View.*;
// import Controler.*;
// import Controler.Character;
// import Model.*;

// public class GameManager {
    
//         // Instances de classe utiles
//         Inputs inputs = new Inputs();
//         Bonus b = new Bonus();
//         Character c = new Character(b, inputs);
//         Tir t = new Tir(c);
//         Position position = new Position();  // Exemple : position de départ (100, 100)
//         Araignee araignee = new Araignee(position,c, t, b);
//         Affichage a = new Affichage(c, t, araignee, position, b, inputs);
//         ReactionClic m = new ReactionClic(t);
//         Avancer_tir avancer_tir = new Avancer_tir(t);
//         Redessine r = new Redessine(a);
//         MouvementAraignee mvtA = new MouvementAraignee(position);
//         Collision col = new Collision(c, t, araignee, a);

//         // Démarrer les mouvements des fantomes
//         f1.startMovement();
//         f2.startMovement();
//         f3.startMovement();

//         // 
//         Ennemies.startCollision(c,t);

//         // Démarrer nos différentes Threads
//         col.start();
//         mvtA.start();
//         avancer_tir.start();
//         r.start();
//         c.start();

//         // Ajouter un KeyListener pour gérer les entrées clavier
//         f.addKeyListener(inputs);

//         // Ajouter la vue à la fenêtre et afficher la fenetre
//         f.add(a);
//         f.pack();
//         f.setVisible(true);

// }
