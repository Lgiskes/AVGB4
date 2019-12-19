package HAT_Bot_GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
     *
     */
    public class ApplicationMain extends Application {
        private KeyboardController keyboardController;
        private BluetoothController bluetoothController;


        public static void ApplicationMain(String[] args) {
            launch(ApplicationMain.class);
        }

        /**
         *
         * @param primaryStage
         * @throws Exception
         * */
        @Override
        public void start(Stage primaryStage) throws Exception {
            this.bluetoothController = new BluetoothController("COM6");
            this.keyboardController = new KeyboardController(this.bluetoothController);

            VBox vBox = new VBox();
            HBox topHBox = new HBox();
            HBox bottomHBox = new HBox();
            TabPane tabPane = new TabPane();

            topHBox.getChildren().addAll(moveButtons(), speedButtons());
            bottomHBox.getChildren().addAll(emergencyBreak());

            vBox.setSpacing(20);
            topHBox.setSpacing(62.5);
            bottomHBox.setSpacing(5);

            vBox.getChildren().addAll(topHBox, bottomHBox);

            Group group = new Group(vBox);
            Tab manualControlTab = new Tab("Manual Control");
            Tab routeControlTab = new Tab("Route Control");
            manualControlTab.setContent(group);
            tabPane.getTabs().addAll(manualControlTab, routeControlTab);
            tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

            Scene scene = new Scene(tabPane, 600, 450, Color.WHITE);
            primaryStage.setScene(scene);
            primaryStage.show();

            scene.setOnKeyPressed(event -> {
                this.keyboardController.keyPressedHandler(event);
            });
        }

        public Node moveButtons() {
            GridPane moveButtonGp = new GridPane();
            moveButtonGp.setHgap(2.5);
            moveButtonGp.setVgap(2.5);

            ArrayList<ButtonController> buttons = new ArrayList<>();
            buttons.add(new ButtonController("^", 33, 1, 0, 100));
            buttons.add(new ButtonController("<", 39, 0, 1, 100));
            buttons.add(new ButtonController("stop", 25, 1, 1, 100));
            buttons.add(new ButtonController(">", 37, 2, 1, 100));
            buttons.add(new ButtonController("v", 35, 1, 2, 100));

            for (ButtonController b : buttons) {
                Button button = new Button(b.getText());
                button.setPrefSize(b.getButtonSize(), b.getButtonSize());
                moveButtonGp.add(button, b.getX(), b.getY());
                button.setOnAction(event -> {
                    this.bluetoothController.sendBinary(b.getCommand());
                });
            }

            return moveButtonGp;
        }

        public Node speedButtons() {
            GridPane speedButtonGp = new GridPane();
            speedButtonGp.setHgap(2.5);
            speedButtonGp.setVgap(2.5);

            ArrayList<ButtonController> buttons = new ArrayList<>();
            buttons.add(new ButtonController("1", 1, 0, 0, 75));
            buttons.add(new ButtonController("2", 3, 1, 0, 75));
            buttons.add(new ButtonController("3", 5, 2, 0, 75));
            buttons.add(new ButtonController("4", 7, 0, 1, 75));
            buttons.add(new ButtonController("5", 9, 1, 1, 75));
            buttons.add(new ButtonController("6", 11, 2, 1, 75));
            buttons.add(new ButtonController("7", 13, 0, 2, 75));
            buttons.add(new ButtonController("8", 15, 1, 2, 75));
            buttons.add(new ButtonController("9", 17, 2, 2, 75));
            buttons.add(new ButtonController("10", 19, 1, 3, 75));

            for (ButtonController b : buttons) {
                Button button = new Button(b.getText());
                button.setPrefSize(b.getButtonSize(), b.getButtonSize());
                speedButtonGp.add(button, b.getX(), b.getY());
                button.setOnAction(event -> {
                    this.bluetoothController.sendBinary(b.getCommand());
                });
            }

            return speedButtonGp;
        }

        public Node emergencyBreak() {
            Button emergencyBreak = new Button("EmergencyBrake");
            emergencyBreak.setPrefSize(200, 75);
            emergencyBreak.setStyle("-fx-background-color: RED");
            emergencyBreak.setFont(Font.font("Arial Black", 18));
            emergencyBreak.setOnAction(event -> {
                this.bluetoothController.sendBinary((byte)43);
            });

            return emergencyBreak;
        }
    }


//    @Override
//    public void start(Stage primaryStage) throws Exception {
//
//        GridPane mainPane = new GridPane();
//
//        SerialPort serialPort = new SerialPort("COM6");
//        try{
//            serialPort.openPort();
//
//            serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//        }
//        catch (SerialPortException ex){
//            System.out.println(ex.getMessage());
//        }
//
//        //button writestring == ascii waarde + 255 == remotecontrol value
//        Button forwardButton = new Button("\u2191");
//        forwardButton.setOnAction((event -> {
//            try{
//                serialPort.writeString("!"); //Vooruit
//            }
//            catch (SerialPortException ex){
//                System.out.println(ex.getMessage());
//            }
//        }));
//
//        Button backwardButton = new Button("\u2193");
//        backwardButton.setOnAction((event -> {
//            try{
//                serialPort.writeString("#"); //Achteruit
//            }
//            catch (SerialPortException ex){
//                System.out.println(ex.getMessage());
//            }
//        }));
//
//        Button leftButton = new Button("\u2190");
//        leftButton.setOnAction((event -> {
//            try{
//                serialPort.writeString("'"); //Links
//            }
//            catch (SerialPortException ex){
//                System.out.println(ex.getMessage());
//            }
//        }));
//
//        Button rightButton = new Button("\u2192");
//        rightButton.setOnAction((event -> {
//            try{
//                serialPort.writeString("%"); //Rechts
//            }
//            catch (SerialPortException ex){
//                System.out.println(ex.getMessage());
//            }
//        }));
//
//        Button brakeButton = new Button("Stop");
//        brakeButton.setOnAction((event -> {
//            try{
//                serialPort.writeString("+"); //Stop
//            }
//            catch (SerialPortException ex){
//                System.out.println(ex.getMessage());
//            }
//        }));
//
//        Button toggleLights = new Button("Lights");
//        toggleLights.setOnAction((event -> {
//            try{
//                serialPort.writeString("q"); //Toggle Lights
//            }
//            catch (SerialPortException ex){
//                System.out.println(ex.getMessage());
//            }
//        }));
//
//        mainPane.setHgap(5);
//        mainPane.setVgap(5);
//
//        final int buttonSize = 100;
//        forwardButton.setPrefSize(buttonSize, buttonSize);
//        rightButton.setPrefSize(buttonSize, buttonSize);
//        leftButton.setPrefSize(buttonSize, buttonSize);
//        backwardButton.setPrefSize(buttonSize, buttonSize);
//        brakeButton.setPrefSize(buttonSize, buttonSize);
//        toggleLights.setPrefSize(buttonSize, buttonSize);
//
//
//        mainPane.add(forwardButton, 1, 0);
//        mainPane.add(backwardButton, 1, 2);
//        mainPane.add(leftButton, 0, 1);
//        mainPane.add(rightButton, 2, 1);
//        mainPane.add(brakeButton, 1, 1);
//        mainPane.add(toggleLights, 2, 2);
//
//        Scene scene = new Scene(mainPane);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//}
