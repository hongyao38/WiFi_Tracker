package entity;

import main.AppPanel;

public class Router {

    private static int idSequence = 1;

    // Router position
    public int x;
    public int y;

    // Router properties
    public final String ID;
    private AppPanel ap;
    private double frequency = 2400; // Mhz
    private double rssi; // Db

    public Router(int x, int y, AppPanel ap) {
        this.x = x;
        this.y = y;
        this.ap = ap;

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
    
}
