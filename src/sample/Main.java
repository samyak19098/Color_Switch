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
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.animation.PathTransition.OrientationType;

import java.lang.reflect.Array;
import java.text.DateFormat;
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


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


import javax.xml.crypto.Data;


import static javafx.scene.media.AudioClip.INDEFINITE;


public class Main extends Application {

//    private static double screenwidth = 1200;
//    private static double screenheight = 800;
//    private final int movedistance = 100;//distance moved in one move
//    private final int movtime = 250;
//    private Timer timer,trailtimer;

    private Stage MainStage;
    private Group root = new Group();
    private GameMain gm = new GameMain(root, this);

    public Stage getMainStage() {
        return MainStage;
    }

    public MainPageMenu getMain_page() {
        return main_page;
    }

    private MainPageMenu main_page = new MainPageMenu(this);

    public Group getRoot() {
        return root;
    }

    public GameMain getGm() {
        return gm;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.initStyle(StageStyle.UNDECORATED);
        this.MainStage = primaryStage;
        new LoadingPage().start(primaryStage);



        Timeline wait=new Timeline();
        wait.getKeyFrames().add(   new KeyFrame(Duration.millis(1000), new   EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
//                System.out.println("this is called every 5 seconds on UI thread");
                try {
                    main_page.start(MainStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
                    ));
        wait.play();

    }


    public static void main(String[] args) {
        launch(args);
    }
}

//This is a branch
//
//

