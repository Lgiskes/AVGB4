package HAT_Bot_Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortException;


/**
 * Dit is de GUI van de besturing/inprogrammering van de BoeBot
 */
public class ApplicationMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane mainPane = new GridPane();

        SerialPort serialPort = new SerialPort("COM7");
        try{
            serialPort.openPort();

            serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        }
        catch (SerialPortException ex){
            System.out.println(ex.getMessage());
        }

        Button forwardButton = new Button("\u2191");
        forwardButton.setOnAction((event -> {
            try{
                serialPort.writeString("!"); //Vooruit
            }
            catch (SerialPortException ex){
                System.out.println(ex.getMessage());
            }
        }));

        Button backwardButton = new Button("\u2193");
        backwardButton.setOnAction((event -> {
            try{
                serialPort.writeString("#"); //Achteruit
            }
            catch (SerialPortException ex){
                System.out.println(ex.getMessage());
            }
        }));

        Button leftButton = new Button("\u2190");
        leftButton.setOnAction((event -> {
            try{
                serialPort.writeString("'"); //Links
            }
            catch (SerialPortException ex){
                System.out.println(ex.getMessage());
            }
        }));

        Button rightButton = new Button("\u2192");
        rightButton.setOnAction((event -> {
            try{
                serialPort.writeString("%"); //Rechts
            }
            catch (SerialPortException ex){
                System.out.println(ex.getMessage());
            }
        }));

        Button brakeButton = new Button("Stop");
        brakeButton.setOnAction((event -> {
            try{
                serialPort.writeString("+"); //Stop
            }
            catch (SerialPortException ex){
                System.out.println(ex.getMessage());
            }
        }));

        Button toggleLights = new Button("Lights");
        toggleLights.setOnAction((event -> {
            try{
                serialPort.writeString("q"); //Toggle Lights
            }
            catch (SerialPortException ex){
                System.out.println(ex.getMessage());
            }
        }));

        mainPane.setHgap(5);
        mainPane.setVgap(5);

        final int buttonSize = 100;
        forwardButton.setPrefSize(buttonSize, buttonSize);
        rightButton.setPrefSize(buttonSize, buttonSize);
        leftButton.setPrefSize(buttonSize, buttonSize);
        backwardButton.setPrefSize(buttonSize, buttonSize);
        brakeButton.setPrefSize(buttonSize, buttonSize);
        toggleLights.setPrefSize(buttonSize, buttonSize);


        mainPane.add(forwardButton, 1, 0);
        mainPane.add(backwardButton, 1, 2);
        mainPane.add(leftButton, 0, 1);
        mainPane.add(rightButton, 2, 1);
        mainPane.add(brakeButton, 1, 1);
        mainPane.add(toggleLights, 2, 2);

        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(ApplicationMain.class);
    }

}
