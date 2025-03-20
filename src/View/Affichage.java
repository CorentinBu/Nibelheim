package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import Controler.Character;
import Model.Tir;
import Model.Araignee;
import Model.Obstacle;
import Model.Position;


public class Affichage extends JPanel {
    public static final int X = 1920;
    public static final int Y = 1080;

    private List<Point> bonusPoints = new ArrayList<>();
    private List<Integer> bonusSizes = new ArrayList<>();

    Character c;
    private Tir tir;
    Position position;
    private Araignee a = new Araignee(position,c,tir);
    Obstacle o = new Obstacle();

    

    public Affichage(Character c, Tir t,Araignee a, Position position, Obstacle o) {
        setPreferredSize(new Dimension(X, Y));
        this.c = c;
        this.tir = t;
        this.a=a;
        this.position=position;
        this.o=o;
    }

    // Override de la méthode paint qui va afficher l'image "character.png" au
    // centre de l'écran
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(Character.characterSprite, c.current_x, c.current_y, null);

        g.setColor(Color.BLACK);
        // Carré Noir au centre de la fenetre comme point de départ du tir
        g.setColor(Color.BLACK);
        g.fillRect(X / 2, Y / 2, 10, 10);

        // Recuperer la liste des tirs et les afficher sachant x et y c'est leur
        // position par rapport au centre de la fenetre
        if (tir != null && tir.getTirs().size() > 0) {
            for (int j = 0; j < tir.getTirs().size(); j++) {
                System.err.println("x: "+tir.getTirs().get(j).x+"  Y: "+tir.getTirs().get(j).y);
                g.setColor(Color.RED);
                g.fillOval(tir.getTirs().get(j).x, tir.getTirs().get(j).y, 10, 10);
            }
        }

        // Dessiner les bonus
        for (int i = 0; i < bonusPoints.size(); i++) {
            g.setColor(Color.GREEN);
            g.fillOval(bonusPoints.get(i).x, bonusPoints.get(i).y, bonusSizes.get(i), bonusSizes.get(i));
        }

        /*afficher les araignées */
        drawAraignee(g);
        //dessiner les obstacles
        drawObstacle(g);
       
    }

    public void drawBonus(Point p, int size) {
        bonusPoints.add(p);
        bonusSizes.add(size);
        repaint();
    }

    /*dessiner les araignées */
    public void drawAraignee(Graphics g){
        ArrayList<Point> araignee = a.getPosition();
        for(Point araigneP : araignee){
            // afficher les images des araignées
            g.drawImage(Araignee.araigneeSprite, araigneP.x, araigneP.y, null);
            //si le joueur touche une araignée, on appelle la méthode toucher de la classe Araignée
            a.collisionAraigneeJoueur(araigneP);
        }
        
        // faire réaparaitre des araignées s'il reste moins de 4 (Juste pour le fun !)
        if (a.getNombreAraignee() < 4){
            a.Listeposition();
        }
    }

    //methode qui dessine les obstacles
    public void drawObstacle(Graphics g){
        int i = 0;
        ArrayList<Point> obstacles = o.getObstacles();
        for(Point obstacle : obstacles){
            g.setColor(Color.BLUE);
            g.fillRect(obstacle.x, obstacle.y, Obstacle.WIDTH_O, Obstacle.HEIGHT_O);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(i), obstacle.x + Obstacle.WIDTH_O / 2, obstacle.y + Obstacle.HEIGHT_O / 2);
            i+=1;
        }
    }
    
}
