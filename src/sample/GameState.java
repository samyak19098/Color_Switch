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
    private static  double initialhColorSwitcher=230;
    private static  double initialhobstacle=50;
    private static  double screenheight=800;
    private static  double speed=6000;
    private Label scoretext;
    private long numStarsinGame;
    private Ball CurrentBall;
    private Date DateofSave;

    //collision flag :
    public boolean coll_flag;

    public Hand getHand() {
        return hand;
    }

    private Hand hand;
    private static SimpleDateFormat Dateformatter;
    private  ArrayList<Obstacle>   sceneObstacles;
    private ArrayList<Star>   sceneStars;
    private ArrayList<ColorSwitcher>   sceneColorSwitcher;
    private int debug=1;
    public Trail BallTrail;
    public GameState(){
        coll_flag = false;
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
        CurrentBall=new Ball(Color.DEEPPINK,600.0f,600.0f,20.0f,-1);
        BallTrail=new Neontrail(CurrentBall);
        hand=new Hand(screenwidth/2,600+20+100);
        RingObstacle ringObstacle = new RingObstacle("Ring", speed, 0, 100,20, screenwidth/2, initialhobstacle, true);
        ringObstacle.draw();
        ringObstacle.WayOfMovement();
        ringObstacle.rotateRing();//ringObstacle.Pause();
        sceneObstacles.add(ringObstacle);

        SquareObstacle squareObstacle = new SquareObstacle("Square",speed,0,screenwidth/2,initialhobstacle-screenheight  ,175 ,20 ,true);
        squareObstacle.draw();
        squareObstacle.WayOfMovement();
        squareObstacle.rotateSquare();//squareObstacle.Pause();
        sceneObstacles.add(squareObstacle);

        for(int i=0;i<2;i++){
            sceneStars.add(new Star(screenwidth/2,initialhobstacle-(i*screenheight)));
            sceneColorSwitcher.add(new ColorSwitcher(screenwidth/2,initialhColorSwitcher-(i*screenheight),20));

        }

    }


    public void RemoveObstacles(Group grp, Stage stage){
// relocates/ respawns objects to original position
//        if(coll_flag == false) {
            for (Obstacle s : sceneObstacles) {

                if (s.outofBounds()) {
                    System.out.println("len1:" + sceneObstacles.size());
                    //                System.out.println(""+s.getPosition().get_y());
                    Platform.runLater(() -> {
                        if (s.getObstacleType().equals("Ring")) {

                            ConcentricObstacle concentricObstacle = new ConcentricObstacle("Concentric", speed, 0, 100, 20, screenwidth / 2, initialhobstacle - screenheight, true, 45);
                            concentricObstacle.draw();
                            concentricObstacle.WayOfMovement();
                            concentricObstacle.rotateConcentric();
                            concentricObstacle.shownOnScreen(grp);
                            sceneObstacles.add(concentricObstacle);

                        } else if (s.getObstacleType().equals("Square")) {
                            TangentialObstacle tangentialObstacle = new TangentialObstacle("Tangential", speed, 0, 170, 20, screenwidth / 2, initialhobstacle - screenheight, true, 45, 225);
                            tangentialObstacle.draw();
                            tangentialObstacle.WayOfMovement();
                            tangentialObstacle.rotateTangential();
                            tangentialObstacle.shownOnScreen(grp);//tangentialObstacle.Pause();//tangentialObstacle.Resume();
                            sceneObstacles.add(tangentialObstacle);

                        } else if (s.getObstacleType().equals("Concentric")) {
                            speed -= 5;//more difficulty
                            RingObstacle ringObstacle = new RingObstacle("Ring", speed, 0, 100, 20, screenwidth / 2, initialhobstacle - screenheight, true);
                            ringObstacle.draw();
                            ringObstacle.WayOfMovement();
                            ringObstacle.rotateRing();
                            ringObstacle.shownOnScreen(grp);
                            sceneObstacles.add(ringObstacle);
                        } else if (s.getObstacleType().equals("Tangential")) {

                            SquareObstacle squareObstacle = new SquareObstacle("Square", speed, 0, screenwidth / 2, initialhobstacle - screenheight, 175, 20, true);
                            squareObstacle.draw();
                            squareObstacle.WayOfMovement();
                            squareObstacle.rotateSquare();
                            squareObstacle.shownOnScreen(grp);
                            sceneObstacles.add(squareObstacle);

                        } else if (s.getObstacleType().equals("Cross")) {


                        }
                        Star newstar = new Star(screenwidth / 2, initialhobstacle - screenheight);
                        newstar.shownOnScreen(grp);
                        sceneStars.add(newstar);
                        ColorSwitcher newcolorSwitcher = new ColorSwitcher(screenwidth / 2, initialhColorSwitcher - screenheight, 20);
                        newcolorSwitcher.shownOnScreen(grp);

                        sceneColorSwitcher.add(newcolorSwitcher);
                        s.removeself(grp);
                        sceneObstacles.remove(s);
                    });
                    //                sceneObstacles.remove(0);//hopefully 1st obstacle needs to be removed
                    break;
                }
                //            System.out.println("aa11");
            }
            if (CurrentBall.outofBounds()) {
                //            Platform.runLater(() -> {
                //                mp_GameOver.stop();
                //                mp_GameOver.play();
                //            });
                //                ObstacleHitMenu obm = new ObstacleHitMenu();
                //                try {
                //                    obm.start(stage);
                //                } catch (Exception e) {
                //                    e.printStackTrace();
                //                }
                //            });
                throw new GameOverException("ball gone out");
            }
        }
//    }


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
            BallTrail.shownOnScreen(g);
            CurrentBall.shownOnScreen(g);
//            g.getChildren().add(CurrentBall.getBallShape());

            g.getChildren().add(scoretext );
            g.getChildren().add(hand.getPolygon() );
        });

    }

    public void checkAllcollisions(Group g, Stage stage){
//        System.out.println("1:"+sceneStars.size());
//        System.out.println("2:"+sceneColorSwitcher.size());
//        System.out.println("3:"+sceneObstacles.size());

//        if(coll_flag == ){
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
        //                    ObstacleHitMenu obm = new ObstacleHitMenu();
        //                    try {
        //                        obm.start(stage);
        //                    } catch (Exception e) {
        //                        e.printStackTrace();
        //                    }
        //                });

//                        s.removeself(g);
//                        sceneObstacles.remove(s);
                        throw new GameOverException("struck an obstacle");
                    }
            }
//            System.out.println("d1:");
//        }
    }


//        System.out.println("d2:");


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
