package View;


//Redessine l'écran
public class Redessine extends Thread{
    //delai de rafraichissement
    public static final int DELAY = 50;
    public Affichage monAffichage;

    public Redessine(Affichage a){
        this.monAffichage = a;
    }

    //Redessine l'écran 
    @Override
    public void run() {
      while (true) {
        monAffichage.revalidate();
        monAffichage.repaint();
        try { Thread.sleep(DELAY); }
        catch (Exception e) { e.printStackTrace(); }
      }
    }

}