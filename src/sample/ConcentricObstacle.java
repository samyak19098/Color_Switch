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

public class ConcentricObstacle extends Obstacle{

    private double radius;  //radius of inner ring ; R_outer = R_inner + width + 5;
    private double width;
    private ArrayList<RingObstacle> rings = new ArrayList<RingObstacle>();      // rings[0] = inner ring, rings[1] = outer ring;
    private boolean directionClockwise; // direction of inner ring(T = clockwise, F = anti)
    private double angleRing; //angle by which rings are rotated ( generally 45)

    ConcentricObstacle(String type, double speed, int orientation, double radius, double width, double centre_x, double centre_y, boolean direction, double angle){
        super(type,speed,orientation);
        this.setPosition(new Position(centre_x,centre_y));
        this.radius = radius;
        this.width = width;
        this.directionClockwise = direction;
        this.angleRing = angle;
    }

    @Override
    public void movedown(Ball b) {

    }

    @Override
    protected void WayOfMovement() {
        for(int i = 0 ; i < rings.get(0).getTimelines().size(); i++){
            rings.get(0).getTimelines().get(i).setCycleCount(Animation.INDEFINITE);
            rings.get(1).getTimelines().get(i).setCycleCount(Animation.INDEFINITE);
            int angle_to_cover_inner, angle_to_cover_outer;
            if(directionClockwise == true){
                angle_to_cover_inner = (int) (360 + angleRing);
                angle_to_cover_outer = (int) (-360 + angleRing);
            }
            else{
                angle_to_cover_inner = (int) (-360 + angleRing);
                angle_to_cover_outer = (int) (360 + angleRing);
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
        RingObstacle ring1 = new RingObstacle("Ring", 6000, 0, radius, width ,pos.get_x(), pos.get_y(), true);
        ring1.draw();
        RingObstacle ring2 = new RingObstacle("Ring", 6000, 0, radius + width + 5, width ,pos.get_x(), pos.get_y(), false);
        ring2.draw();
        for(int i = 0 ;i < 4; i++){
            ring1.getRotate_list().get(i).setAngle(angleRing);
            ring2.getRotate_list().get(i).setAngle(angleRing);
        }
        rings.add(ring1);
        rings.add(ring2);
    }

    @Override
    public boolean collisionCheck(Ball b) {
        return false;
    }
}
