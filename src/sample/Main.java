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

        this.MainStage = primaryStage;
        new LoadingPage().start(primaryStage);
//        Media ballup = new Media(new File("ballup.mp3").toURI().toString());
//        MediaPlayer mp_ballup = new MediaPlayer(ballup);
//        Media button = new Media(new File("button.wav").toURI().toString());
//        MediaPlayer mp_button = new MediaPlayer(button);

        //===========https://stackoverflow.com/questions/31784698/javafx-background-thread-task-should-play-music-in-a-loop-as-background-thread

        main_page.start(primaryStage);



    }


    public static void main(String[] args) {
        launch(args);
    }
}

//This is a branch
//
//

