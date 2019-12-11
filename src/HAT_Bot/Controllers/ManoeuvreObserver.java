package HAT_Bot.Controllers;

public interface ManoeuvreObserver {

    void onManoeuvreDetected(MotionController m, ManoeuvreCommand command);
}
