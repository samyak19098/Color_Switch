package sample;
import java.util.*;

import javafx.application.Platform;
import javafx.scene.Group;

public class GameMain extends TimerTask {

    private Group grp;
    private GameState CurrentGameState;

    public GameMain(Group grp){
        this.grp=grp;

    }

    public void run( ) {
        CurrentGameState.checkAllcollisions(grp);
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
}
