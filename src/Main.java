import javax.swing.*;
import View.*;
import Controler.Character;
import View.Redessine;
import Controler.Inputs;

public class Main {
    public static void main(String[] args) throws Exception {

        JFrame f = new JFrame("Nibelheim");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
