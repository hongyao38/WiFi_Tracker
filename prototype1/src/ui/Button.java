package ui;

import javax.swing.JComponent;

import java.awt.event.MouseEvent;

import controls.MouseHandler;
import main.AppPanel;

public abstract class Button extends JComponent {
    
    public AppPanel ap;
    private MouseHandler mouse;

    // Button position and dimension
    public int x;
    public int y;
    protected int width;
    protected int height;

    // Button states
    protected int stateNumber;

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
}