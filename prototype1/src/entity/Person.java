package entity;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.ejml.data.*;
import org.ejml.simple.SimpleMatrix;

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
        System.out.println("Entered triangulate");

        List<Map<String, Double>> rssiValues = MapLayout.readRssiValues();

        // System.out.println("Finished gettings rssi values");

        for (Map<String, Double> m : rssiValues) {
            Set<String> ids = m.keySet();
            List<Map<Router, Double>> routerList = new ArrayList<>();

            // Assign RSSI values to routers
            // System.out.println(ids);
            for (String id : ids) {
                System.out.println(id);
                Map<Router, Double> routerMap = new HashMap<>();
                Router router = ap.map.getRouter(id);
                if (router == null) {
                    System.out.println("returned null");
                    return;
                }
                router.setRSSI(m.get(id));
                // System.out.println("Finished assigning rssi values");
                
                // Calculate approximate distance to router and store in map + add to ArrayList
                double distance = this.getEstimatedDistanceWithRSSIValue(router);
                routerMap.put(router, distance);
                routerList.add(routerMap);
                // System.out.println("Finished putting map");
            }

            SimpleMatrix matrixA = null;
            SimpleMatrix matrixB = null;
            int nX = 0;
            int nY = 0;
            Double nDistance = 0.0;

            // Get nth element in ArrayList
            Map<Router, Double> nMap = routerList.remove(routerList.size() - 1);
            for (Router r : nMap.keySet()) {
                nDistance = nMap.get(r);
                nX = r.getX();
                nY = r.getY();

                // System.out.println("nX " + nX + " nY " + nY);
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

                int tempX = r.getX();
                int tempY = r.getY();

                // System.out.println("Distance = " + tempDistance + " X = " + tempX + " Y = " + tempY);

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

                // System.out.print("Current Matrix A = ");
                // matrixA.print();
                // System.out.print("Current Matrix B = ");
                // matrixB.print();

            }
            // System.out.println("Ended loop");

            System.out.print("Matrix A = ");
            matrixA.print();
            System.out.print("Matrix B = ");
            matrixB.print();

            SimpleMatrix matrixATransposed = matrixA.transpose();
            SimpleMatrix projectionMatrix = ((matrixA.mult(matrixATransposed)).invert()).mult(matrixATransposed);

            System.out.print("Projection Matrix = ");
            projectionMatrix.print();

            SimpleMatrix resultMatrix = projectionMatrix.mult(matrixB);

            int xCoord = (int) resultMatrix.get(0, 0);
            int yCoord = (int) resultMatrix.get(1, 0);

            System.out.println("X Coord: " + xCoord + " y Coord: " + yCoord);

            locations.add(new int[]{xCoord, yCoord});
            
            // Triangulation Formula
            /*
             * TODO: INSERT FORMULA HERE
             * - Generate {x, y}
             * - Store into locations arraylist
             */

        }
        for (int[] l : locations) {
            System.out.println(Arrays.toString(l));
        }
    }

    private double getEstimatedDistanceWithRSSIValue(Router router) {
        double freqInMHz = router.getFrequency();
        double levelInDb = router.getRssi();

        double exp = (27.55 - (20 * Math.log10(freqInMHz)) + Math.abs(levelInDb)) / 20.0;
        return Math.pow(10.0, exp);
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
