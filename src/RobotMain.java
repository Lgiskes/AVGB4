import HAT_Bot.Logic.Updatable;
import HAT_Bot.Logic.MotionController;
import TI.BoeBot;
import TI.Servo;
import TI.Timer;

import java.util.ArrayList;
import java.util.List;

public class RobotMain {

    public static void main(String[] args) {

        List<Updatable> updatables = new ArrayList<>();
        updatables.add(new MotionController(14, 15));

        while (true){
            BoeBot.wait(20);

            for (Updatable updatable : updatables) {
                updatable.update();
            }

        }

    }


    public static void draaien(int draaisnelheid){
        Servo left = new Servo(14);
        Servo right = new Servo(15);
        left.start();
        right.start();

        draaisnelheid=Math.min(200, draaisnelheid);
        draaisnelheid=Math.max(-200,draaisnelheid);

        draaisnelheid +=1500;

        right.update(draaisnelheid);
        left.update(draaisnelheid);
    }

    public static void noodrem(){

        Servo left = new Servo(13);
        Servo right = new Servo(12);
        left.start();
        right.start();

        left.update(1500);
        right.update(1500);

    }

    public static void draaiGraden(int graden, int draaisnelheid){
        Servo left = new Servo(14);
        Servo right = new Servo(15);
        left.start();
        right.start();


        double omloopConstante=1.08;
        int meetSnelheid=200;

        double tijdMillisec = (graden*omloopConstante*meetSnelheid*1000)/(360.0*Math.abs(draaisnelheid));
        System.out.println(tijdMillisec);

        draaien(draaisnelheid);
        Timer timer = new Timer((int)tijdMillisec);
        while (!timer.timeout()){

        }
        noodrem();

    }

    public static void gaNaarSnelheid(int snelheid){
        snelheid=Math.max(-200,snelheid);
        snelheid=Math.min(200,snelheid);

        snelheid=1500+snelheid;

        Servo left = new Servo(13);
        Servo right = new Servo(12);
        left.start();
        right.start();
        int j;

        if(left.getPulseWidth()>snelheid){
            j=right.getPulseWidth();
            for(int i=left.getPulseWidth(); i<snelheid; i++){
                left.update(i);
                right.update(j);
                j--;
            }
        }else {
            j=right.getPulseWidth();
            for(int i=left.getPulseWidth(); i>snelheid; i--){
                left.update(i);
                right.update(j);
                j++;
            }

        }
    }

}

