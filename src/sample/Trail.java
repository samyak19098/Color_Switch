package sample;
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
public class Trail  extends TimerTask implements Serializable{



    protected boolean atend;
    protected static double screenwidth = 1200;
    protected static double screenheight = 800;
    protected final int movedistance = 100;
    protected final int movtime = 250;
    protected final int n = 5;
    protected  boolean pausetrail;
    protected transient  Ball parentball;
    protected transient Queue<Ball> q;
    protected transient ArrayList<Timeline> t1;
    protected transient ArrayList<Ball> list;

    public Trail(){

    }
    public Trail(Ball b) {



        atend=false;
        t1 = new ArrayList<Timeline>();
        parentball = b;

        q = new LinkedList<Ball>();
        list=new ArrayList<Ball>();
        for (int i = 0; i < n; i++) {
            Ball a = new Ball(Color.GRAY, parentball.position.get_x(), parentball.position.get_y(), parentball.getRadius(), i);
            list.add(a);
            q.add(a);
            t1.add(new Timeline());
            t1.get(i).setCycleCount(1);
            t1.get(i).getKeyFrames().add(new KeyFrame(Duration.millis(4*movtime), new KeyValue(((Circle) a.getBallShape()).radiusProperty(), 0, Interpolator.LINEAR)));
            t1.get(i).setOnFinished((new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Platform.runLater(() -> {
                    a.getBallShape().setVisible(false);
                        a. translateTransition.setToY(0);


                        a.translateTransition.setCycleCount(1);
                        a.translateTransition.setDuration(Duration.millis(1));
                        a.translateTransition.play();

                    ((Circle) a.getBallShape()).setRadius(parentball.getRadius());
                    });
                    q.add(a);
//                    System.out.println("Hello World2!"+t);
                }
            }));
        }
        for (Ball var : q) {
            var.getBallShape().setVisible(false);
        }

    }


    public void shownOnScreen(Group g) {
        Platform.runLater(() -> {

            for (Ball b : q) {
                g.getChildren().add(b.getBallShape());
            }

        });

    }
    public int t=0;
    public void run() {

        if (!q.isEmpty() && pausetrail==false) {
//            System.out.println("Hello World!"+t);
            t++;
            Ball b = q.remove();
//            b.atend();
            Platform.runLater(() -> {

                        ((Circle) b.getBallShape()).setCenterY(parentball.getBallShape().getTranslateY() + parentball.getPosition().get_y());
//                System.out.println("a:"+parentball.getBallShape().getTranslateY());
//                System.out.println("b:"+parentball.getPosition().get_y());
//                System.out.println("c:"+b.getBallShape().getTranslateY());
//                System.out.println("d:"+((Circle)b.getBallShape()).getCenterY());

                b.getBallShape().setVisible(true);
                    });
            t1.get(b.id).play();
//            System.out.println("atend:"+atend);
            if(atend)
                b.atendtrail();
        }

//      spawn balls
//              push the balls in a queue;
//      there  radius will change
//                they will be spawned at the position of the ball
//                ****when obstacles move down ,the circles should move down too.

        //They all need TranslateTransition that will be called when objects move down

        //System.out.println("Timer ran ");
    }
    public void Pause(){
        pausetrail=true;
        for (Ball var : list) {
            var.Pause();
            t1.get(var.id).pause();
        }

    }
    public void setColor(Color c) {
        for (Ball var : q) {
            var.getBallShape().setFill(c);
        }
    }

    public void Resume() {
        pausetrail=false;
        for (Ball var : list) {
            var.Resume();
            t1.get(var.id).play();
        }
    }
    public boolean getAtend() {
        return atend;
    }

    public void setAtend(boolean atend) {
        this.atend = atend;
    }
}