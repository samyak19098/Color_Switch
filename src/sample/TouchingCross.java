package sample;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class TouchingCross extends Obstacle implements Serializable {

    private double length;
    private double thickness;
    private transient ArrayList<CrossObstacle> crosses = new ArrayList<CrossObstacle>();
    private boolean directionClockwise; // direction of left cross(T = clockwise, F = anti)
    private double saved_angle_left;
    private double saved_angle_right;

    TouchingCross(String type, double speed, int orientation, double length,double thickness, double centre_x, double centre_y, boolean direction){
        super(type, speed, orientation);
        this.setPosition(new Position(centre_x,centre_y));
        this.length = length;
        this.thickness = thickness;
        this.directionClockwise = direction;

    }

    @Override
    public void movedown(Ball b) {
        crosses.get(0).movedown(b);crosses.get(1).movedown(b);
    }

    @Override
    protected void WayOfMovement() {
        crosses.get(0).WayOfMovement();
        crosses.get(1).WayOfMovement();
    }

    @Override
    public void shownOnScreen(Group g) {
        crosses.get(0).shownOnScreen(g);
        crosses.get(1).shownOnScreen(g);
    }

    public void rotateTouchingCross(){
        crosses.get(0).rotateCross();
        crosses.get(1).rotateCross();
    }

    @Override
    protected void save_attributes(){
        crosses.get(0).save_attributes();   crosses.get(1).save_attributes();
    }

    @Override
    public void load_attributes(){
        crosses.get(0).load_attributes();   crosses.get(1).load_attributes();
    }



    @Override
    public void draw() {

        Position pos = this.getPosition();
        double delta =0;
//        System.out.println("pos.get_x() - length - delta " + (pos.get_x() - length - delta));
//        System.out.println("pos.get_x() + length + delta"+pos.get_x() + length + delta);
        CrossObstacle cross_left  = new CrossObstacle("Cross", 6000, 0, length, thickness,pos.get_x() - (length/2) - delta, pos.get_y(), !directionClockwise );
        CrossObstacle cross_right = new CrossObstacle("Cross", 6000, 0, length, thickness,pos.get_x() + (length/2) + delta, pos.get_y(), (directionClockwise));
        cross_left.draw();  cross_right.draw();
        cross_right.getBars().get(3).setFill(Color.YELLOW); cross_right.getBars().get(1).setFill(Color.DEEPPINK);
        crosses.add(cross_left);    crosses.add(cross_right);
    }
    @Override
    public boolean outofBounds(){
        return crosses.get(1).outofBounds();//outer ring
    }

    @Override
    public boolean collisionCheck(Ball b) {

        return crosses.get(1).collisionCheck(b);
    }
    @Override
    public void Resume(){
        crosses.get(0).Resume();crosses.get(1).Resume();

    }
    @Override
    public void removeself(Group grp){
        crosses.get(0).removeself(grp);    crosses.get(1).removeself(grp);
//        System.out.println("notvisible");
    }
    @Override
    public void Pause(){
        crosses.get(0).Pause();crosses.get(1).Pause();

    }


    public double getLength() {
        return length;
    }

    public double getThickness() {
        return thickness;
    }



    public boolean isDirectionClockwise() {
        return directionClockwise;
    }

    public double getSaved_angle_left() {
        return saved_angle_left;
    }

    public double getSaved_angle_right() {
        return saved_angle_right;
    }

}
