package business.SubUtilizador;
/**
 * Write a description of class Jogador here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import business.SubCampeonato.Campeonato;
import business.SubCarro.Carro;
import business.SubCircuito.Piloto;

import java.io.Serializable;

public class Jogador implements Comparable<Jogador>,Serializable {


    //------------------------------------- VARIAVEIS DE INSTANCIA -------------------------------------//


    private String username;
    private String password;
    private int pontosJogador;  // pontos totais obtidos por um jogador
    private boolean isAutenticado; // para verificar se um jogador está autenticado
    private int pontosJCorrida; // pontos obtidos por um jogador numa corrida
    


    //------------------------------------- CONSTRUTORES -------------------------------------//


    public Jogador() {
        this.username = "";
        this.password = "";
        this.pontosJogador = 0;
        this.isAutenticado=false;
        this.pontosJCorrida=0;
    }

    public Jogador(String name, String pass, int pon,boolean aut,int pc) {
        this();
        this.username = name;
        this.password = pass;
        this.pontosJogador = pon;
        this.isAutenticado=aut;
        this.pontosJCorrida=pc;
    }

    public Jogador(Jogador j) {
        this.username = j.getUsername();
        this.password = j.getPassword();
        this.pontosJogador = j.getPontos();
        this.isAutenticado=j.jogAutenticado();
        this.pontosJCorrida=j.getPontosCorrida();
    }


    //------------------------------------- GETS / SETS -------------------------------------//


    public String getUsername() {
        return this.username;
    }


    public String getPassword() {
        return this.password;
    }

    public int getPontos(){
        return this.pontosJogador;
    }
    public boolean jogAutenticado(){
        return this.isAutenticado;
    }
    public int getPontosCorrida(){
        return this.pontosJCorrida;
    }

  
    public void setAutenticado(){
        if(this.isAutenticado==true) isAutenticado=false;
        else isAutenticado=true;
    }


    //------------------------------------- METODOS USUAIS -------------------------------------//


    public Jogador clone() {
        return new Jogador(this);
    }


    /**
     * Informacao do jogador
     */
    public String printInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nJOGADOR");
        sb.append("\nUsername: ");
        sb.append(this.username);
        sb.append("\nPontuação Global:");
        sb.append(this.pontosJogador);
        sb.append("\nPontuação corrida:");
        sb.append(this.pontosJCorrida);
        return sb.toString();
    }


    @Override
    public int compareTo(Jogador outro_jogador) {
        return 0;
    }

    //adiciona ao jogador os pontos da corrida
    public void addPontosJCorrida(int p){
        this.pontosJCorrida += p;
    }

    //adiciona ao jogador os seus pontos globais
    public void addPontosJG(int p){
        this.pontosJogador += p;
    }

}
  

