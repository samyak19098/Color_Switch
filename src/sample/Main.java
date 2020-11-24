package sample;
//https://www.geeksforgeeks.org/javafx-background-class/



import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.control.*;
import javafx.scene.web.*;
import javafx.scene.paint.Color;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.animation.PathTransition.OrientationType;
import java.text.SimpleDateFormat;
import javafx.event.*;
import javafx.util.*;
import javafx.concurrent.*;
import javafx.beans.*;
import java.util.*;
import java.io.*;
import javafx.geometry.*;
import org.w3c.dom.css.Rect;
import javafx.scene.media.*;
import java.awt.AWTException;
//import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import static javafx.scene.media.AudioClip.INDEFINITE;


public class Main extends Application {

    private static double screenwidth = 1200;
    private static double screenheight = 800;
    private final int movedistance = 100;//distance moved in one move
    private final int movtime = 250;
    private Timer timer,trailtimer;

    @Override
    public void start(Stage primaryStage) throws Exception {

        new LoadingPage().start(primaryStage);
//        Media ballup = new Media(new File("ballup.mp3").toURI().toString());
//        MediaPlayer mp_ballup = new MediaPlayer(ballup);
//        Media button = new Media(new File("button.wav").toURI().toString());
//        MediaPlayer mp_button = new MediaPlayer(button);

        //===========https://stackoverflow.com/questions/31784698/javafx-background-thread-task-should-play-music-in-a-loop-as-background-thread
         final Task task = new Task() {
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
        //======

        Group root = new Group();
        // set background
        Rectangle hbox = new Rectangle(screenwidth, screenheight);
        Image im = new Image("file:bg.jpg", false);
        hbox.setFill(new ImagePattern(im));
        root.getChildren().add(hbox);


        GameMain gm = new GameMain(root);
//        Star s=new Star(600,300);
        GameState g = new GameState();
        gm.setCurrentGameState(g);


        Button pause_button = new Button("PAUSE GAME");
        pause_button.setPrefSize(100, 50);
        pause_button.setLayoutX(20);
        pause_button.setLayoutY(50);
        root.getChildren().add(pause_button);
        EventHandler<ActionEvent> event_new_game = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                System.out.println("PAUSE BUTTON PRESSED");


            }
        };
        pause_button.setOnAction(event_new_game);


        Scene scene = new Scene(root, screenwidth, screenheight);//, Color.BLACK);

        g.shownOnScreen(root);
          timer = new Timer();


        timer.schedule(gm, 500, 100);
          trailtimer = new Timer();


        trailtimer.schedule(gm.getCurrentGameState().BallTrail, 500, 200);

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

                        if ((600.0f + gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateY() - movedistance) > (screenheight / 2))
                            gm.getCurrentGameState().getCurrentBall().MoveBall(gm.getGrp());
                        else {
                            gm.getCurrentGameState().getCurrentBall().getTranslateTransition().stop();
                            gm.getCurrentGameState().BallTrail.atend = true;
//                            for(int i=0;i< (gm.getCurrentGameState().getCurrentBall().n-1);i++){
//                                gm.getCurrentGameState().getCurrentBall().t2.get(i).stop();
//
//                            }
                            //g.CurrentBall.translateTransition.setByY(0);
                            //g.CurrentBall.translateTransition.setCycleCount(1);
                            //g.CurrentBall.translateTransition.setDuration(Duration.millis(0));
                            boolean fl = false;//if no obstacle is on screen, then to make  ball go down this flag  is used
//                            System.out.println("movedown1");
                            for (Obstacle o : gm.getCurrentGameState().getSceneObstacles()) {
                                fl = true;
//                                System.out.println("movedown");
                                o.movedown(gm.getCurrentGameState().getCurrentBall());
                            }
                            for (Star o : gm.getCurrentGameState().getSceneStars()) {
                                fl = true;
                                o.movedown(gm.getCurrentGameState().getCurrentBall(), gm.getCurrentGameState().BallTrail);
                            }
                            for (ColorSwitcher o : gm.getCurrentGameState().getSceneColorSwitcher()) {
                                fl = true;
                                o.movedown(gm.getCurrentGameState().getCurrentBall());
                            }
                            if (!fl)
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

                        System.out.println("circle.getTranslateX():" + gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateX());
                        System.out.println("circle.getTranslateY():" + gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateY());
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().stop();
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setToY(Double.NaN);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setToX(Double.NaN);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setByX(0);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setByY(-movedistance);

                        //Setting the cycle count for the transition
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setCycleCount(1);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setDuration(Duration.millis(movtime));
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setOnFinished(null);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().play();
                        break;
                    case A:
                        System.out.println("circle.getTranslateX():" + gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateX());
                        System.out.println("circle.getTranslateY():" + gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateY());
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().stop();
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setToX(Double.NaN);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setToY(Double.NaN);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setByY(0);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setByX(-movedistance);

                        //Setting the cycle count for the transition
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setCycleCount(1);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setDuration(Duration.millis(movtime));
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setOnFinished(null);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().play();
                        break;
                    case S:
                        System.out.println("circle.getTranslateX():" + gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateX());
                        System.out.println("circle.getTranslateY():" + gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateY());
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().stop();
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setToY(Double.NaN);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setToX(Double.NaN);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setByX(0);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setByY(movedistance);

                        //Setting the cycle count for the transition
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setCycleCount(1);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setDuration(Duration.millis(movtime));
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setOnFinished(null);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().play();
                        break;
                    case D:
                        System.out.println("circle.getTranslateX():" + gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateX());
                        System.out.println("circle.getTranslateY():" + gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateY());
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().stop();
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setToX(Double.NaN);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setToY(Double.NaN);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setByY(0);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setByX(movedistance);

                        //Setting the cycle count for the transition
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setCycleCount(1);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setDuration(Duration.millis(movtime));
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().setOnFinished(null);
                        gm.getCurrentGameState().getCurrentBall().getTranslateTransition().play();
                        break;
                    case P://pause
//                        Platform.runLater(() -> {
//                                    mp_button.stop();
//                                    mp_button.play();
//                                });
//                        gm.getCurrentGameState().getSceneObstacles().get(1).Pause();
//                        timer.cancel();
//                        trailtimer.cancel();
                        gm.Pause();
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
                        gm.continueGame();
                        break;
                    case X://screenshot
                        try {
                            Robot robot = new Robot();
                            String format = "jpg";
                            String fileName = "FullScreenshot." + format;

                            java.awt.Rectangle screenRect = new java.awt.Rectangle((int)primaryStage.getX()+5,(int)primaryStage.getY()+30,(int)screenwidth,(int)screenheight-50);//Toolkit.getDefaultToolkit().getScreenSize());
                            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
                            ImageIO.write(screenFullImage, format, new File(fileName));

                            System.out.println("A full screenshot saved!");
                        } catch (AWTException | IOException ex) {
                            System.err.println(ex);
                        }
                        break;
                    default:
                        System.out.println("defaultkey");
                        break;
                }
            }
        });
    }



    public static void main(String[] args) {
        launch(args);
    }
}

//This is a branch
//
//

