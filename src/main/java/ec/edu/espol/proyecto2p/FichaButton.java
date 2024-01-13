package ec.edu.espol.proyecto2p;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FichaButton extends Button {
    Ficha fichaReferenciada;
    
    public FichaButton(Ficha ficha, Image image) {
        super();
        this.fichaReferenciada = ficha;
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        this.setStyle("-fx-border-color: transparent; -fx-background-color: lightgray;");
        this.setGraphic(imageView);
    }

    public Ficha getFichaReferenciada() {
        return fichaReferenciada;
    }
    
    public static void construirFichasEn(Pane panel, Image[] images, ArrayList<Ficha> mano) {
        VBox vBox = new VBox();  
        vBox.setFillWidth(true);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        vBox.setSpacing(6);
        if (!mano.isEmpty()) {  // Verifica si la lista no está vacía
            for (int i = 0; i < images.length; i++) {
                FichaButton fichaButton = new FichaButton(mano.get(i), images[i]);
                vBox.getChildren().add(fichaButton);
            }
        }
        Rectangle background = new Rectangle(panel.getWidth(), panel.getHeight(), Color.LIGHTGRAY);
        panel.getChildren().addAll(background, vBox);
        panel.setStyle("-fx-background-color: lightgray;");
        panel.widthProperty().addListener((obs, oldWidth, newWidth) -> resizeButtons(panel, vBox));
        panel.heightProperty().addListener((obs, oldHeight, newHeight) -> resizeButtons(panel, vBox));
    }


    public static void resizeButtons(Pane panel, VBox vBox) {
        double panelWidth = panel.getWidth();
        double panelHeight = panel.getHeight();
        double buttonWidth = panelWidth / vBox.getChildren().size() * 2.9;
        double buttonHeight = panelHeight;
        // Ajustar el tamaño de cada botón
        vBox.getChildren().forEach(node -> {
            if (node instanceof FichaButton) {
                FichaButton fichaButton = (FichaButton) node;
                ImageView imageView = (ImageView) fichaButton.getGraphic();
                imageView.setFitWidth(buttonWidth);
                imageView.setFitHeight(buttonHeight);
                fichaButton.setMaxHeight(vBox.getHeight() / vBox.getChildren().size());
                fichaButton.setMaxWidth(vBox.getWidth() / vBox.getChildren().size());
            }
        });
        panel.getChildren().forEach(node -> {
            if (node instanceof VBox) {
                VBox vbox = (VBox) node;
                vbox.setAlignment(Pos.CENTER);
                vbox.setFillWidth(true);
                vbox.setPrefHeight(panelHeight);
                vbox.setPrefWidth(panelWidth);
            }
        });
    }
    
    public static void resizeButtons(Pane panel,HBox hBox) {
        hBox.getChildren().forEach((node) -> {
            if (node instanceof FichaButton) {
                FichaButton fichaButton = (FichaButton) node;
                fichaButton.setMinHeight(37);
                fichaButton.setMinWidth(hBox.getWidth() / hBox.getChildren().size()+70.5);
                
            }
        });
    
    }
    
    public void resizeItself(){
        double buttonWidth;
        double buttonHeight;
        if (this.getParent()!=null){
            Parent parent = (Parent) this.getParent();
            if (parent instanceof Pane){
                System.out.println(parent.toString());
                Pane thing = (Pane) parent;
                buttonWidth = thing.getWidth() / 6 * 2.9;
                buttonHeight = thing.getHeight();
                ImageView imageView = (ImageView) this.getGraphic();
                imageView.setFitWidth(buttonWidth);
                imageView.setFitHeight(buttonHeight);
            }
        }
    }
    
    public static List<Object> comodin() {
        // Opciones para la dirección
        List<String> choices = Arrays.asList("Izquierda", "Derecha");
        List<Object> lista = new ArrayList<>();

        // Crear un ChoiceDialog para la dirección
        ChoiceDialog<String> directionDialog = new ChoiceDialog<>(choices.get(0), choices);
        directionDialog.setTitle("Alerta con Selección");
        directionDialog.setHeaderText("Elige una dirección:");
        directionDialog.setContentText("Dirección:");

        // Mostrar y esperar hasta que el usuario seleccione una dirección
        Optional<String> selectedDirection = directionDialog.showAndWait();

        // Variables para almacenar la entrada del usuario
        String numberStr;
        int number;

        // Bucle do-while para solicitar un número válido
        do {
            // Crear un TextInputDialog para el número
            TextInputDialog numberDialog = new TextInputDialog("1");
            numberDialog.setTitle("Alerta con Ingreso");
            numberDialog.setHeaderText("Ingresa un número del 1 al 6:");
            numberDialog.setContentText("Número:");

            // Mostrar y esperar hasta que el usuario ingrese un número
            Optional<String> enteredNumber = numberDialog.showAndWait();

            // Obtener la entrada del usuario y validar si es un número válido
            numberStr = enteredNumber.orElse("0"); // Valor predeterminado si no se ingresa nada
            try {
                number = Integer.parseInt(numberStr);
            } catch (NumberFormatException e) {
                number = 0; // Establecer un valor que no esté en el rango para continuar el bucle
            }

            
        } while (number < 1 || number > 6);

        // Procesar las selecciones e ingresos
        if (selectedDirection.isPresent()) {
            String direction = selectedDirection.get(); 
            lista.add(direction);
            lista.add(number);
            // Mostrar una alerta informativa con las selecciones
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alerta");
            alert.setHeaderText(null);
            alert.setContentText("Dirección seleccionada: " + direction + "\nNúmero ingresado: " + number);
            alert.showAndWait();
        }
        return lista;
    }
}
