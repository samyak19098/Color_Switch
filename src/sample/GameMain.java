package sample;
import java.util.*;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import static javafx.scene.media.AudioClip.INDEFINITE;

public class GameMain extends TimerTask {

    public Group getRoot() {
        return root;
    }

    public Scene getGm_scene() {
        return gm_scene;
    }

    private Scene gm_scene;
    private Group root;
    private GameState CurrentGameState;
    public Stage GameMainStage;
    public Main AssociatedMain;

    private Task task;

    private static double screenwidth = 1200;
    private static double screenheight = 800;
    private final int movedistance = 100;//distance moved in one move
    private final int movtime = 250;
    private Timer timer,trailtimer;

    //made as an indicator for run() method of thread
    boolean collided_flag;

    public GameMain(Group root, Main m){
        this.root = new Group();
        this.AssociatedMain = m;
        timer = new Timer();
        timer.schedule(this, 500, 100);
    }


    public void startGame(Stage primaryStage){

        this.collided_flag = false;
        Group grp = new Group();
        this.root = grp;
        this.GameMainStage = primaryStage;
        InGameMenu igm = new InGameMenu();
        igm.main_page_obj = this.AssociatedMain.getMain_page();
        igm.game_main = this;


        final AudioClip[] audio = new AudioClip[1];
        task = new Task() {
            @Override
            protected Object call() throws Exception {
                int s = INDEFINITE;
                audio[0] = new AudioClip( "file:background.wav" );
                audio[0].setVolume(0.5f);
                audio[0].setCycleCount(s);
                audio[0].play();
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();
        //======

//        Group root = new Group();
        // set background
        Rectangle hbox = new Rectangle(screenwidth, screenheight);
        Image im = new Image("file:bg.jpg", false);
        hbox.setFill(new ImagePattern(im));
        root.getChildren().add(hbox);


//        GameMain gm = new GameMain(root);
//        Star s=new Star(600,300);
        GameState g = new GameState();
        this.setCurrentGameState(g);


        Button pause_button = new Button("PAUSE GAME");
        pause_button.setPrefSize(100, 50);
        pause_button.setLayoutX(20);
        pause_button.setLayoutY(50);
        root.getChildren().add(pause_button);
        EventHandler<ActionEvent> event_pause_game = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

//                InGameMenu igm = new InGameMenu();
                try {
                    igm.start(primaryStage);
                    Pause();
                    audio[0].stop();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println("PAUSE BUTTON PRESSED");
            }
        };
        pause_button.setOnAction(event_pause_game);


        Scene scene = new Scene(root, screenwidth, screenheight);//, Color.BLACK);
        gm_scene = scene;
        g.shownOnScreen(root);

        trailtimer = new Timer();


        trailtimer.schedule(this.getCurrentGameState().BallTrail, 500, 200);

        //timer checks collions after every 1s
        // to stop the timer ,use down arrow key
//        moveBallOnKeyPress(scene, gm ,timer,trailtimer);//,mp_ballup,mp_button);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Color Switch");
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        primaryStage.show();


//    private void moveBallOnKeyPress(Scene scene,  GameMain gm,Timer timer,Timer trailtimer){//, MediaPlayer mp_ballup,MediaPlayer mp_button) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
//                        System.out.println("up");
//                        Platform.runLater(() -> {
//                                    mp_ballup.stop();
//                                    mp_ballup.play();

//                                });
                        //g.debug();

                        if ((600.0f + CurrentGameState.getCurrentBall().getBallShape().getTranslateY() - movedistance) > (screenheight / 2))
                            CurrentGameState.getCurrentBall().MoveBall(root);
                        else {
                            CurrentGameState.getCurrentBall().getTranslateTransition().stop();
                            CurrentGameState.BallTrail.atend = true;
//                            for(int i=0;i< (gm.getCurrentGameState().getCurrentBall().n-1);i++){
//                                gm.getCurrentGameState().getCurrentBall().t2.get(i).stop();
//
//                            }
                            //g.CurrentBall.translateTransition.setByY(0);
                            //g.CurrentBall.translateTransition.setCycleCount(1);
                            //g.CurrentBall.translateTransition.setDuration(Duration.millis(0));
                            boolean fl = false;//if no obstacle is on screen, then to make  ball go down this flag  is used
//                            System.out.println("movedown1");
                            for (Obstacle o : CurrentGameState.getSceneObstacles()) {
                                fl = true;
//                                System.out.println("movedown");
                                o.movedown(CurrentGameState.getCurrentBall());
                            }
                            for (Star o : CurrentGameState.getSceneStars()) {
                                fl = true;
                                o.movedown(CurrentGameState.getCurrentBall(), CurrentGameState.BallTrail);
                            }
                            for (ColorSwitcher o : CurrentGameState.getSceneColorSwitcher()) {
                                fl = true;
                                o.movedown(CurrentGameState.getCurrentBall());
                            }
                            if (!fl)
                                CurrentGameState.getCurrentBall().atend();
                            //for (ColorSwitcher o : g.sceneColorSwitcher)
                            //  o.movedown(g.CurrentBall.getBallShape(),g.CurrentBall.translateTransition );
                            //g.CurrentBall.translateTransition.play();
                        }
                        removehand();
                        break;

                    case DOWN://todo move to exit from game button button
                        System.out.println("downkey");
                        timer.cancel();
                        timer.purge();
                        break;
                    case LEFT:
                        System.out.println("leftkey");
//                        s.getPolygon().setVisible(false);
//                        gm.getCurrentGameState().getSceneColorSwitcher().remove(s);
//                        gm.getGrp().getChildren().remove(s);
//                        gm.getCurrentGameState().getSceneStars().remove(s);
//                        primaryStage.show();
                        break;
                    case RIGHT:
                        System.out.println("rightkey");
//                        scoretext.setText(""+1);

//                          primaryStage.show();
                        break;
                    case W:

                        System.out.println("circle.getTranslateX():" + CurrentGameState.getCurrentBall().getBallShape().getTranslateX());
                        System.out.println("circle.getTranslateY():" + CurrentGameState.getCurrentBall().getBallShape().getTranslateY());
                        CurrentGameState.getCurrentBall().getTranslateTransition().stop();
                        CurrentGameState.getCurrentBall().getTranslateTransition().setToY(Double.NaN);
                        CurrentGameState.getCurrentBall().getTranslateTransition().setToX(Double.NaN);
                        CurrentGameState.getCurrentBall().getTranslateTransition().setByX(0);
                        CurrentGameState.getCurrentBall().getTranslateTransition().setByY(-movedistance);

                        //Setting the cycle count for the transition
                        CurrentGameState.getCurrentBall().getTranslateTransition().setCycleCount(1);
                        CurrentGameState.getCurrentBall().getTranslateTransition().setDuration(Duration.millis(movtime));
                        CurrentGameState.getCurrentBall().getTranslateTransition().setOnFinished(null);
                        CurrentGameState.getCurrentBall().getTranslateTransition().play();
                        break;
                    case A:
                        System.out.println("circle.getTranslateX():" + CurrentGameState.getCurrentBall().getBallShape().getTranslateX());
                        System.out.println("circle.getTranslateY():" + CurrentGameState.getCurrentBall().getBallShape().getTranslateY());
                        CurrentGameState.getCurrentBall().getTranslateTransition().stop();
                        CurrentGameState.getCurrentBall().getTranslateTransition().setToX(Double.NaN);
                        CurrentGameState.getCurrentBall().getTranslateTransition().setToY(Double.NaN);
                        CurrentGameState.getCurrentBall().getTranslateTransition().setByY(0);
                        CurrentGameState.getCurrentBall().getTranslateTransition().setByX(-movedistance);

                        //Setting the cycle count for the transition
                        CurrentGameState.getCurrentBall().getTranslateTransition().setCycleCount(1);
                        CurrentGameState.getCurrentBall().getTranslateTransition().setDuration(Duration.millis(movtime));
                        CurrentGameState.getCurrentBall().getTranslateTransition().setOnFinished(null);
                        CurrentGameState.getCurrentBall().getTranslateTransition().play();
                        break;
                    case S:
                        System.out.println("circle.getTranslateX():" + CurrentGameState.getCurrentBall().getBallShape().getTranslateX());
                        System.out.println("circle.getTranslateY():" + CurrentGameState.getCurrentBall().getBallShape().getTranslateY());
                        CurrentGameState.getCurrentBall().getTranslateTransition().stop();
                        CurrentGameState.getCurrentBall().getTranslateTransition().setToY(Double.NaN);
                        CurrentGameState.getCurrentBall().getTranslateTransition().setToX(Double.NaN);
                        CurrentGameState.getCurrentBall().getTranslateTransition().setByX(0);
                        CurrentGameState.getCurrentBall().getTranslateTransition().setByY(movedistance);

                        //Setting the cycle count for the transition
                        CurrentGameState.getCurrentBall().getTranslateTransition().setCycleCount(1);
                        CurrentGameState.getCurrentBall().getTranslateTransition().setDuration(Duration.millis(movtime));
                        CurrentGameState.getCurrentBall().getTranslateTransition().setOnFinished(null);
                        CurrentGameState.getCurrentBall().getTranslateTransition().play();
                        break;
                    case D:
                        System.out.println("circle.getTranslateX():" + CurrentGameState.getCurrentBall().getBallShape().getTranslateX());
                        System.out.println("circle.getTranslateY():" + CurrentGameState.getCurrentBall().getBallShape().getTranslateY());
                        CurrentGameState.getCurrentBall().getTranslateTransition().stop();
                        CurrentGameState.getCurrentBall().getTranslateTransition().setToX(Double.NaN);
                        CurrentGameState.getCurrentBall().getTranslateTransition().setToY(Double.NaN);
                        CurrentGameState.getCurrentBall().getTranslateTransition().setByY(0);
                        CurrentGameState.getCurrentBall().getTranslateTransition().setByX(movedistance);

                        //Setting the cycle count for the transition
                        CurrentGameState.getCurrentBall().getTranslateTransition().setCycleCount(1);
                        CurrentGameState.getCurrentBall().getTranslateTransition().setDuration(Duration.millis(movtime));
                        CurrentGameState.getCurrentBall().getTranslateTransition().setOnFinished(null);
                        CurrentGameState.getCurrentBall().getTranslateTransition().play();
                        break;
                    case P://pause
//                        Platform.runLater(() -> {
//                                    mp_button.stop();
//                                    mp_button.play();
//                                });
//                        gm.getCurrentGameState().getSceneObstacles().get(1).Pause();
//                        timer.cancel();
//                        trailtimer.cancel();
                        Pause();
                        break;


                    case R://continue
//                        Platform.runLater(() -> {
//                            mp_button.stop();
//                            mp_button.play();
//                        });
//                        gm.getCurrentGameState().getSceneObstacles().get(1).Resume();
//                        timer = new Timer();
//                        trailtimer = new Timer();
//                        timer.schedule(gm, 500, 100);
//                        trailtimer.schedule(gm.getCurrentGameState().BallTrail, 500, 150);
                        continueGame();
                        break;
                    default:
                        System.out.println("defaultkey");
                        break;
                }
            }
        });
    }


    public void run( ) {
        if(CurrentGameState != null && this.collided_flag == false ){
            try {
                CurrentGameState.checkAllcollisions(root,AssociatedMain.getMainStage());
            } catch (Exception e) {
                System.out.println("IN RUN ");
                Pause();
                this.collided_flag = true;
                this.getCurrentGameState().coll_flag = true;
                this.getCurrentGameState().getCurrentBall().setColor(Color.WHITE);
                Platform.runLater(() -> {
                    ObstacleHitMenu obm = new ObstacleHitMenu();
                    obm.game_main = this;
                    obm.main_page_obj = this.AssociatedMain.getMain_page();

                    try {
                        obm.start(GameMainStage);
                    } catch (Exception e1) {
                        e.printStackTrace();
                    }
                });
                e.printStackTrace();
            }
            try {
                CurrentGameState.RemoveObstacles(root, AssociatedMain.getMainStage());
            } catch (Exception e) {
                System.out.println("IN RUN ");
                Pause();
                this.collided_flag = true;
                this.getCurrentGameState().coll_flag = true;
                Platform.runLater(() -> {
                    ObstacleHitMenu obm = new ObstacleHitMenu();
                    obm.game_main = this;
                    obm.main_page_obj = this.AssociatedMain.getMain_page();
                    try {
                        obm.start(GameMainStage);
                    }
                    catch (Exception e1) {
                        e.printStackTrace();
                    }
                });
                e.printStackTrace();
            }
        }
//        CurrentGameState.AddObjects(grp);
        //System.out.println("Timer ran ");
    }

    public void Pause(){
        System.out.println("pausing!!");
        for(Star s: CurrentGameState.getSceneStars()) {
            s.Pause();
        }
        for(ColorSwitcher s: CurrentGameState.getSceneColorSwitcher()) {
            s.Pause();
        }
        for(Obstacle s: CurrentGameState.getSceneObstacles()) {
            s.Pause();
        }
        CurrentGameState.getCurrentBall().Pause();
        CurrentGameState.BallTrail.Pause();

    }
    public void continueGame(){//todo shift to InGameMenu


        task = new Task() {
            @Override
            protected Object call() throws Exception {
                int s = INDEFINITE;
                AudioClip audio = new AudioClip( "file:background.wav" );
                audio.setVolume(0.5f);
                audio.setCycleCount(s);
                audio.play();
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();

        System.out.println("continuing !!");
        for(Star s: CurrentGameState.getSceneStars()) {
            s.Resume();
        }
        for(ColorSwitcher s: CurrentGameState.getSceneColorSwitcher()) {
            s.Resume();
        }
        for(Obstacle s: CurrentGameState.getSceneObstacles()) {
            s.Resume();
        }
        CurrentGameState.getCurrentBall().Resume();
        CurrentGameState.BallTrail.Resume();
    }
    public Group getGrp() {
        return root;
    }
    public GameState getCurrentGameState() {
        return CurrentGameState;
    }
    public void setCurrentGameState(GameState currentGameState) {
        CurrentGameState = currentGameState;
    }

    public void removehand() {
        CurrentGameState.getHand().removeself(root);
    }
}
