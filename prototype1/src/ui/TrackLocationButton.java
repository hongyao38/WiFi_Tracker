package ui;

import java.awt.event.MouseEvent;

import javax.swing.SwingWorker;

import main.AppPanel;

public class TrackLocationButton extends Button {

    public TrackLocationButton(AppPanel ap) {
        super(13 * ap.TILE_SIZE, 9 * ap.TILE_SIZE, 149, 45, ap);
    }

    @Override
    public void onLeftClick(MouseEvent e) {
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
