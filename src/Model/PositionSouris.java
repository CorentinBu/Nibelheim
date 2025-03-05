package Model;

import java.awt.*;

import Controler.ReactionClic;

public class PositionSouris extends Thread {
    // Variables
    public static final int DELAY = 50;
    ReactionClic m = new ReactionClic();

    public PositionSouris(ReactionClic m) {
        this.m = m;
    }

    public void AffichePosition() {
        PointerInfo mouseInfo = MouseInfo.getPointerInfo();
        int mouse_X = (int) mouseInfo.getLocation().getX();
        int mouse_Y = (int) mouseInfo.getLocation().getY();

        System.out.println("Position X : " + mouse_X);
        System.out.println("Position Y : " + mouse_Y + "\n");
    }

    @Override
    public void run() {
        while (true) {
            if (m.isPressed) {
                AffichePosition();
            }

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
