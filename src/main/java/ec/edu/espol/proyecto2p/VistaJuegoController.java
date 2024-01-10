package ec.edu.espol.proyecto2p;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
public class VistaJuegoController implements Initializable {

    @FXML
    public Pane J1Panel;
    @FXML
    public Pane J2Panel;
    @FXML
    public BorderPane TableroPanel;
    @FXML
    public BorderPane MainPanel;
    @FXML
    private Label lblQuienJuega;
    @FXML
    private HBox HboxContainer;
    @Override
    public void initialize(URL url, ResourceBundle rb){
        jugar1v1();
    }

    public void jugar1v1(){
        //Asumiendo que estoy ya jugando contra una persona en la VistaJuego
        Jugador j1 = new Jugador("Jugador1", (ArrayList<Ficha>) Utilitaria.crearManoJugador());
        Jugador j2 = new Jugador("Jugador2", (ArrayList<Ficha>) Utilitaria.crearManoJugador());
        j1.cargarFichasJugador(J1Panel);
        j2.cargarFichasJugador(J2Panel);
        Tablero tablero = new Tablero(j1,j2, TableroPanel,HboxContainer);
        tablero.tableroDisplaysOn(MainPanel,TableroPanel);
        tablero.addTurnoListener(j1, J1Panel, HboxContainer, lblQuienJuega);
        tablero.addTurnoListener(j2, J2Panel, HboxContainer, lblQuienJuega);
    }
     
}
