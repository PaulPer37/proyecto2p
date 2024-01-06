package ec.edu.espol.proyecto2p;

import javafx.animation.Animation;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;

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
}
