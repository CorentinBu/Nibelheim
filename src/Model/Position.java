package Model;
/*Gere la position des ennemis */
public class Position {
    //dimension de l'araignée
    public  final int LARGEUR_A=10;
    public  final int HAUTEUR_A=10;

    //constante pour l'horizon
    public static final int BEFORE= 50;
    public static final int AFTER=2000;

    
    public static final int HAUTEUR_MAX= 1000;
    //centre page
    public static final int X_CENTRE = AFTER/3 -BEFORE;
    public static final int Y_CENTRE = HAUTEUR_MAX/4;

    //vitesse de l'araignée
    public int vitesseA=5;
    
    /*getter et setter pour la vitesse */
    public int getVitesseAraignee() {
        return this.vitesseA;
    }
    public void setVitesseAraignee(int vitesse) {
        this.vitesseA = vitesse;
    }
    //Methode pour gerer les collision entre joueurs et bords de l'ecran
    public int[] collisionJoueur(int x, int y) {
        // Vérification des collisions pour l'axe X
        if (x < 0) {
            x = 0;  // Empêcher d'aller au-delà du bord gauche
        }
        if (x > AFTER - LARGEUR_A) {
            x = AFTER - LARGEUR_A;  // Empêcher de dépasser le bord droit
        }
    
        // Vérification des collisions pour l'axe Y
        if (y < 0) {
            y = 0;  // Empêcher d'aller au-delà du bord supérieur
        }
        if (y > HAUTEUR_MAX - HAUTEUR_A) {
            y = HAUTEUR_MAX - HAUTEUR_A;  // Empêcher de dépasser le bord inférieur
        }
    
        // Retourne les nouvelles coordonnées après les ajustements
        return new int[]{x, y};
    }
}
