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

import java.io.FileInputStream;
import java.util.Map;

public class AchievementsPage extends Application{
    protected static  double screenheight=800;
    private static double screenwidth = 1200;
    MainPageMenu main_page_obj;
    GameMain gm;
    Stage achievement_page_stage;
    Stage mp_stage;

    @Override
    public void start(Stage HelpStage) throws Exception {

        this.achievement_page_stage = HelpStage;
        Group help_page_group = new Group();

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
        imageView.setPreserveRatio(true);


        ConcentricObstacle conc = new ConcentricObstacle("concentric", 6000, 0, 130, 15, 600, 185, true, 45);
        conc.draw();
        conc.WayOfMovement();
        conc.rotateConcentric();
        conc.shownOnScreen(help_page_group);


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
            if(t.getValue().Unlock)
                tmp=t.getValue().unlocked;
            else
                tmp=t.getValue().locked;
//            System.out.println("get:"+tmp.getHeight());
            tmp.setLayoutY(380+(i*tmp.getHeight()));
            help_page_group.getChildren().add(tmp);
            t.getValue().text.setLayoutY(380+(i*tmp.getHeight()));
            help_page_group.getChildren().add(t.getValue().text);
            i+=1;
        }
//        help_page_group.getChildren().add(text);
        help_page_group.getChildren().addAll(imageView,home_button,redball);
//        help_page_group.getChildren().add(home_button);

        Scene scene = new Scene(help_page_group,1200,800, Color.BLACK);
        HelpStage.setScene(scene);
        HelpStage.setTitle("Main Page Menu");
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

