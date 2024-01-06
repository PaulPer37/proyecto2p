package ec.edu.espol.proyecto2p;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
public class Jugador{
    private final String nombre;
    private List<Ficha>mano = new ArrayList<>();
    public Jugador(String n, List<Ficha> mano){
        this.nombre = n;
        this.mano = mano;
    }

    public String getNombre(){
        return nombre;
    }

    public Ficha getFicha(int i){
        if(i>=0 && i<this.mano.size()){
            return this.mano.get(i);
        }else{
            return null;
        }
    }
    
    public void cargarFichasJugador(Pane panel){
        Image[] images = new Image[6];
        try {
            for (int i = 0 ; i<this.mano.size();i++){
                Ficha ficha = this.mano.get(i);
                if (ficha.getLado1()==-1&&ficha.getLado2()==-1){
                    ficha.setFile(0+"-"+0+".jpg");
                }
                if (ficha.getLado1()==-1&&ficha.getLado1()!=-1){
                    ficha.setFile(0+"-"+ficha.getLado2()+".jpg");
                }
                if (ficha.getLado1()!=-1&&ficha.getLado1()==-1){
                    ficha.setFile(ficha.getLado1()+"-"+0+".jpg");
                }
                Image img = new Image("img/"+ficha.getFile());
                images[i] = img;
            }
            FichaButton.construirFichasEn(panel, images, (ArrayList<Ficha>) this.mano);
            
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"¡Ha ocurrido un error!").show();
        }
        
    }
    
    public void removerFicha(Ficha f){
        this.mano.remove(f);
    }
    
    public List<Ficha> getMano(){
        return this.mano;
    }
    
    public void jugarFicha(Tablero tablero,Pane JPane,FichaButton fichaButton){
        if (tablero.isLegalMove(fichaButton)){
            tablero.moverFichaButton(JPane, fichaButton, this);
            tablero.setTurnoDeJugador(this);
        }else{
            new Alert(Alert.AlertType.WARNING,"¡Ficha no válida!").show();
            tablero.setTurnoDeJugador(this);
        }
    }
}
