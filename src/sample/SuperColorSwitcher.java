package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class SuperColorSwitcher extends ColorSwitcher{

    public SuperColorSwitcher(double x,double y,double radius){
         super(x,y,radius);
        Image im = new Image("file:supercolorswitcher.jpg",false);
        circle.setFill(new ImagePattern(im));
    }
    @Override
    public void specialChange(Ball b) {
        Color x=b.getColor();

        b.setColor(Color.WHITE);
    }
    @Override
    public void load_attributes() {
        super.load_attributes();
        Image im = new Image("file:supercolorswitcher.jpg",false);
        circle.setFill(new ImagePattern(im));
    }
}
