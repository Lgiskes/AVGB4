package HAT_Bot_GUI;

public class RoundButtonController {
    private String name;
    private int X;
    private int y;
    private int ButtonSize;

    public RoundButtonController(String name, int x, int y) {
        this.name = name;
        X = x;
        this.y = y;
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

    public int getButtonSize() {
        return ButtonSize;
    }
}
