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
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import java.awt.Desktop;
import java.net.URI;

import java.io.FileInputStream;
import java.util.Optional;

import static javafx.scene.media.AudioClip.INDEFINITE;

public class MainPageMenu extends Menu {

    // privatise !
    protected HowToPlayPage how_to_play_page_obj = new HowToPlayPage();
    protected AchievementsPage achievement_page_obj = new AchievementsPage();
    protected ShopPage shop_page_obj;
    protected LoadGamesMenu load_page;
    protected SpinWheelPage spin_page = new SpinWheelPage();

    //    InGameMenu in_game_obj = new InGameMenu();
    protected Main AssociatedMain;
    protected static AudioClip mp_button,mp_bgmusic;
    protected Stage main_page_stage;

    public MainPageMenu(Main m){
        mp_bgmusic= new AudioClip( "file:background.wav" );
        mp_bgmusic.setVolume(0.5);
        mp_bgmusic.setCycleCount(INDEFINITE);
        mp_bgmusic.play();
        mp_button=new AudioClip( "file:button.wav" );
        mp_button.setCycleCount(1);mp_button.setVolume(1);
        this.AssociatedMain = m;
        this.how_to_play_page_obj.main_page_obj = this;
        this.achievement_page_obj.setMain_page_obj(this);
        this.achievement_page_obj.setGm(m.getGm());
//        this.achievement_page_obj.main_page_obj=this;
//        this.achievement_page_obj.gm=m.getGm();
        shop_page_obj=new ShopPage(m.getGm());
        this.shop_page_obj.main_page_obj=this;

        load_page= new LoadGamesMenu(AssociatedMain.getGm().getGameDetails().getPlayernames());
        this.load_page.main_page_obj = this;
        this.load_page.gm = m.getGm();

        this.spin_page.setMain_page_obj(this);
        this.spin_page.setGame_main(m.getGm());


//        this.in_game_obj.main_page_obj = this;

    }
    @Override
    public void start(Stage stage) throws Exception {

        this.main_page_stage = stage;
        this.how_to_play_page_obj.mp_stage = stage;
//        this.achievement_page_obj.mp_stage = stage;
        this.achievement_page_obj.setMp_stage(stage);
        this.shop_page_obj.mp_stage = stage;
        this.load_page.mp_stage = stage;
        this.spin_page.setMp_stage(stage);

        Group main_page_group = new Group();

        Image image = new Image(new FileInputStream("logo.png"));
        ImageView imageView = new ImageView(image);
        imageView.setX(485);
        imageView.setY(100);
        imageView.setFitHeight(180);
        imageView.setFitWidth(230);
        imageView.setPreserveRatio(true);

        String original = " -fx-background-color: #ecebe9, rgba(0,0,0,0.05), linear-gradient(#dcca8a, #c7a740), linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%), linear-gradient(#f6ebbe, #e6c34d); -fx-background-insets: 0,9 9 8 9,9,10,11; -fx-background-radius: 50; -fx-padding: 15 30 15 30; -fx-font-family: Arial; -fx-font-size: 18px; -fx-text-fill: #311c09; -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);";
        String entered =  " -fx-background-color: darkslateblue, rgba(0,0,0,0.05), linear-gradient(#dcca8a, #c7a740), linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%), linear-gradient(#f6ebbe, #e6c34d); -fx-background-insets: 0,9 9 8 9,9,10,11; -fx-background-radius: 50; -fx-padding: 15 30 15 30; -fx-font-family: Arial; -fx-font-size: 18px; -fx-text-fill: #311c09; -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);";
        String exited =   " -fx-background-color: #ecebe9, rgba(0,0,0,0.05), linear-gradient(#dcca8a, #c7a740), linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%), linear-gradient(#f6ebbe, #e6c34d); -fx-background-insets: 0,9 9 8 9,9,10,11; -fx-background-radius: 50; -fx-padding: 15 30 15 30; -fx-font-family: Arial; -fx-font-size: 18px; -fx-text-fill: #311c09; -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);";


        ConcentricObstacle conc = new ConcentricObstacle("concentric", 6000, 0, 140, 15, 600, 185, true, 45);
        conc.draw();
        conc.WayOfMovement();
        conc.rotateConcentric();
        conc.shownOnScreen(main_page_group);

        Button new_game_button = new Button("NEW GAME");
        new_game_button.setPrefSize(200,50);
        new_game_button.setLayoutX(500);
        new_game_button.setLayoutY(390);
        new_game_button.setStyle(original);
        new_game_button.setOnMouseEntered(e -> new_game_button.setStyle(entered));
        new_game_button.setOnMouseExited(e -> new_game_button.setStyle(exited));

        Button resume_game_button = new Button("RESUME GAME");
        resume_game_button.setPrefSize(200,50);
        resume_game_button.setLayoutX(500);
        resume_game_button.setLayoutY(470);
        resume_game_button.setStyle(original);
        resume_game_button.setOnMouseEntered(e -> resume_game_button.setStyle(entered));
        resume_game_button.setOnMouseExited(e -> resume_game_button.setStyle(exited));

        Button how_to_play_button = new Button("HOW TO PLAY");
        how_to_play_button.setPrefSize(200,50);
        how_to_play_button.setLayoutX(500);
        how_to_play_button.setLayoutY(550);
        how_to_play_button.setStyle(original);
        how_to_play_button.setOnMouseEntered(e -> how_to_play_button.setStyle(entered));
        how_to_play_button.setOnMouseExited(e -> how_to_play_button.setStyle(exited));

        Button lucky_spin_button = new Button("LUCKY SPIN");
        lucky_spin_button.setPrefSize(200,50);
        lucky_spin_button.setLayoutX(270);
        lucky_spin_button.setLayoutY(510);
        lucky_spin_button.setStyle(original);
        lucky_spin_button.setOnMouseEntered(e -> lucky_spin_button.setStyle(entered));
        lucky_spin_button.setOnMouseExited(e -> lucky_spin_button.setStyle(exited));

        Button exit_game_button = new Button("EXIT GAME");
        exit_game_button.setPrefSize(200,50);
        exit_game_button.setLayoutX(500);
        exit_game_button.setLayoutY(630);
        exit_game_button.setStyle(original);
        exit_game_button.setOnMouseEntered(e -> exit_game_button.setStyle(entered));
        exit_game_button.setOnMouseExited(e -> exit_game_button.setStyle(exited));

        Button achievements_button = new Button("ACHIEVEMENTS");
        achievements_button.setPrefSize(200,50);
        achievements_button.setLayoutX(730);
        achievements_button.setLayoutY(420);
        achievements_button.setWrapText(true);
        achievements_button.setStyle(original);
        achievements_button.setOnMouseEntered(e -> achievements_button.setStyle(entered));
        achievements_button.setOnMouseExited(e -> achievements_button.setStyle(exited));
        Button feedback_button = new Button("FEEDBACK");
        feedback_button.setPrefSize(200,50);
        feedback_button.setLayoutX(730);
        feedback_button.setLayoutY(510);
        feedback_button.setStyle(original);
        feedback_button.setOnMouseEntered(e -> feedback_button.setStyle(entered));
        feedback_button.setOnMouseExited(e -> feedback_button.setStyle(exited));
//        shop_button.setStyle(   " -fx-background-color: #ecebe9, rgba(0,0,0,0.05), linear-gradient(#dcca8a, #c7a740), linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%), linear-gradient(#f6ebbe, #e6c34d); -fx-background-insets: 0,9 9 8 9,9,10,11; -fx-background-radius: 50; -fx-padding: 15 30 15 30; -fx-font-family: Arial; -fx-font-size: 18px; -fx-text-fill: #311c09; -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
//        shop_button.setOnMouseEntered(e ->         shop_button.setStyle(   " -fx-background-color: darkslateblue, rgba(0,0,0,0.05), linear-gradient(#dcca8a, #c7a740), linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%), linear-gradient(#f6ebbe, #e6c34d) ;-fx-background-insets: 0,9 9 8 9,9,10,11; -fx-background-radius: 50; -fx-padding: 15 30 15 30; -fx-font-family: Arial; -fx-font-size: 18px; -fx-text-fill: #311c09; -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"));
//        shop_button.setOnMouseExited(e ->  shop_button.setStyle(   " -fx-background-color: #ecebe9, rgba(0,0,0,0.05), linear-gradient(#dcca8a, #c7a740), linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%), linear-gradient(#f6ebbe, #e6c34d); -fx-background-insets: 0,9 9 8 9,9,10,11; -fx-background-radius: 50; -fx-padding: 15 30 15 30; -fx-font-family: Arial; -fx-font-size: 18px; -fx-text-fill: #311c09; -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"));

        Button shop_button = new Button("SHOP");
        shop_button.setPrefSize(200,50);
        shop_button.setLayoutX(270);
        shop_button.setLayoutY(420);
        shop_button.setTextFill(Color.WHITESMOKE);
        shop_button.setStyle(original);
        shop_button.setOnMouseEntered(e -> shop_button.setStyle(entered));
        shop_button.setOnMouseExited(e -> shop_button.setStyle(exited));

//        shop_button.setStyle("-fx-background-color: darkslateblue, rgba(0,0,0,0.05), linear-gradient(#dcca8a, #c7a740), linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%), linear-gradient(#f6ebbe, #e6c34d); -fx-background-radius: 50; -fx-text-fill: black; -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1); -fx-padding: 15 30 15 30; -fx-background-insets: 0,9 9 8 9,9,10,11;");
//        shop_button.setOnMouseEntered(e->shop_button.setStyle("-fx-background-color: orange, rgba(0,0,0,0.05), linear-gradient(#dcca8a, #c7a740), linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%), linear-gradient(#f6ebbe, #e6c34d); -fx-background-radius: 50; -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1); -fx-padding: 15 30 15 30; -fx-background-insets: 0,9 9 8 9,9,10,11; "));
//        shop_button.setOnMouseExited(e->shop_button.setStyle("-fx-background-color: darkslateblue, rgba(0,0,0,0.05), linear-gradient(#dcca8a, #c7a740), linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%), linear-gradient(#f6ebbe, #e6c34d);  -fx-background-radius: 50; -fx-text-fill: black; -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);  -fx-padding: 15 30 15 30; -fx-background-insets: 0,9 9 8 9,9,10,11;"));
//        shop_button.setStyle(   " -fx-background-color: #ecebe9, rgba(0,0,0,0.05), linear-gradient(#dcca8a, #c7a740), linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%), linear-gradient(#f6ebbe, #e6c34d); -fx-background-insets: 0,9 9 8 9,9,10,11; -fx-background-radius: 50; -fx-padding: 15 30 15 30; -fx-font-family: Arial; -fx-font-size: 18px; -fx-text-fill: #311c09; -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
//        shop_button.setOnMouseEntered(e ->         shop_button.setStyle(   " -fx-background-color: darkslateblue, rgba(0,0,0,0.05), linear-gradient(#dcca8a, #c7a740), linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%), linear-gradient(#f6ebbe, #e6c34d) ;-fx-background-insets: 0,9 9 8 9,9,10,11; -fx-background-radius: 50; -fx-padding: 15 30 15 30; -fx-font-family: Arial; -fx-font-size: 18px; -fx-text-fill: #311c09; -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"));
//        shop_button.setOnMouseExited(e ->  shop_button.setStyle(   " -fx-background-color: #ecebe9, rgba(0,0,0,0.05), linear-gradient(#dcca8a, #c7a740), linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%), linear-gradient(#f6ebbe, #e6c34d); -fx-background-insets: 0,9 9 8 9,9,10,11; -fx-background-radius: 50; -fx-padding: 15 30 15 30; -fx-font-family: Arial; -fx-font-size: 18px; -fx-text-fill: #311c09; -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);"));
//        shop_button.setStyle("-fx-background-color: #72f542; -fx-background-radius: 50;  -fx-font-family: Snell Roundhand; -fx-font-size: 25px;-fx-font-weight: 900");
        main_page_group.getChildren().addAll(imageView,new_game_button, resume_game_button, how_to_play_button, lucky_spin_button, exit_game_button,achievements_button,feedback_button,shop_button);
//        main_page_group.getChildren().add(new_game_button);
        Scene scene = new Scene(main_page_group,1200,800, Color.BLACK);
        stage.setScene(scene);
        stage.setTitle("Main Page Menu");
        stage.show();

        EventHandler<ActionEvent> event_new_game = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                mp_button.stop();
                mp_button.play();
                System.out.println("BUTTON NEW GAME PRESSED");

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
                mp_button.stop();
                mp_button.play();
                System.out.println("BUTTON LOAD GAME PRESSED");
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
                mp_button.stop();
                mp_button.play();
                System.out.println("BUTTON HOW TO PLAY PRESSED");
                try {
                    HowToPlay();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };

        EventHandler<ActionEvent> event_spin_wheel = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                mp_button.stop();
                mp_button.play();
                System.out.println("BUTTON Lucky Spin PRESSED");
                try {
                    LuckySpin();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        EventHandler<ActionEvent> event_exit_game = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                mp_button.stop();
                mp_button.play();
                System.out.println("BUTTON Exit Game PRESSED");
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
                mp_button.stop();
                mp_button.play();
                System.out.println("BUTTON Achievements PRESSED");
                try {
                    MainPageMenu.mp_button.stop();MainPageMenu.mp_button.play();
                    Achieve();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };
        EventHandler<ActionEvent> event_feedback = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                mp_button.stop();
                mp_button.play();
                System.out.println("BUTTON Feedback PRESSED");
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
                mp_button.stop();
                mp_button.play();
                System.out.println("BUTTON Shop PRESSED");
                try {
                    shop();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };

        new_game_button.setOnAction(event_new_game);
        lucky_spin_button.setOnAction(event_spin_wheel);
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
        dialog.setContentText("Please enter the Player name :");
        dialog.initStyle(StageStyle.UNDECORATED);

        Optional<String> name_string=null;
        while(name_string==null  || name_string.get().equals("Empty Slot") ||name_string.get().equals("")){

            //cancel or cross
            // empty string
            //Empty Slot
            //normal name then ok
            name_string=dialog.showAndWait();
            if(name_string.isEmpty())
                break;

            System.out.println("name_string:"+name_string);
//            System.out.println("name_string2:"+name_string.get());
        }
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

    public void LuckySpin() throws Exception{
//        System.out.println("DISPLAY DEVELOPERS");
        spin_page.start((this.main_page_stage));
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
            AssociatedMain.getGm().serializeGameDetails();
            Platform.exit();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void showMenu(){
        try {
            start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
