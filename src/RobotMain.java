import HAT_Bot.Logic.Updatable;
import HAT_Bot.Logic.MotionController;
import TI.BoeBot;
import TI.Servo;
import TI.Timer;
import java.util.ArrayList;
import java.util.List;

public class RobotMain {

    public static void main(String[] args) {
        MotionController m = new MotionController(14, 15,5 ,7);
        List<Updatable> updatables = new ArrayList<>();
        updatables.add(m);
        updatables.add(new TestClass());



        while (true){

            for (Updatable updatable : updatables) {
                updatable.update();
            }

            BoeBot.wait(1);

        }

    }

}

