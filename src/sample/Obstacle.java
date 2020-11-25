package sample;
import java.io.Serializable;
import java.util.*;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.util.Duration;

public abstract class Obstacle extends GameObject implements Serializable {

    protected String ObstacleType;
    protected double ObstacleSpeed;
    protected int Orientation;
    protected transient ArrayList<TranslateTransition> tlist;
//    protected double saved_angle;


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
    public void removeself(Group grp){

    }
    public abstract void movedown(Ball b);


    protected abstract void save_attributes();
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


    public boolean outofBounds() {
        return false;
    }
    public static double adjust(double ang){
        while(!(0<=ang && ang <360)){
            if(ang>=360)
            ang-=360;
            else
                ang+=360;
        }
        return ang;


    }



}
