package ui;

import business.RacingManager_LNFacade;
import business.SubCampeonato.Campeonato;
import business.SubCarro.*;
import business.SubCircuito.*;
import business.SubCircuito.Record;
import business.SubCorrida.Corrida;
import business.SubUtilizador.SubUtilizadorFacade;
import exceptions.CarroJaExisteException;
import exceptions.CircuitoJaExisteException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TextUI {

    RacingManager_LNFacade model = new RacingManager_LNFacade();

    //------------------------------------- VARIAVEIS DE INSTANCIA -------------------------------------//


    // Menus da aplicação
    //private Menu menu_login;
    private Menu menu_jogador;
    private Menu menu_administrador;
    private Carro meu_carro;
    private Piloto meu_piloto;

    //boolean is_jogador;
    //boolean is_administrador;

    // Scanner para leitura
    private Scanner input;


    //------------------------------------- CONSTRUTORES -------------------------------------//


    public TextUI() {

        // Criar o menu de login
        /*
        this.menu_login = new Menu(new String[]{
                "Iniciar sessão como jogador",
                "Iniciar sessão como administrador",
                "Criar conta de jogador",
                "Criar conta de administrador"

        });
        this.menu_login.setHandler(1, this::iniciarSessaoJogador);
        this.menu_login.setHandler(2, this::iniciarSessaoAdministrador);
        this.menu_login.setHandler(3, this::criarJogador);
        this.menu_login.setHandler(4, this::criarAdministrador);
        */

        // Criar o menu de administrador
        this.menu_administrador = new Menu(new String[]{
                "Adicionar circuito",
                "Adicionar carro",
                "Adicionar piloto",
                "Adicionar campeonato"
        });
        this.menu_administrador.setHandler(1, this::adicionar_circuito);
        this.menu_administrador.setHandler(2, this::adicionar_carro);
        this.menu_administrador.setHandler(3, this::adicionar_piloto);
        this.menu_administrador.setHandler(4, this::adicionar_campeonato);

        // Criar o menu de jogador
        // configuração da corrida feita durante a simulação
        this.menu_jogador = new Menu(new String[]{
                "Configurar campeonato",
                "Simular campeonato",
                "Simular corrida (para fins de teste)"
        });
        this.menu_jogador.setHandler(1, this::configurar_campeonato);
        this.menu_jogador.setHandler(2, this::simular_campeonato);
        this.menu_jogador.setHandler(3, this::simular_corrida);


        //this.model = new RacingManager_LNFacade();
        input = new Scanner(System.in);
    }

    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */
    public void run_admin() throws CircuitoJaExisteException {
        //this.menu_login.run();

        this.menu_administrador.run();
        System.out.println("Até breve!...");
    }

    public void run_joga() throws CircuitoJaExisteException {
        this.menu_jogador.run();
        System.out.println("Até breve!...");
    }


    //------------------------------------- METODOS ADMINISTRADOR -------------------------------------//

    private void adicionar_circuito() throws CircuitoJaExisteException {
        Scanner input = new Scanner(System.in);
        Map<String, Double> temp_med = new HashMap<>();
        List<Tipo_Estrada> road_list = new ArrayList<>();
        Record record = new Record();
        int i;

        System.out.println("---> Insira o nome do novo circuito");
        String nome = input.next();

        System.out.println("---> Insira a distancia do novo circuito (em metros)");
        int distancia = Integer.parseInt(input.next());

        System.out.println("---> Insira a penalização do novo circuito");
        double penalizacao = Double.parseDouble(input.next());

        System.out.println("---> Quantas secções?");
        int num_secoes = Integer.parseInt((input.next()));

        System.out.println("---> Insira ordenadamente as secções do novo circuito");
        System.out.println("---> Tipo: (Recta, Curva, Chicane)");
        System.out.println("---> ID: (por exemplo: curva_1_<nome_circuito> )");
        System.out.println("---> GDU: (Impossivel, Possivel, Dificil)");
        for(i = 0; i < num_secoes; i++){
            String secao = input.next();
            String id = input.next();
            String gdu = input.next();
            if(secao.equals("Curva")){ road_list.add(new Curva(id, gdu));}
            else if(secao.equals("Chicane")){ road_list.add(new Chicane(id));}
            else if(secao.equals("Recta")){ road_list.add(new Recta(id, gdu));}
            System.out.println("    ......    ");
        }

        System.out.println("---> Insira o numero de voltas do novo circuito");
        int voltas = Integer.parseInt(input.next());

        Circuito circuito = new Circuito(nome, distancia, voltas, temp_med, penalizacao,road_list,record);
        model.subCircuitoFacade.addCircuito(circuito);

    }

    private void adicionar_carro() {
        Scanner input = new Scanner(System.in);

        System.out.println("---> Insira o ID do novo carro");
        int id = Integer.parseInt(input.next());


        if(model.subCarroFacade.carroExiste(id)){
            System.out.println("Erro, esse carro já existe");
        }

        System.out.println("---> Insira a categoria do novo carro");
        String categoria = input.next();

        System.out.println("---> Insira a marca do novo carro");
        String marca = input.next();

        System.out.println("---> Insira o modelo do novo carro");
        String modelo = input.next();

        System.out.println("---> Insira a cilindrada do novo carro");
        int cilindrada = Integer.parseInt(input.next());

        System.out.println("---> Insira a potencia do motor combustão do novo carro");
        int potencia_comb = Integer.parseInt(input.next());

        System.out.println("---> Insira a potencia do motor eletrico do novo carro");
        System.out.println("---> (se for hibrido valor diferente de 0)");
        System.out.println("---> (se não for hibrido valor igual a 0)");
        int potencia_eletr = Integer.parseInt(input.next());

        System.out.println("---> Insira o PAC do novo carro");
        double pac = Double.parseDouble(input.next());

        //----- adiciona c1 -----

        if(categoria == "C1" && potencia_eletr == 0){
            PC1 carro_c1 = new PC1();
            carro_c1 = new PC1(id, marca, modelo, cilindrada, potencia_comb,"", pac,0).clone();
            model.subCarroFacade.criaCarro(carro_c1);
        }
        else if(categoria == "C1" && potencia_eletr != 0){
            PC1H carro_c1_h = new PC1H();
            carro_c1_h = new PC1H(id, marca, modelo, cilindrada, potencia_comb,"", potencia_eletr,pac,0).clone();
            model.subCarroFacade.criaCarro(carro_c1_h);
        }

        //----- adiciona c2 -----

        else if(categoria == "C2" && potencia_eletr == 0){
            PC2 carro_c2 = new PC2();
            carro_c2 = new PC2(id, marca, modelo, cilindrada, potencia_comb, potencia_comb,"",pac,0).clone();
            model.subCarroFacade.criaCarro(carro_c2);
        }
        else if(categoria == "C2" && potencia_eletr != 0){
            PC2H carro_c2_h = new PC2H();
            carro_c2_h = new PC2H(id, marca, modelo, cilindrada, potencia_comb, potencia_comb,"",potencia_eletr,pac,0).clone();
            model.subCarroFacade.criaCarro(carro_c2_h);
        }

        //----- adiciona gt -----

        else if(categoria == "GT" && potencia_eletr == 0){
            GT carro_gt = new GT();
            carro_gt = new GT(id, marca, modelo, cilindrada, potencia_comb,"",pac,0).clone();
            model.subCarroFacade.criaCarro(carro_gt);
        }
        else if(categoria == "GT" && potencia_eletr != 0){
            GTH carro_gt_h = new GTH();
            carro_gt_h = new GTH(id, marca, modelo, cilindrada, potencia_comb, "",potencia_eletr,pac,0).clone();
            model.subCarroFacade.criaCarro(carro_gt_h);
        }

        //----- adiciona sc -----

        else if(categoria == "SC" && potencia_eletr != 0){
            SC carro_sc = new SC();
            carro_sc = new SC(id, marca, modelo, cilindrada, potencia_comb,"",pac,0).clone();
            model.subCarroFacade.criaCarro(carro_sc);
        }

        System.out.println("Carro criado com sucesso");
    }

    private void adicionar_piloto(){
        Scanner input = new Scanner(System.in);

        System.out.println("---> Insira o nome do novo piloto");
        String nome = input.next();


        if(model.subCircuitoFacade.pilotoExiste(nome)){
            System.out.println("Erro, esse piloto já existe");
        }

        System.out.println("---> Insira o CTS do novo piloto");
        double cts = Double.parseDouble(input.next());

        System.out.println("---> Insira o SVA do novo piloto");
        double sva = Double.parseDouble(input.next());

        model.subCircuitoFacade.adicionaPiloto(nome, cts, sva);
        System.out.println("Piloto criado com sucesso");
    }

    private void adicionar_campeonato(){
        Scanner input = new Scanner(System.in);
        String nome_circ;
        Circuito circuito = new Circuito();
        List<Corrida> corridas = new ArrayList<>();
        Map<Piloto, Carro> partcipantes = new HashMap<>();
        Set<Carro> r = new TreeSet<>();
        List<Carro> p = new ArrayList<>();
        Campeonato campeonato = new Campeonato();
        Map<String,Integer> cla = new HashMap<>();
        Map<String,Integer> claH = new HashMap<>();

        System.out.println("---> Insira o nome do novo campeonato");
        String nome_camp = input.next();

        System.out.println("---> Quantos circuitos?");
        int num_circuitos = Integer.parseInt((input.next()));

        System.out.println("---> Escolha os circuitos que pertencem ao campeonato");
        lista_circuitos();
        for(int i = 1; i <= num_circuitos; i++){
            nome_circ = input.next();

            if (this.model.subCircuitoFacade.circuitoExiste(nome_circ)) {
                circuito = model.subCircuitoFacade.getCircuito(nome_circ).clone();
                corridas.add(new Corrida(partcipantes, i,circuito,r,p,0));
            } else {
                System.out.println("Erro - esse nome de circuito não existe");
            }
            System.out.println("    ......    ");
        }

        campeonato = new Campeonato(nome_camp,corridas,cla,claH,0);
        model.subCampeonatoFacade.adicionaCamp(campeonato);
        System.out.println("Campeonato criado com sucesso");
    }


    //------------------------------------- METODOS JOGADOR -------------------------------------//


    private void lista_pilotos(){
        try {
            System.out.println(this.model.subCircuitoFacade.getPilotos());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void lista_carros(){
        try {
            System.out.println(this.model.subCarroFacade.getCarros());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void lista_circuitos(){
        try {
            System.out.println(this.model.subCircuitoFacade.get_todos_Circuito());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void configurar_campeonato(){
        // escolha do piloto e carro

        try {
            System.out.println("Escolher piloto (selecionar nome)");
            lista_pilotos();
            String num = input.nextLine();
            if (this.model.subCircuitoFacade.pilotoExiste(num)) {
                this.meu_piloto = this.model.subCircuitoFacade.get_1_Piloto(num);
            } else {
                System.out.println("Erro - esse nome de piloto não existe");
            }

            System.out.println("Escolher carro (selecionar id)");
            lista_carros();
            num = input.nextLine();
            int carro_escolhido = Integer.parseInt(num);
            if (this.model.subCarroFacade.carroExiste(carro_escolhido)) {
                this.meu_carro = this.model.subCarroFacade.getCar(carro_escolhido);
            } else {
                System.out.println("Erro - esse id de carro não existe");
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void simular_campeonato(){
        if(this.meu_piloto == null){
            System.out.println("Erro - não selecionaste um piloto");
            return;
        }
        if(this.meu_carro == null){
            System.out.println("Erro - não selecionaste um carro");
            return;
        }

    }

    private void simular_corrida(){
        if(this.meu_piloto == null){
            System.out.println("Erro - não selecionaste um piloto");
            return;
        }
        if(this.meu_carro == null){
            System.out.println("Erro - não selecionaste um carro");
            return;
        }

        System.out.println("Escolher circuito (selecionar nome)");
        lista_circuitos();
        String num = input.nextLine();

        Circuito circuito = new Circuito();

        if (this.model.subCircuitoFacade.circuitoExiste(num)) {
             circuito = model.subCircuitoFacade.getCircuito(num).clone();
        } else {
            System.out.println("Erro - esse nome de circuito não existe");
        }

        System.out.println("Escolher clima");
        System.out.println("1 = chuva | 0 = sol");
        int clima = Integer.parseInt(input.nextLine());

        Map<Piloto, Carro> participantes = new HashMap<>();
        Set<Carro> resultados =  new TreeSet<>();
        List<Carro> primeiroVolta = new ArrayList<>();

        participantes.put(meu_piloto.clone(), meu_carro.clone());
        resultados.add(meu_carro.clone());
        primeiroVolta.add(meu_carro.clone());

        Corrida corrida_1 = new Corrida(participantes, 1, circuito, resultados, primeiroVolta, clima);
        corrida_1.simulaCorrida();

    }

}
