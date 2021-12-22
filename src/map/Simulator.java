package map;

import io.jbotsim.core.Color;
import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.ui.JViewer;
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
    private int config;
    private int algo;
    private int nbRep;
    private int firstChoice;
    private int[] tabSizes;
    private int nbBlocs;

    public static PrintWriter writer;

    public Simulator(int config, int algo, int nbRep, int nbBlocs, int firstChoice, int[] tabSizes){

        this.result = new long[tabSizes.length][nbRep];
        this.config = config;
        this.algo = algo;
        this.nbRep = nbRep;
        this.firstChoice = firstChoice;
        this.tabSizes = tabSizes;
        this.nbBlocs = nbBlocs;

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
                //JViewer jv = new JViewer(tp);
                Node firstRobot = null;
                new Configurations(tp,config,algo,tabSizes[s],nbBlocs);
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
                }else if(algo == 6) {
                    a.algo6((Robot)firstRobot);
                }
                else if(algo == 7) {
                    a.algo7((Robot)firstRobot);
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
            writer = new PrintWriter(new File(s+File.separator+"simulations"+File.separator+config+"-"+algo+"-"+firstChoice+"_"+dtf.format(java.time.LocalDateTime.now())+".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.write("#config  algo  firstChoice  nbRobots  time  resType\n");

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
            writer.write(config +" "+ algo +" "+ firstChoice+" " + tabSizes[s] +" " +(moyenne/nbRep)+" "+0+"\n");
            writer.write(config +" "+ algo +" "+ firstChoice+" " + tabSizes[s] +" " +meilleur+" "+1+"\n");
            writer.write(config +" "+ algo +" "+ firstChoice+" " + tabSizes[s] +" " +pire+" "+2+"\n");
        }

        writer.flush();
        writer.close();
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
