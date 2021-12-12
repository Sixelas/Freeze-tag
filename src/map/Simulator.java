package map;

import io.jbotsim.core.Color;
import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import robot.Robot;
import robot.algos.Algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Simulator {

    private Topology tp;
    public long[][] result;
    private int type;
    private int algo;
    private int nbRep;
    private int firstChoice;
    private int[] tabSizes;

    public static PrintWriter writer;

    public Simulator(int type, int algo, int nbRep, int firstChoice, int[] tabSizes){

        this.result = new long[tabSizes.length][nbRep];
        this.type = type;
        this.algo = algo;
        this.nbRep = nbRep;
        this.firstChoice = firstChoice;
        this.tabSizes = tabSizes;

        launchSimulation();
        writeResult();
        System.exit(0);
    }


    private void launchSimulation() {

        for(int s=0; s<tabSizes.length; s++){

            for(int i = 0; i<nbRep; i++){

                tp = new Topology(800,800);
                tp.setCommunicationRange(60);
                tp.setDefaultNodeModel(Robot.class);
                Node firstRobot = null;

                switch (type) {
                    case 0 :
                        generateRandom(tabSizes[s], algo);
                        break;
                    case 1 :
                        config1(algo);
                        break;
                    case 2 :
                        config2(algo);
                        break;
                    case 3 :
                        config3(algo);
                        break;
                    case 4 :
                        config4(algo);
                        break;
                    case 5 :
                        config5(algo);
                        break;
                    case 6 :
                        config6(algo);
                        break;
                }

                switch (firstChoice){

                    case 0 : //Random choice
                        Random r = new Random();
                        firstRobot = tp.getNodes().get(r.nextInt(tp.getNodes().size()));
                        ((Robot)firstRobot).setAwake(true);
                        ((Robot)firstRobot).setIconSize(25);
                        ((Robot)firstRobot).setColor(new Color(Color.CYAN));
                        ((Robot)firstRobot).setAwake(true);
                        ((Robot)firstRobot).setCommunicationRange(0);
                        break;
                    case 1 : //Gentil
                        int bestDegree = -1;
                        for(Node n : tp.getNodes()){
                            if(bestDegree < n.getNeighbors().size()){
                                bestDegree = n.getNeighbors().size();
                                firstRobot = n;
                            }
                        }
                        ((Robot)firstRobot).setAwake(true);
                        ((Robot)firstRobot).setIconSize(25);
                        ((Robot)firstRobot).setColor(new Color(Color.CYAN));
                        ((Robot)firstRobot).setCommunicationRange(0);
                        break;
                    case 2 : //Méchant
                        int badDegree = Integer.MAX_VALUE;
                        for(Node n : tp.getNodes()){
                            if(n.getNeighbors().size() ==0){
                                firstRobot = n;
                                break;
                            }else if (badDegree > n.getNeighbors().size()){
                                badDegree = n.getNeighbors().size();
                                firstRobot = n;
                            }
                        }
                        ((Robot)firstRobot).setAwake(true);
                        ((Robot)firstRobot).setIconSize(25);
                        ((Robot)firstRobot).setColor(new Color(Color.CYAN));
                        ((Robot)firstRobot).setCommunicationRange(0);
                        break;
                }

                Algorithms a = new Algorithms(tp);
                if(algo == 1){
                    a.algo1((Robot)firstRobot);
                }else if(algo == 2) {
                    a.algo2((Robot)firstRobot);
                }else if(algo == 3) {
                    a.algo3((Robot)firstRobot);
                }else if(algo == 4) {
                    a.algo4((Robot)firstRobot);
                }else if(algo == 5) {
                    a.algo5((Robot)firstRobot);
                }else{
                    a.algo1((Robot)firstRobot);
                }

                tp.start();
                int timer = tp.getTime();
                System.out.println("nbRobots : "+tabSizes[s]+ " iteration : "+(i+1)+" en cours...");
                while(!finish()){
                }
                System.out.println("time : "+(tp.getTime()-timer));
                result[s][i] = (tp.getTime()-timer);
                tp.pause();
                tp.clear();
            }
        }

    }

//Fonction pour écrire toutes les datas obtenues dans un fichier.
    private void writeResult(){
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            writer = new PrintWriter(new File(s+File.separator+"simulations"+File.separator+type+"-"+algo+"-"+firstChoice+"_"+dtf.format(java.time.LocalDateTime.now())+".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.write("#type  algo  firstChoice  nbRobots  time  resType\n");

        for(int s = 0; s<tabSizes.length; s++){
            long pire = -1;
            long meilleur = Long.MAX_VALUE;
            long moyenne = 0;

            for(int t = 0; t<nbRep; t++){
                moyenne = moyenne+result[s][t];
                if(pire < result[s][t]){
                    pire = result[s][t];
                }
                if(meilleur > result[s][t]){
                    meilleur = result[s][t];
                }
            }
            writer.write(type +" "+ algo +" "+ firstChoice+" " + tabSizes[s] +" " +(moyenne/nbRep)+" "+0+"\n");
            writer.write(type +" "+ algo +" "+ firstChoice+" " + tabSizes[s] +" " +meilleur+" "+1+"\n");
            writer.write(type +" "+ algo +" "+ firstChoice+" " + tabSizes[s] +" " +pire+" "+2+"\n");
        }

        writer.flush();
        writer.close();
    }

    private void generateRandom(int nbRobots, int algo) { //nbRobots placés au hasard
        int width = tp.getWidth();
        int height = tp.getHeight();
        Random r  = new Random(10);
        for(int i=0; i<nbRobots; i++){
            tp.addNode(10+r.nextInt(width-20), 10+r.nextInt(height-20), new Robot(algo));
        }
    }

    private void config1(int algo) { //Quadrillage de robots
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-50; i=i+(width/10)){
            for(int j=50; j<height-50; j=j+(height/10)) {
                tp.addNode(i, j, new Robot(algo));
            }
        }
    }

    private void config2(int algo) { //Quadrillage de robots (pas bcp de robots)
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-50; i=i+(width/5)){
            for(int j=50; j<height-50; j=j+(height/5)) {
                tp.addNode(i, j, new Robot(algo));
            }
        }
    }

    private void config3(int algo) { //Quadrillage de robots (bcp de robots)
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-50; i=i+(width/20)){
            for(int j=50; j<height-50; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algo));
            }
        }
    }

    private void config4(int algo) { //théorie pour prouver qu'il faut choisir en premier un robot avec pleins de voisins
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-300; i=i+(width/20)){
            for(int j=50; j<height-300; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algo));
            }
        }
        tp.addNode(width-10, height-10, new Robot(algo));
        tp.addNode(10, height-10, new Robot(algo));
    }

    private void config5(int algo) { //théorie pour prouver qu'il faut choisir en premier un robot avec pleins de voisins
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=10; i<(width/3); i=i+(width/20)){
            for(int j=10; j<(height/3); j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algo));
            }
        }

        for(int i=2*(width/3); i<width-10; i=i+(width/20)){
            for(int j=2*(height/3); j<height-10; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algo));
            }
        }

        for(int i=2*(width/3); i<width-10; i=i+(width/20)){
            for(int j=10; j<(height/3); j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algo));
            }
        }

        for(int i=10; i<(width/3); i=i+(width/20)){
            for(int j=2*(height/3); j<height-10; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algo));
            }
        }

        tp.addNode(width/2, height/2, new Robot(algo));
    }

    private void config6(int algo) { //4 groupements de robots aux angles de la map
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-(width-200); i=i+(width/20)){
            for(int j=50; j<height-(height-200); j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algo));
            }
        }

        for(int i=width-200; i<width; i=i+(width/20)){
            for(int j=height-200; j<height; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algo));
            }
        }

        for(int i=width-200; i<width; i=i+(width/20)){
            for(int j=50; j<height-(height-200); j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algo));
            }
        }

        for(int i=50; i<width-(width-200); i=i+(width/20)){
            for(int j=height-200; j<height; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algo));
            }
        }
    }

    private boolean finish() {
        for(Node n : tp.getNodes()){
            if(n instanceof Robot){
                if( !((Robot) n).isAwake() ){
                    return false;
                }
            }
        }
        tp.pause();
        return true;
    }

    private boolean finish2() {
        if(algo == 5){
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

}
