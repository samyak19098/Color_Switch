package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;


    import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

    public class Neontrail extends Trail{
        public Neontrail(Ball b){
            super(b);
            for (Ball var : q) {
                Image im = new Image("file:neontrail.jpg",false);
                var.getBallShape().setFill(new ImagePattern(im));

            }

        }
    }

