package ui;

import java.awt.event.MouseEvent;

import main.AppPanel;

public class AddRouterButton extends Button {
    
    public AddRouterButton(AppPanel ap) {
        super(13 * ap.TILE_SIZE, 10 * ap.TILE_SIZE, 149, 45, ap);
    }

    @Override
    public void onLeftClick(MouseEvent e) {
        ap.map.inAddRouterMode = !ap.map.inAddRouterMode;
    }

    @Override
    public void onRightClick(MouseEvent e) {}

    @Override
    public void onMouseEnter() {
        enlarge();
    }

    @Override
    public void onMouseExit() {
        shrink();
    }
}
