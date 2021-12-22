package map;

import io.jbotsim.core.Color;
import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.core.event.ClockListener;
import io.jbotsim.ui.JViewer;
import io.jbotsim.ui.painting.BackgroundPainter;
import io.jbotsim.ui.painting.UIComponent;
import robot.Robot;
import robot.algos.Algorithms;

import java.awt.*;
import java.util.Random;

public class Map implements BackgroundPainter, ClockListener {

    Topology tp;
    int aa;

    public static void main(String[] args) {

        // ICI on choisit entre version Map et version Simulator.
        // Voir dans le README pour comprendre comment lancer une topologie.

        //new Map(new Topology(800,800),5, 9, 8,800, 4);

        new Simulator(9,5,30,6,0, new int[]{100});

    }

    public Map(Topology tp, int type, int firstChoice, int algo, int nbRobots, int nbBlocs){
        this.tp = tp;
        //tp = new Topology(800,800);
        this.tp.setCommunicationRange(60);
        JViewer jv = new JViewer(this.tp);
        jv.getJTopology().setDefaultBackgroundPainter(this);
        this.tp.setNodeModel("robot", Robot.class);
        aa = algo;
        new Configurations(this.tp, type,algo, nbRobots, nbBlocs);
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
                a.algo5((Robot) z);
            }else if(algo == 6) {
                a.algo6((Robot)z);
            }else if(algo == 7) {
                a.algo7((Robot)z);
            }else if(algo == 8) {
                a.algo8((Robot)z);
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
            }else if(algo == 6) {
                a.algo6((Robot)z);
            }else if(algo == 7) {
                a.algo7((Robot)z);
            }else if(algo == 8) {
                a.algo8((Robot)z);
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
            }else if(algo == 6) {
                a.algo6((Robot)z);
            }else if(algo == 7) {
                a.algo7((Robot)z);
            }else if(algo == 8) {
                a.algo8((Robot)z);
            }else{
                a.algo1((Robot)z);
                aa = 1;
            }

        }else{ //Choix libre
            System.out.println("choisir un robot avec ctrl+clic puis lancer avec start execution !");
            if((algo < 1) || (algo > 8) ){
                aa = 1;
            }
            for(Node n : tp.getNodes()){
                if(n instanceof Robot) {
                    ((Robot) n).a = aa;
                }
            }
            tp.addClockListener(this);
            return;
        }

        tp.addClockListener(this);
        tp.start();
    }

    // Cette fonction détecte quand tous les robots sont réveillés. Elle a été remplacée par onClock() !
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

    //Pour ajouter un fond ? Juste du visuel sinon inutile.
    @Override
    public void paintBackground(UIComponent uiComponent, Topology topology) {
        /** //A COMPRENDRE POUR PLUS TARD
        Graphics2D g2d = (Graphics2D) uiComponent.getComponent();
        g2d.drawImage();
         **/
    }

    @Override
    public void onClock() {
        int cpt=0;
        for(Node n : tp.getNodes()){
            if(n instanceof Robot){
                if( ((Robot) n).isAwake() ){
                    cpt++;
                }
            }
        }
        if(cpt == tp.getNodes().size()){
            tp.pause();
            System.out.println("Temps : "+tp.getTime());
            System.exit(0);
        }
    }
}