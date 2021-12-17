package map;

import io.jbotsim.core.Topology;
import robot.Robot;

import java.util.Random;

public class Configurations {

    Topology tp;
    int algorithm;
    int nbRobots;
    int nbBlocs;


    public Configurations(Topology tp, int config, int algorithm, int nbRobots, int nbBlocs){
        this.tp = tp;
        this.algorithm = algorithm;
        this.nbRobots = nbRobots;
        this.nbBlocs = nbBlocs;

        switch (config) {
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
            case 7 :
                config7(nbRobots,nbBlocs);
                break;
            case 8 :
                config8(nbBlocs);
                break;
            case 9 :
                config9(nbBlocs);
                break;
            default: //Génère des robots aléatoirement
                generateRobots(nbRobots);
                break;
        }
    }



    public void generateRobots(int nbRobots) { //nbRobots Robots placés au hasard
        int width = tp.getWidth();
        int height = tp.getHeight();
        Random r  = new Random(10);
        for(int i=0; i<nbRobots; i++){

            tp.addNode(10+r.nextInt(width-20), 10+r.nextInt(height-20), new Robot(algorithm));
        }
    }

    public void config1() { //Quadrillage de robots
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-50; i=i+(width/10)){
            for(int j=50; j<height-50; j=j+(height/10)) {
                tp.addNode(i, j, new Robot(algorithm));
            }
        }
    }

    public void config2() { //Quadrillage de robots (pas bcp de robots)
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-50; i=i+(width/5)){
            for(int j=50; j<height-50; j=j+(height/5)) {
                tp.addNode(i, j, new Robot(algorithm));
            }
        }
    }

    public void config3() { //Quadrillage de robots (bcp de robots)
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-50; i=i+(width/20)){
            for(int j=50; j<height-50; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algorithm));
            }
        }
    }

    public void config4() { //Config théorique pour prouver qu'il faut choisir en premier un robot avec pleins de voisins.
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-300; i=i+(width/20)){
            for(int j=50; j<height-300; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algorithm));
            }
        }
        tp.addNode(width-10, height-10, new Robot(algorithm));
        tp.addNode(10, height-10, new Robot(algorithm));
    }

    public void config5() { //Config théorique pour prouver qu'il faut choisir en premier un robot avec pleins de voisins
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-(width-200); i=i+(width/20)){
            for(int j=50; j<height-(height-200); j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algorithm));
            }
        }

        for(int i=width-200; i<width; i=i+(width/20)){
            for(int j=height-200; j<height; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algorithm));
            }
        }

        for(int i=width-200; i<width; i=i+(width/20)){
            for(int j=50; j<height-(height-200); j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algorithm));
            }
        }

        for(int i=50; i<width-(width-200); i=i+(width/20)){
            for(int j=height-200; j<height; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algorithm));
            }
        }

        tp.addNode(width/2, height/2, new Robot(algorithm));
    }

    public void config6() { //4 groupements de robots aux angles de la map
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int i=50; i<width-(width-200); i=i+(width/20)){
            for(int j=50; j<height-(height-200); j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algorithm));
            }
        }

        for(int i=width-200; i<width; i=i+(width/20)){
            for(int j=height-200; j<height; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algorithm));
            }
        }

        for(int i=width-200; i<width; i=i+(width/20)){
            for(int j=50; j<height-(height-200); j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algorithm));
            }
        }

        for(int i=50; i<width-(width-200); i=i+(width/20)){
            for(int j=height-200; j<height; j=j+(height/20)) {
                tp.addNode(i, j, new Robot(algorithm));
            }
        }
    }

    public void config7(int nbRobots, int nbBlocs) { // nbBlocs*nbBlocs groupements en largeur*hauteur de robots
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int x = 1; x<=nbBlocs; x++){
            for(int y = 1; y<=nbBlocs; y++){
                int ir = x*(width/(nbBlocs)) - (width/(nbBlocs))  ;
                int jr = y*(height/(nbBlocs))-(height/(nbBlocs));
                Random r = new Random();
                //System.out.println("carre  : ["+ir+","+jr+"] "+ "["+(x*(width/(nbBlocs)))+","+(y*(height/(nbBlocs)))+"]");
                for(int i=0;i<(nbRobots/(nbBlocs*nbBlocs)); i++){
                    int rx = (ir+50)+r.nextInt((x*(width/(nbBlocs)))-(ir+60));
                    int ry = (jr+50)+r.nextInt((y*(height/(nbBlocs)))-(jr+60));
                    //System.out.println("rx : "+rx+", ry : "+ry);
                    tp.addNode(rx, ry, new Robot(algorithm));
                }
            }
        }
    }

    public void config8(int nbBlocs) { // nbBlocs*nbBlocs groupements en largeur*hauteur de robots. Pour cchaque bloc on peut avoir aléatoirement entre 1 et 40 robots.
        int width = tp.getWidth();
        int height = tp.getHeight();

        for(int x = 1; x<=nbBlocs; x++){
            for(int y = 1; y<=nbBlocs; y++){
                int ir = x*(width/(nbBlocs)) - (width/(nbBlocs))  ;
                int jr = y*(height/(nbBlocs))-(height/(nbBlocs));
                Random r = new Random();
                //System.out.println("carre  : ["+ir+","+jr+"] "+ "["+(x*(width/(nbBlocs)))+","+(y*(height/(nbBlocs)))+"]");
                for(int i=0;i<(1+r.nextInt(40)-1); i++){
                    int rx = (ir+50)+r.nextInt((x*(width/(nbBlocs)))-(ir+60));
                    int ry = (jr+50)+r.nextInt((y*(height/(nbBlocs)))-(jr+60));
                    //System.out.println("rx : "+rx+", ry : "+ry);
                    tp.addNode(rx, ry, new Robot(algorithm));
                }
            }
        }
    }

    public void config9(int nbBlocs) {  // nbBlocs*nbBlocs groupements en largeur*hauteur de robots. Pour cchaque bloc on peut avoir aléatoirement entre 1 et 80 robots.
        int width = tp.getWidth();      // on remplit pas tous les blocs
        int height = tp.getHeight();

        for(int x = 1; x<=nbBlocs; x++){
            for(int y = 1; y<=nbBlocs; y++){
                if((x%2 ==0) || (y%2 ==0)){
                    int ir = x*(width/(nbBlocs)) - (width/(nbBlocs))  ;
                    int jr = y*(height/(nbBlocs))-(height/(nbBlocs));
                    Random r = new Random();
                    for(int i=0;i<(1+r.nextInt(80)-1); i++){
                        int rx = (ir+50)+r.nextInt((x*(width/(nbBlocs)))-(ir+60));
                        int ry = (jr+50)+r.nextInt((y*(height/(nbBlocs)))-(jr+60));
                        tp.addNode(rx, ry, new Robot(algorithm));
                    }
                }
            }
        }
    }
}
