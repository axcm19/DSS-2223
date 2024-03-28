package business.SubCircuito;

import java.util.ArrayList;
import java.util.List;
import business.I_SubCircuito;
import business.SubCarro.Carro;
import business.SubCorrida.TimeConverter;
import business.SubUtilizador.Administrador;
import data.CircuitoDAO;
import data.PilotoDAO;
import exceptions.CircuitoJaExisteException;
import exceptions.CircuitoNaoExisteException;
import exceptions.JogadorJaExisteException;

public class SubCircuitoFacade implements I_SubCircuito {
        CircuitoDAO circuitoDAO;
        PilotoDAO pilotoDAO;
        private List<Circuito> circuitoDisp; // lista com os circuito disponíveis
        
      public SubCircuitoFacade(){
          this.circuitoDAO = CircuitoDAO.getInstance();
          this.pilotoDAO = PilotoDAO.getInstance();
      } 

      public Circuito getCircuito(String nomeCir){
        return this.circuitoDAO.get(nomeCir).clone();
      }

      public void addCircuito(Circuito circuito) throws CircuitoJaExisteException{
        if(!this.circuitoDAO.containsKey(circuito.getNome())){
          this.circuitoDAO.put(circuito.getNome(), circuito);
        }
        else throw new CircuitoJaExisteException("O circuito que pretende adicionar já existe");
      }

      public void removeCircuito(Circuito circuito) throws CircuitoNaoExisteException {
          if (this.circuitoDisp.contains(circuito)) {
              this.circuitoDisp.remove(circuito);
          } else throw new CircuitoNaoExisteException("O circuito que pretende remover não existe");

      }

      public List<Piloto> getPilotos(){
          return new ArrayList<>(this.pilotoDAO.values());
      }

      public boolean pilotoExiste(String nome){
        return this.pilotoDAO.containsKey(nome);
    }

    public void adicionaPiloto(String nome,double cts, double sva) {
        if(this.pilotoDAO.containsKey(nome)) {
            System.out.println("Esse piloto já existe");
        }
        else {
            this.pilotoDAO.put(nome,  new Piloto(nome, sva, cts));
        }
    }

    public Piloto get_1_Piloto(String nome){
        return this.pilotoDAO.get(nome);
    }

    public String get_todos_Circuito(){
        String circuitos = null;
        StringBuilder sb = new StringBuilder();
        for(Circuito c : this.circuitoDAO.values()){
            sb.append("[ ");
            sb.append("Nome: "); sb.append(c.getNome()); sb.append(" , ");
            sb.append("Distância: "); sb.append(c.getDistancia()); sb.append(" , ");
            sb.append("Número de voltas: "); sb.append(c.getVoltas()); sb.append(" , ");
            sb.append("Desvio Tempo (aka: Penalização): "); sb.append(c.getPenalizacao()); sb.append(" , ");
            sb.append("Tempo Recorde: "); sb.append(c.getRecord().getTempo()); sb.append(" , ");
            sb.append(" ]\n");
        }
        circuitos = String.valueOf(sb);
        return circuitos;
      }

    public boolean circuitoExiste(String nome){
        return this.circuitoDAO.containsKey(nome);
    }
}



