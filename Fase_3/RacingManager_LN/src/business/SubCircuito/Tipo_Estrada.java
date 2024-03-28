package business.SubCircuito;

public abstract class Tipo_Estrada {

    //------------------------------------- VARIAVEIS DE INSTANCIA -------------------------------------//


    private String id_tipo_estrada; // usar, por exemplo, curva_1_<nome_circuito>
                                    //                    curva_2_<nome_circuito>
                                    //                    chicane_1_<nome_circuito>

    private String gdu; // grau de dificuldade de ulptrapassagem (Impossivel, Possivel, Dificil)



    //------------------------------------- CONSTRUTORES -------------------------------------//


    public Tipo_Estrada() {
        this.id_tipo_estrada = "";
        this.gdu = "";
    }

    public Tipo_Estrada(String id, String gdu) {
        this.id_tipo_estrada = id;
        this.gdu = gdu;
    }

    public Tipo_Estrada(Tipo_Estrada te) {
        this.id_tipo_estrada = te.getIdTipoEstrada();
        this.gdu = te.getGDU();
    }


    //------------------------------------- GETS / SETS -------------------------------------//


    public String getIdTipoEstrada() {
        return this.id_tipo_estrada;
    }

    public String getGDU() {
        return this.gdu;
    }

    public void setIdTipoEstrada(String id ){
        this.id_tipo_estrada = id;
    }

    public void setGDU(String gdu) {
        this.gdu = gdu;
    }

    //------------------------------------- METODOS USUAIS -------------------------------------//


    public abstract Tipo_Estrada clone();

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nID_Tipo_Estrada: ");sb.append(this.id_tipo_estrada);
        sb.append("\nGDU: ");sb.append(this.gdu);
        return sb.toString();
    }

    public abstract  String imprime_tipo_estrada();

    public boolean equals(Object o){
        if(this==o) {
            return true;
        }

        if(o==null || this.getClass()!=o.getClass()) {
            return false;
        }

        Tipo_Estrada te = (Tipo_Estrada) o;
        return ( this.id_tipo_estrada ==  te.getIdTipoEstrada() && this.gdu == te.getGDU());
    }
}


