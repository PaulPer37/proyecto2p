package ec.edu.espol.proyecto2p;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Utilitaria{
    public static List<Ficha> crearManoJugador(){
        List<Ficha> mano = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int lado1 = random.nextInt(7);
            while (lado1==0) {
                lado1 = random.nextInt(7);
            }
            int lado2 = random.nextInt(7);
            while (lado2==0) {
                lado2 = random.nextInt(7);
            }
            Ficha ficha = new Ficha(lado1, lado2){};
            mano.add(ficha);
        }
        FichaComodin fichaComodin = new FichaComodin();
        mano.add(fichaComodin);
        
        return mano;
    }
}