package sample;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.text.SimpleDateFormat;
import java.util.Date;

public   class Achievement {
    protected int requirednumber;
    protected  static   double screenwidth=1200;
    protected static  double screenheight=800;
    protected TextArea text;
    public Rectangle unlocked,locked;
    public  boolean Unlock;
    public Date DateofUnlock;
    public SimpleDateFormat DateFormatter;

    public Achievement(){
        text=new TextArea();

        text.setDisable(true);
        text.setPrefColumnCount(8);
        text.setPrefRowCount(1);
         text.setLayoutX(screenwidth/2);
        text.setStyle(" -fx-font-weight: bold; -fx-font-size:18;");
        unlocked=new Rectangle(screenwidth/15,screenheight/10);locked=new Rectangle(screenwidth/15,screenheight/10);
        unlocked.setLayoutX(screenwidth*0.25);locked.setLayoutX(screenwidth*0.25);
        Image im = new Image("file:unlocked2.jpg",false);
        unlocked.setFill(new ImagePattern(im));
          im = new Image("file:locked2.jpg",false);
        locked.setFill(new ImagePattern(im));
        Unlock=true;
//        box=new Rectangle(screenwidth/4,screenheight/4);
    }
    public boolean Requirement(long a){
         return true;
    }

}
