package HAT_Bot_GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
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
            vBox.getChildren().addAll(topHBox, bottomHBox);
            vBox.setSpacing(20);
            topHBox.setSpacing(62.5);
            bottomHBox.setSpacing(5);
            Group group = new Group(vBox);

            topHBox.getChildren().addAll(moveButtons(), speedButtons());
            bottomHBox.getChildren().addAll(emergencyBreak());

            TabPane tabPane = new TabPane();
            Tab manualControlTab = new Tab("Manual Control");
            manualControlTab.setContent(group);

            tabPane.getTabs().addAll(manualControlTab, roundButtonGridpane());
            tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

            Scene scene = new Scene(tabPane, 602, 700, Color.WHITE);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

            scene.setOnKeyPressed(event -> {
                this.keyboardController.keyPressedHandler(event);
            });
        }

        /**
         *
         * @return
         */
        private Node moveButtons() {
            GridPane moveButtonGp = new GridPane();
            moveButtonGp.setHgap(2.5);
            moveButtonGp.setVgap(2.5);

            ArrayList<ButtonController> buttons = new ArrayList<>();
            buttons.add(new ButtonController("^", BluetoothCommands.FORWARD, 1, 0, 100));
            buttons.add(new ButtonController("<", BluetoothCommands.LEFT, 0, 1, 100));
            buttons.add(new ButtonController("stop", BluetoothCommands.STOP, 1, 1, 100));
            buttons.add(new ButtonController(">", BluetoothCommands.RIGHT, 2, 1, 100));
            buttons.add(new ButtonController("v", BluetoothCommands.BACKWARD, 1, 2, 100));

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

        /**
         *
         * @return
         */
        private Node speedButtons() {
            GridPane speedButtonGp = new GridPane();
            speedButtonGp.setHgap(2.5);
            speedButtonGp.setVgap(2.5);

            ArrayList<ButtonController> buttons = new ArrayList<>();
            buttons.add(new ButtonController("1", BluetoothCommands.SPEED10, 0, 0, 75));
            buttons.add(new ButtonController("2", BluetoothCommands.SPEED20, 1, 0, 75));
            buttons.add(new ButtonController("3", BluetoothCommands.SPEED30, 2, 0, 75));
            buttons.add(new ButtonController("4", BluetoothCommands.SPEED40, 0, 1, 75));
            buttons.add(new ButtonController("5", BluetoothCommands.SPEED50, 1, 1, 75));
            buttons.add(new ButtonController("6", BluetoothCommands.SPEED60, 2, 1, 75));
            buttons.add(new ButtonController("7", BluetoothCommands.SPEED70, 0, 2, 75));
            buttons.add(new ButtonController("8", BluetoothCommands.SPEED80, 1, 2, 75));
            buttons.add(new ButtonController("9", BluetoothCommands.SPEED90, 2, 2, 75));
            buttons.add(new ButtonController("10", BluetoothCommands.SPEED100, 1, 3, 75));

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

        private Node emergencyBreak() {
            Button emergencyBreak = new Button("EmergencyBrake");
            emergencyBreak.setPrefSize(200, 75);
            emergencyBreak.setStyle("-fx-background-color: RED");
            emergencyBreak.setFont(Font.font("Arial Black", 18));
            emergencyBreak.setOnAction(event -> {
                this.bluetoothController.sendBinary(BluetoothCommands.EMERGENCY_BRAKE);
            });

            return emergencyBreak;
        }

        private Tab roundButtonGridpane(){
            HBox hbox = new HBox();
            GridPane gridPane = new GridPane();
            gridPane.setVgap(76);
            gridPane.setHgap(75);

            Tab routeControlTab = new Tab("Route Control");
            // new Image(url)
            Image image = new Image("file:Resources/Grid.png");
            // new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
            // new BackgroundImage(image, repeatX, repeatY, position, size)
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
            // new Background(images...)
            Background background = new Background(backgroundImage);
            gridPane.setBackground(background);

            for(int x = 1; x < 6; x++){
                for(int y = 1; y < 5; y++){
                    RoundButtonController roundButton = new RoundButtonController("", x, y);
                    Button button = new Button(roundButton.getName());
                    button.setStyle(
                            "-fx-background-radius: 5em; " +
                                    "-fx-min-width: 30px; " +
                                    "-fx-min-height: 30px; " +
                                    "-fx-max-width: 30px; " +
                                    "-fx-max-height: 30px;"
                    );
                    gridPane.add(button, x, y);
                }
            }
            gridPane.getColumnConstraints().add(new ColumnConstraints(6));
            gridPane.getRowConstraints().add(new RowConstraints(6));
            routeControlTab.setContent(gridPane);

            return routeControlTab;
        }

    }
