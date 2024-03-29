package sample;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.application.Platform;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;

//  Platform.runLater(() -> {
// any code that involves changes in gui components needs to be enclosed within it
public class GameState implements Serializable {


    private int n,r;
    private Obstaclefactory factory;
    private final int movtime = 250;
    private transient ArrayList<Circle> gameoverballs;
    private transient ArrayList<Timeline> gameovertimeline;
    private static  double screenwidth=1200;
    private static  double initialhColorSwitcher=270;
    private static  double initialhobstacle=50;
    private static  double screenheight=800;
    private static  double speed=6000;
    private transient Label scoretext;
    private long numStarsinGame;
    private   Ball CurrentBall;
    private Date DateofSave;
    private boolean removed;
    private transient AudioClip  mp_colorswitchercollect,mp_starcollect;
    private final String player_name;
    //collision flag :
    private boolean coll_flag;

//    private int xx;



    private transient Hand hand;



    private static SimpleDateFormat Dateformatter;
    private    ArrayList<Obstacle>   sceneObstacles;
    private   ArrayList<Star>   sceneStars;
    private   ArrayList<ColorSwitcher>   sceneColorSwitcher;



    private   transient  ArrayList<SuperColorSwitcher>   sceneSuperColorSwitcher;
    private int debug=1;



    private  transient Trail BallTrail;
    public GameState(){player_name="player1";}
    public GameState(Trail trail, String name){
        factory=new Obstaclefactory();
        mp_colorswitchercollect=new AudioClip( "file:colorswitchercollect.wav" );
        mp_colorswitchercollect.setCycleCount(1);mp_colorswitchercollect.setVolume(1);
        mp_starcollect=new AudioClip( "file:starcollect.wav" );
        mp_starcollect.setCycleCount(1);mp_starcollect.setVolume(1);
        player_name  = name;
        coll_flag = false;removed=false;
        n=10;
        r=400;
        load_attributes();
//        System.out.println("x:"+scoretext.getLayoutX());
//        System.out.println("y:"+scoretext.getLayoutY());
//        System.out.println("hei:"+scoretext.getHeight());
//        System.out.println("wi:"+scoretext.getWidth());
        sceneObstacles=new ArrayList<Obstacle>();
        sceneStars=new ArrayList<Star>();
        sceneColorSwitcher=new ArrayList<ColorSwitcher>();
        CurrentBall=new Ball(Color.DEEPPINK,600.0f,600.0f,20.0f,-1);
        setBallTrail(trail);
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
//                    System.out.println("len1:" + sceneObstacles.size());
                    //                System.out.println(""+s.getPosition().get_y());
                    Platform.runLater(() -> {
                        if (s.getObstacleType().equals("cross")) {
                            if(speed>1010)
                            speed -= 2000;//more difficulty
                        }
                        sceneObstacles.add(factory.createObstacle(  s.getObstacleType(),   speed,   grp,  screenwidth,  screenheight,  initialhColorSwitcher,  initialhobstacle,   sceneObstacles,   sceneColorSwitcher));




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
                CurrentBall.getBallShape().setVisible(false);
                gameovereffect(grp);
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
    public void setBallTrail(Trail trail ) {
        if(trail==null) {
            BallTrail=trail;
        }
        else if(trail instanceof Firetrail){
            BallTrail=new Firetrail(CurrentBall);
        }
        else if(trail instanceof Neontrail){
            BallTrail=new Neontrail(CurrentBall);
        }
        else if(trail instanceof Trail){
            BallTrail=new Trail(CurrentBall);
        }
    }
    public void decreaseNumStarsinGame(){
        this.numStarsinGame -= 2;
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
            if(BallTrail!=null)
            BallTrail.shownOnScreen(g);
            CurrentBall.shownOnScreen(g);
//            g.getChildren().add(CurrentBall.getBallShape());

            g.getChildren().add(scoretext );
            if(!removed)
            g.getChildren().add(hand.getPolygon() );
            for(int i=0;i<n;i++){
                g.getChildren().add(gameoverballs.get(i));
            }
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
                        mp_starcollect.stop();
                        mp_starcollect.play();
                        incNumStarsinGame();

        //                s.polygon.setFill(Color.BLUE);
                        Platform.runLater(() -> {


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
                            mp_colorswitchercollect.stop();
                            mp_colorswitchercollect.play();

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
        if(CurrentBall.getColor()==Color.WHITE)
            return ;
        for(Obstacle s: sceneObstacles) {
            if (s.collisionCheck(CurrentBall)) {
                System.out.println("collided3!!");
                System.out.println(debug);
                System.out.println(s.getClass());
                debug+=1;
                CurrentBall.getBallShape().setVisible(false);
                gameovereffect(g);

                //todo remove obstacles
//                Platform.runLater(() -> {
//                            mp_GameOver.stop();
//                            mp_GameOver.play();
//                        });
                throw new GameOverException("struck an obstacle");
            }
//            System.out.println("d1:");
        }


//        System.out.println("d2:");
    }

    private void gameovereffect(Group g) {
        Platform.runLater(() -> {
                    for (int i = 0; i < n; i++) {
                        gameoverballs.get(i).setVisible(true);
                        gameovertimeline.get(i).getKeyFrames().clear();
                        gameoverballs.get(i).setCenterY(CurrentBall.getPosition().get_y() + CurrentBall.getBallShape().getTranslateY());
                        gameoverballs.get(i).setCenterX(CurrentBall.getPosition().get_x() + CurrentBall.getBallShape().getTranslateX());
                        gameovertimeline.get(i).getKeyFrames().add(new KeyFrame(Duration.millis(8 * movtime), new KeyValue(gameoverballs.get(i).centerYProperty(), gameoverballs.get(i).getCenterY() + r * Math.sin(Math.toRadians((360 * i) / n)), Interpolator.LINEAR)));
                        gameovertimeline.get(i).getKeyFrames().add(new KeyFrame(Duration.millis(8 * movtime), new KeyValue(gameoverballs.get(i).centerXProperty(), gameoverballs.get(i).getCenterX() + r * Math.cos(Math.toRadians((360 * i) / n)), Interpolator.LINEAR)));
                        gameovertimeline.get(i).play();
                    }
                });
        //n=50;
        //angle 0  to 360
        // distance travelled=r;
        //tranlatory motion along rcos angle and rsin angle
        //radius= Ballcolor.radius/2
        //setVisible(true);
        //ColorSwitcher.map(random number)
    }

    public void load_attributes(){
        mp_colorswitchercollect=new AudioClip( "file:colorswitchercollect.wav" );
        mp_colorswitchercollect.setCycleCount(1);mp_colorswitchercollect.setVolume(1);
        mp_starcollect=new AudioClip( "file:starcollect.wav" );
        mp_starcollect.setCycleCount(1);mp_starcollect.setVolume(1);
        gameoverballs=new ArrayList<>();
        gameovertimeline=new ArrayList<>();
        for(int i=0;i<n;i++) {

            gameoverballs.add(new Circle(600.0f, 600.0f, 10.0f));gameoverballs.get(i).setVisible(false);
            gameoverballs.get(i).setFill(ColorSwitcher.map.get((int) (Math.random() * 4)));
            gameovertimeline.add(new Timeline());
            gameovertimeline.get(i).setCycleCount(1);
            //gameovertimeline.get(i).getKeyFrames().add(new KeyFrame(Duration.millis(4*movtime), new KeyValue((gameoverballs.get(i).radiusProperty(), r, Interpolator.LINEAR)));
            int finalI = i;
            gameovertimeline.get(i).setOnFinished((new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Platform.runLater(() -> {
                        gameoverballs.get(finalI).setVisible(false);
//                        gameoverballs.get(i).setCenterY(600);
//                        gameoverballs.get(i).setCenterX(600);

//                    System.out.println("Hello World2!"+t);
                    });
                }
            }));
        }
        scoretext = new Label("Score:"+numStarsinGame);
        scoretext.setAlignment(Pos.TOP_LEFT);
        scoretext.setTextFill(Color.WHITE);
        scoretext.setStyle(" -fx-font-weight: bold; -fx-font-size:20;");
    }


    public long getNumStarsinGame() {
        return numStarsinGame;
    }
    public void setNumStarsinGame(long s) {
        this.numStarsinGame = s;
    }
    public Ball getCurrentBall() {
        return CurrentBall;
    }

    public void setCurrentBall(Ball b) {
        CurrentBall = b;
    }

    public Date getDateofSave() {
        return DateofSave;
    }

    public void setDateofSave(Date d) {
        DateofSave = d;
    }

    public ArrayList<Obstacle> getSceneObstacles() {
        return sceneObstacles;
    }

    public void setSceneObstacles(ArrayList<Obstacle> c) {
        this.sceneObstacles = c;
    }

    public ArrayList<Star> getSceneStars() {
        return sceneStars;
    }

    public void setSceneStars(ArrayList<Star> c) {
        this.sceneStars = c;
    }

    public ArrayList<ColorSwitcher> getSceneColorSwitcher() {
        return sceneColorSwitcher;
    }

    public void setSceneColorSwitcher(ArrayList<ColorSwitcher> c) {
        this.sceneColorSwitcher = c;
    }
    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public String getPlayer_name() {
        return player_name;
    }
    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public boolean getRemoved() {
        return removed;
    }
    public ArrayList<SuperColorSwitcher> getSceneSuperColorSwitcher() {
        return sceneSuperColorSwitcher;
    }

    public void setSceneSuperColorSwitcher(ArrayList<SuperColorSwitcher> d) {
        this.sceneSuperColorSwitcher = d;
    }
    public static SimpleDateFormat getDateformatter() {
        return Dateformatter;
    }

    public static void setDateformatter(SimpleDateFormat d) {
        Dateformatter = d;
    }
    public void setcoll_flag(boolean s){
        coll_flag=s;
    }
    public Trail getBallTrail() {
        return BallTrail;
    }
}
