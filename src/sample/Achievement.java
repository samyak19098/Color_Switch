package sample;

import javafx.scene.shape.Rectangle;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Achievement {
    protected  static   double screenwidth=1200;
    protected static  double screenheight=800;
    public  boolean Unlock;
    public Date DateofUnlock;
    public SimpleDateFormat DateFormatter;
    public Rectangle box;
    public Achievement(){
        Unlock=false;
        box=new Rectangle(screenwidth,screenheight);
    }
    public abstract  boolean Requirement(long a);

}
