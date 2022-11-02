package ui;

import java.awt.event.MouseEvent;

import javax.swing.SwingWorker;

import main.AppPanel;

public class TrackLocationButton extends Button {

    public TrackLocationButton(AppPanel ap) {
        super(ap.SCREEN_WIDTH - 3 * ap.TILE_SIZE, ap.SCREEN_HEIGHT - 3 * ap.TILE_SIZE, 149, 45, ap);
    }

    @Override
    public void onLeftClick(MouseEvent e) {

        // Multi-threading to allow triangulate to run while rendering UI
        SwingWorker<String, String> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() throws Exception {
                ap.person.triangulate();
                return null;
            }
        };
        worker.execute();
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
