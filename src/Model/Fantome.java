package Model;

import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

public class Fantome extends Ennemies {
    private static final int HEALTH_MAX = 20;

    public static final int weight = 80;
    public static final int height = 50;
    
    // Image gif de l'araignée
    public static final Image sprite = new ImageIcon("src/Images/ghost.png").getImage().getScaledInstance(weight, height, Image.SCALE_DEFAULT);

    public Fantome(int speed, int bonusAmount, Point pos){
        super(HEALTH_MAX, speed, bonusAmount, pos, sprite);
    }
}
