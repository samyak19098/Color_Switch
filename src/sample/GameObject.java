package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.shape.Shape;

public abstract class GameObject   {


    protected final int movedistance = 100;
    protected final int movtime=250;
    //    private Trail BallTrail;

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


        //Setting the node for the transition

    }



}
