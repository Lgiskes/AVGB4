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
    private ArrayList<Button> buttons = new ArrayList<>();
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

        this.bluetoothController = new BluetoothController("COM4");
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
        emergencyBreak.setStyle("-fx-background-color: RED; -fx-text-fill: BLACK");
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
                RoundButtonController roundButton = new RoundButtonController(" ", x, y);
                this.buttonList[(x * 4) + y] = roundButton;
                Button button = new Button(roundButton.getName());
                this.buttons.add(button);
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
                                if (buttonController.getName().equals(roundButton.getName()) && buttonController.getButtonState() != 0){
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
        comboBox.getItems().add("[New Route]");
        comboBox.getItems().addAll(this.routeController.getRouteNames());
        comboBox.getSelectionModel().selectFirst();

        TextField textField = new TextField("");
        textField.promptTextProperty().set("Route name");
        textField.setPrefSize(250, 37.5);

        comboBox.setOnAction(event -> {
            String routeName = (String)comboBox.getValue();

            if(!routeName.equals("[New Route]")){
                loadGrid(this.routeController.getRoute(routeName));
                textField.setText(routeName);
            }
            else{
                loadGrid("                    ");
                textField.setText("");
            }
        });

            Button runButton = new Button("Run route");
            runButton.setPrefSize(150, 37.5);
            runButton.setOnAction( event -> {
                runButtonPressed((String)comboBox.getValue());
            });
        Button saveButton = new Button("Save route");
        saveButton.setPrefSize(150, 37.5);
        saveButton.setOnAction(event -> {
            String route = saveGrid();

            if(!textField.getText().equals("[New Route]")){
                this.routeController.addRoute(textField.getText(), route);
                comboBox.getItems().remove(0, comboBox.getItems().size());

                comboBox.getItems().add("[New Route]");
                comboBox.getItems().addAll(this.routeController.getRouteNames());
                comboBox.getSelectionModel().select(textField.getText());
            }

            System.out.println("Route saved!");

        });



            HBox hBox = new HBox();
            hBox.setSpacing(7);
            VBox vBox1 = new VBox();
            VBox vBox2 = new VBox();

            hBox.getChildren().addAll(vBox1, vBox2, emergencyBreak());

            vBox1.getChildren().addAll(comboBox, textField);
            vBox2.getChildren().addAll(runButton, saveButton);
            return hBox;
        }

        private void runButtonPressed(String routeName) {

            ArrayList<Integer> routeOrder = new ArrayList<>();
            boolean calculating = true;
            int lastButton = 0;
            while (calculating) {
                int closestButton = 99;
                int arrayPosition = 0;
                for (RoundButtonController button : this.buttonList) {
                    if (button.getButtonState() != 0 && button.getButtonState() > lastButton) {
                        if (button.getButtonState() < closestButton) {
                            closestButton = button.getButtonState();
                            arrayPosition = (button.getX()*4) + button.getY();
                        }
                    }
                }
                if (closestButton == 99) {
                    calculating = false;
                    break;
                }
                else {
                    routeOrder.add(arrayPosition);
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
                                routeSteps += "L";
                                break;
                            case EAST:
                                routeSteps += "T";
                                break;
                            case SOUTH:
                                routeSteps += "R";
                                break;
                            case WEST:
                                routeSteps += "F";
                                break;
                        }
                        this.facingDirection = Directions.WEST;
                    }
                    else if (this.buttonList[step].getX() - this.buttonList[routeOrder.get(routeOrder.indexOf(step) + 1)].getX() < 0) { // Needs to go east
                        switch (this.facingDirection) {
                            case NORTH:
                                routeSteps += "R";
                                break;
                            case EAST:
                                routeSteps += "F";
                                break;
                            case SOUTH:
                                routeSteps += "L";
                                break;
                            case WEST:
                                routeSteps += "T";
                                break;
                        }
                        this.facingDirection = Directions.EAST;
                    }
                    for (int i = 1; i < Math.abs(this.buttonList[step].getX() - this.buttonList[routeOrder.get(routeOrder.indexOf(step) + 1)].getX()); i++) {
                        routeSteps += "F";
                    }

                    if (this.buttonList[step].getY() - this.buttonList[routeOrder.get(routeOrder.indexOf(step) + 1)].getY() > 0) { // Needs to go north
                        switch (this.facingDirection) {
                            case NORTH:
                                routeSteps += "F";
                                break;
                            case EAST:
                                routeSteps += "L";
                                break;
                            case SOUTH:
                                routeSteps += "T";
                                break;
                            case WEST:
                                routeSteps += "R";
                                break;
                        }
                        this.facingDirection = Directions.NORTH;
                    }
                    else if (this.buttonList[step].getY() - this.buttonList[routeOrder.get(routeOrder.indexOf(step) + 1)].getY() < 0) { // Needs to go south
                        switch (this.facingDirection) {
                            case NORTH:
                                routeSteps += "T";
                                break;
                            case EAST:
                                routeSteps += "R";
                                break;
                            case SOUTH:
                                routeSteps += "F";
                                break;
                            case WEST:
                                routeSteps += "L";
                                break;
                        }
                        this.facingDirection = Directions.SOUTH;
                    }
                    for (int i = 1; i < Math.abs(this.buttonList[step].getY() - this.buttonList[routeOrder.get(routeOrder.indexOf(step) + 1)].getY()); i++) {
                        routeSteps += "F";
                    }

                    routeSteps += "S";
                }
                else {


                    this.bluetoothController.sendBinary((byte)255);
                    this.bluetoothController.sendString(routeSteps);
                    this.bluetoothController.sendBinary((byte)0);

                    System.out.println(routeSteps);
                }
            }
        }

        public String saveGrid() {
            String gridString = "";
            for (RoundButtonController button : this.buttonList) {
                gridString += button.getName();
            }
            return gridString;
        }

        private void loadGrid(String gridString) {
            char[] alphabet = {' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            for (int i = 0; i < 20 && i < gridString.length(); i++) {
                int number = 0;
                for (char letter : alphabet) {
                    if (letter == gridString.toUpperCase().charAt(i)) {
                        this.buttonList[i].setButtonState(number);
                        this.buttons.get(i).setText(letter+"");
                    }
                    number++;
                }
            }
        }
    }

