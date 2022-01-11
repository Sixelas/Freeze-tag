package robot;

import io.jbotsim.core.Color;
import io.jbotsim.core.Message;
import io.jbotsim.core.Node;
import io.jbotsim.core.Point;
import io.jbotsim.ui.icons.Icons;
import robot.algos.Algorithms;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Robot extends Node {

    private boolean awake = false;
    private boolean arrive = false;
    public boolean choice = false;
    private double speed;
    public boolean forRandom = true;
    public boolean lock = false;
    public boolean lock2 = false;

    private Robot cible;
    private Point dest;
    private Robot source;
    private Point sourcePoint;

    public Algorithms algo;
    public int a;

    public Path currentRelativePath = Paths.get("");
    public String s = currentRelativePath.toAbsolutePath().toString()+File.separator+"src"+File.separator+"map"+File.separator+"images"+File.separator;


    public Robot(){
        //setIcon(Icons.ROBOT);
        setIcon(s+"ufo.png");
        setIconSize((int)(getIconSize()*2));
        speed = 3;
    }

    public Robot(int algo){
        //setIcon(Icons.ROBOT);
        setIcon(s+"ufo.png");
        setIconSize((int)(getIconSize()*2));
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

        this.source = this;
        algo = new Algorithms(super.getTopology());

    }

    @Override
    public void onClock() {

        if(awake && !lock && !lock2){
            if(!(dest==null)){
                if (distance(dest) > speed) {
                    setDirection(dest);
                    move(speed);
                }else{
                    if(!arrive){
                        setLocation(dest);
                        arrive();
                    }else{
                        arrive = false;
                        if(!searchTarget() && (a!= 5)){
                            lock2 = true;
                            //setColor(new Color(Color.RED));
                            setIcon(s+"ufo-red.png");
                            //setIconSize(15);
                        }
                    }
                }
            }
        }else if(awake && lock){
            if(!lock2){
                searchTarget();
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
        arrive = true;
    }

    @Override
    public void onSelection() {

        algo = new Algorithms(super.getTopology());
        //setIconSize(25);
        //setColor(new Color(Color.CYAN));
        setIcon(s+"ufo-cyan.png");
        super.onSelection();
        awake = true;
        this.source = this;
        searchTarget();

    }

    @Override
    public void onMessage(Message message) {

        if(message.getFlag().equals("DEBOUT")){
            awake = true;
            //setIconSize(25);
            //setColor(new Color(Color.CYAN));
            setIcon(s+"ufo-cyan.png");

            if(!searchTarget()){
                lock2 = true;
                lock = true;
                //setColor(new Color(Color.RED));
                setIcon(s+"ufo-red.png");
                //setIconSize(15);
            }
        }
    }

    public boolean searchTarget() { // Cherche un robot à réveiller, retourne false si il n'y a plus de robots à réveiller.

        if(lock){ //Ne s'applique que si on a choisi l'algo5.
            boolean res = algo.algo3(this); // Algorithme intermédiaire à choisir ici !
            if(res){
                //setColor(new Color(Color.GREEN));
                setIcon(s+"ufo-green.png");
                //setIconSize(25);
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
        }else if (a == 6) {
            return algo.algo6(this);
        }else if (a == 7) {
            return algo.algo7(this);
        } else if (a == 8) {
            return algo.algo8(this);
        }else{
            return algo.algo1(this);
        }
    }

    @Override
    public String toString() {
        if(cible==null){ //Pour éviter un segfault quand on passe la souris sur un robot sans cible.
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
