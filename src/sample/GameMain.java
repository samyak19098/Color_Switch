package sample;
import java.util.*;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.stage.Stage;

public class GameMain extends TimerTask {

    private Group grp;
    private GameState CurrentGameState;


    private Stage gameplay_stage;
    public GameMain(Group grp, Stage game_stage){
        this.grp=grp;
        this.gameplay_stage = game_stage;

    }

    public void run( ) {
        try {
            CurrentGameState.checkAllcollisions(grp, gameplay_stage);
        } catch (Exception e) {
            e.printStackTrace();
            while(true){
            }

        }
//        try {
        try {
            CurrentGameState.RemoveObstacles(grp, gameplay_stage);
        } catch (Exception e) {
            e.printStackTrace();
            while(true){
//                System.out.println("WHILE");
            }
        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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

    public void removehand() {
        CurrentGameState.getHand().removeself(grp);
    }

    public Stage getGameplay_stage() {
        return gameplay_stage;
    }

    public void setGameplay_stage(Stage gameplay_stage) {
        this.gameplay_stage = gameplay_stage;
    }
}

