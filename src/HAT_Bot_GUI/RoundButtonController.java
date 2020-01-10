package HAT_Bot_GUI;

import java.util.ArrayList;

/**
 * Controls the round buttons for indicating a route
 */

public class RoundButtonController {
    private String name;
    private int X;
    private int y;
    private int maxOptions;
    private int buttonState;
    private String[] alphabet = {" ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public RoundButtonController(String name, int x, int y) {
        this.name = name;
        this.X = x;
        this.y = y;
        this.buttonState = 0;
    }

    /**
     * How many possible points are available for use.
     * @param amount the amount of point currently being ussed
     */
    public void setMaxOptions(int amount) {
        this.maxOptions = Math.max(amount, 2);
        this.maxOptions = Math.min(this.maxOptions, 26);
    }

    public String getName() {
        return this.name;
    }

    public int getX() {
        return this.X;
    }

    public int getY() {
        return this.y;
    }

    /**
     * Sets a point on the route
     */

    public void buttonPressed() {
        this.buttonState++;
        if (this.buttonState > this.maxOptions) {
            this.buttonState = 0;
        }
        this.name = alphabet[this.buttonState];
    }

    public int getButtonState() {
        return this.buttonState;
    }

    public void setButtonState(int buttonState) {
        this.buttonState = buttonState;
        if (buttonState > this.maxOptions) {
            this.buttonState = 0;
        }
        this.name = alphabet[this.buttonState];
    }
}
