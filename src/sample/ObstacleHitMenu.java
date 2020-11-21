package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;

public class ObstacleHitMenu extends Application {

    MainPageMenu main_page_obj;
    Stage in_game_stage;

    @Override
    public void start(Stage InGameStage) throws Exception {
        this.in_game_stage = InGameStage;
        Group in_game_group = new Group();

        Text game_paused_text = new Text("OBSTACLE HIT");
        game_paused_text.setStyle("-fx-font: 80 arial;");

        game_paused_text.setFill(Color.WHITE);
        game_paused_text.setX(300);
        game_paused_text.setLayoutY(100);

        RingObstacle restart_ring = new RingObstacle("Ring", 6000, 0, 110, 15, 270, 410, true);
        Button restart_game_button = new Button("RESTART GAME");
        restart_game_button.setPrefSize(150,50);
        restart_game_button.setLayoutX(195);
        restart_game_button.setLayoutY(385);
        restart_ring.draw();
        restart_ring.WayOfMovement();
        restart_ring.rotateRing();
        restart_ring.shownOnScreen(in_game_group);


        RingObstacle continue_ring = new RingObstacle("Ring", 6000, 0, 110, 15, 580, 410, true);
        Button continue_game_button = new Button("CONTINUE GAME USING STARS");
        continue_game_button.setPrefSize(150,50);
        continue_game_button.setWrapText(true);
        continue_game_button.setLayoutX(505);
        continue_game_button.setLayoutY(385);
        continue_ring.draw();
        continue_ring.WayOfMovement();
        continue_ring.rotateRing();
        continue_ring.shownOnScreen(in_game_group);


        RingObstacle exit_to_main_ring = new RingObstacle("Ring", 6000, 0, 110, 15, 890, 410, true);
        Button exit_to_main_button = new Button("EXIT TO MAIN MENU");
        exit_to_main_button.setPrefSize(150,50);
        exit_to_main_button.setLayoutX(815);
        exit_to_main_button.setLayoutY(385);
        exit_to_main_ring.draw();
        exit_to_main_ring.WayOfMovement();
        exit_to_main_ring.rotateRing();
        exit_to_main_ring.shownOnScreen(in_game_group);



        EventHandler<ActionEvent> event_restart_game = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("BUTTON RESTART GAME PRESSED");
                try {
                    saveGame();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };

        EventHandler<ActionEvent> event_continue_game = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("BUTTON CONTINUE GAME PRESSED");
                try {
                    continueGame();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };

        EventHandler<ActionEvent> event_exit_to_main = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("BUTTON EXIT TO MAIN MENU PRESSED");
                try {
                    exitToMainPage();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };

        restart_game_button.setOnAction(event_restart_game);
        continue_game_button.setOnAction(event_continue_game);
        exit_to_main_button.setOnAction(event_exit_to_main);



        in_game_group.getChildren().addAll(game_paused_text, restart_game_button, continue_game_button, exit_to_main_button);

        Scene scene = new Scene(in_game_group,1200,800, Color.BLACK);
        InGameStage.setScene(scene);
        InGameStage.setTitle("In Game Menu");
        InGameStage.show();

        InGameStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

    }


    public void continueGame(){
        System.out.println("GAME WILL BE CONTINUED !!");
    }

    public void saveGame(){
        System.out.println("GAME WILL BE SAVED !!");
    }
    public void exitToMainPage() throws Exception {
        System.out.println("GAME WILL BE EXITED TO MAIN PAGE !!");
    }

    public static void main(String args[]){
        launch(args);
    }
}
