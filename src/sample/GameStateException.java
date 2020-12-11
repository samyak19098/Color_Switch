
package sample;

public class GameStateException extends RuntimeException{
    public GameStateException(String message) {
        super(message);
    }
    public GameStateException() {
        super();
    }

}

