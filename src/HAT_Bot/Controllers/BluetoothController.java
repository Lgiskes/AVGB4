package HAT_Bot.Controllers;

import HAT_Bot.Hardware.Sensors.Bluetooth;

import java.util.ArrayList;

public class BluetoothController implements Updatable {

    private Bluetooth bluetooth;
    private RemoteControl remoteControl;
    private RouteController routeController;
    private boolean isRemoteControlling;

    public BluetoothController(RemoteControl remoteControl, RouteController routeController) {
        this.remoteControl = remoteControl;
        this.bluetooth = new Bluetooth();
        this.routeController = routeController;
        this.isRemoteControlling = true;
    }


    @Override
    public void update() {

        if (this.bluetooth.getBoolean()){
            int btValue = this.bluetooth.getValue();


            //if the bot is being remote controlled
            if(this.isRemoteControlling){
                if(btValue == 255) {
                    this.isRemoteControlling = false;
                    this.routeController.setRoute(new ArrayList<>());
                }
                else{
                    this.remoteControl.actions(btValue);
                }
            }
            //if the bot gets a new route
            else{
                if(btValue == 0){
                    this.routeController.addCommand(RouteCommand.stop);
                    this.isRemoteControlling = true;
                }
                else{
                    RouteCommand command = RouteCommand.None;
                    switch (btValue){
                        case 70: command = RouteCommand.forward;
                        break;
                        case  82: command= RouteCommand.right;
                        break;
                        case 76: command = RouteCommand.left;
                        break;
                        case  84: command = RouteCommand.turnAround;
                        break;
                        case 83: command = RouteCommand.stop;
                        break;
                    }

                    this.routeController.addCommand(command);
                }
            }

        }
    }
}
