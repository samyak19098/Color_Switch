package sample;

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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.effect.DropShadow;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopPage extends Application{
    protected static  double screenheight=800;
    private static double screenwidth = 1200;
    public HashMap<Integer,Achievement> OwnedTrails;
    private static final String HOVERED_BUTTON_STYLE = "-fx-foreground-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
    MainPageMenu main_page_obj;
    GameMain gm;
    Text totalstars;
    Alert alert;
    Stage shop_page_stage;
    Stage mp_stage;
    Group help_page_group;
    ArrayList<RadioButton> whichtrail;
    ToggleGroup selectedtrail;

    public ShopPage(GameMain oldgm){
        gm=oldgm;
        selectedtrail=new ToggleGroup();
        whichtrail=new ArrayList<>();
        for(int i=0;i<4;i++){
            whichtrail.add(new RadioButton());
            whichtrail.get(i).setToggleGroup(selectedtrail);
//            int finalI = i;
//            whichtrail.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent mouseEvent) {
//                    if( whichtrail.get(finalI).isSelected()) {
//                        whichtrail.get(finalI).setSelected(false);
//                        for(int i=0;i<4;i++){
//                            if(i)
//                        }
//                    }
//                    else{
//                        whichtrail.get(finalI).setSelected(true);
//                    }
//                    System.out.println("aaa");
//                }
//            });

//            whichtrail.get(i).setLayoutX();
//            whichtrail.get(i).setLayoutY();

        }
        whichtrail.get(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                gm.setCurrentTrail(null);
                System.out.println("bbb");
            }
        });
        whichtrail.get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                gm.setCurrentTrail(new Trail());
                System.out.println("bbb");
            }
        });
        whichtrail.get(2).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                gm.setCurrentTrail(new Firetrail());
                System.out.println("bbb");
            }
        });
        whichtrail.get(3).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                gm.setCurrentTrail(new Neontrail());
                System.out.println("bbb");
            }
        });
        whichtrail.get(0).fire();

        totalstars =new Text();
        totalstars.setLayoutX(30);
        totalstars.setLayoutY(screenheight/4);
        totalstars.setFill(Color.WHITESMOKE);

        totalstars.setStyle("-fx-text-shadow: 5px 3px 2px  ;  -fx-font-weight: bold; -fx-font-size:38; ");
        alert = new Alert(Alert.AlertType.INFORMATION,"Stars not Sufficient");

        OwnedTrails=new HashMap<>();
        Achievement a=new Achievement();
        System.out.println("gm"+gm);
        System.out.println("gameDetails"+gm.getGameDetails());
        a.setUnlock(gm.getGameDetails().getTrailsunlocked().get(0));
        Achievement finalA0 = a;
        a.text.setText("No trail");
        OwnedTrails.put(0, a);
        Image im = new Image("file:notrail.png",false);
        a.getUnlocked().setFill(new ImagePattern(im));
        a.getUnlocked().setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                whichtrail.get(0).fire();
                MainPageMenu.mp_button.stop();MainPageMenu.mp_button.play();
                gm.setCurrentTrail(null);
            }
        });
        a.setUnlock(true);a.text.setPrefColumnCount(10);a.text.setStyle(" -fx-font-weight: bold; -fx-font-size:20;");
        whichtrail.get(0).fire();

        a=new Achievement();
        a.setUnlock(gm.getGameDetails().getTrailsunlocked().get(1));
        a.requirednumber=5;
        a.text.setText("Generating mist in the space with dash of the ball. Cost " + a.requirednumber+" stars");
        im = new Image("file:greytrail.jpg",false);
        a.getUnlocked().setFill(new ImagePattern(im));
        a.getUnlocked().setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                Platform.runLater(() -> {
                whichtrail.get(1).fire();
                MainPageMenu.mp_button.stop();MainPageMenu.mp_button.play();
                gm.setCurrentTrail(new Trail());
                });
            }
        });
        Achievement finalA = a;

        a.getLocked().setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                Platform.runLater(() -> {
                    MainPageMenu.mp_button.stop();
                    MainPageMenu.mp_button.play();
                    if (finalA.requirednumber <= gm.getNumStars()) {

                        long to_set = gm.getNumStars() - finalA.requirednumber;
                        gm.setNumStars(to_set);
//                    gm.numStars-= finalA.requirednumber;
//                    finalA.Unlock=true;
                        finalA.setUnlock(true);
                        help_page_group.getChildren().remove(finalA.getLocked());
                        help_page_group.getChildren().add(finalA.getUnlocked());
                        totalstars.setText("Your Stars:" + main_page_obj.AssociatedMain.getGm().getNumStars());
                        whichtrail.get(1).setDisable(false);
                    } else {
                        alert.show();
                    }
                });
            }
        });
                OwnedTrails.put(1, a);a.text.setPrefColumnCount(30);

        a=new Achievement();
        a.setUnlock(gm.getGameDetails().getTrailsunlocked().get(2));
        Achievement finalA1 = a;
        a.requirednumber=5;
        a.text.setText("Fire Balls signifying the rage of the ball. Cost " + a.requirednumber+" stars");
        im = new Image("file:firetrail2.jpg",false);
        a.getUnlocked().setFill(new ImagePattern(im));
        a.getUnlocked().setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                Platform.runLater(() -> {
                    whichtrail.get(2).fire();
                    MainPageMenu.mp_button.stop();
                    MainPageMenu.mp_button.play();
                    gm.setCurrentTrail(new Firetrail());
                });
            }
        });
        a.getLocked().setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                Platform.runLater(() -> {
                    MainPageMenu.mp_button.stop();
                    MainPageMenu.mp_button.play();
                    if (finalA1.requirednumber <= gm.getNumStars()) {
                        long to_set = gm.getNumStars() - finalA1.requirednumber;
                        gm.setNumStars(to_set);
//                    gm.numStars-= finalA1.requirednumber;
                        finalA1.setUnlock(true);
                        help_page_group.getChildren().remove(finalA1.getLocked());
                        help_page_group.getChildren().add(finalA1.getUnlocked());
                        totalstars.setText("Your Stars:" + main_page_obj.AssociatedMain.getGm().getNumStars());
                        whichtrail.get(2).setDisable(false);
                    } else {
                        alert.show();
                    }
                });

            }
        });
        OwnedTrails.put(2, a);a.text.setPrefColumnCount(30);//a.text.setStyle(" -fx-font-weight: bold; -fx-font-size:15;");

        a=new Achievement();
        a.setUnlock(gm.getGameDetails().getTrailsunlocked().get(3));
        Achievement finalA2 = a;
        a.requirednumber=5;
        a.text.setText("Laser like trail. Similar to nitro boost in race cars. Cost " + a.requirednumber+" stars");
        im = new Image("file:neontrail.jpg",false);
        a.getUnlocked().setFill(new ImagePattern(im));
        a.getUnlocked().setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                Platform.runLater(() -> {
                    whichtrail.get(3).fire();
                    MainPageMenu.mp_button.stop();
                    MainPageMenu.mp_button.play();
                    gm.setCurrentTrail(new Neontrail());
                });
            }
        });
        a.getLocked().setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                Platform.runLater(() -> {
                    MainPageMenu.mp_button.stop();
                    MainPageMenu.mp_button.play();
                    if (finalA2.requirednumber <= gm.getNumStars()) {
                        long to_set = gm.getNumStars() - finalA2.requirednumber;
                        gm.setNumStars(to_set);
//                    gm.numStars-= finalA2.requirednumber;
                        finalA2.setUnlock(true);
                        help_page_group.getChildren().remove(finalA2.getLocked());
                        help_page_group.getChildren().add(finalA2.getUnlocked());
                        totalstars.setText("Your Stars:" + main_page_obj.AssociatedMain.getGm().getNumStars());
                        whichtrail.get(3).setDisable(false);

                    } else {
                        alert.show();
                    }
                });

            }
        });
        OwnedTrails.put(3, a);a.text.setPrefColumnCount(30);//a.text.setStyle(" -fx-font-weight: bold; -fx-font-size:15;");

    }


    @Override
    public void start(Stage HelpStage) throws Exception {
        totalstars.setText("Your Stars:"+main_page_obj.AssociatedMain.getGm().getNumStars());
        this.shop_page_stage = HelpStage;
          help_page_group= new Group();


        Image how_to_play_image = new Image(new FileInputStream("shop.png"));
        ImageView imageView = new ImageView(how_to_play_image);
        imageView.setX(485);
        imageView.setY(120);
        imageView.setFitHeight(180);
        imageView.setFitWidth(230);
        imageView.setPreserveRatio(true);

        Image x = new Image(new FileInputStream("astronaut.jpg"));
        ImageView astronaut = new ImageView(x);
        astronaut.setX(100);
        astronaut.setY(screenheight/4);

       x= new Image(new FileInputStream("dragonballs.jpg"));
        ImageView dragonball = new ImageView(x);
        dragonball.setX(screenwidth*0.75);
        dragonball.setY(screenheight/4);




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
        for(Map.Entry<Integer,Achievement> t:  OwnedTrails.entrySet()) {

            if(t.getValue().getUnlock()) {
                tmp = t.getValue().getUnlocked();
                whichtrail.get(i).setDisable(false);
            }
            else {
                tmp = t.getValue().getLocked();
                whichtrail.get(i).setDisable(true);
            }
            whichtrail.get(i).setLayoutX(  t.getValue().getUnlocked().getLayoutX()-20);
            whichtrail.get(i).setLayoutY(400+(i*tmp.getHeight()));
            t.getValue().getUnlocked().setLayoutY(380+(i*tmp.getHeight()));
            t.getValue().getLocked().setLayoutY(380+(i*tmp.getHeight()));
//            System.out.println("get:"+tmp.getHeight());
            //tmp.setLayoutY(380+(i*tmp.getHeight()));
            help_page_group.getChildren().add(tmp);
            t.getValue().text.setLayoutY(380+(i*tmp.getHeight()));
            help_page_group.getChildren().add(t.getValue().text);
            i+=1;
        }
//        help_page_group.getChildren().add(text);
        for(int j=0;j<whichtrail.size();j++){
            help_page_group.getChildren().add(whichtrail.get(j));
        }
        help_page_group.getChildren().addAll(imageView,home_button,totalstars,astronaut,dragonball);
//        help_page_group.getChildren().add();

        Scene scene = new Scene(help_page_group,1200,800, Color.BLACK);
        HelpStage.setScene(scene);
        HelpStage.setTitle("Main Page Menu");
        HelpStage.show();

        EventHandler<ActionEvent> event_back_to_home = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("BUTTON 1 PRESSED");
                try {
                    Platform.runLater(() -> {
                                MainPageMenu.mp_button.stop();
                                MainPageMenu.mp_button.play();
                            });
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
    public HashMap<Integer, Achievement> getOwnedTrails() {
        return OwnedTrails;
    }
    public static void main(String[] args) {
        launch(args);
    }

}

