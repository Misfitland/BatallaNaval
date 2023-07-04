package myProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Clase principal
 * @autor
 * @autor
 * @version
 */
public class Vista extends JFrame {
    public static final String PATH ="/recursos/";
    public static final String AYUDA ="Batalla Naval es un juego tradicional de estrategia y suerte, que involucra a dos participantes (para este caso un jugador vs el computador).\n"+
            "\nEl objetivo del juego es ser el primero en hundir los barcos del oponente. \n"+"\nCada jugador tiene 2 tableros compuesto por 10 filas y 10 columnas: \n"+
            "\n-> Tablero de posición: Representa tu territorio, en él distribuirás tu flota antes de comenzar la partida y sólo será de observación. Verás la posición de tus barcos \ny los disparos de tu oponente en tu territorio, pero no podrás realizar ningún cambio ni disparo en él. \n"+
            "\n-> Tablero principal: Representa el territorio del enemigo, donde tiene desplegada su flota. Será aquí donde se desarrollen los movimientos (disparos) del jugador tratando \nde hundir los barcos enemigos. Este tablero aparecerá en la pantalla del jugador una vez comience la partida y en él quedarán registrados todos sus movimientos, reflejando \ntanto los disparos al agua como los barcos tocados y hundidos hasta el momento. \n"
            +"\n\nCada jugador tiene una flota de 9 barcos de diferente tamaño, por lo que cada uno ocupará un número determinado de casillas en el tablero: \n"+
            "\n• 1 portaaviones: ocupa 4 casillas "+"\n• 2 submarinos: ocupan 3 casillas cada uno."+"\n• 3 destructores: ocupan 2 casillas cada uno "+"\n• 4 fragatas: ocupan 1 casilla cada uno "
            +"\n\nCada barco puede ser ubicado de manera horizontal o vertical en el tablero de posición. "+"\n\nTerminología y movimientos: \n\n"+"• Agua: cuando se dispara sobre una casilla donde no está colocado ningún barco enemigo. En el tablero principal del jugador aparecerá una X. Pasa el turno a tu oponente."+
            "\n• Tocado: cuando se dispara en una casilla en la que está ubicado un barco enemigo que ocupa 2 o más casillas y se destruye sólo una parte del barco. En el tablero del jugador \naparecerá esa parte del barco con una marca indicativa de que ha sido tocado. El jugador vuelve a disparar. "
            +"\n• Hundido: si se dispara en una casilla en la que está ubicado una fragata (1 casilla) u otro barco con el resto de casillas tocadas, se habrá hundido, es decir, se ha eliminado ese \nbarco del juego. Aparecerá en el tablero principal del jugador, el barco completo con la marca indicativa de que ha sido hundido. El jugador puede volver a disparar, siempre y cuando no hayas\n hundido toda la flota de su enemigo, en cuyo caso habrá ganado. ";

    private Header headerProject;
    private JButton ayuda,comenzarPartida, movimientosEnemigo,reiniciar;
    private Escucha escucha;
    private ImageIcon help, expSentido;
    private JPanel panelNorte, panelSur, panelEste, panelCentro;
    private PanelTablero panelTablero;
    private PintarFlota pintarFlota;
    private PanelFlota panelFlota;
    private Controlador ventanaOponente;
    private int estadoJuego;
    private Modelo modelo;
    private int contadorHundidos; // Contador de barcos hundidos
    private Timer timer; // establece el tiempo que tarde el oponente en escoger casilla

    /**
     * Constructor de la clase GUI
     */
    public Vista(){
        initGUI();

        // Configuración del JFrame
        this.setTitle("BATALLA NAVAL");
        this.setUndecorated(false);
        this.setSize(1500,750);
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Este método se utiliza para configurar la configuración predeterminada de JComponent,
     * crear objetos de escucha y control utilizados para la clase GUI
     */
    private void initGUI() {
        // Creación de la ventana del oponente
        ventanaOponente = new Controlador(this);


        // Set up JFrame Container's Layout
        panelNorte = new JPanel();
        panelSur = new JPanel();
        panelEste = new JPanel();
        panelCentro = new JPanel();

        // Creación de paneles para el JFrame
        panelNorte.setBackground(Color.WHITE);
        panelSur.setBackground(Color.white);
        panelEste.setBackground(Color.white);
        panelCentro.setBackground(Color.white);

        panelSur.setLayout(new FlowLayout(FlowLayout.CENTER,250,5));
        panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER,200,5));
        panelEste.setLayout(new FlowLayout(FlowLayout.CENTER,100,60));
        panelCentro.setLayout(new GridLayout(1,1,0,100));

        panelSur.setPreferredSize(new Dimension(100,60));
        panelNorte.setPreferredSize(new Dimension(100,60));
        panelEste.setPreferredSize(new Dimension(1000,60));
        panelCentro.setPreferredSize(new Dimension(600,100));

        // Se agregan los paneles al JFrame
        this.add(panelNorte,BorderLayout.NORTH);
        this.add(panelSur,BorderLayout.SOUTH);
        this.add(panelEste,BorderLayout.EAST);
        this.add(panelCentro,BorderLayout.CENTER);

        // Estado del juego
        estadoJuego = 1;

        // Creación el evento escucha
        escucha = new Escucha();

        // Creación del panel Flota
        panelFlota = new PanelFlota();

        expSentido = new ImageIcon(getClass().getResource(PATH + "Instrucciones.jpg"));


        // JComponents de la parte superior
        headerProject = new Header("BATALLA NAVAL", Color.white);
        panelNorte.add(headerProject,FlowLayout.LEFT);

        // Creación botón ayuda
        ayuda = new JButton("HELP");
        ayuda.addActionListener(escucha);
        ayuda.setBackground(Color.white);
        ayuda.setFocusable(false);
        ayuda.setBorder(null);
        panelNorte.add(ayuda,FlowLayout.CENTER);

        // Se agrega escucha al botón de la información de los botones de la clase PanelFlota
        panelFlota.getExplicacionBotones().addActionListener(escucha);

        // JComponents de la parte central
        panelTablero = new PanelTablero();
        pintarFlota = new PintarFlota(panelTablero, panelFlota);
        panelEste.add(panelTablero);

        // Flota
        panelCentro.add(panelFlota);

        //JComponents de la parte Inferior
        // Creación de botón comenzar partida
        comenzarPartida = new JButton("PLAY");
        comenzarPartida.addActionListener(escucha);
        comenzarPartida.setBackground(Color.white);
        comenzarPartida.setFocusable(false);
        comenzarPartida.setBorder(null);
        panelSur.add(comenzarPartida,FlowLayout.LEFT);

        // Creación de botón de movimientos del oponente
        movimientosEnemigo = new JButton("ENEMIGO");
        movimientosEnemigo.addActionListener(escucha);
        movimientosEnemigo.setBackground(Color.white);
        movimientosEnemigo.setFocusable(false);
        movimientosEnemigo.setBorder(null);
        panelSur.add(movimientosEnemigo,FlowLayout.CENTER);

        // Creación del botón de reinicio
        reiniciar = new JButton("REINICIO");
        reiniciar.addActionListener(escucha);
        reiniciar.setBackground(Color.white);
        reiniciar.setFocusable(false);
        reiniciar.setBorder(null);
        panelSur.add(reiniciar,FlowLayout.CENTER);

        // Se agrega él escucha a todos los botones de todas las clases
        setEscuchaBotones("remover");
        setVerticalHorizontal("remover");
        setOrientacionSentidoVertical("remover");
        setOrientacionSentidoHorizontal("remover");

        // Se distribuye la flota del oponente
        while(ventanaOponente.getPintarFlotaOponente().cantidadTotalNaves() > 0){
            ventanaOponente.distribucionFlotaOponente();
        }

        // Objeto para invocar funciones de combate
        modelo = new Modelo(panelTablero, ventanaOponente.getTableroOponente());

        contadorHundidos = 0;
        // Timer para el turno del oponente
        timer = new Timer(2000, escucha);
    }

    /**
     * Reinicia el juego
     */
    public void reset(){
        this.dispose();
        Vista gui = new Vista();
    }

    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            Vista miProjectGUI = new Vista();
        });
    }

    /**
     * Agrega o remueve él escucha al botón de cada barco
     */
    public void setEscuchaBotones(String evento){
        if(evento == "agregar"){
            panelFlota.getBotonBarco("portavion").addActionListener(escucha);
            panelFlota.getBotonBarco("destructor").addActionListener(escucha);
            panelFlota.getBotonBarco("fragata").addActionListener(escucha);
            panelFlota.getBotonBarco("submarino").addActionListener(escucha);
            panelFlota.getBotonBarco("portavion").setEnabled(true);
            panelFlota.getBotonBarco("destructor").setEnabled(true);
            panelFlota.getBotonBarco("fragata").setEnabled(true);
            panelFlota.getBotonBarco("submarino").setEnabled(true);
        }else{
            panelFlota.getBotonBarco("portavion").removeActionListener(escucha);
            panelFlota.getBotonBarco("destructor").removeActionListener(escucha);
            panelFlota.getBotonBarco("fragata").removeActionListener(escucha);
            panelFlota.getBotonBarco("submarino").removeActionListener(escucha);
            panelFlota.getBotonBarco("portavion").setEnabled(false);
            panelFlota.getBotonBarco("destructor").setEnabled(false);
            panelFlota.getBotonBarco("fragata").setEnabled(false);
            panelFlota.getBotonBarco("submarino").setEnabled(false);
        }
    }

    /**
     *Agrega o remueve él escucha a los botones Vertical y Horizontal
     * @param evento
     */
    public void setVerticalHorizontal(String evento){
        if(evento == "agregar"){
            panelFlota.getBotonOrientacion("vertical").addActionListener(escucha);
            panelFlota.getBotonOrientacion("horizontal").addActionListener(escucha);
            panelFlota.getBotonOrientacion("vertical").setEnabled(true);
            panelFlota.getBotonOrientacion("horizontal").setEnabled(true);
        }else{
            panelFlota.getBotonOrientacion("vertical").removeActionListener(escucha);
            panelFlota.getBotonOrientacion("horizontal").removeActionListener(escucha);
            panelFlota.getBotonOrientacion("vertical").setEnabled(false);
            panelFlota.getBotonOrientacion("horizontal").setEnabled(false);
        }
    }

    /**
     * Agrega o remueve él escucha de los botones verticales
     * @param evento
     */
    public void setOrientacionSentidoVertical(String evento){
        if(evento == "agregar"){
            panelFlota.getBotonSentidoOrientacion("sup_inf").addActionListener(escucha);
            panelFlota.getBotonSentidoOrientacion("inf_sup").addActionListener(escucha);
            panelFlota.getBotonSentidoOrientacion("sup_inf").setEnabled(true);
            panelFlota.getBotonSentidoOrientacion("inf_sup").setEnabled(true);
        }else{
            panelFlota.getBotonSentidoOrientacion("sup_inf").removeActionListener(escucha);
            panelFlota.getBotonSentidoOrientacion("inf_sup").removeActionListener(escucha);
            panelFlota.getBotonSentidoOrientacion("sup_inf").setEnabled(false);
            panelFlota.getBotonSentidoOrientacion("inf_sup").setEnabled(false);
        }
    }

    /**
     * Agrega o remueve él escucha de los botones horizontales
     * @param evento
     */
    public void setOrientacionSentidoHorizontal(String evento){
        if(evento == "agregar"){
            panelFlota.getBotonSentidoOrientacion("der_izq").addActionListener(escucha);
            panelFlota.getBotonSentidoOrientacion("izq_der").addActionListener(escucha);
            panelFlota.getBotonSentidoOrientacion("der_izq").setEnabled(true);
            panelFlota.getBotonSentidoOrientacion("izq_der").setEnabled(true);
        }else{
            panelFlota.getBotonSentidoOrientacion("der_izq").removeActionListener(escucha);
            panelFlota.getBotonSentidoOrientacion("izq_der").removeActionListener(escucha);
            panelFlota.getBotonSentidoOrientacion("der_izq").setEnabled(false);
            panelFlota.getBotonSentidoOrientacion("izq_der").setEnabled(false);
        }
    }

    /**
     * Agrega o remueve él escucha a cada uno de los JLabel de la matriz posición de PintarTablero
     * @param evento
     */
    public void setEscuchaCasillas(String evento){
        if(evento == "agregar"){
            for (int row = 0; row < panelTablero.getTablero("posicion").getMatriz().length; row++) {
                for (int col = 0; col < panelTablero.getTablero("posicion").getMatriz()[row].length; col++) {
                    panelTablero.getTablero("posicion").getMatriz()[row][col].addMouseListener(escucha);
                }
            }
        }else{
            for (int row = 0; row < panelTablero.getTablero("posicion").getMatriz().length; row++) {
                for (int col = 0; col < panelTablero.getTablero("posicion").getMatriz()[row].length; col++) {
                    panelTablero.getTablero("posicion").getMatriz()[row][col].removeMouseListener(escucha);
                }
            }
        }
    }

    /**
     * Agrega o remueve él escucha a cada uno de los JLabel de la matriz principal de PintarTablero
     * @param evento
     */
    public void setEscuchaCasillasPrincipal(String evento){
        if(evento == "agregar"){
            for (int row = 0; row < panelTablero.getTablero("principal").getMatriz().length; row++) {
                for (int col = 0; col < panelTablero.getTablero("principal").getMatriz()[row].length; col++) {
                    panelTablero.getTablero("principal").getMatriz()[row][col].addMouseListener(escucha);
                }
            }
        }else{
            for (int row = 0; row < panelTablero.getTablero("principal").getMatriz().length; row++) {
                for (int col = 0; col < panelTablero.getTablero("principal").getMatriz()[row].length; col++) {
                    panelTablero.getTablero("principal").getMatriz()[row][col].removeMouseListener(escucha);
                }
            }
        }
    }

    /**
     * Identifica si hay un barco en la casilla del tablero principal para hundirlo
     * @param row
     * @param col
     * @param barco
     */
    public void funcionesCombate(int row, int col, String barco){
        // Establece una imagen a la casilla seleccionada del tablero principal del usuario y del tablero posicion del oponente si un barco fue tocado
        ventanaOponente.getTableroOponente().getTableroOponente("posicion").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/tocado.png")));
        panelTablero.getTablero("principal").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/tocado.png")));
        panelTablero.getTablero("principal").getCasillasOcupadas().replace(panelTablero.getTablero("principal").getMatriz()[row][col], Integer.valueOf(2));

        // Reduce las casillas ocupadas del barco tocado para poder ser hundido
        ventanaOponente.getTableroOponente().getTableroOponente("posicion").reducirCasillasUsadas(barco);

        // Si no hay mas casillas ocupadas, el barco se hunde y se establecen las imagenes respectivas
        if(ventanaOponente.getTableroOponente().getTableroOponente("posicion").getCasillaBarco().get(ventanaOponente.getTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]) == Integer.valueOf(0)){
            panelFlota.getInformacionJuego().setText("Barco hundido, selecciona otra casilla");
            estadoJuego = 5;
            contadorHundidos++;
            for (int fil = 1; fil < 11; fil++) {
                for (int colu = 1; colu < 11; colu++) {
                    if(ventanaOponente.getTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(ventanaOponente.getTableroOponente().getTableroOponente("posicion").getMatriz()[fil][colu]) != null){
                        if(ventanaOponente.getTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(ventanaOponente.getTableroOponente().getTableroOponente("posicion").getMatriz()[fil][colu]).equals(barco)){
                            ventanaOponente.getTableroOponente().getTableroOponente("posicion").getMatriz()[fil][colu].setIcon(new ImageIcon(getClass().getResource("/recursos/hundido.png")));
                            panelTablero.getTablero("principal").getMatriz()[fil][colu].setIcon(new ImageIcon(getClass().getResource("/recursos/hundido.png")));
                        }
                    }else{
                        continue;
                    }
                }
            }
        }else{
            panelFlota.getInformacionJuego().setText("Tocaste una nave, selecciona otra casilla");
            estadoJuego = 5;
        }

        if(contadorHundidos == 10){
            panelFlota.getInformacionJuego().setText("Todos los barcos enemigos han sido hundidos, ganaste el juego");
            setEscuchaCasillasPrincipal("remover");
        }
    }

    /**
     * Retorna el objeto de clase PanelTablero
     * @return PanelTablero
     */
    public PanelTablero getPanelTablero(){
        return panelTablero;
    }

    /**
     * clase interna que extiende una clase de adaptador o implementa oyentes utilizados por la clase GUI
     */
    private class Escucha implements ActionListener, MouseListener {

        @Override


        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == reiniciar) {
                reset();
            } else {
                if (e.getSource() == ayuda) {
                    JOptionPane.showMessageDialog(null, AYUDA, "¿Cómo se juega batalla naval?", JOptionPane.PLAIN_MESSAGE, help);
                } else {
                    if (e.getSource() == comenzarPartida) {
                        comenzarPartida.removeActionListener(this);
                        setEscuchaBotones("agregar");
                        setVerticalHorizontal("remover");
                        setOrientacionSentidoVertical("remover");
                        setOrientacionSentidoHorizontal("remover");
                        panelFlota.getAsignarTurno().setText("¡Tu turno!");
                        panelFlota.getInformacionJuego().setText("Selecciona la nave que quieres desplegar");
                    } else {
                        if (e.getSource() == movimientosEnemigo) {
                            ventanaOponente.setVisible(true);
                        } else {
                            if (e.getSource() == panelFlota.getExplicacionBotones()) {
                                JOptionPane.showMessageDialog(null, "", "Cómo jugar", JOptionPane.PLAIN_MESSAGE, expSentido);
                            } else {
                                if (estadoJuego == 6) {
                                    if (e.getSource() == timer) {
                                        ventanaOponente.oponenteVsUsuario();
                                        if (ventanaOponente.getEstado() == 0) {
                                            timer.stop();
                                            estadoJuego = 5;
                                            panelFlota.getAsignarTurno().setText("Tu turno");
                                            panelFlota.getInformacionJuego().setText("Selecciona otra casilla del tablero principal");
                                        } else {
                                            if (ventanaOponente.getEstado() == 2) {
                                                timer.stop();
                                                panelFlota.getInformacionJuego().setText("Tus barcos han sido hundidos, perdiste el juego");
                                            }
                                        }
                                    }
                                } else {
                                    switch (estadoJuego) {
                                        case 1:
                                            if (e.getSource() == panelFlota.getBotonBarco("portavion")) {
                                                if (panelFlota.getCantidadBarco("portavion") > 0) {
                                                    panelFlota.setCantidadBarco("portavion");
                                                    setEscuchaBotones("remover");
                                                    panelFlota.getInformacionJuego().setText("Escoge si quieres ubicarlo vertical u horizontal");
                                                    setVerticalHorizontal("agregar");
                                                    panelFlota.setNombreBoton("portavion");
                                                    estadoJuego = 2;
                                                } else {
                                                    panelFlota.getInformacionJuego().setText("No hay más portaviones disponibles");
                                                }
                                            } else {
                                                if (e.getSource() == panelFlota.getBotonBarco("destructor")) {
                                                    if (panelFlota.getCantidadBarco("destructor") > 0) {
                                                        panelFlota.setCantidadBarco("destructor");
                                                        setEscuchaBotones("remover");
                                                        panelFlota.getInformacionJuego().setText("Escoge si quieres ubicarlo vertical u horizontal");
                                                        setVerticalHorizontal("agregar");
                                                        panelFlota.setNombreBoton("destructor");
                                                        estadoJuego = 2;
                                                    } else {
                                                        panelFlota.getInformacionJuego().setText("No hay más destructores disponibles");
                                                    }
                                                } else {
                                                    if (e.getSource() == panelFlota.getBotonBarco("fragata")) {
                                                        if (panelFlota.getCantidadBarco("fragata") > 0) {
                                                            panelFlota.setCantidadBarco("fragata");
                                                            setEscuchaBotones("remover");
                                                            panelFlota.getInformacionJuego().setText("Escoge si quieres ubicarlo vertical u horizontal");
                                                            setVerticalHorizontal("agregar");
                                                            panelFlota.setNombreBoton("fragata");
                                                            estadoJuego = 2;
                                                        } else {
                                                            panelFlota.getInformacionJuego().setText("No hay más fragatas disponibles");
                                                        }
                                                    } else {
                                                        if (e.getSource() == panelFlota.getBotonBarco("submarino")) {
                                                            if (panelFlota.getCantidadBarco("submarino") > 0) {
                                                                panelFlota.setCantidadBarco("submarino");
                                                                setEscuchaBotones("remover");
                                                                panelFlota.getInformacionJuego().setText("Escoge si quieres ubicarlo vertical u horizontal");
                                                                setVerticalHorizontal("agregar");
                                                                panelFlota.setNombreBoton("submarino");
                                                                estadoJuego = 2;
                                                            } else {
                                                                panelFlota.getInformacionJuego().setText("No hay más submarinos disponibles");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            break;
                                        case 2:
                                            if (e.getSource() == panelFlota.getBotonOrientacion("vertical")) {
                                                setVerticalHorizontal("remover");
                                                panelFlota.getInformacionJuego().setText("Escoge cuál sentido quieres usar");
                                                setOrientacionSentidoVertical("agregar");
                                                panelFlota.setOrientacion(0);
                                                estadoJuego = 3;
                                            } else {
                                                if (e.getSource() == panelFlota.getBotonOrientacion("horizontal")) {
                                                    setVerticalHorizontal("remover");
                                                    panelFlota.getInformacionJuego().setText("Escoge cuál sentido quieres usar");
                                                    setOrientacionSentidoHorizontal("agregar");
                                                    panelFlota.setOrientacion(1);
                                                    estadoJuego = 3;
                                                }
                                            }
                                            break;
                                        case 3:
                                            if (e.getSource() == panelFlota.getBotonSentidoOrientacion("sup_inf")) {
                                                setOrientacionSentidoVertical("remover");
                                                panelFlota.getInformacionJuego().setText("Selecciona la casilla en la que quieres ubicar la nave");
                                                setEscuchaCasillas("agregar");
                                                panelFlota.setSentidoOrientacion(1);
                                                estadoJuego = 4;
                                            } else {
                                                if (e.getSource() == panelFlota.getBotonSentidoOrientacion("inf_sup")) {
                                                    setOrientacionSentidoVertical("remover");
                                                    panelFlota.getInformacionJuego().setText("Selecciona la casilla en la que quieres ubicar la nave");
                                                    setEscuchaCasillas("agregar");
                                                    panelFlota.setSentidoOrientacion(2);
                                                    estadoJuego = 4;
                                                } else {
                                                    if (e.getSource() == panelFlota.getBotonSentidoOrientacion("izq_der")) {
                                                        setOrientacionSentidoHorizontal("remover");
                                                        panelFlota.getInformacionJuego().setText("Selecciona la casilla en la que quieres ubicar la nave");
                                                        setEscuchaCasillas("agregar");
                                                        panelFlota.setSentidoOrientacion(3);
                                                        estadoJuego = 4;
                                                    } else {
                                                        if (e.getSource() == panelFlota.getBotonSentidoOrientacion("der_izq")) {
                                                            setOrientacionSentidoHorizontal("remover");
                                                            panelFlota.getInformacionJuego().setText("Selecciona la casilla en la que quieres ubicar la nave");
                                                            setEscuchaCasillas("agregar");
                                                            panelFlota.setSentidoOrientacion(4);
                                                            estadoJuego = 4;
                                                        }
                                                    }
                                                }
                                            }
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        @Override
        public void mouseClicked(MouseEvent e) {
            int auxiliar = 0; // Variable para indicar cuando se debe terminar el primer ciclo
            switch(estadoJuego){
                case 4:
                    for (int row = 1; row < 11; row++) {
                        for (int col = 1; col < 11; col++) {
                            if(e.getSource() == panelTablero.getTablero("posicion").getMatriz()[row][col]) {
                                // Condicional para saber si el usuario pudo colocar el barco
                                if(pintarFlota.funcionesFlota(panelFlota.getNombreBoton(), panelFlota.getOrientacion(), panelFlota.getSentidoOrientacion(), col, row)){
                                    if(panelFlota.cantidadTotalNaves() > 0){
                                        setEscuchaCasillas("remover");
                                        panelFlota.getInformacionJuego().setText("Escoge otro barco");
                                        setEscuchaBotones("agregar");
                                        estadoJuego = 1;
                                    }else{
                                        setEscuchaCasillas("remover");
                                        panelFlota.getInformacionJuego().setText("El combate comienza, selecciona una casilla del tablero principal");
                                        modelo.usuarioVsOponente();
                                        modelo.oponenteVsUsuario();
                                        setEscuchaCasillasPrincipal("agregar");
                                        estadoJuego = 5;
                                    }
                                }
                                auxiliar = 1;
                                break;
                            }
                        }
                        if(auxiliar == 1){
                            break;
                        }
                    }
                    break;
                case 5:
                    for (int row = 1; row < 11; row++) {
                        for (int col = 1; col < 11; col++) {
                            if(e.getSource() == panelTablero.getTablero("principal").getMatriz()[row][col]) {
                                // Verifica si la casilla seleccionada hay un barco oponente
                                if(panelTablero.getTablero("principal").getCasillasOcupadas().get(panelTablero.getTablero("principal").getMatriz()[row][col]) == Integer.valueOf(1)){
                                    // Verifica si todas las casillas del barco fueron seleccionadas
                                    if(ventanaOponente.getTableroOponente().getTableroOponente("posicion").getCasillaBarco().get(ventanaOponente.getTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]) != Integer.valueOf(0)){
                                        for(int num=1; num < 11; num++){
                                            if(ventanaOponente.getTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(ventanaOponente.getTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]).equals("portavion" + String.valueOf(num))){
                                                funcionesCombate(row, col, "portavion" + String.valueOf(num));
                                                break;
                                            }else{
                                                if(ventanaOponente.getTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(ventanaOponente.getTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]).equals("submarino" + String.valueOf(num))){
                                                    funcionesCombate(row, col, "submarino" + String.valueOf(num));
                                                    break;
                                                }else{
                                                    if(ventanaOponente.getTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(ventanaOponente.getTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]).equals("destructor" + String.valueOf(num))){
                                                        funcionesCombate(row, col, "destructor" + String.valueOf(num));
                                                        break;
                                                    }else{
                                                        if(ventanaOponente.getTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(ventanaOponente.getTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]).equals("fragata" + String.valueOf(num))){
                                                            funcionesCombate(row, col, "fragata" + String.valueOf(num));
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }else{
                                    if(panelTablero.getTablero("principal").getCasillasOcupadas().get(panelTablero.getTablero("principal").getMatriz()[row][col]) == Integer.valueOf(2)){
                                        panelFlota.getInformacionJuego().setText("Casilla usada, presiona otra");
                                        estadoJuego = 5;
                                    }else{
                                        panelFlota.getInformacionJuego().setText("Le diste al agua, espera el turno del oponente");
                                        panelTablero.getTablero("principal").getCasillasOcupadas().put(panelTablero.getTablero("principal").getMatriz()[row][col], Integer.valueOf(2));
                                        ventanaOponente.getTableroOponente().getTableroOponente("posicion").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/agua.png")));
                                        panelTablero.getTablero("principal").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/agua.png")));
                                        estadoJuego = 6;
                                        panelFlota.getAsignarTurno().setText("¡Turno del oponente!");
                                        timer.start();
                                    }
                                }
                                auxiliar = 1;
                                break;
                            }
                        }
                        if(auxiliar == 1){
                            break;
                        }
                    }
                    break;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}