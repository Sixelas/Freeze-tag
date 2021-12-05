package robot;

import io.jbotsim.core.Color;
import io.jbotsim.core.Message;
import io.jbotsim.core.Node;
import io.jbotsim.core.Point;
import io.jbotsim.ui.icons.Icons;
import robot.algos.Algorithms;

public class Robot extends Node {

    private double speed;
    private boolean awake = false;
    private Point dest;
    private Robot cible;
    private boolean arrive = false;


    public Robot(){
        setIcon(Icons.ROBOT);
        setIconSize((int)(getIconSize()*1.5));
        speed = 3;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isAwake() {
        return awake;
    }

    public void setAwake(boolean awake) {
        this.awake = awake;
    }


    @Override
    public void onStart() {
        //super.onStart();
        //speed = 1;
    }

    @Override
    public void onClock() {
        //super.onClock();
        if(awake){
            if (distance(dest) > speed) {
                setDirection(dest);
                move(speed);
            }else{
                if(!arrive){
                    setLocation(dest);
                    arrive();
                }
            }
        }

    }

    private void arrive() { //Quand on est arrivé sur le robot cible, on lui envoie un message pour le réveiller.
        send(cible, new Message(null,"DEBOUT"));
        //System.out.println("arrivé");
        arrive = true;
    }

    @Override
    public void onSelection() {
        //setIcon(Icons.STATION);
        setIconSize(25);
        setColor(new Color(Color.CYAN));
        super.onSelection();
        awake = true;
        if(searchTarget()){
            System.out.println("Cible : "+ cible);
        }else{
            System.out.println("FINI");
        }
    }

    @Override
    public void onMessage(Message message) {
        //super.onMessage(message);
        //System.out.println("recu");
        if(message.getFlag().equals("DEBOUT")){
            awake = true;
            setIconSize(25);
            setColor(new Color(Color.CYAN));
            //System.out.println("recu");
            if(searchTarget()){
                System.out.println("Cible : "+ cible);
            }else{
                System.out.println("FINI");
            }
        }
    }

    private boolean searchTarget() { // Cherche un robot à réveiller
        Algorithms algo = new Algorithms(super.getTopology());
        return algo.algo1(this);
    }

    @Override
    public String toString() {
        return "Robot{" +
                "ID=" + this.getID() +
                ", speed=" + speed +
                ", awake=" + awake +
                ", dest=" + dest +
                ", cible=" + cible +
                '}';
    }

    public void setCible(Robot r) {
        this.cible = r;
    }

    public void setDest(Point point) {
        this.dest = point;
    }
}
