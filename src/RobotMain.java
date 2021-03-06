import HAT_Bot.Controllers.*;
import HAT_Bot.Hardware.Sensors.Bluetooth;
import HAT_Bot.Hardware.Sensors.LineFollower;
import HAT_Bot.Logic.OperatingLogic;
import HAT_Bot.Hardware.Sensors.Ultrasone;
import TI.BoeBot;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.List;

public class RobotMain {

    public static void main(String[] args) {

        MotionController m = new MotionController(14, 15);
        IndicatorController i = new IndicatorController(3, 4);
        RemoteControl r = new RemoteControl(2, null);
        ObstacleDetection o = new ObstacleDetection(new Ultrasone(0, 1), new Ultrasone(7,8), null);
        LineDetectionController l = new LineDetectionController(new LineFollower(0), new LineFollower(1), new LineFollower(2), null);
        RouteController e = new RouteController();
        BluetoothController b = new BluetoothController(r, e);
        OperatingLogic a = new OperatingLogic(i, m, o, r, l, e, b);



        List<Updatable> updatables = new ArrayList<>();
        updatables.add(m);
        updatables.add(a);
        updatables.add(r);
        updatables.add(i);
        updatables.add(o);
        updatables.add(l);
        updatables.add(b);

        while (true){

            for (Updatable updatable : updatables) {
                updatable.update();
            }

            BoeBot.wait(1);

        }

    }

}

