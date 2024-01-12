package ec.edu.espol.proyecto2p;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utilitaria {
    private static ArrayList<Ficha> fichasCreadas = new ArrayList<>();

    public static List<Ficha> crearManoJugador() {
        List<Ficha> mano = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int lado1, lado2;

            do {
                lado1 = random.nextInt(6) + 1;
                lado2 = random.nextInt(6) + 1;
            } while (yaExisteFichaEnMano(lado1, lado2));

            Ficha ficha = new Ficha(lado1, lado2);
            fichasCreadas.add(ficha);
            mano.add(ficha);
        }

        FichaComodin fichaComodin = new FichaComodin();
        fichasCreadas.add(fichaComodin);
        mano.add(fichaComodin);

        return mano;
    }

    private static boolean yaExisteFichaEnMano(int lado1, int lado2) {
        for (Ficha ficha : fichasCreadas) {
            if (ficha.getLado1() == lado1 && ficha.getLado2() == lado2) {
                return true; // La ficha ya existe en las manos
            }
        }
        return false; // La ficha no existe en las manos
    }
}
