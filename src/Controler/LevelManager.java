package Controler;

import Model.Araignee;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class LevelManager {
    private int niveau = 1;
    private Araignee araignee;
    private boolean niveauEnTransition = false;
    private Thread transitionThread;

    public LevelManager(Araignee araignee) {
        this.araignee = araignee;
    }

    public void araigneeTuee() {
        if (araignee.getNombreAraignee() == 0 && !niveauEnTransition) {
            niveauTermine();
        }
    }

    public void niveauTermine() {
        if (niveauEnTransition) return;
        
        niveauEnTransition = true;
        
        // Création d'un thread pour gérer la transition
        transitionThread = new Thread(() -> {
            try {
                // Temps d'affichage du message (3 secondes)
                Thread.sleep(3000);
                
                // Passage au niveau suivant
                SwingUtilities.invokeLater(() -> {
                    niveau++;
                    int nouvellesAraignees = niveau * 5;
                    araignee.reinitialiser(nouvellesAraignees);
                    niveauEnTransition = false;
                });
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        transitionThread.start();
    }

    public int getNiveau() {
        return niveau;
    }
    
    public boolean isNiveauTermine() {
        return niveauEnTransition;
    }
    
    public void stopTransition() {
        if (transitionThread != null && transitionThread.isAlive()) {
            transitionThread.interrupt();
        }
    }
}