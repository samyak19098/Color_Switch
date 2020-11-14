package sample;
import java.util.*;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public abstract class Obstacle extends GameObject{

    private String ObstacleType;
    private double ObstacleSpeed;
    private int Orientation;
    protected ArrayList<TranslateTransition> tlist;

    public void pauseRing(){
        System.out.println("");
    }
    Obstacle(String type, double speed, int orientation){
        this.ObstacleType = type;
        this.ObstacleSpeed = speed;
        this.Orientation = orientation;
        this.tlist=new ArrayList<TranslateTransition>();
        for(int i=0;i<4;i++){
            tlist.add(new TranslateTransition());
            tlist.get(i).setDuration(Duration.millis(250));
        }



    }
    protected abstract void movedown(Ball b);


    protected abstract void WayOfMovement();

    public String getObstacleType() {
        return this.ObstacleType;
    }

    public void setObstacleType(String obstacleType) {
        this.ObstacleType = obstacleType;
    }

    public double getObstacleSpeed() {
        return this.ObstacleSpeed;
    }

    public void setObstacleSpeed(double obstacleSpeed) {
        this.ObstacleSpeed = obstacleSpeed;
    }

    public int getOrientation() {
        return this.Orientation;
    }

    public void setOrientation(int orientation) {
        this.Orientation = orientation;
    }


}
