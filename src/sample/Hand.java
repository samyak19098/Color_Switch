package sample;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.util.Duration;
import javafx.application.Platform;

public class Hand extends GameObject{


    private Polygon polygon;
    private int width=0;
    private double radius=1.414;
    private boolean  removed;

    //    private Trail BallTrail;


    public Hand(double x,double y){
        super();
        removed=false;
        position=new Position(x,y);
        polygon   = new Polygon();
        Double[] ar=new Double[]{//y-axis is inverted
                0.0, 0.0,
                5.0, 0.0,
                6.0, -5.0,
                5.0, -6.0,
                4.0, -5.0,
                3.0, -6.0,
                2.0,-5.0,
                1.0,-6.0,
                0.0,-5.0,
                0.0,-10.0,
                -1.0,-10.0,
                -1.0, -5.0,
                -2.0,-5.0,
                -2.0,-4.0,
                -1.0,-3.0,
                0.0,0.0,};
        for(int i=0;i<ar.length;i++){

            ar[i]=ar[i]*10;//scaling
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
//            System.out.println("move down");
            translateTransition.setOnFinished(new EventHandler<ActionEvent>() {//todo: dont create eventhandler  everytime

                public void handle(ActionEvent t) {
                    b.atend();
                }
            });
            //Playing the animation
            translateTransition.play();
        });
    }




    public boolean collisionCheck(Ball b) {
        //returns true  if collision occurs
        //otherwise false


        double cal = b.getPosition().get_y() + b.getBallShape().getTranslateY() - position.get_y() - polygon.getTranslateY();

        cal=Math.abs(cal);

        if  (cal <= (radius + b.getRadius()+width)){


            return true;//different color
        }
        return false;
    }

    public void shownOnScreen(Group g) {
        Platform.runLater(() -> {
            g.getChildren().add(polygon);
        });

    }
    public void removeself(Group grp) {
        polygon.setVisible(false);
        grp.getChildren().remove(polygon);

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
