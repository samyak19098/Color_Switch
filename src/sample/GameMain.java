package sample;
import java.util.*;


public class GameMain extends TimerTask{
    public GameState getCurrentGameState() {
        return CurrentGameState;
    }

    public void setCurrentGameState(GameState currentGameState) {
        CurrentGameState = currentGameState;
    }

    GameState CurrentGameState;
    public GameMain(){

    }

    public void run( )
    {
        CurrentGameState.debug();
        //System.out.println("Timer ran ");
    }
}
