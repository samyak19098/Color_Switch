package sample;

public class Position {
    double x_pos;
    double y_pos;

    Position(double x, double y){
        this.x_pos = x;
        this.y_pos = y;
    }

    public void set_x(double x){
        this.x_pos = x;
    }
    public void set_y(double y){
        this.y_pos = y;
    }
    public double get_x(){
        return this.x_pos;
    }
    public double get_y() {
        return y_pos;
    }
}
