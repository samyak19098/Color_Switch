package sample;
//https://gist.github.com/jewelsea/4569878
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import javafx.scene.shape.Circle;

public class Ball extends GameObject {
    private final int movtime=250;//time duration of one move
    private final int movedistance = 100;//distance moved in one move
    private Color BallColor;
    private Shape BallShape;
    private double radius;


    public TranslateTransition getTranslateTransition() {
        return translateTransition;
    }

//    private Trail BallTrail;
    private TranslateTransition translateTransition;

    public Shape getBallShape() {
        return BallShape;
    }

    public Ball(Color c, Shape s) {

    }
    public Ball(Color c,double x,double y,double r) {
        BallColor = c;

        BallShape=new Circle(x,y,r,c);
//        BallTrail = t;
        translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(movtime));

        //Setting the node for the transition
        translateTransition.setNode(BallShape);
        position=new Position(x,y);
        radius=r;

    }
    public Ball(Color c, Shape s,double x,double y,double r) {


    }

    public void setShape(Shape s) {
        BallShape = s;

    }

    public void MoveBall() {
        translateTransition.stop();
        translateTransition.setToY(Double.NaN);
        translateTransition.setByY(- movedistance);

        //Setting the cycle count for the transition
        translateTransition.setCycleCount(1);
        translateTransition.setDuration(Duration.millis(movtime));
        //Setting auto reverse value to false
        // translateTransition.setAutoReverse(false);
//        System.out.println("move up");

        //Playing the animation
        translateTransition.setOnFinished(new EventHandler<ActionEvent>() {//todo: dont create eventhandler  everytime
            @Override public void handle(ActionEvent t ) {
                atend( );
            }
        });
        translateTransition.play();
    }
    public void atend(){
        double a=  BallShape.getTranslateX();
//        System.out.println("circle.getTranslateX():"+BallShape.getTranslateX());
//        System.out.println("circle.getTranslateY():"+BallShape.getTranslateY());
//        System.out.println("circle.getCenterY():"+circle.getCenterY());
//        System.out.println("circle.getCenterX():"+circle.getCenterX());
        translateTransition.setToY(0.0);
        translateTransition.setCycleCount(1);
        translateTransition.setDuration(Duration.millis(movtime));
        translateTransition.play();
        translateTransition.setOnFinished(null);

    }

    @Override
    public void shownOnScreen(Group g) {

            g.getChildren().add(BallShape);

    }

    public void draw() {

    }

//    public void setTrail(Trail t) {
//
//    }

    public Color getColor() {
        return BallColor;
    }

    public void setColor(Color c) {
        BallColor = c;
        BallShape.setFill(c);

    }
    @Override
    public  boolean collisionCheck(Ball b){
        System.out.println("error");
        return true;
    }
    public  double getRadius() {
        return radius;
    }


}