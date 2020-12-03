package sample;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameDetails implements Serializable {
    private ArrayList<Boolean> trailsunlocked;
    private ArrayList<Boolean> achievementsunlocked;
    private ArrayList<String> playernames;
    private int numStars;

    public GameDetails(){
        numStars = 0;
        trailsunlocked = new ArrayList<Boolean>();
        for(int i = 0; i < 4 ; i++) {
            trailsunlocked.add(false);
        }
        achievementsunlocked = new ArrayList<Boolean>();
        for(int i = 0 ;i < 3; i++){
            achievementsunlocked.add(false);
        }
        playernames = new ArrayList<String>();
        for(int i = 0; i < 5; i++) {
            playernames.add("Empty Slot");
        }
    }

    public ArrayList<Boolean> getTrailsunlocked() {
        return trailsunlocked;
    }

    public void setTrailsunlocked(ArrayList<Boolean> trailsunlocked) {
        this.trailsunlocked = trailsunlocked;
    }

    public ArrayList<Boolean> getAchievementsunlocked() {
        return achievementsunlocked;
    }

    public void setAchievementsunlocked(ArrayList<Boolean> achievementsunlocked) {
        this.achievementsunlocked = achievementsunlocked;
    }

    public ArrayList<String> getPlayernames() {
        return playernames;
    }

    public void setPlayernames(ArrayList<String> playernames) {
        this.playernames = playernames;
    }

    public int getNumStars() {
        return numStars;
    }

    public void setNumStars(int numStars) {
        this.numStars = numStars;
    }


}
