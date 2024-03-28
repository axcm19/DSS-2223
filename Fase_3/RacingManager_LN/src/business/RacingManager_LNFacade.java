package business;
import business.SubCampeonato.SubCampeonatoFacade;
import business.SubCarro.SubCarroFacade;
import business.SubCircuito.SubCircuitoFacade;
import business.SubCorrida.SubCorridaFacade;
import business.SubUtilizador.SubUtilizadorFacade;

public class RacingManager_LNFacade{
    public I_SubCorrida subCorridaFacade;
    public I_SubCircuito subCircuitoFacade;
    public I_SubUtilizador subUtilizadorFacade;
    public I_SubCarro subCarroFacade;
    public I_SubCampeonato subCampeonatoFacade;

    public RacingManager_LNFacade(){
        subCorridaFacade = new SubCorridaFacade();
        subCircuitoFacade = new SubCircuitoFacade();
        subUtilizadorFacade = new SubUtilizadorFacade();
        subCarroFacade = new SubCarroFacade();
        subCampeonatoFacade = new SubCampeonatoFacade();
    }
}