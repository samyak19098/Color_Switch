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
import javafx.concurrent.*;
import javafx.beans.*;
import java.util.*;
import java.io.*;
import javafx.geometry.*;
import org.w3c.dom.css.Rect;
import javafx.scene.media.*;

import static javafx.scene.media.AudioClip.INDEFINITE;


public class LoadingPage extends Application {

    private static double screenwidth = 1200;
    private static double screenheight = 800;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        // set background
        Scene scene = new Scene(root, screenwidth, screenheight,Color.BLACK);
        Image image = new Image(new FileInputStream("loading.png"));
        ImageView imageView = new ImageView(image);
        imageView.setX(285);
        imageView.setY(100);
        imageView.setFitHeight(580);
        imageView.setFitWidth(630);
        imageView.setPreserveRatio(true);
        //HBox hbox = new HBox();//screenwidth,screenheight);




        Rectangle bar = new Rectangle(screenwidth/4,screenheight*0.75,screenwidth/2, 50);
        Rectangle progress = new Rectangle(screenwidth/4,screenheight*0.75,0, 50);
        Timeline t1=new Timeline();
        t1.setCycleCount(1);
        t1.getKeyFrames().add(new KeyFrame(Duration.millis(2000), new KeyValue(progress.widthProperty(), screenwidth*0.5, Interpolator.LINEAR)));
        progress.setFill(Color.YELLOW);
        bar.setFill(Color.WHITE);
        t1.play();
        root.getChildren().addAll(bar,progress,imageView);

        primaryStage.setScene(scene);
        primaryStage.setTitle("LOADING");
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        primaryStage.show();


    }



    public static void main(String[] args) {
        launch(args);
    }
}






