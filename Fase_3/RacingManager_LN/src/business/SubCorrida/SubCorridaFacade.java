package business.SubCorrida;
import java.util.List;
import business.I_SubCorrida;
import business.SubCarro.Carro;
import business.SubCircuito.Piloto;
import business.SubCorrida.Corrida;
import business.I_SubCorrida;

public class SubCorridaFacade implements I_SubCorrida {
    private List<Carro> carrDisp; //Lista com os carros disponiveis
    private List<Piloto> pilotDisp; //Lista com os pilotos disponiveis

      /**
       * Método que verifica se existe um carro na lista dos disponiveis
       * @return true caso exista e false caso contrário
       */
      public boolean existeCarr(int id_carro){
        boolean resposta = false;
        for(Carro carr : this.carrDisp){
            if(id_carro==carr.get_ID_Carro()){
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
       * Método que verifica se existe um piloto na lista dos disponiveis
       * @return true caso exista e false caso contrário
       */
      public boolean existePilot(String nomec){
        boolean res = false;
        for(Piloto pilot : this.pilotDisp){
            if(nomec==pilot.getNome()){
                res = true;
            }
            else{
                res = false;
            }
        }
        return res;
  }
  /**
   * Método que adiciona um piloto à lista de pilotos disponiveis
   */
  public void adicionaPilot(String nomec){
      for(Piloto pilot : this.pilotDisp){
          if(existePilot(nomec)==false) this.pilotDisp.add(pilot);
      }
  }
}
