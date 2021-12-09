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
        for(Robot r : robots){
            if((! r.isAwake()) && (! r.isChoice())){
                start.setCible(r);
                (r).setChoice(true);
                (r).setColor(new Color(Color.ORANGE));
                r.setCommunicationRange(0);
                start.setDest(new Point(r.getX(), r.getY()));
                return true;
            }
        }
        return false;
    }

    //Réveille les robots en les choisissant aléatoirement dans la liste.

    public boolean algo2(Robot start){

        List<Robot> robots = new ArrayList<>();

        for(Node r : tp.getNodes()){
            if(r instanceof Robot){
                if((!(((Robot) r).isAwake())) && (!(((Robot) r).isChoice()))){
                    robots.add((Robot) r);
                }
            }
        }
        if(robots.size() > 0){
            Random r = new Random();
            Robot z = robots.get(r.nextInt(robots.size()));
            start.setCible(z);
            z.setChoice(true);
            z.setColor(new Color(Color.ORANGE));
            z.setCommunicationRange(0);
            start.setDest(new Point(z.getX(), z.getY()));
            return true;
        }

        return false;
    }


    // Un robot choisit comme cible à réveiller le robot le plus proche de lui.
    public boolean algo3(Robot start){
        double bestDist = Integer.MAX_VALUE;
        Robot target = null;
        for(Robot r : robots){
            if( (!(r.isAwake())) && (!(r.isChoice())) ){
                if(bestDist > r.distance(start)){
                    bestDist = r.distance(start);
                    target = r;
                }
            }
        }
        if(bestDist == Integer.MAX_VALUE){
            return false;
        }
        start.setCible(target);
        target.setChoice(true);
        target.setColor(new Color(Color.ORANGE));
        target.setCommunicationRange(0);
        start.setDest(new Point(target.getX(), target.getY()));
        return true;
    }

    // Un robot choisit comme cible à réveiller le robot le plus proche de lui.
    public boolean algo4(Robot start){
        //double bestDist = Integer.MAX_VALUE;
        int bestDegree = -1;
        Robot target = null;
        for(Robot r : robots){
            if( (!(r.isAwake())) && (!(r.isChoice())) ){
                if(bestDegree < r.getNeighbors().size()){
                    bestDegree = r.getNeighbors().size();
                    target = r;
                }
            }
        }
        if(bestDegree == -1){
            return false;
        }
        start.setCible(target);
        target.setChoice(true);
        target.setColor(new Color(Color.ORANGE));
        target.setCommunicationRange(0);
        start.setDest(new Point(target.getX(), target.getY()));
        return true;
    }




}