package ec.edu.espol.proyecto2p;

import java.time.Duration;
import javafx.animation.Animation;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.HBox;
import javafx.animation.TranslateTransition;

public abstract class Animaciones extends Animation{
    public static void animar_boton(Button button){
        button.setOnMouseEntered((event) -> {
            button.setBlendMode(BlendMode.HARD_LIGHT);
        });
        
        button.setOnMouseExited((event) -> {
            button.setBlendMode(null);
        });
    }
    
    public static void desanimar_boton(Button button){
        button.setOnMouseEntered((event) -> {
            button.setBlendMode(null);
        });
    }
    
    public static void mover_ficha_button(FichaButton fichaButton, HBox HboxContainer, int position){
        // Crear una transición de posición
        javafx.util.Duration drtn = new javafx.util.Duration(2);
        TranslateTransition transition = new TranslateTransition(drtn, fichaButton);
        // Establecer la posición final (puedes ajustar estos valores según tus necesidades)
        transition.setToX(0);
        transition.setToY(0);

        // Configurar cualquier otra opción de la transición si es necesario
        transition.setCycleCount(1);

        // Manejar el evento de finalización de la transición si es necesario
        transition.setOnFinished(event -> {
            // Lógica que deseas ejecutar después de mover la ficha al HBox
            HboxContainer.getChildren().add(position, fichaButton);
            HboxContainer.requestLayout();
            fichaButton.setStyle("-fx-background-color: black");
        });

        // Iniciar la transición
       transition.play();
    }
}
