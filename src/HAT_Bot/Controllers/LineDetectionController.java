package HAT_Bot.Controllers;

import HAT_Bot.Hardware.Sensors.LineFollower;

public class LineDetectionController implements Updatable {
    private LineFollower leftLineFollower;
    private LineFollower middleLineFollower;
    private LineFollower rightLineFollower;
    private LineDetectionObserver observer;

    public LineDetectionController(LineFollower leftLineFollower, LineFollower middleLineFollower, LineFollower rightLineFollower, LineDetectionObserver observer) {
        this.leftLineFollower = leftLineFollower;
        this.middleLineFollower = middleLineFollower;
        this.rightLineFollower = rightLineFollower;
        this.observer = observer;
    }

    public void setObserver(LineDetectionObserver observer) {
        this.observer = observer;
    }

    @Override
    public void update() {
        leftLineFollower.update();
        middleLineFollower.update();
        rightLineFollower.update();
        System.out.println(leftLineFollower.getValue());
        System.out.println(middleLineFollower.getValue());
        System.out.println(rightLineFollower.getValue());
        if (leftLineFollower.getBoolean()) {
            if (middleLineFollower.getBoolean()) {
                observer.onLineDetected(this, LineDetectionCommand.slightLeft);
            }
            else {
                observer.onLineDetected(this, LineDetectionCommand.left);
            }
        }
        else if (rightLineFollower.getBoolean()) {
            if (middleLineFollower.getBoolean()) {
                observer.onLineDetected(this, LineDetectionCommand.slightRight);
            }
            else {
                observer.onLineDetected(this, LineDetectionCommand.right);
            }
        }
        else if (middleLineFollower.getBoolean()) {
            observer.onLineDetected(this, LineDetectionCommand.forward);
        }
    }
}
