/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moviebookingsystem;

/**
 *
 * @author mardiliza
 */
public class Cinema {
    String location;
    
    public Cinema(String location) {
        this.location = location;
    }
    
    @Override
    public String toString() {
        return "Location: " + location + "";
    }        
}
