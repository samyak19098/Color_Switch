package sample;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Firetrail extends Trail{
    public Firetrail( ){}
    public Firetrail(Ball b){
        super(b);

        Image img = new Image("file:firetrail2.jpg",false);

        for (Ball var : q) {
            Image im = new Image("file:firetrail2.jpg",false);
            var.getBallShape().setFill(new ImagePattern(im));

        }

    }
}
