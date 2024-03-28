package business.SubCircuito;

public class Recta extends Tipo_Estrada{

    // esta classe é subclasse de Tipo_Estrada
    // não necessita de variaveis de instancia proprias ou de get's e set's


    //------------------------------------- CONSTRUTORES -------------------------------------//


    public Recta() {
        super();
    }

    public Recta(String id, String gdu) {
        super(id, gdu);
    }

    public Recta(Recta re) {
        super(re);
    }


    //------------------------------------- METODOS USUAIS -------------------------------------//


    public Recta clone() {
        return new Recta(this);
    }

    public String imprime_tipo_estrada(){
        return "Recta";
    }

    public boolean equals(Object o) {
        if(this==o) {
            return true;
        }

        if(o==null || this.getClass()!=o.getClass()) {
            return false;
        }

        Recta re = (Recta) o;
        return ( super.equals(re) );
    }

}
