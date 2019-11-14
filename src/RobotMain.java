import TI.BoeBot;
import TI.Servo;
import TI.Timer;

import java.util.FormatFlagsConversionMismatchException;

public class RobotMain {

    public static void main(String[] args) {
        Servo s1 = new Servo(14);
        Servo s2 = new Servo(15);

        s1.update(1700);
        s2.update(1300);

        while (true){
            BoeBot.wait(20);

            if (BoeBot.digitalRead(5)== false || BoeBot.digitalRead(7) == false){
                s1.update(1300);
                s2.update(1700);
                BoeBot.wait(500);
                s1.update(1700);
                s1.update(1700);
                BoeBot.wait(950);




            }else{
                s1.update(1700);
                s2.update(1300);
            }
        }

    }

    public static void draaien(int draaisnelheid){
        Servo left = new Servo(13);
        Servo right = new Servo(12);
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
        Servo left = new Servo(13);
        Servo right = new Servo(12);
        left.start();
        right.start();


        double omloopConstante=1.08;
        int meetSnelheid=200;

        double tijdMillisec = (graden*omloopConstante*meetSnelheid*1000)/(360.0*Math.abs(draaisnelheid));
        System.out.println(tijdMillisec);

        draaien(draaisnelheid);
        Timer timer = new Timer((int)tijdMillisec);
        Timer timer1 = new Timer(10);
        while (!timer.timeout()){
            while (timer1.timeout()){
            }
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
