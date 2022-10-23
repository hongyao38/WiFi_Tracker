package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.awt.Graphics2D;

import main.AppPanel;

public class Person {
    
    private AppPanel ap;

    private List<int[]> locations = new ArrayList<>();

    public Person(AppPanel ap) {
        this.ap = ap;
    }

    public void triangulate(Graphics2D g2) {
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
        }
        // TODO: Draw image
    }
}
