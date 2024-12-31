package utils;

public class Waypoint {
    private int x;
    private int y;
    private boolean allowVerticalMovement;

    public Waypoint(int x, int y) {
        this.x = x;
        this.y = y;
        this.allowVerticalMovement = false;
    }

    public Waypoint(int x, int y, boolean allowVerticalMovement) {
        this.x = x;
        this.y = y;
        this.allowVerticalMovement = allowVerticalMovement;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setAllowVerticalMovement(boolean allowVerticalMovement) {
        this.allowVerticalMovement = allowVerticalMovement;
    }

    public boolean getAllowVerticalMovement() {
        return this.allowVerticalMovement;
    }

}
