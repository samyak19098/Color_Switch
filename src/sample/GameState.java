package sample;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


import java.util.*;
import java.text.SimpleDateFormat;
public class GameState {
    public Ball CurrentBall;
    public Date DateofSave;
    public static SimpleDateFormat Dateformatter;
    public long numStarsinGame;
    public  ArrayList<Obstacle>   sceneObstacles;
    public ArrayList<Star>   sceneStars;
    public ArrayList<ColorSwitcher>   sceneColorSwitcher;
    public GameState(){
        sceneObstacles=new ArrayList<Obstacle>();
        sceneStars=new ArrayList<Star>();
        sceneColorSwitcher=new ArrayList<ColorSwitcher>();
        CurrentBall=new Ball(Color.PURPLE,600.0f,600.0f,20.0f);
        RingObstacle ringObstacle = new RingObstacle("Ring", 6000, 0, 100,20, 600, 50, true);
        ringObstacle.draw();
//      root.getChildren().add(ringObstacle.getQuarters().get(0));
        ringObstacle.WayOfMovement();
        ringObstacle.rotateRing();
        sceneObstacles.add(ringObstacle);

    }
    public void shownOnScreen(Group g){
        g.getChildren().add(CurrentBall.getBallShape());
        for(Obstacle o: sceneObstacles)
            o.shownOnScreen(g);
        for(Star o: sceneStars)
            o.shownOnScreen(g);
        for(ColorSwitcher o: sceneColorSwitcher)
            o.shownOnScreen(g);

    }

    public void debug(){
//        System.out.println("checking");
        if(sceneObstacles.get(0).collisionCheck(CurrentBall)) {
            System.out.println("collided!!");
        }//else
//        System.out.println("notcollided!!");
    }

}
