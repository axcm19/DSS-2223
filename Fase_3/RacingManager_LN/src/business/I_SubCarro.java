package business;

import business.SubCarro.Carro;
import business.SubCarro.PC2;
import exceptions.CarroJaExisteException;
import exceptions.CarroNaoExisteException;

import java.util.List;

public interface I_SubCarro {
    public String getCat(int Id_Carro);
    public Carro getCar(int idC);
    public void addCar(Carro car) throws CarroJaExisteException;
    public void removeCar(Carro car) throws CarroNaoExisteException;
    public String getCarros();
    public boolean carroExiste(int id_carro);
    public void criaCarro(Carro car);
}
