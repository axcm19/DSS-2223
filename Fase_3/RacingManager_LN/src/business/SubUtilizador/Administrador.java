package business.SubUtilizador;

import business.SubCarro.*;
import business.SubCircuito.Circuito;
import business.SubCircuito.Piloto;
import data.CarroDAO;
import data.CircuitoDAO;
import business.SubCircuito.Record;
import business.SubCircuito.Tipo_Estrada;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Administrador implements Comparable<Administrador>, Serializable{

    //------------------------------------- VARIAVEIS DE INSTANCIA -------------------------------------//


    private String username;
    private String password;
    private boolean isAutenticado;


    //------------------------------------- CONSTRUTORES -------------------------------------//


    public Administrador() {
        this.username = "";
        this.password = "";
        this.isAutenticado=false;
    }

    public Administrador(String name, String pass,boolean aut) {
        this();
        this.username = name;
        this.password = pass;
        this.isAutenticado=aut;
    }

    public Administrador(Administrador a) {
        this.username = a.getUsername();
        this.password = a.getPassword();
        this.isAutenticado=a.AdAut();
    }


    //------------------------------------- GETS / SETS -------------------------------------//


    public String getUsername() {
        return this.username;
    }


    public String getPassword() {
        return this.password;
    }

    public boolean AdAut(){
        return this.isAutenticado;
    }
    
    public void setAutenticado(){
        if(this.isAutenticado==true) isAutenticado=false;
        else isAutenticado=true;
    }
    //------------------------------------- METODOS USUAIS -------------------------------------//


    public Administrador clone() {
        return new Administrador(this);
    }

    /**
     * Informacao do jogador
     */
    public String printInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nADMINISTRADOR");
        sb.append("\nUsername: ");
        sb.append(this.username);

        return sb.toString();
    }

    public int compareTo(Administrador outro_administrador) {
        return 0;
    }


    //------------------------------------- OUTROS METODOS -------------------------------------//

    /*
    ESTES METODOS VÃO À BASE DE DADOS E ADICIONAM NOVAS COISAS NAS TABELAS
     */


    public void regista_ADMI(String username, String password){
        // falta UtilizadorDAO
    }

    public void autentica_ADMI(String username, String password){
        // falta UtilizadorDAO
    }

    public void adicionaPiloto(String nome, double sva, double cts){
        Piloto p = new Piloto(nome, sva, cts);
        // falta PilotoDAO
    }

    /*
    ---> ESTES METODOS DE ADICIONAR DEVERAM SER COLOCADOS NO SubUtilizadorFacade

    public int adicionaCarro(String categoria, int id, String marca, String modelo, int cilindrada, int potencia, int eletrico, int p_mecanica){
        // se for adicionado com sucesso devolve 1; caso contrario devolve 0
        int res = 0;
        if (categoria == "PC1"){
            PC1 carro_cat_1 = new PC1(id, marca, modelo, cilindrada, potencia);
            CarroDAO.put(id, carro_cat_1);
            res = 1;
        }
        else if (categoria == "PC1H"){
            PC1H carro_cat_1_H = new PC1H(id, marca, modelo, cilindrada, potencia, eletrico);
            CarroDAO.put(id, carro_cat_1_H);
            res = 1;
        }
        else if (categoria == "PC2"){
            PC2 carro_cat_2 = new PC2(id, marca, modelo, cilindrada, potencia, p_mecanica);
            CarroDAO.put(id, carro_cat_2);
            res = 1;
        }
        else if (categoria == "PC2H"){
            PC2H carro_cat_2_H = new PC2H(id, marca, modelo, cilindrada, potencia, p_mecanica, eletrico);
            CarroDAO.put(id, carro_cat_2_H);
            res = 1;
        }
        else if (categoria == "GT"){
            GT carro_cat_gt = new GT(id, marca, modelo, cilindrada, potencia);
            CarroDAO.put(id, carro_cat_gt);
            res = 1;
        }
        else if (categoria == "GTH"){
            GTH carro_cat_gt_H = new GTH(id, marca, modelo, cilindrada, potencia, eletrico);
            CarroDAO.put(id, carro_cat_gt_H);
            res = 1;
        }
        else if (categoria == "SC"){
            SC carro_cat_sc = new SC(id, marca, modelo, cilindrada, potencia);
            CarroDAO.put(id, carro_cat_sc);
            res = 1;
        }
        return res;
    }

    public void adicionaCircuito(String n, int d, int v, Map<String, Long> temp_med, long ds, List<Tipo_Estrada> road_list, Record r){
        Circuito c = new Circuito(n, d, v, temp_med, ds, road_list, r);
        CircuitoDAO.put(n, c);
    }

    public void adicionaCampeonato(){
        // falta melhorar o Campeonato.java
        // falta CampeonatoDAO
    }
    */

    public void alteraPassword(String novaP){
        this.password= new String(novaP);
    }

}

