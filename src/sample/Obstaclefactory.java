package sample;

import javafx.scene.Group;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Obstaclefactory implements Serializable {
    public Obstacle createObstacle(String type, double speed, Group grp,double screenwidth,double screenheight,double initialhColorSwitcher,double initialhobstacle, ArrayList<Obstacle> sceneObstacles, ArrayList<ColorSwitcher> sceneColorSwitcher){
        if (type.equals("Ring")) {

            ConcentricObstacle concentricObstacle = new ConcentricObstacle("Concentric", speed, 0, 100, 20, screenwidth / 2, initialhobstacle - screenheight, true, 45);
            concentricObstacle.draw();
            concentricObstacle.WayOfMovement();
            concentricObstacle.rotateConcentric();
            concentricObstacle.shownOnScreen(grp);
           // sceneObstacles.add(concentricObstacle);
            SuperColorSwitcher scs=new SuperColorSwitcher(screenwidth/2,initialhColorSwitcher-screenheight-(2*21),20);
            scs.shownOnScreen(grp);

            sceneColorSwitcher.add(scs);
            return concentricObstacle;
        }
        else if (type.equals("Square")) {
            TangentialObstacle tangentialObstacle = new TangentialObstacle("Tangential", speed, 0, 170, 20, screenwidth / 2, initialhobstacle - screenheight, true, 45, 225);
            tangentialObstacle.draw();
            tangentialObstacle.WayOfMovement();
            tangentialObstacle.rotateTangential();
            tangentialObstacle.shownOnScreen(grp);
            return tangentialObstacle;
            //tangentialObstacle.Pause();//tangentialObstacle.Resume();
           // sceneObstacles.add(tangentialObstacle);

        }
        else if(type.equals("Concentric")){
            VerTanObstacle vertanObstacle= new VerTanObstacle("VerTan",speed,0,100, 20, screenwidth/2,initialhobstacle-screenheight ,true,45);
            vertanObstacle.draw();
            vertanObstacle.WayOfMovement();
            vertanObstacle.rotateConcentric();
            vertanObstacle.shownOnScreen(grp);
            return vertanObstacle;
            //                        vertanObstacle.Pause();
            //sceneObstacles.add(vertanObstacle);
//
//
//
        }
        else if(type.equals("Tangential")){
            CrossObstacle crossObstacle=new CrossObstacle("cross",speed,0,screenwidth*0.2,20,screenwidth *0.6, initialhobstacle - screenheight,true);
            crossObstacle.draw();
            crossObstacle.WayOfMovement();
            crossObstacle.rotateCross();
            crossObstacle.shownOnScreen(grp);
            return crossObstacle;
            //sceneObstacles.add(crossObstacle);
////                            crossObstacle.Pause();

//
        }
        else if(type.equals("VerTan")){
            TouchingCross touchingcrossObstacle=new TouchingCross("touchingcross",speed,0,screenwidth*0.2,20,screenwidth/2, initialhobstacle - screenheight,true);
            touchingcrossObstacle.draw();
            touchingcrossObstacle.WayOfMovement();
            touchingcrossObstacle.rotateTouchingCross();
            touchingcrossObstacle.shownOnScreen(grp);
            //sceneObstacles.add(touchingcrossObstacle);
            return touchingcrossObstacle;
//
//
        }
        else if (type.equals("cross")) {
            speed-=5;//more difficulty
            RingObstacle ringObstacle = new RingObstacle("Ring", speed, 0, 100,20, screenwidth/2, initialhobstacle-screenheight, true);
            ringObstacle.draw();
            ringObstacle.WayOfMovement();
            ringObstacle.rotateRing();
            ringObstacle.shownOnScreen(grp);
            //sceneObstacles.add(ringObstacle);
            return ringObstacle;

        }
        else if (type.equals("touchingcross")) {
            SquareObstacle squareObstacle = new SquareObstacle("Square",speed,0,screenwidth/2,initialhobstacle-screenheight  ,175 ,20 ,true);
            squareObstacle.draw();
            squareObstacle.WayOfMovement();
            squareObstacle.rotateSquare();
            squareObstacle.shownOnScreen(grp);
           // sceneObstacles.add(squareObstacle);
            return squareObstacle;
        }
        return null;
    }
}
