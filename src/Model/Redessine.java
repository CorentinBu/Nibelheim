package Model;
import View.Affichage;

// cette classe permet de redessiner régulièrement la fenetre
public class Redessine extends Thread {
    Affichage affichage;
    private int DELAY = 10;

    public Redessine(Affichage a) {
        this.affichage = a;
    }

    // On modifie sa méthode run() pour redessiner la fenetre toutes les 10ms
    @Override
    public void run() {
        while (true) {
            // Redessiner la fenetre
            affichage.repaint();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
