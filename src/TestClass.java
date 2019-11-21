import HAT_Bot.Logic.MotionController;
import HAT_Bot.Logic.StoppableTimer;
import HAT_Bot.Logic.Updatable;

public class TestClass implements Updatable {
    private StoppableTimer motionControllerTest = new StoppableTimer(1000);
    private MotionController m = new MotionController(14, 15,5 ,7);
    private int turnsMade=0;

    @Override
    public void update() {

        if(motionControllerTest.timeout()){
            if(this.turnsMade<4){
                m.turnRight();
                this.turnsMade++;
                m.forward();
                m.goToSpeed(0);
            }
            else{
                motionControllerTest.stop();
                this.turnsMade=0;
            }
        }

    }

    public void testMotionController(){
        motionControllerTest.start();
    }
}
