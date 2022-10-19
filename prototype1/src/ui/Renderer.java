package ui;

import main.AppPanel;

import javax.imageio.ImageIO;

import entity.Router;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;

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
        addNewImg("app/res/map/map.jpg");        // 0
        addNewImg("app/res/router/router.png");  // 1
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
    }


    /*
     * Render all the routers onto screen
     */
    public void renderRouters(Graphics2D g2) {
        for (Router r : ap.map.routers) {
            g2.drawImage(imgs[1], r.x + ap.map.x, r.y + ap.map.y, ap.TILE_SIZE/2, ap.TILE_SIZE/2, null);
        }
    }
}
