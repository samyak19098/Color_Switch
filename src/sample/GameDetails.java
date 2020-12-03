package sample;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameDetails implements Serializable {
    public ArrayList<Boolean> trailsunlocked;
    public ArrayList<Boolean> achievementsunlocked;
    public ArrayList<String> playernames;
    public int numStars;
    public GameDetails(){
        numStars=0;
        trailsunlocked=new ArrayList<Boolean>();
        for(int i=0;i<4;i++)
        trailsunlocked.add(false);
        achievementsunlocked=new ArrayList<Boolean>();
        for(int i=0;i<3;i++)
            achievementsunlocked.add(false);
        playernames=new ArrayList<String>();
        for(int i=0;i<5;i++)
            playernames.add("Empty Slot");

    }

}
