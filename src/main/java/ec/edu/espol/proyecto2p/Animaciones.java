package ec.edu.espol.proyecto2p;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public abstract class Animaciones {
    public static void animar_boton(Button button) {
        button.setOnMouseEntered((event) -> {
            button.setBlendMode(BlendMode.HARD_LIGHT);
        });

        button.setOnMouseExited((event) -> {
            button.setBlendMode(null);
        });
    }
    
    public static void agrandar_boton(Button button) {
        ImageView imview = (ImageView) button.getGraphic();
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(500), imview);

        scaleIn.setFromX(1.0);
        scaleIn.setFromY(1.0);
        scaleIn.setToX(1.2);
        scaleIn.setToY(1.2);

        // Reproducir la transición
        scaleIn.play();
    }

    public static void achicar_boton(Button button) {
        ImageView imview = (ImageView) button.getGraphic();
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(500), imview);

        scaleOut.setFromX(1.2);
        scaleOut.setFromY(1.2);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        // Reproducir la transición
        scaleOut.play();
    }

    public static void desanimar_boton(Button button) {
        button.setOnMouseEntered((event) -> {
            button.setBlendMode(null);
        });
    }

    public static void desactivar_boton(Button button) {
        button.setBlendMode(BlendMode.DARKEN);
    }

    // Método usado en Tablero
    public static void mover_ficha_button(FichaButton fichaButton, VBox origen, HBox destino, int position) {
     // Agregar la FichaButton al HBox en la posición deseada
     destino.getChildren().add(position, fichaButton);

     // Obtener las coordenadas X e Y del HBox destino en relación con su contenedor padre (TableroPanel)
     // Eliminar la FichaButton del HBox
     destino.getChildren().remove(fichaButton);

     // Crear una transición de posición desde las coordenadas obtenidas
     Duration duration = new Duration(1000);
     TranslateTransition transition = new TranslateTransition(duration, fichaButton);

     // Establecer la posición final en el destino
//     transition.setInterpolator(Interpolator.EASE_BOTH);
//     fichaButton.setStyle("-fx-background-color: black");

     // Configurar cualquier otra opción de la transición si es necesario
     transition.setCycleCount(1);

     // Manejar el evento de finalización de la transición si es necesario
     transition.setOnFinished(event -> {
         // Lógica que deseas ejecutar después de mover la ficha al destino
         fichaButton.setVisible(true);
         destino.getChildren().add(position, fichaButton);
     });

     // Iniciar la transición
     transition.play();
 }


}
