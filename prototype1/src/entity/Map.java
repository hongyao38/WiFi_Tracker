package entity;

import main.AppPanel;
import ui.Button;
import ui.ViewPortButton;

import java.util.List;

import java.awt.event.MouseEvent;

import java.util.ArrayList;

public class Map extends Button {

    // View Port hover buttons
    public ViewPortButton moveLeftButton = new ViewPortButton(0, 0, ap.TILE_SIZE, ap.SCREEN_HEIGHT, ap, "LEFT");
    public ViewPortButton moveRightButton = new ViewPortButton(ap.SCREEN_WIDTH - ap.TILE_SIZE, 0, ap.TILE_SIZE, ap.SCREEN_HEIGHT, ap, "RIGHT");
    public ViewPortButton moveUpButton = new ViewPortButton(0, 0, ap.SCREEN_WIDTH, ap.TILE_SIZE, ap, "UP");
    public ViewPortButton moveDownButton = new ViewPortButton(0, ap.SCREEN_HEIGHT - ap.TILE_SIZE, ap.SCREEN_WIDTH, ap.TILE_SIZE, ap, "DOWN");

    // Routers
    public List<Router> routers = new ArrayList<>();

    public Map(int x, int y, int width, int height, AppPanel ap) {
        super(x, y, width, height, ap);

        // Add view port hover buttons
        this.add(moveLeftButton);
        this.add(moveRightButton);
        this.add(moveUpButton);
        this.add(moveDownButton);

        // Create a dummy router
        routers.add(new Router(100, 100));
    }

    @Override
    public void onLeftClick(MouseEvent e) {
        Router newRouter = new Router(e.getX() - x, e.getY() - y);
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
    
}
