package ec.edu.espol.proyecto2p;
public class FichaComodin extends Ficha{
    public FichaComodin(){
        super(-1,-1);
    }
    public void setLado1(int v){
        this.lado1=v;
    }
    
    public void setLado2(int v){
        this.lado2=v;
    }

    @Override
    public String toString(){
        return "*"+super.toString()+"*";
    }
}