package HAT_Bot.Logic;

import HAT_Bot.Controllers.*;
import com.sun.jndi.cosnaming.RemoteToCorba;

public class OperatingLogic implements Updatable, ObstacleDetectionObserver, RemoteControlObserver {

    private IndicatorController indicatorController;
    private MotionController motionController;
    private ObstacleDetection obstacleDetection;
    private RemoteControl remoteControl;

    public OperatingLogic(IndicatorController indicatorController, MotionController motionController, ObstacleDetection obstacleDetection, RemoteControl remoteControl) {
        this.indicatorController = indicatorController;
        this.motionController = motionController;
        this.obstacleDetection = obstacleDetection;
        this.remoteControl = remoteControl;
        obstacleDetection.setObserver(this);
        remoteControl.setObserver(this);
    }

    public void onObstacleDetected (ObstacleDetection o, String command){
        System.out.println(command);
        if (command.equals("Slow down")){
            this.motionController.goToSpeed(0);
            this.indicatorController.foundObstacleIndication();
        }
        else if(command.equals("Stop")){
            this.motionController.emergencyBrake();
            this.indicatorController.inFrontOfObstacleIndication();
        }
        else{
            this.motionController.goToSpeed(100);
            this.indicatorController.drivingIndication();
        }

    }

    public void onRemoteControlDetected(RemoteControl r, String command) {

    }

    @Override
    public void update() {

    }
}
