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
    public boolean forRandom = true;
    public boolean choice = false;
    public Algorithms algo;
    public int a;


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

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }


    @Override
    public void onStart() {
        //super.onStart();
        //speed =
        algo = new Algorithms(super.getTopology());
    }

    @Override
    public void onClock() {
        //super.onClock();
        //isEnd();
        if(awake){
            //System.out.println("id : "+this.getID()+" dest : "+dest);
            if(!(dest==null)){
                if (distance(dest) > speed) {
                    //System.out.println("Coucou id : "+this.getID()+" dest : "+dest);
                    setDirection(dest);
                    move(speed);
                }else{
                    if(!arrive){
                        setLocation(dest);
                        arrive();
                    }else{
                        arrive = false;
                        searchTarget();
                    }
                }
            }
        }

    }

    private void isEnd() {
        int cpt = 0;
        for(Node n : super.getTopology().getNodes()){
            if(n instanceof Robot){
                if(((Robot) n).isAwake()){
                    cpt++;
                    if(cpt == super.getTopology().getNodes().size()){
                        super.getTopology().pause();
                        System.out.println("FINI, time = "+ super.getTime());
                    }
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
        algo = new Algorithms(super.getTopology());
        setIconSize(25);
        setColor(new Color(Color.CYAN));
        super.onSelection();
        awake = true;
        if(searchTarget()){
            //System.out.println("Cible : "+ cible);
        }else{
            //super.getTopology().pause();
            //System.out.println("FINI, time = "+ super.getTime());

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
                //System.out.println("Cible : "+ cible);
            }else{
                //super.getTopology().pause();
                //System.out.println("FINI, time = "+ super.getTime());
            }
        }
    }

    private boolean searchTarget() { // Cherche un robot à réveiller
        if(a == 1){
            return algo.algo1(this);
        }else if (a == 2){
            return algo.algo2(this);
        }else if (a == 3) {
            return algo.algo3(this);
        }else{
            return algo.algo1(this);
        }
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
