package map;

import io.jbotsim.core.Color;
import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.ui.JViewer;
import io.jbotsim.ui.painting.BackgroundPainter;
import io.jbotsim.ui.painting.UIComponent;
import robot.Robot;
import robot.algos.Algorithms;

import java.util.Random;

public class Map implements BackgroundPainter{

    private Topology tp;

    public static void main(String[] args) {
        new Map(1); //Avec ça on controle quel type de map on veut
    }

    public Map(int type){
        tp = new Topology();
        tp.setCommunicationRange(0);
        JViewer jv = new JViewer(tp);
        jv.getJTopology().setDefaultBackgroundPainter(this);
        tp.setNodeModel("robot", Robot.class);

        switch (type) {
            case 1 : //On place soi-même les robots ou on lance une save
                config1();
                break;
            case 2 : //Génère des robots aléatoirement
                generateRobots(20);
                break;
        }
        chooseFirst(2,1);
    }

    private void chooseFirst(int type, int algo) { //Choix du premier robot à réveiller

        if(type == 0){ //random choice

            Random r = new Random();
            Node z = tp.getNodes().get(r.nextInt(tp.getNodes().size()));
            ((Robot)z).setAwake(true);
            ((Robot)z).setIconSize(25);
            ((Robot)z).setColor(new Color(Color.CYAN));
            Algorithms a = new Algorithms(tp);

            if(algo == 1){
                a.algo1((Robot)z);
            }else if(algo == 2) {
                a.algo2((Robot)z);
            }else{
                a.algo1((Robot)z);
            }

        }else if (type == 1){ //the first robot of the list is chosen

            Node z = tp.getNodes().get(0);
            ((Robot)z).setAwake(true);
            ((Robot)z).setIconSize(25);
            ((Robot)z).setColor(new Color(Color.CYAN));
            Algorithms a = new Algorithms(tp);
            if(algo == 1){
                a.algo1((Robot)z);
            }else if(algo == 2) {
                a.algo2((Robot)z);
            }else{
                a.algo1((Robot)z);
            }

        }else{ //Choix libre
            System.out.println("choisir un robot avec ctrl+clic puis lancer avec start execution !");
            return;
        }
        tp.start();
    }

    private void generateRobots(int nbRobots) { //Robots placés au hasard
        int width = tp.getWidth();
        int height = tp.getHeight();
        for(int i=0; i<nbRobots; i++){
            Random r  = new Random();
            tp.addNode(10+r.nextInt(width-20), 10+r.nextInt(height-20), new Robot());
        }
    }

    private void config1() { //Quadrillage de robots
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-50; i=i+(width/10)){
            for(int j=50; j<height-50; j=j+(height/10)) {
                tp.addNode(i, j, new Robot());
            }
        }
    }

    @Override
    public void paintBackground(UIComponent uiComponent, Topology topology) {

    }
}
