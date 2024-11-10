package models.tiles;

public class Tile {
    private boolean isSolid;
    private double x;
    private double y;

    public Tile(boolean isSolid, double startX, double startY) {
        this.isSolid = isSolid;
        this.x = startX;
        this.y = startY;
    }

    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setSolid(boolean isSolid) {
        this.isSolid = isSolid;
    }

    public boolean getIsSolid() {
        return this.isSolid;
    }
}
