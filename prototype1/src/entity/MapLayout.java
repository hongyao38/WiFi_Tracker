package entity;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map;

import main.AppPanel;
import ui.AddRouterButton;
import ui.Button;
import ui.ViewPortButton;

public class MapLayout extends Button {

    // Buttons
    private ViewPortButton moveLeftButton = new ViewPortButton(0, 0, ap.TILE_SIZE, ap.SCREEN_HEIGHT, ap, "LEFT");
    private ViewPortButton moveRightButton = new ViewPortButton(ap.SCREEN_WIDTH - ap.TILE_SIZE, 0, ap.TILE_SIZE, ap.SCREEN_HEIGHT, ap, "RIGHT");
    private ViewPortButton moveUpButton = new ViewPortButton(0, 0, ap.SCREEN_WIDTH, ap.TILE_SIZE, ap, "UP");
    private ViewPortButton moveDownButton = new ViewPortButton(0, ap.SCREEN_HEIGHT - ap.TILE_SIZE, ap.SCREEN_WIDTH, ap.TILE_SIZE, ap, "DOWN");

    // Map Parameters
    public boolean inAddRouterMode; 
    public AddRouterButton addRouterBtn;

    // Routers
    public List<Router> routers = new ArrayList<>();

    public MapLayout(int x, int y, int width, int height, AppPanel ap) {
        super(x, y, width, height, ap);

        // Add view port hover buttons
        this.add(moveLeftButton);
        this.add(moveRightButton);
        this.add(moveUpButton);
        this.add(moveDownButton);

        // Add "addNewRouterButton"
        addRouterBtn = new AddRouterButton(ap);
        this.add(addRouterBtn);

        // Create a dummy router
        routers.add(new Router(100, 100, ap));
    }

    public static List<Map<String, Double>> readRssiValues() {
        List<Map<String, Double>> rssiValues = new ArrayList<>();

        try {
            File file = new File("app/res/rssi/rssi_values.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                Map<String, Double> routerRssi = new HashMap<>();
                String[] values = sc.nextLine().split(",");
                for (String v : values) {
                    String[] idToRssi = v.split(":");
                    routerRssi.put(idToRssi[0], Double.parseDouble(idToRssi[1]));
                }
                rssiValues.add(routerRssi);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // To visualise result
        for (Map<String, Double> m : rssiValues) {
            System.out.println(m);
        }
        return rssiValues;
    }

    public Router getRouter(String id) {
        for (Router r : routers) {
            if (r.ID.equals(id)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public void onLeftClick(MouseEvent e) {
        if (!inAddRouterMode) return;

        Router newRouter = new Router(e.getX()-10-x, e.getY()-20-y, ap);
        routers.add(newRouter);
    }

    @Override
    public void onRightClick(MouseEvent e) {
        System.out.println("Right-clicked map");
    }

    @Override
    public void onMouseEnter() {}

    @Override
    public void onMouseExit() {}


    public void updateViewPort() {
        moveLeftButton.updateViewPort();
        moveRightButton.updateViewPort();
        moveUpButton.updateViewPort();
        moveDownButton.updateViewPort();
    }
}
