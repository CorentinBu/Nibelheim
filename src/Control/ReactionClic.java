package Control;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import Model.Tir;
import View.Affichage;



// cette classe reagit aux clics de la souris sur la fenetre
public class ReactionClic implements MouseListener{
    // Objets utiles pourla classe
    Affichage affichage;
    Tir tir;
    
    public ReactionClic(Affichage affichage, Tir t){
        this.tir = t;
        this.affichage = affichage;
    }
    
    public void mouseClicked(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        tir.addTir();
        affichage.repaint();
    }
    
    public void mousePressed(MouseEvent e){
        
    }
    
    public void mouseReleased(MouseEvent e){
        
    }
    
    public void mouseEntered(MouseEvent e){
        
    }
    
    public void mouseExited(MouseEvent e){
        
    }

}
