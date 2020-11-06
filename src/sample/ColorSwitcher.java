package sample;
import javafx.scene.paint.Color;
import java.util.*;
public class ColorSwitcher implements SpecialObject{
    static HashMap<Integer,Color> map=new HashMap<Integer,Color>();
    static {
        map.put(0,Color.PURPLE);
        map.put(1,Color.CYAN);
        map.put(2,Color.DEEPPINK);
        map.put(3,Color.YELLOW);
    }
    public ColorSwitcher() {

    }
    @Override
public void specialChange(Ball b) {
    Color x=b.getColor();
       int t=((int)(Math.random()*4));
while(x==map.get(t)){
    t=(int)(Math.random()*4);
}
b.setColor(map.get(t));
}
public boolean collisionCheck(Ball b)  {
return true;
}
}
