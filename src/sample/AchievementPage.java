package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class AchievementPage extends Application{

    MainPageMenu main_page_obj;
    Stage mp_stage;
    @Override
    public void start(Stage stage) throws Exception {
        Group achievement_page_group = new Group();

        Image image = new Image(new FileInputStream("/Users/rohitritika/Desktop/AP_RESOURCE/achievement.png"));
        ImageView imageView = new ImageView(image);
        imageView.setX(485);
        imageView.setY(130);
        imageView.setFitHeight(180);
        imageView.setFitWidth(230);
        imageView.setPreserveRatio(true);

        ConcentricObstacle conc = new ConcentricObstacle("concentric", 6000, 0, 140, 15, 600, 185, true, 45);
        conc.draw();
        conc.WayOfMovement();
        conc.rotateConcentric();
        conc.shownOnScreen(achievement_page_group);

        FileInputStream input = new FileInputStream("home_button.png");
        Image home = new Image(input);
        ImageView home_button_image = new ImageView(home);
        home_button_image.setX(0);
        home_button_image.setY(0);
        home_button_image.setFitHeight(100);
        home_button_image.setFitWidth(50);
        home_button_image.setPreserveRatio(true);

        Button home_button = new Button("HOME", home_button_image);
        home_button.setPrefSize(140,50);
        home_button.setLayoutX(0);
        home_button.setLayoutY(0);

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


        achievement_page_group.getChildren().addAll(imageView, home_button);
        Scene scene = new Scene(achievement_page_group,1200,800, Color.BLACK);
        stage.setScene(scene);
        stage.setTitle("Main Page Menu");
        stage.show();
    }
    public void backToHome() throws Exception {
//        Main main_page_obj;
        main_page_obj.start(mp_stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
