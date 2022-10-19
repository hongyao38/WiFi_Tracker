package entity;

import main.AppPanel;
import ui.AddRouterButton;
import ui.Button;
import ui.ViewPortButton;

import java.awt.event.MouseEvent;

import java.util.List;
import java.util.ArrayList;

public class Map extends Button {

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

    public Map(int x, int y, int width, int height, AppPanel ap) {
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
        routers.add(new Router(100, 100));
    }

    @Override
    public void onLeftClick(MouseEvent e) {
        if (!inAddRouterMode) return;

        Router newRouter = new Router(e.getX()-10-x, e.getY()-20-y);
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
