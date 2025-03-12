package Model;

import java.awt.Point;

import View.Affichage;

//Cette classe décrit les bonus que le joueur peut ramasser.
//Les bonus sont lâchés par les ennemis lorsqu'ils sont détruits.
//Les bonus sont aimantés, attirés par le joueur

public class Bonus extends Thread {

    private Affichage a;
    private Point spawnPoint;
    private int size = 1;

    public Bonus(Affichage a) {
        this.a = a;
    }

    public Point getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Point spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    //Méthode faisant apparaître le bonus à la coordonnée spawnPoint
    public void spawn(Point spawnPoint, int size) {
        a.drawBonus(spawnPoint, size);
    }

    //Méthode vérifiant si le joueur touche le bonus, si le joueur le touche alors le bonus disparaît et le joueur gagne des points
    public void checkCollision() {
        //TODO
    }

    @Override
    public void run(){

    }

}
