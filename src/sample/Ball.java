package sample;
//https://gist.github.com/jewelsea/4569878
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class Ball extends GameObject {
    private  final int      KEYBOARD_MOVEMENT_DELTA = 100;
    private Color BallColor;
    private Shape BallShape;

//    private Trail BallTrail;
    private TranslateTransition translateTransition;

    public Shape getBallShape() {
        return BallShape;
    }

    public Ball(Color c, Shape s) {
        BallColor = c;
        BallShape = s;
//        BallTrail = t;
        translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(250));

        //Setting the node for the transition
        translateTransition.setNode(s);
//        Position x=new Position(s.)
    }

    public void setShape(Shape s) {
        BallShape = s;

    }

    public void MoveBall() {
        translateTransition.stop();
        translateTransition.setToY(Double.NaN);
        translateTransition.setByY(- KEYBOARD_MOVEMENT_DELTA);

        //Setting the cycle count for the transition
        translateTransition.setCycleCount(1);
        translateTransition.setDuration(Duration.millis(250));
        //Setting auto reverse value to false
        // translateTransition.setAutoReverse(false);
        System.out.println("asdasd");

        //Playing the animation
        translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent t ) {
                atend( );
            }
        });
        translateTransition.play();
    }
    public void atend(){
        double a=  BallShape.getTranslateX();
        System.out.println("circle.getTranslateX():"+BallShape.getTranslateX());
        System.out.println("circle.getTranslateY():"+BallShape.getTranslateY());
//        System.out.println("circle.getCenterY():"+circle.getCenterY());
//        System.out.println("circle.getCenterX():"+circle.getCenterX());
        translateTransition.setToY(0.0);
        translateTransition.setCycleCount(1);
        translateTransition.setDuration(Duration.millis(250));
        translateTransition.play();
        translateTransition.setOnFinished(null);

    }

    @Override
    public void showOnScreen(Group g) {

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
}