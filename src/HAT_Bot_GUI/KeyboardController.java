package HAT_Bot_GUI;

import javafx.scene.input.KeyEvent;

public class KeyboardController {
    private BluetoothController bluetoothController;

    public KeyboardController(BluetoothController bluetoothController) {
        this.bluetoothController = bluetoothController;
    }

    public void keyPressedHandler(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                System.out.println("Forward");
                break;
            case A:
                System.out.println("Left");
                break;
            case S:
                System.out.println("Backwards");
                break;
            case D:
                System.out.println("Right");
                break;
            case NUMPAD1:
                System.out.println("goToSpeed 10");
                break;
            case NUMPAD2:
                System.out.println("goToSpeed 20");
                break;
            case NUMPAD3:
                System.out.println("goToSpeed 30");
                break;
            case NUMPAD4:
                System.out.println("goToSpeed 40");
                break;
            case NUMPAD5:
                System.out.println("goToSpeed 50");
                break;
            case NUMPAD6:
                System.out.println("goToSpeed 60");
                break;
            case NUMPAD7:
                System.out.println("goToSpeed 70");
                break;
            case NUMPAD8:
                System.out.println("goToSpeed 80");
                break;
            case NUMPAD9:
                System.out.println("goToSpeed 90");
                break;
            case NUMPAD0:
                System.out.println("goToSpeed 100");
                break;
            case DECIMAL:
                System.out.println("goToSpeed 0");
                break;
            case L:
                System.out.println("DISCO POLO TRINGELINGELING");
                break;
            case ENTER: // werkt niet (klikt op de geselecteerde knop)
                System.out.println("E-Break");
                break;
        }
    }
}
