package sample;


public class StarsNotSufficientException extends RuntimeException{
    public StarsNotSufficientException(String message) {
        super(message);
    }
    public StarsNotSufficientException() {
        super();
    }

}
