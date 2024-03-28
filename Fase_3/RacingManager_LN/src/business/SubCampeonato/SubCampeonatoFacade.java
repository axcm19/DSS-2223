package business.SubCampeonato;
import java.util.List;
import business.SubCircuito.Circuito;
import business.I_SubCampeonato;
import business.SubCampeonato.Campeonato;
import business.SubCircuito.Piloto;
import data.CampeonatoDAO;
import data.CircuitoDAO;
import data.PilotoDAO;


public class SubCampeonatoFacade implements I_SubCampeonato {
    private List<Campeonato> campDisp; //lista com os campeonatos disponíveis para jogar
    private List<Circuito> circDisp; //Lista com os circuitos disponíveis para jogar
    CampeonatoDAO campeonatoDAO;

    public SubCampeonatoFacade(){
        this.campeonatoDAO = CampeonatoDAO.getInstance();
    }

      /**
       * Método que verifica se existe um campeonato na lista dos disponiveis
       * @return true caso exista e false caso contrário
       */
    public boolean existeCamp(String nomec){
        boolean resposta = false;
        for(Campeonato camp : this.campDisp){
            if(nomec==camp.getNome()){
                resposta = true;
                break;
            }
            else{
                resposta = false;
            }
        }
        return resposta;
    }
    /**
     * Método que adiciona um campeonato à lista de campeonatos disponiveis
     */
    public void adicionaCamp(Campeonato campeonato){
        if(this.campeonatoDAO.containsKey(campeonato.getNome())) {
            System.out.println("Esse campeonato já existe");
        }
        else {
            this.campeonatoDAO.put(campeonato.getNome(), campeonato);
        }
    }


      /**
       * Método que verifica se existe um circuito na lista dos disponiveis
       * @return true caso exista e false caso contrário
       */
      public boolean existeCirc(String nomec){
          boolean res = false;
          for(Circuito circ : this.circDisp){
              if(nomec==circ.getNome()){
                  res = true;
              }
              else{
                  res = false;
              }
          }
          return res;
    }
    /**
     * Método que adiciona um circuito à lista de circuitos disponiveis
     */
    public void adicionaCirc(String nomec){
        for(Circuito circ : this.circDisp){
            if(existeCirc(nomec)==false) this.circDisp.add(circ);
        }
    }
}
