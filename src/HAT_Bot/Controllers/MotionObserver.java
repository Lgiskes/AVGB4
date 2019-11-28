package HAT_Bot.Controllers;

import HAT_Bot.Hardware.Actuators.Motor;

public interface MotionObserver {

    void onMotionEnd(MotionController sender, String motionFunction);


}
