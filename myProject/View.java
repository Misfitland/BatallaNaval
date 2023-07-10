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
public class View extends JFrame {
    public static final String PATH ="/recursos/";
    public static final String HELP ="Batalla Naval es un juego tradicional de estrategia y suerte, que involucra a dos participantes (para este caso un jugador vs el computador).\n"+
            "\nEl objetivo del juego es ser el primero en hundir los barcos del oponente. \n"+"\nCada jugador tiene 2 tableros compuesto por 10 filas y 10 columnas: \n"+
            "\n-> Tablero de posición: Representa tu territorio, en él distribuirás tu flota antes de comenzar la partida y sólo será de observación. Verás la posición de tus barcos \ny los disparos de tu oponente en tu territorio, pero no podrás realizar ningún cambio ni disparo en él. \n"+
            "\n-> Tablero principal: Representa el territorio del enemigo, donde tiene desplegada su flota. Será aquí donde se desarrollen los movimientos (disparos) del jugador tratando \nde hundir los barcos enemigos. Este tablero aparecerá en la pantalla del jugador una vez comience la partida y en él quedarán registrados todos sus movimientos, reflejando \ntanto los disparos al agua como los barcos tocados y hundidos hasta el momento. \n"
            +"\n\nCada jugador tiene una flota de 9 barcos de diferente tamaño, por lo que cada uno ocupará un número determinado de casillas en el tablero: \n"+
            "\n• 1 portaaviones: ocupa 4 casillas "+"\n• 2 submarinos: ocupan 3 casillas cada uno."+"\n• 3 destructores: ocupan 2 casillas cada uno "+"\n• 4 fragatas: ocupan 1 casilla cada uno "
            +"\n\nCada barco puede ser ubicado de manera horizontal o vertical en el tablero de posición. "+"\n\nTerminología y movimientos: \n\n"+"• Agua: cuando se dispara sobre una casilla donde no está colocado ningún barco enemigo. En el tablero principal del jugador aparecerá una X. Pasa el turno a tu oponente."+
            "\n• Tocado: cuando se dispara en una casilla en la que está ubicado un barco enemigo que ocupa 2 o más casillas y se destruye sólo una parte del barco. En el tablero del jugador \naparecerá esa parte del barco con una marca indicativa de que ha sido tocado. El jugador vuelve a disparar. "
            +"\n• Hundido: si se dispara en una casilla en la que está ubicado una fragata (1 casilla) u otro barco con el resto de casillas tocadas, se habrá hundido, es decir, se ha eliminado ese \nbarco del juego. Aparecerá en el tablero principal del jugador, el barco completo con la marca indicativa de que ha sido hundido. El jugador puede volver a disparar, siempre y cuando no hayas\n hundido toda la flota de su enemigo, en cuyo caso habrá ganado. ";

    private Header headerProject;
    private JButton help,startGame, enemyMovements,restart;
    private Escucha escucha;
    private ImageIcon Instructions;
    private JPanel northPanel, southPanel, eastPanel, centerPanel;
    private BoardPanel boardPanel;
    private PaintFleet paintFleet;
    private fleetPanel fleetPanel;
    private Controller opponentView;
    private int gameState;
    private Model model;
    private int sunk; // Contador de barcos hundidos
    private Timer timer; // establece el tiempo que tarde el oponente en escoger casilla

    /**
     * Constructor de la clase GUI
     */
    public View(){
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
        opponentView = new Controller(this);


        // Set up JFrame Container's Layout
        northPanel = new JPanel();
        southPanel = new JPanel();
        eastPanel = new JPanel();
        centerPanel = new JPanel();

        // Creación de paneles para el JFrame
        northPanel.setBackground(Color.WHITE);
        southPanel.setBackground(Color.white);
        eastPanel.setBackground(Color.white);
        centerPanel.setBackground(Color.white);

        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER,250,5));
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER,200,5));
        eastPanel.setLayout(new FlowLayout(FlowLayout.CENTER,100,60));
        centerPanel.setLayout(new GridLayout(1,1,0,100));

        southPanel.setPreferredSize(new Dimension(100,60));
        northPanel.setPreferredSize(new Dimension(100,60));
        eastPanel.setPreferredSize(new Dimension(1000,60));
        centerPanel.setPreferredSize(new Dimension(600,100));

        // Se agregan los paneles al JFrame
        this.add(northPanel,BorderLayout.NORTH);
        this.add(southPanel,BorderLayout.SOUTH);
        this.add(eastPanel,BorderLayout.EAST);
        this.add(centerPanel,BorderLayout.CENTER);

        // Estado del juego
        gameState = 1;

        // Creación el evento escucha
        escucha = new Escucha();

        // Creación del panel Flota
        fleetPanel = new fleetPanel();

        Instructions = new ImageIcon(getClass().getResource(PATH + "Instrucciones.jpg"));


        // JComponents de la parte superior
        headerProject = new Header("BATALLA NAVAL", Color.white);
        northPanel.add(headerProject,FlowLayout.LEFT);

        // Creación botón help
        help = new JButton("HELP");
        help.addActionListener(escucha);
        help.setBackground(Color.white);
        help.setFocusable(false);
        help.setBorder(null);
        northPanel.add(help,FlowLayout.CENTER);

        // Se agrega escucha al botón de la información de los botones de la clase fleetPanel
        fleetPanel.getExplicacionBotones().addActionListener(escucha);

        // JComponents de la parte central
        boardPanel = new BoardPanel();
        paintFleet = new PaintFleet(boardPanel, fleetPanel);
        eastPanel.add(boardPanel);

        // Flota
        centerPanel.add(fleetPanel);

        //JComponents de la parte Inferior
        // Creación de botón comenzar partida
        startGame = new JButton("PLAY");
        startGame.addActionListener(escucha);
        startGame.setBackground(Color.white);
        startGame.setFocusable(false);
        startGame.setBorder(null);
        southPanel.add(startGame,FlowLayout.LEFT);

        // Creación de botón de movimientos del oponente
        enemyMovements = new JButton("ENEMIGO");
        enemyMovements.addActionListener(escucha);
        enemyMovements.setBackground(Color.white);
        enemyMovements.setFocusable(false);
        enemyMovements.setBorder(null);
        southPanel.add(enemyMovements,FlowLayout.CENTER);

        // Creación del botón de reinicio
        restart = new JButton("REINICIO");
        restart.addActionListener(escucha);
        restart.setBackground(Color.white);
        restart.setFocusable(false);
        restart.setBorder(null);
        southPanel.add(restart,FlowLayout.CENTER);

        // Se agrega él escucha a todos los botones de todas las clases
        setEscuchaBotones("remover");
        setVerticalHorizontal("remover");
        setOrientacionSentidoVertical("remover");
        setOrientacionSentidoHorizontal("remover");

        // Se distribuye la flota del oponente
        while(opponentView.getPintarFlotaOponente().cantidadTotalNaves() > 0){
            opponentView.distribucionFlotaOponente();
        }

        // Objeto para invocar funciones de combate
        model = new Model(boardPanel, opponentView.getTableroOponente());

        sunk = 0;
        // Timer para el turno del oponente
        timer = new Timer(2000, escucha);
    }

    /**
     * Reinicia el juego
     */
    public void reset(){
        this.dispose();
        View gui = new View();
    }

    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            View miProjectGUI = new View();
        });
    }

    /**
     * Agrega o remueve él escucha al botón de cada barco
     */
    public void setEscuchaBotones(String evento){
        if(evento == "agregar"){
            fleetPanel.getBotonBarco("portavion").addActionListener(escucha);
            fleetPanel.getBotonBarco("destructor").addActionListener(escucha);
            fleetPanel.getBotonBarco("fragata").addActionListener(escucha);
            fleetPanel.getBotonBarco("submarino").addActionListener(escucha);
            fleetPanel.getBotonBarco("portavion").setEnabled(true);
            fleetPanel.getBotonBarco("destructor").setEnabled(true);
            fleetPanel.getBotonBarco("fragata").setEnabled(true);
            fleetPanel.getBotonBarco("submarino").setEnabled(true);
        }else{
            fleetPanel.getBotonBarco("portavion").removeActionListener(escucha);
            fleetPanel.getBotonBarco("destructor").removeActionListener(escucha);
            fleetPanel.getBotonBarco("fragata").removeActionListener(escucha);
            fleetPanel.getBotonBarco("submarino").removeActionListener(escucha);
            fleetPanel.getBotonBarco("portavion").setEnabled(false);
            fleetPanel.getBotonBarco("destructor").setEnabled(false);
            fleetPanel.getBotonBarco("fragata").setEnabled(false);
            fleetPanel.getBotonBarco("submarino").setEnabled(false);
        }
    }

    /**
     *Agrega o remueve él escucha a los botones Vertical y Horizontal
     * @param evento
     */
    public void setVerticalHorizontal(String evento){
        if(evento == "agregar"){
            fleetPanel.getBotonOrientacion("vertical").addActionListener(escucha);
            fleetPanel.getBotonOrientacion("horizontal").addActionListener(escucha);
            fleetPanel.getBotonOrientacion("vertical").setEnabled(true);
            fleetPanel.getBotonOrientacion("horizontal").setEnabled(true);
        }else{
            fleetPanel.getBotonOrientacion("vertical").removeActionListener(escucha);
            fleetPanel.getBotonOrientacion("horizontal").removeActionListener(escucha);
            fleetPanel.getBotonOrientacion("vertical").setEnabled(false);
            fleetPanel.getBotonOrientacion("horizontal").setEnabled(false);
        }
    }

    /**
     * Agrega o remueve él escucha de los botones verticales
     * @param evento
     */
    public void setOrientacionSentidoVertical(String evento){
        if(evento == "agregar"){
            fleetPanel.getBotonSentidoOrientacion("sup_inf").addActionListener(escucha);
            fleetPanel.getBotonSentidoOrientacion("inf_sup").addActionListener(escucha);
            fleetPanel.getBotonSentidoOrientacion("sup_inf").setEnabled(true);
            fleetPanel.getBotonSentidoOrientacion("inf_sup").setEnabled(true);
        }else{
            fleetPanel.getBotonSentidoOrientacion("sup_inf").removeActionListener(escucha);
            fleetPanel.getBotonSentidoOrientacion("inf_sup").removeActionListener(escucha);
            fleetPanel.getBotonSentidoOrientacion("sup_inf").setEnabled(false);
            fleetPanel.getBotonSentidoOrientacion("inf_sup").setEnabled(false);
        }
    }

    /**
     * Agrega o remueve él escucha de los botones horizontales
     * @param evento
     */
    public void setOrientacionSentidoHorizontal(String evento){
        if(evento == "agregar"){
            fleetPanel.getBotonSentidoOrientacion("der_izq").addActionListener(escucha);
            fleetPanel.getBotonSentidoOrientacion("izq_der").addActionListener(escucha);
            fleetPanel.getBotonSentidoOrientacion("der_izq").setEnabled(true);
            fleetPanel.getBotonSentidoOrientacion("izq_der").setEnabled(true);
        }else{
            fleetPanel.getBotonSentidoOrientacion("der_izq").removeActionListener(escucha);
            fleetPanel.getBotonSentidoOrientacion("izq_der").removeActionListener(escucha);
            fleetPanel.getBotonSentidoOrientacion("der_izq").setEnabled(false);
            fleetPanel.getBotonSentidoOrientacion("izq_der").setEnabled(false);
        }
    }

    /**
     * Agrega o remueve él escucha a cada uno de los JLabel de la matriz posición de PintarTablero
     * @param evento
     */
    public void setEscuchaCasillas(String evento){
        if(evento == "agregar"){
            for (int row = 0; row < boardPanel.getTablero("posicion").getMatriz().length; row++) {
                for (int col = 0; col < boardPanel.getTablero("posicion").getMatriz()[row].length; col++) {
                    boardPanel.getTablero("posicion").getMatriz()[row][col].addMouseListener(escucha);
                }
            }
        }else{
            for (int row = 0; row < boardPanel.getTablero("posicion").getMatriz().length; row++) {
                for (int col = 0; col < boardPanel.getTablero("posicion").getMatriz()[row].length; col++) {
                    boardPanel.getTablero("posicion").getMatriz()[row][col].removeMouseListener(escucha);
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
            for (int row = 0; row < boardPanel.getTablero("principal").getMatriz().length; row++) {
                for (int col = 0; col < boardPanel.getTablero("principal").getMatriz()[row].length; col++) {
                    boardPanel.getTablero("principal").getMatriz()[row][col].addMouseListener(escucha);
                }
            }
        }else{
            for (int row = 0; row < boardPanel.getTablero("principal").getMatriz().length; row++) {
                for (int col = 0; col < boardPanel.getTablero("principal").getMatriz()[row].length; col++) {
                    boardPanel.getTablero("principal").getMatriz()[row][col].removeMouseListener(escucha);
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
        opponentView.getTableroOponente().getTableroOponente("posicion").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/tocado.png")));
        boardPanel.getTablero("principal").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/tocado.png")));
        boardPanel.getTablero("principal").getCasillasOcupadas().replace(boardPanel.getTablero("principal").getMatriz()[row][col], Integer.valueOf(2));

        // Reduce las casillas ocupadas del barco tocado para poder ser hundido
        opponentView.getTableroOponente().getTableroOponente("posicion").reducirCasillasUsadas(barco);

        // Si no hay mas casillas ocupadas, el barco se hunde y se establecen las imagenes respectivas
        if(opponentView.getTableroOponente().getTableroOponente("posicion").getCasillaBarco().get(opponentView.getTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]) == Integer.valueOf(0)){
            fleetPanel.getInformacionJuego().setText("Barco hundido, selecciona otra casilla");
            gameState = 5;
            sunk++;
            for (int fil = 1; fil < 11; fil++) {
                for (int colu = 1; colu < 11; colu++) {
                    if(opponentView.getTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(opponentView.getTableroOponente().getTableroOponente("posicion").getMatriz()[fil][colu]) != null){
                        if(opponentView.getTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(opponentView.getTableroOponente().getTableroOponente("posicion").getMatriz()[fil][colu]).equals(barco)){
                            opponentView.getTableroOponente().getTableroOponente("posicion").getMatriz()[fil][colu].setIcon(new ImageIcon(getClass().getResource("/recursos/hundido.png")));
                            boardPanel.getTablero("principal").getMatriz()[fil][colu].setIcon(new ImageIcon(getClass().getResource("/recursos/hundido.png")));
                        }
                    }else{
                        continue;
                    }
                }
            }
        }else{
            fleetPanel.getInformacionJuego().setText("Tocaste una nave, selecciona otra casilla");
            gameState = 5;
        }

        if(sunk == 10){
            fleetPanel.getInformacionJuego().setText("Todos los barcos enemigos han sido hundidos, ganaste el juego");
            setEscuchaCasillasPrincipal("remover");
        }
    }

    /**
     * Retorna el objeto de clase BoardPanel
     * @return BoardPanel
     */
    public BoardPanel getPanelTablero(){
        return boardPanel;
    }

    /**
     * clase interna que extiende una clase de adaptador o implementa oyentes utilizados por la clase GUI
     */
    private class Escucha implements ActionListener, MouseListener {

        @Override


        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == restart) {
                reset();
            } else {
                if (e.getSource() == help) {
                    JOptionPane.showMessageDialog(null, HELP, "¿Cómo se juega batalla naval?", JOptionPane.PLAIN_MESSAGE);
                } else {
                    if (e.getSource() == startGame) {
                        startGame.removeActionListener(this);
                        setEscuchaBotones("agregar");
                        setVerticalHorizontal("remover");
                        setOrientacionSentidoVertical("remover");
                        setOrientacionSentidoHorizontal("remover");
                        fleetPanel.getAsignarTurno().setText("¡Tu turno!");
                        fleetPanel.getInformacionJuego().setText("Selecciona la nave que quieres desplegar");
                    } else {
                        if (e.getSource() == enemyMovements) {
                            opponentView.setVisible(true);
                        } else {
                            if (e.getSource() == fleetPanel.getExplicacionBotones()) {
                                JOptionPane.showMessageDialog(null, "", "Cómo jugar", JOptionPane.PLAIN_MESSAGE, Instructions);
                            } else {
                                if (gameState == 6) {
                                    if (e.getSource() == timer) {
                                        opponentView.oponenteVsUsuario();
                                        if (opponentView.getEstado() == 0) {
                                            timer.stop();
                                            gameState = 5;
                                            fleetPanel.getAsignarTurno().setText("Tu turno");
                                            fleetPanel.getInformacionJuego().setText("Selecciona otra casilla del tablero principal");
                                        } else {
                                            if (opponentView.getEstado() == 2) {
                                                timer.stop();
                                                fleetPanel.getInformacionJuego().setText("Tus barcos han sido hundidos, perdiste el juego");
                                            }
                                        }
                                    }
                                } else {
                                    switch (gameState) {
                                        case 1:
                                            if (e.getSource() == fleetPanel.getBotonBarco("portavion")) {
                                                if (fleetPanel.getCantidadBarco("portavion") > 0) {
                                                    fleetPanel.setCantidadBarco("portavion");
                                                    setEscuchaBotones("remover");
                                                    fleetPanel.getInformacionJuego().setText("Escoge si quieres ubicarlo vertical u horizontal");
                                                    setVerticalHorizontal("agregar");
                                                    fleetPanel.setNombreBoton("portavion");
                                                    gameState = 2;
                                                } else {
                                                    fleetPanel.getInformacionJuego().setText("No hay más portaviones disponibles");
                                                }
                                            } else {
                                                if (e.getSource() == fleetPanel.getBotonBarco("destructor")) {
                                                    if (fleetPanel.getCantidadBarco("destructor") > 0) {
                                                        fleetPanel.setCantidadBarco("destructor");
                                                        setEscuchaBotones("remover");
                                                        fleetPanel.getInformacionJuego().setText("Escoge si quieres ubicarlo vertical u horizontal");
                                                        setVerticalHorizontal("agregar");
                                                        fleetPanel.setNombreBoton("destructor");
                                                        gameState = 2;
                                                    } else {
                                                        fleetPanel.getInformacionJuego().setText("No hay más destructores disponibles");
                                                    }
                                                } else {
                                                    if (e.getSource() == fleetPanel.getBotonBarco("fragata")) {
                                                        if (fleetPanel.getCantidadBarco("fragata") > 0) {
                                                            fleetPanel.setCantidadBarco("fragata");
                                                            setEscuchaBotones("remover");
                                                            fleetPanel.getInformacionJuego().setText("Escoge si quieres ubicarlo vertical u horizontal");
                                                            setVerticalHorizontal("agregar");
                                                            fleetPanel.setNombreBoton("fragata");
                                                            gameState = 2;
                                                        } else {
                                                            fleetPanel.getInformacionJuego().setText("No hay más fragatas disponibles");
                                                        }
                                                    } else {
                                                        if (e.getSource() == fleetPanel.getBotonBarco("submarino")) {
                                                            if (fleetPanel.getCantidadBarco("submarino") > 0) {
                                                                fleetPanel.setCantidadBarco("submarino");
                                                                setEscuchaBotones("remover");
                                                                fleetPanel.getInformacionJuego().setText("Escoge si quieres ubicarlo vertical u horizontal");
                                                                setVerticalHorizontal("agregar");
                                                                fleetPanel.setNombreBoton("submarino");
                                                                gameState = 2;
                                                            } else {
                                                                fleetPanel.getInformacionJuego().setText("No hay más submarinos disponibles");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            break;
                                        case 2:
                                            if (e.getSource() == fleetPanel.getBotonOrientacion("vertical")) {
                                                setVerticalHorizontal("remover");
                                                fleetPanel.getInformacionJuego().setText("Escoge cuál sentido quieres usar");
                                                setOrientacionSentidoVertical("agregar");
                                                fleetPanel.setOrientacion(0);
                                                gameState = 3;
                                            } else {
                                                if (e.getSource() == fleetPanel.getBotonOrientacion("horizontal")) {
                                                    setVerticalHorizontal("remover");
                                                    fleetPanel.getInformacionJuego().setText("Escoge cuál sentido quieres usar");
                                                    setOrientacionSentidoHorizontal("agregar");
                                                    fleetPanel.setOrientacion(1);
                                                    gameState = 3;
                                                }
                                            }
                                            break;
                                        case 3:
                                            if (e.getSource() == fleetPanel.getBotonSentidoOrientacion("sup_inf")) {
                                                setOrientacionSentidoVertical("remover");
                                                fleetPanel.getInformacionJuego().setText("Selecciona la casilla en la que quieres ubicar la nave");
                                                setEscuchaCasillas("agregar");
                                                fleetPanel.setSentidoOrientacion(1);
                                                gameState = 4;
                                            } else {
                                                if (e.getSource() == fleetPanel.getBotonSentidoOrientacion("inf_sup")) {
                                                    setOrientacionSentidoVertical("remover");
                                                    fleetPanel.getInformacionJuego().setText("Selecciona la casilla en la que quieres ubicar la nave");
                                                    setEscuchaCasillas("agregar");
                                                    fleetPanel.setSentidoOrientacion(2);
                                                    gameState = 4;
                                                } else {
                                                    if (e.getSource() == fleetPanel.getBotonSentidoOrientacion("izq_der")) {
                                                        setOrientacionSentidoHorizontal("remover");
                                                        fleetPanel.getInformacionJuego().setText("Selecciona la casilla en la que quieres ubicar la nave");
                                                        setEscuchaCasillas("agregar");
                                                        fleetPanel.setSentidoOrientacion(3);
                                                        gameState = 4;
                                                    } else {
                                                        if (e.getSource() == fleetPanel.getBotonSentidoOrientacion("der_izq")) {
                                                            setOrientacionSentidoHorizontal("remover");
                                                            fleetPanel.getInformacionJuego().setText("Selecciona la casilla en la que quieres ubicar la nave");
                                                            setEscuchaCasillas("agregar");
                                                            fleetPanel.setSentidoOrientacion(4);
                                                            gameState = 4;
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
            switch(gameState){
                case 4:
                    for (int row = 1; row < 11; row++) {
                        for (int col = 1; col < 11; col++) {
                            if(e.getSource() == boardPanel.getTablero("posicion").getMatriz()[row][col]) {
                                // Condicional para saber si el usuario pudo colocar el barco
                                if(paintFleet.funcionesFlota(fleetPanel.getNombreBoton(), fleetPanel.getOrientacion(), fleetPanel.getSentidoOrientacion(), col, row)){
                                    if(fleetPanel.cantidadTotalNaves() > 0){
                                        setEscuchaCasillas("remover");
                                        fleetPanel.getInformacionJuego().setText("Escoge otro barco");
                                        setEscuchaBotones("agregar");
                                        gameState = 1;
                                    }else{
                                        setEscuchaCasillas("remover");
                                        fleetPanel.getInformacionJuego().setText("El combate comienza, selecciona una casilla del tablero principal");
                                        model.usuarioVsOponente();
                                        model.oponenteVsUsuario();
                                        setEscuchaCasillasPrincipal("agregar");
                                        gameState = 5;
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
                            if(e.getSource() == boardPanel.getTablero("principal").getMatriz()[row][col]) {
                                // Verifica si la casilla seleccionada hay un barco oponente
                                if(boardPanel.getTablero("principal").getCasillasOcupadas().get(boardPanel.getTablero("principal").getMatriz()[row][col]) == Integer.valueOf(1)){
                                    // Verifica si todas las casillas del barco fueron seleccionadas
                                    if(opponentView.getTableroOponente().getTableroOponente("posicion").getCasillaBarco().get(opponentView.getTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]) != Integer.valueOf(0)){
                                        for(int num=1; num < 11; num++){
                                            if(opponentView.getTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(opponentView.getTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]).equals("portavion" + String.valueOf(num))){
                                                funcionesCombate(row, col, "portavion" + String.valueOf(num));
                                                break;
                                            }else{
                                                if(opponentView.getTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(opponentView.getTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]).equals("submarino" + String.valueOf(num))){
                                                    funcionesCombate(row, col, "submarino" + String.valueOf(num));
                                                    break;
                                                }else{
                                                    if(opponentView.getTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(opponentView.getTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]).equals("destructor" + String.valueOf(num))){
                                                        funcionesCombate(row, col, "destructor" + String.valueOf(num));
                                                        break;
                                                    }else{
                                                        if(opponentView.getTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(opponentView.getTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]).equals("fragata" + String.valueOf(num))){
                                                            funcionesCombate(row, col, "fragata" + String.valueOf(num));
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }else{
                                    if(boardPanel.getTablero("principal").getCasillasOcupadas().get(boardPanel.getTablero("principal").getMatriz()[row][col]) == Integer.valueOf(2)){
                                        fleetPanel.getInformacionJuego().setText("Casilla usada, presiona otra");
                                        gameState = 5;
                                    }else{
                                        fleetPanel.getInformacionJuego().setText("Le diste al agua, espera el turno del oponente");
                                        boardPanel.getTablero("principal").getCasillasOcupadas().put(boardPanel.getTablero("principal").getMatriz()[row][col], Integer.valueOf(2));
                                        opponentView.getTableroOponente().getTableroOponente("posicion").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/agua.png")));
                                        boardPanel.getTablero("principal").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/agua.png")));
                                        gameState = 6;
                                        fleetPanel.getAsignarTurno().setText("¡Turno del oponente!");
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