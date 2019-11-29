package HAT_Bot.Controllers;

import HAT_Bot.Hardware.Sensors.LineFollower;

public class LineDetectionController implements Updatable{
    private LineFollower leftLineFollower;
    private LineFollower middelLineFollower;
    private LineFollower rightLineFollower;
    private LineDetectionObserver observer;

    public LineDetectionController(LineFollower leftLineFollower, LineFollower middelLineFollower, LineFollower rightLineFollower, LineDetectionObserver observer) {
        this.leftLineFollower = leftLineFollower;
        this.middelLineFollower = middelLineFollower;
        this.rightLineFollower = rightLineFollower;
        this.observer = observer;
    }

    public void setObserver(LineDetectionObserver observer) {
        this.observer = observer;
    }

    @Override
    public void update() {
        if (leftLineFollower.getBoolean()) {
            if (middelLineFollower.getBoolean()) {
                observer.onLineDetected(this, LineDetectionCommand.slightLeft);
            }
            else {
                observer.onLineDetected(this, LineDetectionCommand.left);
            }
        }
        else if (rightLineFollower.getBoolean()) {
            if (middelLineFollower.getBoolean()) {
                observer.onLineDetected(this, LineDetectionCommand.slightRight);
            }
            else {
                observer.onLineDetected(this, LineDetectionCommand.right);
            }
        }
        else if (middelLineFollower.getBoolean()) {
            observer.onLineDetected(this, LineDetectionCommand.forward);
        }
    }
}
