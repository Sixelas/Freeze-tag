package map;

import io.jbotsim.core.Topology;
import io.jbotsim.ui.JViewer;
import io.jbotsim.ui.painting.BackgroundPainter;
import io.jbotsim.ui.painting.UIComponent;
import robot.Robot;

import java.util.Random;

public class Map implements BackgroundPainter{

    private Topology tp;

    public static void main(String[] args) {
        new Map(2); //Avec ça on controle quel type de map on veut
    }

    public Map(int type){
        tp = new Topology();
        tp.setCommunicationRange(0);
        JViewer jv = new JViewer(tp);
        jv.getJTopology().setDefaultBackgroundPainter(this);
        tp.setNodeModel("robot", Robot.class);

        switch (type) {
            case 1 : //On place soi-même les robots
                break;
            case 2 : //Génère des robots aléatoirement
                generateRobots(15);
                break;
        }
        tp.start();
    }

    private void generateRobots(int nbRobots) {
        int width = tp.getWidth();
        int height = tp.getHeight();
        for(int i=0; i<nbRobots; i++){
            Random r  = new Random();
            tp.addNode(10+r.nextInt(width-20), 10+r.nextInt(height-20), new Robot());
        }
    }

    @Override
    public void paintBackground(UIComponent uiComponent, Topology topology) {

    }
}
