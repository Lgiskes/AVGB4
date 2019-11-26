package HAT_Bot.Controllers;

public interface RemoteControlObserver {
    void onRemoteControlDetected(RemoteControl r, String command);
}
