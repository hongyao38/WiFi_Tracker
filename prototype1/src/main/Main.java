package main;

import javax.swing.JFrame;

public class Main {
    
    public static void main(String[] args) {

        // Create JFrame parameters
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Wi-Fi Location Tracker");

        // Create an AppPanel
        AppPanel appPanel = new AppPanel();
        window.add(appPanel);
        window.pack();
        window.setVisible(true);

        // Set window position on screen
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Start application thread
        appPanel.startThread();
    }
}
