package Model;

import java.awt.Point;

// Classe pour les projectiles (position et direction)
public class Projectile {

    // Attributs de position et direction
    private Point position;
    private Point direction;

    // Constructeur pour initialiser la position et la direction
    public Projectile(Point position, Point direction) {
        this.position = position;
        this.direction = direction;
    }

    // Getteurs pour récupérer la position et la direction
    public Point getPosition() {
        return position;
    }

    public Point getDirection() {
        return direction;
    }

    // Setteus pour modifier la position et la direction
    public void setPosition(Point position) {
        this.position = position;
    }

    public void setDirection(Point direction) {
        this.direction = direction;
    }

}
