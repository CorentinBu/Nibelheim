package Controler;

import java.awt.Rectangle;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.w3c.dom.css.Rect;

import java.awt.Point;

import Model.Araignee;
import Model.Tir;
import View.Affichage;
import Model.Obstacles;

// Gérer toutes les collisions
public class Collision extends Thread {

    // Constante pour le délai de rafraichissement
    public static final int DELAY = 25;

    // Instances de classe utiles
    private Character c;
    private Tir t;
    private Araignee a;
    private Affichage aff;

    // Constructeur pour initialiser les instances de classe
    public Collision(Character c, Tir t, Araignee a, Affichage aff) {
        this.c = c;
        this.t = t;
        this.a = a;
        this.aff = aff;
    }
    //verifier si il a collision entre  le joueur et une araignee
    public boolean verifierCollisionAraigneeJoueur( Character character, Point araignee) {
        //initilisation des rectangles
        Rectangle r1 = new Rectangle((int) character.getCurrent_x(), (int) character.getCurrent_y(), Character.WIDTH, Character.HEIGHT);
        Rectangle r2 = new Rectangle(araignee.x, araignee.y, Araignee.weight, Araignee.height);   
        //verifier si il y a collision
        return r1.intersects(r2);
    }
    //verifier si il y collision entre le joueur et un obstacle
    /*public boolean verifierCollisionObstacleJoueur(Character character, Point obstacle) {
        //initiliastion des rectangles
        Rectangle r1 = new Rectangle((int) character.getCurrent_x(), (int) character.getCurrent_y(), Character.WIDTH, Character.HEIGHT);
        Rectangle r2 = new Rectangle(obstacle.x, obstacle.y, Obstacles.WIDTH_O, Obstacles.HEIGHT_O);
        return r1.intersects(r2);
    }*/

    // Methode pour gérer les collisions
    @Override
    public void run() {
        while (true) {
            // Gérer les collisions entre les tirs et les araignées
            a.removeAraigneeTouchee();

            // Détecter si le joueur touche un bonus
            c.checkBonusProche();
            ArrayList<Point> posAraignee = a.getAraignee();
            for(int i = 0; i < posAraignee.size(); i++){
                if(verifierCollisionAraigneeJoueur(c, posAraignee.get(i))){
                    c.setVie(c.getVie()-10);
                    posAraignee.remove(i);
                    //System.out.println("collision");
                }
            }
            // Détecter si le joueur touche un obstacle
            //c.detecterCollisionObstacleJoueur();

            // Redessiner l'écran
            aff.revalidate();
            aff.repaint();

            try {
                Thread.sleep(DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
