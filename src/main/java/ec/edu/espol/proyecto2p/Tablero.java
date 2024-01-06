package ec.edu.espol.proyecto2p;

import java.util.ArrayList;
import java.util.Random;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
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
        } else {
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

    public void moverFichaButton(Pane jPane, FichaButton fichaButton, Jugador jugador){
        //Quitarla de la mano del jugador
        jugador.getMano().remove(fichaButton.getFichaReferenciada());
        
        //Quitarla del panel del jugador y rastrear en que posicion del tablero se debe poner
        int position;
        if (tablero.isEmpty()){
            HboxContainer.getChildren().add(fichaButton);
        }
        if (fichaButton.getFichaReferenciada().getLado2() == this.getLeftMostNum()) {
            position = 0;
        }else{
            position = this.getTableroSize()-1;
        }
        jPane.getChildren().remove(fichaButton);
        if (!HboxContainer.getChildren().contains(fichaButton)) {
            HboxContainer.getChildren().add(position,fichaButton);
            HboxContainer.requestLayout();
            fichaButton.setStyle("-fx-background-color: black");
            
        }
    }
    
     public void addTurnoListener(Jugador jugador, Pane JPane, HBox HboxContainer, Label lblQuienJuega) {

        boolean esSuTurno = turnoDeJugador.equals(jugador);

        int indiceJugador = this.getJugadores().indexOf(jugador);
        int indiceJugadorSiguiente = (indiceJugador + 1) % this.getJugadores().size();
        
        Jugador jugadorSiguiente = this.getJugadores().get(indiceJugadorSiguiente);
        turnoDeJugadorProperty.addListener((obs, oldTurno, newTurno) -> {
        if (newTurno.equals(jugador)) {
            //Si es el turno de este jugador, animar los botones y que pueda mover sus fichas
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
                        jugador.jugarFicha(this, JPane, fichaButton);
                        this.setTurnoDeJugador(jugadorSiguiente);
                        updateTurnoLabel(lblQuienJuega, jugadorSiguiente);
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
    
    public void updateTurnoLabel(Label lblQuienJuega, Jugador nuevoTurno) { //Esta wea no funciona y no se por q
        lblQuienJuega.setText("Turno del jugador: " + nuevoTurno.getNombre());
        lblQuienJuega.setFont(new Font("Arial", 24));
        lblQuienJuega.setTextAlignment(TextAlignment.CENTER);
        lblQuienJuega.setAlignment(Pos.CENTER);
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
            Label lblQuienJuega = new Label("Turno del jugador: "+this.turnoDeJugador.getNombre());
            lblQuienJuega.setFont(new Font("Arial", 24));
            lblQuienJuega.setTextAlignment(TextAlignment.CENTER);
            lblQuienJuega.setAlignment(Pos.CENTER);
            bp.setBottom(lblQuienJuega);
        }
    }
}