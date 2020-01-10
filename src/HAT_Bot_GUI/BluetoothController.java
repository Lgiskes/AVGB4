package HAT_Bot_GUI;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * Controls the information that is being sent to the BoeBot
 */

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

    /**
     * Sends a string to the BoeBot
     * @param text the String that is being sent.
     */
    public void sendString(String text) {
        try{
            this.serialPort.writeString(text);
        }
        catch (SerialPortException ex){
            System.out.println(ex.getMessage());
        }
    }


    /**
     * Sends a byte to the BoeBot
     * @param binary the byte that is being sent.
     */
    public void sendBinary(byte binary) {
        try{
            serialPort.writeByte(binary);
        }
        catch (SerialPortException ex){
            System.out.println(ex.getMessage());
        }
    }

}
