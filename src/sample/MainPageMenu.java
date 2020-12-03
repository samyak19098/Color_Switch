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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import java.awt.Desktop;
import java.net.URI;

import java.io.FileInputStream;
import java.util.Optional;

public class MainPageMenu extends Application {

    // privatise !
    HowToPlayPage how_to_play_page_obj = new HowToPlayPage();
    AchievementsPage achievement_page_obj = new AchievementsPage();
    ShopPage shop_page_obj = new ShopPage();
    LoadGamesMenu load_page = new LoadGamesMenu();
//    InGameMenu in_game_obj = new InGameMenu();
    Main AssociatedMain;
    Stage main_page_stage;

    MainPageMenu(Main m){
        this.AssociatedMain = m;
        this.how_to_play_page_obj.main_page_obj = this;
        this.achievement_page_obj.main_page_obj=this;
        this.achievement_page_obj.gm=m.getGm();
        this.shop_page_obj.main_page_obj=this;
        this.shop_page_obj.gm=m.getGm();
        this.load_page.main_page_obj = this;
        this.load_page.gm = m.getGm();

//        this.in_game_obj.main_page_obj = this;

    }
    @Override
    public void start(Stage stage) throws Exception {

        this.main_page_stage = stage;
        this.how_to_play_page_obj.mp_stage = stage;
        this.achievement_page_obj.mp_stage = stage;
        this.shop_page_obj.mp_stage = stage;
        this.load_page.mp_stage = stage;

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

        Button achievements_button = new Button("ACHIEVEMENTS");
        achievements_button.setPrefSize(200,50);
        achievements_button.setLayoutX(710);
        achievements_button.setLayoutY(370);
        Button feedback_button = new Button("FEEDBACK");
        feedback_button.setPrefSize(200,50);
        feedback_button.setLayoutX(710);
        feedback_button.setLayoutY(450);
        Button shop_button = new Button("SHOP");
        shop_button.setPrefSize(200,50);
        shop_button.setLayoutX(710);
        shop_button.setLayoutY(530);


        main_page_group.getChildren().addAll(imageView,new_game_button, resume_game_button, how_to_play_button, display_developer_button, exit_game_button,achievements_button,feedback_button,shop_button);
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
        EventHandler<ActionEvent> event_achievement = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("BUTTON 6 PRESSED");
                try {
                    Achieve();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };
        EventHandler<ActionEvent> event_feedback = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("BUTTON 7 PRESSED");
                try {//===========https://stackoverflow.com/questions/37926495/desktop-and-desktop-browse-are-supported-but-browse-still-hangs
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(new URI("https://docs.google.com/forms/d/e/1FAIpQLSeOVoNOlZNfHiGfusyXL1aTqCRjpRcf46kuARrMvLID1WJWbA/viewform?usp=sf_link"));
                    }
                    //=====================
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };
        EventHandler<ActionEvent> event_shop = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("BUTTON 8 PRESSED");
                try {
                    shop();
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
        achievements_button.setOnAction(event_achievement);
        feedback_button.setOnAction(event_feedback);
        shop_button.setOnAction(event_shop);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

    }

    public void newGame() throws Exception {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Game Save Name");
        dialog.setHeaderText("GAME SAVE NAME ");
        dialog.setContentText("Please enter the game name :");
//        dialog.init
        Optional<String> name_string = dialog.showAndWait();
        String name_inp;
        if (name_string.isPresent()) {
            System.out.println("Your name: " + name_string.get());
            name_inp = name_string.get();
            this.AssociatedMain.getGm().setLoad(false);
            this.AssociatedMain.getGm().startGame(main_page_stage, name_inp);
        }
        else{

        }

    }

    public void resumeGame() throws Exception{
        System.out.println("RESUME FUNCTIONALITY");
//        this.AssociatedMain.getGm().loadgame(main_page_stage);
        load_page.start((this.main_page_stage));
    }

    public void displayDeveloper() throws Exception{
        System.out.println("DISPLAY DEVELOPERS");
    }

    public void HowToPlay() throws Exception {
        how_to_play_page_obj.start((this.main_page_stage));
    }
    public void Achieve() throws Exception {
        achievement_page_obj.start((this.main_page_stage));
    }
    public void shop() throws Exception {
        shop_page_obj.start((this.main_page_stage));
    }


    public void exitGame() throws Exception{
        //exit confirm dialog box
        Alert confirm_alert = new Alert(Alert.AlertType.CONFIRMATION,  "ARE YOU SURE YOU WANT TO EXIT THE GAME ?", ButtonType.YES, ButtonType.NO);
        confirm_alert.setHeaderText(" EXIT CONFIRMATION ");
        confirm_alert.setTitle("EXIT CONFIRMATION");
        confirm_alert.showAndWait();
        if (confirm_alert.getResult() == ButtonType.YES) {
            Platform.exit();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
