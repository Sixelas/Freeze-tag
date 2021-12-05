package robot.algos;

import io.jbotsim.core.Node;
import io.jbotsim.core.Point;
import io.jbotsim.core.Topology;
import robot.Robot;

public class Algorithms {

    public Topology tp;

    public Algorithms(Topology tp){
        this.tp = tp;
    }

    //Réveille les robots dans la liste les uns à la suite des autres, pas opti du tout.
    public boolean algo1(Robot start){
        for(Node r : tp.getNodes()){
            if(r instanceof Robot){
                if(!((Robot) r).isAwake()){
                    start.setCible((Robot) r);
                    start.setDest(new Point(r.getX(), r.getY()));
                    return true;
                }
            }
        }
        return false;
    }



}
