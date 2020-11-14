package sample;
import java.util.*;
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
