package robot.algos;

import io.jbotsim.core.Color;
import io.jbotsim.core.Node;
import io.jbotsim.core.Point;
import io.jbotsim.core.Topology;
import robot.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Algorithms {

    public Topology tp;
    public List<Robot> robots = new ArrayList<>();

    public Algorithms(Topology tp){
        this.tp = tp;
        for(Node n : tp.getNodes()){
            robots.add((Robot)n);
        }
    }

    //Réveille les robots dans la liste les uns à la suite des autres, pas opti du tout.
    public boolean algo1(Robot start){
        for(Node r : tp.getNodes()){
            if(r instanceof Robot){
                if((!((Robot) r).isAwake()) && (!((Robot) r).isChoice())){
                    start.setCible((Robot) r);
                    ((Robot) r).setChoice(true);
                    ((Robot) r).setColor(new Color(Color.ORANGE));
                    start.setDest(new Point(r.getX(), r.getY()));
                    return true;
                }
            }
        }
        return false;
    }

    //Réveille les robots en les choisissant aléatoirement dans la liste.
    /*
    public boolean algo2(Robot start){
        Random rand = new Random();

        for(Robot r : robots){

            if(!((Robot) r).isAwake()){
                start.setCible((Robot) r);
                start.setDest(new Point(r.getX(), r.getY()));
                return true;
            }
        }
        return false;
    }
    */



}
