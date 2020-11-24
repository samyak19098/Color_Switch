package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

public class CrossObstacle extends Obstacle{

    private double length;
    private double thickness;
    private ArrayList<Path> bars = new ArrayList<Path>();
    private ArrayList<Timeline> timelines = new ArrayList<Timeline>();
    private ArrayList<Rotate> rotate_list = new ArrayList<Rotate>();
    private boolean directionClockwise;

    CrossObstacle(String type, double speed, int orientation, double length,double thickness, double centre_x, double centre_y, boolean direction){
        super(type, speed, orientation);
        this.setPosition(new Position(centre_x,centre_y));
        this.length = length;
        this.thickness = thickness;
        this.directionClockwise = direction;
    }
    @Override
    public void movedown(Ball b) {

    }

    @Override
    protected void WayOfMovement() {
        for(int i = 0 ; i < timelines.size(); i++){
            timelines.get(i).setCycleCount(Animation.INDEFINITE);
            int angle_to_cover;
            if(directionClockwise == true){
                angle_to_cover = 360;
            }
            else{
                angle_to_cover = -360;
            }
            timelines.get(i).getKeyFrames().add(new KeyFrame(Duration.millis((this.getObstacleSpeed())), new KeyValue(rotate_list.get(i).angleProperty(), angle_to_cover)));
        }
    }


    @Override
    public void shownOnScreen(Group g) {
        for(int i = 0 ; i < bars.size(); i++){
            g.getChildren().add(bars.get(i));
        }
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
    public boolean collisionCheck(Ball b) {
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
}
