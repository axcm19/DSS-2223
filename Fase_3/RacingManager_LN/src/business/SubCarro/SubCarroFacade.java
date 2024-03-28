package business.SubCarro;
import java.util.ArrayList;
import java.util.List;
import business.I_SubCarro;
import business.SubCircuito.Piloto;
import data.CarroDAO;
import exceptions.CarroJaExisteException;
import exceptions.CarroNaoExisteException;

public class SubCarroFacade implements I_SubCarro  {
    private CarroDAO carroDAO;
    private List<Carro> carroDisp; // lista com os carros disponíveis
    
    public SubCarroFacade(){
      this.carroDAO = CarroDAO.getInstance();
    }

    /**
    *
    * método para obter a categoria do carro
    * @return categoria
    */
    public String getCat(int Id_Carro){
        String res = null;
        for(Carro c : this.carroDisp){
          if(c.get_ID_Carro()==Id_Carro) res = c.getClass().toString();
        }
        return res;
    }

    public Carro getCar(int idC){
      return this.carroDAO.get(idC);
    }
    
    public void addCar(Carro car) throws CarroJaExisteException{
        if(!this.carroDisp.contains(car)){
          this.carroDisp.add(car);
        }
        else throw new CarroJaExisteException("O carro que pretende adicionar já existe");
    }

    public void removeCar(Carro car) throws CarroNaoExisteException{
      if(this.carroDisp.contains(car)){
        this.carroDisp.remove(car);
      }
      else throw new CarroNaoExisteException("O carro que pretende remover não existe");
    }

    public String getCarros(){
        String carros = null;
        StringBuilder sb = new StringBuilder();
        for(Carro c : this.carroDAO.values()){
            sb.append("[ ");
            sb.append("ID: "); sb.append(c.get_ID_Carro()); sb.append(" , ");
            sb.append("Marca: "); sb.append(c.getMarca()); sb.append(" , ");
            sb.append("Modelo: "); sb.append(c.getModelo()); sb.append(" , ");
            sb.append("Cilindrada: "); sb.append(c.getCilindrada()); sb.append(" , ");
            sb.append("Potencia(Comb): "); sb.append(c.getPotencia()); sb.append(" , ");
            sb.append("Fiabilidade: "); sb.append(c.getFiabilidade()); sb.append(" , ");
            sb.append("PAC: "); sb.append(c.getPAC());
            sb.append(" ]\n");
        }
        carros = String.valueOf(sb);
        return carros;
    }

    public boolean carroExiste(int id_carro){
        return this.carroDAO.containsKey(id_carro);
    }

    public void criaCarro(Carro car){
        if(this.carroDAO.containsKey(car.get_ID_Carro())) {
            System.out.println("Esse carro já existe");
        }
        else {
            this.carroDAO.put(car.get_ID_Carro(),  car);
        }
    }

}
