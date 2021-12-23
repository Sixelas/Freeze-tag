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
        // Voir dans le README|vidéo pour comprendre comment lancer une topologie.


        /** Version Map **/
        //Exemple simple de Map sur algorithme 1 en configuration 0 avec 20 robots. On choisit soi-même son robot initial.
        //new Map(new Topology(800,800),0, 3, 1,20, 2);

        //Exemple de Map sur algorithme 5 en configuration 0 avec 300 robots. Choix du premier robot aléatoire.
        //new Map(new Topology(800,800),0, 0, 5,300, 2);

        //nbBlocs ne sert que pour  les configuration 7, 8 et 9.
        //Pour la configuration 7 il faut renseigner un nbRobots au minimum avec 1 robot par bloc. Par exemple pour nbBlocs=4 il faut au minimum NbRobots = 4*4 = 16.
        //Exemple présenté dans la vidéo pour l'algorithme 7.
        //new Map(new Topology(800,800),8, 3, 7,300, 2);

        /** Version Simulator **/
        //Exemples de simulations sur des algorithmes et instances avec nombre de robots différents :
        //Note : - choisir plusieurs quantités de robots ne sert à rien pour les configutation ou le nombre est fixé d'avance (configs 1,2,3,4,5,6,8,9).
        //       - nbRep > 1 utile seulement si la topologie place aléatoirement les robots, ou si on choisit aléatoirement le premier robot.

        //new Simulator(0,9,10,6,0, new int[]{10,20,40,80,160,320});
        //new Simulator(0,5,20,6,0, new int[]{5,10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,100,160,200,300,320,400,500,600,700,800}); //long
        //new Simulator(6,8,10,8,0, new int[]{100});

    }

    public Map(Topology tp, int config, int firstChoice, int algo, int nbRobots, int nbBlocs){
        this.tp = tp;
        this.tp.setCommunicationRange(60);
        JViewer jv = new JViewer(this.tp);
        jv.getJTopology().setDefaultBackgroundPainter(this);
        this.tp.setNodeModel("robot", Robot.class);
        aa = algo;
        new Configurations(this.tp, config, algo, nbRobots, nbBlocs);
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