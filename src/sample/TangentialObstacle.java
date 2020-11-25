package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;

public class TangentialObstacle extends Obstacle implements Serializable {

    private double radius;  //radius of inner ring ; R_outer = R_inner + width + 5;
    private double width;
    private ArrayList<RingObstacle> rings = new ArrayList<RingObstacle>();      // rings[0] = left ring, rings[1] = right ring;
    private boolean directionClockwise; // direction of left ring(T = clockwise, F = anti)
    private double angleRing1; //angle by which left right are rotated
    private double angleRing2; //angle by which left right are rotated
    private double saved_angle_left;
    private double saved_angle_right;

    TangentialObstacle(String type, double speed, int orientation, double radius, double width,double centre_x, double centre_y, boolean directionClockwise, double angle1, double angle2) {
        super(type, speed, orientation);
        this.setPosition(new Position(centre_x, centre_y)); // centre_x,centre_Y position of the point at which both rings meet tangentially side by side
        this.radius = radius;
        this.width = width;
        this.directionClockwise = directionClockwise;
        this.angleRing1 = angle1;
        this.angleRing2 = angle2;
    }

    @Override
    public void movedown(Ball b) {
        rings.get(0).movedown(b);        rings.get(1).movedown(b);
    }

    @Override
    protected void save_attributes() {
        rings.get(0).save_attributes(); rings.get(1).save_attributes();
    }

    @Override
    public void load_attributes(){
        rings.get(0).load_attributes(); rings.get(1).load_attributes();
    }

    @Override
    protected void WayOfMovement() {
        for(int i = 0 ; i < rings.get(0).getTimelines().size(); i++){
            rings.get(0).getTimelines().get(i).setCycleCount(Animation.INDEFINITE);
            rings.get(1).getTimelines().get(i).setCycleCount(Animation.INDEFINITE);
            int angle_to_cover_left, angle_to_cover_right;
            if(directionClockwise == true){
                //angle_to_cover = (int) (360 + angle1);
                angle_to_cover_left = (int) (360 + angleRing1);
                angle_to_cover_right = (int) (-360 + angleRing2);
            }
            else{
                angle_to_cover_left = (int) (-360 + angleRing1);
                angle_to_cover_right = (int) (360 + angleRing2);
            }
            rings.get(0).getTimelines().get(i).getKeyFrames().add(new KeyFrame(Duration.millis((this.getObstacleSpeed())), new KeyValue(rings.get(0).getRotate_list().get(i).angleProperty(), angle_to_cover_left)));
            rings.get(1).getTimelines().get(i).getKeyFrames().add(new KeyFrame(Duration.millis((this.getObstacleSpeed())), new KeyValue(rings.get(1).getRotate_list().get(i).angleProperty(), angle_to_cover_right)));
        }
    }

    public void rotateTangential(){
//        WayOfMovement();
        rings.get(0).rotateRing();
        rings.get(1).rotateRing();
    }

    @Override
    public void shownOnScreen(Group g) {
        this.rings.get(0).shownOnScreen(g);
        this.rings.get(1).shownOnScreen(g);
    }

    @Override
    public void draw() {

        Position pos = this.getPosition();
        RingObstacle ring1 = new RingObstacle("Ring", this.getObstacleSpeed(), this.getOrientation(), radius, width, pos.get_x() - width - radius, pos.get_y(), directionClockwise);
        RingObstacle ring2 = new RingObstacle("Ring", this.getObstacleSpeed(), this.getOrientation(), radius, width, pos.get_x() + width + radius, pos.get_y(), (!directionClockwise));
        ring1.draw();   ring2.draw();
        for(int i = 0 ;i < 4; i++) {
            ring1.getRotate_list().get(i).setAngle(angleRing1);
            ring2.getRotate_list().get(i).setAngle(angleRing2);
        }
        ring2.getQuarters().get(1).setFill(Color.YELLOW);   ring2.getQuarters().get(3).setFill(Color.DEEPPINK);

        rings.add(ring1);   rings.add(ring2);

    }
    @Override
    public void removeself(Group grp){
         rings.get(0).removeself(grp);    rings.get(1).removeself(grp);
        System.out.println("notvisible");
    }
    @Override
    public boolean collisionCheck(Ball b) {
        //returns true  if collision occurs
        //otherwise false
//        if(true)
//            return false;
        int i = -1;
        //System.out.println("will do");
//        System.out.println("b.getShape().getTranslateX():" + b.getBallShape().getTranslateX());
//        System.out.println("b.getShape().getTranslateY():" + b.getBallShape().getTranslateY());
//        System.out.println("b.getPosition().get_x():" + b.getPosition().get_x());
//        System.out.println("b.getPosition().get_y():" + b.getPosition().get_y());
//        System.out.println("b.getPosition().getRadius():" + b.getRadius());

        //matching color
        for (i = 0; i < 4; i++) {
            if (b.getColor() == rings.get(0).getQuarters().get(i).getFill()) {
                break;
            }
        }
//        System.out.println("i:" + i);
//        System.out.println(b.getColor() == quarters.get(i).getFill());
        double ang = rings.get(0).getRotate_list().get(i).getAngle();//left ring
//        System.out.println("ang:" + ang);
        //cal=distance b/w centers
        double cal = b.getPosition().get_y() + b.getBallShape().getTranslateY() - position.get_y() - rings.get(0).getQuarters().get(i).getTranslateY();
//        System.out.println("quarters.get(i).getTranslateY():" + quarters.get(i).getTranslateY());
//        System.out.println("cal:" + cal);
        ang += (i * 90);
//        System.out.println("ang2:" + ang);
        //So that angle is b/w 0 and 360
        ang = adjust(ang);

//        System.out.println("ang3:" + ang);

//            System.out.println("(radius - b.getRadius()) <= (cal):" +( (radius - b.getRadius()) <= (cal)));
//            System.out.println("(radius + b.getRadius()) >= (cal):" +( (radius + b.getRadius()) >= (cal)));
//            System.out.println("1:" + ((radius - b.getRadius()) <= (cal)));
//            System.out.println("2:" + (cal <= (radius + b.getRadius()+width)));
//            System.out.println("2.1:" +cal);
//                    System.out.println("2.2:" +(radius + b.getRadius()+width));
        double theta1= Math.acos((radius+width)/(b.getRadius()+radius+width));

        if(cal>0){//when ball is near bottom of obstacle
            if (   Math.atan(cal/radius)  < theta1 ) {

                if (  theta1<=ang && ang<=90  )
                    return false;//same color
                else
                    return true;//different color
            }
        }
        else{//when ball is near top of obstacle
            cal=Math.abs(cal);
            if (   Math.atan(cal/radius)  <  Math.acos((radius+width)/(b.getRadius()+radius+width))) {

                if (   ang<=(90-theta1)    )
                    return false;//same color
                else
                    return true;//different color
            }

        }


        //System.out.println("b.getShape().getTranslateY():"+b.getBallShape().getr());
        return false;
    }
    @Override
    public boolean outofBounds(){
      return rings.get(0).outofBounds();
    }
    @Override
    public void Pause(){
        rings.get(0).Pause();rings.get(1).Pause();
    }
    @Override
    public void Resume(){
        rings.get(0).Resume();rings.get(1).Resume();
    }


    public double getRadius() {
        return radius;
    }

    public double getWidth() {
        return width;
    }

    public ArrayList<RingObstacle> getRings() {
        return rings;
    }

    public boolean isDirectionClockwise() {
        return directionClockwise;
    }

    public double getAngleRing1() {
        return angleRing1;
    }

    public double getAngleRing2() {
        return angleRing2;
    }

    public double getSaved_angle_left() {
        return saved_angle_left;
    }

    public double getSaved_angle_right() {
        return saved_angle_right;
    }

}
