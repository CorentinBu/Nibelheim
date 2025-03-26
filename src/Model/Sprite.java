package Model;
import java.awt.Image;
import javax.swing.ImageIcon;
// classe qui aide a representer les elements graphoques du jeu sous forme de rectangle
public class Sprite {
    // position x du sprite
    private int x;
    // position y du sprite
    private int y;
    // largeur du sprite
    private int width;
    // hauteur du sprite
    private int height;
    //image du sprite
    private Image image;

    // Constructeur
    public Sprite(String image, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        try {
            this.image = new ImageIcon(image).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'image : " + image);
        }
    }
    
}
