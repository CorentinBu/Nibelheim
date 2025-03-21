package View;

public class Redessine extends Thread {

  // Constante pour le délai de rafraichissement
  public static final int DELAY = 50;

  private Affichage monAffichage;

  // Constructeur
  public Redessine(Affichage aff) {
    this.monAffichage = aff;
  }

  // Thread redessinant l'écran lors de chaque frame
  @Override
  public void run() {
    while (true) {
      // Redessiner l'écran avec les méthodes de la classe Affichage
      monAffichage.revalidate();
      monAffichage.repaint();
      try {
        Thread.sleep(DELAY);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}