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
import java.util.Random;

public class Database implements Serializable {




    private int count;
    private static Database gen = null;
    private Database(){

    }
    public static Database getInstance(){
        if(gen==null){
            gen=new Database();
        }
        return gen;
    }
    public void serialize(GameState gs, int slot_num) throws IOException {

        String saving_file = "save_file" + slot_num +".ser";

        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(saving_file));
            out.writeObject(gs);

        }
        catch(FileNotFoundException e){
//            try {
//                File myfile = new File(saving_file);
//                myfile.createNewFile()   ;
//
//            } catch (IOException e1) {
//                e.printStackTrace();
//            }

            e.printStackTrace();
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

    public GameState deserialize(int slot_num) throws IOException, ClassNotFoundException{

        String saving_file = "save_file" + slot_num +".ser";
        ObjectInputStream in = null;
        GameState loaded = null;
        try {
            in = new ObjectInputStream(new FileInputStream(saving_file));
            loaded = (GameState) in.readObject();
        }

        catch(FileNotFoundException e){
//            try {
//                File myfile = new File(saving_file);
//                myfile.createNewFile()   ;
//
//            } catch (IOException e1) {
//                e.printStackTrace();
//            }

            e.printStackTrace();
        }

        finally {
            if(in == null) {
                System.out.println("in is nulll");
            }
            else {
                in.close();
            }
        }
        return loaded;
    }
    public void serialize(GameState gs ){

    }
    public GameState deserialize( ){
        return null;
    }
    public int getCount() {
        return count;
    }
    public void incCount() {
         count+=1;
    }

}
