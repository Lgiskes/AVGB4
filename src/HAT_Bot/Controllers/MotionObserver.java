package HAT_Bot.Controllers;

/**
 * Rules for the motion classes.
 */

public interface MotionObserver {

    void onMotionEnd(MotionController sender, String motionFunction);

}
