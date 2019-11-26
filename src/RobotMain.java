import HAT_Bot.Controllers.*;
import HAT_Bot.Logic.OperatingLogic;
import HAT_Bot.Sensors.Ultrasone;
import TI.BoeBot;

import java.util.ArrayList;
import java.util.List;

public class RobotMain {

    public static void main(String[] args) {

        MotionController m = new MotionController(14, 15);
        IndicatorController i = new IndicatorController(3, 4);
        RemoteControl r = new RemoteControl(2, m, i);
        ObstacleDetection o = new ObstacleDetection(new Ultrasone(0, 1), null);
        OperatingLogic a = new OperatingLogic(i, m, o, r);

        List<Updatable> updatables = new ArrayList<>();
        updatables.add(m);
        updatables.add(a);
        updatables.add(r);
        updatables.add(i);
        updatables.add(o);

        while (true){

            for (Updatable updatable : updatables) {
                updatable.update();
            }

            BoeBot.wait(1);

        }

    }

}

