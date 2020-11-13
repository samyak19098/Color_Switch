package sample;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.*;
public class ColorSwitcher extends GameObject implements SpecialObject{
    public Polygon s;
    private  TranslateTransition translateTransition;
    static HashMap<Integer,Color> map=new HashMap<Integer,Color>();
    static {
        map.put(0,Color.PURPLE);
        map.put(1,Color.CYAN);
        map.put(2,Color.DEEPPINK);
        map.put(3,Color.YELLOW);
    }

    public ColorSwitcher() {


//        BallTrail = t;
        translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(250));

        //Setting the node for the transition
        translateTransition.setNode(s);
//        Position x=new Position(s.)
    }
    @Override
    public void shownOnScreen(Group g) {

        g.getChildren().add(s);

    }
    @Override
    public void draw(){
        //todo
    }
    @Override
public void specialChange(Ball b) {
    Color x=b.getColor();
       int t=((int)(Math.random()*4));
while(x==map.get(t)){
    t=(int)(Math.random()*4);
}
b.setColor(map.get(t));
}
public boolean collisionCheck(Ball b)  {
return true;
}
}
