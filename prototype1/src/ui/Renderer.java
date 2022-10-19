package ui;

import main.AppPanel;
import entity.Router;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;

import javax.imageio.ImageIO;

import java.io.FileInputStream;
import java.io.IOException;

public class Renderer {
    
    private AppPanel ap;

    // UI image library
    private BufferedImage[] imgs = new BufferedImage[10];
    private int imgCount;

    public Renderer(AppPanel ap) {
        this.ap = ap;

        // Initialise image library
        addNewImg("app/res/map/map.jpg");                 // 0
        addNewImg("app/res/router/router.png");           // 1
        addNewImg("app/res/buttons/add_router_btn0.png"); // 2
        addNewImg("app/res/buttons/add_router_btn1.png"); // 3
    }


    /*
     * Opens from filepath as a new BufferedImage
     * to add to imgs library array
     */
    private void addNewImg(String filepath) {
        BufferedImage newImg = null;

        try {
            newImg = ImageIO.read(new FileInputStream(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgs[imgCount++] = newImg;
    }


    /*
     * Render the map onto the screen
     */
    public void renderMap(Graphics2D g2) {
        Color c = new Color(40, 40, 40);
        g2.setColor(c);
        g2.fillRect(-700, -700, 4000, 4000);
        g2.drawImage(imgs[0], ap.map.x, ap.map.y, ap.map.width, ap.map.height, null);

        // Transparent overlay for adding routers
        if (ap.map.inAddRouterMode) {
            c = new Color(30, 30, 30, 50);
            g2.setColor(c);
            g2.fillRect(-700, -700, 4000, 4000);
        }
    }


    /*
     * Render all the routers onto screen
     */
    public void renderRouters(Graphics2D g2) {
        for (Router r : ap.map.routers) {
            g2.drawImage(imgs[1], r.x + ap.map.x, r.y + ap.map.y, ap.TILE_SIZE/2, ap.TILE_SIZE/2, null);
        }
    }

    /*
     * Renders UI buttons
     */
    public void renderUiButtons(Graphics2D g2) {
        Button btn = ap.map.addRouterBtn;
        btn.stateNumber = ap.map.inAddRouterMode ? 1 : 0;
        g2.drawImage(imgs[2 + btn.stateNumber], btn.x, btn.y, btn.width, btn.height, null);
    }
}
