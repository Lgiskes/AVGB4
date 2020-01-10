package HAT_Bot_GUI;

/**
 * Controls the GUI buttons with a few more attributes
 */

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
        return this.text;
    }

    public byte getCommand() {
        return this.command;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getButtonSize() {
        return this.buttonSize;
    }
}
