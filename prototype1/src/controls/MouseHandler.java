package controls;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import ui.Button;

public class MouseHandler implements MouseInputListener {

    private Button button;

    public MouseHandler(Button button) {
        this.button = button;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            button.onLeftClick(e);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            button.onRightClick(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        button.onMouseEnter();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        button.onMouseExit();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
    
}
