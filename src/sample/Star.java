package sample;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.util.Duration;
import javafx.application.Platform;

public class Star extends GameObject{


    private Polygon polygon;
    private int width=0;
    private double radius=1.414;


    //    private Trail BallTrail;

    public Star( ) {
    }
    public Star(double x,double y){
         super();
        position=new Position(x,y);
        polygon   = new Polygon();
        Double[] ar=new Double[]{//y-axis is inverted
                0.0, -2.0,
                1.0, -1.0,
                2.0, -1.0,
                1.0, -0.0,
                2.0, 2.0,
                0.0, 1.0,
                -2.0,2.0,
                -1.0,-0.0,
                -2.0,-1.0,
                -1.0,-1.0,
                0.0, -2.0};
        for(int i=0;i<ar.length;i++){

            ar[i]=ar[i]*20;//scaling
            if(i%2==0)
                ar[i]=ar[i]+x;//displacing
            else
                ar[i]=ar[i]+y;
        }
        polygon.getPoints().addAll(ar);
        polygon.setFill(Color.WHITE);
        polygon.setStrokeWidth(1.0);
        translateTransition.setNode(polygon);
    }

    public void movedown(Ball b,Trail BallTrail) {
        Platform.runLater(() -> {
            translateTransition.stop();
            translateTransition.setToY(Double.NaN);
            translateTransition.setByY(movedistance);

            //Setting the cycle count for the transition
            translateTransition.setCycleCount(1);
            translateTransition.setDuration(Duration.millis(movtime));
            //Setting auto reverse value to false
            // translateTransition.setAutoReverse(false);
//            System.out.println("move down");
            translateTransition.setOnFinished(new EventHandler<ActionEvent>() {//todo: dont create eventhandler  everytime

                public void handle(ActionEvent t) {
                    translateTransition.setByY(0); b.atend();
                    if(BallTrail!=null)
                    BallTrail.setAtend(false);
                }
            });
            //Playing the animation
            translateTransition.play();
        });
    }




    public boolean collisionCheck(Ball b) {
        //returns true  if collision occurs
        //otherwise false

        //System.out.println("will do");
//        System.out.println("b.getShape().getTranslateX():" + b.getBallShape().getTranslateX());
//        System.out.println("b.getShape().getTranslateY():" + b.getBallShape().getTranslateY());
//        System.out.println("b.getPosition().get_x():" + b.getPosition().get_x());
//        System.out.println("b.getPosition().get_y():" + b.getPosition().get_y());
//        System.out.println("b.getPosition().getRadius():" + b.getRadius());


//        System.out.println("i:" + i);
//        System.out.println(b.getColor() == quarters.get(i).getFill());

//        System.out.println("ang:" + ang);
        //cal=distance b/w centers
        double cal = b.getPosition().get_y() + b.getBallShape().getTranslateY() - position.get_y() - polygon.getTranslateY();
//        System.out.println("quarters.get(i).getTranslateY():" + quarters.get(i).getTranslateY());
//        System.out.println("cal:" + cal);

//        System.out.println("ang2:" + ang);
        //So that angle is b/w 0 and 360

//        System.out.println("ang3:" + ang);
        cal=Math.abs(cal);
//            System.out.println("(radius - b.getRadius()) <= (cal):" +( (radius - b.getRadius()) <= (cal)));
//            System.out.println("(radius + b.getRadius()) >= (cal):" +( (radius + b.getRadius()) >= (cal)));
//            System.out.println("1:" + ((radius - b.getRadius()) <= (cal)));
//            System.out.println("2:" + (cal <= (radius + b.getRadius()+width)));
//            System.out.println("2.1:" +cal);
//                    System.out.println("2.2:" +(radius + b.getRadius()+width));
            if  (cal <= (radius + b.getRadius()+width)){


                    return true;//different color
            }


        //System.out.println("b.getShape().getTranslateY():"+b.getBallShape().getr());

        return false;
    }

    public void shownOnScreen(Group g) {
        Platform.runLater(() -> {
            g.getChildren().add(polygon);
        });

    }

    @Override
    public void draw() {

    }
    public Polygon getPolygon() {
        return polygon;
    }
    public TranslateTransition getTranslateTransition() {
        return translateTransition;
    }
}
