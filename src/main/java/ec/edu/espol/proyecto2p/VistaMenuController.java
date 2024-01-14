package ec.edu.espol.proyecto2p;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
public class VistaMenuController implements Initializable {

    @FXML
    private Button btn1vs1;
    @FXML
    private Button btn1vsIA;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private void animarBoton(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setBlendMode(BlendMode.HARD_LIGHT);
        Animaciones.agrandar_boton(button);
        button.setOnMouseExited((t) -> {
            button.setBlendMode(null);
            Animaciones.achicar_boton(button);
        });
    }
  
    @FXML
    private void animarBotonJuego(MouseEvent event){
        animarBoton(event);
    }
    
    private void regresar(MouseEvent event) {
        FXMLLoader fml;
        try {
            fml = App.loadFXML("VistaMenu");
            Scene s = new Scene(fml.load(),600,600);
            VistaJuegoController cc = fml.getController();
            Stage st = new Stage();
            st.setScene(s);
            st.show();
            
            Button b = (Button)event.getSource();
            Stage curr = (Stage)b.getScene().getWindow();
            curr.close();
        } catch (IOException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR,"No se pudo abrir el archivo fxml");
                a.show();
        }
    }
    
    @FXML
    private void abrirVistaJuego1vsIA(MouseEvent event){
        try {
            // Crear un cuadro de diálogo personalizado con un GridPane
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Nombre de Jugador");
            dialog.setHeaderText(null);

            // Crear un GridPane y configurar los campos de texto
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            TextField nombreJugador1 = new TextField();
            grid.add(new Label("Jugador 1:"), 0, 0);
            grid.add(nombreJugador1, 1, 0);
            dialog.getDialogPane().setContent(grid);
            // Mostrar el cuadro de diálogo y obtener los nombres de los jugadores
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String j1 = nombreJugador1.getText();

                // Cargar el FXML y establecer los nombres de los jugadores
                FXMLLoader fml = App.loadFXML("VistaJuego");
                Scene s = new Scene(fml.load(), 1100, 480);
                VistaJuegoController cc = fml.getController();
                cc.setNombreJugador(j1);

                // Configurar y mostrar la ventana
                Stage st = new Stage();
                st.setScene(s);
                st.show();

                // Cerrar la ventana actual
                Button b = (Button) event.getSource();
                Stage curr = (Stage) b.getScene().getWindow();
                curr.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    private void abrirVistaJuego1vs1(MouseEvent event){
        try {
            // Crear un cuadro de diálogo personalizado con un GridPane
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Nombres de Jugadores");
            dialog.setHeaderText(null);

            // Crear un GridPane y configurar los campos de texto
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            TextField nombreJugador1 = new TextField();
            TextField nombreJugador2 = new TextField();
            grid.add(new Label("Jugador 1:"), 0, 0);
            grid.add(nombreJugador1, 1, 0);
            grid.add(new Label("Jugador 2:"), 0, 1);
            grid.add(nombreJugador2, 1, 1);
            dialog.getDialogPane().setContent(grid);

            // Mostrar el cuadro de diálogo y obtener los nombres de los jugadores
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String j1 = nombreJugador1.getText();
                String j2 = nombreJugador2.getText();

                // Cargar el FXML y establecer los nombres de los jugadores
                FXMLLoader fml = App.loadFXML("VistaJuego");
                Scene s = new Scene(fml.load(), 1100, 480);
                VistaJuegoController cc = fml.getController();
                cc.setNombresJugadores(j1, j2);

                // Configurar y mostrar la ventana
                Stage st = new Stage();
                st.setScene(s);
                st.show();

                // Cerrar la ventana actual
                Button b = (Button) event.getSource();
                Stage curr = (Stage) b.getScene().getWindow();
                curr.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
