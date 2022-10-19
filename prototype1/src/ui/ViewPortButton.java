package ui;

import java.awt.event.MouseEvent;

import main.AppPanel;

public class ViewPortButton extends Button {

    private final String DIRECTION;
    private final int VIEW_PORT_SPEED = 6;

    public boolean movingUp;
    public boolean movingDown;
    public boolean movingLeft;
    public boolean movingRight;

    public ViewPortButton(int x, int y, int width, int height, AppPanel ap, String DIRECTION) {
        super(x, y, width, height, ap);
        this.DIRECTION = DIRECTION;
    }

    @Override
    public void onMouseEnter() {
        if (DIRECTION.equals("UP")) {
            movingUp = true;
        }
        else if (DIRECTION.equals("DOWN")) {
            movingDown = true;
        }
        else if (DIRECTION.equals("LEFT")) {
            movingLeft = true;
        }
        else if (DIRECTION.equals("RIGHT")) {
            movingRight = true;
        }
    }

    @Override
    public void onMouseExit() {
        movingUp = false;
        movingDown = false;
        movingLeft = false;
        movingRight = false;
    }

    public void updateViewPort() {
        if (movingUp) ap.map.y += VIEW_PORT_SPEED;
        else if (movingDown) ap.map.y -= VIEW_PORT_SPEED;
        else if (movingLeft) ap.map.x += VIEW_PORT_SPEED;
        else if (movingRight) ap.map.x -= VIEW_PORT_SPEED;
    }

    @Override
    public void onLeftClick(MouseEvent e) {}

    @Override
    public void onRightClick(MouseEvent e) {}
}
