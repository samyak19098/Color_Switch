package sample;

import javafx.scene.Group;
import javafx.scene.shape.*;
public class Star {
    Polygon s;
    public Star(){
        s   = new Polygon();
        s.getPoints().addAll(new Double[]{
                0.0, 0.0,
                20.0, 10.0,
                10.0, 20.0 });
    }
    public  void movedown(Ball b){

    }


    public void shownOnScreen(Group g) {

            g.getChildren().add(s);

    }
}
