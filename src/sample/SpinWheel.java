package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.util.concurrent.atomic.AtomicBoolean;


public class SpinWheel extends Application {

    MainPageMenu main_page_obj;
    Stage spin_wheel_stage;
    Stage mp_stage;

    public Path makeArrow(double centre_x, double centre_y, double thickness, double length, Color c){

        Path path = new Path();
        double delta_thick = (double)(thickness / (double)(2));
        double delta_len = (double)(length / (double)(2));
        double arrow_headwidth = delta_thick;
        MoveTo start_point = new MoveTo(centre_x - delta_thick, centre_y - delta_len);
        VLineTo vline1 = new VLineTo(centre_y + delta_len);
        HLineTo hline2 = new HLineTo(centre_x + delta_thick);
        VLineTo vline3 = new VLineTo(centre_y - delta_len);
        HLineTo hline4 = new HLineTo(centre_x + delta_thick + arrow_headwidth);
        LineTo  line5  = new LineTo(centre_x, centre_y - delta_len - (2 * delta_thick));
        LineTo  line6  = new LineTo(centre_x - delta_thick - arrow_headwidth , centre_y - delta_len);
        HLineTo hline7 = new HLineTo(centre_x - delta_thick);


        path.getElements().addAll(start_point, vline1,hline2, vline3, hline4, line5, line6, hline7);
        path.setFill(c);

        return path;
    }

    @Override
    public void start(Stage WheelStage) throws Exception {

        this.spin_wheel_stage = WheelStage;
        Group spin_wheel_group = new Group();





        ObservableList<PieChart.Data> chart_element =
                FXCollections.observableArrayList(
                        new PieChart.Data("5 STARS", 1),
                        new PieChart.Data("10 STARS", 1),
                        new PieChart.Data("15 STARS", 1),
                        new PieChart.Data("30 STARS JACKPOT", 1));
//                        new PieChart.Data("Apples", 30));
        PieChart wheel = new PieChart(chart_element);
        wheel.setLegendVisible(false);
        wheel.setLabelsVisible(false);
        wheel.setLayoutX(300);
        wheel.setLayoutY(100);
        wheel.setPrefSize(600, 600);


        Path p = makeArrow(600,410, 20, 200, Color.BLANCHEDALMOND);
        RotateTransition rot1 = new RotateTransition(Duration.millis(8000), p);
        rot1.setCycleCount(1);
        rot1.setByAngle(1800f +(1000f * Math.random()));
//        rot1.play();


        Text five_stars = new Text();
        five_stars.setText("5 Stars");
        five_stars.setLayoutX(400);
        five_stars.setLayoutY(270);
        five_stars.setFont(Font.font ("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 40));
        five_stars.setFill(Color.PURPLE);

        Text ten_stars = new Text();
        ten_stars.setText("10 Stars");
        ten_stars.setLayoutX(630);
        ten_stars.setLayoutY(270);
        ten_stars.setFont(Font.font ("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 40));
        ten_stars.setFill(Color.PURPLE);

        Text fifteen_stars = new Text();
        fifteen_stars.setText("15 Stars");
        fifteen_stars.setLayoutX(400);
        fifteen_stars.setLayoutY(570);
        fifteen_stars.setFont(Font.font ("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 40));
        fifteen_stars.setFill(Color.PURPLE);

        Text jackpot = new Text();
        jackpot.setText("30 Stars");
        jackpot.setLayoutX(630);
        jackpot.setLayoutY(570);
        jackpot.setFont(Font.font ("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 40));
        jackpot.setFill(Color.PURPLE);

        Image button_img = new Image(new FileInputStream("/Users/rohitritika/Desktop/AP_RESOURCE/spin_button.png"));
        ImageView spin_button_img = new ImageView(button_img);
        spin_button_img.setX(950);
        spin_button_img.setY(300);
        spin_button_img.setFitHeight(200);
        spin_button_img.setFitWidth(200);
        spin_button_img.setPreserveRatio(true);

        Text click_text = new Text();
        click_text.setText("Click to Spin");
        click_text.setLayoutX(980);
        click_text.setLayoutY(390);
        click_text.setFont(Font.font ("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        click_text.setFill(Color.WHITE);

        AtomicBoolean spin_done = new AtomicBoolean(false);
        spin_button_img.setOnMouseClicked(mouseEvent -> {

            if(spin_done.get() == false) {
                rot1.play();
                System.out.println("TO ANGLE " + rot1.getByAngle());
                System.out.println("TO ANGLE " + ( ((int)((rot1.getByAngle() - 1800) / 90)) % 4));
                int quarter_stopped = (((int)((rot1.getByAngle() - 1800) / 90)) % 4);

//                System.out.println("TO ANGLE " + ( ((int)((rot1.getByAngle() - 2000) / 360))) % 4);
                spin_done.set(true);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("NO SPIN");
                alert.setHeaderText(null);
                alert.setContentText("SORRY :( !! You have no spins left.");
                alert.showAndWait();
            }
        });



        spin_wheel_group.getChildren().add(wheel);
        spin_wheel_group.getChildren().addAll(p,five_stars,ten_stars,fifteen_stars,jackpot);
        spin_wheel_group.getChildren().addAll(spin_button_img, click_text);
//        spin_wheel_group.getChildren().add(imageView);
        Scene scene = new Scene(spin_wheel_group,1200,800, Color.BLACK);
        WheelStage.setScene(scene);
//        scene.setFill(Color.TRANSPARENT);
//        WheelStage.initStyle(StageStyle.TRANSPARENT);
        WheelStage.setTitle("Spin Wheel Page");
        WheelStage.show();


    }

    public static void main(String[] args){
        launch(args);
    }
}