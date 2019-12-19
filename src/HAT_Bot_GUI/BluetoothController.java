package HAT_Bot_GUI;

import jssc.SerialPort;
import jssc.SerialPortException;

public class BluetoothController {
    private String port;
    private SerialPort serialPort;

    public BluetoothController(String port) {
        this.port = port;

        this.serialPort = new SerialPort(this.port);
        try{
            this.serialPort.openPort();

            this.serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        }
        catch (SerialPortException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void sendString(String text) {
        try{
            serialPort.writeString(text);
        }
        catch (SerialPortException ex){
            System.out.println(ex.getMessage());
        }
    }


    public void sendBinary(byte bin) {
        try{
            serialPort.writeByte(bin);
        }
        catch (SerialPortException ex){
            System.out.println(ex.getMessage());
        }
    }

}
