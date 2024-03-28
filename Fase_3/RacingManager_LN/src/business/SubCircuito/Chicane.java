package business.SubCircuito;

public class Chicane extends Tipo_Estrada{
    // esta classe é subclasse de Tipo_Estrada
    // não necessita de variaveis de instancia proprias ou de get's e set's


    //------------------------------------- CONSTRUTORES -------------------------------------//


    public Chicane() {
        super();
    }

    public Chicane(String id) {
        super(id, "Dificil");   // ---> nao passamos parametro 'String gdu' pois a chicane tem sempre 'GDU = Dificil'
    }

    public Chicane(Chicane ch) {
        super(ch);
    }


    //------------------------------------- METODOS USUAIS -------------------------------------//


    public Chicane clone() {
        return new Chicane(this);
    }

    public String imprime_tipo_estrada(){
        return "Chicane";
    }

    public boolean equals(Object o) {
        if(this==o) {
            return true;
        }

        if(o==null || this.getClass()!=o.getClass()) {
            return false;
        }

        Chicane ch = (Chicane) o;
        return ( super.equals(ch) );
    }
}
