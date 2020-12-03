package sample;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import javax.xml.crypto.Data;

import static javafx.scene.media.AudioClip.INDEFINITE;
//lock : used to stop detection of arrow keys b/w time of start of splitting of ball and time of appearance of game over menu
public class GameMain extends TimerTask  implements Serializable {

    public long numStars;
    public int whichtrail;
    public Trail currentTrail;

    private Scene gm_scene;
    private Group root;
    private GameState CurrentGameState;
    public Stage GameMainStage;
    public Main AssociatedMain;
    public boolean lock=false;


    private boolean load;

    private HashMap<Integer,Achievement> GameAchievements;
    private Task task;

    private static double screenwidth = 1200;
    private static double screenheight = 800;
    private final int movedistance = 100;//distance moved in one move
    private final int movtime = 250;
    private Timer timer,trailtimer;

    //made as an indicator for run() method of thread
    boolean collided_flag;
    boolean pause_var;

    Database db = new Database();

    public GameMain(Group root, Main m){
        load=false;
        whichtrail=0;
        numStars=0;
        this.root = new Group();
        this.AssociatedMain = m;
        timer = new Timer();
        timer.schedule(this, 500, 100);
        GameAchievements=new  HashMap<>();
        for(int i=0;i<3;i++)
            GameAchievements.put(i,new StarAchievement(((i+1)*5)));

    }


    public void startGame(Stage primaryStage, String name_inp){
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
//                audio[0].play();
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();

        Rectangle hbox = new Rectangle(1600, screenheight);
        Image im = new Image("file:bg.jpg", false);
        hbox.setFill(new ImagePattern(im));
        root.getChildren().add(hbox);


        Button pause_button = new Button("PAUSE GAME");
        pause_button.setPrefSize(100, 50);
        pause_button.setLayoutX(20);
        pause_button.setLayoutY(50);
        pause_button.setWrapText(true);
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

        /* ------------------------------------------------------------------------------------------------
  //      SAVE GAME BUTTON ON GAMEPLAY SCREEN ISSUE

//        Button save_button = new Button("SAVE GAME");
//        save_button.setPrefSize(100, 50);
//        save_button.setLayoutX(20);
//        save_button.setLayoutY(120);
//        save_button.setWrapText(true);
//        root.getChildren().add(save_button);
//
//        EventHandler<ActionEvent> event_save_game = new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent e) {
//
////                InGameMenu igm = new InGameMenu();
//                try {
////                    igm.start(primaryStage);
////                    Pause();
//                    savegame();
////                    audio[0].stop();
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                System.out.println("SAVE BUTTON PRESSED");
//            }
//        };
//        save_button.setOnAction(event_save_game);
        ------------------------------------------------------------------------------------------------*/

        Scene scene = new Scene(root, screenwidth, screenheight);//, Color.BLACK);
        gm_scene = scene;

        //if not a loaded game i.e indeed a new game
        this.lock=false;
        if(!load){

            this.collided_flag = false;
            this.pause_var = false;
//            games_list.getItems().set(slot, name_string.get());
            GameState g = new GameState(currentTrail, name_inp);
            CurrentGameState=g;
        }
        CurrentGameState.shownOnScreen(root);
        trailtimer = new Timer();
        if(CurrentGameState.BallTrail!=null)
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

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("lock"+lock);
//                if(lock)
//                    return;
                switch (event.getCode()) {

                    case UP:


//                        System.out.println("up");
//                        Platform.runLater(() -> {
//                                    mp_ballup.stop();
//                                    mp_ballup.play();

//                                });
                        //g.debug();
                        if(pause_var == true){
                            continueGame();
                            pause_var = false;
                        }
                        if ((600.0f + CurrentGameState.getCurrentBall().getBallShape().getTranslateY() - movedistance) > (screenheight / 2))
                            CurrentGameState.getCurrentBall().MoveBall(root);
                        else {
                            CurrentGameState.getCurrentBall().getTranslateTransition().stop();
                            if(CurrentGameState.BallTrail!=null)
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
        if(load){
            continueGame();
        }
    }


    public void run() {
        if(CurrentGameState != null && this.collided_flag == false ){
            try {
                CurrentGameState.checkAllcollisions(root,AssociatedMain.getMainStage());
//                System.out.println("CHECKING ALL COLLISIONS");
            } catch (Exception e) {
                Platform.runLater(() -> {
                    System.out.println("IN RUN ");
                    Pause();
                    this.lock=true;
                    this.collided_flag = true;
                    this.pause_var = true;
    //                this.CurrentGameState.coll_flag = true;
                    this.CurrentGameState.getCurrentBall().setColor(Color.WHITE);
                    this.CurrentGameState.getCurrentBall().reposition();
    //                Platform.runLater(() -> {
                        ObstacleHitMenu obm = new ObstacleHitMenu();
                        obm.game_main = this;
                        obm.main_page_obj = this.AssociatedMain.getMain_page();
                    Timeline wait=new Timeline();
                    wait.getKeyFrames().add(   new KeyFrame(Duration.millis(2000), new   EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                CurrentGameState.getCurrentBall().getBallShape().setVisible(true);

                                obm.start(GameMainStage);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }

                    }
                    ));
                    wait.play();

                        e.printStackTrace();
                });

            }
        }

        if(CurrentGameState != null && this.collided_flag == false ) {
            try {
                CurrentGameState.RemoveObstacles(root, AssociatedMain.getMainStage());
            } catch (Exception e) {
                Platform.runLater(() -> {
                    System.out.println("IN RUN ");
    //                for(int i = 0 ; i < 100 ; i++) {
    //                    this.CurrentGameState.getCurrentBall().MoveBall(root);
    //                }
                    Pause();
                    this.CurrentGameState.getCurrentBall().setColor(Color.WHITE);
                    this.CurrentGameState.getCurrentBall().reposition();
                    this.lock=true;//
                    this.collided_flag = true;
                    this.pause_var = true;
                    this.getCurrentGameState().coll_flag = true;
    //                Platform.runLater(() -> {
                        ObstacleHitMenu obm = new ObstacleHitMenu();
                        obm.game_main = this;
                        obm.main_page_obj = this.AssociatedMain.getMain_page();
                    Timeline wait=new Timeline();
                    wait.getKeyFrames().add(   new KeyFrame(Duration.millis(2000), new   EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                CurrentGameState.getCurrentBall().getBallShape().setVisible(true);

                                obm.start(GameMainStage);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }

                    }
                    ));
                    wait.play();

                        e.printStackTrace();
                });

            }
        }//
        if(CurrentGameState != null && this.collided_flag == false ) {
            for (Map.Entry<Integer, Achievement> t : GameAchievements.entrySet()) {
                if (t.getValue().Requirement(numStars))
                    t.getValue().Unlock = true;

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
        if(CurrentGameState.BallTrail!=null)
        CurrentGameState.BallTrail.Pause();
        this.collided_flag = true;

    }
    public void continueGame(){//todo shift to InGameMenu
        this.collided_flag = false;

        task = new Task() {
            @Override
            protected Object call() throws Exception {
                int s = INDEFINITE;
                AudioClip audio = new AudioClip( "file:background.wav" );
                audio.setVolume(0.5f);
                audio.setCycleCount(s);
//                audio.play();
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
        if(CurrentGameState.BallTrail!=null)
        CurrentGameState.BallTrail.Resume();
    }

    public void savegame(int slot_num) throws IOException {

        for(Obstacle s : CurrentGameState.getSceneObstacles()){
            s.save_attributes();
        }
        for(Star s : CurrentGameState.getSceneStars()){
            s.save_star();
        }
        for(ColorSwitcher s : CurrentGameState.getSceneColorSwitcher()){
            s.save_color_switcher();
        }
        CurrentGameState.getCurrentBall().save_ball();

        db.serialize(CurrentGameState, slot_num);

    }

    public void loadgame(Stage primaryStage, int slot_num) throws IOException, ClassNotFoundException {
        this.CurrentGameState = db.deserialize(slot_num);
        for(Obstacle s : CurrentGameState.getSceneObstacles()){
            s.load_attributes();
        }
        for(Star s : CurrentGameState.getSceneStars()){
            s.load_attributes();
        }
        for(ColorSwitcher s : CurrentGameState.getSceneColorSwitcher()){
            s.load_attributes();
        }
        CurrentGameState.getCurrentBall().load_attributes();

            CurrentGameState.setHand(new  Hand(screenwidth/2,600+20+100));
        if(CurrentGameState.removed)
            removehand();
        CurrentGameState.load_attributes();
        load=true;
        startGame(primaryStage,CurrentGameState.getPlayer_name());


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
        CurrentGameState.removed=true;
    }
    public HashMap<Integer, Achievement> getGameAchievements() {
        return GameAchievements;
    }
    public Group getRoot() {
        return root;
    }

    public Scene getGm_scene() {
        return gm_scene;
    }
    public void setLoad(boolean load) {
        this.load=load;
    }

}
