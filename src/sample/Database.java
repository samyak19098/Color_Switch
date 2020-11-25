package sample;

import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Database implements Serializable {



    private final String saving_file = "/Users/rohitritika/Desktop/AP_RESOURCE/game_save.ser";

    public void serialize(GameState gs) throws IOException {

        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(saving_file));
            out.writeObject(gs);

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
           if (out != null) {
                out.close();
            }
        }
    }

    public GameState deserialize() throws IOException, ClassNotFoundException{

        ObjectInputStream in = null;
        GameState loaded;
        try {
            in = new ObjectInputStream(new FileInputStream(saving_file));
            loaded = (GameState) in.readObject();
        } finally {
            in.close();
        }
        return loaded;
    }
}
