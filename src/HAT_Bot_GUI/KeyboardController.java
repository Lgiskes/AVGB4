package HAT_Bot_GUI;

import HAT_Bot.Hardware.Sensors.Bluetooth;
import javafx.scene.input.KeyEvent;

/**
 * Reads the keyboard input
 */

public class KeyboardController {
    private BluetoothController bluetoothController;

    public KeyboardController(BluetoothController bluetoothController) {
        this.bluetoothController = bluetoothController;
    }

    public void keyPressedHandler(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                this.bluetoothController.sendBinary( BluetoothCommands.FORWARD) ;
                break;
            case A:
                this.bluetoothController.sendBinary(BluetoothCommands.LEFT);
            break;
            case S:
                this.bluetoothController.sendBinary(BluetoothCommands.BACKWARD);
                break;
            case D:
                this.bluetoothController.sendBinary(BluetoothCommands.RIGHT);
                break;
            case NUMPAD1:
                this.bluetoothController.sendBinary(BluetoothCommands.SPEED10);
                break;
            case NUMPAD2:
                this.bluetoothController.sendBinary(BluetoothCommands.SPEED20);
                break;
            case NUMPAD3:
                this.bluetoothController.sendBinary(BluetoothCommands.SPEED30);
                break;
            case NUMPAD4:
                this.bluetoothController.sendBinary(BluetoothCommands.SPEED40);
                break;
            case NUMPAD5:
                this.bluetoothController.sendBinary(BluetoothCommands.SPEED50);
                break;
            case NUMPAD6:
                this.bluetoothController.sendBinary(BluetoothCommands.SPEED60);
                break;
            case NUMPAD7:
                this.bluetoothController.sendBinary(BluetoothCommands.SPEED70);
                break;
            case NUMPAD8:
                this.bluetoothController.sendBinary(BluetoothCommands.SPEED80);
                break;
            case NUMPAD9:
                this.bluetoothController.sendBinary(BluetoothCommands.SPEED90);
                break;
            case NUMPAD0:
                this.bluetoothController.sendBinary(BluetoothCommands.SPEED100);
                break;
            case DECIMAL:
                this.bluetoothController.sendBinary(BluetoothCommands.STOP);
                break;
            case L:
                this.bluetoothController.sendBinary(BluetoothCommands.LIGHTS);
                break;
            case M:
                this.bluetoothController.sendBinary(BluetoothCommands.MUTE);
            case ENTER:
                this.bluetoothController.sendBinary(BluetoothCommands.EMERGENCY_BRAKE);
                break;
        }
    }
}
