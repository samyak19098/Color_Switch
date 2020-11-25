package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

public class CrossObstacle extends Obstacle implements Serializable {

    private double length;
    private double thickness;
    private transient ArrayList<Path> bars = new ArrayList<Path>();
    private transient ArrayList<Timeline> timelines = new ArrayList<Timeline>();
    private transient ArrayList<Rotate> rotate_list = new ArrayList<Rotate>();
    private boolean directionClockwise;
    private double saved_angle;

    CrossObstacle(String type, double speed, int orientation, double length,double thickness, double centre_x, double centre_y, boolean direction){
        super(type, speed, orientation);
        this.setPosition(new Position(centre_x,centre_y));
        this.length = length;
        this.thickness = thickness;
        this.directionClockwise = direction;
    }
    @Override
    public void movedown(Ball b) {
        Platform.runLater(() -> {
            for (int i = 0; i < 4; i++) {
                tlist.get(i).stop();
                tlist.get(i).setToY(Double.NaN);
                tlist.get(i).setByY(movedistance);

                //Setting the cycle count for the transition
                tlist.get(i).setCycleCount(1);
                tlist.get(i).setDuration(Duration.millis(movtime));
                //Setting auto reverse value to false
                // translateTransition.setAutoReverse(false);
//                        System.out.println("move down");
                int finalI = i;
                tlist.get(i).setOnFinished(new EventHandler<ActionEvent>() {//todo: dont create eventhandler  everytime
                    @Override
                    public void handle(ActionEvent t) {
                        tlist.get(finalI).setByY(0);
                        b.atend();
                    }
                });
                //Playing the animation

                tlist.get(i).play();
            }
        });
    }

    @Override
    protected void save_attributes() {

            this.saved_angle = rotate_list.get(0).getAngle();
        savedposition.set_x(bars.get(0).getTranslateX());
        savedposition.set_y(bars.get(0).getTranslateY());
    }

    @Override
    public void load_attributes(){
        bars = new ArrayList<Path>();
         timelines = new ArrayList<Timeline>();
         rotate_list = new ArrayList<Rotate>();
       draw();
       WayOfMovement();
        super.load_attributes();
        for(int i=0;i<4;i++) {
            tlist.get(i).setToX(savedposition.get_x());
            tlist.get(i).setToY(savedposition.get_y());
            tlist.get(i).setCycleCount(1);
            tlist.get(i).setDuration(Duration.millis(1));
            tlist.get(i).setOnFinished(null);
            tlist.get(i).play();
        }
        for(int i = 0 ; i  < 4; i++){
            rotate_list.get(i).setAngle(saved_angle);
        }
    }


    @Override
    protected void WayOfMovement() {
        for(int i = 0 ; i < timelines.size(); i++){
            timelines.get(i).setCycleCount(Animation.INDEFINITE);
            double angle_to_cover;
            if(directionClockwise == true){
                angle_to_cover = 360+saved_angle;
            }
            else{
                angle_to_cover = -360+saved_angle;
            }
            timelines.get(i).getKeyFrames().add(new KeyFrame(Duration.millis((this.getObstacleSpeed())), new KeyValue(rotate_list.get(i).angleProperty(), angle_to_cover)));
        }
    }


    @Override
    public void shownOnScreen(Group g) {
        Platform.runLater(() -> {
        for(int i = 0 ; i < bars.size(); i++){
            g.getChildren().add(bars.get(i));
        }
        });
    }

    @Override
    public void draw() {
        Path bar1 = makeBar(1);
        Path bar2 = makeBar(2);
        Path bar3 = makeBar(3);
        Path bar4 = makeBar(4);
        bars.add(bar1);bars.add(bar2);bars.add(bar3);bars.add(bar4);

        for(int i = 0 ; i < bars.size(); i++){
            Rotate r = new Rotate();
            r.setPivotX(this.getPosition().get_x());
            r.setPivotY(this.getPosition().get_y());
            timelines.add(new Timeline());
            rotate_list.add(r);
            bars.get(i).getTransforms().add(r);
            tlist.get(i).setNode(bars.get(i));
        }
    }

    public void rotateCross(){
        for(int i = 0 ; i < timelines.size(); i++){
            timelines.get(i).play();
        }
    }
    public Path makeBar(int rect_number){

        Position obstacleCentre = this.getPosition();
        Path path_to_add = new Path();
        Color c = Color.WHITE;
        double change_in_coordinates = (double)(thickness /  (double)(2));
        double startX = 0;
        double startY = 0;
        double HLine2 = 0;
        double HLine4 = 0;
        double VLine1 = 0;
        double VLine3 = 0;
        if(rect_number == 1){
            startX = obstacleCentre.get_x() - change_in_coordinates;
            startY = obstacleCentre.get_y() - change_in_coordinates;
            VLine1 = obstacleCentre.get_y() - change_in_coordinates - length;
            HLine2 = obstacleCentre.get_x() + change_in_coordinates;
            VLine3 = obstacleCentre.get_y() - change_in_coordinates;
            HLine4 = obstacleCentre.get_x() - change_in_coordinates;
            c = Color.PURPLE;
        }

        else if(rect_number == 2){
            startX = obstacleCentre.get_x() + change_in_coordinates;
            startY = obstacleCentre.get_y() - change_in_coordinates;
            VLine1 = obstacleCentre.get_y() + change_in_coordinates;
            HLine2 = obstacleCentre.get_x() + change_in_coordinates + length;
            VLine3 = obstacleCentre.get_y() - change_in_coordinates;
            HLine4 = obstacleCentre.get_x() + change_in_coordinates;
            c = Color.YELLOW;
        }

        else if(rect_number == 3){
            startX = obstacleCentre.get_x() + change_in_coordinates;
            startY = obstacleCentre.get_y() + change_in_coordinates;
            VLine1 = obstacleCentre.get_y() + change_in_coordinates + length;
            HLine2 = obstacleCentre.get_x() - change_in_coordinates;
            VLine3 = obstacleCentre.get_y() + change_in_coordinates;
            HLine4 = obstacleCentre.get_x() + change_in_coordinates;
            c = Color.CYAN;
        }

        else if(rect_number == 4){
            startX = obstacleCentre.get_x() - change_in_coordinates;
            startY = obstacleCentre.get_y() - change_in_coordinates;
            VLine1 = obstacleCentre.get_y() + change_in_coordinates;
            HLine2 = obstacleCentre.get_x() - change_in_coordinates - length;
            VLine3 = obstacleCentre.get_y() - change_in_coordinates;
            HLine4 = obstacleCentre.get_x() - change_in_coordinates;
            c = Color.DEEPPINK;
        }

        MoveTo start_position = new MoveTo(startX,startY);
        HLineTo horizontal4 = new HLineTo(HLine4);
        HLineTo horizontal2 = new HLineTo(HLine2);
        VLineTo vertical1 = new VLineTo(VLine1);
        VLineTo vertical3 = new VLineTo(VLine3);

        path_to_add.getElements().addAll(start_position, vertical1, horizontal2, vertical3, horizontal4);
        path_to_add.setFill(c);
        return path_to_add;
    }
    @Override
    public void Resume(){
        Platform.runLater(() -> {
            rotateCross();
            for (int i = 0; i < tlist.size(); i++) {

                tlist.get(i).play();
            }
        });

    }
    @Override
    public void Pause(){
        Platform.runLater(() -> {
            for (int i = 0; i < timelines.size(); i++) {
                timelines.get(i).pause();
                tlist.get(i).pause();
            }
//            for (int i = 0; i < rotate_list.size(); i++) {
//                System.out.println("angle of ring " + (i));
//                System.out.println(":" + rotate_list.get(i).getAngle());
//            }
        });

    }
    @Override
    public boolean collisionCheck(Ball b) {
        //returns true  if collision occurs
        //otherwise false
        int i = -1;
        //System.out.println("will do");
//        System.out.println("b.getShape().getTranslateX():" + b.getBallShape().getTranslateX());
//        System.out.println("b.getShape().getTranslateY():" + b.getBallShape().getTranslateY());
//        System.out.println("b.getPosition().get_x():" + b.getPosition().get_x());
//        System.out.println("b.getPosition().get_y():" + b.getPosition().get_y());
//        System.out.println("b.getPosition().getRadius():" + b.getRadius());

        //matching color
        for (i = 0; i < 4; i++) {
            if (b.getColor() == bars.get(i).getFill()) {
                break;
            }
        }
//        System.out.println("i:" + i);
//        System.out.println(b.getColor() == quarters.get(i).getFill());
        double ang = rotate_list.get(i).getAngle();
//        System.out.println("ang:" + ang);
        //cal=distance b/w centers
        double cal = b.getPosition().get_y() + b.getBallShape().getTranslateY() - position.get_y() - bars.get(i).getTranslateY();
        double calx=Math.abs(position.get_x()-b.getPosition().get_x());
        //        System.out.println("quarters.get(i).getTranslateY():" + quarters.get(i).getTranslateY());
//        System.out.println("cal:" + cal);
        ang += (i * 90);
        ang = adjust(ang);
        double ang2=ang%90;
//        double ang3=ang%90;
//        System.out.println("cal:" + cal);
//        System.out.println("ang2:" + ang2);
//        System.out.println("Math.atan(calx/cal):" + Math.atan(calx/cal));
//        System.out.println("Math.asin(b.getRadius()/(calx)):" + Math.asin(b.getRadius()/(calx)));
        if (cal > 0){//ball is at bottom{

                if (   Math.abs(Math.atan(calx/cal)-Math.toRadians(ang2))<=Math.asin(b.getRadius()/(calx)) && (((cal*cal) + (calx*calx))<=(length*length) ) ){
                    if(180<=ang && ang<=270) {
                        return false;
                    }
                    else
                        return true;
                }

            }
        else{
            cal=-cal;
            if (   Math.abs(Math.atan(calx/cal)-Math.toRadians(90-ang2))<=Math.asin(b.getRadius()/(calx)) && (((cal*cal) + (calx*calx))<=(length*length) ) ){
                if(270<=ang && ang<=360) {
                    return false;
                }
                else
                    return true;
            }



        }





        return false;
    }
    public double effectiveside(double ang,double sidel){


        return sidel/(2* Math.cos( Math.toRadians(ang-(90*(Math.ceil((ang-45)/90))) )) );
    }
    @Override
    public void removeself(Group grp){
        for(Path p: bars) {
            Platform.runLater(() -> {
                p.setVisible(false);
                grp.getChildren().remove(p);
            });
        }
//        System.out.println("notvisible");
    }
    @Override
    public boolean outofBounds(){
        if((position.get_y()+bars.get(0).getTranslateY()-length)>screenheight)
            return true;
        return false;
    }

    public double getLength() {
        return length;
    }

    public double getThickness() {
        return thickness;
    }

    public ArrayList<Path> getBars() {
        return bars;
    }

    public ArrayList<Timeline> getTimelines() {
        return timelines;
    }

    public ArrayList<Rotate> getRotate_list() {
        return rotate_list;
    }

    public boolean isDirectionClockwise() {
        return directionClockwise;
    }

    public double getSaved_angle() {
        return saved_angle;
    }

}
