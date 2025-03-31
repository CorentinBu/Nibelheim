package Model;

public class Niveau {
    private int numero;
    private int nombreEnnemis;
    
    public Niveau(int numero, int nombreEnnemis) {
        this.numero = numero;
        this.nombreEnnemis = nombreEnnemis;
    }

    public int getNumero() {
        return numero;
    }

    public int getNombreEnnemis() {
        return nombreEnnemis;
    }

    public void diminuerNombreEnnemis() {
        if (nombreEnnemis > 0) {
            nombreEnnemis--;
        }
    }

    public boolean estTermine() {
        return nombreEnnemis == 0;
    }
}