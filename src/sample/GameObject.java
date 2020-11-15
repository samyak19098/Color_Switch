package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public abstract class GameObject   {
    protected  static   double screenwidth=1200;
    protected static  double screenheight=800;

    protected final int movedistance = 100;
    protected final int movtime=250;
    //    private Trail BallTrail;
    protected TranslateTransition translateTransition;
    protected Position position;
    public abstract void shownOnScreen(Group g);

    public abstract void draw();
    public abstract boolean collisionCheck(Ball b);
    public Position getPosition(){
        return this.position;
    }
    public void setPosition(Position p){
        this.position = p;
    }

    public GameObject(){
        translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(movtime));

        //Setting the node for the transition

    }

    public void Pause( ) {
        Platform.runLater(() -> {
            translateTransition.pause();
        });

    }

    public void Resume( ) {
        Platform.runLater(() -> {
            translateTransition.play();
        });

    }



}
