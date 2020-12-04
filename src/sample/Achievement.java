package sample;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public   class Achievement implements Serializable {
    protected int requirednumber;
    protected  static   double screenwidth=1200;
    protected static  double screenheight=800;
    protected TextArea text;

    private Rectangle unlocked,locked;
    private boolean Unlock;
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
        Unlock=false;
//        box=new Rectangle(screenwidth/4,screenheight/4);
    }
    public boolean Requirement(long a){
        if(a>=requirednumber)
            return true;
        return false;

    }

    public void setUnlock(boolean unlock) {
        Unlock = unlock;
    }

    public void setDateofUnlock(Date dateofUnlock) {
        DateofUnlock = dateofUnlock;
    }

    public TextArea getText() {
        return text;
    }


    public boolean getUnlock() {
        return Unlock;
    }


    public Rectangle getUnlocked() {
        return unlocked;
    }

    public Rectangle getLocked() {
        return locked;
    }


    public void setUnlocked(Rectangle unlocked) {
        this.unlocked = unlocked;
    }

    public void setLocked(Rectangle locked) {
        this.locked = locked;
    }


    public Date getDateofUnlock() {
        return DateofUnlock;
    }

}
