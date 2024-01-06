package ec.edu.espol.proyecto2p;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
    
}
