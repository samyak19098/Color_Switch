package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.animation.PathTransition.OrientationType;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.text.SimpleDateFormat;
import javafx.scene.shape.*;

public class Main extends Application {

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
        RingObstacle ringObstacle = new RingObstacle("Ring", 1500, 0, 50,20, 300, 300, true);
        ringObstacle.draw();
//        root.getChildren().add(ringObstacle.getQuarters().get(0));
        ringObstacle.WayOfMovement();

        RingObstacle ringObstacle2 = new RingObstacle("Ring", 1500, 0, 70,20, 300, 300, false);
        ringObstacle2.draw();
//        root.getChildren().add(ringObstacle.getQuarters().get(0));
        ringObstacle2.WayOfMovement();
        ringObstacle.rotateRing();
        ringObstacle2.rotateRing();
        ringObstacle.showOnScreen(root);
        ringObstacle2.showOnScreen(root);

        SquareObstacle squareObstacle = new SquareObstacle("Square", 1500, 0, 500, 500, 60, 20, true);
        squareObstacle.draw();
        squareObstacle.WayOfMovement();
        squareObstacle.rotateSquare();
        squareObstacle.showOnScreen(root);

        Scene scene = new Scene(root,1200,900, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Path Transition Example");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
