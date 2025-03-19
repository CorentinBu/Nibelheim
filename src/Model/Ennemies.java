package Model;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.text.Position;

public class Ennemies {
    /*VARIABLES*/
    int health, speed, bonusAmount;
    boolean randomSpawn;
    Point position;

    public static List<Ennemies> ListEnnemies = new ArrayList<>();

    public static final int weight = 50;
    public static final int height = 50;
    public Image img = new ImageIcon("src/Images/character.png").getImage().getScaledInstance(weight, height, Image.SCALE_DEFAULT);

    /*CONSTRUCTEUR*/
    public Ennemies(int health, int speed, int bonusAmount, Point position, Image sprite){
        this.speed = speed;
        this.bonusAmount= bonusAmount;
        this.img = sprite;
        this.position = position;
        System.out.println("On insère dans liste d'ennemies : "+speed+" vitesse");
        ListEnnemies.add(this);
    }

    /*GETTERS & SETTERS*/
    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int v){
        this.speed = v;
    }

    public static List<Ennemies> getListEnnemies(){
        return ListEnnemies;
    }

    public Point getPosition(){
        return position;
    }
    /*METHODES*/

    //Va à la position du point qui est passé en paramètre
    public void goToPosition(Point p){
/*      if(positionSpawn.getX() != p.getX() && positionSpawn.getY() != p.getY()){
            
        }   */ 
    }

    public void goToCharacter(Position p){
        //Va se déplacer vers le joueur    
    }

    //Losque l'ennemi est touché, il perd de la vie
    public void isHit(int damage){
        health-=damage;
        if (health == 0){
            kill();
        }
    }

    //L'ennemi meurt quand sa vie arrive à 0
    public void kill(){
        //L'ennemie meurt
    }
}
