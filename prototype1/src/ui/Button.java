package ui;

import javax.swing.JComponent;

import java.awt.event.MouseEvent;

import controls.MouseHandler;
import main.AppPanel;

public abstract class Button extends JComponent {
    
    public AppPanel ap;
    private MouseHandler mouse;

    // Button position and dimension
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    // Button states
    private int stateNumber;

    public Button(int x, int y, int width, int height, AppPanel ap) {
        this.ap = ap;

        // Set button position and bounds
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.setBounds(x, y, width, height);

        // Create and add mouse listener
        mouse = new MouseHandler(this);
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
    }

    public abstract void onLeftClick(MouseEvent e);

    public abstract void onRightClick(MouseEvent e);

    public abstract void onMouseEnter();

    public abstract void onMouseExit();


    /*
     * Simple hover animations
     */
    private void enlarge() {
        x -= 4;
        y -= 4;
        width += 8;
        height += 8;
    }

    private void shrink() {
        x += 4;
        y += 4;
        width -= 8;
        height -= 8;
    }
}