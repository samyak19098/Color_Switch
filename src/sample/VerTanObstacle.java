package sample;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.transform.Rotate;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.shape.*;

public class VerTanObstacle extends Obstacle implements Serializable {

    private double radius;  //radius of inner ring ; R_outer = R_inner + width + 5;
    private double width;
    private ArrayList<RingObstacle> rings = new ArrayList<RingObstacle>();      // rings[0] = inner ring, rings[1] = outer ring;
    // direction of inner ring(T = clockwise, F = anti)
    private double angleRing; //angle by which rings are rotated ( generally 45)
    private double saved_angle_left;
    private double saved_angle_right;

    VerTanObstacle(String type, double speed, int orientation, double radius, double width, double centre_x, double centre_y, boolean direction, double angle){
        super(type,speed,orientation);
        this.setPosition(new Position(centre_x,centre_y));
        this.radius = radius;
        this.width = width;
        this.directionClockwise = direction;
        this.angleRing = angle;
    }

    @Override
    public void movedown(Ball b) {
        rings.get(0).movedown(b);        rings.get(1).movedown(b);
    }

    @Override
    public void save_attributes(){
        rings.get(0).save_attributes(); rings.get(1).save_attributes();
    }

    @Override
    public void load_attributes(){
        rings.get(0).load_attributes(); rings.get(1).load_attributes();
        rings.get(1).getQuarters().get(0).setFill(Color.CYAN);   rings.get(1).getQuarters().get(2).setFill(Color.PURPLE);
        rings.get(1).getQuarters().get(1).setFill(Color.YELLOW);   rings.get(1).getQuarters().get(3).setFill(Color.DEEPPINK);
    }

    @Override
    public void WayOfMovement() {
        for(int i = 0 ; i < rings.get(0).getTimelines().size(); i++){
            rings.get(0).getTimelines().get(i).setCycleCount(Animation.INDEFINITE);
            rings.get(1).getTimelines().get(i).setCycleCount(Animation.INDEFINITE);
            int angle_to_cover_inner, angle_to_cover_outer;
            if(directionClockwise == true){
                angle_to_cover_inner = (int) (360 + angleRing);
                angle_to_cover_outer = (int) (360 + angleRing);
            }
            else{
                angle_to_cover_inner = (int) (-360 + angleRing);
                angle_to_cover_outer = (int) (-360 + angleRing);
            }
            rings.get(0).getTimelines().get(i).getKeyFrames().add(new KeyFrame(Duration.millis((this.getObstacleSpeed())), new KeyValue(rings.get(0).getRotate_list().get(i).angleProperty(), angle_to_cover_inner)));
            rings.get(1).getTimelines().get(i).getKeyFrames().add(new KeyFrame(Duration.millis((this.getObstacleSpeed())), new KeyValue(rings.get(1).getRotate_list().get(i).angleProperty(), angle_to_cover_outer)));
        }
    }

    @Override
    public void shownOnScreen(Group g) {
        rings.get(0).shownOnScreen(g);
        rings.get(1).shownOnScreen(g);
    }

    public void rotateConcentric(){
//        this.WayOfMovement();
        rings.get(0).rotateRing();
        rings.get(1).rotateRing();
    }



    @Override
    public void draw() {

        Position pos = this.getPosition();
        RingObstacle ring1 = new RingObstacle("Ring", ObstacleSpeed, 0, radius, width ,pos.get_x(), pos.get_y(), true);//lower ring
        ring1.draw();
        RingObstacle ring2 = new RingObstacle("Ring", ObstacleSpeed, 0, radius  , width ,pos.get_x(), pos.get_y()-(2*(radius+width)), true);//upper ring
        ring2.draw();
        for(int i = 0 ;i < 4; i++){
            ring1.getRotate_list().get(i).setAngle(angleRing);
            ring2.getRotate_list().get(i).setAngle(angleRing);

        }
        ring2.getQuarters().get(1).setFill(Color.YELLOW);   ring2.getQuarters().get(3).setFill(Color.DEEPPINK);
        ring2.getQuarters().get(0).setFill(Color.CYAN);   ring2.getQuarters().get(2).setFill(Color.PURPLE);
        rings.add(ring1);
        rings.add(ring2);
    }

    @Override
    public boolean collisionCheck(Ball b) {
        //double ang = rings.get(0).getRotate_list().get(0).getAngle();//inner ring
        //double ang2 = rings.get(0).getRotate_list().get(0).getAngle();//outer ring
        if(rings.get(0).collisionCheck(b) || rings.get(1).collisionCheck((b)))
            return true;
//        System.out.println("rings.get(0).collisionCheck(b):"+rings.get(0).collisionCheck(b));
//        System.out.println("rings.get(1).collisionCheck(b):"+rings.get(1).collisionCheck(b));

        return false;
    }
    @Override
    public void removeself(Group grp){
        rings.get(0).removeself(grp);rings.get(1).removeself(grp);
//        System.out.println("notvisible");
    }
    @Override
    public boolean outofBounds(){
        return rings.get(1).outofBounds();//outer ring
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

    public double getAngleRing() {
        return angleRing;
    }

    public double getSaved_angle_left() {
        return saved_angle_left;
    }

    public double getSaved_angle_right() {
        return saved_angle_right;
    }

}
