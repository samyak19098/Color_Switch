package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Firetrail extends Trail{
    public Firetrail(Ball b){
        super(b);
        for (Ball var : q) {
            Image im = new Image("file:firetrail2.jpg",false);
            var.getBallShape().setFill(new ImagePattern(im));

        }

    }
}
