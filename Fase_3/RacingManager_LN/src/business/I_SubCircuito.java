package business;

import business.SubCircuito.Circuito;
import business.SubCircuito.Piloto;
import data.CircuitoDAO;
import exceptions.CircuitoJaExisteException;
import exceptions.CircuitoNaoExisteException;

import java.util.ArrayList;
import java.util.List;

public interface I_SubCircuito{

    public Circuito getCircuito(String nomeCir);
    public void addCircuito(Circuito circuito) throws CircuitoJaExisteException ;
    public void removeCircuito(Circuito circuito) throws CircuitoNaoExisteException;
    public List<Piloto> getPilotos();
    public boolean pilotoExiste(String nome);
    public void adicionaPiloto(String nome,double cts, double sva) ;
    public Piloto get_1_Piloto(String nome);
    public String get_todos_Circuito();
    public boolean circuitoExiste(String nome);

}