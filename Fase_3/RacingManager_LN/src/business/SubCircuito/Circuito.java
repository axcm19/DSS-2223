package business.SubCircuito;
/**
 * Classe que representa um circuito
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import business.SubCorrida.TimeConverter;
import business.SubCircuito.Record;
import business.SubCircuito.Tipo_Estrada;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

public class Circuito implements Serializable {


    //------------------------------------- VARIAVEIS DE INSTANCIA -------------------------------------//


    private String nome;
    private int distancia;
    private int voltas;
    //private long tempoMedio;  // ---> nao vamos usar pois temos categorias diferentes de carros
    private Map<String, Double> temposMedios; // <categoria, tempo> ---> lista o tempo medio para cada categoria de carro
    private double penalizacao; // penalizaçao no tempo por sair da pista
    //private long tempoBox;  // ---> nao temos box no enunciado logo nao vamos usar
    private List<Tipo_Estrada> lista_tipo_estrada;  // lista por ordem em que aparecem todas as curvas, rectas e chicanes de um circuito
    private Record record;



    //------------------------------------- CONSTRUTORES -------------------------------------//


    public Circuito() {
        this.nome = "";
        this.distancia = 0;
        this.voltas = 0;
        this.temposMedios = new HashMap<String, Double>();
        this.penalizacao = 0;
        this.lista_tipo_estrada = new ArrayList<>();
        this.record = null;
    }

    public Circuito(String n,int d, int v, Map<String, Double> temp_med, double ds, List<Tipo_Estrada> road_list, Record r) {
        this.nome = n;
        this.distancia = d;
        this.voltas = v;
        HashMap<String,Double> aux = new HashMap<>();

        if(temp_med == null) {
            //this.temposMedios = new HashMap<>();
            aux = new HashMap<>();
        }

        else {
            for(String g : temp_med.keySet()) {
                aux.put(g, temp_med.get(g));
            }
        }

        this.temposMedios = aux;
        this.penalizacao = ds;

        List<Tipo_Estrada> aux_2 = new ArrayList<>();

        if(road_list == null) {
            aux_2 = new ArrayList<>();
        }

        else {
            for(Tipo_Estrada te : road_list) {
                aux_2.add(te);
            }
        }


        this.lista_tipo_estrada = aux_2;
        this.record = r.clone();
    }
    
    public Circuito(Circuito c) {
        this.nome = c.getNome();
        this.distancia = c.getDistancia();
        this.voltas = c.getVoltas();
        this.temposMedios = c.getTemposMedios();
        this.penalizacao = c.getPenalizacao();
        this.lista_tipo_estrada = c.getListaEstrada();
        this.record = c.getRecord();
    }


    //------------------------------------- GETS / SETS -------------------------------------//


    public String getNome() {
        return this.nome;
    }
    
    public int getDistancia() {
        return this.distancia;
    }
    
    public int getVoltas() {
        return this.voltas;
    }
    
    public Map<String,Double> getTemposMedios() {
        HashMap<String,Double> aux = new HashMap<>();

        for(String g : this.temposMedios.keySet()) {
            aux.put(g, this.temposMedios.get(g));
        }

        return aux;
    }
    
    public double getPenalizacao() {
        return this.penalizacao;
    }

    public List<Tipo_Estrada> getListaEstrada() {
        List<Tipo_Estrada> aux = new ArrayList<>();

        for(Tipo_Estrada te : this.lista_tipo_estrada) {
            aux.add(te);
        }

        return aux;
    }
    
    public Record getRecord() {
        return this.record.clone();
    }
    
    public void setNome(String n) {
        this.nome = n;
    }
    
    public void setDistancia(int d) {
        this.distancia = d;
    }
    
    public void setVoltas(int v) {
        this.voltas = v;
    }
    
    public void setPenalizacao(long ds) {
        this.penalizacao = ds;
    }
    
    public void setRecord(Record r) {
        this.record = r.clone();
    }

    public void setTempoMedio(String categoria, double tempo) {
        this.temposMedios.put(categoria, tempo);
    }

    public void setListaEstrada(List<Tipo_Estrada> road_list){
        List<Tipo_Estrada> aux_2 = new ArrayList<>();

        if(road_list == null) {
            aux_2 = new ArrayList<>();
        }

        else {
            for(Tipo_Estrada te : road_list) {
                aux_2.add(te);
            }
        }

        this.lista_tipo_estrada = aux_2;
    }


    //------------------------------------- METODOS USUAIS -------------------------------------//


    public Circuito clone() {
        return new Circuito(this);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nNome: ");sb.append(this.nome);
        sb.append("\nDistância: ");sb.append(this.distancia);
        sb.append("\nNúmero de voltas: ");sb.append(this.voltas);
        //sb.append("\nTempo Medio: ");sb.append(TimeConverter.toTimeFormat(this.tempoMedio));
        sb.append("\nDesvio Tempo (aka: Penalização): ");sb.append(TimeConverter.toTimeFormat(this.penalizacao));
        
        sb.append("\nTempo Recorde: ");sb.append(this.record.toString());
        return sb.toString();
    }
    
    public boolean equals(Object o) {
       if(this == o) {
           return true;
       }
       
       if(o == null || this.getClass() != o.getClass()) {
           return false;
       }
       
       Circuito c = (Circuito) o;
       return ( this.nome.equals(c.getNome()) &&
                this.distancia == c.getDistancia() &&
                this.voltas == c.getVoltas() &&
                //this.tempoMedio == c.getTempoMedio() &&
                this.temposMedios == c.getTemposMedios() &&
                this.penalizacao == c.getPenalizacao() &&
                this.lista_tipo_estrada == c.getListaEstrada() &&
                this.record.equals(c.getRecord()));
    }
    
}
