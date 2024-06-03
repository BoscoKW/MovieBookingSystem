/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moviebookingsystem;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class CinemaPanel extends JPanel {

    private Map<Integer, String> cinemaMap;

    public static Map<Integer, String> getCinemas() {
        Map<Integer, String> cinemas = new HashMap<>();
        cinemas.put(1, "Albany");
        cinemas.put(2, "Manukau");
        cinemas.put(3, "Mission Bay");
        cinemas.put(4, "Newmarket");
        cinemas.put(5, "Queen Street");
        cinemas.put(6, "St Lukes");
        cinemas.put(7, "Sylvia Park");
        cinemas.put(8, "Westgate");

        return cinemas;
    }
}
