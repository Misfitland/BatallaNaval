package myProject;

import javax.swing.*;
import java.awt.*;

/**
 * Clase PanelTablero
 * @autor Esteban Camilo Martinez Urbano-esteban.urbano@correounivalle.edu.co
 * esteban.urbano@correounivalle.edu.co
 * @autor David Ordoñez-david.camilo.ordonez@correounivalle.edu.co
 * david.camilo.ordonez@correounivalle.edu.co
 * @autor Juan Felipe Palechor
 * @autor Jhon Frank Vasquez - jhon.frank.vasquez@correounivalle.edu.co
 * @version 1.0
 * 04/07/2023
 */
public class PanelTablero extends JPanel {

    public static final String PATH ="/recursos/";
    private JLabel nombreTableroPosicion, nombreTableroPrincipal, imagenTiros;
    private BackgroundPane panelTableroPosicion, panelTableroPrincipal;
    private Tableros tableroPosicion, tableroPrincipal;
    private String letras[];

    /**
     * Constructor de la clase PanelTablero
     */
    public PanelTablero(){
        GridBagLayout gb = new GridBagLayout();
        this.setLayout(gb);
        this.setBackground(Color.WHITE);
        tableroPosicion = new Tableros();
        tableroPrincipal = new Tableros();
        letras = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        iniciar();
        modelTablero();
    }

    /**
     * Establece la configuración inicial del JComponent
     */
    public void iniciar(){
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel tablero posición
        nombreTableroPosicion = new JLabel("Tablero pocision");
        nombreTableroPosicion.setForeground(new Color(0, 0, 0, 230));
        nombreTableroPosicion.setFont(new Font(Font.MONOSPACED,Font.BOLD,15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(nombreTableroPosicion, gbc);

        panelTableroPosicion = new BackgroundPane();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0,15,0,15);
        this.add(panelTableroPosicion, gbc);

        // Panel tablero principal
        nombreTableroPrincipal = new JLabel("Tablero principal");
        nombreTableroPrincipal.setForeground(new Color(0, 0, 0, 230));
        nombreTableroPrincipal.setFont(new Font(Font.MONOSPACED,Font.BOLD,15));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(nombreTableroPrincipal, gbc);

        panelTableroPrincipal = new BackgroundPane();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0,15,0,15);
        this.add(panelTableroPrincipal, gbc);

    }

    /**
     * JPanel con imagen para agregar las matrices
     */
    public class BackgroundPane extends JPanel{
        private Image img;

        public BackgroundPane(){
            img = new ImageIcon(getClass().getResource("/recursos/mar.jpg")).getImage();
            this.setLayout(new GridLayout(11, 11));
            this.setPreferredSize(new Dimension(400, 400));
            this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        }

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(img, 0, 0, this);
            revalidate();
            repaint();
        }
    }

    /**
     * Crea los tableros posición y principal
     */
    public void modelTablero(){
        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 11; col++) {
                if(row == 0 && col == 0){
                    tableroPosicion.getMatriz()[row][col] = new JLabel();
                    tableroPosicion.getMatriz()[row][col].setOpaque(true);
                    tableroPosicion.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    tableroPosicion.getMatriz()[row][col].setBackground(Color.WHITE);

                    tableroPrincipal.getMatriz()[row][col] = new JLabel();
                    tableroPrincipal.getMatriz()[row][col].setOpaque(true);
                    tableroPrincipal.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    tableroPrincipal.getMatriz()[row][col].setBackground(Color.WHITE);
                }else{
                    if(row == 0 && col > 0){
                        tableroPosicion.getMatriz()[row][col] = new JLabel(letras[col-1], SwingConstants.CENTER);
                        tableroPosicion.getMatriz()[row][col].setOpaque(true);
                        tableroPosicion.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        tableroPosicion.getMatriz()[row][col].setBackground(Color.WHITE);

                        tableroPrincipal.getMatriz()[row][col] = new JLabel(letras[col-1], SwingConstants.CENTER);
                        tableroPrincipal.getMatriz()[row][col].setOpaque(true);
                        tableroPrincipal.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        tableroPrincipal.getMatriz()[row][col].setBackground(Color.WHITE);
                    }else{
                        if(row > 0 && col == 0){
                            tableroPosicion.getMatriz()[row][col] = new JLabel(String.valueOf(row), SwingConstants.CENTER);
                            tableroPosicion.getMatriz()[row][col].setOpaque(true);
                            tableroPosicion.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                            tableroPosicion.getMatriz()[row][col].setBackground(Color.WHITE);

                            tableroPrincipal.getMatriz()[row][col] = new JLabel(String.valueOf(row), SwingConstants.CENTER);
                            tableroPrincipal.getMatriz()[row][col].setOpaque(true);
                            tableroPrincipal.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                            tableroPrincipal.getMatriz()[row][col].setBackground(Color.WHITE);
                        }else{
                            tableroPosicion.getMatriz()[row][col] = new JLabel();
                            tableroPosicion.getMatriz()[row][col].setOpaque(false);
                            tableroPosicion.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                            tableroPrincipal.getMatriz()[row][col] = new JLabel();
                            tableroPrincipal.getMatriz()[row][col].setOpaque(false);
                            tableroPrincipal.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        }
                    }
                }

                panelTableroPosicion.add(tableroPosicion.getMatriz()[row][col]);
                panelTableroPrincipal.add(tableroPrincipal.getMatriz()[row][col]);
            }
        }
    }

    /**
     * Retorna el tablero ingresado
     */
    public Tableros getTablero(String _tablero){
        Tableros tablero = new Tableros();
        if(_tablero.equals("posicion")){
            tablero = tableroPosicion;
        }else{
            if(_tablero.equals("principal")){
                tablero = tableroPrincipal;
            }
        }
        return tablero;
    }
}