package sample;

import javafx.animation.*;
import javafx.scene.transform.Rotate;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.ArrayList;

import javafx.scene.shape.*;

public class SquareObstacle extends Obstacle {

    private double sideLength;
    private boolean directionClockwise;
    private double thickness;
    private Path squareObject;
    private ArrayList<Path> sides = new ArrayList<Path>();
    private ArrayList<Timeline> timelines = new ArrayList<Timeline>();
    private ArrayList<Rotate> rotate_list = new ArrayList<Rotate>();
    SquareObstacle(String type, double speed, int orientation, double centre_x, double centre_y, double side, double thickness, boolean isClockwise) {
        super(type, speed, orientation);
        this.setPosition(new Position(centre_x,centre_y));
        this.sideLength = side;
        this.thickness = thickness;
        this.directionClockwise = isClockwise;
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
        for(int i = 0 ; i < sides.size(); i++){
            g.getChildren().add(sides.get(i));
        }
    }

    public void rotateSquare(){
        for(int i = 0 ; i < timelines.size(); i++){
            timelines.get(i).play();
        }
    }

    @Override
    public void draw() {
        Path side1 = makeSide(1);
        Path side2 = makeSide(2);
        Path side3 = makeSide(3);
        Path side4 = makeSide(4);
        sides.add(side1); sides.add(side2); sides.add(side3); sides.add(side4);

        for(int i = 0; i < sides.size(); i++){
            Rotate r = new Rotate();
            r.setPivotX(this.getPosition().get_x());
            r.setPivotY(this.getPosition().get_y());
            timelines.add(new Timeline());
            rotate_list.add(r);
            sides.get(i).getTransforms().add(r);
        }
    }
    /*
        right side = side 1; bottom side = side 2; left side = side 3; upper side = side 4
     */
    public Path makeSide(int side_number){

        Position obstacleCentre = this.getPosition();
        Path path_to_add = new Path();
//        path_to_add.setStrokeWidth(this.thickness);
        double change_in_coordinate = (double)(sideLength / (double)(2));
        double startX = 0;
        double startY = 0;
        double HLine2 = 0;
        double HLine4 = 0;
        double VLine1 = 0;
        double VLine3 = 0;
        Color c = Color.PURPLE;

        if(side_number == 1){
            startX = obstacleCentre.get_x() + change_in_coordinate;
            startY = obstacleCentre.get_y() - change_in_coordinate;
            HLine4 = obstacleCentre.get_x() + change_in_coordinate + thickness;
            VLine1 = obstacleCentre.get_y() + change_in_coordinate;
            HLine2 = obstacleCentre.get_x() + change_in_coordinate;
            VLine3 = obstacleCentre.get_y() - change_in_coordinate;
            c = Color.PURPLE;
        }
        else if(side_number == 2){
            startX = obstacleCentre.get_x() - change_in_coordinate - thickness;
            startY = obstacleCentre.get_y() + change_in_coordinate;
            HLine4 = obstacleCentre.get_x() + change_in_coordinate + thickness;
            VLine1 = obstacleCentre.get_y() + change_in_coordinate + thickness;
            HLine2 = obstacleCentre.get_x() - change_in_coordinate - thickness;
            VLine3 = obstacleCentre.get_y() + change_in_coordinate;
            c = Color.YELLOW;
        }
        else if(side_number == 3){
            startX = obstacleCentre.get_x() - change_in_coordinate - thickness;
            startY = obstacleCentre.get_y() - change_in_coordinate;
            HLine4 = obstacleCentre.get_x() - change_in_coordinate;
            VLine1 = obstacleCentre.get_y() + change_in_coordinate;
            HLine2 = obstacleCentre.get_x() - change_in_coordinate - thickness;
            VLine3 = obstacleCentre.get_y() - change_in_coordinate;
            c = Color.CYAN;
        }
        else{
            startX = obstacleCentre.get_x() - change_in_coordinate - thickness;
            startY = obstacleCentre.get_y() - change_in_coordinate - thickness;
            HLine4 = obstacleCentre.get_x() + change_in_coordinate + thickness;
            VLine1 = obstacleCentre.get_y() - change_in_coordinate;
            HLine2 = obstacleCentre.get_x() - change_in_coordinate - thickness;
            VLine3 = obstacleCentre.get_y() - change_in_coordinate - thickness;
            c = Color.DEEPPINK;
        }
        MoveTo start_position = new MoveTo(startX,startY);
        HLineTo horizontal4 = new HLineTo(HLine4);
        HLineTo horizontal2 = new HLineTo(HLine2);
        VLineTo vertical1 = new VLineTo(VLine1);
        VLineTo vertical3 = new VLineTo(VLine3);

        path_to_add.getElements().addAll(start_position, horizontal4,vertical1,horizontal2, vertical3);
        path_to_add.setFill(c);
        return path_to_add;
    }
    @Override
    public boolean collisionCheck(Ball b)  {
        return true;
    }
    @Override
    public void movedown(Ball b) {

    }
}
