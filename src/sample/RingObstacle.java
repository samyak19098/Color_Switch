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

public class RingObstacle extends Obstacle{

    /*
    radius - radius of the ring obstacle
    width - thickness of the ring obstacle
    list quarters : list of all the 4 quarters of the ring; the i'th element of the list represents the (i + 1)'th quarter where 0 <= i <= 3
    list timelines and rotate : list of timeline and rotate objects of the corresponding quarters; indexing and quarter number are related in the same way as above
    direction : represents direction of rotation ; clockwise = true & anticlockwise = false
    */
    private double radius;
    private double width;
    private ArrayList<Path> quarters = new ArrayList<Path>();
    private ArrayList<Timeline> timelines = new ArrayList<Timeline>();
    private ArrayList<Rotate> rotate_list = new ArrayList<Rotate>();
    private boolean directionClockwise;

    RingObstacle(String type, double speed, int orientation, double radius, double width, double centre_x, double centre_y, boolean direction){
        super(type,speed,orientation);
        this.setPosition(new Position(centre_x,centre_y));
        this.radius = radius;
        this.width = width;
        this.directionClockwise = direction;
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

    public void rotateRing(){
        for(int i = 0 ; i < timelines.size(); i++){
            timelines.get(i).play();
        }
    }

    @Override
    public void showOnScreen(Group g) {
        for(int i = 0 ; i < quarters.size(); i++){
            g.getChildren().add(quarters.get(i));
        }
    }

    @Override
    public void draw() {

        //Making the four quarters one by one
        Path quarter1 = makeQuarter(1);
        Path quarter2 = makeQuarter(2);
        Path quarter3 = makeQuarter(3);
        Path quarter4 = makeQuarter(4);

        //adding them to the list of quarters
        quarters.add(quarter1);quarters.add(quarter2);quarters.add(quarter3);quarters.add(quarter4);

        /* Making the timelines for each quarter and also the corresponding rotate object ;  the pivots of rotate object are set to the centre coordinates of the obstacle */
        for(int i = 0; i < quarters.size(); i++){
            Rotate r = new Rotate();
            r.setPivotX(this.getPosition().get_x());
            r.setPivotY(this.getPosition().get_y());
            timelines.add(new Timeline());
            rotate_list.add(r);
            quarters.get(i).getTransforms().add(r);
        }

    }

    /*
    This function returns a Path type object which represents a particular quarter of the Ring as specified by the quarter number in th argument
     quarter_numbers are as follows : Quarter (12 to 3) - 1 , (3 - 6) - 2, (6 - 9) - 3, (9 - 12)- 4
    */
    public Path makeQuarter(int quarter_number){

        Position obstacleCentre = this.getPosition();
        ArcTo innerArc = new ArcTo();
        ArcTo outerArc = new ArcTo();
        Path path_to_add = new Path();
        double startX,startY,HLineX,VLineY,outerArcX,outerArcY,innerArcX,innerArcY;
        Color c;
        if (quarter_number == 1) {
            startX = obstacleCentre.get_x();
            startY = obstacleCentre.get_y() - radius;
            innerArcX = obstacleCentre.get_x() + radius;
            innerArcY = obstacleCentre.get_y();
            HLineX = obstacleCentre.get_x() + radius + width;
            VLineY = obstacleCentre.get_y() - radius;
            outerArcX = obstacleCentre.get_x();
            outerArcY = obstacleCentre.get_y() - radius - width;
            innerArc.setSweepFlag(true);
            c = Color.PURPLE;
        } else if (quarter_number == 2) {
            startX = obstacleCentre.get_x();
            startY = obstacleCentre.get_y() + radius;
            innerArcX = obstacleCentre.get_x() + radius;
            innerArcY = obstacleCentre.get_y();
            HLineX = obstacleCentre.get_x() + radius + width;
            VLineY = obstacleCentre.get_y() + radius;
            outerArcX = obstacleCentre.get_x();
            outerArcY = obstacleCentre.get_y() + radius + width;
            outerArc.setSweepFlag(true);
            c = Color.DEEPPINK;
        } else if (quarter_number == 3) {
            startX = obstacleCentre.get_x();
            startY = obstacleCentre.get_y() + radius;
            innerArcX = obstacleCentre.get_x() - radius;
            innerArcY = obstacleCentre.get_y();
            HLineX = obstacleCentre.get_x() - radius - width;
            VLineY = obstacleCentre.get_y() + radius;
            outerArcX = obstacleCentre.get_x();
            outerArcY = obstacleCentre.get_y() + radius + width;
            innerArc.setSweepFlag(true);
            c = Color.CYAN;
        } else {
            startX = obstacleCentre.get_x();
            startY = obstacleCentre.get_y() - radius;
            innerArcX = obstacleCentre.get_x() - radius;
            innerArcY = obstacleCentre.get_y();
            HLineX = obstacleCentre.get_x() - radius - width;
            VLineY = obstacleCentre.get_y() - radius;
            outerArcX = obstacleCentre.get_x();
            outerArcY = obstacleCentre.get_y() - radius - width;
            outerArc.setSweepFlag(true);
            c = Color.YELLOW;
        }

        MoveTo start_position = new MoveTo(startX,startY);
        innerArc.setX(innerArcX);
        innerArc.setY(innerArcY);
        outerArc.setX(outerArcX);
        outerArc.setY(outerArcY);
        innerArc.setRadiusX(radius);
        innerArc.setRadiusY(radius);
        outerArc.setRadiusX(radius + width);
        outerArc.setRadiusY(radius + width);
        VLineTo vertical_line = new VLineTo(VLineY);
        HLineTo horizontal_line = new HLineTo(HLineX);
        path_to_add.getElements().addAll(start_position,innerArc,horizontal_line,outerArc,vertical_line);
        path_to_add.setFill(c);

        return path_to_add;
    }

    public ArrayList<Path> getQuarters() {
        return quarters;
    }

    public ArrayList<Timeline> getTimelines() {
        return timelines;
    }

    public ArrayList<Rotate> getRotate_list() {
        return rotate_list;
    }

}
