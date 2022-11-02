package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ejml.simple.SimpleMatrix;

import main.AppPanel;

public class Person {
    
    private AppPanel ap;

    public List<int[]> locations = new ArrayList<>();
    public double spriteNum;

    public Person(AppPanel ap) {
        this.ap = ap;
    }

    public void triangulate() {
        locations.clear();
        List<Map<String, Double>> rssiValues = MapLayout.readRssiValues();

        for (Map<String, Double> m : rssiValues) {
            Set<String> ids = m.keySet();
            List<Map<Router, Double>> routerList = new ArrayList<>();

            // Assign RSSI values to routers
            for (String id : ids) {
                Map<Router, Double> routerMap = new HashMap<>();
                Router router = ap.map.getRouter(id);
                if (router == null) {
                    return;
                }
                router.enlarge();
                router.setRSSI(m.get(id));
                waitTime(100);
                router.shrink();
                
                // Calculate approximate distance to router and store in map + add to ArrayList
                double distance = this.getEstimatedDistanceWithRSSIValue(router);
                // System.out.println(String.format("Distance from Router %s = %.2f", id, distance));
                routerMap.put(router, distance);
                routerList.add(routerMap);
            }

            SimpleMatrix matrixA = null;
            SimpleMatrix matrixB = null;
            double nX = 0;
            double nY = 0;
            Double nDistance = 0.0;

            // Get nth element in ArrayList
            Map<Router, Double> nMap = routerList.remove(0);
            for (Router r : nMap.keySet()) {
                nDistance = nMap.get(r);
                nX = r.x;
                nY = r.y;
            }

            // Get rest of the elements and fill up matrix A and B
            
            for (Map<Router, Double> tempMap : routerList) {
                // Getting all the variables
                Router r = null;
                Double tempDistance = null;

                for (Map.Entry<Router, Double> entry : tempMap.entrySet()) {
                    r = entry.getKey();
                    tempDistance = entry.getValue();
                }

                if (r == null || tempDistance == null) {
                    continue;
                }

                double tempX = r.x;
                double tempY = r.y;

                SimpleMatrix tempARow = new SimpleMatrix(new double[][]{ {2 * (tempX - nX), 2 * (tempY - nY)} } );
                SimpleMatrix tempBRow = new SimpleMatrix(new double[][]{ {Math.pow(tempX, 2) + Math.pow(tempY, 2) - Math.pow(nX, 2) - Math.pow(nY, 2) - Math.pow(tempDistance, 2) + Math.pow(nDistance, 2)} } );

                if (matrixA == null) {
                    matrixA = tempARow;
                } else {
                    matrixA = matrixA.concatRows(tempARow);
                }

                if (matrixB == null) {
                    matrixB = tempBRow;
                } else {
                    matrixB = matrixB.concatRows(tempBRow);
                }
            }

            SimpleMatrix matrixATransposed = matrixA.transpose();
            SimpleMatrix projectionMatrix = ((matrixATransposed.mult(matrixA)).invert()).mult(matrixATransposed);

            SimpleMatrix resultMatrix = projectionMatrix.mult(matrixB);

            int xCoord = ((int)(resultMatrix.get(0, 0)));
            int yCoord = ((int)(resultMatrix.get(1, 0)));

            locations.add(new int[]{xCoord, yCoord});
            waitTime(200);
        }
        // for (int[] l : locations) {
        //     System.out.println("here: " + Arrays.toString(l));
        // }
    }

    private double getEstimatedDistanceWithRSSIValue(Router router) {
        double freqInMHz = router.getFrequency();
        double levelInDb = router.getRssi();

        double exp = (27.55 - (20 * Math.log10(freqInMHz)) + Math.abs(levelInDb)) / 20.0;
        return Math.pow(10.0, exp);
    }

    private void waitTime(int duration) {
        // Wait at least 1s
        long previousTime = System.currentTimeMillis();
        long interval = 0;

        while (interval < duration) {
            long currentTime = System.currentTimeMillis();
            interval = currentTime - previousTime;
        }
    }

    public void animate() {
        spriteNum += 0.1;
        if (spriteNum > 8) {
            spriteNum = 0;
        }
    }
}
