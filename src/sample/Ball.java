package sample;
//https://gist.github.com/jewelsea/4569878
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import javafx.scene.shape.Circle;
import java.util.*;

public class Ball extends GameObject {
    public final int id;
    private final int movtime=250;//time duration of one move
    private final int movedistance = 100;//distance moved in one move
    private transient Color BallColor;
    private transient Shape BallShape;
    private int savedcolor;
    private double radius;
    public int n=10;




    public Ball(Color c, Shape s) {
    id=-1;
    }
    public Ball(Color c,double x,double y,double r,int id) {
        super();

        this.id=id;
        BallColor = c;
        savedcolor=ColorSwitcher.getcolormap.get(BallColor);
        BallShape=new Circle(x,y,r,c);
//        BallShape.setVisible(false);
//        BallTrail = t;
        //Setting the node for the transition
        translateTransition.setNode(BallShape);
        position=new Position(x,y);
        radius=r;
        ////
        savedposition.set_x();

    }
    public Ball(Color c, Shape s,double x,double y,double r) {
        id = 0;
    }



    public void MoveBall(Group grp) {
        Platform.runLater(() -> {
            translateTransition.stop();
//            for (int i = 0; i < (n); i++) {
               // trail.get(i). setCenterY(position.get_y()+BallShape.getTranslateY() -((movedistance*(i))/n) );
//                t2.get(n-1-i).stop();
//            }
            translateTransition.setToY(Double.NaN);
            translateTransition.setByY(-movedistance);

            //Setting the cycle count for the transition
            translateTransition.setCycleCount(1);
            translateTransition.setDuration(Duration.millis(movtime));
            //Setting auto reverse value to false
            // translateTransition.setAutoReverse(false);
//            System.out.println("move up");

            //Playing the animation
            translateTransition.setOnFinished(new EventHandler<ActionEvent>() {//todo: dont create eventhandler  everytime
                @Override
                public void handle(ActionEvent t) {
                    atend();
                }
            });
            translateTransition.play();
//            System.out.println(" 0:"+Duration.ZERO);


        });
    }
    public void atend(){
        Platform.runLater(() -> {

//            System.out.println("move ball down ");
//        System.out.println("circle.getTranslateX():"+BallShape.getTranslateX());
//        System.out.println("circle.getTranslateY():"+BallShape.getTranslateY());
//        System.out.println("circle.getCenterY():"+circle.getCenterY());
//        System.out.println("circle.getCenterX():"+circle.getCenterX());
            translateTransition.setToY(screenheight-position.get_y()+(2*radius));
//            translateTransition.setToY(0);
            translateTransition.setCycleCount(1);
            translateTransition.setDuration(Duration.millis((2*movtime)));
            translateTransition.play();
            translateTransition.setOnFinished(null);
//            for (int i = 0; i < (n); i++) {
//                trail.get(i).setVisible(false);
//                trail.get(i). setCenterY(position.get_y()+BallShape.getTranslateY() -((BallShape.getTranslateY()*(i+1))/n) );
////                System.out.println("i:"+i);
////                t2.get(i).getKeyFrames().clear();
////                t2.get(i).getKeyFrames().add(new KeyFrame(Duration.millis((movtime*1)/1), new KeyValue(trail.get(i).visibleProperty(), true)));
//                t2.get(i).play();
//            }

        });

    }
    public void atendtrail(){
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
            translateTransition.setOnFinished(null);
            //Playing the animation
            translateTransition.play();
        });

    }

    public void reposition(){
        Platform.runLater(() -> {
            translateTransition.stop();
            translateTransition.setToY(Double.NaN);
            translateTransition.setByY(-movedistance);
            translateTransition.setCycleCount(1);
            translateTransition.setDuration(Duration.millis(1));
            translateTransition.setOnFinished(null);
            translateTransition.play();
        });

    }

    public boolean outofBounds(){
        if((position.get_y()+BallShape.getTranslateY())>(screenheight+radius))
                                                    //  600 + 0   >  800+20
            return true;
        return false;
    }
    @Override
    public void shownOnScreen(Group g) {
        Platform.runLater(() -> {
            g.getChildren().add(BallShape);
        });

    }
    @Override
    public  boolean collisionCheck(Ball b){
        System.out.println("error");
        return true;
    }
    public void draw() {

    }

//    public void setTrail(Trail t) {
//
//    }



    public void setColor(Color c) {
        BallColor = c;
        Platform.runLater(() -> {
            BallShape.setFill(c);

        });
        savedcolor=ColorSwitcher.getcolormap.get(BallColor);

    }

    public Color getColor() {
        return BallColor;
    }
    public TranslateTransition getTranslateTransition() {
        return translateTransition;
    }

    public  double getRadius() {
        return radius;
    }
    public Shape getBallShape() {
        return BallShape;
    }
    public void setShape(Shape s) {
        BallShape = s;
    }
}

