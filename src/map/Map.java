package map;

import io.jbotsim.core.Color;
import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.ui.JViewer;
import io.jbotsim.ui.painting.BackgroundPainter;
import io.jbotsim.ui.painting.UIComponent;
import robot.Robot;
import robot.algos.Algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Map implements BackgroundPainter{

    private Topology tp;
    int aa;

    public static void main(String[] args) {

        // ICI ON CHANGE LES PARAMETRES
        // type 1 = config1
        // type 2 = Génère des robots aléatoirement (on peut choisir le nombre)
        new Map(1);    //Avec ça on controle quel type de map on veut


        //new Simulator(0,5,10,0, new int[]{10,20,40,80,160,320});
        //type = quelle config de robots : 0 random, 1 config1, ..., 5 config5.
        //algo = quel algo : 1 algo1, ..., 4 algo4.
        //nbRep = combien de répétitions par algo et par taille.
        //firstChoice = quel choix de premier robot : 0 random, 1 gentil, 2 adversaire.
        //int[] tabSizes = Le nombre de robots à générer par map. (Pour l'instant fonctionne que sur type = 0 config random)


    }

    public Map(int type){

        tp = new Topology(800,800);
        tp.setCommunicationRange(60);
        JViewer jv = new JViewer(tp);
        jv.getJTopology().setDefaultBackgroundPainter(this);
        tp.setNodeModel("robot", Robot.class);


        switch (type) {
            case 1 : //On lance la config
                config5();
                break;
            case 2 : //Génère des robots aléatoirement
                generateRobots(700);
                break;
        }
        //ICI ON CHANGE LES PARAMETRES
        chooseFirst(0,5); //type = méthode pour choisir le premier robot.
        // algo = quel algo sera utilisé par tous les robots.
        // type 0 = random choice
        // type 1 = the first robot of the list is chosen
        // type 2 = the robot with highest degree is chosen
        // type>2 ou type<0 = on choisit notre robot
        // algo 1  = algo1 (voir dans Algorithms.java)
        // algo 2  = algo2 (voir dans Algorithms.java)
        // algo 3  = algo3 (voir dans Algorithms.java)
        // algo 4 = algo4
        // algo 5 = algo5
        // algo>4 ou algo<1  = algo1
    }

    private void chooseFirst(int type, int algo) { //Choix du premier robot à réveiller
        Node z = null;
        aa = algo;
        if(type == 0){ //random choice

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

        }else if (type == 1){ //the first robot of the list is chosen

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

        }else if (type == 2){ //same as algo4
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
            //tp.start();
            long timer2 = System.currentTimeMillis();
            while(!finish()){
            }
            System.out.println(System.currentTimeMillis() - timer2);
            System.out.println("time : "+(tp.getTime()-timer));
            return;
        }

        for(Node n : tp.getNodes()){
            if(n instanceof Robot){
                if(n instanceof Robot) {
                    ((Robot) n).a = aa;
                }
            }
        }
        int timer = tp.getTime();
        long timer2 = System.currentTimeMillis();
        tp.start();
        while(!finish()){
        }
        System.out.println(System.currentTimeMillis() - timer2);
        System.out.println("time : "+(tp.getTime()-timer));

    }

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

    private void generateRobots(int nbRobots) { //Robots placés au hasard
        int width = tp.getWidth();
        int height = tp.getHeight();
        Random r  = new Random(10);
        for(int i=0; i<nbRobots; i++){

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

    private void config2() { //Quadrillage de robots (pas bcp de robots)
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-50; i=i+(width/5)){
            for(int j=50; j<height-50; j=j+(height/5)) {
                tp.addNode(i, j, new Robot());
            }
        }
    }

    private void config3() { //Quadrillage de robots (bcp de robots)
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-50; i=i+(width/20)){
            for(int j=50; j<height-50; j=j+(height/20)) {
                tp.addNode(i, j, new Robot());
            }
        }
    }

    private void config4() { //théorie pour prouver qu'il faut choisir en premier un robot avec pleins de voisins
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-300; i=i+(width/20)){
            for(int j=50; j<height-300; j=j+(height/20)) {
                tp.addNode(i, j, new Robot());
            }
        }
        tp.addNode(width-10, height-10, new Robot());
        tp.addNode(10, height-10, new Robot());
    }

    private void config5() { //théorie pour prouver qu'il faut choisir en premier un robot avec pleins de voisins
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-(width-200); i=i+(width/20)){
            for(int j=50; j<height-(height-200); j=j+(height/20)) {
                tp.addNode(i, j, new Robot());
            }
        }

        for(int i=width-200; i<width; i=i+(width/20)){
            for(int j=height-200; j<height; j=j+(height/20)) {
                tp.addNode(i, j, new Robot());
            }
        }

        for(int i=width-200; i<width; i=i+(width/20)){
            for(int j=50; j<height-(height-200); j=j+(height/20)) {
                tp.addNode(i, j, new Robot());
            }
        }

        for(int i=50; i<width-(width-200); i=i+(width/20)){
            for(int j=height-200; j<height; j=j+(height/20)) {
                tp.addNode(i, j, new Robot());
            }
        }


        //tp.addNode(width-10, height-10, new Robot());
        tp.addNode(width/2, height/2, new Robot());
    }

    @Override
    public void paintBackground(UIComponent uiComponent, Topology topology) {

    }
}