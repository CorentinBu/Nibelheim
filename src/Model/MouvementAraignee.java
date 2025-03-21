package Model;

//Classe à retirer, les méthodes relatives aux araignées seront utilisées dans la classe Ennemie et ses sous-classes
public class MouvementAraignee extends Thread {
    public static final int DELAY = 25;
    Position p;

    public MouvementAraignee(Position p) {
        this.p = p;
    }

    // methode run pour faire avancer les araignée
    @Override
    public void run() {
        while (true) {
            p.setVitesseAraignee(p.getVitesseAraignee());
            try {
                Thread.sleep(DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
