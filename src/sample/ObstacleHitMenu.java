package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.util.Map;

public class ObstacleHitMenu extends Menu {

    MainPageMenu main_page_obj;
    Stage ob_hit_stage;
    GameMain game_main;
    public ObstacleHitMenu(){

    }
    @Override
    public void start(Stage HitMenuStage) throws Exception {
        this.ob_hit_stage = HitMenuStage;
        Group in_game_group = new Group();

        Text game_over_text = new Text("GAME OVER");
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
                    Platform.runLater(() -> {
                        MainPageMenu.mp_button.stop();
                        MainPageMenu.mp_button.play();
                    });
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
                    Platform.runLater(() -> {
                        MainPageMenu.mp_button.stop();
                        MainPageMenu.mp_button.play();
                    });
                    resurrect();
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
                    Platform.runLater(() -> {
                        MainPageMenu.mp_button.stop();
                        MainPageMenu.mp_button.play();
                    });
                    exitToMainPage();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };

        restart_game_button.setOnAction(event_restart_game);
        continue_game_button.setOnAction(event_continue_game);
        exit_to_main_button.setOnAction(event_exit_to_main);



        in_game_group.getChildren().addAll(game_over_text, restart_game_button, continue_game_button, exit_to_main_button);

        Scene scene = new Scene(in_game_group,1200,800, Color.BLACK);
        HitMenuStage.setScene(scene);
        HitMenuStage.setTitle("In Game Menu");
        HitMenuStage.show();

        HitMenuStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

    }


    public void resurrect(){
        Platform.runLater(() -> {
            System.out.println("GAME WILL BE CONTINUED !! + COLL FLAG == " + game_main.getCollided_flag());
            if ( game_main.getCurrentGameState().getNumStarsinGame() < 2){
                try{
                    throw new StarsNotSufficientException();
                }
                catch(StarsNotSufficientException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Insufficient Stars");
                    alert.setHeaderText("INSUFFICIENT STARS !! ");
                    alert.setContentText("Sorry ! You do not have sufficient stars to continue ");
                    alert.showAndWait();
                }
            } else {
                game_main.setCollided_flag(false);
                game_main.setLock(false);
//            game_main.lock = false;
//        game_main.getCurrentGameState().coll_flag = false;
                game_main.continueGame();
            game_main.getCurrentGameState().decreaseNumStarsinGame();
                game_main.getAssociatedMain().getMainStage().setScene(game_main.getGm_scene());
                game_main.getAssociatedMain().getMainStage().show();
//            game_main.AssociatedMain.getMainStage().setScene(game_main.getGm_scene());
//            game_main.AssociatedMain.getMainStage().show();
//        game_main.Pause();
            }
        });

    }

    public void restartGame(){
//        System.out.println("GAME WILL BE SAVED !!");
        game_main.setLoad(false);
        game_main.startGame(game_main.getAssociatedMain().getMainStage(), game_main.getCurrentGameState().getPlayer_name());
//        game_main.startGame(game_main.AssociatedMain.getMainStage(), game_main.getCurrentGameState().getPlayer_name());
    }
    public void exitToMainPage() throws Exception {
        long to_set = main_page_obj.AssociatedMain.getGm().getNumStars() + main_page_obj.AssociatedMain.getGm().getCurrentGameState().getNumStarsinGame();
        main_page_obj.AssociatedMain.getGm().setNumStars(to_set);
//        main_page_obj.AssociatedMain.getGm().numStars+=main_page_obj.AssociatedMain.getGm().getCurrentGameState().getNumStarsinGame();


        main_page_obj.AssociatedMain.getGm().setCurrentGameState(null);
        System.out.println("GAME WILL BE EXITED TO MAIN PAGE !!");
        main_page_obj.start(main_page_obj.main_page_stage);

    }

    public static void main(String args[]){
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
