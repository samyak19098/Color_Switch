package sample;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.transform.Rotate;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.shape.*;

public class RingObstacle extends Obstacle  implements Serializable {

    /*
    radius - radius of the ring obstacle
    width - thickness of the ring obstacle
    list quarters : list of all the 4 quarters of the ring; the i'th element of the list represents the (i + 1)'th quarter where 0 <= i <= 3
    list timelines and rotate : list of timeline and rotate objects of the corresponding quarters; indexing and quarter number are related in the same way as above
    direction : represents direction of rotation ; clockwise = true & anticlockwise = false
    */
    private double radius;
    private double width;
    private double x;
    private transient ArrayList<Path> quarters = new ArrayList<Path>();
    private transient ArrayList<Timeline> timelines = new ArrayList<Timeline>();
    private transient ArrayList<Rotate> rotate_list = new ArrayList<Rotate>();
    private boolean directionClockwise;
    private double saved_angle;

    RingObstacle(String type, double speed, int orientation, double radius, double width, double centre_x, double centre_y, boolean direction) {
        super(type, speed, orientation);
        this.setPosition(new Position(centre_x, centre_y));
        this.radius = radius;
        this.width = width;
        this.directionClockwise = direction;
    }
    public void movedown(Ball b) {
        Platform.runLater(() -> {
                    for (int i = 0; i < 4; i++) {
                        tlist.get(i).stop();
                        tlist.get(i).setToY(Double.NaN);
                        tlist.get(i).setByY(movedistance);

                        //Setting the cycle count for the transition
                        tlist.get(i).setCycleCount(1);
                        tlist.get(i).setDuration(Duration.millis(movtime));

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
//        collisionCheck(b);
    }

    @Override
    protected void save_attributes(){
        this.saved_angle = rotate_list.get(0).getAngle();
        savedposition.set_x(quarters.get(0).getTranslateX());
        savedposition.set_y(quarters.get(0).getTranslateY());
    }


    @Override
    public void load_attributes(){
         quarters = new ArrayList<Path>();
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
    public boolean outofBounds(){
        if((position.get_y()+quarters.get(0).getTranslateY()-radius)>screenheight)
          return true;
        return false;
    }
    @Override
    public void removeself(Group grp){
        for(Path p: quarters) {
            Platform.runLater(() -> {
                p.setVisible(false);
                grp.getChildren().remove(p);
            });
        }
//        System.out.println("notvisible");
    }
    @Override
    protected void WayOfMovement() {
        for (int i = 0; i < timelines.size(); i++) {
            timelines.get(i).setCycleCount(Animation.INDEFINITE);
            double angle_to_cover;
            if (directionClockwise == true) {
                angle_to_cover = 360+saved_angle;
            } else {
                angle_to_cover = -360+saved_angle;
            }
            timelines.get(i).getKeyFrames().add(new KeyFrame(Duration.millis((this.getObstacleSpeed())), new KeyValue(rotate_list.get(i).angleProperty(), angle_to_cover)));
        }
    }

    public void rotateRing() {
        Platform.runLater(() -> {
//            System.out.println("rotating");
            for (int i = 0; i < timelines.size(); i++) {
                timelines.get(i).play();
            }
        });

    }

    @Override
    public void shownOnScreen(Group g) {
        Platform.runLater(() -> {
            for (int i = 0; i < quarters.size(); i++) {
                g.getChildren().add(quarters.get(i));
            }
        });
    }

    @Override
    public void draw() {

        //Making the four quarters one by one
        Path quarter1 = makeQuarter(1);
        Path quarter2 = makeQuarter(2);
        Path quarter3 = makeQuarter(3);
        Path quarter4 = makeQuarter(4);

        //adding them to the list of quarters
        quarters.add(quarter1);
        quarters.add(quarter2);
        quarters.add(quarter3);
        quarters.add(quarter4);

        /* Making the timelines for each quarter and also the corresponding rotate object ;  the pivots of rotate object are set to the centre coordinates of the obstacle */
        for (int i = 0; i < quarters.size(); i++) {
            Rotate r = new Rotate();
            r.setPivotX(this.getPosition().get_x());
            r.setPivotY(this.getPosition().get_y());
            timelines.add(new Timeline());
            rotate_list.add(r);
            quarters.get(i).getTransforms().add(r);
            tlist.get(i).setNode(quarters.get(i));
        }


    }

    /*
    This function returns a Path type object which represents a particular quarter of the Ring as specified by the quarter number in th argument
     quarter_numbers are as follows : Quarter (12 to 3) - 1 , (3 - 6) - 2, (6 - 9) - 3, (9 - 12)- 4
    */
    public Path makeQuarter(int quarter_number) {

        Position obstacleCentre = this.getPosition();
        ArcTo innerArc = new ArcTo();
        ArcTo outerArc = new ArcTo();
        Path path_to_add = new Path();
        double startX, startY, HLineX, VLineY, outerArcX, outerArcY, innerArcX, innerArcY;
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

        MoveTo start_position = new MoveTo(startX, startY);
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
        path_to_add.getElements().addAll(start_position, innerArc, horizontal_line, outerArc, vertical_line);
        path_to_add.setFill(c);

        return path_to_add;
    }


    @Override
    public void Resume(){
        Platform.runLater(() -> {
            rotateRing();
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
//        System.out.println("will do");
//        System.out.println("b.getShape().getTranslateX():" + b.getBallShape().getTranslateX());
//        System.out.println("b.getShape().getTranslateY():" + b.getBallShape().getTranslateY());
//        System.out.println("b.getPosition().get_x():" + b.getPosition().get_x());
//        System.out.println("b.getPosition().get_y():" + b.getPosition().get_y());
//        System.out.println("b.getPosition().getRadius():" + b.getRadius());

        //matching color
        for (i = 0; i < 4; i++) {
            if (b.getColor() == quarters.get(i).getFill()) {
                break;
            }
        }
//        System.out.println("i:" + i);
//        System.out.println(b.getColor() == quarters.get(i).getFill());
        double ang = rotate_list.get(i).getAngle();
//        System.out.println("ang:" + ang);
        //cal=distance b/w centers
        double cal = b.getPosition().get_y() + b.getBallShape().getTranslateY() - position.get_y() - quarters.get(i).getTranslateY();
//        System.out.println("quarters.get(i).getTranslateY():" + quarters.get(i).getTranslateY());
//        System.out.println("cal:" + cal);
        if(directionClockwise)
        ang += (i * 90);
        else
            ang -= (i * 90);
//        System.out.println("ang2:" + ang);
        //So that angle is b/w 0 and 360

        ang = adjust(ang);

//        System.out.println("ang3:" + ang);
        if (cal > 0) {//when ball is near bottom of obstacle
//            System.out.println("(radius - b.getRadius()) <= (cal):" +( (radius - b.getRadius()) <= (cal)));
//            System.out.println("(radius + b.getRadius()) >= (cal):" +( (radius + b.getRadius()) >= (cal)));
//            System.out.println("1:" + ((radius - b.getRadius()) <= (cal)));
//            System.out.println("2:" + (cal <= (radius + b.getRadius()+width)));
//            System.out.println("2.1:" +cal);
//                    System.out.println("2.2:" +(radius + b.getRadius()+width));
            if (((radius - b.getRadius()) <= (cal)) && (cal <= (radius + b.getRadius()+width))) {

                if (((90) <= ang) && (ang < 180))
                    return false;//same color
                else
                    return true;//different color
            }
        }
        if (cal < 0) {//when ball is near top of obstacle
            if (((radius - b.getRadius()) <= (-cal)) && ((-cal) <= (radius + b.getRadius()+width))) {
//                System.out.println("3:" + ((radius - b.getRadius()) <= (-cal)));
//                System.out.println("4:" + ((-cal) <= (radius + b.getRadius())));
                if ((270 <= ang) && (ang < 360))
                    return false;
                else
                    return true;
            }
        }
        //System.out.println("b.getShape().getTranslateY():"+b.getBallShape().getr());
        return false;
    }



    public double getRadius() {
        return radius;
    }

    public double getWidth() {
        return width;
    }

    public boolean isDirectionClockwise() {
        return directionClockwise;
    }

    public double getSaved_angle() {
        return saved_angle;
    }

    public ArrayList<Timeline> getTimelines() {
        return timelines;
    }

    public ArrayList<Rotate> getRotate_list() {
        return rotate_list;
    }
    public ArrayList<Path> getQuarters() {
        return quarters;
    }

}
