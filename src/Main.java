import javax.swing.*;
import View.Affichage;

public class Main {
    public static void main(String[] args) throws Exception {
        Affichage a = new Affichage();
        System.out.println("Hello, World!");
        JFrame f = new JFrame("Test Windows");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new JLabel("Hello World!"));
        f.add(a);
        f.pack();
        f.setVisible(true);
        
    }
}
