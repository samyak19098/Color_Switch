package sample;
import java.util.*;

import javafx.application.Platform;
import javafx.scene.Group;

public class GameMain extends TimerTask {

    private Group grp;
    private GameState CurrentGameState;
    private HashMap<Integer,Achievement> GameAchievements;
    public GameMain(Group grp){
        this.grp=grp;
        GameAchievements=new  HashMap<>();
        for(int i=0;i<3;i++)
            GameAchievements.put(i,new StarAchievement((i*30)));

    }

    public void run( ) {
        CurrentGameState.checkAllcollisions(grp);
        CurrentGameState.RemoveObstacles(grp);
//        CurrentGameState.AddObjects(grp);
        //System.out.println("Timer ran ");
    }
    public void Pause(){
        System.out.println("pausing!!");
        for(Star s: CurrentGameState.getSceneStars()) {
            s.Pause();
        }
        for(ColorSwitcher s: CurrentGameState.getSceneColorSwitcher()) {
            s.Pause();
        }
        for(Obstacle s: CurrentGameState.getSceneObstacles()) {
            s.Pause();
        }
        CurrentGameState.getCurrentBall().Pause();
        CurrentGameState.BallTrail.Pause();

    }
    public void continueGame(){//todo shift to InGameMenu
        System.out.println("continuing !!");
        for(Star s: CurrentGameState.getSceneStars()) {
            s.Resume();
        }
        for(ColorSwitcher s: CurrentGameState.getSceneColorSwitcher()) {
            s.Resume();
        }
        for(Obstacle s: CurrentGameState.getSceneObstacles()) {
            s.Resume();
        }
        CurrentGameState.getCurrentBall().Resume();
        CurrentGameState.BallTrail.Resume();
    }
    public Group getGrp() {
        return grp;
    }
    public GameState getCurrentGameState() {
        return CurrentGameState;
    }
    public void setCurrentGameState(GameState currentGameState) {
        CurrentGameState = currentGameState;
    }

    public void removehand() {
        CurrentGameState.getHand().removeself(grp);
    }
}
