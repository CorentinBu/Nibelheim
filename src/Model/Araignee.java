package Model;
import java.util.*;

import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.Point;
import Controler.Character;
import Controler.LevelManager;

public class Araignee {

    // Insances de classes utiles
    private ArrayList<Point> posAraignee;
    private Position position;
    private Tir tir;
    private Character c;
    private Bonus b;
    // Dimensions de l'araignée (taille du gif)
    public static final int weight = 80;
    public static final int height = 50;
    // Image gif de l'araignée
    public static final Image araigneeSprite = new ImageIcon(Araignee.class.getResource("/Images/araignee.gif"))
            .getImage().getScaledInstance(weight, height, Image.SCALE_DEFAULT);
            

    private LevelManager levelManager; // Gestionnaire de niveaux
    // Quantité d'araignées à afficher

    private int quantite = 10;
    private static final int POINTPERDU = 10; // Dégats causés par l'araignée
    
    public static final Random rand = new Random();

    public Araignee(Position position, Character c, Tir tir, Bonus bonus) {
        this.tir = tir;
        this.position = position;
        this.c=c;
        this.posAraignee = new ArrayList<Point>();
        this.b = bonus;
        this.levelManager = new LevelManager(this); // Initialisez le LevelManager avec cette instance
        ListePosition();
        
    }

    // Getters pour recuperer le nombre d'araignées
    public int getNombreAraignee() {
        return posAraignee.size();
    }

    public void ListePosition() {
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

    //recuperer la position des araignées et les deplaces vers le centre
    public ArrayList<Point> getPosition() {
        ArrayList<Point> araignee = new ArrayList<Point>();
        for(Point point : this.posAraignee) {
            if(point.x < (c.getCurrent_x() +Character.WIDTH/2)){
                if (point.y < c.getCurrent_y() + Character.HEIGHT/2){
                    point.x += rand.nextInt(position.vitesseA);
                    point.y += rand.nextInt(position.vitesseA);
                }
                else if (point.y > c.getCurrent_x() + Character.HEIGHT/2){
                    point.x += rand.nextInt(position.vitesseA);
                    point.y -= rand.nextInt(position.vitesseA);
                }
                else{
                    point.x += rand.nextInt(position.vitesseA);
                }
            }
            else if (point.x> c.getCurrent_x() + Character.WIDTH/2){
                if (point.y > c.getCurrent_y() + Character.HEIGHT/2){
                    point.x -= rand.nextInt(position.vitesseA);
                    point.y -= rand.nextInt(position.vitesseA/2);
                }
                else if (point.y < c.getCurrent_y() + Character.HEIGHT/2){
                    point.x -= rand.nextInt(position.vitesseA);
                    point.y += rand.nextInt(position.vitesseA/2);
                }
                else{
                    point.x -= rand.nextInt(position.vitesseA);
                }
            }
            else if (point.y < c.getCurrent_y() + Character.HEIGHT/2){
                point.y += rand.nextInt(position.vitesseA);
            }
            else if (point.y > c.getCurrent_y() + Character.HEIGHT/2){
                point.y -= rand.nextInt(position.vitesseA);
            }
            araignee.add(new Point(point.x, point.y));
        }
        return araignee;
    }

    // Détecter une collision entre l'araignée et le joueur
    public void detecterCollisionAraigneeJoueur(Point point){
        if (point.x >= c.getCurrent_x() && point.x <= c.getCurrent_x() + Character.WIDTH
                && point.y >= c.getCurrent_y() && point.y <= c.getCurrent_y() + Character.HEIGHT) {
            // Le joueur perds des points de vie du joueur s'il en a encore
            if (c.getVie() > 0) {
                c.setVie(c.getVie()-POINTPERDU);
            }
            // Un bonus apparait la où l'araignéer est supprimée
            b.addBonus(point);
            // L'araignée est supprimée de la liste
            posAraignee.remove(point);

        }
           
    }

    // Détecter une collision entre l'araignée et le tir (Taille de l'araignée prise en compte, on divise par 2 pour ne pas prendre en compte les pates)
    public boolean collisionAraigneeProjectile(Point point) {
        for (int i = 0; i < tir.getTirs().size(); i++) {
            if (tir.getTirs().get(i).getPosition().x >= point.x && tir.getTirs().get(i).getPosition().x <= point.x + weight/2
                    && tir.getTirs().get(i).getPosition().y >= point.y && tir.getTirs().get(i).getPosition().y <= point.y + height/2) {
                // Un bonus apparait la où l'araignée meurt
                b.addBonus(point);
                return true;
            }
        }
        return false;
    }

    // Détecter les araignées qui ont été touchées par un projectile et les supprimer de la liste
    public void removeAraigneeTouchee() {
        Iterator<Point> iterator = posAraignee.iterator();
        boolean araigneeSupprimee = false;
        
        while (iterator.hasNext()) {
            Point point = iterator.next();
            if (collisionAraigneeProjectile(point)) {
                b.addBonus(point);
                iterator.remove();
                araigneeSupprimee = true;
            }
        }
        
        if (araigneeSupprimee && posAraignee.isEmpty()) {
            levelManager.araigneeTuee();
        }
    }
    public void reinitialiser(int nombre) {
        System.out.println("Réinitialisation des araignées avec " + nombre + " nouvelles araignées.");
        posAraignee.clear();  // Supprime toutes les anciennes araignées
        for (int i = 0; i < nombre; i++) {
            int x = rand.nextInt(Position.AFTER);
            int y = rand.nextInt(Position.HAUTEUR_MAX);
            posAraignee.add(new Point(x, y));
        }
    }

    // Méthode pour supprimer une araignée
    public void supprimerAraignee(Point point){
        // L'araignée est supprimée de la liste
        posAraignee.remove(point);
    }

    // @Override
    public int getQuantite() {
        return quantite;
    }

   // @Override
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
