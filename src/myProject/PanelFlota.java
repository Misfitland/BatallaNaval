package myProject;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

/**
 * Clase PanelFlota
 * @autor Esteban Camilo Martinez Urbano-2224043
 * esteban.urbano@correounivalle.edu.co
 * @autor David Ordoñez
 * david.camilo.ordonez@correounivalle.edu.co
 * @autor Juan Felipe Palechor
 * @version 1.0
 * 04/07/2023
 */
public class PanelFlota extends JPanel{
    public static final String PATH ="/recursos/";
    private JButton portavion, destuctor, fragata, submarino, vertical, horizontal, si, is, id, di, exp_Botones;
    private JPanel panelFlota, infojuego, panelBotones, subpanelBotones, subpanelBotones2;
    private JLabel asignarTurno;
    private JTextPane explicacionJuego;
    private ImageIcon imageDestructor, imagePortavion, imageFragata, imageSubmarino;
    private TitledBorder tituloFlota,tituloInfo, titulo_Orientacion;
    private Border blackline;
    private String nombreBoton;
    private int orientacion; // 0 si es vertical, 1 si es horizontal
    private int sentidoOrientacion;
    private int cantidadPortavion;
    private int cantidadSubmarino;
    private int cantidadDestructor;
    private int cantidadFragata;

    /**
     * Constructor de clase PanelFlota
     */
    public PanelFlota(){
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(600,100));
        barcos();
    }

    /**
     * Creación de panel con los barcos y los botones de sentido y orientación
     */
    public void barcos(){
        GridBagConstraints gbc = new GridBagConstraints();

        nombreBoton = "";
        orientacion = 0;
        sentidoOrientacion = 0;

        // Cantidad inicial de barcos
        cantidadPortavion = 1;
        cantidadSubmarino = 2;
        cantidadDestructor = 3;
        cantidadFragata = 4;

        // Imágenes
        imageDestructor = new ImageIcon(getClass().getResource(PATH + "destructor.png"));
        imagePortavion = new  ImageIcon(getClass().getResource(PATH+"portavion.png"));
        imageFragata = new ImageIcon(getClass().getResource(PATH+"fragata.png"));
        imageSubmarino = new ImageIcon(getClass().getResource(PATH+"submarino.png"));

        // Botones de aviones
        portavion = new JButton();
        portavion.setText("PORTAVION");
        portavion.setIcon(imagePortavion);
        portavion.setBackground(Color.WHITE);
        portavion.setHorizontalTextPosition( SwingConstants.CENTER );
        portavion.setVerticalTextPosition( SwingConstants.BOTTOM );
        portavion.setFocusable(false);
        portavion.setBorder(null);

        destuctor = new JButton();
        destuctor.setText("DESTRUCTOR");
        destuctor.setIcon(imageDestructor);
        destuctor.setBackground(Color.WHITE);
        destuctor.setHorizontalTextPosition( SwingConstants.CENTER );
        destuctor.setVerticalTextPosition( SwingConstants.BOTTOM );
        destuctor.setFocusable(false);
        destuctor.setBorder(null);

        fragata = new JButton();
        fragata.setText("FRAGATA");
        fragata.setIcon(imageFragata);
        fragata.setBackground(Color.WHITE);
        fragata.setHorizontalTextPosition( SwingConstants.CENTER );
        fragata.setVerticalTextPosition( SwingConstants.BOTTOM );
        fragata.setFocusable(false);
        fragata.setBorder(null);

        submarino = new JButton();
        submarino.setText("SUBMARINO");
        submarino.setIcon(imageSubmarino);
        submarino.setBackground(Color.WHITE);
        submarino.setHorizontalTextPosition( SwingConstants.CENTER );
        submarino.setVerticalTextPosition( SwingConstants.BOTTOM );
        submarino.setFocusable(false);
        submarino.setBorder(null);

        // Botones de orientación
        vertical = new JButton("Vertical");
        vertical.setFont(new Font(Font.DIALOG,Font.BOLD,15));
        vertical.setBackground(new Color(0,0,128));
        vertical.setForeground(Color.BLACK);
        vertical.setFocusable(false);
        vertical.setBorder(null);

        horizontal = new JButton("Horizontal");
        horizontal.setFont(new Font(Font.DIALOG,Font.BOLD,15));
        horizontal.setBackground(new Color(0,0,128));
        horizontal.setForeground(Color.BLACK);
        horizontal.setFocusable(false);
        horizontal.setBorder(null);

        // Botones de sentido
        si = new JButton("Superior-Inferior");
        si.setFont(new Font(Font.DIALOG,Font.BOLD,15));
        si.setBackground(new Color(0,0,128));
        si.setForeground(Color.BLACK);
        si.setFocusable(false);
        si.setBorder(null);

        is = new JButton("Inferior-Superior");
        is.setFont(new Font(Font.DIALOG,Font.BOLD,15));
        is.setBackground(new Color(0, 0, 128));
        is.setForeground(Color.BLACK);
        is.setFocusable(false);
        is.setBorder(null);

        id = new JButton("Izquierda-Derecha");
        id.setFont(new Font(Font.DIALOG,Font.BOLD,15));
        id.setBackground(new Color(0,0,128));
        id.setForeground(Color.BLACK);
        id.setFocusable(false);
        id.setBorder(null);

        di = new JButton("Derecha-Izquierda");
        di.setFont(new Font(Font.DIALOG,Font.BOLD,15));
        di.setBackground(new Color(0, 0, 128));
        di.setForeground(Color.BLACK);
        di.setFocusable(false);
        di.setBorder(null);

        // Botón explicación de los botones de orientación
        exp_Botones = new JButton("Explicacion de los botones");
        exp_Botones.setFont(new Font(Font.DIALOG,Font.BOLD,15));
        exp_Botones.setBackground(new Color(0, 0, 200));
        exp_Botones.setFocusable(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(exp_Botones, gbc);

        // Flota
        //blackline = BorderFactory.createLineBorder(Color.black);
        panelFlota = new JPanel();
        panelFlota.setLayout(new GridLayout(2,2,10,10));
        panelFlota.setPreferredSize(new Dimension(350,300));
        panelFlota.setBackground(Color.WHITE);
        tituloFlota = BorderFactory.createTitledBorder(blackline, "Barcos disponibles");
        tituloFlota.setTitleJustification(TitledBorder.CENTER);
        panelFlota.setBorder(tituloFlota);
        panelFlota.add(portavion);
        panelFlota.add(destuctor);
        panelFlota.add(fragata);
        panelFlota.add(submarino);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(panelFlota, gbc);

        // Texto bajo flota
        asignarTurno = new JLabel();
        asignarTurno.setHorizontalAlignment(SwingConstants.CENTER);

        explicacionJuego = new JTextPane();
        explicacionJuego.setEditable(false);
        explicacionJuego.setBackground(Color.WHITE);
        StyledDocument documentStyle = explicacionJuego.getStyledDocument();
        SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_CENTER);
        documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);

        infojuego = new JPanel(new GridLayout(2,0,0,0));
        infojuego.setPreferredSize(new Dimension(350,100));
        infojuego.setBackground(Color.WHITE);
        tituloInfo = BorderFactory.createTitledBorder(blackline, "Información del juego");
        tituloInfo.setTitleJustification(TitledBorder.CENTER);
        infojuego.setBorder(tituloInfo);
        infojuego.add(asignarTurno);
        infojuego.add(explicacionJuego);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(infojuego, gbc);

        // Panel botones dentro de panel flota
        panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(0,2,5,10));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setPreferredSize(new Dimension(350,120));
        titulo_Orientacion = BorderFactory.createTitledBorder(blackline, "Direcciones de tu flota");
        titulo_Orientacion.setTitleJustification(TitledBorder.CENTER);
        panelBotones.setBorder(titulo_Orientacion);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(panelBotones,gbc);

        // panel dentro de panel botones para los botones de horizontal y vertical
        subpanelBotones = new JPanel(new GridLayout());
        subpanelBotones.setPreferredSize(new Dimension(175,120));
        subpanelBotones.setBackground(Color.WHITE);
        subpanelBotones.setLayout(new GridLayout(2,0,5,5));
        subpanelBotones.add(vertical);
        subpanelBotones.add(horizontal);
        panelBotones.add(subpanelBotones,BorderLayout.WEST);

        // panel dentro de panel botones para los botones de sup_inf, inf_sup, der_izq, izq_der
        subpanelBotones2 = new JPanel();
        subpanelBotones2.setPreferredSize(new Dimension(175,120));
        subpanelBotones2.setBackground(Color.WHITE);
        subpanelBotones2.setLayout(new GridLayout(4,0,5,5));
        subpanelBotones2.add(si);
        subpanelBotones2.add(is);
        subpanelBotones2.add(id);
        subpanelBotones2.add(di);
        panelBotones.add(subpanelBotones2,BorderLayout.EAST);

    }

    /**
     * Retorna el botón del barco especificado
     * @param barco
     * @return JButton
     */
    public JButton getBotonBarco(String barco){
        JButton boton = new JButton();
        if(barco.equals("portavion")){
            boton = portavion;
        }else{
            if(barco.equals("submarino")){
                boton = submarino;
            }else{
                if(barco.equals("destructor")){
                    boton = destuctor;
                }else{
                    if(barco.equals("fragata")){
                        boton = fragata;
                    }
                }
            }
        }
        return boton;
    }

    /**
     * Retorna el botón de orientación especificado
     * @param orientacion
     * @return JButton
     */
    public JButton getBotonOrientacion(String orientacion){
        JButton boton = new JButton();
        if(orientacion.equals("vertical")){
            boton = vertical;
        }else{
            if(orientacion.equals("horizontal")){
                boton = horizontal;
            }
        }
        return boton;
    }


    /**
     * Retorna el botón del sentido de la orientación especificado
     * @param sentido
     * @return JButton
     */
    public JButton getBotonSentidoOrientacion(String sentido){
        JButton boton = new JButton();
        if(sentido.equals("si")){
            boton = si;
        }else{
            if(sentido.equals("is")){
                boton = is;
            }else{
                if(sentido.equals("id")){
                    boton = id;
                }else{
                    if(sentido.equals("di")){
                        boton = di;
                    }
                }
            }
        }
        return boton;
    }

    /**
     * Guarda el nombre del botón presionado en un string
     * @param _nombreBoton
     */
    public void setNombreBoton(String _nombreBoton){
        nombreBoton = _nombreBoton;
    }

    /**
     * Retorna el nombre del barco que se presionó
     * @return String
     */
    public String getNombreBoton(){
        return nombreBoton;
    }

    /**
     * Asigna el estado de orientación
     * @param _orientacion
     */
    public void setOrientacion(int _orientacion){
        orientacion = _orientacion;
    }

    /**
     * Asigna el estado de sentidoOrientacion
     * @param _sentidoOrientacion
     */
    public void setSentidoOrientacion(int _sentidoOrientacion){
        sentidoOrientacion = _sentidoOrientacion;
    }

    /**
     * Retorna el estado de orientación
     * @return int
     */
    public int getOrientacion(){
        return orientacion;
    }

    /**
     * Retorna el estado de sentidoOrientacion
     * @return int
     */
    public int getSentidoOrientacion(){
        return sentidoOrientacion;
    }

    /**
     * Reduce la cantidad disponible del barco ingresado
     * @param barco
     */
    public void setCantidadBarco(String barco){
        if(barco.equals("portavion")){
            cantidadPortavion--;
        }else{
            if(barco.equals("submarino")){
                cantidadSubmarino--;
            }else{
                if(barco.equals("destructor")){
                    cantidadDestructor--;
                }else{
                    if(barco.equals("fragata")){
                        cantidadFragata--;
                    }
                }
            }
        }
    }

    /**
     * Retorna la cantidad disponible del barco ingresado
     * @param barco
     * @return int
     */
    public int getCantidadBarco(String barco){
        int cantidad = 0;
        if(barco.equals("portavion")){
            cantidad = cantidadPortavion;
        }else{
            if(barco.equals("submarino")){
                cantidad = cantidadSubmarino;
            }else{
                if(barco.equals("destructor")){
                    cantidad = cantidadDestructor;
                }else{
                    if(barco.equals("fragata")){
                        cantidad = cantidadFragata;
                    }
                }
            }
        }
        return cantidad;
    }

    /**
     * Retorna la cantidad total de naves disponibles
     * @return int
     */
    public int cantidadTotalNaves(){
        return cantidadPortavion + cantidadSubmarino + cantidadDestructor + cantidadFragata;
    }

    /**
     * Retorna el JTextPane para editar la información del juego
     * @return JTextPane
     */
    public JTextPane getExplicacionJuego(){
        return explicacionJuego;
    }

    /**
     * Retorna el JLabel que edita el turno
     * @return JLabel
     */
    public JLabel getAsignarTurno(){
        return asignarTurno;
    }

    /**
     * Retorna el boton que explica la dinámica de los botones
     * @return JButton
     */
    public JButton getExp_Botones(){
        return exp_Botones;
    }
}