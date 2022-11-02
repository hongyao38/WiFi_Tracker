package ui;

import main.AppPanel;
import entity.Router;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;

import javax.imageio.ImageIO;

import java.io.FileInputStream;
import java.io.IOException;

public class Renderer {

    private AppPanel ap;

    // UI image library
    private BufferedImage[] imgs = new BufferedImage[30];
    private int imgCount;

    public Renderer(AppPanel ap) {
        this.ap = ap;

        // Initialise image library
        addNewImg("app/res/map/t_junction.png");              // 0
        for (int i = 0; i < 8; i++) {
            addNewImg("app/res/router/router" + i + ".png");           // 1
        }
        addNewImg("app/res/buttons/add_router_btn0.png");     // 9
        addNewImg("app/res/buttons/add_router_btn1.png");     // 10
        addNewImg("app/res/buttons/track_location_btn0.png"); // 11
        for (int i = 0; i < 4; i++) {
            addNewImg("app/res/buttons/view_port" + i + ".png");        // 12
        }
        for (int i = 0; i < 8; i++) {
            addNewImg("app/res/person/person" + i + ".png");            // 16
        }
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
        Color c = new Color(64, 64, 64);
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
            // Draw router itself
            g2.drawImage(imgs[(int)(1 + r.spriteNum)], r.x + ap.map.x, r.y + ap.map.y, r.width, r.height, null);

            // Draw ID number circle
            g2.setColor(new Color(255, 255, 255));
            g2.fillRoundRect(r.x + ap.map.x + 24, r.y + ap.map.y + 24, 29, 18, 9, 9);

            // Draw Outline
            g2.setColor(new Color(0, 0, 0));
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(r.x + ap.map.x + 24, r.y + ap.map.y + 24, 29, 18, 9, 9);

            // Write ID number
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 19F));
            g2.drawString(r.ID, r.x + ap.map.x + 27, r.y + ap.map.y + 40);
        }
    }

    /*
     * Renders UI buttons
     */
    public void renderUiButtons(Graphics2D g2) {
        // Add Router
        Button btn = ap.map.addRouterBtn;
        int btnState = ap.map.inAddRouterMode ? 1 : 0;
        g2.drawImage(imgs[9 + btnState], btn.x, btn.y, btn.width, btn.height, null);

        // Track Location
        btn = ap.map.trackNewLocationBtn;
        g2.drawImage(imgs[11], btn.x, btn.y, btn.width, btn.height, null);
    }


    /*
     * Render view port arrows
     */
    public void renderViewPort(Graphics2D g2) {
        if (ap.map.isMovingLeft()) {
            g2.drawImage(imgs[12], 20, ap.SCREEN_HEIGHT / 2 - ap.TILE_SIZE / 2, ap.TILE_SIZE, ap.TILE_SIZE, null);
        }
        if (ap.map.isMovingRight()) {
            g2.drawImage(imgs[13], ap.SCREEN_WIDTH - ap.TILE_SIZE - 20, ap.SCREEN_HEIGHT / 2 - ap.TILE_SIZE / 2, ap.TILE_SIZE, ap.TILE_SIZE, null);
        }
        if (ap.map.isMovingUp()) {
            g2.drawImage(imgs[14], ap.SCREEN_WIDTH / 2 - ap.TILE_SIZE / 2, 20, ap.TILE_SIZE, ap.TILE_SIZE, null);
        }
        if (ap.map.isMovingDown()) {
            g2.drawImage(imgs[15], ap.SCREEN_WIDTH / 2 - ap.TILE_SIZE / 2, ap.SCREEN_HEIGHT - ap.TILE_SIZE - 20, ap.TILE_SIZE, ap.TILE_SIZE, null);
        }
    }


    /*
     * Render person on screen
     */
    public void renderPerson(Graphics2D g2) {
        for (int[] l : ap.person.locations) {
            int x = l[0];
            int y = l[1];
            g2.drawImage(imgs[16 + (int)ap.person.spriteNum], x + ap.map.x, y + ap.map.y, null);
        }
    }
}
