package ec.edu.espol.proyecto2p;
//
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Tablero{
    private ArrayList<FichaButton> tablero;
    private ArrayList<Jugador> jugadores;
    private int fichasEnJuego;
    private Jugador turnoDeJugador;
    private ObjectProperty<Jugador> turnoDeJugadorProperty = new SimpleObjectProperty<>(this, "turnoDeJugador");
    private BorderPane tableroPanel;
    private HBox HboxContainer;
    public Tablero(Jugador j1, Jugador j2, BorderPane tableroPanel, HBox HboxContainer){
        this.tablero = new ArrayList<FichaButton>();
        this.jugadores= new ArrayList<>();
        this.tableroPanel = tableroPanel;
        this.jugadores.add(j1);
        this.jugadores.add(j2);
        //this.FichasEnJuego = 0; //Se sobreentiende esto
        this.turnoDeJugador = generarTurnoAleatorio(j1, j2);
        this.HboxContainer = HboxContainer;
    }
        public ObjectProperty<Jugador> turnoDeJugadorProperty() {
        return turnoDeJugadorProperty;
    }

    public void setTurnoDeJugador(Jugador nuevoTurno) {
        this.turnoDeJugador = nuevoTurno;
        turnoDeJugadorProperty.set(nuevoTurno);

        // Aquí puedes manejar el evento directamente en la clase
        System.out.println("Turno cambiado a: " + nuevoTurno.getNombre());
    }
    public void setFichasEnJuego(int fichasEnJuego) {
        this.fichasEnJuego = fichasEnJuego;
    }
    
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public Jugador getTurnoDeJugador() {
        return turnoDeJugador;
    }
    
    /**
     * Retorna las fichas del tablero
     * @return lista de fichas en el tablero
     */
    public ArrayList<FichaButton> getTablero() {
        return tablero;
    }

    public int getTableroSize() {
        return tablero.size();
    }

    /**
     * Checks if tablero is empty
     * @return boolean value
     */
    public boolean isEmpty() {
        return tablero.isEmpty();
    }

    public boolean isLegalMove(FichaButton fichaButton){
        int leftSide = fichaButton.getFichaReferenciada().getLado1();
        int rightSide = fichaButton.getFichaReferenciada().getLado2();

        if (tablero.isEmpty()){
            fichasEnJuego++;
            return true;
        } else if(leftSide == -1 || rightSide == -1){
            List<Object>lista = FichaButton.comodin();
            Image img = null;
            String lado = (String)lista.get(0);
            int numero = (int)lista.get(1);
            
            if(lado.equals("Izquierda")){
                fichaButton.getFichaReferenciada().setLado1(numero);
                img = new Image("img/"+fichaButton.getFichaReferenciada().getLado1()+"-0.jpg");
            }else{
                fichaButton.getFichaReferenciada().setLado2(numero);
                img = new Image("img/0-"+fichaButton.getFichaReferenciada().getLado2()+".jpg");
            }
                ImageView imageView = new ImageView(img);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
                fichaButton.setStyle("-fx-border-color: transparent; -fx-background-color: lightgray;");
                fichaButton.setGraphic(imageView);
                fichaButton.resizeItself();
                fichaButton.requestLayout();
            return true;
        }else {
            int checkLeft = this.getLeftMostNum();
            int checkRight = this.getRightMostNum();
            if (rightSide == checkLeft){ //Retorna verdadero si no encuentra fichas en el tablero aun(recien empieza el juego) y luego da preferencia a revisar si el lado izquierdo de la ficha a querer jugar coincide con el lado derecho de la ultima ficha en el tablero
                fichasEnJuego++;
                return true;
            }
            if (leftSide == checkRight){
                fichasEnJuego++;
                return true;
            }
        }
        return false;
    }


    public int getLeftMostNum() {
        return this.getTablero().get(0).getFichaReferenciada().getLado1();
    }

    public int getRightMostNum() {
        return this.getTablero().get(getTableroSize()-1).getFichaReferenciada().getLado2();
    }

    public boolean moverFichaButton(Pane jPane, FichaButton fichaButton, Jugador jugador){
        //Quitarla de la mano del jugador
        jugador.getMano().remove(fichaButton.getFichaReferenciada());
        
        int position = 0 ;
        if (tablero.isEmpty()){
            jPane.getChildren().remove(fichaButton);
            if (!HboxContainer.getChildren().contains(fichaButton)) {
                HboxContainer.getChildren().add(position, fichaButton);
                HboxContainer.requestLayout();
                fichaButton.setStyle("-fx-background-color: black");
                tablero.add(position,fichaButton);
                return true;
            }
        }else if(fichaButton.getFichaReferenciada().getLado2()==-1||fichaButton.getFichaReferenciada().getLado1()==-1){
             if (fichaButton.getFichaReferenciada().getLado2()==-1){
                 if (!HboxContainer.getChildren().contains(fichaButton)) {
                    HboxContainer.getChildren().add(position, fichaButton);
                    HboxContainer.requestLayout();
                    fichaButton.setStyle("-fx-background-color: black");
                    tablero.add(position,fichaButton);
                    if (jugador.getMano().isEmpty()) {
                        new Alert(AlertType.INFORMATION, "¡" + jugador.getNombre() + " ha ganado!").showAndWait();
                        System.exit(0); // Cerrar la aplicación
                    }
                    return true;
                }
             }else{
                 position = this.getTableroSize();
                if (!HboxContainer.getChildren().contains(fichaButton)) {
                    HboxContainer.getChildren().add(position, fichaButton);
                    HboxContainer.requestLayout();
                    fichaButton.setStyle("-fx-background-color: black");
                    tablero.add(position,fichaButton);
                    if (jugador.getMano().isEmpty()) {
                        new Alert(AlertType.INFORMATION, "¡" + jugador.getNombre() + " ha ganado!").showAndWait();
                        System.exit(0); // Cerrar la aplicación
                    }
                    return true;
                }
             }
        }else{
            if (fichaButton.getFichaReferenciada().getLado2()==this.getLeftMostNum()) {
                if (!HboxContainer.getChildren().contains(fichaButton)) {
                    HboxContainer.getChildren().add(position, fichaButton);
                    HboxContainer.requestLayout();
                    fichaButton.setStyle("-fx-background-color: black");
                    tablero.add(position,fichaButton);
                    if (jugador.getMano().isEmpty()) {
                        new Alert(AlertType.INFORMATION, "¡" + jugador.getNombre() + " ha ganado!").showAndWait();
                        System.exit(0); // Cerrar la aplicación
                    }
                    return true;
                }
            }
            if (fichaButton.getFichaReferenciada().getLado1()==this.getRightMostNum()){
                position = this.getTableroSize();
                if (!HboxContainer.getChildren().contains(fichaButton)) {
                    HboxContainer.getChildren().add(position, fichaButton);
                    HboxContainer.requestLayout();
                    fichaButton.setStyle("-fx-background-color: black");
                    tablero.add(position,fichaButton);
                    if (jugador.getMano().isEmpty()) {
                        new Alert(AlertType.INFORMATION, "¡" + jugador.getNombre() + " ha ganado!").showAndWait();
                        System.exit(0); // Cerrar la aplicación
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    
     public void addTurnoListener(Jugador jugador, Pane JPane, HBox HboxContainer, Label lblQuienJuega) {
        boolean esSuTurno = turnoDeJugador.equals(jugador);
        int indiceJugador = this.getJugadores().indexOf(jugador);
        int indiceJugadorSiguiente = (indiceJugador + 1) % this.getJugadores().size();
        Jugador jugadorSiguiente = this.getJugadores().get(indiceJugadorSiguiente);
        
        turnoDeJugadorProperty.addListener((obs, oldTurno, newTurno) -> {
            lblQuienJuega.setText("Turno del jugador: " + turnoDeJugador.getNombre());
            if (newTurno.equals(jugador)) {
                if (!jugador.tieneFichasValidas(this)) {
                    new Alert(AlertType.WARNING, "¡" + jugador.getNombre() + " ha perdido!").showAndWait();
                    System.exit(0); // Cerrar la aplicación
                }//Si es el turno de este jugador, animar los botones y que pueda mover sus fichas
                JPane.getChildren().stream()
                    .filter(node -> node instanceof VBox)
                    .map(node -> (VBox) node)
                    .flatMap(vbox -> vbox.getChildren().stream())
                    .filter(posibleButtons -> posibleButtons instanceof FichaButton)
                    .map(posibleButtons -> (FichaButton) posibleButtons)
                    .filter(fichaButton -> fichaButton.getFichaReferenciada() != null)
                    .forEach(fichaButton -> {
                        Animaciones.animar_boton(fichaButton);
                        fichaButton.setOnMouseClicked(event -> {
                            jugador.jugarFicha(this, JPane, fichaButton,jugadorSiguiente);
                            Animaciones.desanimar_boton(fichaButton);
                        });
                    });
            } else {
                // Si no es el turno de este jugador, desanimar los botones y que pueda mover sus fichas
                JPane.getChildren().stream()
                    .filter(node -> node instanceof VBox)
                    .map(node -> (VBox) node)
                    .flatMap(vbox -> vbox.getChildren().stream())
                    .filter(posibleButtons -> posibleButtons instanceof FichaButton)
                    .map(posibleButtons -> (FichaButton) posibleButtons)
                    .filter(fichaButton -> fichaButton.getFichaReferenciada() != null)
                    .forEach((fichaButton) -> {
                        Animaciones.desanimar_boton(fichaButton);
                        fichaButton.setOnMouseClicked(null);
                    });
                }
            });
        
        if (esSuTurno) {
            this.setTurnoDeJugador(jugador);
        }
         
            
     }
     
    public void jugarFichaMaquina(Tablero tablero, Pane JPane, FichaButton fichaButton, Jugador jugadorSiguiente){
        int leftSide = fichaButton.getFichaReferenciada().getLado1();
        int rightSide = fichaButton.getFichaReferenciada().getLado2();
        Random random = new Random();
        int posibilidadDeLado;
        System.out.println(fichaButton.getHeight());
        System.out.println(fichaButton.getWidth());
        final double buttonWidth = 90;
        final double buttonHeight =45.0;
        if (fichaButton.getWidth()==0 || fichaButton.getHeight()==0) {
            ImageView imageView = (ImageView) fichaButton.getGraphic();
            imageView.setFitWidth(buttonWidth);
            imageView.setFitHeight(buttonHeight);
        }
        fichaButton.setPrefWidth(90);
        fichaButton.setPrefHeight(45);
        //Quitarla de la mano de la maquina
        jugadores.get(1).getMano().remove(fichaButton.getFichaReferenciada());
        if(leftSide == -1 || rightSide == -1){
            Image img = null;
            posibilidadDeLado = random.nextInt(2);
            int numero = Utilitaria.numeroEntreCeroYSeis();
            if(posibilidadDeLado==0){
                fichaButton.getFichaReferenciada().setLado1(numero);
                img = new Image("img/"+numero+"-0.jpg");
            }else{
                fichaButton.getFichaReferenciada().setLado2(numero);
                img = new Image("img/0-"+numero+".jpg");
            }
            ImageView imageView = new ImageView(img);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            fichaButton.setStyle("-fx-border-color: transparent; -fx-background-color: lightgray;");
            fichaButton.setGraphic(imageView);
            fichaButton.resizeItself();
            fichaButton.requestLayout();
            this.moverFichaButton(JPane, fichaButton, jugadores.get(1));//Mover desde el Pane de la maquina hasta el Tablero
            tablero.setTurnoDeJugador(jugadorSiguiente);
        }else{
            this.moverFichaButton(JPane, fichaButton, jugadores.get(1));//Mover desde el Pane de la maquina hasta el Tablero
            tablero.setTurnoDeJugador(jugadorSiguiente);
        }
    }
    
     public void addTurnoMaquinaListener(Jugador maquina, Pane JPane, HBox HboxContainer, Label lblQuienJuega) {
        boolean esSuTurno = turnoDeJugador.equals(maquina);
        int indiceMaquina = this.getJugadores().indexOf(maquina);
        int indiceJugadorSiguiente = (indiceMaquina + 1) % this.getJugadores().size();
        Jugador jugadorSiguiente = this.getJugadores().get(indiceJugadorSiguiente);
        VBox vbox = (VBox) JPane.getChildren().get(1);
        turnoDeJugadorProperty.addListener((var obs, var oldTurno, var newTurno) -> {
            if (newTurno.equals(maquina)){
                if(!maquina.tieneFichasValidas(this)){
                    FichaButton fichaComodinButton = (FichaButton) vbox.getChildren().get(vbox.getChildren().size()-1);
                    boolean tieneFichaComodin = fichaComodinButton.getFichaReferenciada() instanceof FichaComodin;
                    if (tieneFichaComodin) {
                        this.jugarFichaMaquina(this, JPane, fichaComodinButton,jugadorSiguiente);//juega la ficha comodin
                    }else{
                        new Alert(AlertType.WARNING, "¡" + maquina.getNombre() + " ha perdido!").showAndWait();
                        System.exit(0); // Cerrar la aplicación
                    }
                }else{
                    FichaButton fichaButton = this.getFichaJugable(JPane);
                    if (fichaButton == null){
                        new Alert(AlertType.WARNING, "¡" + maquina.getNombre() + " ha perdido!").showAndWait();
                        System.exit(0); // Cerrar la aplicación
                    }else{
                        this.jugarFichaMaquina(this, JPane, this.getFichaJugable(JPane), jugadorSiguiente);
                    }
                }
            }
        });
        if (esSuTurno) {
            this.setTurnoDeJugador(maquina);
        }
    }
     
    public FichaButton getFichaJugable(Pane JPane) {
    int checkLeft;
    int checkRight;
    ArrayList<FichaButton> posiblesFichasAJugar = new ArrayList<>();
    Random random = new Random();
    int indiceRandom;

    if (this.tablero.isEmpty()) {
        VBox vbox = (VBox) JPane.getChildren().get(1);
        return (FichaButton) vbox.getChildren().get(random.nextInt(vbox.getChildren().size()));
    } else {
        checkLeft = this.getLeftMostNum();
        checkRight = this.getRightMostNum();

        JPane.getChildren().stream()
                .filter(node -> node instanceof VBox)
                .map(node -> (VBox) node)
                .flatMap(vbox -> vbox.getChildren().stream())
                .filter(posibleButtons -> posibleButtons instanceof FichaButton)
                .map(posibleButtons -> (FichaButton) posibleButtons)
                .filter(fichaButton -> fichaButton.getFichaReferenciada() != null)
                .forEach((fichaButton) -> {
                    if (fichaButton.getFichaReferenciada().getLado2() == checkLeft || fichaButton.getFichaReferenciada().getLado1() == checkRight) {
                        posiblesFichasAJugar.add(fichaButton);
                    }
                });

        // Si no se encuentran fichas jugables, verifica si hay una ficha comodín
        if (posiblesFichasAJugar.isEmpty()) {
            posiblesFichasAJugar.addAll(obtenerComodines(JPane));
        }

        if (posiblesFichasAJugar.isEmpty()) {
            return null;
        } else {
            indiceRandom = random.nextInt(posiblesFichasAJugar.size());
        }

        return posiblesFichasAJugar.get(indiceRandom);
    }
}
     private List<FichaButton> obtenerComodines(Pane JPane) {
    VBox vbox = (VBox) JPane.getChildren().get(1);
    List<FichaButton> comodines = new ArrayList<>();
    
    for (Node node : vbox.getChildren()) {
        if (node instanceof FichaButton) {
            FichaButton fichaButton = (FichaButton) node;
            if (fichaButton.getFichaReferenciada() instanceof FichaComodin) {
                comodines.add(fichaButton);
            }
        }
    }

    return comodines;
}
    public Jugador generarTurnoAleatorio(Jugador j1,Jugador j2){
        Random random = new Random();
        int r = random.nextInt(2);
        if (r==0){//Esto le da chance a que cuando empieza una partida no necesariamente el Jugador 1 tenga el turno siempre
            return j1;
        }else{
            return j2;
        }
    }

    public void tableroDisplaysOn(Parent parent, Node node){
        if (node instanceof Pane){
            HboxContainer.setAlignment(Pos.CENTER);
            BorderPane bp = (BorderPane) parent;
            bp.setCenter(HboxContainer);
        }
    }
    public void checkJugadorSinFichas(Jugador jugador) {
        boolean tieneFichasUtilizables = jugador.getMano().stream()
                .anyMatch(ficha -> isLegalMove(new FichaButton(ficha, null)));

        if (!tieneFichasUtilizables) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Juego Terminado");
            alert.setHeaderText(null);
            alert.setContentText("¡El jugador " + jugador.getNombre() + " no tiene fichas utilizables! Fin del juego.");
            alert.showAndWait();
            System.exit(0); // Otra opción podría ser cerrar la aplicación de manera más controlada
        }
    }
    
    
}
