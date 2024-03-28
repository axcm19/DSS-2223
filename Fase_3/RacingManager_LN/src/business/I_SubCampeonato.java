package business;

import business.SubCampeonato.Campeonato;

public interface I_SubCampeonato {
    public boolean existeCamp(String nomec);
    public void adicionaCamp(Campeonato campeonato);
    public boolean existeCirc(String nomec);
    public void adicionaCirc(String nomec);
}
