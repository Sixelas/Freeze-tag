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

    /** Tous les algorithmes retournent true si ils ont pu choisir une cible, et false sinon **/

    //Réveille les robots dans la liste les uns à la suite des autres, pas opti du tout.
    public boolean algo1(Robot start){
        for(Robot r : robots){
            if((! r.isAwake()) && (! r.isChoice())){
                start.setCible(r);
                r.setChoice(true);
                r.setColor(new Color(Color.ORANGE));
                r.setSource(start);
                r.setSourcePoint(new Point(start.getX(), start.getY()));
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
            z.setSource(start);
            z.setSourcePoint(new Point(start.getX(), start.getY()));
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
        target.setSource(start);
        target.setSourcePoint(new Point(start.getX(), start.getY()));
        start.setDest(new Point(target.getX(), target.getY()));
        return true;
    }

    // Un robot choisit comme cible à réveiller le robot avec le plus gros degré (donc celui avec le plus de voisins).
    public boolean algo4(Robot start){

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

        if(bestDegree == -1){ //Si on a pas réussi à trouver de cible, il n'y a plus de robots à réveiller. On devient RED.
            return false;
        }

        start.setCible(target);
        target.setChoice(true);
        target.setColor(new Color(Color.ORANGE));
        target.setCommunicationRange(0);
        target.setSource(start);
        target.setSourcePoint(new Point(start.getX(), start.getY()));
        start.setDest(new Point(target.getX(), target.getY()));
        return true;
    }

    // Un robot choisit comme cible à réveiller le robot le plus proche de lui.
    // Si il choisit un robot qui est déjà la cible d'un autre robot, il regarde si il est plus proche de la cible que l'ancien robot source.
    // Si il est plus proche que l'ancien robot source, alors il devient source de la cible et l'ancien robot source doit trouver une nouvelle cible.
    public boolean algo5(Robot start){

        double bestDist = Integer.MAX_VALUE;
        Robot target = null;

        for(Robot r : robots){
            if( (!(r.isAwake())) ){
                if(bestDist > r.distance(start)){

                    if((r.isChoice()) /**&& (!r.getLocation().equals(start.getLocation())) **/ ){

                        if(!(start.getSource().equals(r.getSource()))){ //Mon propre robot source est prioritaire sur la cible si on est à distance équivalente de la cible.
                            double dist1 = r.distance(r.getSource());
                            double dist2 = r.distance(start);

                            if(dist1>dist2 && (! r.getSource().getLocation().equals(start.getLocation()))){ //On vole la cible que si on est pas collé au robot source initial de la cible.
                                target = r;
                                bestDist = r.distance(start);
                            }
                        }

                    }else{ //Cas classique comme algo3, la cible n'a pas déjà de robot source au moment ou on la choisit.
                        target = r;
                        bestDist = r.distance(start);
                    }
                }
            }
        }

        if(target == null){ //Si on a pas réussi à trouver de cible, il n'y a plus de robots à réveiller. On devient RED.
            start.lock = true;
            start.setColor(new Color(Color.RED));
            start.setIconSize(15);
            return false;
        }

        if(target.isChoice()){ //Si notre cible avait déjà un robot source, on lui vole et il doit s'en trouver une autre.
            target.getSource().setColor(new Color(Color.RED));
            target.getSource().setIconSize(15);
            target.getSource().lock = true;
        }

        start.setCible(target);
        target.setChoice(true);
        target.setColor(new Color(Color.ORANGE));
        target.setCommunicationRange(0);
        target.setSource(start);
        target.setSourcePoint(new Point(start.getX(), start.getY()));
        start.setDest(new Point(target.getX(), target.getY()));
        return true;
    }


}