package sample;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import javafx.scene.image.*;
import javafx.scene.paint.ImagePattern;

import java.io.Serializable;
import java.util.*;
public class ColorSwitcher extends GameObject implements SpecialObject, Serializable {


    protected transient Circle circle;

    static transient HashMap<Integer,Color> map=new HashMap<Integer,Color>();
    static transient HashMap<Color,Integer> getcolormap=new HashMap<Color,Integer>();
    private double radius;
    static {
        map.put(0,Color.PURPLE);
        map.put(1,Color.CYAN);
        map.put(2,Color.DEEPPINK);
        map.put(3,Color.YELLOW);
        map.put(4,Color.WHITE);
        getcolormap.put(Color.PURPLE,0);
        getcolormap.put(Color.CYAN,1);
        getcolormap.put(Color.DEEPPINK,2);
        getcolormap.put(Color.YELLOW,3);
        getcolormap.put(Color.WHITE,4);
    }
    public ColorSwitcher(int x){

    }
    public ColorSwitcher( double x,double y,double radius) {
        //keep the radius same as ball :looks good
        position =new Position(x,y);
        this.radius = radius;
        circle=new   Circle(x, y, radius,Color.WHITE);
        Image im = new Image("file:sss.png",false);
        circle.setFill(new ImagePattern(im));
//        BallTrail = t;

        translateTransition.setDuration(Duration.millis(movtime));

        //Setting the node for the transition
        translateTransition.setNode(circle);
//      Position x=new Position(s.)
    }
    public void movedown(Ball b) {
        Platform.runLater(() -> {
            translateTransition.stop();
                    translateTransition.setToY(Double.NaN);
                    translateTransition.setByY(movedistance);
                    //Setting the cycle count for the transition
                    translateTransition.setCycleCount(1);
                    translateTransition.setDuration(Duration.millis(movtime));
                    //Setting auto reverse value to false
                    // translateTransition.setAutoReverse(false);
//                    System.out.println("move down");
                    translateTransition.setOnFinished(new EventHandler<ActionEvent>() {//todo: dont create eventhandler  everytime
                        @Override
                        public void handle(ActionEvent t) {
                            translateTransition.setByY(0); b.atend();
                        }
                    });
                    //Playing the animation
                    translateTransition.play();
                });
//        collisionCheck(b);
    }
    @Override
    public void shownOnScreen(Group g) {
        Platform.runLater(() -> {
            g.getChildren().add(circle);
        });
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
        //returns true  if collision occurs
        //otherwise false
        //System.out.println("will do");
//        System.out.println("b.getShape().getTranslateX():" + b.getBallShape().getTranslateX());
//        System.out.println("b.getShape().getTranslateY():" + b.getBallShape().getTranslateY());
//        System.out.println("b.getPosition().get_x():" + b.getPosition().get_x());
//        System.out.println("b.getPosition().get_y():" + b.getPosition().get_y());
//        System.out.println("b.getPosition().getRadius():" + b.getRadius());
        //cal=distance b/w centers
        double cal = b.getPosition().get_y() + b.getBallShape().getTranslateY() - position.get_y() - circle.getTranslateY();
//        System.out.println("quarters.get(i).getTranslateY():" + quarters.get(i).getTranslateY());
//        System.out.println("cal:" + cal);
//        System.out.println("ang3:" + ang);
        cal=Math.abs(cal);

//            System.out.println("(radius - b.getRadius()) <= (cal):" +( (radius - b.getRadius()) <= (cal)));
//            System.out.println("(radius + b.getRadius()) >= (cal):" +( (radius + b.getRadius()) >= (cal)));
//            System.out.println("1:" + ((radius - b.getRadius()) <= (cal)));
//            System.out.println("2:" + (cal <= (radius + b.getRadius()+width)));
//            System.out.println("2.1:" +cal);
//                    System.out.println("2.2:" +(radius + b.getRadius()+width));
            if  (cal <= (circle.getRadius()  + b.getRadius())) {
                return true;//different color
            }

        return false;
    }

    public void save_color_switcher(){
        savedposition.set_x(circle.getTranslateX());
        savedposition.set_y(circle.getTranslateY());
    }

    @Override
    public void load_attributes(){
        super.load_attributes();
        circle=new   Circle(position.get_x(), position.get_y(),radius ,Color.WHITE);
        Image im = new Image("file:sss.png",false);
        circle.setFill(new ImagePattern(im));
        translateTransition.setDuration(Duration.millis(1));
        translateTransition.setNode(circle);
        translateTransition.play();
        translateTransition.setDuration(Duration.millis(movtime));
    }

    public Circle getCircle() {
        return circle;
    }
}
