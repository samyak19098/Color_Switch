package sample;

import java.io.Serializable;

public class Position implements Serializable {
    private double x_pos;
    private double y_pos;

    public Position(double x, double y){
        this.x_pos = x;
        this.y_pos = y;
    }

    public void set_x(double x_set){
        this.x_pos = x_set;
    }
    public void set_y(double y_set){
        this.y_pos = y_set;
    }
    public double get_x(){
        return this.x_pos;
    }
    public double get_y() {
        return y_pos;
    }
}
