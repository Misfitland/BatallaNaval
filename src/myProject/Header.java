package myProject;

import javax.swing.*;
import java.awt.*;

/**
 * Clase Header
 * @autor Esteban Camilo Martinez Urbano-esteban.urbano@correounivalle.edu.co
 * esteban.urbano@correounivalle.edu.co
 * @autor David Ordoñez-david.camilo.ordonez@correounivalle.edu.co
 * david.camilo.ordonez@correounivalle.edu.co
 * @autor Juan Felipe Palechor
 * @autor Jhon Frank Vasquez - jhon.frank.vasquez@correounivalle.edu.co
 * @version 1.0
 * 04/07/2023
 */
public class Header extends JLabel {
    /**
     * Constructor de la clase Header
     * @param title Cadena que contiene el texto del encabezado
     * @param colorBackground Objeto de color que se asignará para el fondo del encabezado
     */
    public Header(String title, Color colorBackground){
        this.setText(title);
        this.setBackground(colorBackground);
        this.setForeground(new Color(0, 0, 0, 230));
        this.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
        this.setOpaque(true);
    }
}
