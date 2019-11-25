package HAT_Bot.Controllers;

import HAT_Bot.Sensors.Ultrasone;

public class ObstacleDetection implements Updatable {

    private Ultrasone ultrasone;
    private MotionController motionController;


    public ObstacleDetection(Ultrasone ultrasone, MotionController motionController) {
        this.ultrasone = ultrasone;
        this.motionController = motionController;
    }

    @Override
    public void update() {
        ultrasone.update();

        if(ultrasone.getValue() < 10){
            motionController.emergencyBrake();
            motionController.goToSpeed(-100);
            motionController.turnLeft();
        }

    }

}
