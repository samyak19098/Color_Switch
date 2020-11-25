package sample;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;


    import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

    public class Neontrail extends Trail{
        public Neontrail( ){}
        public Neontrail(Ball b){
            super(b);

            Image img = new Image("file:neontrail.jpg",false);

            for (Ball var : q) {
                Image im = new Image("file:neontrail.jpg",false);
                var.getBallShape().setFill(new ImagePattern(im));

            }

        }
    }

