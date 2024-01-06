package ec.edu.espol.proyecto2p;


public class Ficha {
    private String propietarioDeLaFicha;
    protected int lado1;
    protected int lado2;
    private String file;

    public String getPropietarioDeLaFicha() {
        return propietarioDeLaFicha;
    }
    
    public void setFile(String file) {
        this.file = file;
    }

    public Ficha(int lado1, int lado2) {
        this.lado1 = lado1;
        this.lado2 = lado2;
        this.file = lado1+"-"+lado2+".jpg";
    }

    public void setLado1(int lado1) {
        this.lado1 = lado1;
    }

    public void setLado2(int lado2) {
        this.lado2 = lado2;
    }

    public int getLado1() {
        return lado1;
    }

    public int getLado2() {
        return lado2;
    }

    @Override
    public String toString() {
        return lado1 + ":" + lado2;
    }

    public String getFile() {
        return file;
    }
    
}