package myProject;
/**
 * Clase Combate
 * @autor Esteban Camilo Martinez Urbano-esteban.urbano@correounivalle.edu.co
 * esteban.urbano@correounivalle.edu.co
 * @autor David Ordoñez-david.camilo.ordonez@correounivalle.edu.co
 * david.camilo.ordonez@correounivalle.edu.co
 * @autor Juan Felipe Palechor
 * @autor Jhon Frank Vasquez - jhon.frank.vasquez@correounivalle.edu.co
 * @version 1.0
 * 04/07/2023
 */
public class Modelo {
    private PanelTablero panelTablero;
    private PanelTableroOponente tableroOponente;

    /**
     * Constructor de la clase Combate
     * @param _panelTablero
     * @param _panelTableroOponente
     */
    public Modelo(PanelTablero _panelTablero, PanelTableroOponente _panelTableroOponente){
        this.panelTablero =_panelTablero;
        this.tableroOponente = _panelTableroOponente;
    }

    /**
     * Busca las casillas ocupadas por naves del tablero posición del oponente y las marca en el tablero principal del usuario
     */
    public void usuarioVsOponente(){
        for(int row = 1; row < 11; row++) {
            for (int col = 1; col < 11; col++) {
                if(tableroOponente.getTableroOponente("posicion").getCasillasOcupadas().get(tableroOponente.getTableroOponente("posicion").getMatriz()[row][col]) == Integer.valueOf(1)){
                    panelTablero.getTablero("principal").getCasillasOcupadas().put(panelTablero.getTablero("principal").getMatriz()[row][col], 1);
                }
            }
        }
    }

    /**
     * Busca las casillas ocupadas por naves del tablero posición del usuario y las marca en el tablero principal del oponente
     */
    public void oponenteVsUsuario(){
        for(int row = 1; row < 11; row++) {
            for (int col = 1; col < 11; col++) {
                if(panelTablero.getTablero("posicion").getCasillasOcupadas().get(panelTablero.getTablero("posicion").getMatriz()[row][col]) == Integer.valueOf(1)){
                    tableroOponente.getTableroOponente("principal").getCasillasOcupadas().put(tableroOponente.getTableroOponente("principal").getMatriz()[row][col], 1);
                }
            }
        }
    }
}
