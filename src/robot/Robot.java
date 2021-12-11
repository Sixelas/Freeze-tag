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
    private Robot source;

    private Point sourcePoint;
    private boolean arrive = false;
    public boolean forRandom = true;
    public boolean choice = false;
    public Algorithms algo;
    public int a;
    public boolean lock = false;


    public Robot(){
        setIcon(Icons.ROBOT);
        setIconSize((int)(getIconSize()*1.5));
        speed = 3;
    }
    public Robot(int algo){
        setIcon(Icons.ROBOT);
        setIconSize((int)(getIconSize()*1.5));
        speed = 3;
        a = algo;
    }

    public Robot getSource() {
        return source;
    }

    public void setSource(Robot source) {
        this.source = source;
    }

    public Point getSourcePoint() {
        return sourcePoint;
    }

    public void setSourcePoint(Point sourcePoint) {
        this.sourcePoint = sourcePoint;
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
        this.source = this;
        algo = new Algorithms(super.getTopology());
        //System.out.println("speed : "+speed);
    }

    @Override
    public void onClock() {
        //super.onClock();
        //isEnd();
        if(awake && !lock){
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
        }else if(awake && lock){
            searchTarget();
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
        this.source = this;
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

    public boolean searchTarget() { // Cherche un robot à réveiller
        if(lock){

            boolean res = algo.algo4(this);
            if(res){
                setColor(new Color(Color.GREEN));
                lock = false;
            }
            return res;
        }
        if(a == 1){
            return algo.algo1(this);
        }else if (a == 2){
            return algo.algo2(this);
        }else if (a == 3) {
            return algo.algo3(this);
        }else if (a == 4) {
            return algo.algo4(this);
        }else if (a == 5) {
            return algo.algo5(this);
        }else{
            return algo.algo1(this);
        }
    }

    @Override
    public String toString() {
        if(cible==null){
            return "Robot{" +
                    "ID=" + this.getID() +
                    ", speed=" + speed +
                    ", awake=" + awake +
                    ", dest= " + dest +
                    ", cible= no " +
                    ", source= no " +
                    ", sourcePoint= no" +
                    '}';
        }
        return "Robot{" +
                "ID=" + this.getID() +
                ", speed=" + speed +
                ", awake=" + awake +
                ", dest=" + dest +
                ", cible=" + cible.getID() +
                ", source=" + source.getID() +
                ", sourcePoint=" + sourcePoint +
                '}';
    }

    public void setCible(Robot r) {
        this.cible = r;
    }

    public void setDest(Point point) {
        this.dest = point;
    }

    public void reset() {
        speed = 0;
        awake = false;
        dest = null;
        cible = null;
        arrive = false;
        forRandom = true;
        choice = false;
        Algorithms algo = null;
        a = -1;
    }
}
