package Model;
import java.awt.Point;
import java.util.Random;
import java.util.ArrayList;
import Controler.Character;

//classe qui represente les obstacles
public class Obstacle {
    //nombre d'obstacle
    private int nbObstacle = 10;
    //dimension de l'obstacle
    public static final int HEIGHT_O = 12;
    public static final int WIDTH_O = 16;
    //liste d'obstacle
    public ArrayList<Point> obstacles= new ArrayList<Point>();
    //generer un nombre aleatoire
    Random rand = new Random();
    //private Character c;

    //constructeur
    public Obstacle() {
        //this.c = c;
        this.obstacles = new ArrayList<Point>();
        genererObstacle();
    }
    //generer une liste d'obstacle
    public void genererObstacle() {
        for (int i = 0; i < nbObstacle; i++) {
            //generer un obstacle
            int x = rand.nextInt(1500);
            int y = rand.nextInt(1000);
            Point p = new Point(x, y);
            //ajouter l'obstacle a la liste
            obstacles.add(p);
            
        }
    }
    //retourner la liste d'obstacle
    public ArrayList<Point> getObstacles() {
        return this.obstacles;
    }
    
}
