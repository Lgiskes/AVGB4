package HAT_Bot_GUI;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

    /**
     *
     */
    public class ApplicationMain extends Application {
    private KeyboardController keyboardController;
    private BluetoothController bluetoothController;
    private Font Bold = new Font("Arial Black", 25);
    private RoundButtonController[] buttonList = new RoundButtonController[20];
    private RouteController routeController =  new RouteController();
    private Directions facingDirection;


    public static void ApplicationMain(String[] args) {
        launch(ApplicationMain.class);
    }

    /**
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        this.bluetoothController = new BluetoothController("COM6");
        this.keyboardController = new KeyboardController(this.bluetoothController);

        TabPane tabPane = new TabPane(controlTab(), routeTab());

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);


        Scene scene = new Scene(tabPane, 602, 640, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        scene.setOnKeyPressed(event -> {
            this.keyboardController.keyPressedHandler(event);
        });
    }

    private Tab controlTab() {
        Tab controlTab = new Tab("Manual Control");

        AnchorPane anchorPane = new AnchorPane();

        Node emergencyBreak = emergencyBreak();
        Node speedControl = speedButtons();
        Node directionalControl = moveButtons();

        AnchorPane.setTopAnchor(directionalControl, 100.0);
        AnchorPane.setTopAnchor(speedControl, 100.0);
        AnchorPane.setLeftAnchor(directionalControl, 20.0);
        AnchorPane.setRightAnchor(speedControl, 20.0);
        AnchorPane.setLeftAnchor(emergencyBreak, 30.0);
        AnchorPane.setRightAnchor(emergencyBreak, 30.0);
        AnchorPane.setBottomAnchor(emergencyBreak, 100.0);

        anchorPane.getChildren().addAll(directionalControl, speedControl, emergencyBreak);

        controlTab.setContent(anchorPane);

        return controlTab;
    }

    private Tab routeTab() {
        Tab routeTab = new Tab("Route Control");

        AnchorPane anchorPane = new AnchorPane();

        Node roundButtonGridpane = roundButtonGridpane();
        Node routeButtons = routeButtons();

        AnchorPane.setTopAnchor(roundButtonGridpane, 0.0);
        AnchorPane.setLeftAnchor(roundButtonGridpane, 0.0);
        AnchorPane.setRightAnchor(roundButtonGridpane, 0.0);
        AnchorPane.setBottomAnchor(routeButtons, 0.0);
        anchorPane.getChildren().addAll(roundButtonGridpane, routeButtons);

        routeTab.setContent(anchorPane);

        return routeTab;
    }

    /**
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
            button.setFont(this.Bold);
            button.setPrefSize(b.getButtonSize(), b.getButtonSize());
            moveButtonGp.add(button, b.getX(), b.getY());
            button.setOnAction(event -> {
                this.bluetoothController.sendBinary(b.getCommand());
            });
        }

        return moveButtonGp;
    }

    /**
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
            button.setFont(this.Bold);
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

    private Node roundButtonGridpane() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(70);
        gridPane.setHgap(70);

        // new Image(url)
        Image image = new Image("file:Resources/Grid.png");
        // new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        // new BackgroundImage(image, repeatX, repeatY, position, size)
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        // new Background(images...)
        Background background = new Background(backgroundImage);
        gridPane.setBackground(background);

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 4; y++) {
                RoundButtonController roundButton = new RoundButtonController("", x, y);
                this.buttonList[(x * 4) + y] = roundButton;
                Button button = new Button(roundButton.getName());
                button.setFont(new Font("Arial Black", 15));
                button.setStyle(
                        "-fx-background-radius: 5em; " +
                                "-fx-min-width: 35px; " +
                                "-fx-min-height: 35px; " +
                                "-fx-max-width: 35px; " +
                                "-fx-max-height: 35px;"
                );
                roundButton.setMaxOptions(10);
                gridPane.add(button, x + 1, y + 1);
                button.setOnAction(event -> {
                    roundButton.buttonPressed();
                    boolean running = true;
                    while (running) {
                        for (RoundButtonController buttonController : this.buttonList){
                            if (buttonController != roundButton){
                                if (buttonController.getName().equals(roundButton.getName())){
                                    roundButton.buttonPressed();
                                    running = true;
                                    break;
                                }
                                else {
                                    running = false;
                                }
                            }
                        }
                    }
                    button.setText(roundButton.getName());
                    System.out.println(roundButton.getX() + " " + roundButton.getY());
                });
                gridPane.add(new Label(), 0, 5);
            }
        }
        gridPane.getColumnConstraints().add(new ColumnConstraints(12));
        gridPane.getRowConstraints().add(new RowConstraints(12));

        return gridPane;
    }

    private Node routeButtons() {
            ComboBox comboBox = new ComboBox();
            comboBox.setPrefSize(250, 37.5);
            comboBox.getItems().addAll(this.routeController.getRouteNames());
            comboBox.getSelectionModel().selectFirst();

            TextField textField = new TextField("Type route name");
            textField.setPrefSize(250, 37.5);

            Button loadButton = new Button("Run route");
            loadButton.setPrefSize(150, 37.5);
            loadButton.setOnAction( event -> {
                String routeName =(String) comboBox.getValue();
                String route = this.routeController.getRoute(routeName);
                loadButtonPressed((String)comboBox.getValue());
                System.out.println(route);
            });
            Button saveButton = new Button("Save route");
            saveButton.setPrefSize(150, 37.5);
            saveButton.setOnAction(event -> {
                    String route = "Hello World";

                    this.routeController.addRoute(textField.getText(), route);
                    comboBox.getItems().remove(0, comboBox.getItems().size());

                    comboBox.getItems().addAll(this.routeController.getRouteNames());
                    comboBox.getSelectionModel().select(textField.getText());

                    System.out.println("Route saved!");

            });



            HBox hBox = new HBox();
            hBox.setSpacing(7);
            VBox vBox1 = new VBox();
            VBox vBox2 = new VBox();

            hBox.getChildren().addAll(vBox1, vBox2, emergencyBreak());

            vBox1.getChildren().addAll(comboBox, textField);
            vBox2.getChildren().addAll(loadButton, saveButton);
            return hBox;
        }

        private void loadButtonPressed(String route) {
            if (route.equals("Current route")) {
                ArrayList<Integer> routeOrder = new ArrayList<>();
                boolean calculating = true;
                int lastButton = 0;
                while (calculating) {
                    int closestButton = 99;
                    int arrayPosision = 0;
                    for (RoundButtonController button : this.buttonList) {
                        if (button.getButtonState() != 0 && button.getButtonState() > lastButton) {
                            if (button.getButtonState() < closestButton) {
                                closestButton = button.getButtonState();
                                arrayPosision = (button.getX()*4) + button.getY();
                            }
                        }
                    }
                    if (closestButton == 99) {
                        calculating = false;
                        break;
                    }
                    else {
                        routeOrder.add(arrayPosision);
                        lastButton = closestButton;
                    }
                }
                String routeSteps = "";
                this.facingDirection = Directions.NORTH;
                for (Integer step : routeOrder) {
                    if (routeOrder.get(routeOrder.size()-1) != step) {
                        if (this.buttonList[step].getX() - this.buttonList[routeOrder.get(routeOrder.indexOf(step) + 1)].getX() > 0) { // Needs to go west
                            switch (this.facingDirection) {
                                case NORTH:
                                    routeSteps += "l";
                                    break;
                                case EAST:
                                    routeSteps += "b";
                                    break;
                                case SOUTH:
                                    routeSteps += "r";
                                    break;
                                case WEST:
                                    routeSteps += "f";
                                    break;
                            }
                            this.facingDirection = Directions.WEST;
                        }
                        else if (this.buttonList[step].getX() - this.buttonList[routeOrder.get(routeOrder.indexOf(step) + 1)].getX() < 0) { // Needs to go east
                            switch (this.facingDirection) {
                                case NORTH:
                                    routeSteps += "r";
                                    break;
                                case EAST:
                                    routeSteps += "f";
                                    break;
                                case SOUTH:
                                    routeSteps += "l";
                                    break;
                                case WEST:
                                    routeSteps += "b";
                                    break;
                            }
                            this.facingDirection = Directions.EAST;
                        }
                        for (int i = 1; i < Math.abs(this.buttonList[step].getX() - this.buttonList[routeOrder.get(routeOrder.indexOf(step) + 1)].getX()); i++) {
                            routeSteps += "f";
                        }

                        if (this.buttonList[step].getY() - this.buttonList[routeOrder.get(routeOrder.indexOf(step) + 1)].getY() > 0) { // Needs to go north
                            switch (this.facingDirection) {
                                case NORTH:
                                    routeSteps += "f";
                                    break;
                                case EAST:
                                    routeSteps += "l";
                                    break;
                                case SOUTH:
                                    routeSteps += "b";
                                    break;
                                case WEST:
                                    routeSteps += "r";
                                    break;
                            }
                            this.facingDirection = Directions.NORTH;
                        }
                        else if (this.buttonList[step].getY() - this.buttonList[routeOrder.get(routeOrder.indexOf(step) + 1)].getY() < 0) { // Needs to go south
                            switch (this.facingDirection) {
                                case NORTH:
                                    routeSteps += "b";
                                    break;
                                case EAST:
                                    routeSteps += "r";
                                    break;
                                case SOUTH:
                                    routeSteps += "f";
                                    break;
                                case WEST:
                                    routeSteps += "l";
                                    break;
                            }
                            this.facingDirection = Directions.SOUTH;
                        }
                        for (int i = 1; i < Math.abs(this.buttonList[step].getY() - this.buttonList[routeOrder.get(routeOrder.indexOf(step) + 1)].getY()); i++) {
                            routeSteps += "f";
                        }






                        routeSteps += "!";
                    }
                    else {
                        System.out.println(routeSteps);
                    }
                }
            }
        }
    }

