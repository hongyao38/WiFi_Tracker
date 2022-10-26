package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import ui.Renderer;
import entity.MapLayout;
import entity.Person;

public class AppPanel extends JPanel implements Runnable {

    // TILE SETTINGS
    public final int TILE_SIZE = (int)(64 * 1);

    // SCREEN SETTINGS
    public final int FPS = 60;
    public final int MAX_SCREEN_COL = 16;
    public final int MAX_SCREEN_ROW = 12;
    public final int SCREEN_WIDTH = MAX_SCREEN_COL * TILE_SIZE;
    public final int SCREEN_HEIGHT = MAX_SCREEN_ROW * TILE_SIZE;

    // APPLICATION PARAMETERS
    Thread appThread;
    Renderer renderer = new Renderer(this);
    public MapLayout map = new MapLayout(0, 0, 2482, 1232, this);
    public Person person = new Person(this);

    /*
     * Constructor for AppPanel
     * Sets the screen size
     * and initializes any objects belonging to the app
     */
    public AppPanel() {

        // Screen size settings
        this.setPreferredSize(new Dimension(SCREEN_WIDTH ,SCREEN_HEIGHT));
        this.setDoubleBuffered(true);
        this.setLayout(null);

        // Add components
        this.add(map);
        // person.triangulate();
    }


    /*
     * Starts a new thread for the application
     */
    public void startThread() {
        appThread = new Thread(this);
        appThread.start();
    }


    /*
     * Creates the application loop of updating attributes
     * and rendering images onto screen
     */
    @Override
    public void run() {

        // Limit loop speed to FPS
        double drawInterval = 1000000000 / FPS; // 1 second / FPS
        double delta = 0;
        long lastTime = System.nanoTime();

        while (appThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }


    /*
     * Update any changes to the application attributes
     */
    public void update() {
        // Update view port
        map.updateViewPort();
    }


    /*
     * Graphics renderer
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        // Draw map
        renderer.renderMap(g2);

        // Draw routers
        renderer.renderRouters(g2);

        // Draw UI buttons
        renderer.renderUiButtons(g2);

        // Draw person location path
        person.renderPerson(g2);
    }
    
}
