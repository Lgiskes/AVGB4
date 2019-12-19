package HAT_Bot.Controllers;

import HAT_Bot.Hardware.Sensors.Bluetooth;

public class BluetoothController implements Updatable {

    private Bluetooth bluetooth;
    private RemoteControl remoteControl;

    public BluetoothController(RemoteControl remoteControl) {
        this.remoteControl = remoteControl;
        this.bluetooth = new Bluetooth();
    }


    @Override
    public void update() {

        if (this.bluetooth.getBoolean()){
            int btValue = this.bluetooth.getValue();
            this.remoteControl.actions(btValue);

        }
    }
}
