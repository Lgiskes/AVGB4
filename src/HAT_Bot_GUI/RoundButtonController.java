package HAT_Bot_GUI;

import java.util.ArrayList;

public class RoundButtonController {
    private String name;
    private int X;
    private int y;
    private int maxOptions;
    private int buttonState;
    private String[] alphabet = {" ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public RoundButtonController(String name, int x, int y) {
        this.name = name;
        X = x;
        this.y = y;
        this.buttonState = 0;
    }

    public void setMaxOptions(int amount) {
        this.maxOptions = Math.max(amount, 2);
        this.maxOptions = Math.min(this.maxOptions, 26);
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return y;
    }

    public void buttonPressed() {
        this.buttonState++;
        if (this.buttonState > this.maxOptions) {
            this.buttonState = 0;
        }
        this.name = alphabet[this.buttonState];
    }

    public int getButtonState() {
        return buttonState;
    }

    public void setButtonState(int buttonState) {
        this.buttonState = buttonState;
        if (buttonState > this.maxOptions) {
            this.buttonState = 0;
        }
        this.name = alphabet[this.buttonState];
    }
}
