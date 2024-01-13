package ec.edu.espol.proyecto2p;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
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
  
    private void switchToVistaJuego() throws IOException {
        App.setRoot("VistaJuego");
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
    private void abrirVistaJuego(MouseEvent event){
        FXMLLoader fml;
        try {
            fml = App.loadFXML("VistaJuego");
            Scene s = new Scene(fml.load(),900,480);
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
}
