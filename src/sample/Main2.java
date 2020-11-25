package sample;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.*;



class Manager extends ImageView implements Serializable {
  public  String name;
  public TranslateTransition t;
  public Manager(String n) {  name=n; }//t=new TranslateTransition(); }
    public void setter(TranslateTransition c){
      t=c;
    }
}
  class Main2  {

    public static void serialize() throws IOException {
        Manager s1 = new Manager("Amy");
        s1.setter(new TranslateTransition());
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("out.txt"));
            out.writeObject(s1);
        } finally {
            out.close();
        }
    }
    public static void deserialize()throws IOException, ClassNotFoundException {
         ObjectInputStream in = null;
         try {
             in = new ObjectInputStream (
                    new FileInputStream("out.txt"));
             Manager s1 = (Manager) in.readObject();
             System.out.println("name:"+s1.name);
             } finally {
             in.close();
             }

        }
        public static void main(String[] args) throws IOException,ClassNotFoundException{
            serialize();
          deserialize();

        }
}