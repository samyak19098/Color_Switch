package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;

public class TangentialObstacle extends Obstacle{

    private double radius;  //radius of inner ring ; R_outer = R_inner + width + 5;
    private double width;
    private ArrayList<RingObstacle> rings = new ArrayList<RingObstacle>();      // rings[0] = left ring, rings[1] = right ring;
    private boolean directionClockwise; // direction of left ring(T = clockwise, F = anti)
    private double angleRing1; //angle by which left right are rotated
    private double angleRing2; //angle by which left right are rotated
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
    public boolean collisionCheck(Ball b) {
        return false;
    }
}
