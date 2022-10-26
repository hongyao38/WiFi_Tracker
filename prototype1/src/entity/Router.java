package entity;

public class Router {

    private static int idSequence = 1;
    public double spriteNum = 0;

    // Router pixel position
    public int x;
    public int y;
    public int width = 32;
    public int height = 32;

    // Router properties
    public final String ID;
    private double frequency = 5000; // Mhz
    private double rssi; // Db

    public Router(int x, int y) {
        this.x = x;
        this.y = y;

        // Assign an ID to router
        ID = "" + idSequence++;
    }

    public void setRSSI(double rssi) {
        this.rssi = rssi;
    }

    public double getFrequency() {
        return frequency;
    }

    public double getRssi() {
        return rssi;
    }

    protected void enlarge() {
        x -= 4;
        y -= 4;
        width += 8;
        height += 8;
    }

    protected void shrink() {
        x += 4;
        y += 4;
        width -= 8;
        height -= 8;
    }

    public void animate() {
        spriteNum += 0.1;
        if (spriteNum > 8) {
            spriteNum = 0;
        }
    }
}
