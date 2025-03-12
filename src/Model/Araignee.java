package Model;
import java.util.*;

import java.awt.Point;
import Controler.Character;

public class Araignee {
    private ArrayList<Point> posAraignee;
    private int quantite = 10;
    //nombre de points perdu par le joueur quand il est touché par une araignée
    private static final int POINTPERDU = 10;
    Position position;
    Character c;

    
    
    public static final Random rand = new Random();

    public Araignee(Position position, Character c) {
        this.position = position;
        this.c=c;
        this.posAraignee = new ArrayList<Point>();
        Listeposition();
        
    }
    /*methode qui genere une liste de points */
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
    /*methode qui diminue les points de vie du joueur et supprime une araignée quand celle ci touche le character */
    public void toucher(Point point){
        if (point.x == c.current_x+ Character.WIDTH/2 && point.y == c.current_y+ Character.HEIGHT/2){
            c.setVie(c.getVie()-POINTPERDU);
            System.out.println("Vie: "+c.getVie());
            supprimerAraignee(point);
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
