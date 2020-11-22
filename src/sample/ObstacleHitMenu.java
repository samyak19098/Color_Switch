package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ObstacleHitMenu extends Application {

//    MainPageMenu main_page_obj;
    Stage hit_menu_stage;

    @Override
    public void start(Stage HitMenuStage) throws Exception {
        this.hit_menu_stage = HitMenuStage;
        Group hit_menu_group = new Group();

        Text game_over_text = new Text(" GAME OVER ");
        game_over_text.setStyle("-fx-font: 80 arial;");

        game_over_text.setFill(Color.WHITE);
        game_over_text.setX(300);
        game_over_text.setLayoutY(100);

        RingObstacle restart_ring = new RingObstacle("Ring", 6000, 0, 110, 15, 270, 410, true);
        Button restart_game_button = new Button("RESTART GAME");
        restart_game_button.setPrefSize(150,50);
        restart_game_button.setLayoutX(195);
        restart_game_button.setLayoutY(385);
        restart_ring.draw();
        restart_ring.WayOfMovement();
        restart_ring.rotateRing();
        restart_ring.shownOnScreen(hit_menu_group);


        RingObstacle continue_ring = new RingObstacle("Ring", 6000, 0, 110, 15, 580, 410, true);
        Button continue_game_button = new Button("CONTINUE GAME USING STARS");
        continue_game_button.setPrefSize(150,50);
        continue_game_button.setWrapText(true);
        continue_game_button.setLayoutX(505);
        continue_game_button.setLayoutY(385);
        continue_ring.draw();
        continue_ring.WayOfMovement();
        continue_ring.rotateRing();
        continue_ring.shownOnScreen(hit_menu_group);


        RingObstacle exit_to_main_ring = new RingObstacle("Ring", 6000, 0, 110, 15, 890, 410, true);
        Button exit_to_main_button = new Button("EXIT TO MAIN MENU");
        exit_to_main_button.setPrefSize(150,50);
        exit_to_main_button.setLayoutX(815);
        exit_to_main_button.setLayoutY(385);
        exit_to_main_ring.draw();
        exit_to_main_ring.WayOfMovement();
        exit_to_main_ring.rotateRing();
        exit_to_main_ring.shownOnScreen(hit_menu_group);



        EventHandler<ActionEvent> event_restart_game = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("BUTTON RESTART GAME PRESSED");
                try {
                    restartGame();
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



        hit_menu_group.getChildren().addAll(game_over_text, restart_game_button, continue_game_button, exit_to_main_button);

        Scene scene = new Scene(hit_menu_group,1200,800, Color.BLACK);
        HitMenuStage.setScene(scene);
        HitMenuStage.setTitle("Game Over Menu");
        HitMenuStage.show();

        HitMenuStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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

    public void restartGame(){
        System.out.println("GAME WILL BE RESTARTED !!");

    }
    public void exitToMainPage() throws Exception {
        System.out.println("GAME WILL BE EXITED TO MAIN PAGE !!");
        MainPageMenu mainpage = new MainPageMenu();
        mainpage.start(hit_menu_stage);
    }

    public static void main(String args[]){
        launch(args);
    }
}
