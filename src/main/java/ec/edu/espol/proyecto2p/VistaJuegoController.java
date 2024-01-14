package ec.edu.espol.proyecto2p;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
public class VistaJuegoController implements Initializable {
    public VistaJuegoController(){
        super();
    }
    public VistaJuegoController(String j1,String j2){
        super();
        jugar1v1(j1, j2);
    }
    
    public VistaJuegoController(String j1){
        super();
        jugar1vsIA(j1);
    }
    private String nombreJugador1;
    private String nombreJugador2;

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
    @FXML
    private Label lblJugador1;
    @FXML
    private Label lblJugador2;
    @Override
    public void initialize(URL url, ResourceBundle rb){
        lblQuienJuega.setFont(new Font("Arial", 24));
        lblQuienJuega.setTextAlignment(TextAlignment.CENTER);
        lblQuienJuega.setAlignment(Pos.CENTER);
    }

    public void jugar1v1(String name1,String name2){
        //Asumiendo que estoy ya jugando contra una persona en la VistaJuego
        Jugador j1 = new Jugador(name1, (ArrayList<Ficha>) Utilitaria.crearManoJugador());
        Jugador j2 = new Jugador(name2, (ArrayList<Ficha>) Utilitaria.crearManoJugador());
        j1.cargarFichasJugador(J1Panel);
        j2.cargarFichasJugador(J2Panel);
        lblJugador1.setText(j1.getNombre());
        lblJugador2.setText(j2.getNombre());
        Tablero tablero = new Tablero(j1,j2, TableroPanel,HboxContainer);
        tablero.tableroDisplaysOn(MainPanel,TableroPanel);
        tablero.addTurnoListener(j1, J1Panel, HboxContainer, lblQuienJuega);
        tablero.addTurnoListener(j2, J2Panel, HboxContainer, lblQuienJuega);
        HboxContainer.setOnMouseEntered((t) -> {
            ObservableList<Node> children = HboxContainer.getChildren();
            for (Node node : children) {
                if (node instanceof FichaButton) {
                    FichaButton fichaButton = (FichaButton) node;
                    fichaButton.setOnMouseClicked(null);
                }
            }
        });
        HboxContainer.getChildren().addListener((ListChangeListener<Node>) change -> {
            for (Node node : HboxContainer.getChildren()) {
                if (node instanceof FichaButton) {
                    FichaButton fichaButton = (FichaButton) node;
                    fichaButton.resizeItself();
                }
            }
        });
    }
    
    public void jugar1vsIA(String name1){
        Jugador j1 = new Jugador(name1, (ArrayList<Ficha>) Utilitaria.crearManoJugador());
        Jugador j2 = new Jugador("Computadora", (ArrayList<Ficha>) Utilitaria.crearManoJugador());
        j1.cargarFichasJugador(J1Panel);
        j2.cargarFichasJugador(J2Panel);
        lblJugador1.setText(j1.getNombre());
        lblJugador2.setText(j2.getNombre());
        Tablero tablero = new Tablero(j1,j2, TableroPanel,HboxContainer);
        tablero.tableroDisplaysOn(MainPanel,TableroPanel);
        tablero.addTurnoListener(j1, J1Panel, HboxContainer, lblQuienJuega);
        tablero.addTurnoMaquinaListener(j2, J2Panel, HboxContainer, lblQuienJuega);
        HboxContainer.setOnMouseEntered((t) -> {
            ObservableList<Node> children = HboxContainer.getChildren();
            for (Node node : children) {
                if (node instanceof FichaButton) {
                    FichaButton fichaButton = (FichaButton) node;
                    fichaButton.setOnMouseClicked(null);
                }
            }
        });
        
        
        HboxContainer.getChildren().addListener((ListChangeListener<Node>) change -> {

            for (Node node : HboxContainer.getChildren()) {
                
                if (node instanceof FichaButton) {
                    FichaButton fichaButton = (FichaButton) node;
                    fichaButton.resizeItself();
                }
            }
        });
    }
    public void setNombresJugadores(String nombreJugador1, String nombreJugador2) {
        this.nombreJugador1 = nombreJugador1;
        this.nombreJugador2 = nombreJugador2;

        // Si la interfaz de usuario ya está cargada, llamamos a jugar1v1 inmediatamente
        if (J1Panel != null && J2Panel != null && TableroPanel != null && HboxContainer != null) {
            jugar1v1(nombreJugador1, nombreJugador2);
        }
    }
    public void setNombreJugador(String nombreJugador1) {
        this.nombreJugador1 = nombreJugador1;

        // Si la interfaz de usuario ya está cargada, llamamos a jugar1v1 inmediatamente
        if (J1Panel != null && J2Panel != null && TableroPanel != null && HboxContainer != null) {
            jugar1vsIA(nombreJugador1);
        }
    }
}
