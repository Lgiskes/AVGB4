import HAT_Bot.Actuators.Motor;
import HAT_Bot.Actuators.Updatable;
import HAT_Bot.Logic.MotionController;
import TI.BoeBot;
import TI.Servo;
import TI.Timer;

import java.util.ArrayList;
import java.util.List;

public class RobotMain {

    public static void main(String[] args) {
        Servo s1 = new Servo(14);
        Servo s2 = new Servo(15);

        s1.update(1700);
        s2.update(1300);

        List<Updatable> updatables = new ArrayList<>();
        updatables.add(new Motor(14));
        updatables.add(new Motor(15));
        updatables.add(new MotionController());

        updatables.add(new Updatable() {
            @Override
            public void update() {
                System.out.println("Hallo groep B4");
            }
        });

        while (true){
            BoeBot.wait(20);

            for (Updatable updatable : updatables) {
                updatable.update();
            }

            if (BoeBot.digitalRead(5)== false && BoeBot.digitalRead(7) == false){
                s1.update(1300);
                s2.update(1700);
                BoeBot.wait(500);
                s1.update(1550);
                s2.update(1550);
                BoeBot.wait(1650);
                break;

            }else if(BoeBot.digitalRead(5) == false) {
                s1.update(1300);
                s2.update(1700);
                BoeBot.wait(250);
                s1.update(1550);
                s2.update(1500);
                BoeBot.wait(825);

            }else if(BoeBot.digitalRead(7) ==  false){
                s1.update(1300);
                s2.update(1700);
                BoeBot.wait(250);
                s1.update(1500);
                s2.update(1450);
                BoeBot.wait(825);

            } else{
                s1.update(1700);
                s2.update(1300);
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


        double omloopConstante=1.650;
        int meetSnelheid=50;

        double tijdMillisec = (graden*omloopConstante*meetSnelheid*1000)/(180.0*Math.abs(draaisnelheid));
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

