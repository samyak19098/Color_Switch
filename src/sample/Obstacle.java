package sample;

public abstract class Obstacle extends GameObject{

    private String ObstacleType;
    private double ObstacleSpeed;
    private int Orientation;

    public String getObstacleType() {
        return this.ObstacleType;
    }

    public void setObstacleType(String obstacleType) {
        this.ObstacleType = obstacleType;
    }

    public double getObstacleSpeed() {
        return this.ObstacleSpeed;
    }

    public void setObstacleSpeed(double obstacleSpeed) {
        this.ObstacleSpeed = obstacleSpeed;
    }

    public int getOrientation() {
        return this.Orientation;
    }

    public void setOrientation(int orientation) {
        this.Orientation = orientation;
    }



    Obstacle(String type, double speed, int orientation){
        this.ObstacleType = type;
        this.ObstacleSpeed = speed;
        this.Orientation = orientation;
    }

    protected abstract void WayOfMovement();


}
