package Model;
import java.util.*;

import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.Point;
import Controler.Character;

public class Araignee {

    // Insances de classes utiles
    private ArrayList<Point> posAraignee;
    private Position position;
    private Tir tir;
    private Character c;
    // Dimensions de l'araignée (taille du gif)
    public static final int weight = 80;
    public static final int height = 50;
    // Image gif de l'araignée
    public static final Image araigneeSprite = new ImageIcon("src/Images/araignee.gif").getImage()
            .getScaledInstance(weight, height, Image.SCALE_DEFAULT);
    // Quantité d'araignées à afficher
    private int quantite = 10;
    private static final int POINTPERDU = 10;
    
    public static final Random rand = new Random();

    public Araignee(Position position, Character c, Tir tir) {
        this.tir = tir;
        this.position = position;
        this.c=c;
        this.posAraignee = new ArrayList<Point>();
        Listeposition();
        
    }

    // Getters pour recuperer le nombre d'araignées
    public int getNombreAraignee() {
        return posAraignee.size();
    }

    public void Listeposition() {
        for(int i=0; i<quantite;i++){
            int x,y;
            //mettre tous les points hors de la fenetre
            //si le booleen est true, on met x en dessous de 0, sinon on met x au-dessus de AFTER
            if (rand.nextBoolean()) { 
                x = rand.nextInt(Position.BEFORE); 
            } else { 
                x = Position.AFTER + rand.nextInt(20); 
            }
    
            // Générer Y soit en dessous de 0, soit au-dessus de HAUTEUR_MAX
            if (rand.nextBoolean()) { 
                y = -rand.nextInt(50);
            } else { 
                y = Position.HAUTEUR_MAX + rand.nextInt(50); 
            }
    
            posAraignee.add(new Point(x, y)); 
        }
    }

    //recuperer la position des araignées et les deplacer vers le centre
    public ArrayList<Point> getPosition() {
        ArrayList<Point> araignee = new ArrayList<Point>();
        for(Point point : this.posAraignee) {
            if(point.x < (c.current_x +Character.WIDTH/2)){
                if (point.y < c.current_y + Character.HEIGHT/2){
                    point.x += rand.nextInt(position.vitesseA);
                    point.y += rand.nextInt(position.vitesseA);
                }
                else if (point.y > c.current_y + Character.HEIGHT/2){
                    point.x += rand.nextInt(position.vitesseA);
                    point.y -= rand.nextInt(position.vitesseA);
                }
                else{
                    point.x += rand.nextInt(position.vitesseA);
                }
            }
            else if (point.x> c.current_x + Character.WIDTH/2){
                if (point.y > c.current_y + Character.HEIGHT/2){
                    point.x -= rand.nextInt(position.vitesseA);
                    point.y -= rand.nextInt(position.vitesseA/2);
                }
                else if (point.y < c.current_y + Character.HEIGHT/2){
                    point.x -= rand.nextInt(position.vitesseA);
                    point.y += rand.nextInt(position.vitesseA/2);
                }
                else{
                    point.x -= rand.nextInt(position.vitesseA);
                }
            }
            else if (point.y < c.current_y + Character.HEIGHT/2){
                point.y += rand.nextInt(position.vitesseA);
            }
            else if (point.y > c.current_y + Character.HEIGHT/2){
                point.y -= rand.nextInt(position.vitesseA);
            }
           
            araignee.add(new Point(point.x, point.y));
        }
        return araignee;
    }

    public void toucher(Point point){
        if (point.x == c.current_x+ Character.WIDTH/2 && point.y == c.current_y+ Character.HEIGHT/2){
            c.setVie(c.getVie()-POINTPERDU);
            System.out.println("Vie: "+c.getVie());
            supprimerAraignee(point);
        }
           
    }

    // Détecter une collision entre l'araignée et le tir (Taille de l'araignée prise en compte)
    public boolean detectionCollision(Point point) {
        for (Point tir : tir.getTirs()) {
            //if (point.x < tir.x + 10 && point.x + 10 > tir.x && point.y < tir.y + 10 && point.y + 10 > tir.y) {
            if (tir.x >= point.x && tir.x <= point.x + weight && tir.y >= point.y && tir.y <= point.y + height) {
                return true;
            }
        }
        return false;
    }

    // Supprimer les araignées qui ont été touchées en utilisant la méthode detectionCollision
    public void removeAraigneeTouchee() {
        for (int i = 0; i < posAraignee.size(); i++) {
            if (detectionCollision(posAraignee.get(i))) {
                posAraignee.remove(i);
                i--;
            }
        }
    }

    //methode pour supprimer une araignée
    public void supprimerAraignee(Point point){
        posAraignee.remove(point);
    }

    //@Override
    public int getQuantite() {
        return quantite;
    }

   // @Override
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
