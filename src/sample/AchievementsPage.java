package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

public class AchievementsPage extends Application{
    private static  double screenheight=800;
    private static double screenwidth = 1200;
    private MainPageMenu main_page_obj;
    private GameMain gm;
    private Stage achievement_page_stage;
    private Stage mp_stage;

    @Override
    public void start(Stage AchievementStage) throws Exception {

        this.achievement_page_stage = AchievementStage;
        Group achievement_page_group = new Group();
        ImageView gif1=new ImageView();
        gif1.setImage(  new Image(new File("source3.gif").toURI().toString()));
        gif1.setX(screenwidth*0.05);
        gif1.setY(screenheight*0.1);
        gif1.setFitHeight(230);
        gif1.setFitWidth(290);

        ImageView gif2=new ImageView();
        gif2.setImage(  new Image(new File("source4.gif").toURI().toString()));
        gif2.setX(screenwidth*0.75);
        gif2.setY(screenheight*0.1);
        gif2.setFitHeight(230);
        gif2.setFitWidth(290);

        Image how_to_play_image = new Image(new FileInputStream("achievements.png"));
        ImageView imageView = new ImageView(how_to_play_image);
        imageView.setX(485);
        imageView.setY(120);
        imageView.setFitHeight(180);
        imageView.setFitWidth(230);
        imageView.setPreserveRatio(true);

        ImageView redball = new ImageView(new Image(new FileInputStream("redball2.jpg")));
        redball.setX(screenwidth*0.33);
        redball.setY(screenheight*0.75);
//        imageView.setFitHeight(180);
//        imageView.setFitWidth(230);



        ConcentricObstacle conc = new ConcentricObstacle("concentric", 6000, 0, 130, 15, 600, 185, true, 45);
        conc.draw();
        conc.WayOfMovement();
        conc.rotateConcentric();
        conc.shownOnScreen(achievement_page_group);


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
        Rectangle tmp;
        int i=0;
        for(Map.Entry<Integer,Achievement> t: gm.getGameAchievements().entrySet()) {
            if(t.getValue().getUnlock()) {
                tmp = t.getValue().getUnlocked();
            }
            else {
                tmp = t.getValue().getLocked();
            }
//            System.out.println("get:"+tmp.getHeight());
            tmp.setLayoutY(380+(i*tmp.getHeight()));
            achievement_page_group.getChildren().add(tmp);
            t.getValue().text.setLayoutY(380+(i*tmp.getHeight()));
            achievement_page_group.getChildren().add(t.getValue().text);
            i+=1;
        }
//        help_page_group.getChildren().add(text);
        achievement_page_group.getChildren().addAll(imageView,home_button,redball,gif1,gif2);
//        help_page_group.getChildren().add(home_button);

        Scene scene = new Scene(achievement_page_group,1200,800, Color.BLACK);
        AchievementStage.setScene(scene);
        AchievementStage.setTitle("Main Page Menu");
        AchievementStage.show();

        EventHandler<ActionEvent> event_back_to_home = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("BUTTON 1 PRESSED");
                try {
                    MainPageMenu.mp_button.stop();MainPageMenu.mp_button.play();
                    backToHome();
//                    m.start(stage);
//                    this.newGame();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };

        home_button.setOnAction(event_back_to_home);

        AchievementStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
    public MainPageMenu getMain_page_obj() {
        return main_page_obj;
    }

    public void setMain_page_obj(MainPageMenu main_page_obj) {
        this.main_page_obj = main_page_obj;
    }

    public GameMain getGm() {
        return gm;
    }

    public void setGm(GameMain gm) {
        this.gm = gm;
    }

    public Stage getAchievement_page_stage() {
        return achievement_page_stage;
    }

    public void setAchievement_page_stage(Stage achievement_page_stage) {
        this.achievement_page_stage = achievement_page_stage;
    }

    public Stage getMp_stage() {
        return mp_stage;
    }

    public void setMp_stage(Stage mp_stage) {
        this.mp_stage = mp_stage;
    }

}

