package Model;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Classe qui gère la mise à l'échelle des éléments pour la responsivité de l'interface
 * Permet de respecter le pattern MVC en centralisant la logique de dimensionnement
 * dans le modèle, séparément de la vue
 */
public class Dimension {
    // Dimensions de référence (design original)
    private static final int REFERENCE_WIDTH = 1500;
    private static final int REFERENCE_HEIGHT = 650;
    
    // Facteurs d'échelle actuels
    private static float scaleX = 1.0f;
    private static float scaleY = 1.0f;
    
    // Largeur et hauteur actuelles de la fenêtre
    private static int currentWidth = REFERENCE_WIDTH;
    private static int currentHeight = REFERENCE_HEIGHT;
    
    /**
     * Met à jour les dimensions actuelles et recalcule les facteurs d'échelle
     * @param width Largeur actuelle de la fenêtre
     * @param height Hauteur actuelle de la fenêtre
     */
    public static void updateDimensions(int width, int height) {
        currentWidth = width;
        currentHeight = height;
        scaleX = (float) width / REFERENCE_WIDTH;
        scaleY = (float) height / REFERENCE_HEIGHT;
    }
    
    /**
     * Convertit une coordonnée X en tenant compte de l'échelle
     * @param x Coordonnée X de référence
     * @return Coordonnée X mise à l'échelle
     */
    public static int scaleX(int original) {
        int scaled = (original * currentWidth) / REFERENCE_WIDTH;
        if (scaled <= 0) {
            System.err.println("Attention: scaleX retourne " + scaled + " pour original=" + original);
            return 1; // Valeur minimale
        }
        return scaled;
    }
    
    /**
     * Convertit une coordonnée Y en tenant compte de l'échelle
     * @param y Coordonnée Y de référence
     * @return Coordonnée Y mise à l'échelle
     */
    public static int scaleY(int y) {
        return (int)(y * scaleY);
    }
    
    /**
     * Retourne le facteur d'échelle minimum entre X et Y
     * Utile pour des éléments qui doivent conserver leurs proportions
     * @return Le plus petit facteur d'échelle entre X et Y
     */
    public static float getMinScale() {
        return Math.min(scaleX, scaleY);
    }
    
    /**
     * Calcule une taille de police adaptée en fonction de l'échelle
     * @param baseSize Taille de police de référence
     * @return Taille de police mise à l'échelle
     */
    public static int scaleFontSize(int baseSize) {
        return Math.max(10, (int)(baseSize * getMinScale()));
    }
    
    /**
     * Transforme un Point en Point mis à l'échelle
     * @param p Point de référence
     * @return Nouveau Point mis à l'échelle
     */
    public static Point scalePoint(Point p) {
        return new Point(scaleX(p.x), scaleY(p.y));
    }
    
    /**
     * Crée un Rectangle mis à l'échelle
     * @param x Coordonnée X de référence
     * @param y Coordonnée Y de référence
     * @param width Largeur de référence
     * @param height Hauteur de référence
     * @return Rectangle mis à l'échelle
     */
    public static Rectangle scaleRect(int x, int y, int width, int height) {
        return new Rectangle(scaleX(x), scaleY(y), scaleX(width), scaleY(height));
    }
    
    /**
     * @return Largeur actuelle de la fenêtre
     */
    public static int getWidth() {
        return currentWidth;
    }
    
    /**
     * @return Hauteur actuelle de la fenêtre
     */
    public static int getHeight() {
        return currentHeight;
    }
    
    /**
     * @return Largeur de référence (design original)
     */
    public static int getReferenceWidth() {
        return REFERENCE_WIDTH;
    }
    
    /**
     * @return Hauteur de référence (design original)
     */
    public static int getReferenceHeight() {
        return REFERENCE_HEIGHT;
    }
}