package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class TouchingCross extends Obstacle{

    private double length;
    private double thickness;
    private ArrayList<CrossObstacle> crosses = new ArrayList<CrossObstacle>();
    private boolean directionClockwise; // direction of left cross(T = clockwise, F = anti)

    TouchingCross(String type, double speed, int orientation, double length,double thickness, double centre_x, double centre_y, boolean direction){
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
    public void draw() {

        Position pos = this.getPosition();
        double delta = (double)(thickness /  (double)(2));
        CrossObstacle cross_left  = new CrossObstacle("Cross", 6000, 0, length, thickness,pos.get_x() - length - delta, pos.get_y(), directionClockwise );
        CrossObstacle cross_right = new CrossObstacle("Cross", 6000, 0, length, thickness,pos.get_x() + length + delta, pos.get_y(), !(directionClockwise));
        cross_left.draw();  cross_right.draw();
        cross_right.getBars().get(3).setFill(Color.YELLOW); cross_right.getBars().get(1).setFill(Color.DEEPPINK);
        crosses.add(cross_left);    crosses.add(cross_right);
    }

    @Override
    public boolean collisionCheck(Ball b) {
        return false;
    }
}