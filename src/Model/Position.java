package Model;

/* Gère la position des ennemis */
public class Position {
    // Dimension de l'araignée
    public static final int LARGEUR_A = 10;
    public static final int HAUTEUR_A = 10;

    // Constantes pour l'horizon
    public static final int BEFORE = 50;
    public static final int AFTER = 2000;
    public static final int HAUTEUR_MAX = 1000;

    // Centre de la page
    public static final int X_CENTRE = AFTER / 3 - BEFORE;
    public static final int Y_CENTRE = HAUTEUR_MAX / 4;

    // Vitesse de l'araignée
    public int vitesseA = 5;

    // Position actuelle de l'araignée
    private int x;
    private int y;

    // Constructeur pour initialiser la position
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters et Setters pour la position
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Getter et Setter pour la vitesse
    public int getVitesseAraignee() {
        return this.vitesseA;
    }

    public void setVitesseAraignee(int vitesse) {
        this.vitesseA = vitesse;
    }

    // Méthode pour déplacer l'araignée (exemple)
    public void deplacerAraignee(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
}