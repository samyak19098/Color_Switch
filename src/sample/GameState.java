package sample;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.application.Platform;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.File;
import java.util.*;
import java.text.SimpleDateFormat;

//  Platform.runLater(() -> {
// any code that involves changes in gui components needs to be enclosed within it
public class GameState {

    Media starcollect = new Media(new File("starcollect.wav").toURI().toString());
//    MediaPlayer mp_starcollect = new MediaPlayer(starcollect);
    Media colorswitchercollect = new Media(new File("colorswitchercollect.wav").toURI().toString());
//    MediaPlayer mp_colorswitchercollect = new MediaPlayer(colorswitchercollect);
    Media GameOver = new Media(new File("GameOver.wav").toURI().toString());
//    MediaPlayer mp_GameOver = new MediaPlayer(GameOver);

    private static  double screenwidth=1200;
    protected static  double screenheight=800;

    private Label scoretext;
    private long numStarsinGame;
    private Ball CurrentBall;
    private Date DateofSave;

    public Hand getHand() {
        return hand;
    }

    private Hand hand;
    private static SimpleDateFormat Dateformatter;
    private  ArrayList<Obstacle>   sceneObstacles;
    private ArrayList<Star>   sceneStars;
    private ArrayList<ColorSwitcher>   sceneColorSwitcher;
    private int debug=1;
    public GameState(){
        scoretext = new Label("Score:"+numStarsinGame);
        scoretext.setAlignment(Pos.TOP_LEFT);
        scoretext.setTextFill(Color.WHITE);
        scoretext.setStyle(" -fx-font-weight: bold; -fx-font-size:20;");
//        System.out.println("x:"+scoretext.getLayoutX());
//        System.out.println("y:"+scoretext.getLayoutY());
//        System.out.println("hei:"+scoretext.getHeight());
//        System.out.println("wi:"+scoretext.getWidth());
        sceneObstacles=new ArrayList<Obstacle>();
        sceneStars=new ArrayList<Star>();
        sceneColorSwitcher=new ArrayList<ColorSwitcher>();
        CurrentBall=new Ball(Color.DEEPPINK,600.0f,600.0f,20.0f);
        hand=new Hand(600,600+20+100);
        RingObstacle ringObstacle = new RingObstacle("Ring", 6000, 0, 100,20, 600, 50, true);
        ringObstacle.draw();

        ringObstacle.WayOfMovement();
        ringObstacle.rotateRing();
        sceneObstacles.add(ringObstacle);
        RingObstacle ringObstacle2 = new RingObstacle("Ring", 6000, 0, 100,20, 600, 50-screenheight, true);
        ringObstacle2.draw();

        ringObstacle2.WayOfMovement();
        ringObstacle2.rotateRing();
        sceneObstacles.add(ringObstacle2);
        ConcentricObstacle concentricObstacle= new ConcentricObstacle("Concentric",6000,0,100, 20, screenwidth*0.75,screenheight/4 ,true,45);
        concentricObstacle.draw();
        concentricObstacle.WayOfMovement();
        concentricObstacle.rotateConcentric();


        SquareObstacle squareObstacle = new SquareObstacle("Square",6000,0,screenwidth/4,screenheight/4 ,100 ,20 ,true);
        squareObstacle.draw();
        squareObstacle.WayOfMovement();
        squareObstacle.rotateSquare();
        //        String type, double speed, int orientation, double centre_x, double centre_y, double side, double thickness, boolean
        TangentialObstacle tangentialObstacle = new TangentialObstacle("Tangential",6000,0,100, 20,screenwidth/4 ,screenheight*0.75 ,true,45,235);
        tangentialObstacle.draw();
        tangentialObstacle.WayOfMovement();
        tangentialObstacle.rotateTangential();
        sceneObstacles.add(concentricObstacle);
        sceneObstacles.add(squareObstacle);
        sceneObstacles.add(tangentialObstacle);
        sceneStars.add(new Star(600,50));
        sceneStars.add(new Star(600,50-screenheight));

        sceneColorSwitcher.add(new ColorSwitcher(600,250,20));
        sceneColorSwitcher.add(new ColorSwitcher(600,250-screenheight,20));



    }


    public void RemoveObstacles(Group grp, Stage stage){
// relocates/ respawns objects to original position
//        System.out.println("len1:"+sceneObstacles.size());
        for(Obstacle s: sceneObstacles){

            if(s.outofBounds()){
//                System.out.println(""+s.getPosition().get_y());
//                //Platform.runLater(() -> {
//                System.out.println("len2:"+sceneObstacles.size());
//                System.out.println("outofbounds"+debug);
//                debug+=1;
//                RingObstacle ringObstacle2 = new RingObstacle("Ring", 6000, 0, 100,20, 600, 50-screenheight, true);
//                ringObstacle2.draw();
//
//                ringObstacle2.WayOfMovement();
//                ringObstacle2.rotateRing();
//                sceneObstacles.add(ringObstacle2);
//                ringObstacle2.shownOnScreen(grp);
//
//                Star a1=new Star(600,50-screenheight);
//                a1.shownOnScreen(grp);
//                sceneStars.add(a1);
//                ColorSwitcher b1=new ColorSwitcher(600,200-screenheight,20);
//                b1.shownOnScreen(grp);
//                sceneColorSwitcher.add(b1);


                s.removeself(grp);
                //});
                sceneObstacles.remove(0);//hopefully 1st obstacle needs to be removed
                break;
            }
//            System.out.println("aa11");
        }
        if(    CurrentBall.outofBounds()) {
            Platform.runLater(() -> {
//                mp_GameOver.stop();
//                mp_GameOver.play();
//            });
                ObstacleHitMenu obm = new ObstacleHitMenu();
                try {
                    obm.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                throw new GameOverException("ball gone out");
            });
        }
    }


    public void incNumStarsinGame( ) {
        this.numStarsinGame +=1;
        Platform.runLater(() -> {
            scoretext.setText("Score:" + numStarsinGame);
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
            g.getChildren().add(hand.getPolygon() );
        });

    }

    public void checkAllcollisions(Group g, Stage stage) throws Exception{
//        System.out.println("checking");
        for(Star s: sceneStars) {
            if (s.collisionCheck(CurrentBall)) {
//                System.out.println("collided1!!");



                incNumStarsinGame();

//                s.polygon.setFill(Color.BLUE);
                Platform.runLater(() -> {
//                    mp_starcollect.stop();
//                    mp_starcollect.play();
                            s.getPolygon().setVisible(false);

                            sceneStars.remove(s);
                            g.getChildren().remove(s.getPolygon());
                        });
//                System.out.println("numStarsinGame:"+numStarsinGame);
                break;
            }
//            System.out.println("d1:");
        }
        for(ColorSwitcher s: sceneColorSwitcher) {
            if (s.collisionCheck(CurrentBall)) {
//                System.out.println("collided2!!");
                Platform.runLater(() -> {
//                    mp_colorswitchercollect.stop();
//                    mp_colorswitchercollect.play();
                s.specialChange(CurrentBall);
//                s.polygon.setFill(Color.BLUE);
                s.getCircle().setVisible(false);
                    sceneColorSwitcher.remove(s);
                    g.getChildren().remove(s.getCircle());
//                System.out.println("aa1:"+);

//                    System.out.println("bb1:"+);
                });

//                System.out.println("numStarsinGame:"+numStarsinGame);
                break;
            }
//            System.out.println("d1:");
        }
        for(Obstacle s: sceneObstacles) {
            if (s.collisionCheck(CurrentBall)) {
                System.out.println("collided3!!");
                System.out.println("12");
                //todo remove obstacles
//                Platform.runLater(() -> {
//                            mp_GameOver.stop();
//                            mp_GameOver.play();
//                        });
                Platform.runLater(() -> {
//                            mp_GameOver.stop();
//                            mp_GameOver.play();
//                        });
//                throw new GameOverException("struck an obstacle");

                    ObstacleHitMenu obm = new ObstacleHitMenu();
//                Pause();
                    try {
                        obm.start(stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                throw new GameOverException("struck an obstacle");
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
