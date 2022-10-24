package entity;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.AppPanel;

public class Person {
    
    private AppPanel ap;

    private List<int[]> locations = new ArrayList<>();

    public Person(AppPanel ap) {
        this.ap = ap;

        // // Dummy location
        // locations.add(new int[]{200, 200});
        // locations.add(new int[]{220, 200});
        // locations.add(new int[]{230, 210});
        // locations.add(new int[]{240, 220});
        // locations.add(new int[]{250, 230});
    }

    public void triangulate() {

        List<Map<String, Double>> rssiValues = MapLayout.readRssiValues();

        for (Map<String, Double> m : rssiValues) {
            Set<String> ids = m.keySet();

            // Assign RSSI values to routers
            for (String id : ids) {
                Router router = ap.map.getRouter(id);
                if (router == null) {
                    return;
                }
                router.setRSSI(m.get(id));
            }

            // Triangulation Formula
            /*
             * TODO: INSERT FORMULA HERE
             * - Generate {x, y}
             * - Store into locations arraylist
             */
        }
    }

    public void renderPerson(Graphics2D g2) {
        for (int[] l : locations) {
            int x = l[0];
            int y = l[1];

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 19F));
            g2.drawString("X", x + ap.map.x, y + ap.map.y);
        }
    }
}
