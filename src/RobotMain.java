import TI.BoeBot;
import TI.Servo;

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
}
