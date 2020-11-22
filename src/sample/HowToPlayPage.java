package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.FileInputStream;

public class HowToPlayPage extends Application{

    MainPageMenu main_page_obj;
    Stage help_page_stage;
    Stage mp_stage;

    @Override
    public void start(Stage HelpStage) throws Exception {

        this.help_page_stage = HelpStage;
        Group help_page_group = new Group();

        Image how_to_play_image = new Image(new FileInputStream("how_to_play.png"));
        ImageView imageView = new ImageView(how_to_play_image);
        imageView.setX(485);
        imageView.setY(120);
        imageView.setFitHeight(180);
        imageView.setFitWidth(230);
        imageView.setPreserveRatio(true);


        ConcentricObstacle conc = new ConcentricObstacle("concentric", 6000, 0, 130, 15, 600, 185, true, 45);
        conc.draw();
        conc.WayOfMovement();
        conc.rotateConcentric();
        conc.shownOnScreen(help_page_group);

        TextArea text = new TextArea();
        text.setText("You play as a small circle, which is 1 of 4 colours: blue, purple, orange, or pink.The goal is to tap the screen, which will make the circle move up. Ahead of you are obstacles that move, rotate, and change in challenging ways that are coloured the same. Your goal is to move through the obstacles, but you are only able to pass through the colour that your circle resembles. After you pass one obstacle, your colour will change. As you progress, the challenges will become more difficult and diverse. It’s difficult to even get to 10 points.");
//        text.setStyle("text-area-background : #000000;");
        text.setWrapText(true);
        text.setLayoutX(350);
        text.setLayoutY(430);

        text.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas; -fx-highlight-fill: #00ff00; -fx-highlight-text-fill: #000000; -fx-text-fill: #ffffff; ");

//        help_page_group.getChildren().add(text);
//        help_page_group.getChildren().add(imageView);

        FileInputStream input = new FileInputStream("home_button.png");
        Image image = new Image(input);
        ImageView home_button_image = new ImageView(image);
        home_button_image.setX(0);
        home_button_image.setY(0);
        home_button_image.setFitHeight(100);
        home_button_image.setFitWidth(50);
        home_button_image.setPreserveRatio(true);

        Button home_button = new Button("HOME", home_button_image);
        home_button.setPrefSize(140,50);
        home_button.setLayoutX(0);
        home_button.setLayoutY(0);

        help_page_group.getChildren().add(text);
        help_page_group.getChildren().add(imageView);
        help_page_group.getChildren().add(home_button);

        Scene scene = new Scene(help_page_group,1200,800, Color.BLACK);
        HelpStage.setScene(scene);
        HelpStage.setTitle("How To Play");
        HelpStage.show();

        EventHandler<ActionEvent> event_back_to_home = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("BUTTON 1 PRESSED");
                try {
                    backToHome();
//                    m.start(stage);
//                    this.newGame();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };

        home_button.setOnAction(event_back_to_home);

        HelpStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

    }

    public void backToHome() throws Exception {
        main_page_obj.start(mp_stage);
    }
    public static void main(String[] args) {
        launch(args);
    }
}

