import HAT_Bot.Actuators.LED;
import HAT_Bot.Logic.IndicatorController;
import HAT_Bot.Logic.Updatable;
import HAT_Bot.Logic.MotionController;
import TI.BoeBot;
import TI.Servo;
import TI.Timer;
import HAT_Bot.Sensors.Infrared;
import HAT_Bot.Logic.RemoteControl;
import java.util.ArrayList;
import java.util.List;

public class RobotMain {

    public static void main(String[] args) {

        MotionController m = new MotionController(14, 15);
        IndicatorController i = new IndicatorController(3, 4);
        RemoteControl r = new RemoteControl(2, m, i);

        List<Updatable> updatables = new ArrayList<>();
        updatables.add(m);
        updatables.add(r);
        updatables.add(i);

        while (true){

            for (Updatable updatable : updatables) {
                updatable.update();
            }

            BoeBot.wait(1);

        }

    }

}

