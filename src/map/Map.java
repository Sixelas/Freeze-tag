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
    int aa;

    public static void main(String[] args) {

        // ICI on choisit entre version Map et version Simulator.
        // Voir dans le README pour comprendre comment lancer une topologie.

        //new Map(5, 7, 5,100);
        //new Map(0, 0, 5,500);
        //15,95

        new Simulator(0,5,5,0, new int[]{15,95});
        //new Simulator(6,5,10,0, new int[]{10});

    }

    public Map(int type, int firstChoice, int algo, int nbRobots){

        tp = new Topology(800,800);
        tp.setCommunicationRange(60);
        JViewer jv = new JViewer(tp);
        jv.getJTopology().setDefaultBackgroundPainter(this);
        tp.setNodeModel("robot", Robot.class);
        aa = algo;

        switch (type) {
            case 1 : //On lance la config1
                config1();
                break;
            case 2 : //On lance la config2
                config2();
                break;
            case 3 : //On lance la config3
                config3();
                break;
            case 4 : //On lance la config4
                config4();
                break;
            case 5 : //On lance la config5
                config5();
                break;
            case 6 :
                config6();
                break;
            default: //Génère des robots aléatoirement
                generateRobots(nbRobots);
                break;
        }

        chooseFirst(firstChoice, algo);

    }

    private void chooseFirst(int firstChoice, int algo) { //Fonction qui choisit le premier robot à réveiller.
        Node z = null;
        aa = algo;
        if(firstChoice == 0){ //random choice

            Random r = new Random();
            z = tp.getNodes().get(r.nextInt(tp.getNodes().size()));
            ((Robot)z).setAwake(true);
            ((Robot)z).setIconSize(25);
            ((Robot)z).setColor(new Color(Color.CYAN));
            Algorithms a = new Algorithms(tp);

            if(algo == 1){
                a.algo1((Robot)z);
            }else if(algo == 2) {
                a.algo2((Robot)z);
            }else if(algo == 3) {
                a.algo3((Robot)z);
            }else if(algo == 4) {
                a.algo4((Robot)z);
            }else if(algo == 5) {
                a.algo5((Robot)z);
            }else{
                a.algo1((Robot)z);
                aa = 1;
            }

        }else if (firstChoice == 1){ //the first robot of the list is chosen

            z = tp.getNodes().get(0);
            ((Robot)z).setAwake(true);
            ((Robot)z).setIconSize(25);
            ((Robot)z).setColor(new Color(Color.CYAN));
            Algorithms a = new Algorithms(tp);
            if(algo == 1){
                a.algo1((Robot)z);
            }else if(algo == 2) {
                a.algo2((Robot)z);
            }else if(algo == 3) {
                a.algo3((Robot)z);
            }else if(algo == 4) {
                a.algo4((Robot)z);
            }else if(algo == 5) {
                a.algo5((Robot)z);
            }else{
                a.algo1((Robot)z);
                aa = 1;
            }

        }else if (firstChoice == 2){ //same as algo4
            int bestDegree = 0;
            z = null;
            for(Node r : tp.getNodes()){
                if(bestDegree < r.getNeighbors().size()){
                    bestDegree = r.getNeighbors().size();
                    z = r;
                }
            }
            ((Robot)z).setAwake(true);
            ((Robot)z).setIconSize(25);
            ((Robot)z).setColor(new Color(Color.CYAN));
            Algorithms a = new Algorithms(tp);
            if(algo == 1){
                a.algo1((Robot)z);
            }else if(algo == 2) {
                a.algo2((Robot)z);
            }else if(algo == 3) {
                a.algo3((Robot)z);
            }else if(algo == 4) {
                a.algo4((Robot)z);
            }else if(algo == 5) {
                a.algo5((Robot)z);
            }else{
                a.algo1((Robot)z);
                aa = 1;
            }

        }else{ //Choix libre
            System.out.println("choisir un robot avec ctrl+clic puis lancer avec start execution !");
            if((algo < 1) || (algo > 5) ){
                aa = 1;
            }
            for(Node n : tp.getNodes()){
                if(n instanceof Robot) {
                    ((Robot) n).a = aa;
                }
            }
            int timer = tp.getTime();
            while(!finish()){
            }
            System.out.println("time : "+(tp.getTime()-timer));
            return;
        }

        int timer = tp.getTime();
        tp.start();
        while(!finish()){
        }
        System.out.println("time : "+(tp.getTime()-timer));

    }

    // Cette fonction détecte quand tous les robots sont réveillés.
    private boolean finish() {
        if(aa == 5){
            int cpt=0;
            for(Node n : tp.getNodes()){
                if(n instanceof Robot){
                    if( !((Robot) n).isAwake() ){
                        return false;
                    }else{
                        cpt++;
                    }
                }
            }
            if(cpt >= tp.getNodes().size()-1){
                tp.pause();
                //System.exit(0);
                return true;
            }
        }
        int cpt=0;
        for(Node n : tp.getNodes()){
            if(n instanceof Robot){
                if( !((Robot) n).isAwake() ){
                    cpt++;
                    return false;
                }
            }
        }
        tp.pause();
        //System.exit(0);
        return true;

    }

    private void generateRobots(int nbRobots) { //nbRobots Robots placés au hasard
        int width = tp.getWidth();
        int height = tp.getHeight();
        Random r  = new Random(10);
        for(int i=0; i<nbRobots; i++){

            tp.addNode(10+r.nextInt(width-20), 10+r.nextInt(height-20), new Robot(aa));
        }
    }

    private void config1() { //Quadrillage de robots
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-50; i=i+(width/10)){
            for(int j=50; j<height-50; j=j+(height/10)) {
                tp.addNode(i, j, new Robot(aa));
            }
        }
    }

    private void config2() { //Quadrillage de robots (pas bcp de robots)
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-50; i=i+(width/5)){
            for(int j=50; j<height-50; j=j+(height/5)) {
                tp.addNode(i, j, new Robot(aa));
            }
        }
    }

    private void config3() { //Quadrillage de robots (bcp de robots)
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-50; i=i+(width/20)){
            for(int j=50; j<height-50; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(aa));
            }
        }
    }

    private void config4() { //Config théorique pour prouver qu'il faut choisir en premier un robot avec pleins de voisins.
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-300; i=i+(width/20)){
            for(int j=50; j<height-300; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(aa));
            }
        }
        tp.addNode(width-10, height-10, new Robot(aa));
        tp.addNode(10, height-10, new Robot(aa));
    }

    private void config5() { //Config théorique pour prouver qu'il faut choisir en premier un robot avec pleins de voisins
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-(width-200); i=i+(width/20)){
            for(int j=50; j<height-(height-200); j=j+(height/20)) {
                tp.addNode(i, j, new Robot(aa));
            }
        }

        for(int i=width-200; i<width; i=i+(width/20)){
            for(int j=height-200; j<height; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(aa));
            }
        }

        for(int i=width-200; i<width; i=i+(width/20)){
            for(int j=50; j<height-(height-200); j=j+(height/20)) {
                tp.addNode(i, j, new Robot(aa));
            }
        }

        for(int i=50; i<width-(width-200); i=i+(width/20)){
            for(int j=height-200; j<height; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(aa));
            }
        }

        tp.addNode(width/2, height/2, new Robot(aa));
    }

    private void config6() { //4 groupements de robots aux angles de la map
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-(width-200); i=i+(width/20)){
            for(int j=50; j<height-(height-200); j=j+(height/20)) {
                tp.addNode(i, j, new Robot(aa));
            }
        }

        for(int i=width-200; i<width; i=i+(width/20)){
            for(int j=height-200; j<height; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(aa));
            }
        }

        for(int i=width-200; i<width; i=i+(width/20)){
            for(int j=50; j<height-(height-200); j=j+(height/20)) {
                tp.addNode(i, j, new Robot(aa));
            }
        }

        for(int i=50; i<width-(width-200); i=i+(width/20)){
            for(int j=height-200; j<height; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(aa));
            }
        }
    }


    //Pour ajouter un fond ? Juste du visuel sinon inutile.
    @Override
    public void paintBackground(UIComponent uiComponent, Topology topology) {

    }
}