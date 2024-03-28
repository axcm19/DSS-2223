package business.SubCircuito;
/**
 * Write a description of class Piloto here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.Serializable;

public class Piloto implements Serializable {


    //------------------------------------- VARIAVEIS DE INSTANCIA -------------------------------------//


    private String nome;
    //private String nacionalidade;
    //private int qualidade;
    //private int qualidade_chuva;
    //private int palmares;
    private double sva;     // SVA ---> Segurança vs. Agressividade
    private double cts;     // CTS ---> Chuva vs. Tempo Seco


    //------------------------------------- CONSTRUTORES -------------------------------------//


    public Piloto() {
        this.nome = "";
        this.sva = 0;
        this.cts = 0;
        
    }
    
    public Piloto(String nome, double sva, double cts) {
        this.nome = nome;
        this.sva = sva;
        this.cts = cts;
    }
    
    public Piloto(Piloto p) {
        this.nome = p.getNome();
        this.sva = p.getSva();
        this.cts = p.getCts();
    }


    //------------------------------------- GETS / SETS -------------------------------------//


    public String getNome()
    {
        return this.nome;
    }
    
    
    public double getSva()
    {
        return this.sva;
    }
    
    public double getCts()
    {
        return this.cts;
    }
    
    
    
    public void setNome(String nome)
    {
        this.nome = nome;
    }
    
    
    public void setSva(int d)
    {
        this.sva = d;
    }
    
    public void setCts(int d)
    {
        this.cts = d;
    }


    //------------------------------------- METODOS USUAIS -------------------------------------//


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nNome: "); sb.append(this.nome);
        
        
        sb.append("\tSVA: ");sb.append(this.sva);
        sb.append("\tCTS: ");sb.append(this.cts);
        return sb.toString();
    }
    
    public Piloto clone() {
        return new Piloto(this);
    }
    
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        
        if((o == null) || (this.getClass() != o.getClass())) {
            return false;
        }
        
        Piloto p = (Piloto) o;
        return (this.nome.equals(p.getNome()) && 
                
                this.sva==p.getSva() &&
                this.cts == p.getCts());
    }
}
