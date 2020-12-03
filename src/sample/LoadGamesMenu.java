package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class LoadGamesMenu extends Application{
    protected static  double screenheight=800;
    private static double screenwidth = 1200;
    MainPageMenu main_page_obj;
    GameMain gm;
    Stage loading_page_stage;
    Stage mp_stage;
    ListView games_list;

    LoadGamesMenu(ArrayList<String> names){
        games_list = new ListView();
        System.out.println("names.get(0) "+names.get(0));
//        if(names!=null) {
            for (int i = 0; i < 5; i++)
                games_list.getItems().add(names.get(i));
//        }
//        else{
//            for (int i = 0; i < 5; i++)
//                games_list.getItems().add("Empty Slot");
//        }
    }


    @Override
    public void start(Stage LoadStage) throws Exception {
        this.loading_page_stage = LoadStage;
        Group load_group = new Group();

        Image load_im = new Image(new FileInputStream("load_game.png"));
        ImageView load_image = new ImageView(load_im);
        load_image.setX(435);
        load_image.setY(120);
        load_image.setFitHeight(300);
        load_image.setFitWidth(400);
        load_image.setPreserveRatio(true);

        ListView list = this.games_list;
        Button load_button = new Button("LOAD GAME");

        load_button.setOnAction(event -> {
            MainPageMenu.mp_button.stop();MainPageMenu.mp_button.play();
            ObservableList selectedIndices = list.getSelectionModel().getSelectedIndices();
            for(Object o : selectedIndices){
                int slot_select = (int)(o);
                System.out.println(list.getItems().get((int)(o)));
                try {
                    load_game(slot_select);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        load_button.setLayoutX(470);
        load_button.setLayoutY(570);
        load_button.setPrefSize(150,50);

        Button save_button = new Button("SAVE GAME");

        save_button.setOnAction(event -> {
            ObservableList selectedIndices = list.getSelectionModel().getSelectedIndices();
            for(Object o : selectedIndices){
                int slot_select = (int)(o);

                System.out.println(list.getItems().get((int)(o)));
                try {
                    MainPageMenu.mp_button.stop();MainPageMenu.mp_button.play();
                    save_game_implementation(slot_select);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        save_button.setLayoutX(650);
        save_button.setLayoutY(570);
        save_button.setPrefSize(150,50);

        list.setLayoutX(400);
        list.setLayoutY(300);
        list.setPrefSize(500,238);
        list.setStyle("-fx-font-size: 24px; -fx-font-family: 'Bradley Hand'");




        //home button
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

        load_group.getChildren().addAll(load_image, list, load_button, save_button, home_button);

        Scene scene = new Scene(load_group,1200,800, Color.BLACK);
        LoadStage.setScene(scene);
        LoadStage.setTitle("Main Page Menu");
        LoadStage.show();


    }


    public void save_game_implementation(int slot) throws Exception {
            if(this.gm.getCurrentGameState()!=null) {
//            System.out.println("Your name: " + name_string.get());
                games_list.getItems().set(slot, this.gm.getCurrentGameState().getPlayer_name());
                this.gm.savegame(slot + 1);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Proceeding Choices");
                alert.setHeaderText("Game saved succesfully !");
                alert.setContentText("Choose where to proceed :");
            alert.initStyle(StageStyle.UNDECORATED);
                ButtonType continue_button = new ButtonType("Resume Game");
                ButtonType main_menu_button = new ButtonType("Exit to Main Menu");
//            ButtonType buttonTypeThree = new ButtonType("Three");
//                ButtonType cancel_button = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(continue_button, main_menu_button);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == continue_button) {
                    this.gm.continueGame();
                    this.gm.getAssociatedMain().getMainStage().setScene(gm.getGm_scene());
                    this.gm.getAssociatedMain().getMainStage().show();
//                    this.gm.AssociatedMain.getMainStage().setScene(gm.getGm_scene());
//                    this.gm.AssociatedMain.getMainStage().show();
                } else if (result.get() == main_menu_button) {

                    long to_set = this.main_page_obj.AssociatedMain.getGm().getNumStars() + main_page_obj.AssociatedMain.getGm().getCurrentGameState().getNumStarsinGame();
                    this.main_page_obj.AssociatedMain.getGm().setNumStars(to_set);

//                    this.main_page_obj.AssociatedMain.getGm().numStars += main_page_obj.AssociatedMain.getGm().getCurrentGameState().getNumStarsinGame();
                    main_page_obj.AssociatedMain.getGm().setCurrentGameState(null);
                    this.main_page_obj.start(main_page_obj.main_page_stage);
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("No game to save");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();

            }
        }



    public void load_game(int slot) throws IOException, ClassNotFoundException {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("CONFIRM LOAD");
//        alert.setHeaderText("LOAD GAME CONFIRMATION");
//        alert.setContentText("Do you want to load the selected game ? You will lose any unsaved progress.");
//
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.get() == ButtonType.OK){
        System.out.println(games_list.getItems().get(slot));
        if(((String)games_list.getItems().get(slot)).equals("Empty Slot")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("No saved game present at the slot");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        }
        else{
            this.gm.loadgame(this.main_page_obj.main_page_stage, slot + 1);
        }
    }

    public void backToHome() throws Exception {
        if(main_page_obj.AssociatedMain.getGm().getCurrentGameState()!=null) {
            long to_set = this.main_page_obj.AssociatedMain.getGm().getNumStars() + main_page_obj.AssociatedMain.getGm().getCurrentGameState().getNumStarsinGame();
            this.main_page_obj.AssociatedMain.getGm().setNumStars(to_set);
//            this.main_page_obj.AssociatedMain.getGm().numStars += main_page_obj.AssociatedMain.getGm().getCurrentGameState().getNumStarsinGame();
            main_page_obj.AssociatedMain.getGm().setCurrentGameState(null);
        }
        main_page_obj.start(mp_stage);
    }
    public static void main(String[] args) {
        launch(args);
    }
}

