package sample;
import java.util.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class GameMain extends TimerTask {

    private Group grp;
    private GameState CurrentGameState;
    private Stage gameplay_stage;

    private static  double screenwidth=1200;
    private static double screenheight=800;
    private final int movedistance = 100;//distance moved in one move
    private final int movtime=250;

//    public Main getAssociatedMain() {
//        return associatedMain;
//    }
//
//    public void setAssociatedMain(Main associatedMain) {
//        this.associatedMain = associatedMain;
//    }

//    private Main associatedMain;

    public GameMain(Group grp){
        this.grp=grp;

    }

    public void run( ) {
        try {
            CurrentGameState.checkAllcollisions(grp, gameplay_stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CurrentGameState.RemoveObstacles(grp, gameplay_stage);
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

    public void startGame(Stage primaryStage){
//        Group root = new Group();
        // set background
        Rectangle hbox=new Rectangle(screenwidth,screenheight);
        Image im = new Image("file:bg.jpg",false);
        hbox.setFill(new ImagePattern(im));
        grp.getChildren().add(hbox);

//        GameMain gm=new GameMain(root);
//        Star s=new Star(600,300);
        this.setGameplay_stage(primaryStage);
        GameState g =new GameState();
//        this.CurrentGameState = g;
        this.setCurrentGameState(g);

        Button pause_button = new Button("PAUSE GAME");
        pause_button.setWrapText(true);
        pause_button.setPrefSize(100,50);
        pause_button.setLayoutX(20);
        pause_button.setLayoutY(50);
        grp.getChildren().add(pause_button);
        EventHandler<ActionEvent> event_pause_game = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("PAUSE BUTTON PRESSED");
                show_pause_screen(primaryStage);
            }
        };
        pause_button.setOnAction(event_pause_game);

        Scene scene = new Scene(grp,screenwidth,screenheight);//, Color.BLACK);

        g.shownOnScreen(grp);
        Timer timer = new Timer();

        timer.schedule(this , 500, 100);

        //timer checks collions after every 1s
        // to stop the timer ,use down arrow key
        moveBallOnKeyPress(scene, this ,timer);//,mp_ballup,mp_button);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Color Switch");
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public  void handle(WindowEvent t){
                Platform.exit();
                System.exit(0);
            }
                                       });
        primaryStage.show();
    }

    private void moveBallOnKeyPress(Scene scene, GameMain gm, Timer timer){//, MediaPlayer mp_ballup,MediaPlayer mp_button) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED,new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        System.out.println("up");
//                        Platform.runLater(() -> {
//                                    mp_ballup.stop();
//                                    mp_ballup.play();

//                                });
                        //g.debug();
                        if((600.0f+ gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateY()-movedistance)> (screenheight/2))
                            gm.getCurrentGameState().getCurrentBall().MoveBall();
                        else {
                            gm.getCurrentGameState().getCurrentBall().getTranslateTransition().stop();
                            //g.CurrentBall.translateTransition.setByY(0);
                            //g.CurrentBall.translateTransition.setCycleCount(1);
                            //g.CurrentBall.translateTransition.setDuration(Duration.millis(0));
                            boolean fl=false;//if no obstacle is on screen, then to make  ball go down this flag  is used
                            System.out.println("movedown1");
                            for (Obstacle o : gm.getCurrentGameState().getSceneObstacles()) {
                                fl=true;
                                System.out.println("movedown");
                                o.movedown(gm.getCurrentGameState().getCurrentBall());
                            }
                            for(Star o: gm.getCurrentGameState().getSceneStars()) {
                                fl=true;
                                o.movedown(gm.getCurrentGameState().getCurrentBall());
                            }
                            for(ColorSwitcher o: gm.getCurrentGameState().getSceneColorSwitcher()) {
                                fl=true;
                                o.movedown(gm.getCurrentGameState().getCurrentBall());
                            }
                            if(!fl)
                                gm.getCurrentGameState().getCurrentBall().atend();
                            //for (ColorSwitcher o : g.sceneColorSwitcher)
                            //  o.movedown(g.CurrentBall.getBallShape(),g.CurrentBall.translateTransition );
                            //g.CurrentBall.translateTransition.play();
                        }
                        gm.removehand();
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

                        System.out.println("circle.getTranslateX():"+ gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateX());
                        System.out.println("circle.getTranslateY():"+ gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateY());
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().stop();
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setToY(Double.NaN);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setToX(Double.NaN);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setByX( 0);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setByY(- movedistance);

                        //Setting the cycle count for the transition
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setCycleCount(1);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setDuration(Duration.millis(movtime));
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setOnFinished(null) ;
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().play();
                        break;
                    case A:
                        System.out.println("circle.getTranslateX():"+ gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateX());
                        System.out.println("circle.getTranslateY():"+ gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateY());
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().stop();
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setToX(Double.NaN);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setToY(Double.NaN);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setByY( 0);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setByX(- movedistance);

                        //Setting the cycle count for the transition
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setCycleCount(1);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setDuration(Duration.millis(movtime));
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setOnFinished(null) ;
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().play();
                        break;
                    case S:
                        System.out.println("circle.getTranslateX():"+ gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateX());
                        System.out.println("circle.getTranslateY():"+ gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateY());
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().stop();
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setToY(Double.NaN);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setToX(Double.NaN);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setByX( 0);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setByY( movedistance);

                        //Setting the cycle count for the transition
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setCycleCount(1);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setDuration(Duration.millis(movtime));
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setOnFinished(null) ;
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().play();
                        break;
                    case D:
                        System.out.println("circle.getTranslateX():"+ gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateX());
                        System.out.println("circle.getTranslateY():"+ gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateY());
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().stop();
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setToX(Double.NaN);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setToY(Double.NaN);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setByY( 0);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setByX( movedistance);

                        //Setting the cycle count for the transition
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setCycleCount(1);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setDuration(Duration.millis(movtime));
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setOnFinished(null) ;
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().play();
                        break;
                    case P://pause
//                        Platform.runLater(() -> {
//                                    mp_button.stop();
//                                    mp_button.play();
//                                });
                        gm.Pause();
                        break;

                    case R://continue
//                        Platform.runLater(() -> {
//                            mp_button.stop();
//                            mp_button.play();
//                        });
                        gm.continueGame();
                        break;
                    default:
                        System.out.println("defaultkey");
                        break;
                }
            }
        });
    }

    private void show_pause_screen(Stage stage){
        InGameMenu in_game_menu = new InGameMenu();
        in_game_menu.in_game_stage = stage;
        try {
            in_game_menu.start(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Group getGrp() {
        return grp;
    }
    public GameState getCurrentGameState() {
        return CurrentGameState;
    }
    public void setCurrentGameState(GameState currentGameState) {
        CurrentGameState = currentGameState;
    }

    public void removehand() {
        CurrentGameState.getHand().removeself(grp);
    }

    public void setGameplay_stage(Stage gameplay_stage) {
        this.gameplay_stage = gameplay_stage;
    }
    public void setGrp(Group grp) {
        this.grp = grp;
    }

    public Stage getGameplay_stage() {
        return gameplay_stage;
    }


}
