package Controler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Model.Tir;
import View.Affichage;

public class ReactionClic implements MouseListener {
    public boolean isPressed = false;
    private int x;
    private int y;
    public static final int DELAY = 50;

    public boolean getPressed() {
        return this.isPressed;
    }

    Affichage affichage;
    Tir tir;

    public ReactionClic(Affichage affichage, Tir t) {
        this.tir = t;
        this.affichage = affichage;
    }

    public ReactionClic() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        tir.addTir(x, y); // Passer les coordonnées de la souris à la méthode addTir
        affichage.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        isPressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isPressed = false;
    }

    public void run() {
        while (true) {
            if (isPressed) {
                System.out.println("Z appuyé");
                tir.addTir(x, y); // Passer les coordonnées de la souris à la méthode addTir
                affichage.repaint();
            }
            try {
                Thread.sleep(DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
       // throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
       // throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }
}