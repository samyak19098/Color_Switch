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

import javafx.beans.*;
import java.util.*;
import java.io.*;
import javafx.geometry.*;
import org.w3c.dom.css.Rect;


public class Main extends Application {
    private static  double screenwidth=1200;
    private static double screenheight=800;
    private final int movedistance = 100;//distance moved in one move
    private final int movtime=250;
    @Override
    public void start(Stage primaryStage) throws Exception{


//        Media ballup = new Media(new File("ballup.mp3").toURI().toString());
//        MediaPlayer mp_ballup = new MediaPlayer(ballup);
//        Media button = new Media(new File("button.wav").toURI().toString());
//        MediaPlayer mp_button = new MediaPlayer(button);



        Group root = new Group();
        // set background
        Rectangle hbox=new Rectangle(screenwidth,screenheight);
        Image im = new Image("file:bg.jpg",false);
        hbox.setFill(new ImagePattern(im));
        root.getChildren().add(hbox);



        GameMain gm=new GameMain(root);
//        Star s=new Star(600,300);
        GameState g =new GameState();
        gm.setCurrentGameState(g);


        Button pause_button = new Button("PAUSE GAME");
        pause_button.setPrefSize(100,50);
        pause_button.setLayoutX(20);
        pause_button.setLayoutY(50);
        root.getChildren().add(pause_button);
        EventHandler<ActionEvent> event_new_game = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("PAUSE BUTTON PRESSED");



            }
        };
        pause_button.setOnAction(event_new_game);








        Scene scene = new Scene(root,screenwidth,screenheight);//, Color.BLACK);

        g.shownOnScreen(root);
        Timer timer = new Timer();


        timer.schedule(gm , 500, 100);

        //timer checks collions after every 1s
        // to stop the timer ,use down arrow key
        moveBallOnKeyPress(scene, gm ,timer);//,mp_ballup,mp_button);
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
    private void moveBallOnKeyPress(Scene scene,  GameMain gm,Timer timer){//, MediaPlayer mp_ballup,MediaPlayer mp_button) {
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

    public static void main(String[] args) {
        launch(args);
    }
}

//This is a branch
//
//

