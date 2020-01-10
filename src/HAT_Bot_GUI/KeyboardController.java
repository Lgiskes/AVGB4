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
                bluetoothController.sendBinary( BluetoothCommands.FORWARD) ;
                break;
            case A:
                bluetoothController.sendBinary(BluetoothCommands.LEFT);
            break;
            case S:
                bluetoothController.sendBinary(BluetoothCommands.BACKWARD);
                break;
            case D:
                bluetoothController.sendBinary(BluetoothCommands.RIGHT);
                break;
            case NUMPAD1:
                bluetoothController.sendBinary(BluetoothCommands.SPEED10);
                break;
            case NUMPAD2:
                bluetoothController.sendBinary(BluetoothCommands.SPEED20);
                break;
            case NUMPAD3:
                bluetoothController.sendBinary(BluetoothCommands.SPEED30);
                break;
            case NUMPAD4:
                bluetoothController.sendBinary(BluetoothCommands.SPEED40);
                break;
            case NUMPAD5:
                bluetoothController.sendBinary(BluetoothCommands.SPEED50);
                break;
            case NUMPAD6:
                bluetoothController.sendBinary(BluetoothCommands.SPEED60);
                break;
            case NUMPAD7:
                bluetoothController.sendBinary(BluetoothCommands.SPEED70);
                break;
            case NUMPAD8:
                bluetoothController.sendBinary(BluetoothCommands.SPEED80);
                break;
            case NUMPAD9:
                bluetoothController.sendBinary(BluetoothCommands.SPEED90);
                break;
            case NUMPAD0:
                bluetoothController.sendBinary(BluetoothCommands.SPEED100);
                break;
            case DECIMAL:
                bluetoothController.sendBinary(BluetoothCommands.STOP);
                break;
            case L:
                bluetoothController.sendBinary(BluetoothCommands.LIGHTS);
                break;
            case M:
                bluetoothController.sendBinary(BluetoothCommands.MUTE);
            case ENTER:
                bluetoothController.sendBinary(BluetoothCommands.EMERGENCY_BRAKE);
                break;
        }
    }
}
