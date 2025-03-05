import javax.swing.*;
import View.*;
import Controler.Character;
import View.Redessine;
import Controler.Inputs;

public class Main {
    public static void main(String[] args) throws Exception {
        
        System.out.println("Hello, World!");
        JFrame f = new JFrame("Test Windows");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new JLabel("Hello World!"));

        Inputs inputs = new Inputs();
        f.addKeyListener(inputs);

        Character c = new Character(inputs);

        Affichage a = new Affichage(c);
        f.add(a);
        f.pack();
        f.setVisible(true);

        Redessine r = new Redessine(a);
        r.start();
        c.run();
    }
}
