package sample;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.FileInputStream;

public class MainPageMenu extends Application {

    // privatise !
    HowToPlayPage how_to_play_page_obj = new HowToPlayPage();
    AchievementPage ach = new AchievementPage();
//    InGameMenu in_game_obj = new InGameMenu();
    Main AssociatedMain;
    Stage main_page_stage;

    MainPageMenu(Main m){
        this.AssociatedMain = m;
        this.how_to_play_page_obj.main_page_obj = this;
        this.ach.main_page_obj = this;
//        this.in_game_obj.main_page_obj = this;
    }
    @Override
    public void start(Stage stage) throws Exception {

        this.main_page_stage = stage;
        this.how_to_play_page_obj.mp_stage = stage;
        this.ach.mp_stage = stage;

        Group main_page_group = new Group();

        Image image = new Image(new FileInputStream("logo.png"));
        ImageView imageView = new ImageView(image);
        imageView.setX(485);
        imageView.setY(100);
        imageView.setFitHeight(180);
        imageView.setFitWidth(230);
        imageView.setPreserveRatio(true);

        ConcentricObstacle conc = new ConcentricObstacle("concentric", 6000, 0, 140, 15, 600, 185, true, 45);
        conc.draw();
        conc.WayOfMovement();
        conc.rotateConcentric();
        conc.shownOnScreen(main_page_group);

        Button new_game_button = new Button("NEW GAME");
        new_game_button.setPrefSize(200,50);
        new_game_button.setLayoutX(500);
        new_game_button.setLayoutY(370);

        Button resume_game_button = new Button("RESUME GAME");
        resume_game_button.setPrefSize(200,50);
        resume_game_button.setLayoutX(500);
        resume_game_button.setLayoutY(450);

        Button how_to_play_button = new Button("HOW TO PLAY");
        how_to_play_button.setPrefSize(200,50);
        how_to_play_button.setLayoutX(500);
        how_to_play_button.setLayoutY(530);

        Button display_developer_button = new Button("DISPLAY DEVELOPERS");
        display_developer_button.setPrefSize(200,50);
        display_developer_button.setLayoutX(500);
        display_developer_button.setLayoutY(610);

        Button exit_game_button = new Button("EXIT GAME");
        exit_game_button.setPrefSize(200,50);
        exit_game_button.setLayoutX(500);
        exit_game_button.setLayoutY(690);

        Image trophy_img = new Image(new FileInputStream("/Users/rohitritika/Desktop/AP_RESOURCE/final_tp.png"));
        ImageView trohpy_img_view = new ImageView(trophy_img);
        trohpy_img_view.setX(200);
        trohpy_img_view.setY(200);
        trohpy_img_view.setFitHeight(100);
        trohpy_img_view.setFitWidth(1000);
        trohpy_img_view.setPreserveRatio(true);

        RotateTransition rot = new RotateTransition(Duration.millis(3000), trohpy_img_view);
        rot.setByAngle(360);
        rot.setCycleCount(Animation.INDEFINITE);
        rot.setInterpolator(Interpolator.LINEAR);
        rot.play();

        EventHandler mouseHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
//                AchievementPage ap = new AchievementPage();
                try {
                    show_ach_page();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        trohpy_img_view.setOnMouseClicked(mouseHandler);



        main_page_group.getChildren().addAll(imageView,new_game_button, resume_game_button, how_to_play_button, display_developer_button, exit_game_button, trohpy_img_view);
//        main_page_group.getChildren().add(new_game_button);
        Scene scene = new Scene(main_page_group,1200,800, Color.BLACK);
        stage.setScene(scene);
        stage.setTitle("Main Page Menu");
        stage.show();

        EventHandler<ActionEvent> event_new_game = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("BUTTON 1 PRESSED");

                try {
                    newGame();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };

        EventHandler<ActionEvent> event_resume_game = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("BUTTON 2 PRESSED");
                try {
                    resumeGame();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        EventHandler<ActionEvent> event_how_to_play = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("BUTTON 3 PRESSED");
                try {
                    HowToPlay();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };

        EventHandler<ActionEvent> event_display_developers = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("BUTTON 4 PRESSED");
                try {
                    displayDeveloper();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        EventHandler<ActionEvent> event_exit_game = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("BUTTON 5 PRESSED");
                try {
                    exitGame();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };

        new_game_button.setOnAction(event_new_game);
        display_developer_button.setOnAction(event_display_developers);
        resume_game_button.setOnAction(event_resume_game);
        how_to_play_button.setOnAction(event_how_to_play);
        exit_game_button.setOnAction(event_exit_game);


        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

    }

    private void show_ach_page() throws Exception {
        ach.start((this.main_page_stage));

    }

    public void newGame() throws Exception {
       this.AssociatedMain.getGm().startGame(main_page_stage);
    }

    public void resumeGame() throws Exception{
        System.out.println("RESUME FUNCTIONALITY");
    }

    public void displayDeveloper() throws Exception{
        System.out.println("DISPLAY DEVELOPERS");
    }

    public void HowToPlay() throws Exception {
        how_to_play_page_obj.start((this.main_page_stage));
    }

    public void exitGame() throws Exception{
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
