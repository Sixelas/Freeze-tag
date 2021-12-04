package robot;

import io.jbotsim.core.Node;
import io.jbotsim.ui.icons.Icons;

public class Robot extends Node {
    private double speed;


    public Robot(){
        setIcon(Icons.ROBOT);
        setIconSize((int)(getIconSize()*1.5));
        speed = 1;
    }

    @Override
    public void onStart() {
        //super.onStart();
    }

    @Override
    public void onClock() {
        //super.onClock();
    }

}
