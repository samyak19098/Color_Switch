package sample;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.application.Platform;


import java.util.*;
import java.text.SimpleDateFormat;

//  Platform.runLater(() -> {
// any code that involves changes in gui components needs to be enclosed within it
public class GameState {

    private Label scoretext;
    private long numStarsinGame;
    private Ball CurrentBall;
    private Date DateofSave;
    private static SimpleDateFormat Dateformatter;
    private  ArrayList<Obstacle>   sceneObstacles;
    private ArrayList<Star>   sceneStars;
    private ArrayList<ColorSwitcher>   sceneColorSwitcher;

    public GameState(){
        scoretext = new Label(""+numStarsinGame);
        scoretext.setAlignment(Pos.TOP_LEFT);
        scoretext.setTextFill(Color.WHITE);
//        System.out.println("x:"+scoretext.getLayoutX());
//        System.out.println("y:"+scoretext.getLayoutY());
//        System.out.println("hei:"+scoretext.getHeight());
//        System.out.println("wi:"+scoretext.getWidth());
        sceneObstacles=new ArrayList<Obstacle>();
        sceneStars=new ArrayList<Star>();
        sceneColorSwitcher=new ArrayList<ColorSwitcher>();
        CurrentBall=new Ball(Color.DEEPPINK,600.0f,600.0f,20.0f);
        RingObstacle ringObstacle = new RingObstacle("Ring", 6000, 0, 100,20, 600, 50, true);
        ringObstacle.draw();

        ringObstacle.WayOfMovement();
        ringObstacle.rotateRing();
        sceneObstacles.add(ringObstacle);
        sceneStars.add(new Star(600,50));
       sceneColorSwitcher.add(new ColorSwitcher(600,200,20));

    }



    public void incNumStarsinGame( ) {
        this.numStarsinGame +=1;
        Platform.runLater(() -> {
            scoretext.setText("" + numStarsinGame);
        });
    }
    public void shownOnScreen(Group g){
        Platform.runLater(() -> {//
            for (Obstacle o : sceneObstacles)
                o.shownOnScreen(g);
            for (Star o : sceneStars)
                o.shownOnScreen(g);
            for (ColorSwitcher o : sceneColorSwitcher)
                o.shownOnScreen(g);
            g.getChildren().add(CurrentBall.getBallShape());
            g.getChildren().add(scoretext );
        });

    }

    public void checkAllcollisions(Group g){
//        System.out.println("checking");
        for(Star s: sceneStars) {
            if (s.collisionCheck(CurrentBall)) {
                System.out.println("collided1!!");

                incNumStarsinGame();

//                s.polygon.setFill(Color.BLUE);
                Platform.runLater(() -> {
                            s.getPolygon().setVisible(false);

                            sceneStars.remove(s);
                            g.getChildren().remove(s.getPolygon());
                        });
                System.out.println("numStarsinGame:"+numStarsinGame);
                break;
            }
//            System.out.println("d1:");
        }
        for(ColorSwitcher s: sceneColorSwitcher) {
            if (s.collisionCheck(CurrentBall)) {
                System.out.println("collided2!!");
                Platform.runLater(() -> {
                s.specialChange(CurrentBall);
//                s.polygon.setFill(Color.BLUE);
                s.getCircle().setVisible(false);
                System.out.println("aa1:"+sceneColorSwitcher.remove(s));

                    System.out.println("bb1:"+g.getChildren().remove(s.getCircle()));
                });

                System.out.println("numStarsinGame:"+numStarsinGame);
                break;
            }
//            System.out.println("d1:");
        }
        for(Obstacle s: sceneObstacles) {
            if (s.collisionCheck(CurrentBall)) {
                System.out.println("collided3!!");
                System.out.println("12");
                //todo remove obstacles
                //s.polygon.setVisible(false);
//                sceneObstacles.remove(s);
//                g.getChildren().remove(s);
//                System.out.println("numStarsinGame:"+numStarsinGame);
                break;
            }
//            System.out.println("d1:");
        }
//        System.out.println("d2:");
    }

    public long getNumStarsinGame() {
        return numStarsinGame;
    }
    public void setNumStarsinGame(long numStarsinGame) {
        this.numStarsinGame = numStarsinGame;
    }
    public Ball getCurrentBall() {
        return CurrentBall;
    }

    public void setCurrentBall(Ball currentBall) {
        CurrentBall = currentBall;
    }

    public Date getDateofSave() {
        return DateofSave;
    }

    public void setDateofSave(Date dateofSave) {
        DateofSave = dateofSave;
    }

    public ArrayList<Obstacle> getSceneObstacles() {
        return sceneObstacles;
    }

    public void setSceneObstacles(ArrayList<Obstacle> sceneObstacles) {
        this.sceneObstacles = sceneObstacles;
    }

    public ArrayList<Star> getSceneStars() {
        return sceneStars;
    }

    public void setSceneStars(ArrayList<Star> sceneStars) {
        this.sceneStars = sceneStars;
    }

    public ArrayList<ColorSwitcher> getSceneColorSwitcher() {
        return sceneColorSwitcher;
    }

    public void setSceneColorSwitcher(ArrayList<ColorSwitcher> sceneColorSwitcher) {
        this.sceneColorSwitcher = sceneColorSwitcher;
    }

}
