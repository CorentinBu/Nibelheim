package Controler;

import javax.swing.ImageIcon;
import Model.Obstacle;

import java.awt.Image;
import java.awt.Point;



//Classe du personnage principal du jeu. Celui contrôlé par le joueur.
//Le joueur utilise les touches Z,Q,S,D pour se déplacer.
//Il peut se déplacer librement sur toute la fenêre.
//Il peut également tirer des projectiles.
//Il a une barre de vie qui diminue lorsqu'il est touché par un ennemi.
//Il peut ramasser des bonus pour augmenter sa vie ou sa puissance de tir.
public class Character extends Thread {

    // Attributs
    public int current_x = 820;
    public int current_y = 540;
    private int speed = 25;
    private Collision collision;
    //points de vie du joueur
    private int vie=110;

    private Obstacle o;

    private Inputs inputs;

    // Constructeur
    public Character(Inputs i, Obstacle o) {
        this.o = o;
        inputs = i;
    }

    /*image character */
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    //creer des getters pour vx et vy
    public double getVx() {
        return vx;
    }
    public double getVy() {
        return vy;
    }

    // Getters pour la position du joueur
    public double getCurrent_x() {
        return current_x;
    }
    public double getCurrent_y() {
        return current_y;
    }

    // Constructeur pour la classe Character
    public Character(Bonus bonus, Inputs i) {
        this.b = bonus;
        this.inputs = i;
    }

    // Setters pour la position du joueur
    public void setCurrent_x(double current_x) {
        this.current_x = current_x;
    }
    public void setCurrent_y(double current_y) {
        this.current_y = current_y;
    }

    // Getteur et setteur pour le nombre de bonus
    public int getNombreBonus() {
        return nombreBonus;
    }
    public void setNombreBonus(int nombreBonus) {
        this.nombreBonus = nombreBonus;
    }


    // Sprite du personnage
    public static final Image characterSprite = new ImageIcon("src/Images/character.png")
            .getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);

    // Thread qui gère le déplacement
    public void run() {
        while (true) {
            updateMovement(); // Mise à jour des déplacements

            try {
                Thread.sleep(16); // Environ 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Gestion du déplacement avec inertie
    private void updateMovement() {
        // Accélération selon les touches pressées
        if (inputs.up) { vy -= acceleration; }
        if (inputs.down) { vy += acceleration; }
        if (inputs.left) { vx -= acceleration; }
        if (inputs.right) { vx += acceleration; }
        

        // Limite la vitesse maximale
        vx = Math.max(-maxSpeed, Math.min(maxSpeed, vx));
        vy = Math.max(-maxSpeed, Math.min(maxSpeed, vy));

        // Appliquer la friction (simule une glisse après l'arrêt des touches)
        vx *= friction;
        vy *= friction;

        // Appliquer le mouvement
        current_x += vx;
        current_y += vy;

        // Garder la sorcière dans l'écran
        current_x = Math.max(0, Math.min(1800, current_x));
        current_y = Math.max(0, Math.min(1080, current_y));
    }

    // Getter et setter pour les points de vie
    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    // Méthodes de déplacement
    public void moveUp() {
        if (current_y > 0 && !collisionObstacle(current_x, current_y - speed)) {
            this.current_y -= speed;
        }
        
    }
    

    public void moveDown() {
        if (current_y < 1080 && !collisionObstacle(current_x, current_y + speed)) {
            this.current_y += speed;
        }
        
    }

    public void moveLeft() {
        if (current_x > 0  && !collisionObstacle(current_x - speed, current_y)) {
            this.current_x -= speed;
        }
        
    }

    public void moveRight() {
        if (current_x <1800 && !collisionObstacle(current_x + speed, current_y)) {
            this.current_x += speed;
        }
        
    }
    
    //methode qui verifie si le deplacement du joueur l'enmene sur un obstacle
    public boolean collisionObstacle(int x, int y) {
        for (Point obstacle : o.getObstacles()) {
            int x_o = obstacle.x;
            int y_o = obstacle.y;
    
            // Vérification de la collision
            boolean collisionX = (x < x_o + Character.WIDTH) && (x + Character.WIDTH > x_o);
            boolean collisionY = (y < y_o +Character.HEIGHT) && (y + Character.HEIGHT > y_o);
    
            if (collisionX && collisionY) {
                System.out.println("Collision détectée !");
                return true;
            }
        }
        return false;
    }
    

    /*public boolean collisionObstacle(int x, int y) {
        // Le bord gauche, droit, haut et bas du personnage
        int personnageGauche = x - Character.WIDTH / 2;
        int personnageDroit = x + Character.WIDTH / 2;
        int personnageHaut = y - Character.HEIGHT / 2;
        int personnageBas = y + Character.HEIGHT / 2;

        boolean collisionX = false;
        boolean collisionY = false;
        int i=0;
    
        // Parcours des obstacles
        for (Point obstacle : o.getObstacles()) {
            // Le bord gauche, droit, haut et bas de l'obstacle
            int obstacleGauche = obstacle.x - Obstacle.WIDTH_O / 2;
            int obstacleDroit = obstacle.x + Obstacle.WIDTH_O / 2;
            int obstacleHaut = obstacle.y - Obstacle.HEIGHT_O / 2;
            int obstacleBas = obstacle.y + Obstacle.HEIGHT_O / 2;

            //une methode
            // Vérification de la collision sur l'axe X (horizontal)
            boolean collisionX = !(personnageDroit == obstacleGauche || personnageGauche > obstacleDroit);
    
            // Vérification de la collision sur l'axe Y (vertical)
            boolean collisionY = !(personnageBas < obstacleHaut || personnageHaut > obstacleBas);
    
            // Si les deux axes sont en collision, alors il y a une collision
            if (collisionX && collisionY) {
                System.out.println("Collision détectée ! Personnage aux bords : [" + personnageGauche + "," + personnageHaut + "] -> [" + personnageDroit + "," + personnageBas + "], Obstacle aux bords : [" + obstacleGauche + "," + obstacleHaut + "] -> [" + obstacleDroit + "," + obstacleBas + "]");
                return true; // Collision détectée
            }
            //FIN

            //autre methode
            //verifie si la position de base  du joueur est avant ou apres l'obstacle
            if(current_x+Character.WIDTH/2 < obstacle.x-Obstacle.WIDTH_O/2){
                //verifie si le joueur est en collision avec l'obstacle
                if(personnageDroit>obstacleGauche ){
                    collisionX= true;
                }
            }
            else if(current_x-Character.WIDTH/2 > obstacle.x+Obstacle.WIDTH_O/2){
                if(personnageGauche<obstacleDroit){
                    collisionX= true;
                }
            }
            else{
                collisionX= true;
                
            }
            if(current_y+Character.HEIGHT/2 < obstacle.y-Obstacle.HEIGHT_O/2){
                if(personnageBas>obstacleHaut){
                    collisionY= true;
                }
            }
            else if(current_y-Character.HEIGHT/2 > obstacle.y+Obstacle.HEIGHT_O/2){
                if(personnageHaut<obstacleBas){
                    collisionY= true;
                }
            }
            else{
                collisionY= true;
            }
            if(collisionX && collisionY){
                System.out.println("Collision détectée ! a x= "+current_x+" y= "+current_y+" num="+i+" x= "+obstacle.x+" y= "+obstacle.y);
            }
            i+=1;
            //FIN
        }
    
        // Si aucune collision n'est détectée, retour
        return collisionX && collisionY;
    }*/
    
    
    
}