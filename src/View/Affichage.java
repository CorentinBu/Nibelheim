package View;
import javax.swing.*;
import java.awt.*;


public class Affichage extends JPanel{
    public static final int X = 1920;
    public static final int Y = 1080;

    public Affichage(){
        setPreferredSize(new Dimension(X, Y));
    }
    
}
