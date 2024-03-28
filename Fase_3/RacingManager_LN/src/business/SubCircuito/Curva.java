package business.SubCircuito;

public class Curva extends Tipo_Estrada{

    // esta classe é subclasse de Tipo_Estrada
    // não necessita de variaveis de instancia proprias ou de get's e set's


    //------------------------------------- CONSTRUTORES -------------------------------------//


    public Curva() {
        super();
    }

    public Curva(String id, String gdu) {
        super(id, gdu);
    }

    public Curva(Curva cu) {
        super(cu);
    }


    //------------------------------------- METODOS USUAIS -------------------------------------//


    public Curva clone() {
        return new Curva(this);
    }

    public String imprime_tipo_estrada(){
        return "Curva";
    }

    public boolean equals(Object o) {
        if(this==o) {
            return true;
        }

        if(o==null || this.getClass()!=o.getClass()) {
            return false;
        }

        Curva cu = (Curva) o;
        return ( super.equals(cu) );
    }

}
