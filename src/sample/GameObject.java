package sample;

import javafx.scene.Group;

public abstract class GameObject {

    private Position position;
    public abstract void showOnScreen(Group g);
    public abstract void draw();
//    public abstract checkCollision(Ball b);
    public Position getPosition(){
        return this.position;
    }
    public void setPosition(Position p){
        this.position = p;
    }


}
