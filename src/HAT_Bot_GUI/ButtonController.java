package HAT_Bot_GUI;

import java.util.ArrayList;

public class ButtonController {
    private String text;
    private byte command;
    private int x;
    private int y;
    private int buttonSize;

    public ButtonController(String text, int command, int x, int y, int buttonSize) {
        this.text = text;
        this.command = (byte)command;
        this.x = x;
        this.y = y;
        this.buttonSize = buttonSize;
    }

    public String getText() {
        return text;
    }

    public byte getCommand() {
        return command;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getButtonSize() {
        return buttonSize;
    }
}
