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


    private transient Polygon polygon;
    private int width=0;
    private double radius=1.414;


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
        //cal=distance b/w centers
        double cal = b.getPosition().get_y() + b.getBallShape().getTranslateY() - position.get_y() - polygon.getTranslateY();
        //So that angle is b/w 0 and 360
        cal=Math.abs(cal);
            if  (cal <= (radius + b.getRadius()+width)){
                    return true;//different color
            }
        return false;
    }

    public void save_star(){
        savedposition.set_x(polygon.getTranslateX());
        savedposition.set_y(polygon.getTranslateY());

    }

    @Override
    public void load_attributes(){
        super.load_attributes();
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
                ar[i]=ar[i]+position.get_x();//displacing
            else
                ar[i]=ar[i]+position.get_y();
        }
        polygon.getPoints().addAll(ar);
        polygon.setFill(Color.WHITE);
        polygon.setStrokeWidth(1.0);
        translateTransition.setNode(polygon); translateTransition.play();

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


