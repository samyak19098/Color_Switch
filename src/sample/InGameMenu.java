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

import java.awt.AWTException;
//import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

public class InGameMenu extends Menu {

    private MainPageMenu main_page_obj;
    private Stage in_game_stage;
    private GameMain game_main;

    public InGameMenu(){

    }
    @Override
    public void start(Stage InGameStage) throws Exception {
        this.in_game_stage = InGameStage;
        Group in_game_group = new Group();

        Text game_paused_text = new Text("GAME PAUSED");
        game_paused_text.setStyle("-fx-font: 80 arial;");

        game_paused_text.setFill(Color.WHITE);
        game_paused_text.setX(300);
        game_paused_text.setLayoutY(100);
        RingObstacle save_ring = new RingObstacle("Ring", 6000, 0, 110, 15, 270, 410, true);
        Button save_game_button = new Button("SAVE GAME");
        save_game_button.setPrefSize(150,50);
        save_game_button.setLayoutX(195);
        save_game_button.setLayoutY(385);
        save_ring.draw();
        save_ring.WayOfMovement();
        save_ring.rotateRing();
        save_ring.shownOnScreen(in_game_group);


        RingObstacle continue_ring = new RingObstacle("Ring", 6000, 0, 110, 15, 580, 410, true);
        Button continue_game_button = new Button("CONTINUE GAME");
        continue_game_button.setPrefSize(150,50);
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








        EventHandler<ActionEvent> event_save_game = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

//                InGameMenu igm = new InGameMenu();
                try {
//                    igm.start(primaryStage);
//                    Pause();
                    save_button_handle_function();
//                    audio[0].stop();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println("SAVE BUTTON PRESSED");
            }
        };
        save_game_button.setOnAction(event_save_game);


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

        save_game_button.setOnAction(event_save_game);
        continue_game_button.setOnAction(event_continue_game);
        exit_to_main_button.setOnAction(event_exit_to_main);



        in_game_group.getChildren().addAll(game_paused_text,continue_game_button, exit_to_main_button,save_game_button);

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
        game_main.continueGame();
        game_main.getAssociatedMain().getMainStage().setScene(game_main.getGm_scene());
        game_main.getAssociatedMain().getMainStage().show();
    }

    public void saveGame() throws Exception {
        System.out.println("GAME WILL BE SAVED !!");
//        game_main.savegame();
        this.main_page_obj.load_page.start(main_page_obj.main_page_stage);
    }
    public void exitToMainPage() throws Exception {

        long to_set = main_page_obj.AssociatedMain.getGm().getNumStars() + main_page_obj.AssociatedMain.getGm().getCurrentGameState().getNumStarsinGame();
        main_page_obj.AssociatedMain.getGm().setNumStars(to_set);
//        main_page_obj.AssociatedMain.getGm().setNumStars();+=main_page_obj.AssociatedMain.getGm().getCurrentGameState().getNumStarsinGame();
//        for(Map.Entry<Integer,Achievement> t: main_page_obj.AssociatedMain.getGm().getGameAchievements().entrySet()) {
//            if (t.getValue().Requirement(main_page_obj.AssociatedMain.getGm().numStars)) {
//                t.getValue().Unlock=true;
//            }
//        }
        main_page_obj.AssociatedMain.getGm().setCurrentGameState(null);
        main_page_obj.start(main_page_obj.main_page_stage);
    }
    public void save_button_handle_function()throws Exception{
//        public void saveGame() throws Exception {
        System.out.println("GAME WILL BE SAVED !!");
//        game_main.savegame();
        game_main.Pause();
        game_main.getAssociatedMain().getMain_page().load_page.start(game_main.getAssociatedMain().getMain_page().main_page_stage);

    }

    public MainPageMenu getMain_page_obj() {
        return main_page_obj;
    }

    public void setMain_page_obj(MainPageMenu main_page_obj) {
        this.main_page_obj = main_page_obj;
    }

    public Stage getIn_game_stage() {
        return in_game_stage;
    }

    public void setIn_game_stage(Stage in_game_stage) {
        this.in_game_stage = in_game_stage;
    }

    public GameMain getGame_main() {
        return game_main;
    }

    public void setGame_main(GameMain game_main) {
        this.game_main = game_main;
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
