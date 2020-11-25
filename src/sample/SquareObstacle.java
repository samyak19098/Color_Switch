package sample;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.transform.Rotate;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.shape.*;

public class SquareObstacle extends Obstacle  implements Serializable {

    private double sideLength;
    private boolean directionClockwise;
    private double thickness;
    private transient Path squareObject;
    private transient ArrayList<Path> sides = new ArrayList<Path>();
    private transient ArrayList<Timeline> timelines = new ArrayList<Timeline>();
    private transient ArrayList<Rotate> rotate_list = new ArrayList<Rotate>();
    private double saved_angle;

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
            tlist.get(i).setNode(sides.get(i));
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
            c = Color.DEEPPINK;
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
            c = Color.YELLOW;
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
    public void movedown(Ball b) {
//        System.out.println("sq move down");
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
                    int finalI=i;
                    tlist.get(i).setOnFinished(new EventHandler<ActionEvent>() {//todo: dont create eventhandler  everytime
                        @Override
                        public void handle(ActionEvent t) {
                            tlist.get(finalI).setByY(0); b.atend();
                        }
                    });
                    //Playing the animation

                    tlist.get(i).play();
                }
            });
//        collisionCheck(b);

    }

    @Override
    protected void save_attributes(){
            this.saved_angle = rotate_list.get(0).getAngle();
            savedposition.set_x(sides.get(0).getTranslateX());
            savedposition.set_y(sides.get(0).getTranslateY());
    }

    @Override
    public void load_attributes(){
        sides = new ArrayList<Path>();
        timelines = new ArrayList<Timeline>();
     rotate_list = new ArrayList<Rotate>();
        super.load_attributes();
        draw();
        WayOfMovement();
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
    public void removeself(Group grp){
        for(Path p: sides) {
            Platform.runLater(() -> {
                p.setVisible(false);
                grp.getChildren().remove(p);
            });
        }
        System.out.println("notvisible");
    }
    @Override
    public boolean outofBounds(){
        if((position.get_y()+sides.get(0).getTranslateY()-(sideLength*0.707))>screenheight)
            return true;
        return false;
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
    public void Resume(){
        Platform.runLater(() -> {
            rotateSquare();
            for (int i = 0; i < tlist.size(); i++) {

                tlist.get(i).play();
            }
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
            if (b.getColor() == sides.get(i).getFill()) {
                break;
            }
        }
//        System.out.println("i:" + i);
//        System.out.println(b.getColor() == quarters.get(i).getFill());
        double ang = rotate_list.get(i).getAngle();
//        System.out.println("ang:" + ang);
        //cal=distance b/w centers
        double cal = b.getPosition().get_y() + b.getBallShape().getTranslateY() - position.get_y() - sides.get(i).getTranslateY();
//        System.out.println("quarters.get(i).getTranslateY():" + quarters.get(i).getTranslateY());
//        System.out.println("cal:" + cal);
        ang += (i * 90);
        ang = adjust(ang);
//        System.out.println("ang2:" + ang);
        //So that angle is b/w 0 and 360

//        System.out.println("ang3:" + ang);
        if (cal > 0) {//when ball is near bottom of obstacle
//            System.out.println("  (cal):" +  (cal));
//            System.out.println("sideLength:" +(sideLength));
//            System.out.println("(effectiveside(ang,sideLength)):" +(effectiveside(ang,sideLength)));
//            System.out.println("b.getRadius():" + b.getRadius());
//
//            System.out.println("(effectiveside(ang,sideLength+thickness):" +effectiveside(ang,sideLength+thickness));
//                    System.out.println("2.2:" +(radius + b.getRadius()+width));

                if ((((effectiveside(ang,sideLength)) - b.getRadius()) <= (cal)) && (cal <= ((effectiveside(ang,sideLength+thickness)) + b.getRadius()))){
//                    System.out.println("ang1:" + ang);
                    if (((45) <= ang) && (ang < 135))//same color
                        return false;
                else
                    return true;//different color
            }
        }
        if (cal < 0) {//when ball is near top of obstacle
            if ((((effectiveside(ang,sideLength)) - b.getRadius()) <= (-cal)) && ((-cal) <= ((effectiveside(ang,sideLength+thickness)) + b.getRadius() ))) {
//                System.out.println("ang2:" + ang);
                if (((225) <= ang) && (ang < 315))
                    return false;
                else
                    return true;
            }
        }
        //System.out.println("b.getShape().getTranslateY():"+b.getBallShape().getr());
        return false;
    }
    public double effectiveside(double ang,double sidel){


        return sidel/(2* Math.cos( Math.toRadians(ang-(90*(Math.ceil((ang-45)/90))) )) );
    }


    public double getSideLength() {
        return sideLength;
    }

    public boolean isDirectionClockwise() {
        return directionClockwise;
    }

    public double getThickness() {
        return thickness;
    }

    public Path getSquareObject() {
        return squareObject;
    }

    public ArrayList<Path> getSides() {
        return sides;
    }

    public ArrayList<Timeline> getTimelines() {
        return timelines;
    }

    public ArrayList<Rotate> getRotate_list() {
        return rotate_list;
    }

    public double getSaved_angle() {
        return saved_angle;
    }

}
