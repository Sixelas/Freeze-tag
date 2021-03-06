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
                //r.setColor(new Color(Color.ORANGE));
                r.setIcon(r.s+"ufo-orange.png");
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
            //z.setColor(new Color(Color.ORANGE));
            z.setIcon(z.s+"ufo-orange.png");
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
        //target.setColor(new Color(Color.ORANGE));
        target.setIcon(target.s+"ufo-orange.png");
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
        //target.setColor(new Color(Color.ORANGE));
        target.setIcon(target.s+"ufo-orange.png");
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
            //start.setColor(new Color(Color.RED));
            start.setIcon(start.s+"ufo-red.png");
            //start.setIconSize(15);
            return false;
        }

        if(target.isChoice()){ //Si notre cible avait déjà un robot source, on lui vole et il doit s'en trouver une autre.
            //target.getSource().setColor(new Color(Color.RED));
            target.getSource().setIcon(target.s+"ufo-red.png");
            //target.getSource().setIconSize(15);
            target.getSource().lock = true;
        }

        start.setCible(target);
        target.setChoice(true);
        //target.setColor(new Color(Color.ORANGE));
        target.setIcon(target.s+"ufo-orange.png");
        target.setCommunicationRange(0);
        target.setSource(start);
        target.setSourcePoint(new Point(start.getX(), start.getY()));
        start.setDest(new Point(target.getX(), target.getY()));
        return true;
    }

    //Un robot choisit un robot à réveiller qui maximise le rapport entre le nombre de robots présents dans un secteur et la distance pour arriver à ce robot.
    //
    public boolean algo6(Robot start){
        double bestDist = Integer.MAX_VALUE;
        int bestDegree = -1;
        Robot target = null;
        for(Robot r : robots){
            if( (!(r.isAwake())) && (!(r.isChoice())) ){
                if(bestDegree / bestDist < r.getNeighbors().size() / r.distance(start)){
                    bestDegree = r.getNeighbors().size();
                    bestDist = r.distance(start);
                    target = r;
                }
            }
        }

        if(bestDegree == -1 || bestDist == Integer.MAX_VALUE){ //Si on a pas réussi à trouver de cible, il n'y a plus de robots à réveiller. On devient RED.
            return false;
        }

        start.setCible(target);
        target.setChoice(true);
        //target.setColor(new Color(Color.ORANGE));
        target.setIcon(target.s+"ufo-orange.png");
        target.setCommunicationRange(0);
        target.setSource(start);
        target.setSourcePoint(new Point(start.getX(), start.getY()));
        start.setDest(new Point(target.getX(), target.getY()));
        return true;
    }

    public boolean algo7(Robot start){
        Robot target = null;
        double ratio = 0;
        List<Robot> northWestSector = new ArrayList<>();
        List<Robot> southWestSector = new ArrayList<>();
        List<Robot> northEastSector = new ArrayList<>();
        List<Robot> southEastSector = new ArrayList<>();
        for(Robot r : robots){
            if( (!(r.isAwake())) && (!(r.isChoice())) ){
                if (r.getLocation().getX() <= tp.getWidth() / 2 && r.getLocation().getY() > tp.getHeight() / 2){
                    northWestSector.add(r);
                    r.setColor(new Color(Color.BLACK)); //A commenter si on veut plus voir les couleurs des secteurs
                }
                if (r.getLocation().getX() > tp.getWidth() / 2 && r.getLocation().getY() > tp.getHeight() / 2){
                    northEastSector.add(r);
                    r.setColor(new Color(Color.PINK)); //A commenter si on veut plus voir les couleurs des secteurs
                }
                if (r.getLocation().getX() <= tp.getWidth() / 2 && r.getLocation().getY() <= tp.getHeight() / 2){
                    southWestSector.add(r);
                    r.setColor(new Color(Color.BLUE)); //A commenter si on veut plus voir les couleurs des secteurs
                }
                if (r.getLocation().getX() > tp.getWidth() / 2 && r.getLocation().getY() <= tp.getHeight() / 2){
                    southEastSector.add(r);
                    r.setColor(new Color(Color.MAGENTA)); //A commenter si on veut plus voir les couleurs des secteurs
                }
            }
        }

        for(Robot r : robots){
            if (northWestSector.contains(r)){
                for (Robot rnw : northWestSector){
                    if (northWestSector.size() / rnw.distance(start) > ratio){
                        ratio =  northWestSector.size() / rnw.distance(start);
                        target = rnw;
                    }
                }
            }
            if (southWestSector.contains(r)){
                for (Robot rsw : southWestSector){
                    if (southWestSector.size() / rsw.distance(start) > ratio){
                        ratio =  southWestSector.size() / rsw.distance(start);
                        target = rsw;
                    }
                }
            }
            if (southEastSector.contains(r)){
                for (Robot rse : southEastSector){
                    if (southEastSector.size() / rse.distance(start) > ratio){
                        ratio =  southEastSector.size() / rse.distance(start);
                        target = rse;
                    }
                }
            }
            if (northEastSector.contains(r)){
                for (Robot rne : northEastSector){
                    if (northEastSector.size() / rne.distance(start) > ratio){
                        ratio =  northEastSector.size() / rne.distance(start);
                        target = rne;
                    }
                }
            }

        }

        if(ratio == 0){ //Si on a pas réussi à trouver de cible, il n'y a plus de robots à réveiller. On devient RED.
            return false;
        }

        start.setCible(target);
        target.setChoice(true);
        //target.setColor(new Color(Color.ORANGE));
        target.setIcon(target.s+"ufo-orange.png");
        target.setCommunicationRange(0);
        target.setSource(start);
        target.setSourcePoint(new Point(start.getX(), start.getY()));
        start.setDest(new Point(target.getX(), target.getY()));
        return true;
    }

    public boolean algo8(Robot start){
        double bestDist = Integer.MAX_VALUE;
        Random x = new Random();

        List<Integer> sectors = new ArrayList<>();
        Robot target = null;
        List<Robot> northWestSector = new ArrayList<>();
        List<Robot> southWestSector = new ArrayList<>();
        List<Robot> northEastSector = new ArrayList<>();
        List<Robot> southEastSector = new ArrayList<>();
        for(Robot r : robots){
            if( (!(r.isAwake())) && (!(r.isChoice())) ){
                if (r.getLocation().getX() <= tp.getWidth() / 2 && r.getLocation().getY() > tp.getHeight() / 2){
                    northWestSector.add(r);
                    sectors.add(0);
                }
                if (r.getLocation().getX() > tp.getWidth() / 2 && r.getLocation().getY() > tp.getHeight() / 2){
                    northEastSector.add(r);
                    sectors.add(1);
                }
                if (r.getLocation().getX() <= tp.getWidth() / 2 && r.getLocation().getY() <= tp.getHeight() / 2){
                    southWestSector.add(r);
                    sectors.add(2);
                }
                if (r.getLocation().getX() > tp.getWidth() / 2 && r.getLocation().getY() <= tp.getHeight() / 2){
                    southEastSector.add(r);
                    sectors.add(3);
                }
            }
        }

        if (sectors.isEmpty()){
            return false;
        }

        int y = sectors.get(x.nextInt(sectors.size()));

        switch(y){
            case 0 :
                for (Robot rnw : northWestSector) {
                    if (bestDist > start.distance(rnw)) {
                        bestDist = start.distance(rnw);
                        target = rnw;
                    }
                }
                break;
            case 1 :
                for (Robot rne : northEastSector) {
                    if (bestDist > start.distance(rne)) {
                        bestDist = start.distance(rne);
                        target = rne;
                    }
                }
                break;
            case 2 :
                for (Robot rsw : southWestSector) {
                    if (bestDist > start.distance(rsw)) {
                        bestDist = start.distance(rsw);
                        target = rsw;
                    }
                }
                break;
            case 3 :
                for (Robot rse : southEastSector){
                    if (bestDist>start.distance(rse)){
                        bestDist = start.distance(rse);
                        target = rse;
                    }
                }
                break;
            default :
                break;
        }

        start.setCible(target);
        target.setChoice(true);
        //target.setColor(new Color(Color.ORANGE));
        target.setIcon(target.s+"ufo-orange.png");
        target.setCommunicationRange(0);
        target.setSource(start);
        target.setSourcePoint(new Point(start.getX(), start.getY()));
        start.setDest(new Point(target.getX(), target.getY()));
        return true;
    }


}