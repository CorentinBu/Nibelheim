package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import Controler.Character;
import Controler.LevelManager;
import Model.Tir;
import Model.Araignee;
import Model.Bonus;
import Model.Position;
import Model.Ennemies;
import Model.Fantome;

public class Affichage extends JPanel {
    // Dimensions de la vue
    public static final int X = 1500;
    public static final int Y = 800;

    // Instances de classe utiles
    private Bonus b;
    Character c;
    private Tir tir;
    Position position;
    private Araignee a;
    private LevelManager levelManager;

    // Position de la barre de vie
    public static final int xBarreVie = 20;
    public static final int yBarreVie = 20;

    // Dimension barre de vie
    public static final int heightBarreVie = 20;
    public static final int arcBarreVie = 10;

    // Barre de niveau
    public static final int xBarreNiveau = 20;
    public static final int yBarreNiveau = 50;
    public static final int heightBarreNiveau = 20;
    public static final int largeurBarreNiveauMax = 400;
    public static final int NIVEAU_MAX = 10; // Nombre maximum de niveaux

    private Image coinImage;

    public Affichage(Character c, Tir t, Araignee a, Position position, Bonus bonus) {
        setPreferredSize(new Dimension(X, Y));
        this.c = c;
        this.tir = t;
        this.b = bonus;
        this.a = a;
        
        if (position == null) {
            this.position = new Position(100, 100);
        } else {
            this.position = position;
        }
        
        this.levelManager = new LevelManager(a);
        coinImage = new ImageIcon(getClass().getResource("/Images/coin.png"))
                .getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
    }

    private void drawNiveauTermine(Graphics g) {
        // Fond semi-transparent
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, X, Y);
        
        // Message
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        String message = "Niveau " + (levelManager.getNiveau()) + " terminé!";
        int msgWidth = g.getFontMetrics().stringWidth(message);
        g.drawString(message, X/2 - msgWidth/2, Y/2 - 30);
        
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        String nextMsg = "Niveau " + (levelManager.getNiveau()+1) + " en préparation...";
        int nextWidth = g.getFontMetrics().stringWidth(nextMsg);
        g.drawString(nextMsg, X/2 - nextWidth/2, Y/2 + 30);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        if (levelManager.isNiveauTermine()) {
            drawNiveauTermine(g);
            return;
        }

        // Dessiner le personnage
        g.drawImage(Character.characterSprite, (int) c.getCurrent_x(), (int) c.getCurrent_y(), null);

        // Dessiner les tirs
        for (int i = 0; i < tir.getTirs().size(); i++) {
            g.setColor(Color.RED);
            int x = tir.getTirs().get(i).getPosition().x;
            int y = tir.getTirs().get(i).getPosition().y;
            g.fillOval(x, y, 10, 10);
        }

        // Affichage des pièces
        g.drawImage(coinImage, X - 130, 21, null);
        g.setColor(Color.BLACK);
        g.drawString("Pièces : " + c.getNombreBonus(), X - 98, 40);

        // Affichage du niveau et des araignées
        g.drawString("Niveau : " + levelManager.getNiveau(), X - 150, 60);
        g.drawString("Araignées restantes : " + a.getNombreAraignee(), X - 180, 90);

        // Barre de niveau
        g.setColor(Color.GRAY);
        g.fillRoundRect(xBarreNiveau, yBarreNiveau, largeurBarreNiveauMax, heightBarreNiveau, arcBarreVie, arcBarreVie);
        g.setColor(Color.BLUE);
        int niveauWidth = (int)(largeurBarreNiveauMax * ((float)levelManager.getNiveau() / NIVEAU_MAX));
        g.fillRoundRect(xBarreNiveau, yBarreNiveau, niveauWidth, heightBarreNiveau, arcBarreVie, arcBarreVie);
        g.setColor(Color.BLACK);
        g.drawRoundRect(xBarreNiveau, yBarreNiveau, largeurBarreNiveauMax, heightBarreNiveau, arcBarreVie, arcBarreVie);

        // Barre de vie
        g.setColor(Color.GRAY);
        g.fillRoundRect(xBarreVie, yBarreVie, c.maxVie * 2, heightBarreVie, arcBarreVie, arcBarreVie);
        g.setColor(Color.RED);
        g.fillRoundRect(xBarreVie, yBarreVie, c.getVie() * 2, heightBarreVie, arcBarreVie, arcBarreVie);
        g.setColor(Color.BLACK);
        g.drawRoundRect(xBarreVie, yBarreVie, c.maxVie * 2, heightBarreVie, arcBarreVie, arcBarreVie);

        // Dessiner les éléments du jeu
        drawBonus(g);
        drawEnnemies(g);
        drawAraignee(g);
    }

    public void drawBonus(Graphics g) {
        for (int i = 0; i < b.getPointBonus().size(); i++) {
            g.drawImage(coinImage, b.getPointBonus().get(i).x, b.getPointBonus().get(i).y, null);
        }
    }

    public void drawEnnemies(Graphics g) {
        List<Ennemies> ennemies = Ennemies.getListEnnemies();
        for (Ennemies ennemi : ennemies) {
            if (ennemi instanceof Fantome) {
                Fantome fantome = (Fantome) ennemi;
                g.drawImage(fantome.img, (int) fantome.getPosition().getX(), (int) fantome.getPosition().getY(), null);
            }
        }
    }

    public void drawAraignee(Graphics g) {
        ArrayList<Point> araignee = a.getPosition();
        for (Point araigneP : araignee) {
            g.drawImage(Araignee.araigneeSprite, araigneP.x, araigneP.y, null);
            g.setColor(Color.RED);
            g.drawOval(X, Y, 5, 5);
            a.detecterCollisionAraigneeJoueur(araigneP);
        }
    }
}