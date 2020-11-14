package sample;
//https://www.geeksforgeeks.org/javafx-background-class/



import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.animation.PathTransition.OrientationType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.text.SimpleDateFormat;
import javafx.scene.shape.*;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.control.Label;
import javafx.util.*;
import javafx.beans.*;
import javafx.scene.input.KeyEvent;
import java.util.*;

public class Main extends Application {
    private static  double screenwidth=1200;
    private static double screenheight=800;
    private final int movedistance = 100;//distance moved in one move
    private final int movtime=250;
    @Override
    public void start(Stage primaryStage) throws Exception{

//        Path path = new Path();
//        //centre = 300,300
//        // radius = 50
//        MoveTo mt1 = new MoveTo(300,250);
//        ArcTo inner = new ArcTo();
//        ArcTo outer = new ArcTo();
//        VLineTo v1 = new VLineTo(250);
//        HLineTo h1 = new HLineTo(370);
//        inner.setSweepFlag(true);
//        inner.setX(350);
//        inner.setY(300);
//        outer.setX(300);
//        outer.setY(230);
//        inner.setRadiusX(50);
//        inner.setRadiusY(50);
//        outer.setRadiusX(70);
//        outer.setRadiusY(70);
////        outer.setSweepFlag(true);
//        path.setFill(Color.PURPLE);
//        path.getElements().add(mt1);
//        path.getElements().add(inner);
////        path.getElements().add(v1);
//        path.getElements().add(h1);
//        path.getElements().add(outer);
//        path.getElements().add(v1);
//
//        Path path2 = new Path();
//        MoveTo mt2 = new MoveTo(300,250);
//        ArcTo inner2 = new ArcTo();
//        ArcTo outer2 = new ArcTo();
//        VLineTo v2 = new VLineTo(250);
//        HLineTo h2 = new HLineTo(230);
//        outer2.setSweepFlag(true);
//        inner2.setX(250);
//        inner2.setY(300);
//        outer2.setX(300);
//        outer2.setY(230);
//        inner2.setRadiusX(50);
//        inner2.setRadiusY(50);
//        outer2.setRadiusX(70);
//        outer2.setRadiusY(70);
////        outer.setSweepFlag(true);
//        path2.setFill(Color.YELLOW);
//        path2.getElements().add(mt2);
//        path2.getElements().add(inner2);
////        path.getElements().add(v2);
//        path2.getElements().add(h2);
//        path2.getElements().add(outer2);
//        path2.getElements().add(v1);
//
//        Path path3 = new Path();
//        MoveTo mt3 = new MoveTo(300,350);
//        ArcTo inner3 = new ArcTo();
//        ArcTo outer3 = new ArcTo();
//        VLineTo v3 = new VLineTo(350);
//        HLineTo h3 = new HLineTo(370);
////        inner.setSweepFlag(true);
//        outer3.setSweepFlag(true);
//        inner3.setX(350);
//        inner3.setY(300);
//        outer3.setX(300);
//        outer3.setY(370);
//        inner3.setRadiusX(50);
//        inner3.setRadiusY(50);
//        outer3.setRadiusX(70);
//        outer3.setRadiusY(70);
//        path3.setFill(Color.DEEPPINK);
//        path3.getElements().add(mt3);
//        path3.getElements().add(inner3);
//        path3.getElements().add(h3) ;
//        path3.getElements().add(outer3);
//        path3.getElements().add(v3);
////
//        Path path4 = new Path();
//        MoveTo mt4 = new MoveTo(300,350);
//        ArcTo inner4 = new ArcTo();
//        ArcTo outer4 = new ArcTo();
//        VLineTo v4 = new VLineTo(350);
//        HLineTo h4 = new HLineTo(230);
//        inner4.setSweepFlag(true);
//        inner4.setX(250);
//        inner4.setY(300);
//        outer4.setX(300);
//        outer4.setY(370);
//        inner4.setRadiusX(50);
//        inner4.setRadiusY(50);
//        outer4.setRadiusX(70);
//        outer4.setRadiusY(70);
////        outer.setSweepFlag(true);
//        path4.setFill(Color.CYAN);
//        path4.getElements().add(mt4);
//        path4.getElements().add(inner4);
////        path.getElements().add(v1);
//        path4.getElements().add(h4);
//        path4.getElements().add(outer4);
//        path4.getElements().add(v4);
//
//        Rotate rotate = new Rotate();
//        path.getTransforms().add(rotate);
//        rotate.setPivotX(300);
//        rotate.setPivotY(300);
//
//        Timeline t = new Timeline();
//        t.setCycleCount(Animation.INDEFINITE);
//        t.getKeyFrames().add(new KeyFrame(Duration.millis((3000)), new KeyValue(rotate.angleProperty(), 360)));
////        t.play();
//
//        Rotate rotate2 = new Rotate();
//        path2.getTransforms().add(rotate2);
//        rotate2.setPivotX(300);
//        rotate2.setPivotY(300);
//
//        Timeline t2 = new Timeline();
//        t2.setCycleCount(Animation.INDEFINITE);
//        t2.getKeyFrames().add(new KeyFrame(Duration.millis((3000)), new KeyValue(rotate2.angleProperty(), 360)));
////        t2.play();
//
//        Rotate rotate3 = new Rotate();
//        path3.getTransforms().add(rotate3);
//        rotate3.setPivotX(300);
//        rotate3.setPivotY(300);
//
//        Timeline t3 = new Timeline();
//        t3.setCycleCount(Animation.INDEFINITE);
//        t3.getKeyFrames().add(new KeyFrame(Duration.millis((3000)), new KeyValue(rotate3.angleProperty(), 360)));
////        t3.play();
//
//        Rotate rotate4 = new Rotate();
//        path4.getTransforms().add(rotate4);
//        rotate4.setPivotX(300);
//        rotate4.setPivotY(300);
//
//        Timeline t4 = new Timeline();
//        t4.setCycleCount(Animation.INDEFINITE);
//        t4.getKeyFrames().add(new KeyFrame(Duration.millis((3000)), new KeyValue(rotate4.angleProperty(), 360)));
//        t4.play();

        Group root = new Group();
        GameMain gm=new GameMain(root);
        Star s=new Star(600,300);
        GameState g =new GameState(s);
        gm.setCurrentGameState(g);






        Scene scene = new Scene(root,screenwidth,screenheight, Color.BLACK);
        g.shownOnScreen(root);
        Timer timer = new Timer();


        timer.schedule(gm , 500, 100);

        //timer checks collions after every 1s
        // to stop the timer ,use down arrow key
        moveBallOnKeyPress(scene, gm ,timer,s);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Path Transition Example");
        primaryStage.show();
//        gm.getCurrentGameState().sceneObstacles.get(0).pauseRing();
//        long startTime = System.nanoTime();
//
//        long endTime  ;
//        while(true){
//            endTime   = System.nanoTime();
//            if(endTime -startTime>1000000000){
//                g.debug();
//                startTime = System.nanoTime();
//            }
//
//        }
    }
    private void moveBallOnKeyPress(Scene scene,  GameMain gm,Timer timer,Star s) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        //g.debug();
                        if((600.0f+ gm.getCurrentGameState().getCurrentBall().getBallShape().getTranslateY()-movedistance)> (screenheight/2))
                            gm.getCurrentGameState().getCurrentBall().MoveBall();
                        else {
                            gm.getCurrentGameState().getCurrentBall().getTranslateTransition().stop();
                            //g.CurrentBall.translateTransition.setByY(0);
                            //g.CurrentBall.translateTransition.setCycleCount(1);
                            //g.CurrentBall.translateTransition.setDuration(Duration.millis(0));
                            boolean fl=false;
                            for (Obstacle o : gm.getCurrentGameState().getSceneObstacles()) {
                                fl=true;
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
                        break;

                    case DOWN://todo move to exit from game button button
                        System.out.println("downkey");
                        timer.cancel();
                        timer.purge();
                        break;
                    case LEFT:
                        System.out.println("leftkey");
                        s.getPolygon().setVisible(false);
                        gm.getCurrentGameState().getSceneColorSwitcher().remove(s);
                        gm.getGrp().getChildren().remove(s);
                        gm.getCurrentGameState().getSceneStars().remove(s);
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

